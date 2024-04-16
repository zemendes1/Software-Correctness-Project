package Application.Commands
import Application.Main.coordinate_to_canvas

case class RectangleCommand(x1: Int, y1: Int, x2: Int, y2: Int, drawColor: String, fill: Boolean = false) {
  def print(): Unit = println(s"RectangleCommand($x1, $y1, $x2, $y2)")

  def to_String: String = s"RectangleCommand($x1, $y1, $x2, $y2, $drawColor, $fill)"

  def draw(): Array[(Int, Int)] = {
    // Your drawing logic using drawColor
    // starts adding leftSide to points
    if (fill) {
      val point_1 = coordinate_to_canvas.mapToCanvasSpace(x1, y1)
      val point_2 = coordinate_to_canvas.mapToCanvasSpace(x2, y2)
      var points: List [(Int, Int)] = List()
      for (x <- point_1._1 to point_2._1) {
        if (point_1._2 > point_2._2) {
          for (y <- point_1._2 to point_2._2 by -1) {
            points = points :+ (x, y)
          }
        } else {
          for (y <- point_1._2 to point_2._2) {
            points = points :+ (x, y)
          }
        }
      }
      points.toArray
    } else {
      val leftSide = LineCommand(x1, y1, x1, y2, drawColor).draw()
      val rightSide = LineCommand(x2, y1, x2, y2, drawColor).draw()
      val bottomSide = LineCommand(x1, y1, x2, y1, drawColor).draw()
      val topSide = LineCommand(x1, y2, x2, y2, drawColor).draw()
      // points to return should be an array of (x, y)
      leftSide ++ rightSide ++ bottomSide ++ topSide
    }
  }
}