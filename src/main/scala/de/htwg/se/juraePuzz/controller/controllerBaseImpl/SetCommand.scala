package de.htwg.se.juraePuzz.controller.controllerBaseImpl

import de.htwg.se.juraePuzz.util.Command

class SetCommand(xS:Int, yS:Int, xT:Int, yT:Int, controller: Controller) extends Command {

  override def doStep: Boolean = controller.grid.move(xS, yS, xT, yT)

  override def undoStep: Unit = controller.move(xT, yT, xS, yS)

  override def redoStep: Unit = controller.move(xS, yS, xT, yT)

}
