package de.htw.se.juraePuzz

import de.htw.se.juraePuzz.controller.{Database, ServerController, SlickDB}

object Server {
  def main(args: Array[String]): Unit = {
    val database = new Database()
   // val database1 = new SlickDB()
    val controller = new ServerController(database)

    controller.server()
  }

}
