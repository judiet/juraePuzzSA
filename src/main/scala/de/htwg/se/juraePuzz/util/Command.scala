package de.htwg.se.juraePuzz.util

trait Command {
  def doStep:Boolean
  def undoStep:Unit
  def redoStep:Unit
}

