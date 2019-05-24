package de.htwg.se.juraePuzz.controller.controllerBaseImpl

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import com.google.inject.{Guice, Inject}
import de.htwg.se.juraePuzz.JuraePuzzModule
import de.htwg.se.juraePuzz.aview.Gui.CellChanged
import de.htwg.se.juraePuzz.controller.GameStatus._
import de.htwg.se.juraePuzz.controller._
import de.htwg.se.juraePuzz.model.GridInterface
import de.htwg.se.juraePuzz.model.databaseComponent.DatabaseInterface
import de.htwg.se.juraePuzz.model.fileIoComponent.FileIOInterface
import de.htwg.se.juraePuzz.model.gridBaseImpl._
import de.htwg.se.juraePuzz.util._
import net.codingwell.scalaguice.InjectorExtensions._
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.swing.Publisher
import scala.util.{Failure, Success}

class Controller @Inject()(var grid: GridInterface) extends ControllerInterface with Publisher {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  var gameStatus: GameStatus = IDLE
  val undoManager = new UndoManager
  val injector = Guice.createInjector(new JuraePuzzModule)
  val fileIo = injector.instance[FileIOInterface]


  val database = injector.instance[DatabaseInterface]


  //def isSet(row: Int, col: Int): Boolean = grid.cell(row, col).isSet

  def cell(row: Int, col: Int): Piece = grid.cell(row, col)

  def toggleShow(): Unit = publish(new CellChanged)

  def statusText: String = GameStatus.message(gameStatus)

  def move(direction: Direction.Value): Unit = {
    undoManager.doStep(new SetCommand(direction, this)) match {
      case Some(value) => grid = value
      case None => gameStatus = ILLEGAL_TURN
    }
    if (new Solver(grid).check_level()) {
      gameStatus = SOLVED
    } else {
      gameStatus = NOT_SOLVED_YET
    }
    toggleShow()
  }

  def undo: Unit = {
    undoManager.undoStep
    toggleShow
  }

  def redo: Unit = {
    undoManager.redoStep
    toggleShow
  }

  def isSolved(): Unit = {
    while (gameStatus != GameStatus.SOLVED) {}
  }


  def timer(): Unit = {
    var timerStart: Long = 0
    val t = Future {
      timerStart = System.nanoTime()
      isSolved()
    }
    t.onComplete {
      case Success(value) => {
        val resulttime = (System.nanoTime() - timerStart) * math.pow(10, -9)
        printf("Time: %.0fs", resulttime)
      }
      case Failure(e) =>
        println("Error: " + e)
    }
  }

  def solve(): Unit = {
    //grid.solve()

    val solverNew = new Solver(grid)
    val f = Future {
      solverNew.dfsMutableIterative(grid)

    }
    f.onComplete {
      case Success(value) => {
        grid = value
        gameStatus = SOLVED
        toggleShow()
      }
      case Failure(exception) => {
        gameStatus = ERROR
        println("Error " + exception)
      }
    }
  }

  def save: Unit = {
    val json = fileIo.getJasonGrid(grid)

    fileIo.save(grid)
    gameStatus = SAVED
    toggleShow()
  }

  def saveToDB: Unit = {
    val json = fileIo.getJasonGrid(grid)
    database.saveGrid(json)
    gameStatus = SAVED
    toggleShow()
  }

  override def loadFromDB: Unit ={
    val gridFromDB = database.loadGrid()
    gridFromDB
      .onComplete {
        case Success(res) => {
          println("loadFromDB "+res)
          val responseAsString: Future[String] = Unmarshal(res.entity).to[String]
          val parsed = responseAsString.onComplete {
            case Success(res) => {
              val json: JsValue = Json.parse(res)
              val x = database.loadFromJson(json)
              x match {
                case None => {
                  createEmptyGrid()
                  println("fail")
                  gameStatus = COULDNOTLOAD
                  toggleShow()
                }
                case Some(_grid) => {
                  grid = _grid
                  println(grid)
                  gameStatus = LOADED
                  toggleShow()
                }
              }
            }
            case Failure(_) => sys.error("wrong")
          }
        }
        case Failure(_) => sys.error("something wrong")
      }
  }


    /*
    val gridFromDB: Future[HttpResponse] = database.loadGrid()
    gridFromDB.onComplete {
      case Success(value) => {
        val tmp: Future[String] = value.entity.toStrict(1 seconds).map(_.data.decodeString("UTF-8"))
        tmp.onComplete {
          case Success(x) => {
            val json = Json.parse(x)
            val tmp = database.loadFromJson(json)
            tmp match {
              case None => {
                createEmptyGrid()
                gameStatus = COULDNOTLOAD
                println("fail")
                toggleShow()
              }
              case Some(_grid) => {
                grid = _grid
                gameStatus = LOADED
                println(grid)
                toggleShow()
              }
            }


          }
        }
      }
      case Failure(_) => sys.error("failed getting response")
    }
/*
    val json = Json.parse(gridFromDB)
    val x = database.loadFromJson(json)
    x match {
      case None => {
        createEmptyGrid()
        gameStatus = COULDNOTLOAD
        println("fail")
      }
      case Some(_grid) => {
        grid = _grid
        gameStatus = LOADED
        println("it worked")
      }
    }*/

    /*val gridFromDB = database.loadGrid()
    gridFromDB
      .onComplete {
        case Success(res) => {
          val responseAsString: Future[String] = Unmarshal(res.entity).to[String]
          val parsed = responseAsString.onComplete {
            case Success(res) => {
              val json: JsValue = Json.parse(res)
              val x = database.loadFromJson(json)
              x match {
                case None => {
                  createEmptyGrid()
                  gameStatus = COULDNOTLOAD
                }
                case Some(_grid) => {
                  println("test")
                  grid = _grid
                  gameStatus = LOADED
                }
              }
              toggleShow()
            }
            case Failure(_) => sys.error("wrong")
          }
        }
        case Failure(_) => sys.error("something wrong")
      }*/
    toggleShow()
  }*/

  override def load: Unit = {
    val gridOption = fileIo.load

    gridOption match {
      case None => {
        createEmptyGrid()
        gameStatus = COULDNOTLOAD
      }
      case Some(_grid) => {
        grid = _grid
        gameStatus = LOADED
      }
    }
    toggleShow()
  }



  override def gridToString: String = grid.toString()

  def gridSize: Int = grid.size

  //def gridMatrix: Matrix = grid.

  def createEmptyGrid(): Unit = {
    grid.empty()
    toggleShow
    gameStatus = GameStatus.CREATE_LEVEL
    publish(new CellChanged)
  }

  override def createNewGrid: Unit = {
    grid = grid.createNewGrid
    toggleShow
    gameStatus = GameStatus.CREATE_LEVEL
    timer()
    publish(new CellChanged)
  }

  def getJsonGrid() = fileIo.getJasonGrid(grid)


}
