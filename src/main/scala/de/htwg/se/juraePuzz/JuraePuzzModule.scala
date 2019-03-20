package de.htwg.se.juraePuzz

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.se.juraePuzz.controller.ControllerInterface
import de.htwg.se.juraePuzz.model.GridInterface
import de.htwg.se.juraePuzz.model.fileIoComponent.FileIOInterface
import net.codingwell.scalaguice.ScalaModule


class JuraePuzzModule extends AbstractModule with ScalaModule {

  val defaultSize:Int = 3

  def configure() = {

    bindConstant().annotatedWith(Names.named("DefaultSize")).to(defaultSize)
    bind[GridInterface].to[model.gridBaseImpl.Grid]
    bind[ControllerInterface].to[controller.controllerBaseImpl.Controller]

    bind[GridInterface].annotatedWithName("mittel").toInstance(new model.gridBaseImpl.Grid(3))

    bind[FileIOInterface].to[model.fileIoComponent.fileIoXmlImpl.FileIO]

  }

}
