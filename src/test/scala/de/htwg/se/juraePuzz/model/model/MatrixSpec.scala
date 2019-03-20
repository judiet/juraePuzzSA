package de.htwg.se.juraePuzz.model.model

import de.htwg.se.juraePuzz.model.gridBaseImpl.{Matrix, Piece, Rotation}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class MatrixSpec extends WordSpec with Matchers {
  "A matrix" should {
    val m = Matrix(5)
    m.fill(Piece(0, Rotation(0)), 0, 0)
    "have a size" in {
      m.size should be (5)
    }
    "have a piece at 0,0" in {
      m.get(0,0) should be(Piece(0, Rotation(0)))
    }
  }
}
