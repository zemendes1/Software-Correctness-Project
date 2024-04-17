package Application.Commands

case class TextAtCommand(x: Int, y: Int, text: String) {
  def print(): Unit = println(s"TextAtCommand($x, $y, $text)")

  def to_String(draw_color: String): String = s"TextAtCommand($x, $y, $text, $draw_color)"
}