package de.htwg.se.juraePuzz.model.gridBaseImpl

trait SearchTree[M] {

  def moves(): List[M]

  def terminalState(): Boolean

  def move(m: M): SearchTree[M]

  def legalMove(m: M) = moves contains m

  type Path[A] = (List[A], SearchTree[A])

  type Insert[A] = (List[Path[A]], List[Path[A]]) => List[Path[A]]

  def generalSearch(insertNew: Insert[M]): List[M] = {
    var fringe = List[Path[M]](Pair(List[M](), this))

    var result: List[M] = null
    while (!fringe.isEmpty && result == null) {
      val Pair(ms, headState) :: fringeTail = fringe
      if (headState.terminalState()) result = ms.reverse
      else {
        val headsChildren
        = for (m <- headState.moves())
          yield Pair(m :: ms, headState.move(m))
        fringe = insertNew(fringeTail, headsChildren)
      }
    }
    result //is no longer null, or no solution is available
  }

  def breadthFirst(): List[M] = generalSearch((x, y) => x ::: y)
}
