package de.htwg.se.juraePuzz.model.gridBaseImpl

import de.htwg.se.juraePuzz.model.GridInterface
import com.google.inject.Inject
import com.google.inject.name.Named

class Grid @Inject() (@Named("DefaultSize")size:Int) extends GridInterface {
  val matrix = Matrix(size)

  empty()

  def empty(): Unit = {
    for (i <- 0 until size; j <- 0 until size) {
      matrix.fill(Piece(0, Rotation(0)), i, j)
    }
  }

  override def getMatrix(): Matrix = matrix

  def getSize():Int = {
    matrix.size
  }

  override def toString(): String = {
    val sb = new StringBuilder()
    for (i <- 0 until matrix.size; j <- 0 until matrix.size){
      sb.append(matrix.get(i,j).s)
      if (j == matrix.size - 1) {
        sb.append("\n")
      }
    }
    sb.toString()
  }

  def fill(p:Piece, row:Int, col:Int): Unit = {
    matrix.fill(p, row, col)
  }

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

  def move(xS:Int, yS:Int, xT:Int, yT:Int): Boolean = {
    if (checkMove(xS, yS, xT, yT)) {
      val pS = matrix.get(xS, yS)
      val pT = matrix.get(xT, yT)
      matrix.fill(pS, xT, yT)
      matrix.fill(pT, xS, yS)
      true
    } else {
     false
    }
  }

  def checkMove(xS:Int, yS:Int, xT:Int, yT:Int): Boolean = {

    if (xS >= matrix.size ||
      xT >= matrix.size ||
      yS >= matrix.size ||
      yT >= matrix.size) {
      return false
    }

    if (xS < 0 ||
    yS < 0 ||
    xT < 0 ||
    yT <0) {
      return false
    }

    val pT = matrix.get(xT, yT)
    val pS = matrix.get(xS, yS)

    if (pS.s == 0){
      return false
    }

    if (xS == xT) {
      if (yS - yT == -1 || yS - yT == 1) {
        if (pT.s == 0){
          return true
        }
      }
    }

    if (yS == yT) {
      if (xS - xT == -1 || xS - xT == 1) {
        if (pT.s == 0){
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
      sb(j + i * size) = matrix.get(i, j).s
    }
    Level(sb)
  }
  def solve(): Unit ={
    fill(new Solver(this).solve())
  }
}
