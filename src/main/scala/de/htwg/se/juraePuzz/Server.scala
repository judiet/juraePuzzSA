package de.htwg.se.juraePuzz
import de.htwg.se.juraePuzz.ServerData.controller._
import de.htwg.se.juraePuzz.ServerData.controller.Database


object Server {
  def main(args: Array[String]): Unit = {
    val database = new Database()
    val controller = new ServerController(database)

    controller.server()
  }

}
