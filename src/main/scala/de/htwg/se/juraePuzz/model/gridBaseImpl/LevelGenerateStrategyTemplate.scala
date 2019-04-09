package de.htwg.se.juraePuzz.model.gridBaseImpl

import de.htwg.se.juraePuzz.controller.controllerBaseImpl.Controller
import de.htwg.se.juraePuzz.model.GridInterface

trait LevelGenerateStrategyTemplate {
  def createLevel(grid: GridInterface): Level
}
