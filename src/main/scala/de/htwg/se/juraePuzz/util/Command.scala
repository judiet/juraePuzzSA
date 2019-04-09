package de.htwg.se.juraePuzz.util

trait Command {
  def doStep:Unit
  def undoStep:Unit
  def redoStep:Unit
}

