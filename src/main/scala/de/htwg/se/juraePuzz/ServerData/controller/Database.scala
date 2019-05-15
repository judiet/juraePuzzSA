package de.htwg.se.juraePuzz.ServerData.controller

import akka.actor.ActorSystem
import akka.util.Timeout
import redis.RedisClient

import scala.concurrent.duration._


class Database {
  implicit val system = ActorSystem("redis-client")
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(5 seconds)

  val client = RedisClient("localhost", 6379)


}
