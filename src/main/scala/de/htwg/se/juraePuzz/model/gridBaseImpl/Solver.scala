package de.htwg.se.juraePuzz.model.gridBaseImpl

import de.htwg.se.juraePuzz.model.GridInterface

class Solver(g: GridInterface) {

  def solve(): Level = {
    /*var sb = Array.ofDim[Int](g.getSize() * g.getSize())
    for (i <- 0 until g.getMatrix().size; j <- 0 until g.getMatrix().size) {
      sb(j + i * g.getSize()) = (g.getMatrix().get(i, j).s)
    }

    Sorting.quickSort(sb)
    for (i <- 0 until sb.length-1){
      sb(i)=sb(i+1)
    }
    sb(sb.length-1)=0
    */
    Level(List(1, 2, 3, 4, 5, 6, 7, 8, 0).toArray)
  }

  def check_level(): Boolean = {
    val level = g.getLevel()
    level match {
      case Some(l) => l
        l.s.corresponds(solve().s) {
          _ == _
        }
    }
  }
}
