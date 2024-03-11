package Application.Commands

import scalafx.scene.paint.Color

case class LineCommand(x1: Int, y1: Int, x2: Int, y2: Int, drawColor: String) {

  private val numPoints = 1000
  def print(): Unit = println(s"LineCommand($x1, $y1, $x2, $y2)")

  def to_String(draw_color: Color): String = s"LineCommand($x1, $y1, $x2, $y2, $draw_color)"

  def draw(color : Color): Array[(Double, Double)] = {
    val points = new Array[(Double, Double)](numPoints)
    val dx : Double = x2 - x1
    val dy : Double = y2 - y1
    for (i <- 0 until numPoints) {
      val x : Double = x1 + dx * i / numPoints
      val y : Double = y1 + dy * i / numPoints
      points(i) = (x, y)
    }

    points
  }
}
