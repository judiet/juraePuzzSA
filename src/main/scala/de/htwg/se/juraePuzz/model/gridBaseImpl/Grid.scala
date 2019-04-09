package de.htwg.se.juraePuzz.model.gridBaseImpl

import com.google.inject.Inject
import com.google.inject.name.Named
import de.htwg.se.juraePuzz.model.GridInterface

case class Grid(matrix: Matrix[Piece]) extends GridInterface {
  def this(size: Int) = this(new Matrix[Piece](size, Piece(0)))

  //val matrix = Matrix(size)

  val size: Int = matrix.size

  def createNewGrid: GridInterface = (new GetSpecifiedLevel).createNewGrid(size)

  def set(row: Int, col: Int, value: Int): Grid = {
    val s = matrix.replaceCell(row, col, Piece(value))
    copy(s)
  }

  def cell(row: Int, col: Int): Piece = matrix.cell(row, col)

  def empty(): Unit = {
    for (i <- 0 until size; j <- 0 until size) {
      matrix.fill(Piece(0))
      //copy(matrix.replaceCell(i, j, Piece(0)))
    }
  }

  def getSize(): Int = {
    matrix.size
  }

  override def toString(): String = {
    val sb = new StringBuilder()
    for (i <- 0 until matrix.size; j <- 0 until matrix.size) {
      sb.append(matrix.cell(i, j).value)
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
  override def setMove(row: Int, col: Int, value: Int, row1: Int, col1: Int, value1: Int): GridInterface = {
    val grid  = matrix.replaceCells(row, col, Piece(value),row1, col1, Piece(value1))
    copy(grid)
  }

  def move(xS: Int, yS: Int, xT: Int, yT: Int): GridInterface = {
    if (checkMove(xS, yS, xT, yT)) {
      val pS = matrix.cell(xS, yS)
      val pT = matrix.cell(xT, yT)
      //matrix.replaceCell(xT, yT, pS)
      //matrix.replaceCell(xS, yS, pT)
      //matrix.fill(pS, xT, yT)
      //matrix.fill(pT, xS, yS)
      val grid = setMove(xT,yT,pS.value,xS,yS,pT.value)
      grid
    } else {
      new Grid(3)
    }
  }
  def checkMove(xS: Int, yS: Int, xT: Int, yT: Int): Boolean = {
    if (xS >= matrix.size || xT >= matrix.size || yS >= matrix.size || yT >= matrix.size) {
      return false
    }

    if (xS < 0 || yS < 0 || xT < 0 || yT < 0) {
      return false
    }

    val pT = matrix.cell(xT, yT)
    val pS = matrix.cell(xS, yS)

    if (pS.value == 0) {
      return false
    }

    if (xS == xT) {
      if (yS - yT == -1 || yS - yT == 1) {
        if (pT.value == 0) {
          return true
        }
      }
    }

    if (yS == yT) {
      if (xS - xT == -1 || xS - xT == 1) {
        if (pT.value == 0) {
          return true
        }
      }
    }
    false
  }

  def getLevel(): Level = {
    val size = matrix.size
    val sb = Array.ofDim[Int](size * size)

    for (i <- 0 until size; j <- 0 until size) {
      sb(j + i * size) = matrix.cell(i, j).value
    }
    Level(sb)
  }

  def solve(): Unit = {
    //fill(new Solver(this).solve())
  }

}
