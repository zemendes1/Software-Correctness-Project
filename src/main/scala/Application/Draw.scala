package Application

import scalafx.collections.ObservableBuffer
import scalafx.scene.paint.Color
import scalafx.scene.image.PixelWriter
import Application.Main.{gc, AccentColor, canvas, coordinate_to_canvas, first_command}
import Application.Commands.{CircleCommand, LineCommand, RectangleCommand}


private def draw_pixels_on_canvas(commands: ObservableBuffer[String]): Unit = {

  // Clear canvas
  gc.clearRect(0, 0, gc.getCanvas.getWidth, gc.getCanvas.getHeight)

  var bounding_box_x_max = 0
  var bounding_box_y_max = 0
  var bounding_box_x_min = 0
  var bounding_box_y_min = 0

  // Run trough commands
  for (command <- commands) {

    // Define a regular expression pattern to match the command and its parameters
    val line_pattern = """(LineCommand)\(([^)]*)\)""".r
    val rectangle_pattern = """(RectangleCommand)\(([^)]*)\)""".r
    val circle_pattern = """(CircleCommand)\(([^)]*)\)""".r
    val text_pattern = """(TextAtCommand)\(([^)]*)\)""".r
    val bounding_box_pattern = """(BoundingBoxCommand)\(([^)]*)\)""".r

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

      case bounding_box_pattern(command, params) =>
        val parameters = params.split(",").map(_.trim)
        bounding_box_x_min = parameters(0).toInt
        bounding_box_y_min = parameters(1).toInt
        bounding_box_x_max = parameters(2).toInt
        bounding_box_y_max = parameters(3).toInt
        coordinate_to_canvas.init(parameters(0).toInt, parameters(2).toInt, parameters(1).toInt, parameters(3).toInt)
        coordinate_to_canvas.gridSize()
        first_command = true

      case _ => println("Draw Error")

    }
  }

  if (first_command) {
    val (gridSize_X, gridSize_Y) = coordinate_to_canvas.gridSize() // 1 unit in coordinate space is gridSize in canvas space

    // Clean everything outside bounding box
    gc.clearRect(0, 0, gc.getCanvas.getWidth, (coordinate_to_canvas.windowYMax - bounding_box_y_max) * gridSize_Y)
    gc.clearRect(gridSize_Y*bounding_box_y_max, 0,(coordinate_to_canvas.windowXMax - bounding_box_x_max) * gridSize_Y, gc.getCanvas.getHeight)



    // Draw grid
    gc.setStroke(Color.LightGray)
    gc.setLineWidth(0.5)
    // Draw vertical lines
    for (i <- 0 until canvas.getWidth.toInt by gridSize_X) {
      gc.strokeLine(i, 0, i, canvas.getHeight)
    }
    // Draw horizontal lines
    for (i <- canvas.getHeight.toInt until 0 by -gridSize_Y) {
      gc.strokeLine(0, i, canvas.getWidth, i)
    }

    //Draw Axis
    gc.setStroke(Color.Black)
    gc.setLineWidth(2.0)
    val origo = coordinate_to_canvas.mapToCanvasSpace(0, 0)
    gc.strokeLine(0, origo(1), canvas.getWidth, origo(1))
    gc.strokeLine(origo(0), 0, origo(0), canvas.getHeight)

    // Add limiting Perimeter
    gc.setStroke(AccentColor)
    gc.setLineWidth(2.0)
    gc.strokeRect(0, 0, canvas.getWidth, canvas.getHeight)
  }
  else{
    // Create a Dummy Grid

    // Add limiting Perimeter
    gc.setStroke(AccentColor)
    gc.setLineWidth(2.0)
    gc.strokeRect(0, 0, canvas.getWidth, canvas.getHeight)

    // Draw grid
    gc.setStroke(Color.LightGray)
    gc.setLineWidth(0.5)
    // Draw vertical lines
    for (i <- 0 until canvas.getWidth.toInt by 30) {
      gc.strokeLine(i, 0, i, canvas.getHeight)
    }
    // Draw horizontal lines
    for (i <- canvas.getHeight.toInt until 0 by -30) {
      gc.strokeLine(0, i, canvas.getWidth, i)
    }
  }
}
