package de.htwg.se.juraePuzz.model.model

import de.htwg.se.juraePuzz.model.gridBaseImpl.{Grid, Level, Piece, Rotation}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class GridSpec extends WordSpec with Matchers {
    "A grid" should {
      val g = new Grid(2)
      "have a size" in {
        g.getSize() should be(2)
      }
      "have Pieces" in {
        g.empty()
        g.matrix.get(0, 0) should be(Piece(0, Rotation(0)))
        g.matrix.get(0, 1) should be(Piece(0, Rotation(0)))
        g.matrix.get(1, 0) should be(Piece(0, Rotation(0)))
        g.matrix.get(1, 1) should be(Piece(0, Rotation(0)))
      }

      "have Curves" in {
        for (i <- 0 until g.getSize(); j <- 0 until g.getSize()){
          g.matrix.fill(Piece(1, Rotation(0)), i, j)
        }
        g.matrix.get(0, 0) should be(Piece(1, Rotation(0)))
        g.matrix.get(0, 1) should be(Piece(1, Rotation(0)))
        g.matrix.get(1, 0) should be(Piece(1, Rotation(0)))
        g.matrix.get(1, 1) should be(Piece(1, Rotation(0)))
      }
      "be edited" in {
        g.empty()
        g.fill(Piece(1, Rotation(0)), 1, 1)
        g.checkMove(1, 1, 1, 0) should be(true)
        g.checkMove(0, 0, 1, 1) should be(false)
        g.checkMove(1, 1, 0, 1) should be(true)
        g.checkMove(2, 1, 2, 0) should be(false)
        g.fill(Piece(0, Rotation(0)), 0, 0)
        g.checkMove(0, 0, 0, 1) should be(false)
        g.checkMove(1, 1, 0, 0) should be(false)
      }
      "move correct" in {
        g.empty()
        g.fill(Piece(1, Rotation(0)), 0, 0)
        g.move(0, 0, 0, 1)
        g.matrix.get(0,0) should be(Piece(0, Rotation(0)))
        g.matrix.get(0, 1) should be (Piece(1, Rotation(0)))
        g.move(0,0, 1,0) should be (false)
        g.move(0, 0, 1, -1) should be (false)
      }
      "generateGrid with Level" in {
        g.fill(Level(Array(1,2,3,4))) should be (true)
        g.matrix.get(0,0).s should be (1)
        g.matrix.get(0,1).s should be (2)
        g.matrix.get(1,0).s should be (3)
        g.matrix.get(1,1).s should be (4)
      }
      "not be filled with illegal Levelsize" in {
        g.fill(Level(Array(1,2,3,4,5))) should be(false)
      }
      "a string representation" in {
        val grid = new Grid(2)
        grid.toString() should be ("00\n00\n")
      }
    }
}
