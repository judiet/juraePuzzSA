package de.htwg.se.juraePuzz.aview.Gui
import de.htwg.se.juraePuzz.controller.{ControllerInterface, ShowCand}
import de.htwg.se.juraePuzz.controller.controllerBaseImpl.Controller

import scala.swing._
import scala.swing.event.{Event, KeyPressed, MouseClicked}


class CellChanged extends Event

class PiecePanel (row: Int, column: Int, controller: ControllerInterface) extends FlowPanel{

  val givenCellColor = new Color(200, 200, 255)
  val cellColor = new Color(224, 224, 255)
  val highlightedCellColor = new Color(192, 255, 192)
  var clicked = false

  def pieceText = {
    controller.gridMatrix.get(row, column).s
  }

  val label =
    new Label {
      text = pieceText.toString
      font = new Font("OLDENGL", 1, 36)
    }

  val piece = new BoxPanel(Orientation.Vertical) {
    contents +=  label
    preferredSize = new Dimension(51, 51)
    border = Swing.BeveledBorder(Swing.Raised)
    listenTo(mouse.clicks)
    reactions += {
      case e: MouseClicked => {
        if(row - 1 >= 0 && controller.gridMatrix.get(row - 1, column).s==0){
          controller.move(row, column, row - 1, column)
        }
        if (column - 1 >= 0 && controller.gridMatrix.get(row, column - 1).s ==0){
          controller.move(row, column, row, column - 1)
        }
        if (column + 1 < controller.gridMatrix.size && controller.gridMatrix.get(row, column + 1).s ==0){
          controller.move(row, column, row, column + 1)
        }
        if (row + 1 < controller.gridMatrix.size && controller.gridMatrix.get(row + 1, column).s ==0) {
          controller.move(row, column, row + 1, column)
        }
      }
    }
  }

  contents += piece

  def redraw: Unit = {
    contents.clear()
    label.text = pieceText.toString
    contents += piece
    repaint
  }

  def setBackground(p: Panel) = p.background = givenCellColor
}
