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

import scala.swing.Publisher

class Controller @Inject() (var grid: GridInterface) extends ControllerInterface with Publisher{

  var gameStatus: GameStatus = IDLE
  val undoManager = new UndoManager
  val injector = Guice.createInjector(new JuraePuzzModule)
  val fileIo = injector.instance[FileIOInterface]

  def create_empty_grid(): Unit ={
    grid.empty()
    toggleShow
  }

  def toggleShow() = publish(new CellChanged)

  def statusText: String = GameStatus.message(gameStatus)

  def create_Level(): Unit ={
    var st1 = new GetSpecifiedLevel()
    if (grid.fill(st1.createLevel(this))) {
      gameStatus = CREATE_LEVEL
    }
  toggleShow()
  }

  def move(xS:Int, yS:Int, xT:Int, yT:Int): Unit = {
    if (undoManager.doStep(new SetCommand(xS, yS, xT, yT, this))) {
      if (new Solver(grid).check_level()) {
        gameStatus = SOLVED
      } else {
        gameStatus = NOT_SOLVED_YET
      }
    } else {
      gameStatus = ILLEGAL_TURN
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

  def solve(): Unit ={
    grid.solve()
    gameStatus = SOLVED
    toggleShow()
  }

  def create_Level(l:Level): Unit ={
    grid.fill(l)
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
        create_empty_grid()
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

  def gridSize:Int = grid.getSize()

  def gridMatrix: Matrix = grid.getMatrix()
}
