
package de.htw.se.juraePuzz.model

import slick.jdbc.H2Profile.api._

class DbObject(tag: Tag) extends Table[(Int, String, String)](tag, "DbObject") {

  def id = column[Int]("SAVE_ID", O.PrimaryKey)

  def userName = column[String]("USER_NAME")

  def saveJson = column[String]("SAVE_JSON")

  def * = (id, userName, saveJson)

}