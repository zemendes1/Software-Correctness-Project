package Application
import scalafx.scene.paint.Color

private def colorConverter(text: String): Color = {
  text match {
    case "black" => Color.Black
    case "white" => Color.White
    case "red" => Color.Red
    case "green" => Color.Green
    case "yellow" => Color.Yellow
    case "blue" => Color.Blue
    case "brown" => Color.Brown
    case "orange" => Color.Orange
    case "pink" => Color.Pink
    case "purple" => Color.Purple
    case "gray" => Color.Gray
    case _ => Color.Black
  }
}
