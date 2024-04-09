package Application.Commands

import scalafx.scene.paint.Color

case class DrawCommand(color: String, commands: List[String]) {
  def print(): Unit = println(s"DrawCommand($color, $commands)")

  def to_String(draw_color: String): String = s"DrawCommand($commands, $draw_color)"

  def draw(color: Color): Unit = {
  }
}
