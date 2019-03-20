package de.htwg.se.juraePuzz.model.gridBaseImpl

import de.htwg.se.juraePuzz.controller.controllerBaseImpl.Controller

class GetSpecifiedLevel extends LevelGenerateStrategyTemplate {
  override def createLevel(controller: Controller): Level = {
    val size = controller.grid.getSize() * controller.grid.getSize()
    var l = Array.ofDim[Int](size)
    for (i <- 0 until (size) - 1) {
      l(i) = i + 1
    }
    shuffle(l)
    Level(l)
  }

  private def shuffle(array: Array[Int]) = {
    for (i <- 0 until array.length) {
      val r = i + (Math.random() * (array.length - i)).toInt
      val tmp = array(i)
      array(i) = array(r)
      array(r) = tmp
    }
    ensureSolvable(array)
  }

  private def ensureSolvable(array: Array[Int]): Unit = {
    var inv_count = 0
    var arr = Array.ofDim[Int](array.length - 1)
    var j = 0
    for (i <- 0 until array.length) {
      if (array(i) != 0) {
        arr(j) = array(i)
        j += 1
      }
    }
    for (i <- 0 until arr.length; j <- i + 1 until (arr.length)) {
      if (arr(i) > arr(j)) {
        inv_count += 1
      }
    }
    if (inv_count % 2 != 0) {
      shuffle(array)
    }
  }
}