package de.htw.se.juraePuzz.controller

import akka.actor.ActorSystem
import akka.util.Timeout
import redis.RedisClient

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Properties, Success}


class Database extends DatabaseInterface {
  implicit val system = ActorSystem("redis-client")
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(5 seconds)

  val HOST = Properties.envOrElse("REDIS_HOST", "localhost")
  val PORT = Properties.envOrElse("REDIS_PORT", "6379")

  val client = RedisClient(HOST, PORT.toInt)

  override def load(counter:Int): String = {
    var finalresponse = ""
    var done: Boolean = false
    val response = client.get("data")
    response.onComplete {
      case Success(x) => {
        finalresponse = x.get.decodeString("UTF-8")
        println("response_______:" + finalresponse)


      }
      case Failure(_) => {
        finalresponse = "error"


      }
    }
    Thread.sleep(500)
    finalresponse
  }

  override def save(gridJson: String,counter:Int): Unit = {
    client.set("data", gridJson)
  }
}
