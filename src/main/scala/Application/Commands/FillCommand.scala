package Application.Commands

case class FillCommand(color: String, commands: List[String]) {
  def print(): Unit = println(s"FillCommand($color, $commands)")

  def to_String(draw_color: String): String = s"FillCommand($commands, $draw_color)"
}
