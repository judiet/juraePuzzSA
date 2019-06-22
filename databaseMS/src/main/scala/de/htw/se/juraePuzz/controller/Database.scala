package de.htw.se.juraePuzz.controller

import akka.actor.ActorSystem
import akka.util.Timeout
import redis.RedisClient

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Properties, Success}


class Database extends DatabaseInterface {
  implicit val system = ActorSystem("redis-client")
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(5 seconds)

  val HOST = Properties.envOrElse("REDIS_HOST", "localhost")
  val PORT = Properties.envOrElse("REDIS_PORT", "6379")

  val client = RedisClient(HOST, PORT.toInt)

  override def load(): String = {
    val response = client.get("data")
    Await.result(response,Duration.Inf).get.decodeString("UTF-8")
  }

  override def save(gridJson: String): Unit = {
    client.set("data", gridJson)
  }
}
