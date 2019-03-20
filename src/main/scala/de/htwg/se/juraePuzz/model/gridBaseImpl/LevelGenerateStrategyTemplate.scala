package de.htwg.se.juraePuzz.model.gridBaseImpl

import de.htwg.se.juraePuzz.controller.controllerBaseImpl.Controller

trait LevelGenerateStrategyTemplate {
  def createLevel(controller: Controller): Level
}
