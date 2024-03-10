import Application.CommandValidator

// Example usage
val commandValidator = new CommandValidator()

// Test valid commands
val validLineCommand = "(LINE (1 2) (3 4))"
val parsedLineCommand = commandValidator.parseCommand(validLineCommand, null)
println(s"Is '$validLineCommand' valid? $parsedLineCommand")


val validRectangleCommand = "(RECTANGLE (1 2) (3 4))"
val parsedRectangleCommand = commandValidator.parseCommand(validRectangleCommand, null)
println(s"Is '$validRectangleCommand' valid? $parsedRectangleCommand")


val validCircleCommand = "(CIRCLE (1 2) 3)"
val parsedCircleCommand = commandValidator.parseCommand(validCircleCommand, null)
println(s"Is '$validCircleCommand' valid? $parsedCircleCommand")


val validDrawCommand = "(DRAW red (LINE (1 2) (3 4)) (RECTANGLE (5 6) (7 8)))"
val parsed_DrawCommand = commandValidator.parseCommand(validDrawCommand, null)
println(s"Is '$validDrawCommand' valid? $parsed_DrawCommand")


// Test additional valid commands
val validTextAtCommand = "(TEXT-AT (1 2) Hello)"
val parsedTextAtCommand = commandValidator.parseCommand(validTextAtCommand, null)
println(s"Is '$validTextAtCommand' valid? $parsedTextAtCommand")


val validBoundingBoxCommand = "(BOUNDING-BOX (1 2) (3 4))"
val parsedBoundingBoxCommand = commandValidator.parseCommand(validBoundingBoxCommand, null)
println(s"Is '$validBoundingBoxCommand' valid? $parsedBoundingBoxCommand")

val validFillCommand = "(FILL red (RECTANGLE (1 2) (3 4)))"
val parsedFillCommand = commandValidator.parseCommand(validFillCommand, null)
println(s"Is '$validFillCommand' valid? $parsedFillCommand")

// Test invalid commands
val invalidCommand = "(INVALID (1 2) (3 4))"
val parsedInvalidCommand = commandValidator.parseCommand(invalidCommand, null)
println(s"Is '$invalidCommand' valid? $parsedInvalidCommand")


val invalidDrawCommand = "(DRAW hello (LINE (1 2) (3 4)) (RECTANGLE (5 6) (7 8)))"
val parsed_Invalid_DrawCommand = commandValidator.parseCommand(invalidDrawCommand, null)
println(s"Is '$invalidDrawCommand' valid? $parsed_Invalid_DrawCommand")
