package de.htwg.se.juraePuzz.model.fileIoComponent

import de.htwg.se.juraePuzz.model.GridInterface
import play.api.libs.json.JsValue

trait FileIOInterface {
  def load:Option[GridInterface]
  def save(grid:GridInterface):Unit
  def getJasonGrid(grid: GridInterface):JsValue
}
