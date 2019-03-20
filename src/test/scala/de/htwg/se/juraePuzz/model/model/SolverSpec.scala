package de.htwg.se.juraePuzz.model.model

import de.htwg.se.juraePuzz.model.gridBaseImpl.{Grid, Solver}
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SolverSpec extends WordSpec with Matchers {

  "A Solver" should {
    val grid = new Grid(2)
    val solver = new Solver(grid)
    "generate out of a matrix a Level" in {
      solver.solve().s should be (Array(0,0,0,0))
    }
    "checks that the current matirx ist solved or not" in {
      solver.check_level() should be (true)
    }
  }
}

