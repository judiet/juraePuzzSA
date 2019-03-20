package de.htwg.se.juraePuzz.model.fileIoComponent

import de.htwg.se.juraePuzz.model.GridInterface

trait FileIOInterface {
  def load:Option[GridInterface]
  def save(grid:GridInterface):Unit
}
