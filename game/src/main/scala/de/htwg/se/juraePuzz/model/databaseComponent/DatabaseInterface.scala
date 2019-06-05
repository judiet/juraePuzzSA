package de.htwg.se.juraePuzz.model.databaseComponent

import akka.http.scaladsl.model.HttpResponse
import de.htwg.se.juraePuzz.model.GridInterface
import play.api.libs.json.JsValue

import scala.concurrent.Future

trait DatabaseInterface {
  def saveGrid(json: JsValue):Unit
  def loadGrid(): Future[HttpResponse]
  def loadFromJson(jsValue: JsValue): Option[GridInterface]
}
