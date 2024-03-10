package Application.Commands

import scalafx.scene.paint.Color

case class TextAtCommand(x: Int, y: Int, text: String) {
  def print(): Unit = println(s"TextAtCommand($x, $y, $text)")

  def to_String(draw_color: Color): String = s"TextAtCommand($x, $y, $text, $draw_color)"
  
  def draw(color: Color): Unit = {
  }
}