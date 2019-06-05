package de.htw.se.juraePuzz.controller

trait DatabaseInterface {

  def load(counter:Int): String
  def save(gridJson: String,counter:Int): Unit

}
