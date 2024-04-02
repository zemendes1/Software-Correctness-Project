package Application.Commands

import scalafx.scene.paint.Color
import Application.{CommandValidator, CommandExtracter}
class DrawCommand(color: String, commands: Array[String]) {
  val drawColor: String = color
  def print(): Unit = println(s"DrawCommand($color, $commands)")

  // FIXME: What is the point of color and draw_color?
  def to_String(draw_color: String): String = s"DrawCommand($this.color, $this.commands, $this.draw_color)"

  def draw(): Array[(Int, Int)] = {
    for (command <- commands) {
      val draw_command = CommandValidator().parseCommand(command, color)
      val figureArray ++ CommandExtracter().Extract(draw_command)
    }
    return figureArray
  }
}
