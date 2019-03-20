package de.htwg.se.juraePuzz.model.gridBaseImpl

case class Matrix(size:Int) {
  val matrix = Array.ofDim[Piece](size, size)
  def fill(p:Piece, row:Int, col:Int) = matrix(row)(col) = p
  def get(row:Int, col:Int) = matrix(row)(col)
}
