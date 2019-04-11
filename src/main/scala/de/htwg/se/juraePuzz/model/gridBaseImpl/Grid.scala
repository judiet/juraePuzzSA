package de.htwg.se.juraePuzz.model.gridBaseImpl

import com.google.inject.Inject
import com.google.inject.name.Named
import de.htwg.se.juraePuzz.model.GridInterface

case class Grid(matrix: Matrix[Piece]) extends GridInterface {
  def this(size: Int) = this(new Matrix[Piece](size, Piece(0)))

  val size: Int = matrix.size

  def createNewGrid: GridInterface = (new GetSpecifiedLevel).createNewGrid(size)

  def set(row: Int, col: Int, value: Int): Grid = {
    copy(matrix.replaceCell(row, col, Piece(value)))
  }

  def cell(row: Int, col: Int): Piece = matrix.cell(row, col)

  def empty(): Unit = {
    for (i <- 0 until size; j <- 0 until size) yield {
      matrix.fill(Piece(0))
    }
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
    copy(matrix.replaceCells(row, col, Piece(value), row1, col1, Piece(value1)))
  }

  def move(xS: Int, yS: Int, xT: Int, yT: Int): Option[GridInterface] = {
    if (checkMove(xS, yS, xT, yT)) {
      val pS = matrix.cell(xS, yS)
      val pT = matrix.cell(xT, yT)
      //matrix.replaceCell(xT, yT, pS)
      //matrix.replaceCell(xS, yS, pT)
      //matrix.fill(pS, xT, yT)
      //matrix.fill(pT, xS, yS)
      val grid = setMove(xT, yT, pS.value, xS, yS, pT.value)
      Some(grid)
    } else {
      None
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

  def getLevel(): Option[Level] = {
    val size = matrix.size
    val sb = Array.ofDim[Int](size * size)

    for (i <- 0 until size; j <- 0 until size) {
      sb(j + i * size) = matrix.cell(i, j).value
    }
    Some(Level(sb))
  }


  //Solver Methodes:

  def findNullValue(): Option[(Int, Int)] = {
    for (i <- 0 until size; j <- 0 until size) {
      val x = matrix.cell(i, j).value
      if (x == 0) {
        return Some(i, j)
      }
    }
    None
  }

  def mapMoveToDirection(direction: Direction.Value): Option[GridInterface] = {
    val value = findNullValue() match {
      case Some(value) => value
      case None => (0, 0)
    }
    val row = value._1
    val col = value._2
    direction match {
      case Direction.Up =>
        helper(row - 1, col, row, col) match {
          case Some(value) => return Some(value)
          case None =>
        }
      case Direction.Down =>
        helper(row + 1, col, row, col) match {
          case Some(value) => return Some(value)
          case None =>
        }
      case Direction.Left =>
        helper(row, col + 1, row, col) match {
          case Some(value) => return Some(value)
          case None =>
        }
      case Direction.Right =>
        helper(row, col - 1, row, col) match {
          case Some(value) => return Some(value)
          case None =>
        }
    }
    None
  }

  def helper(rowNull: Int, colNull: Int, row: Int, col: Int): Option[GridInterface] = {
    if (checkMove(rowNull, colNull, row, col)) {
      val pS = matrix.cell(rowNull, colNull)
      val pT = matrix.cell(row, col)
      val grid = setMove(rowNull, colNull, pT.value, row, col, pS.value)
      return Some(grid)
    }
    None
  }

}
