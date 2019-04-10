package de.htwg.se.juraePuzz.controller


import de.htwg.se.juraePuzz.controller.GameStatus.GameStatus
import de.htwg.se.juraePuzz.model.gridBaseImpl.{Grid, Level, Matrix, Piece}

import scala.swing.Publisher

trait ControllerInterface extends Publisher {

  def createEmptyGrid(): Unit

  def createNewGrid: Unit

  def toggleShow(): Unit

  def statusText: String

  def move(xS:Int, yS:Int, xT:Int, yT:Int): Unit

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

}


import scala.swing.event.Event

class CellChanged extends Event
class ShowCand extends Event
