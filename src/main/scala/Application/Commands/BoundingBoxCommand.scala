package Application.Commands

import scalafx.scene.paint.Color

case class BoundingBoxCommand(x1: Int, y1: Int, x2: Int, y2: Int, drawColor: String){
  
  def print(): Unit = println(s"BoundingBoxCommand($x1, $y1, $x2, $y2)")

  def to_String(draw_color: Color): String = s"BoundingBoxCommand($x1, $y1, $x2, $y2, $draw_color)"

  def draw(color: Color): Unit = {
  }
}