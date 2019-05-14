package de.htwg.se.juraePuzz.ServerData.controller

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.{HttpRequest, _}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}


class ServerController(database: Database) {


  implicit val system = ActorSystem("System")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  val json: String =
    """
{
  "grid": {
    "size" : 3,
    "cell" : [{
  	  "row" : 0,
  	  "col" : 0,
  	  "value" : 1
    },{
   	  "row" : 0,
   	  "col" : 1,
   	  "value" : 2
     },{
    	  "row" : 0,
    	  "col" : 2,
    	  "value" : 3
      },{
    	  "row" : 1,
    	  "col" : 0,
    	  "value" : 4
      },{
    	  "row" : 1,
    	  "col" : 1,
    	  "value" : 5
      },{
    	  "row" : 1,
    	  "col" : 2,
    	  "value" : 6
      },{
    	  "row" : 2,
    	  "col" : 0,
    	  "value" : 7
      },{
    	  "row" : 2,
    	  "col" : 1,
    	  "value" : 8
      },{
    	  "row" : 2,
    	  "col" : 2,
    	  "value" : 0
      }]
  }
}
"""


  def server(): Unit = {


    val serverSource = Http().bind(interface = "localhost", port = 8888)
    val requestHandler: HttpRequest => HttpResponse = {


      case HttpRequest(GET, Uri.Path("/grid"), _, _, _) =>
        HttpResponse(entity = "grid")

      case HttpRequest(GET, Uri.Path("/left"), _, _, _) =>
        HttpResponse(entity = "left")


      case HttpRequest(GET, Uri.Path("/load"), _, _, _) => {
        var finalresponse = ""
        var done: Boolean = false
        val response = database.client.get("data")
        response.onComplete {
          case Success(x) => {
            finalresponse = x.get.decodeString("UTF-8")
            done = true
          }
          case Failure(_) => {
            finalresponse = "error"
            done = true
          }
        }
        while (!done) {}
        HttpResponse(entity = finalresponse)
      }

      case HttpRequest(GET, Uri.Path("/save"), _, _, _) => {
        val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
          method = HttpMethods.GET,
          uri = "http://localhost:9090/grid",
        ))
        responseFuture.onComplete {
          case Success(value) => {
            val tmp: Future[String] = value.entity.toStrict(5 seconds).map(_.data.decodeString("UTF-8"))
            tmp.onComplete {
              case Success(x) => {
                database.client.set("data", x.toString)
              }

              case Failure(_) => sys.error("sndfkj,hynm")
            }


          }
          case Failure(_) => sys.error("fail")
        }

        HttpResponse(entity = "saved")

      }
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
        //print("Accepted new connection from " + connection.remoteAddress)

        connection handleWithSyncHandler requestHandler
        // this is equivalent to
        // connection handleWith { Flow[HttpRequest] map requestHandler }
      }).run()

  }

}
