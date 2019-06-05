package de.htw.se.juraePuzz.model

import slick.jdbc.H2Profile.api._

class GridObject(tag: Tag) extends Table[(Int, String, String)](tag, "GridObject") {

  def id = column[Int]("SAVE_ID", O.PrimaryKey)

  def userName = column[String]("USER_NAME")

  def save = column[String]("SAVE_JSON")

  def * = (id, userName, save)

}