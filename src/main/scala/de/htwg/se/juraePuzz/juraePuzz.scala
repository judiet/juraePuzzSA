package de.htwg.se.juraePuzz

import akka.actor.{ActorSystem, Props}
import com.google.inject.Guice
import de.htwg.se.juraePuzz.aview.{RestApi, Tui}
import de.htwg.se.juraePuzz.aview.Gui.SwingGui
import de.htwg.se.juraePuzz.controller.ControllerInterface
import de.htwg.se.juraePuzz.controller.controllerBaseImpl.Controller
import de.htwg.se.juraePuzz.model.gridBaseImpl.{Grid, Solver}

object juraePuzz {

  val injector = Guice.createInjector(new JuraePuzzModule)
  //val controller = injector.getInstance(classOf[ControllerInterface])
  val grid = new Grid(3)
  val controller = new Controller(grid)


  val tui = new Tui(controller)
  val gui = new SwingGui(controller)
  val rest = new RestApi(controller)
  controller.toggleShow()

  def main(args: Array[String]): Unit = {
    var input: String = ""
    rest.startRestApi
    do {
      input = scala.io.StdIn.readLine()
      tui.process_input_line(input)

    } while (input != "q")
  }
}
