package de.htwg.se.juraePuzz.aview

import de.htwg.se.juraePuzz.aview.Gui.CellChanged
import de.htwg.se.juraePuzz.controller.{ControllerInterface, GameStatus}
import de.htwg.se.juraePuzz.util.Observer
import de.htwg.se.juraePuzz.controller.GameStatus._
import de.htwg.se.juraePuzz.controller.controllerBaseImpl.Controller
import de.htwg.se.juraePuzz.model.gridBaseImpl.Direction

import scala.swing.Reactor

class Tui (controller: ControllerInterface) extends Reactor{

  listenTo(controller)
  val size = controller.gridSize

  def process_input_line(input: String):Unit = {
    input match {
      case "n" => controller.createEmptyGrid()
      case "z" => controller.undo
      case "y" => controller.redo
      case "c" => controller.createNewGrid
      case "s" => controller.solve()
      case "f" => controller.save
      case "l" => controller.load
      case "up" => controller.move(Direction.Up)
      case "down" => controller.move(Direction.Down)
      case "left" => controller.move(Direction.Left)
      case "right" => controller.move(Direction.Right)
      case _ =>
    }
  }

 reactions += {
   case even: CellChanged => printTui
 }
  def printTui: Unit = {
    println(controller.gridToString)
    println(GameStatus.message(controller.gameStatus))
  }
}
