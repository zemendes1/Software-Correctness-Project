package Application.Commands

import scalafx.scene.paint.Color

case class TextAtCommand(x: Int, y: Int, text: String, drawColor: String) {
  def print(): Unit = println(s"TextAtCommand($x, $y, $text)")

  def to_String(): String = s"TextAtCommand($x, $y, $text, $drawColor)"

  // TODO: draws text t at coordinates x, y
  def draw(color: Color): Unit = {
  }
}