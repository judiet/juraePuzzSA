package de.htwg.se.juraePuzz.model.gridBaseImpl

import de.htwg.se.juraePuzz.model.GridInterface

import scala.util.Sorting

class Solver(g:GridInterface) {

  def solve(): Level = {
    var sb = Array.ofDim[Int](g.size * g.size)
    for (i <- 0 until g.size; j <- 0 until g.size) {
      sb(j + i * g.size) = (g.cell(i, j).value)
    }
    Sorting.quickSort(sb)
    for (i <- 0 until sb.length-1){
      sb(i)=sb(i+1)
    }
    sb(sb.length-1)=0

    Level(sb)
  }
  def check_level(): Boolean ={
    val l = g.getLevel()
    l.s.corresponds(solve().s){_ == _}
  }
}
