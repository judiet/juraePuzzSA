package de.htwg.se.juraePuzz.model.databaseComponent

import com.google.inject.Guice
import com.google.inject.name.Names
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import de.htwg.se.juraePuzz.JuraePuzzModule
import de.htwg.se.juraePuzz.model.GridInterface
import play.api.libs.json.JsValue
import net.codingwell.scalaguice.InjectorExtensions._
import scala.concurrent.Future
import scala.util.{Failure, Success}

class DatabaseConnection extends DatabaseInterface {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher


  def saveGrid(json: JsValue): Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
      method = HttpMethods.GET,
      uri = "http://localhost:8888/save",
      entity = HttpEntity(ContentTypes.`application/json`, json.toString())
    ))

    responseFuture
      .onComplete {
        case Success(res) => println(res)
        case Failure(_) => sys.error("something wrong")
      }
  }

  override def loadGrid(): Future[HttpResponse] = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
      method = HttpMethods.GET,
      uri = "http://localhost:8888/load"))

    responseFuture
  }

  def loadFromJson(jsValue: JsValue): Option[GridInterface] = {
    var gridOption: Option[GridInterface] = None
    val size = (jsValue \ "grid" \ "size").get.toString.toInt
    val injector = Guice.createInjector(new JuraePuzzModule)
    size match {
      case 3 => gridOption = Some(injector.instance[GridInterface](Names.named("mittel")))
      case _ =>
    }
    gridOption match {
      case Some(grid) => {
        val _grid = grid
        for (index <- 0 until size * size) {
          val row = (jsValue \\ "row") (index).as[Int]
          val col = (jsValue \\ "col") (index).as[Int]
          val value = (jsValue \\ "value") (index).as[Int]
          _grid.set(row, col, value)
        }
        gridOption = Some(_grid)
      }
      case None =>
    }
    gridOption
  }

}
