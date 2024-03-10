package Application.Commands

import scalafx.scene.paint.Color

class BoundingBoxCommand{
  var x1: Int = _
  var y1: Int = _
  var x2: Int = _
  var y2: Int = _

  def init(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
    this.x1 = x1
    this.y1 = y1
    this.x2 = x2
    this.y2 = y2
  }

  def print(): Unit = println(s"BoundingBoxCommand($x1, $y1, $x2, $y2)")

  def draw(color: Color): Unit = {
  }
}