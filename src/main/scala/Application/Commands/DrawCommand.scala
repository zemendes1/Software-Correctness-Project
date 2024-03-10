package Application.Commands

import scalafx.scene.paint.Color

class DrawCommand {
  var color: String = ""
  var commands: List[String] = List()

  def init(color: String, commands: List[String]): Unit = {
    this.color = color
    this.commands = commands
  }

  def print(): Unit = println(s"DrawCommand($color, $commands)")

  def draw(color: Color): Unit = {
  }
}
