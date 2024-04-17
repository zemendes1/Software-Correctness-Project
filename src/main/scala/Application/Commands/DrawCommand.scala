package Application.Commands

case class DrawCommand(color: String, commands: List[String]) {
  def print(): Unit = println(s"DrawCommand($color, $commands)")

  def to_String(draw_color: String): String = s"DrawCommand($commands, $draw_color)"
}
