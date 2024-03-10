package Application.Commands

case class RectangleCommand(x1: Int, y1: Int, x2: Int, y2: Int, drawColor: String) {
  def print(): Unit = println(s"RectangleCommand($x1, $y1, $x2, $y2)")

  def to_String: String = s"RectangleCommand($x1, $y1, $x2, $y2, $drawColor)"

  def draw(): Unit = {
    // Your drawing logic using drawColor
  }
}