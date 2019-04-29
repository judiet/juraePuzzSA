package de.htwg.se.juraePuzz.aview

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import de.htwg.se.juraePuzz.controller.ControllerInterface
import de.htwg.se.juraePuzz.model.gridBaseImpl.Direction
import play.api.libs.json.JsValue
import spray.json.JsString

import scala.io.StdIn

class RestApi(controller: ControllerInterface) {

    def startRestApi {

      implicit val system = ActorSystem("my-system")
      implicit val materializer = ActorMaterializer()
      // needed for the future flatMap/onComplete in the end
      implicit val executionContext = system.dispatcher

      val route =
        path("grid") {
          get {
            val json: Object = controller.getJsonGrid
            complete(HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, json.toString)))
          }
        }~ path("left") {
            post {
              controller.move(Direction.Left)
              complete(HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, controller.getJsonGrid.toString)))
            }
          }~ path("right") {
          get {
            controller.move(Direction.Right)
            complete(HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, controller.getJsonGrid.toString)))
          }
        }~ path("up") {
          get {
            controller.move(Direction.Up)
            complete(HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, controller.getJsonGrid.toString)))
          }
        }~ path("down") {
          get {
            controller.move(Direction.Down)
            complete(HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, controller.getJsonGrid.toString)))
          }
        }

      val bindingFuture = Http().bindAndHandle(route, "localhost", 8888)

      println(s"Server online at http://localhost:8888/o\nPress RETURN to stop...")
      StdIn.readLine() // let it run until user presses return
      bindingFuture
        .flatMap(_.unbind()) // trigger unbinding from the port
        .onComplete(_ => system.terminate()) // and shutdown when done
    }
}