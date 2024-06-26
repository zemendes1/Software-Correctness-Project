package Application

import Application.Commands.*

trait Command


class CommandValidator {
  private val allowedColors: Set[String] = Set("black", "white", "red", "green", "yellow", "blue", "brown", "orange", "pink", "purple", "gray")

  private var draw_Color: String = "Black"
  def parseCommand(command: String, painting_color: String, fill: Boolean = false): String = {
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

        draw_Color = Option(painting_color).getOrElse("Black")

        val lineCommandInstance = LineCommand(x1.toInt, y1.toInt, x2.toInt, y2.toInt, draw_Color)
        val stringRepresentation : String = lineCommandInstance.to_String(draw_Color)
        stringRepresentation

      case rectanglePattern(x1, y1, x2, y2) =>

        draw_Color = Option(painting_color).getOrElse("Black")

        val rectangleCommandInstance =  RectangleCommand(x1.toInt, y1.toInt, x2.toInt, y2.toInt, draw_Color, fill)
        val stringRepresentation : String = rectangleCommandInstance.to_String
        stringRepresentation

      case circlePattern(x, y, radius) =>

        draw_Color = Option(painting_color).getOrElse("Black")

        val circleCommandInstance =  CircleCommand(x.toInt, y.toInt, radius.toInt, draw_Color, fill)
        val stringRepresentation: String = circleCommandInstance.to_String(draw_Color)
        stringRepresentation

      case textPattern(x, y, text) =>

        draw_Color = Option(painting_color).getOrElse("Black")

        val textAtCommandInstance = TextAtCommand(x.toInt, y.toInt, text)
        val stringRepresentation: String = textAtCommandInstance.to_String(draw_Color)
        stringRepresentation

      case boundingBoxPattern(x1, y1, x2, y2) =>

        val boundingBoxCommandInstance = BoundingBoxCommand(x1.toInt, y1.toInt, x2.toInt, y2.toInt)
        val stringRepresentation: String = boundingBoxCommandInstance.to_String()
        stringRepresentation

      case drawPattern(color, commands) if allowedColors.contains(color.toLowerCase) =>
        val regex = """\([A-Z-]+\s\(\d+\s\d+\)(?:\s(?:\(\d+\s\d+\)|\w+))?\)""".r
        val commandList = regex.findAllIn(commands).toList

        // Check if the commands are valid
        commandList.foreach(parseCommand(_, color, fill))

        var parsedCommandList: List[String] = for {
          command <- commandList
          parsedCommand = parseCommand(command, color, fill)
        } yield parsedCommand

        val DrawCommandInstance = DrawCommand(color, commandList)
        val stringRepresentation: String = DrawCommandInstance.to_String(draw_Color)
        stringRepresentation

      case fillPattern(color, commands) if allowedColors.contains(color.toLowerCase) =>
        val regex = """\([A-Z-]+\s\(\d+\s\d+\)(?:\s(?:\(\d+\s\d+\)|\w+))?\)""".r
        val commandList = regex.findAllIn(commands).toList

        // Check if the commands are valid
        commandList.foreach(parseCommand(_, color, fill))

        var parsedCommandList: List[String] = for {
          command <- commandList
          parsedCommand = parseCommand(command, color, true)
        } yield parsedCommand

        val FillCommandInstance = new FillCommand(color, commandList)
        val stringRepresentation: String = FillCommandInstance.to_String(draw_Color)
        stringRepresentation

      case _ =>
        ""
    }
  }
}