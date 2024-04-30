package Application.Commands
import Application.Main.coordinate_to_canvas

case class LineCommand(x1: Int, y1: Int, x2: Int, y2: Int, drawColor: String) {

  def print(): Unit = println(s"LineCommand($x1, $y1, $x2, $y2)")

  def to_String(draw_color: String): String = s"LineCommand($x1, $y1, $x2, $y2, $draw_color)"

  def draw(): Array[(Int, Int)] = {
    val point_1 = coordinate_to_canvas.mapToCanvasSpace(x1, y1)
    val point_2 = coordinate_to_canvas.mapToCanvasSpace(x2, y2)

    var points = Array[(Int, Int)]()

    var x = point_1._1
    var y = point_1._2

    val dx = math.abs(point_2._1 - point_1._1)
    val dy = math.abs(point_2._2 - point_1._2)

    val sx = if (point_1._1 < point_2._1) 1 else -1
    val sy = if (point_1._2 < point_2._2) 1 else -1

    var err = dx - dy

    var x_upper_bound = 0
    var x_lower_bound = 0
    var y_upper_bound = 0
    var y_lower_bound = 0

    if(point_1._1 < point_2._1) {
      x_upper_bound = point_2._1
      x_lower_bound = point_1._1
    } else {
      x_upper_bound = point_1._1
      x_lower_bound = point_2._1
    }

    if(point_1._2 < point_2._2) {
      y_upper_bound = point_2._2
      y_lower_bound = point_1._2
    } else {
      y_upper_bound = point_1._2
      y_lower_bound = point_2._2
    }

    while (x <= x_upper_bound && x >= x_lower_bound && y <= y_upper_bound  && y >= y_lower_bound) {
      points = points :+ (x, y)

      val e2 = 2 * err

      if (e2 > -dy) {
        err -= dy
        x += sx
      }

      if (e2 < dx) {
        err += dx
        y += sy
      }
    }

    return points
  }

}
