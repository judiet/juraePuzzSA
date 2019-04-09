package de.htwg.se.juraePuzz.model

import de.htwg.se.juraePuzz.model.gridBaseImpl.{Grid, Level, Matrix, Piece}

import scala.swing.Publisher

trait GridInterface extends Publisher {

  def empty(): Unit

  def size: Int

  override def toString(): String

  def set(row: Int, col: Int, value: Int): GridInterface
  def setMove(row: Int, col: Int, value: Int,row1: Int, col1: Int, value1: Int): GridInterface

  def cell(row: Int, col: Int): Piece

  //def fill(p:Piece, row:Int, col:Int): Unit

  //def fill(l:Level): Boolean

  //def fill(grid: GridInterface) : GridInterface

  def move(xS: Int, yS: Int, xT: Int, yT: Int): GridInterface

  def checkMove(xS: Int, yS: Int, xT: Int, yT: Int): Boolean

  def getLevel(): Level

  def solve(): Unit


  def createNewGrid: GridInterface
}
