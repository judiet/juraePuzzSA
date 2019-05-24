package de.htwg.se.juraePuzz.model.model

import de.htwg.se.juraePuzz.model.gridBaseImpl.{Matrix, Piece, Direction}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class MatrixSpec extends WordSpec with Matchers {
  "A Matrix is a tailor-made immutable data type that contains a two-dimentional Vector of Cells. A Matrix" when {
    "empty " should {
      "be created by using a dimention and a sample cell" in {
        val matrix = new Matrix[Piece](2, Piece(0))
        matrix.size should be(2)
      }
      "for test purposes only be created with a Vector of Vectors" in {
        val testMatrix = Matrix(Vector(Vector(Piece(0))))
        testMatrix.size should be(1)
      }

    }
    "filled" should {
      val matrix = new Matrix[Piece](2, Piece(5))
      "give access to its cells" in {
        matrix.cell(0, 0) should be(Piece(5))
      }
      "replace cells and return a new data structure" in {
        val returnedMatrix = matrix.replaceCell(0, 0, Piece(4))
        matrix.cell(0, 0) should be(Piece(5))
        returnedMatrix.cell(0, 0) should be(Piece(4))
      }
      "be filled using fill operation" in {
        val returnedMatrix = matrix.fill(Piece(3))
        returnedMatrix.cell(0,0) should be(Piece(3))
      }
    }
  }
}