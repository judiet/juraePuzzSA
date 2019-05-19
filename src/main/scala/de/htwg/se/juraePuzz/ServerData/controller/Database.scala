package de.htwg.se.juraePuzz.ServerData.controller

import akka.actor.ActorSystem
import akka.util.Timeout
import redis.RedisClient

import scala.concurrent.duration._
import scala.util.Properties


class Database {
  implicit val system = ActorSystem("redis-client")
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(5 seconds)

  val HOST = Properties.envOrElse("REDIS_HOST", "localhost")
  val PORT = Properties.envOrElse("REDIS_PORT", "6379")

  val client = RedisClient(HOST, PORT.toInt)


}
