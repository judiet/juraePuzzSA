package de.htwg.se.juraePuzz.model.gridBaseImpl

import de.htwg.se.juraePuzz.model.GridInterface

import scala.util.{Random, Sorting}

class Solver(grid: GridInterface) {

  def solve(): Level = {
    val sb = Array.ofDim[Int](grid.size * grid.size)
    for (i <- 0 until grid.size; j <- 0 until grid.size) {
      sb(j + i * grid.size) = grid.cell(i, j).value
    }
    Sorting.quickSort(sb)
    for (i <- 0 until sb.length - 1) {
      sb(i) = sb(i + 1)
    }
    sb(sb.length - 1) = 0

    Level(sb)

  }

  def check_level(): Boolean = {

    val level = grid.getLevel()
    level match {
      case Some(l) =>
        l.s.corresponds(solve().s) {
          _ == _
        }
    }
  }

  def geolState(): GridInterface ={
    val grid = new Grid(new Matrix[Piece](Vector(
      Vector(Piece(0), Piece(0),Piece(0)),
      Vector(Piece(0), Piece(0),Piece(0)),
      Vector(Piece(0), Piece(0),Piece(0)))))
    grid
  }

  def getCurrentState(): GridInterface ={
    grid
  }

  def makeMove(): Direction.Value ={

    //while (getCurrentState() != geolState()){
      val move = Direction(Random.nextInt(Direction.maxId));
    return move
    //}
  }




}
