package Application.Commands

import scalafx.scene.paint.Color
import scala.math.{Pi, cos, sin}

case class CircleCommand(x: Int, y: Int, radius: Int, drawColor: String) {

  private val numPoints = 10000

  def print(): Unit = println(s"CircleCommand($x, $y, $radius)")

  def to_String(draw_color: Color): String = s"CircleCommand($x, $y, $radius, $draw_color)"

  def draw(color: Color): Array[(Double, Double)] = {
    val points = new Array[(Double, Double)](numPoints)
    val thetaIncrement = 2 * Pi / numPoints

    for (i <- 0 until numPoints) {
      val theta : Double = i * thetaIncrement
      val vector_x : Double = x + radius * cos(theta)
      val vector_y : Double = y + radius * sin(theta)
      points(i) = (vector_x, vector_y)
    }

    points
  }
}