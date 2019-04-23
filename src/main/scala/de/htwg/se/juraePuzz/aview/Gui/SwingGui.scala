package de.htwg.se.juraePuzz.aview.Gui


import de.htwg.se.juraePuzz.controller.{ControllerInterface, ShowCand}
import de.htwg.se.juraePuzz.controller.controllerBaseImpl.Controller

import scala.swing._
import scala.swing.event.{Event, MouseClicked}

class SwingGui(controller: ControllerInterface) extends Frame {
  title = "juraePuzz"

  listenTo(controller)

  var cells = Array.ofDim[PiecePanel](controller.gridSize, controller.gridSize)

  def gridPanel = new GridPanel(controller.gridSize, controller.gridSize) {

    for {
      row <- 0 until controller.gridSize
      col <- 0 until controller.gridSize
    } {
      val piecePanel = new PiecePanel(row, col, controller)
      cells(row)(col) = piecePanel
      contents += piecePanel
      listenTo(piecePanel)
    }
  }

  def buttonPanel = new FlowPanel {
    contents += new Button("New") {
      listenTo(mouse.clicks)
      reactions += {
        case e: MouseClicked => {
          controller.createNewGrid
          redraw
        }
      }
    }

    contents += new Button("Quit") {
      listenTo(mouse.clicks)
      reactions += {
        case e: MouseClicked => System.exit(0)
      }
    }
    contents += new Button("Undo step") {
      listenTo(mouse.clicks)
      reactions += {
        case e: MouseClicked => controller.undo
      }
    }
    contents += new Button("Redo step") {
      listenTo(mouse.clicks)
      reactions += {
        case e: MouseClicked => controller.redo
      }
    }

    contents += new Button("Save") {
      listenTo(mouse.clicks)
      reactions += {
        case e: MouseClicked => controller.save
      }
    }

    contents += new Button("Load") {
      listenTo(mouse.clicks)
      reactions += {
        case e: MouseClicked => controller.load
      }
    }
    contents += new Button("Solve") {
      listenTo(mouse.clicks)
      reactions += {
        case e: MouseClicked => controller.solve()
      }
    }

    contents += new GridPanel(controller.gridSize, controller.gridSize)
  }

  val statusline = new TextField(controller.statusText, 20)

  contents = new BorderPanel {
    add(buttonPanel, BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
  }
  visible = true
  redraw

  reactions += {
    case event: CellChanged => redraw
    case event: ShowCand => redraw
  }

  def redraw = {
    for {
      row <- 0 until controller.gridSize
      col <- 0 until controller.gridSize
    } cells(row)(col).redraw
    resizable = false
    resizable = true
    statusline.text = controller.statusText
    repaint
  }
}


