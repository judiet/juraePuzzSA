package de.htwg.se.juraePuzz.model.gridBaseImpl

case class Matrix[T](val rows: Vector[Vector[T]]) {
  def this(size: Int, filling: T) = this(Vector.tabulate(size, size) { (row, col) => filling })

  val size: Int = rows.size

  //val matrix = Array.ofDim[Piece](size, size)

  def cell(row: Int, col: Int): T = rows(row)(col)

  //def fill(p:Piece, row:Int, col:Int) = matrix(row)(col) = p

  def fill(filling: T): Matrix[T] = copy(Vector.tabulate(size, size) { (row, col) => filling })

  def replaceCell(row: Int, col: Int, cell: T): Matrix[T] = {
    val s = rows.updated(row, rows(row).updated(col, cell))
    copy(s)
  }

  def replaceCells(row: Int, col: Int, cell: T,row1: Int, col1: Int, cell1: T): Matrix[T] = {
    var s = rows.updated(row, rows(row).updated(col, cell))
    s = s.updated(row1, s(row1).updated(col1, cell1))
    copy(s)
  }

  //def get(row:Int, col:Int) = matrix(row)(col)
}
