package Application.Commands

case class BoundingBoxCommand(x1: Int, y1: Int, x2: Int, y2: Int){
  
  def print(): Unit = println(s"BoundingBoxCommand($x1, $y1, $x2, $y2)")

  def to_String(): String = s"BoundingBoxCommand($x1, $y1, $x2, $y2)"
}