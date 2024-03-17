package Application

import scalafx.collections.ObservableBuffer
import scalafx.scene.paint.Color
import scalafx.scene.image.PixelWriter
import Application.Main.{gc, AccentColor, canvas, coordinate_to_canvas}
import Application.Commands.{CircleCommand, LineCommand, RectangleCommand}


private def draw_pixels_on_canvas(commands: ObservableBuffer[String]): Unit = {

  // Clear canvas
  gc.clearRect(0, 0, gc.getCanvas.getWidth, gc.getCanvas.getHeight)

  // Add limiting Perimeter
  gc.setStroke(AccentColor)
  gc.setLineWidth(2.0)
  gc.strokeRect(0, 0, canvas.getWidth, canvas.getHeight)

  // Draw grid
  val gridSize = coordinate_to_canvas.mapToCanvasSpace(0, 0)._1 // 1 unit in coordinate space is gridSize in canvas space
  gc.setStroke(Color.LightGray)
  gc.setLineWidth(0.5)
  // Draw vertical lines
  for (i <- 0 until canvas.getWidth.toInt by gridSize) {
    gc.strokeLine(i, 0, i, canvas.getHeight)
  }
  // Draw horizontal lines
  for (i <- canvas.getHeight.toInt until 0 by -gridSize) {
    gc.strokeLine(0, i, canvas.getWidth, i)
  }

  //Draw Axes
  gc.setStroke(Color.Black)
  gc.setLineWidth(2.0)
  val origo = coordinate_to_canvas.mapToCanvasSpace(0, 0)
  gc.strokeLine(0, origo(1), canvas.getWidth, origo(1))
  gc.strokeLine(origo(0), 0, origo(0), canvas.getHeight)

  // Run trough commands
  for (command <- commands) {

    // Define a regular expression pattern to match the command and its parameters
    val line_pattern = """(LineCommand)\(([^)]*)\)""".r
    val rectangle_pattern = """(RectangleCommand)\(([^)]*)\)""".r
    val circle_pattern = """(CircleCommand)\(([^)]*)\)""".r
    val text_pattern = """(TextAtCommand)\(([^)]*)\)""".r

    // TODO
    //val fill_pattern =
    //val bounding_box_pattern =

    // Extract the command and parameters using pattern matching
    command match {
      case line_pattern(command, params) =>
        val parameters = params.split(",").map(_.trim)

        val lineCommandInstance = LineCommand(parameters(0).toInt, parameters(1).toInt, parameters(2).toInt, parameters(3).toInt, parameters(4))
        val array: Array[(Int, Int)] = lineCommandInstance.draw("Black")

        val pixelWriter: PixelWriter = gc.pixelWriter
        for (i <- array.indices) {
          pixelWriter.setColor(array(i)._1, array(i)._2, colorConverter(lineCommandInstance.drawColor.toLowerCase()))
        }

      case rectangle_pattern(command, params) =>
        // println(s"Command: $command")

        // Split the parameters by commas and trim any whitespace
        val parameters = params.split(",").map(_.trim)

        // Print each parameter
        // parameters.foreach(println)
        val rectangleCommandInstance = RectangleCommand(parameters(0).toInt, parameters(1).toInt, parameters(2).toInt, parameters(3).toInt, parameters(4))
        val array: Array[(Int, Int)] = rectangleCommandInstance.draw("Black")

        val pixelWriter: PixelWriter = gc.pixelWriter
        for (i <- array.indices) {
          pixelWriter.setColor(array(i)._1, array(i)._2, colorConverter(rectangleCommandInstance.drawColor.toLowerCase()))
        }

      case circle_pattern(command, params) =>
        // Split the parameters by commas and trim any whitespace
        val parameters = params.split(",").map(_.trim)

        val circleCommandInstance = CircleCommand(parameters(0).toInt, parameters(1).toInt, parameters(2).toInt, parameters(3))

        val array: Array[(Int, Int)] = circleCommandInstance.draw("Black")

        val pixelWriter: PixelWriter = gc.pixelWriter
        for (i <- array.indices) {
          pixelWriter.setColor(array(i)._1, array(i)._2, colorConverter(circleCommandInstance.drawColor.toLowerCase()))
        }

      case text_pattern(command, params) =>
        // Split the parameters by commas and trim any whitespace
        val parameters = params.split(",").map(_.trim)
        val (map_x, map_y) = coordinate_to_canvas.mapToCanvasSpace(parameters(0).toInt, parameters(1).toInt)
        val text = parameters(2)
        val color = colorConverter(parameters(3).toLowerCase)

        // Set the font and fill color for the text
        gc.fill = color

        // Draw the text on the canvas at the specified position
        gc.fillText(text, map_x, map_y)

      // TODO
      //case fill_pattern() =>
      //case bounding_box_pattern() =>


      case _ => println("Draw Error")

    }
  }
}
