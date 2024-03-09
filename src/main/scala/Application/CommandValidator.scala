package Application

trait Command

class CommandValidator {
  private val allowedColors: Set[String] = Set("black", "white", "red", "green", "yellow", "blue", "brown", "orange", "pink", "purple", "gray")

  def parseCommand(command: String): Option[Command] = {
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
        // Create a LineCommand instance
        val lineCommandInstance = new LineCommand_123()
        lineCommandInstance.init(x1.toInt, y1.toInt, x2.toInt, y2.toInt)

        // Use the toString method to get a string representation
        val stringRepresentation : Unit = lineCommandInstance.print()

        Some(LineCommand(x1.toInt, y1.toInt, x2.toInt, y2.toInt))
      case rectanglePattern(x1, y1, x2, y2) =>
        Some(RectangleCommand(x1.toInt, y1.toInt, x2.toInt, y2.toInt))
      case circlePattern(x, y, radius) =>
        Some(CircleCommand(x.toInt, y.toInt, radius.toInt))
      case textPattern(x, y, text) =>
        Some(TextAtCommand(x.toInt, y.toInt, text))
      case boundingBoxPattern(x1, y1, x2, y2) =>
        Some(BoundingBoxCommand(x1.toInt, y1.toInt, x2.toInt, y2.toInt))
      case drawPattern(color, commands) if allowedColors.contains(color.toLowerCase) =>
        // TODO: Fix this
        //println(s"commands == $commands")
        val parsedCommands1 = commands.split("\\s+")
        // println(parsedCommands1)
        // commands == (LINE (1 2) (3 4)) (RECTANGLE (5 6) (7 8))
        val parsedCommands = commands.split("\\s+").flatMap(parseCommand)
        // parsed Commands should be [LineCommand(1,2,3,4), RectangleCommand(5,6,7,8)]
        // parsed Commands is List=()
        Some(DrawCommand(color, parsedCommands.toList))
      case fillPattern(color, command) if allowedColors.contains(color.toLowerCase) =>
        parseCommand(command).map(FillCommand(color, _))
      case _ =>
        None
    }
  }
}
