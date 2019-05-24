package de.htw.se.juraePuzz.controller


import de.htw.se.juraePuzz.model.DbObject
import slick.jdbc.H2Profile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class SlickDB extends DatabaseInterface {

  val db = Database.forConfig("h2mem1")
  val saves: TableQuery[DbObject] = TableQuery[DbObject]
  val id = 1




  val setup = DBIO.seq(
    // Create the tables, including primary and foreign keys
    (saves.schema).create,

    // Insert some suppliers
    saves += (1, "Acme, Inc.","test"),
    saves += ( 1, "Superior Coffee", "test1"),
  )

  val setupFuture = db.run(setup)

  def load(): String = {
    var loadedJson = ""

    val q2 = for {
      c <- saves if 1 == 1
    } yield (c.saveJson)
    val action = q2.result
    val result: Future[Seq[String]] = db.run(action)
    val sql = action.statements.head

    result.onSuccess{
    case(s)=>println(s)}

    ""
  }

  def save(gridJson: String): Unit = {
    val insertValue: DBIO[Unit] = DBIO.seq(
      saves.schema.create,
      saves += (id, "master", gridJson)
    )
    println("insertValue: "+ insertValue)

    db.run(insertValue)
  }

}
