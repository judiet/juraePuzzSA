package de.htwg.se.juraePuzz.model.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}
import de.htwg.se.juraePuzz.controller.GameStatus
import de.htwg.se.juraePuzz.controller.controllerBaseImpl.Controller
import de.htwg.se.juraePuzz.model.gridBaseImpl.{Grid, Level, Matrix, Solver}
import de.htwg.se.juraePuzz.util.{Observable, Observer}

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {
  "A Controller" should {
    var grid = new Grid(2)
    var controller = new Controller(grid)

    val observer = new Observer {
      var updated: Boolean = false

      def isUpdated: Boolean = updated

      override def update: Unit = updated = true
    }

    /*controller.add(observer)
    "notify its Observer after create grid" in {
      controller.create_empty_grid(2)
      observer.updated should be (true)
      controller.grid.getSize() should be (2)
    }
    "notify its Observer after created a Level" in {
      controller.create_empty_grid(3)
      controller.create_Level()
      observer.updated should be (true)
      controller.grid.toString() should be (controller.grid.getLevel().toString)
    }*/
    "should have status solved" in {
      controller.createEmptyGrid()
      controller.create_Level()
      val solver = new Solver(controller.grid)
      controller.solve()
      controller.gameStatus should be (GameStatus.SOLVED)
    }
    "should have status solved with move" in {
      controller.createEmptyGrid()
      controller.create_Level(Level(Array(1,2,0,3)))
      controller.move(1, 1, 1, 0)
      controller.gameStatus should be (GameStatus.SOLVED)
    }
    "should have status illegal turn" in {
      controller.createEmptyGrid()
      controller.create_Level()
      controller.move(2, 1, 1, 2)
      controller.gameStatus should be (GameStatus.ILLEGAL_TURN)
    }
    "should have status not solved yet" in {
      controller.createEmptyGrid()
      controller.create_Level(Level(Array(1,0,2,3)))
      controller.move(1, 1, 0, 1)
      controller.gameStatus should be (GameStatus.NOT_SOLVED_YET)
    }
    "gamestatus should have a message" in {
      val gs = GameStatus.IDLE
      GameStatus.message(gs) should be("")
    }
    "a status text" in {
      controller.gameStatus = GameStatus.SOLVED
      controller.statusText should be("Puzzle solved")
    }
    "undo" in {
      controller.createEmptyGrid()
      controller.create_Level(Level(Array(1,2,0,3)))
      controller.move(1, 1, 1, 0)
      controller.undo
      controller.gameStatus should be (GameStatus.NOT_SOLVED_YET)
    }
    "redo" in {
      controller.redo
      controller.gameStatus should be (GameStatus.SOLVED)
    }
    "save a game" in {
      controller.createEmptyGrid()
      controller.create_Level()
      controller.save
      controller.gameStatus should be (GameStatus.SAVED)
    }
    "not load a game"in{
      controller.load
      controller.gameStatus should be (GameStatus.COULDNOTLOAD)
    }
    "load a game" in {
      var grid = new Grid(3)
      var controller = new Controller(grid)
      controller.create_Level()
      controller.save
      controller.load
      controller.gameStatus should be (GameStatus.LOADED)
    }
    "have a grid size" in {
      controller.gridSize should be (2)
    }
    "have a matrix" in {
      controller.grid should be (Matrix(Vector(Vector(2))))
    }

  }
}