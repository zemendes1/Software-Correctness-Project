package Application.Commands

import scalafx.scene.paint.Color

class TextAtCommand {
  var x: Int = _
  var y: Int = _
  var text: String = _
  
  def init(x: Int, y: Int, text: String): Unit = {
    this.x = x
    this.y = y
    this.text = text
  }
  
  def print(): Unit = println(s"TextAtCommand($x, $y, $text)")
  
  def draw(color: Color): Unit = {
  }
}