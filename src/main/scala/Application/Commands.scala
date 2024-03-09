package Application

class LineCommand_123 {
  var x1: Int = _
  var y1: Int = _
  var x2: Int = _
  var y2: Int = _

  def init(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
    this.x1 = x1
    this.y1 = y1
    this.x2 = x2
    this.y2 = y2
  }

  def print(): Unit = println(s"LineCommand($x1, $y1, $x2, $y2)")
}

case class LineCommand(x1: Int, y1: Int, x2: Int, y2: Int) extends Command
case class RectangleCommand(x1: Int, y1: Int, x2: Int, y2: Int) extends Command
case class CircleCommand(x: Int, y: Int, radius: Int) extends Command
case class TextAtCommand(x: Int, y: Int, text: String) extends Command
case class BoundingBoxCommand(x1: Int, y1: Int, x2: Int, y2: Int) extends Command
case class DrawCommand(color: String, commands: List[Command]) extends Command
case class FillCommand(color: String, command: Command) extends Command
