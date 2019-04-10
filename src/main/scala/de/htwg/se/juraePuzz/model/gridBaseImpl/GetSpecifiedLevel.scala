package de.htwg.se.juraePuzz.model.gridBaseImpl

import de.htwg.se.juraePuzz.model.GridInterface

class GetSpecifiedLevel extends LevelGenerateStrategyTemplate {

  def createNewGrid(size: Int): GridInterface = {
    var grid: GridInterface = new Grid(size)
    grid = fill(grid)
    grid
  }

  def fill(_grid: GridInterface): GridInterface = {
    var grid: GridInterface = new Grid(_grid.size)
    val level = createLevel(_grid)
    level match{
      case Some(l) => l
        for (i <- 0 until _grid.size; j <- 0 until _grid.size) {
          grid = grid.set(i, j, l.s(j + i * grid.size))
        }
    }
    grid
  }

  def createLevel(grid: GridInterface): Option[Level] = {
    val size = grid.size * grid.size
    val l = Array.ofDim[Int](size)
    for (i <- 0 until size - 1) {
      l(i) = i + 1
    }
    shuffle(l)
    Some(Level(l))
  }

  private def shuffle(array: Array[Int]): Unit = {
    for (i <- array.indices) {
      val r = i + (Math.random() * (array.length - i)).toInt
      val tmp = array(i)
      array(i) = array(r)
      array(r) = tmp
    }
    ensureSolvable(array)
  }

  private def ensureSolvable(array: Array[Int]): Unit = {
    var inv_count = 0
    val arr = Array.ofDim[Int](array.length - 1)
    var j = 0
    for (i <- array.indices) {
      if ( array(i) != 0 ) {
        arr(j) = array(i)
        j += 1
      }
    }
    for (i <- arr.indices; j <- i + 1 until arr.length) {
      if ( arr(i) > arr(j) ) {
        inv_count += 1
      }
    }
    if ( inv_count % 2 != 0 ) {
      shuffle(array)
    }
  }
}