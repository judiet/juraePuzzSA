package de.htw.se.juraePuzz.controller

trait DatabaseInterface {

  def load(): String
  def save(gridJson: String): Unit

}
