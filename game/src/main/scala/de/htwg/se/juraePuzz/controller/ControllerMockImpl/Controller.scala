package de.htwg.se.juraePuzz.controller.ControllerMockImpl

import de.htwg.se.juraePuzz.controller.{ControllerInterface, GameStatus}
import de.htwg.se.juraePuzz.model.GridInterface
import de.htwg.se.juraePuzz.model.gridBaseImpl._
import play.api.libs.json.{JsString, JsValue}

class Controller(var grid: Grid) extends ControllerInterface{
  override def createEmptyGrid(): Unit = {}

  override def toggleShow(): Unit = {}

  override def statusText: String = "Status"

  override def createNewGrid: Unit = {}

  override def move(direction:Direction.Value): Unit = {}

  override def undo: Unit = {}

  override def redo: Unit = {}

  override def solve(): Unit = {}

  override def save: Unit = {}

  override def load: Unit = {}

  override def gridToString = "Grid"

  override def gridSize = 4

  override def gameStatus = GameStatus.IDLE

  //override def isSet(row: Int, col: Int): Boolean = true

  override def cell(row: Int, col: Int): Piece = Piece(0)

  override def getJsonGrid: JsValue = JsString("play")

  override def saveToDB: Unit = {}

  override def loadFromDB: Unit = {}
}