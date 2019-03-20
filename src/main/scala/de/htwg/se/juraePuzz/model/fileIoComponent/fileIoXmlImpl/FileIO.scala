package de.htwg.se.juraePuzz.model.fileIoComponent.fileIoXmlImpl

import com.google.inject.Guice
import com.google.inject.name.Names
import de.htwg.se.juraePuzz.JuraePuzzModule
import de.htwg.se.juraePuzz.model.GridInterface
import de.htwg.se.juraePuzz.model.fileIoComponent.FileIOInterface
import de.htwg.se.juraePuzz.model.gridBaseImpl.{Piece, Rotation}
import net.codingwell.scalaguice.InjectorExtensions._

import scala.xml.PrettyPrinter

class FileIO extends FileIOInterface {

  override def load: Option[GridInterface] = {
    var gridOption: Option[GridInterface] = None
    val file = scala.xml.XML.loadFile("grid.xml")
    val sizeAttr = (file \\ "grid" \ "@size")
    val size = sizeAttr.text.toInt
    val injector = Guice.createInjector(new JuraePuzzModule)
    size match {
      case 3 => gridOption = Some(injector.instance[GridInterface](Names.named("mittel")))
      case _ =>
    }
    val cellNodes= (file \\ "cell")
    gridOption match {
      case Some(grid)=> {
        var _grid = grid
        for (cell <- cellNodes) {
          val row: Int = (cell \ "@row").text.toInt
          val col: Int = (cell \ "@col").text.toInt
          val value: Int = cell.text.trim.toInt
          _grid.fill(Piece(value, Rotation(0)),row, col)
        }
        gridOption = Some(_grid)
      }
      case None =>
    }
    gridOption
  }

  def save(grid:GridInterface):Unit = saveString(grid)

  def saveXML(grid:GridInterface):Unit = {
    scala.xml.XML.save("grid.xml", gridToXml(grid))
  }

  def saveString(grid:GridInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("grid.xml" ))
    val prettyPrinter = new PrettyPrinter(120,4)
    val xml = prettyPrinter.format(gridToXml(grid))
    pw.write(xml)
    pw.close
  }
  def gridToXml(grid:GridInterface) = {
    <grid size ={grid.getSize().toString}>
      {
      for {
        row <- 0 until grid.getSize()
        col <- 0 until grid.getSize()
      } yield cellToXml(grid, row, col)
      }
    </grid>
  }

  def cellToXml(grid:GridInterface, row:Int, col:Int) ={
    <cell row ={row.toString} col={col.toString}>
      {grid.getMatrix.get(row,col).s}
    </cell>
  }

}
