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

private def Color_Caps(text: String): String = {
  text match {
    case "black" => "Black"
    case "white" => "White"
    case "red" => "Red"
    case "green" => "Green"
    case "yellow" => "Yellow"
    case "blue" => "Blue"
    case "brown" => "Brown"
    case "orange" => "Orange"
    case "pink" => "Pink"
    case "purple" => "Purple"
    case "gray" => "Gray"
    case _ => "Black"
  }
}
