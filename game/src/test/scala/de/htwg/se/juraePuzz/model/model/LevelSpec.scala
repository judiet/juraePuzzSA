package de.htwg.se.juraePuzz.model.model

import de.htwg.se.juraePuzz.model.gridBaseImpl.Level
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class LevelSpec extends WordSpec with Matchers {
  "A Level" should {
    "have a value" in {
      Level(Array(0)).s should be(Array(0))
      Level(Array(0)).length() should be (1)
    }
    "have string" in {
      val l = Level(Array(1,2,3,4,5,6,7,8,9))
      l.toString should be ("123\n456\n789\n")
    }
  }
}