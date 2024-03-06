package Application


case class LineCommand(x1: Int, y1: Int, x2: Int, y2: Int) extends Command
case class RectangleCommand(x1: Int, y1: Int, x2: Int, y2: Int) extends Command
case class CircleCommand(x: Int, y: Int, radius: Int) extends Command
case class TextAtCommand(x: Int, y: Int, text: String) extends Command
case class BoundingBoxCommand(x1: Int, y1: Int, x2: Int, y2: Int) extends Command
case class DrawCommand(color: String, commands: List[Command]) extends Command
case class FillCommand(color: String, command: Command) extends Command
