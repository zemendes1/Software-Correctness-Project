package Application.Commands
import Application.Main.coordinate_to_canvas

case class CircleCommand(x: Int, y: Int, radius: Int, drawColor: String) {

  def print(): Unit = println(s"CircleCommand($x, $y, $radius)")

  def to_String(draw_color: String): String = s"CircleCommand($x, $y, $radius, $draw_color)"


  // Returns the points to draw with the pixel coordinates
  def draw(): Array[(Int, Int)] = {

    val point = coordinate_to_canvas.mapToCanvasSpace_radius(x, y, radius)
    val converted_x = point._1
    val converted_y = point._2
    val converted_radius = point._3

    var points: List[(Int, Int)] = List()
    var p = (converted_radius, 0)

    // Calculate first point
    var dx = 1
    var dy = 1
    var delta = 2 * (converted_radius - 1)

    // Use the midpoint circle algorithm to find subsequent points
    while (p._1 >= p._2) {
      points = points ++ List((converted_x + p._1, converted_y - p._2), (converted_x - p._1, converted_y - p._2),
        (converted_x + p._1, converted_y + p._2), (converted_x - p._1, converted_y + p._2),
        (converted_x + p._2, converted_y - p._1), (converted_x - p._2, converted_y - p._1),
        (converted_x + p._2, converted_y + p._1), (converted_x - p._2, converted_y + p._1))

      // Update next point using midpoint formula
      if (delta < 0) {
        p = (p._1, p._2 + 1)
        delta += 2 * p._2 + 1
      } else {
        p = (p._1 - 1, p._2 + 1)
        delta += 2 * (p._2 - p._1 + 1)
      }
    }

    points.toArray
  }
}