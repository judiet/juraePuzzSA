package de.htwg.se.juraePuzz.model.gridBaseImpl

case class Level(s:Array[Int]){
  def length(): Int = s.length

  override def toString: String = {
    val str = new StringBuilder

    for (i <- s.indices){
      str.append(s(i))
      if ((i + 1) % Math.sqrt(s.length) == 0) {
        str.append("\n")
      }
    }
    str.toString()
  }
}
