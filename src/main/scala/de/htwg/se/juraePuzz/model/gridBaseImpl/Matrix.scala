package de.htwg.se.juraePuzz.model.gridBaseImpl

case class Matrix[T](rows: Vector[Vector[T]]) {
  def this(size: Int, filling: T) = this(Vector.tabulate(size, size) { (row, col) => filling })

  val size: Int = rows.size

  val matrix = Array.ofDim[Piece](size, size)

  def cell(row: Int, col: Int): T = rows(row)(col)

  //def fill(p:Piece, row:Int, col:Int) = matrix(row)(col) = p

  def fill(filling: T): Matrix[T] = copy(Vector.tabulate(size, size) { (row, col) => filling })

  def replaceCell(row: Int, col: Int, cell: T): Matrix[T] = copy(rows.updated(row, rows(row).updated(col, cell)))

  //def get(row:Int, col:Int) = matrix(row)(col)
}
