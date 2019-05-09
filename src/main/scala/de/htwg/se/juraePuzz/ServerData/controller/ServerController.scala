package de.htwg.se.juraePuzz.ServerData.controller

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink

import scala.concurrent.Future


class ServerController {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher


  def server(): Unit = {
    val serverSource = Http().bind(interface = "localhost", port = 8080)

    val requestHandler: HttpRequest => HttpResponse = {

      case HttpRequest(GET, Uri.Path("/grid"),_,_,_) =>
        HttpResponse(entity = "blub")

      case HttpRequest(GET, Uri.Path("/left"),_,_,_) =>
        HttpResponse(entity = "blub")

      case HttpRequest(GET, Uri.Path("/right"),_,_,_) =>
        HttpResponse(entity = "blub")

      case HttpRequest(GET, Uri.Path("/up"),_,_,_) =>
        HttpResponse(entity = "blub")

      case HttpRequest(GET, Uri.Path("/down"),_,_,_) =>
        HttpResponse(entity = "blub")

      case r: HttpRequest =>
        r.discardEntityBytes() // important to drain incoming HTTP Entity stream
        HttpResponse(404, entity = "Unknown resource!")
    }

    val bindingFuture: Future[Http.ServerBinding] =
      serverSource.to(Sink.foreach { connection =>
        println("Accepted new connection from " + connection.remoteAddress)

        connection handleWithSyncHandler requestHandler
        // this is equivalent to
        // connection handleWith { Flow[HttpRequest] map requestHandler }
      }).run()

  }

}
