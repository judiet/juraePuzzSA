package de.htwg.se.juraePuzz.model.model

import de.htwg.se.juraePuzz.model.gridBaseImpl.{Grid, _}
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
        g.matrix.cell(0, 0) should be (Piece(0))
        g.matrix.cell(0, 1) should be(Piece(0))
        g.matrix.cell(1, 0) should be(Piece(0))
        g.matrix.cell(1, 1) should be(Piece(0))
      }

      "have Curves" in {

        val g = new Grid(new Matrix[Piece](Vector(Vector(Piece(1), Piece(1)), Vector(Piece(1), Piece(1)))))
        for (i <- 0 until g.getSize(); j <- 0 until g.getSize()){
          g.set(i, j,1)
        }
        g.matrix.cell(0, 0) should be(Piece(1))
        g.matrix.cell(0, 1) should be(Piece(1))
        g.matrix.cell(1, 0) should be(Piece(1))
        g.matrix.cell(1, 1) should be(Piece(1))
      }
      "be edited" in {
        val g = new Grid(new Matrix[Piece](Vector(Vector(Piece(0), Piece(0)), Vector(Piece(0), Piece(1)))))
        g.checkMove(1, 1, 1, 0) should be(true)
        g.checkMove(0, 0, 1, 1) should be(false)
        g.checkMove(1, 1, 0, 1) should be(true)
        g.checkMove(2, 1, 2, 0) should be(false)
        g.set( 0, 0,0)
        g.checkMove(0, 0, 0, 1) should be(false)
        g.checkMove(1, 1, 0, 0) should be(false)
      }
      "move correct" in {
        val g = new Grid(new Matrix[Piece](Vector(Vector(Piece(1), Piece(0)), Vector(Piece(0), Piece(0)))))
        g.move(0, 0, 0, 1) should be (new Grid(new Matrix[Piece](Vector(Vector(Piece(0), Piece(1)), Vector(Piece(0), Piece(0))))))
        g.matrix.cell(0,0) should be(Piece(1))
        g.matrix.cell(0, 1) should be (Piece(0))
        g.move(0,0, 1,0) should be (new Grid(new Matrix[Piece](Vector(Vector(Piece(0), Piece(0)), Vector(Piece(1), Piece(0))))))
        g.move(0, 0, 1, -1) should be (new Grid(0))

      }
      /*"generateGrid with Level" in {
        g.fill(Level(Array(1,2,3,4))) should be (true)
        g.matrix.get(0,0).s should be (1)
        g.matrix.get(0,1).s should be (2)
        g.matrix.get(1,0).s should be (3)
        g.matrix.get(1,1).s should be (4)
      }
      "not be filled with illegal Levelsize" in {
        g.fill(Level(Array(1,2,3,4,5))) should be(false)
      }
      */
      "a string representation" in {
        val grid = new Grid(2)
      grid.toString() should be ("00\n00\n")
      }
    }
}
