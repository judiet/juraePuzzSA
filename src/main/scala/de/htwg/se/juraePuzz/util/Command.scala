package de.htwg.se.juraePuzz.util

import de.htwg.se.juraePuzz.model.GridInterface

trait Command {
  def doStep:Option[GridInterface]
  def undoStep:Unit
  def redoStep:Unit
}

