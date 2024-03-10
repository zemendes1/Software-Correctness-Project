package Application

import Application.Commands.{BoundingBoxCommand, CircleCommand, DrawCommand, FillCommand, LineCommand, RectangleCommand, TextAtCommand}
import scalafx.scene.paint.Color

trait Command


class CommandValidator {
  private val allowedColors: Set[String] = Set("black", "white", "red", "green", "yellow", "blue", "brown", "orange", "pink", "purple", "gray")

  private var draw_Color: Color = Color.Black
  def parseCommand(command: String, painting_color: Color): Boolean = {
    // Regular expressions to match the valid command formats
    val linePattern = """\(LINE \((\d+) (\d+)\) \((\d+) (\d+)\)\)""".r
    val rectanglePattern = """\(RECTANGLE \((\d+) (\d+)\) \((\d+) (\d+)\)\)""".r
    val circlePattern = """\(CIRCLE \((\d+) (\d+)\) (\d+)\)""".r
    val textPattern = """\(TEXT-AT \((\d+) (\d+)\) (.+)\)""".r
    val boundingBoxPattern = """\(BOUNDING-BOX \((\d+) (\d+)\) \((\d+) (\d+)\)\)""".r
    val drawPattern = """\(DRAW (\w+) (.+)\)""".r
    val fillPattern = """\(FILL (\w+) (.+)\)""".r

    command match {
      case linePattern(x1, y1, x2, y2) =>

        draw_Color = Option(painting_color).getOrElse(Color.Black)

        val lineCommandInstance = new LineCommand()
        lineCommandInstance.init(x1.toInt, y1.toInt, x2.toInt, y2.toInt)
        val stringRepresentation : Unit = lineCommandInstance.print()
        lineCommandInstance.draw(draw_Color)
        true

      case rectanglePattern(x1, y1, x2, y2) =>

        draw_Color = Option(painting_color).getOrElse(Color.Black)

        val rectangleCommandInstance = new RectangleCommand()
        rectangleCommandInstance.init(x1.toInt, y1.toInt, x2.toInt, y2.toInt)
        val stringRepresentation : Unit = rectangleCommandInstance.print()
        rectangleCommandInstance.draw(draw_Color)
        true

      case circlePattern(x, y, radius) =>

        draw_Color = Option(painting_color).getOrElse(Color.Black)

        val circleCommandInstance = new CircleCommand()
        circleCommandInstance.init(x.toInt, y.toInt, radius.toInt)
        val stringRepresentation: Unit = circleCommandInstance.print()
        circleCommandInstance.draw(draw_Color)
        true

      case textPattern(x, y, text) =>

        draw_Color = Option(painting_color).getOrElse(Color.Black)

        val textAtCommandInstance = new TextAtCommand()
        textAtCommandInstance.init(x.toInt, y.toInt, text)
        val stringRepresentation: Unit = textAtCommandInstance.print()
        textAtCommandInstance.draw(draw_Color)
        true

      case boundingBoxPattern(x1, y1, x2, y2) =>

        draw_Color = Option(painting_color).getOrElse(Color.Black)

        val boundingBoxCommandInstance = new BoundingBoxCommand()
        boundingBoxCommandInstance.init(x1.toInt, y1.toInt, x2.toInt, y2.toInt)
        val stringRepresentation: Unit = boundingBoxCommandInstance.print()
        boundingBoxCommandInstance.draw(draw_Color)
        true

      case drawPattern(color, commands) if allowedColors.contains(color.toLowerCase) =>
        val regex = """\([A-Z-]+\s\(\d+\s\d+\)(?:\s(?:\(\d+\s\d+\)|\w+))?\)""".r
        val commandList = regex.findAllIn(commands).toList

        // Check if the commands are valid
        commandList.foreach(parseCommand(_, colorConverter(color)))

        val DrawCommandInstance = new DrawCommand()
        DrawCommandInstance.init(color, commandList)
        val stringRepresentation: Unit = DrawCommandInstance.print()
        true

      case fillPattern(color, command) if allowedColors.contains(color.toLowerCase) =>

        val fillCommandInstance = new FillCommand()
        fillCommandInstance.init(color, command)
        val stringRepresentation: Unit = fillCommandInstance.print()
        parseCommand(command, colorConverter(color))
        true

      case _ =>
        false
    }
  }

  private def colorConverter(text:String): Color = {
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
}