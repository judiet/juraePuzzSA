package de.htwg.se.juraePuzz.ServerData.controller

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.{HttpRequest, _}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import org.mongodb.scala.{Completed, MongoClient, MongoCollection, MongoDatabase}
import HttpMethods._

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.xml.Document


class ServerController {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  val client: MongoClient = MongoClient("mongodb://localhost:27017")
  val database: MongoDatabase = client.getDatabase("JuraeDB")
  val collection: MongoCollection[Document] = database.getCollection("JuraeColl")




  def server(): Unit = {
    val serverSource = Http().bind(interface = "localhost", port = 8889)
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://localhost:8888/grid"))

    responseFuture
      .onComplete {
        case Success(res) => println(res)
        case Failure(_)   => sys.error("something wrong")
      }

    val insertObservable: Observable[Completed] = collection.insertOne(res)




    val requestHandler: HttpRequest => HttpResponse = {

      case HttpRequest(GET, Uri.Path("/grid"), _, _, _) =>
        HttpResponse(entity = "grid")

      case HttpRequest(GET, Uri.Path("/left"), _, _, _) =>
        HttpResponse(entity = "left")

      case HttpRequest(GET, Uri.Path("/right"), _, _, _) =>
        HttpResponse(entity = "right")

      case HttpRequest(GET, Uri.Path("/up"), _, _, _) =>
        HttpResponse(entity = "up")

      case HttpRequest(GET, Uri.Path("/down"), _, _, _) =>
        HttpResponse(entity = "down")

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


  def handleDatabase(): Unit = {


  }

}
