package Application.Commands

import scala.math.{Pi, cos, sin}

class Circle_Example {
  val numPoints: Int = 1000

  def makeCircle(radius: Double, center: Array[Double]): Array[(Double, Double)] = {
    val points = new Array[(Double, Double)](numPoints)
    val thetaIncrement = 2 * Pi / numPoints

    for (i <- 0 until numPoints) {
      val theta = i * thetaIncrement
      val x = center(0) + radius * cos(theta)
      val y = center(1) + radius * sin(theta)
      points(i) = (x, y)
    }

    points
  }
}