package de.htwg.se.juraePuzz.model.model

import de.htwg.se.juraePuzz.model.playerComponent.Player
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec with Matchers {
  "A Player" when { "new" should {
    val player = Player("Sebastian Rätzer")
    "have a name"  in {
      player.name should be("Sebastian Rätzer")
    }
    "have a nice String representation" in {
      player.toString should be("Sebastian Rätzer")
    }
  }
  }
}
