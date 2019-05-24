package de.htwg.se.juraePuzz.controller.controllerBaseImpl

import de.htwg.se.juraePuzz.model.GridInterface
import de.htwg.se.juraePuzz.model.gridBaseImpl.{Direction, Grid}
import de.htwg.se.juraePuzz.util.Command

class SetCommand(direction:Direction.Value, controller: Controller) extends Command {

  override def doStep: Option[GridInterface] = {
    controller.grid.mapMoveToDirection(direction) match {
      case Some(grid) => Some(grid)
      case None=>None
    }
  }

  override def undoStep: Unit = {
    direction match {
      case Direction.Up=>controller.move(Direction.Down)
      case Direction.Down=>controller.move(Direction.Up)
      case Direction.Left=>controller.move(Direction.Right)
      case Direction.Right=>controller.move(Direction.Left)
    }

  }

  override def redoStep: Unit = controller.move(direction)

}
