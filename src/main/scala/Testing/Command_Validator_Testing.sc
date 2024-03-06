import Application.{CommandValidator, Command, LineCommand, RectangleCommand, CircleCommand, TextAtCommand, BoundingBoxCommand, DrawCommand, FillCommand}

// Example usage
val commandValidator = new CommandValidator()

// Test valid commands
val validLineCommand = "(LINE (1 2) (3 4))"
val parsedLineCommand = commandValidator.parseCommand(validLineCommand)
println(s"Is '$validLineCommand' valid? ${parsedLineCommand.isDefined}")
parsedLineCommand.foreach {
  case LineCommand(x1, y1, x2, y2) =>
    println(s"Line Command: x1=$x1, y1=$y1, x2=$x2, y2=$y2")
  case _ => // Handle other cases if needed
}

val validRectangleCommand = "(RECTANGLE (1 2) (3 4))"
val parsedRectangleCommand = commandValidator.parseCommand(validRectangleCommand)
println(s"Is '$validRectangleCommand' valid? ${parsedRectangleCommand.isDefined}")
parsedRectangleCommand.foreach {
  case RectangleCommand(x1, y1, x2, y2) =>
    println(s"Rectangle Command: x1=$x1, y1=$y1, x2=$x2, y2=$y2")
  case _ => // Handle other cases if needed
}

val validCircleCommand = "(CIRCLE (1 2) 3)"
val parsedCircleCommand = commandValidator.parseCommand(validCircleCommand)
println(s"Is '$validCircleCommand' valid? ${parsedCircleCommand.isDefined}")
parsedCircleCommand.foreach {
  case CircleCommand(x, y, radius) =>
    println(s"Circle Command: x=$x, y=$y, radius=$radius")
  case _ => // Handle other cases if needed
}

val validDrawCommand = "(DRAW red (LINE (1 2) (3 4)) (RECTANGLE (5 6) (7 8)))"
val parsed_DrawCommand = commandValidator.parseCommand(validDrawCommand)
println(s"Is '$validDrawCommand' valid? ${parsed_DrawCommand.isDefined}")
parsed_DrawCommand.foreach {
  case DrawCommand(color, commands) =>
    println(s"Draw Command: color=$color, commands=$commands")
  case _ => // Handle other cases if needed
}

// Test additional valid commands
val validTextAtCommand = "(TEXT-AT (1 2) Hello)"
val parsedTextAtCommand = commandValidator.parseCommand(validTextAtCommand)
println(s"Is '$validTextAtCommand' valid? ${parsedTextAtCommand.isDefined}")
parsedTextAtCommand.foreach {
  case TextAtCommand(x, y, text) =>
    println(s"Text At Command: x=$x, y=$y, text=$text")
  case _ => // Handle other cases if needed
}

val validBoundingBoxCommand = "(BOUNDING-BOX (1 2) (3 4))"
val parsedBoundingBoxCommand = commandValidator.parseCommand(validBoundingBoxCommand)
println(s"Is '$validBoundingBoxCommand' valid? ${parsedBoundingBoxCommand.isDefined}")
parsedBoundingBoxCommand.foreach {
  case BoundingBoxCommand(x1, y1, x2, y2) =>
    println(s"Bounding Box Command: x1=$x1, y1=$y1, x2=$x2, y2=$y2")
  case _ => // Handle other cases if needed
}

val validFillCommand = "(FILL red (RECTANGLE (1 2) (3 4)))"
val parsedFillCommand = commandValidator.parseCommand(validFillCommand)
println(s"Is '$validFillCommand' valid? ${parsedFillCommand.isDefined}")
parsedFillCommand.foreach {
  case FillCommand(color, command) =>
    println(s"Fill Command: color=$color, command=$command")
  case _ => // Handle other cases if needed
}

// Test invalid commands
val invalidCommand = "(INVALID (1 2) (3 4))"
val parsedInvalidCommand = commandValidator.parseCommand(invalidCommand)
println(s"Is '$invalidCommand' valid? ${parsedInvalidCommand.isDefined}")
parsedInvalidCommand.foreach {
  case _ => // Handle other cases if needed
}

val invalidDrawCommand = "(DRAW hello (LINE (1 2) (3 4)) (RECTANGLE (5 6) (7 8)))"
val parsed_Invalid_DrawCommand = commandValidator.parseCommand(invalidDrawCommand)
println(s"Is '$invalidDrawCommand' valid? ${parsed_Invalid_DrawCommand.isDefined}")
parsed_Invalid_DrawCommand.foreach {
  case DrawCommand(color, commands) =>
    println(s"Draw Command: color=$color, commands=$commands")
  case _ => // Handle other cases if needed
}