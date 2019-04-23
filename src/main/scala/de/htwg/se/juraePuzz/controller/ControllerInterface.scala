package de.htwg.se.juraePuzz.controller


import de.htwg.se.juraePuzz.controller.GameStatus.GameStatus
import de.htwg.se.juraePuzz.model.GridInterface
import de.htwg.se.juraePuzz.model.gridBaseImpl._

import scala.swing.Publisher

trait ControllerInterface extends Publisher {

  def createEmptyGrid(): Unit

  def createNewGrid: Unit

  def toggleShow(): Unit

  def statusText: String

  def move(direction:Direction.Value): Unit

  def undo: Unit

  def redo: Unit

  def solve(): Unit

  def save: Unit

  def load: Unit

  def gridToString: String

  def gridSize: Int

  def gameStatus: GameStatus
  //def gridMatrix: Matrix
  //def isSet(row: Int, col: Int): Boolean
  def cell(row: Int, col: Int): Piece

  def solveActor(value: GridInterface):Unit
}


import scala.swing.event.Event

class CellChanged extends Event
class ShowCand extends Event
