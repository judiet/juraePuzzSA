package de.htwg.se.juraePuzz.model.model

import de.htwg.se.juraePuzz.model.gridBaseImpl.Rotation
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class RotationSpec extends WordSpec with Matchers {
  "A Rotation" should {
    "have a value" in {
      Rotation(0).r should be(0)
    }
  }
}
