package de.htwg.se.juraePuzz

import com.google.inject.Guice
import de.htwg.se.juraePuzz.aview.Tui
import de.htwg.se.juraePuzz.aview.Gui.SwingGui
import de.htwg.se.juraePuzz.controller.ControllerInterface
import de.htwg.se.juraePuzz.controller.controllerBaseImpl.Controller
import de.htwg.se.juraePuzz.model.gridBaseImpl.Grid
object juraePuzz {

  val injector = Guice.createInjector(new JuraePuzzModule)
  val controller = injector.getInstance(classOf[ControllerInterface])

  val tui = new Tui(controller)
  val gui = new SwingGui(controller)
  controller.toggleShow()

  def main(args: Array[String]): Unit = {
    var input: String = ""
    do {
      input = scala.io.StdIn.readLine()
      tui.process_input_line(input)
    } while (input != "q")
  }
}
