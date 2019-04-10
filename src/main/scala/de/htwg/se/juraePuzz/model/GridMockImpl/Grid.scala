package de.htwg.se.juraePuzz.model.GridMockImpl

import de.htwg.se.juraePuzz.model.GridInterface
import de.htwg.se.juraePuzz.model.gridBaseImpl.{Level, Matrix, Piece}

class Grid extends GridInterface {
  def empty(): Unit = {}

  //override def getSize(): Int = 4

  //override def fill(p: Piece, row: Int, col: Int): Unit = {}

  //override def fill(l: Level): Boolean = true

  def move(xS: Int, yS: Int, xT: Int, yT: Int): Option[GridInterface] = None

  def checkMove(xS: Int, yS: Int, xT: Int, yT: Int): Boolean = true

  def getLevel(): Level = Level(Array(1, 2, 3, 4))

  def solve(): Unit = {}

  def size: Int = 1

  def set(row: Int, col: Int, value: Int): GridInterface = this

  def cell(row: Int, col: Int): Piece = Piece(0)

  def createNewGrid: GridInterface = this

  def setMove(row: Int, col: Int, value: Int, row1: Int, col1: Int, value1: Int): GridInterface = this
}
