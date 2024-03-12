package Application.Commands

import scalafx.scene.paint.Color

class FillCommand {
  var color: String = ""
  var commands: String = ""

  def init(color: String, commands: String): Unit = {
    this.color = color
    this.commands = commands
  }

  def print(): Unit = println(s"FillCommand($color, $commands)")

  def to_String(draw_color: String): String = s"FillCommand($color, $commands, $draw_color)"

  def draw(color: Color): Unit = {
  }
}
