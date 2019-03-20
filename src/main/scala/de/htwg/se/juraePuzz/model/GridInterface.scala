package de.htwg.se.juraePuzz.model

import de.htwg.se.juraePuzz.model.gridBaseImpl.{Level, Matrix, Piece}

import scala.swing.Publisher

trait GridInterface extends Publisher {

  def empty(): Unit

  def getSize():Int

  override def toString(): String

  def fill(p:Piece, row:Int, col:Int): Unit

  def fill(l:Level): Boolean

  def move(xS:Int, yS:Int, xT:Int, yT:Int): Boolean

  def checkMove(xS:Int, yS:Int, xT:Int, yT:Int): Boolean

  def getLevel(): Level

  def solve(): Unit

  def getMatrix(): Matrix
}
