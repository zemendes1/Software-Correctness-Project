package Application.Commands

import scalafx.scene.paint.Color

class CircleCommand {
  var x: Int = _
  var y: Int = _
  var radius: Int = _

  def init(x: Int, y: Int, radius: Int): Unit = {
    this.x = x
    this.y = y
    this.radius = radius
  }

  def print(): Unit = println(s"CircleCommand($x, $y, $radius)")

  def draw(color: Color): Unit = {
  }
}