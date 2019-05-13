package de.htwg.se.juraePuzz
import de.htwg.se.juraePuzz.ServerData.controller._


object Server {
  def main(args: Array[String]): Unit = {
    val controller = new ServerController()

    controller.server()
  }

}
