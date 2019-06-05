case class Rotation(r:Int)
case class Piece(s:String,r:Rotation)

case class Matrix(size:Int) {
  val matrix = Array.ofDim[Piece](size, size)
  def fill(p:Piece, row:Int, col:Int) = matrix(row)(col) = p
  def get(row:Int, col:Int) = matrix(row)(col)
  def getSize():Int = size
}

case class Grid(size:Int){
  val matrix = Matrix(size)
  def print() = {
    for (i <- 0 until size; j <- 0 until size) {
      println(matrix.get(i,j))
    }
  }

  def init(): Unit = {
    for (i <- 0 until size; j <- 0 until size){
      matrix.fill(Piece("0", Rotation(0)), i, j)
    }
  }

  def render(): Unit = {
    for (i <- 0 until matrix.getSize(); j <- 0 until matrix.getSize()){
      printf("%s", matrix.get(i,j).s)
      if (j == matrix.getSize() - 1) {
        println("")
      }
    }
  }

  def fill(p:Piece, row:Int, col:Int): Unit = {
    matrix.fill(p, row, col)
  }

  def move(xS:Int, yS:Int, xT:Int, yT:Int): Unit = {
    if (checkMove(xS, yS, xT, yT)) {
      val pS = matrix.get(xS, yS)
      val pT = matrix.get(xT, yT)
      matrix.fill(pS, xT, yT)
      matrix.fill(pT, xS, yS)
    } else {
      println("ungültig")
    }
  }

  def checkMove(xS:Int, yS:Int, xT:Int, yT:Int): Boolean = {
    if (xS >= matrix.size ||
      xT >= matrix.size ||
      yS >= matrix.size ||
      yT >= matrix.size) {
      return false
    }

    val pT = matrix.get(xT, yT)

    if (xS == xT) {
      if (yS - yT == -1 || yS - yT == 1) {
        if (pT.s == "0"){
          return true
        }

      }
    }

    if (yS == yT) {
      if (xS - xT == -1 || xS - xT == 1) {
        if (pT.s == "0"){
          return true
        }
      }
    }
    false
  }

  def fill(l:Level): Unit = {
    for (i <- 0 until matrix.getSize(); j <- 0 until matrix.getSize()) {
      matrix.fill(Piece(l.s.charAt(j + i * matrix.getSize()).toString, Rotation(0)), i, j)
    }
  }
}

case class Level(s:String){
  def length(): Int = s.length
}
class Solver (g:Grid,l:Level){

  def solve(): Level = {
    val sb = new StringBuilder()
    for (i <- 0 until g.size; j <- 0 until g.size) {
      sb.append(g.matrix.get(i, j).s)
    }
    println(sb.toString())
    Level(sb.toString())
  }
  def check_level(): Boolean ={
    if(l.length() == solve().length()){
      println("l "+l.s)
      println("solve "+solve().s)
      l.s.equals(solve().s)
    }else{
      false
    }

  }
}
val level = Level("SK00E0000")

val g = Grid(3)
g.init()
g.fill(level)

g.render()

val solver = new Solver(g,level)
solver.check_level()




