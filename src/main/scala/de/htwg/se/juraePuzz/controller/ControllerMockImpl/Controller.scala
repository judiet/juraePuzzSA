package de.htwg.se.juraePuzz.controller.ControllerMockImpl

import de.htwg.se.juraePuzz.controller.{ControllerInterface, GameStatus}
import de.htwg.se.juraePuzz.model.gridBaseImpl.{Grid, Level, Matrix}

class Controller(var grid: Grid) extends ControllerInterface{
  override def create_empty_grid(): Unit = {}

  override def toggleShow(): Unit = {}

  override def statusText: String = "Status"

  override def create_Level(): Unit = {}

  override def move(xS: Int, yS: Int, xT: Int, yT: Int): Unit = {}

  override def undo: Unit = {}

  override def redo: Unit = {}

  override def solve(): Unit = {}

  override def create_Level(l: Level): Unit = {}

  override def save: Unit = {}

  override def load: Unit = {}

  override def gridToString = "Grid"

  override def gridSize = 4

  override def gridMatrix = Matrix(4)

  override def gameStatus = GameStatus.IDLE
}
