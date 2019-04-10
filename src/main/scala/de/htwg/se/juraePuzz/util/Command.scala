package de.htwg.se.juraePuzz.util

import de.htwg.se.juraePuzz.model.GridInterface

trait Command {
  def doStep:Unit
  def undoStep:Unit
  def redoStep:Unit
}

