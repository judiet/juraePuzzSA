package de.htwg.se.juraePuzz.controller

object GameStatus extends Enumeration{
  type GameStatus = Value
  val IDLE, SOLVED, NOT_SOLVED_YET, ILLEGAL_TURN, CREATE_LEVEL, NOT_CREATED_LEVEL, SAVED, LOADED, COULDNOTLOAD, ERROR= Value
  val map = Map[GameStatus, String] (
    IDLE -> "",
    SOLVED -> "Puzzle solved",
    NOT_SOLVED_YET -> "Next turn",
    ILLEGAL_TURN -> "Illegal turn",
    CREATE_LEVEL -> "generated Level",
    SAVED -> "Game saved",
    LOADED -> "Game loaded",
    COULDNOTLOAD -> "Could not load game",
    ERROR -> "An Error occurred"
    )

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }
}