package Application

import Application.Commands.{CircleCommand, LineCommand, RectangleCommand, TextAtCommand}
import scalafx.collections.ObservableBuffer
import Application.Main.{gc, coordinate_to_canvas}

class CommandExtracter {
  // Regular expressions to match the valid command formats

  def Extract(command: String): Unit = {
    // Define a regular expression pattern to match the command and its parameters
    val line_pattern = """(LineCommand)\(([^)]*)\)""".r
    val rectangle_pattern = """(RectangleCommand)\(([^)]*)\)""".r
    val circle_pattern = """(CircleCommand)\(([^)]*)\)""".r
    val text_pattern = """(TextAtCommand)\(([^)]*)\)""".r
    // Extract the command and parameters using pattern matching
    command match {
      case line_pattern(command, params) =>
        val parameters = params.split(",").map(_.trim)
        val lineCommandInstance = new LineCommand(parameters(0).toInt, parameters(1).toInt, parameters(2).toInt, parameters(3).toInt, parameters(4))
        lineCommandInstance.draw()

      case rectangle_pattern(command, params) =>
        val parameters = params.split(",").map(_.trim)
        val rectangleCommandInstance = new RectangleCommand(parameters(0).toInt, parameters(1).toInt, parameters(2).toInt, parameters(3).toInt, parameters(4))
        rectangleCommandInstance.draw()

      case circle_pattern(command, params) =>
        val parameters = params.split(",").map(_.trim)
        val circleCommandInstance = new CircleCommand(parameters(0).toInt, parameters(1).toInt, parameters(2).toInt, parameters(3))
        circleCommandInstance.draw()

        // TODO Is it ok to Generate text here? I feel like it is wrong to run the whole text generation here
        // Maybe make this in TextAtCommand, but still use the same method
        // TODO Implement the text drawing
      case text_pattern(command, params) =>
        val parameters = params.split(",").map(_.trim)
        val (map_x, map_y) = coordinate_to_canvas.mapToCanvasSpace(parameters(0).toInt, parameters(1).toInt)
        val textCommandInstance = new TextAtCommand(parameters(0).toInt, parameters(1).toInt, parameters(2), parameters(3))
        val text = parameters(2)
        val color = colorConverter(parameters(3).toLowerCase)

        // Set the font and fill color for the text
        gc.fill = color

        // Draw the text on the canvas at the specified position
        gc.fillText(text, map_x, map_y)
        return

      case _ => println("Invalid command")
    }

  }
}