package de.htwg.se.juraePuzz.model.gridBaseImpl

import com.google.inject.Inject
import de.htwg.se.juraePuzz.model.GridInterface

case class Grid (matrix: Matrix[Piece]) extends GridInterface {
  def this(size: Int) = this(new Matrix[Piece](size, Piece(0)))
  //val matrix = Matrix(size)

  val size: Int = matrix.size

  def createNewGrid: GridInterface = (new GetSpecifiedLevel).createNewGrid(size)

  def set(row: Int, col: Int, value: Int): Grid = copy(matrix.replaceCell(row, col, Piece(value)))
  empty()
  def cell(row: Int, col: Int): Piece= matrix.cell(row, col)

  def empty(): Unit = {
    for (i <- 0 until size; j <- 0 until size) {
      //matrix.fill(Piece(0), i, j)
      matrix.fill(Piece(0))
    }
  }

  def getSize():Int = {
    matrix.size
  }

  override def toString(): String = {
    val sb = new StringBuilder()
    for (i <- 0 until matrix.size; j <- 0 until matrix.size){
      sb.append(matrix.cell(i,j).value)
      if (j == matrix.size - 1) {
        sb.append("\n")
      }
    }
    sb.toString()
  }

  /*def fill(p:Piece, row:Int, col:Int): Unit = {
    matrix.fill(p, row, col)

  def fill(l:Level): Boolean = {
  if (l.length() == size * size) {
  for (i <- 0 until matrix.size; j <- 0 until matrix.size) {
  matrix.fill(Piece(l.s(j + i * matrix.size), Rotation(0)), i, j)
}
  true
} else {
  false
}
}
  }*/

  def move(xS:Int, yS:Int, xT:Int, yT:Int): Boolean = {
    if (checkMove(xS, yS, xT, yT)) {
      val pS = matrix.cell(xS, yS)
      val pT = matrix.cell(xT, yT)
      matrix.replaceCell(xT,yT,pS)
      matrix.replaceCell(xS,yS,pT)
      //matrix.fill(pS, xT, yT)
      //matrix.fill(pT, xS, yS)
      true
    } else {
     false
    }
  }

  def checkMove(xS:Int, yS:Int, xT:Int, yT:Int): Boolean = {

    if (xS >= matrix.size || xT >= matrix.size || yS >= matrix.size || yT >= matrix.size) {
      return false
    }

    if (xS < 0 || yS < 0 || xT < 0 || yT <0) {
      return false
    }

    val pT = matrix.cell(xT, yT)
    val pS = matrix.cell(xS, yS)

    if (pS.value == 0){
      return false
    }

    if (xS == xT) {
      if (yS - yT == -1 || yS - yT == 1) {
        if (pT.value == 0){
          return true
        }
      }
    }

    if (yS == yT) {
      if (xS - xT == -1 || xS - xT == 1) {
        if (pT.value == 0){
          return true
        }
      }
    }
   false
  }

  def getLevel(): Level = {
    val size = matrix.size
    var sb = Array.ofDim[Int](size * size)

    for (i <- 0 until size; j <- 0 until size) {
      sb(j + i * size) = matrix.cell(i, j).value
    }
    Level(sb)
  }
  def solve(): Unit ={
    //fill(new Solver(this).solve())
  }
}
