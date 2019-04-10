package de.htwg.se.juraePuzz.model.fileIoComponent.fileIoJsonImpl

import com.google.inject.Guice
import com.google.inject.name.Names
import de.htwg.se.juraePuzz.JuraePuzzModule
import de.htwg.se.juraePuzz.model.GridInterface
import de.htwg.se.juraePuzz.model.fileIoComponent.FileIOInterface
import net.codingwell.scalaguice.InjectorExtensions._
import play.api.libs.json._

import scala.io.Source

class FileIO extends FileIOInterface {

  override def load: Option[GridInterface] = {
    var gridOption: Option[GridInterface] = None
    val source: String = Source.fromFile("grid.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val size = (json \ "grid" \ "size").get.toString.toInt
    val injector = Guice.createInjector(new JuraePuzzModule)
    size match {
      case 3 => gridOption = Some(injector.instance[GridInterface](Names.named("mittel")))
      case _ =>
    }
    gridOption match {
      case Some(grid) => {
        val _grid = grid
        for (index <- 0 until size * size) {
          val row = (json \\ "row") (index).as[Int]
          val col = (json \\ "col") (index).as[Int]
          val value = (json \\ "value") (index).as[Int]
          _grid.set(row, col, value)
        }
        gridOption = Some(_grid)
      }
      case None =>
    }
    gridOption
  }

  override def save(grid: GridInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("grid.json"))
    pw.write(Json.prettyPrint(gridToJson(grid)))
    pw.close
  }

  def gridToJson(grid: GridInterface) = {
    Json.obj(
      "grid" -> Json.obj(
        "size" -> JsNumber(grid.size)),
      "cells" -> Json.toJson(
        for {row <- 0 until grid.size;
             col <- 0 until grid.size} yield {
          Json.obj(
            "row" -> row,
            "col" -> col,
            "value" -> JsNumber(grid.cell(row, col).value))
        }
      )
    )
  }

}
