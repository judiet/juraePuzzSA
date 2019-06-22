package de.htw.se.juraePuzz.controller


import de.htw.se.juraePuzz.model.GridObject
import slick.jdbc.H2Profile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

import slick.driver.H2Driver.api._

import scala.concurrent.ExecutionContext.Implicits.global

class SlickDB extends DatabaseInterface {

  val id = 1
  val db = Database.forConfig("h2mem1")
  val saves: TableQuery[GridObject] = TableQuery[GridObject]

  override def load(counter:Int): String = {
    var json=""
    println("-------------------------------- load -------------------------------")

      val query = for {
        r <- saves if r.id === 1
      } yield r.save

      val futureLoad = db.run(query.result)


      val rows = Await.result(futureLoad, Duration.Inf)
      rows.foreach { row =>
        json = row
      }
      println("---------------------------------------"+json)
      json
  }

  override def save(gridJson: String): Unit = {

      println("-------------------------------- save -------------------------------")
      val setup = DBIO.seq(
        saves.schema.drop,
        saves.schema.create,

        saves += (1, "master", gridJson),
        // Insert some suppliers
        /*saves += (1, "Acme, Inc.", "test"),
        saves += (2, "Superior Coffee", "test1"),
        saves += (3, "aas d", "test1"),*/
      )

      val futureSave = db.run(setup)
      val rows = Await.ready(futureSave, Duration.Inf)
  }
}
