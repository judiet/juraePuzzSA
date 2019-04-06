package de.htwg.se.juraePuzz.model.GridMockImpl

import de.htwg.se.juraePuzz.model.GridInterface
import de.htwg.se.juraePuzz.model.gridBaseImpl.{Level, Matrix, Piece}

class Grid extends GridInterface{
  override def empty(): Unit = {}

  //override def getSize(): Int = 4

  //override def fill(p: Piece, row: Int, col: Int): Unit = {}

  //override def fill(l: Level): Boolean = true

  override def move(xS: Int, yS: Int, xT: Int, yT: Int): Boolean = true

  override def checkMove(xS: Int, yS: Int, xT: Int, yT: Int): Boolean = true

  override def getLevel(): Level = Level(Array(1,2,3,4))

  override def solve(): Unit = {}

  override def size: Int = 1

  override def set(row: Int, col: Int, value: Int): GridInterface = this

  override def cell(row: Int, col: Int): Piece = Piece(0)

  override def createNewGrid: GridInterface = this
}
