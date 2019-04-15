package de.htwg.se.juraePuzz.model.gridBaseImpl

import akka.actor.Status.Success
import akka.pattern.FutureRef
import de.htwg.se.juraePuzz.model.GridInterface

import scala.collection.mutable
import scala.concurrent.Future
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

  def geolState(): GridInterface = {
    val grid = new Grid(new Matrix[Piece](Vector(
      Vector(Piece(1), Piece(2), Piece(3)),
      Vector(Piece(4), Piece(5), Piece(6)),
      Vector(Piece(7), Piece(8), Piece(0)))))
    grid
  }

  def getCurrentState(): GridInterface = {
    grid
  }

  def getPosMoves(): Unit = {
    val x = grid.posMoves()
  }

  def dfsMutableIterative(start: GridInterface): GridInterface = {
    var current: GridInterface = start
    val found: mutable.Set[GridInterface] = mutable.Set[GridInterface]()
    val stack: mutable.Stack[GridInterface] = mutable.Stack[GridInterface]()
    stack.push(current)

    while (!stack.isEmpty) {
      current = stack.pop()
      if (!found.contains(current)) {
        found += current
        for(m <- current.posMoves()){
          for (next <- current.mapMoveToDirection(m)) {
            stack.push(next)
            if(geolState() == next){
             return next
            }
          }
        }
      }
    }
    throw new Exception("Not solved Sorry")
  }
  /*
    def searchR(besucht:List[GridInterface],grid: GridInterface,goal: GridInterface): Unit = {


      //while (geolState() != gridSaved) {
        println(grid.posMoves())
        println(grid)
        for (m <- grid.posMoves()) {
          if (!besucht.contains(grid)) {
            besucht = grid :: besucht
            println(besucht)
            grid.mapMoveToDirection(m) match {
             case Some(value)=> grid = value
             case None=> None
           }
            println(grid)
          }
        }
      //}

    }*/

}