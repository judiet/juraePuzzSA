package de.htwg.se.juraePuzz.model.gridBaseImpl

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import de.htwg.se.juraePuzz.controller.controllerBaseImpl.Controller
import de.htwg.se.juraePuzz.controller.controllerBaseImpl.myActor.{PingMessage, StartMessage}
import de.htwg.se.juraePuzz.model.GridInterface

import scala.collection.mutable
import scala.util.Sorting


class Solver(grid: GridInterface, aController: ActorRef) extends Actor {

  def solve(): Level = {
    val sb = Array.ofDim[Int](grid.size * grid.size)
    for (i <- 0 until grid.size; j <- 0 until grid.size) {
      sb(j + i * grid.size) = grid.cell(i, j).value
    }
    Sorting.quickSort(sb)
    for (i <- 0 until sb.length - 1) {
      sb(i) = sb(i + 1)
    }
    sb(sb.length - 1) = 0

    Level(sb)
  }

  def check_level(): Boolean = {

    val level = grid.getLevel()
    level match {
      case Some(l) =>
        l.s.corresponds(solve().s) {
          _ == _
        }
    }
  }

  def geolState(): GridInterface = {
    val grid = new Grid(new Matrix[Piece](Vector(
      Vector(Piece(1), Piece(2), Piece(3)),
      Vector(Piece(4), Piece(5), Piece(6)),
      Vector(Piece(7), Piece(8), Piece(0)))))
    grid
  }

  def dfsMutableIterative(start: GridInterface): GridInterface = {
    var current: GridInterface = start
    val found: mutable.Set[GridInterface] = mutable.Set[GridInterface]()
    val stack: mutable.Stack[GridInterface] = mutable.Stack[GridInterface]()
    stack.push(current)
    while (!stack.isEmpty) {
      current = stack.pop()
      if (!found.contains(current)) {
        found += current
        for (m <- current.posMoves()) {
          for (next <- current.mapMoveToDirection(m)) {
            stack.push(next)
            if (geolState() == next) {
              return next
            }
          }
        }
      }
    }
    throw new Exception("Not solved Sorry")
  }

  override def receive: Receive = {
    case StartMessage(value) =>
      println("Received Grid ---> Solving" )
      aController ! PingMessage(dfsMutableIterative(value))
    /*case PongMessage =>
      incrementAndPrint
      if (count > 99) {
        sender ! StopMessage
        println("ping stopped")
        context.stop(self)
      } else {
        sender ! PingMessage
      }*/
  }
}