package de.htwg.se.juraePuzz.controller.controllerBaseImpl

import com.google.inject.name.Names
import com.google.inject.{Guice, Inject}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.juraePuzz.JuraePuzzModule
import de.htwg.se.juraePuzz.aview.Gui.CellChanged
import de.htwg.se.juraePuzz.controller._
import de.htwg.se.juraePuzz.controller.GameStatus._
import de.htwg.se.juraePuzz.model.GridInterface
import de.htwg.se.juraePuzz.model.fileIoComponent.FileIOInterface
import de.htwg.se.juraePuzz.model.gridBaseImpl._
import de.htwg.se.juraePuzz.util._

import scala.concurrent.Future
import scala.swing.Publisher

class Controller @Inject()(var grid: GridInterface) extends ControllerInterface with Publisher {

  var gameStatus: GameStatus = IDLE
  val undoManager = new UndoManager
  val injector = Guice.createInjector(new JuraePuzzModule)
  val fileIo = injector.instance[FileIOInterface]


  //def isSet(row: Int, col: Int): Boolean = grid.cell(row, col).isSet

  def cell(row: Int, col: Int) = grid.cell(row, col)

  def toggleShow() = publish(new CellChanged)

  def statusText: String = GameStatus.message(gameStatus)

  /*
    def create_Level(): Unit = {

      var st1 = new GetSpecifiedLevel()
      if (grid.fill(st1.createLevel(this))) {
        gameStatus = CREATE_LEVEL
      }
      toggleShow()
    }
  */
  def move(direction: Direction.Value): Unit = {
     undoManager.doStep(new SetCommand(direction, this)) match{
      case Some(value)=> grid = value
      case None=> gameStatus = ILLEGAL_TURN
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

  def solve(): Unit = {
    //grid.solve()
    val solverNew = new Solver(grid)
    solverNew.getPosMoves()
    solverNew.search() match {
      case Some(value)=> grid = value
      case None=> gameStatus = NOT_SOLVED_YET
    }
    gameStatus = SOLVED
    toggleShow()
  }

  def save: Unit = {
    fileIo.save(grid)
    gameStatus = SAVED
    toggleShow()
  }

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
    publish(new CellChanged)
  }

  /*
    def createEmptyGrid: Unit = {
    grid.size match {
    case 1 => grid = injector.instance[GridInterface](Names.named("tiny"))
    case 3 => grid = injector.instance[GridInterface](Names.named("small"))
    case 9 => grid = injector.instance[GridInterface](Names.named("normal"))
    case _ =>
  }
    publish(new CellChanged)
  }

  def createNewGrid: Unit = {
    grid.size match {
    case 1 => grid = injector.instance[GridInterface](Names.named("tiny"))
    case 3 => grid = injector.instance[GridInterface](Names.named("small"))
    case 9 => grid = injector.instance[GridInterface](Names.named("normal"))
    case _ =>
  }
    grid = grid.createNewGrid
    gameStatus = GameStatus.CREATE_LEVEL
    publish(new CellChanged)
  }
  */
}
