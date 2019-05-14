package de.htwg.se.juraePuzz.ServerData.controller

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.{HttpRequest, _}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import com.mongodb.async.client.{Observer, Subscription}
import org.mongodb.scala.bson.collection.mutable.Document
import org.mongodb.scala.model.Filters
import org.mongodb.scala.{Completed, MongoClient, MongoCollection, MongoDatabase, SingleObservable}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}


class ServerController {


  implicit val system = ActorSystem("System")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  val client: MongoClient = MongoClient("mongodb://localhost:27017")
  val database: MongoDatabase = client.getDatabase("JuraeDB")
  val collection: MongoCollection[Document] = database.getCollection("JuraeColl")
  var id: Int = _
  val r = scala.util.Random




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
        val tmp = collection.find(Filters.equal("_id", id)).first()
        val response: Document = Await.result(tmp.toFuture(), Duration(5, TimeUnit.SECONDS))
        println(response)
        //val response = collection.find().subscribe((doc: Document) => println(doc.toJson()))
        HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, response.toJson()))
      }
      case HttpRequest(GET, Uri.Path("/save"), _, _, _) => {
        val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
          method = HttpMethods.GET,
          uri = "http://localhost:9090/grid",
          //entity = HttpEntity(ContentTypes.`application/json`, json.toString)
        ))
        responseFuture.onComplete {
          case Success(value) =>
            val test: Future[String] = value.entity.toStrict(5 seconds).map(_.data.decodeString("UTF-8"))
            test.onComplete {
              case Success(x) => {
                id = r.nextInt(999999999)

                val doc: Document = Document("_id" -> id, "info" -> Document(x))
                println(doc)

                val insertObservable: SingleObservable[Completed] = collection.insertOne(doc)

                insertObservable.subscribe(new Observer[Completed] {
                  override def onNext(result: Completed): Unit = println(s"onNext: $result")

                  override def onError(e: Throwable): Unit = println(s"onError: $e")

                  override def onComplete(): Unit = println("onComplete")

                  override def onSubscribe(subscription: Subscription): Unit = println("was soll das teil hier? Steht nicht in der doku")
                })
              }
              case Failure(_) => sys.error("Document creation failed")
            }

          case Failure(_) => sys.error("Request failed")
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
