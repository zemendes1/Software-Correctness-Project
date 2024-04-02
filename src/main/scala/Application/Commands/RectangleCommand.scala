package Application.Commands
import scalafx.scene.paint.Color

case class RectangleCommand(x1: Int, y1: Int, x2: Int, y2: Int, drawColor: String) {

  // private val numPoints = 40000 // TODO: Should we consider changing it to be based on size
  // of the rectangle? also there is the issue that linecommand uses 10000 points
  def print(): Unit = println(s"RectangleCommand($x1, $y1, $x2, $y2)")

  def to_String: String = s"RectangleCommand($x1, $y1, $x2, $y2, $drawColor)"

  def draw(): Array[(Int, Int)] = {
    // Your drawing logic using drawColor
    // starts adding leftSide to points
    val leftSide = LineCommand(x1, y1, x1, y2, drawColor).draw()
    val rightSide = LineCommand(x2, y1, x2, y2, drawColor).draw()
    val bottomSide = LineCommand(x1, y1, x2, y1, drawColor).draw()
    val topSide = LineCommand(x1, y2, x2, y2, drawColor).draw()
    // points to return should be an array of (x, y)
    leftSide ++ rightSide ++ bottomSide ++ topSide
  }
}