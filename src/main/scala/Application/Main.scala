package Application

import Application.Commands.{CircleCommand, LineCommand, RectangleCommand}
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.control.{Button, ListView, ScrollPane, TextArea}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.{Color, Paint}
import scalafx.scene.text.Text
import scalafx.scene.image.PixelWriter


object Main extends JFXApp3 {

  private val AccentColor: Paint = Color.RoyalBlue

  private val commandValidityTextProperty: StringProperty = StringProperty("Welcome to the Drawing App")
  private val commandValidityColorProperty: ObjectProperty[Paint] = ObjectProperty[Paint](AccentColor)

  private val parsedCommands: ObservableBuffer[String] = ObservableBuffer[String]()
  private val executedCommands: ObservableBuffer[String] = ObservableBuffer[String]()

  private val canvas: Canvas = new Canvas(600, 600)
  private val gc: GraphicsContext = canvas.graphicsContext2D

  // Window Space for the plotter
  private val XMin: Double = -1
  private val XMax: Double = 10
  private val YMin: Double = -1
  private val YMax: Double = 10

  private val coordinate_to_canvas = new CoordinateMapper
  coordinate_to_canvas.init(canvas.getWidth, canvas.getHeight, XMin, XMax, YMin, YMax)

  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage :
      title = "Software Correctness Project"
      scene = new Scene :
        fill = Color.rgb(255, 255, 255)
        content = new VBox :
          padding = Insets(10)
          children = Seq(
            new HBox {
              children = Seq(
                new Text {
                  text = "Graph Creator Pro Max"
                  style = "-fx-font: bold 40pt Montserrat"
                  fill = AccentColor
                }
              )
            },
            new HBox{
              draw_pixels_on_canvas(ObservableBuffer[String]())
              val Command_Pane : ScrollPane= new ScrollPane {
                content = new ListView[String] {
                  items = executedCommands
                  prefHeight = 600
                  prefWidth = 300
                }
              }
              alignment = Pos.CenterRight
              spacing = 10 // You can adjust the spacing as needed
              children = Seq(canvas, Command_Pane)
            },
            new HBox{
              val Command_Validity: Text = new Text {
                text <== commandValidityTextProperty
                fill <== commandValidityColorProperty
              }
              children = Seq(Command_Validity)
            },
            new HBox {
              val commandTextArea: TextArea = new TextArea {
                id = "commandTextArea"
                promptText = "Enter commands here..."
                prefHeight = 100
                prefWidth = 800
                wrapText = true
              }
              val Button_Text: Button = new Button {
                text = "Execute"
                onMouseClicked = (e: MouseEvent) => handleExecuteButtonClick(commandTextArea, e)
                prefWidth = 100
              }
              children = Seq(commandTextArea, Button_Text)
            }
          )

  private def handleExecuteButtonClick(commandTextArea: TextArea, event: MouseEvent): Unit = {
    // Handle the execution of commands entered in the TextArea
    val command = commandTextArea.getText

    val parsedCommand = CommandValidator().parseCommand(command, null)
    if (parsedCommand != "") {
      commandValidityTextProperty.value = ""
      commandValidityColorProperty.value = AccentColor
      executedCommands += command
      parsedCommands +=  parsedCommand
    } else {
      commandValidityTextProperty.value = "Invalid command"
      commandValidityColorProperty.value = Color.Red
      println("Invalid command")
    }

    // println(parsedCommands)

    draw_pixels_on_canvas(parsedCommands)
  }

  private def draw_pixels_on_canvas(commands: ObservableBuffer[String]): Unit = {

    // Clear canvas
    gc.clearRect(0, 0, gc.getCanvas.getWidth, gc.getCanvas.getHeight)

    // Add limiting Perimeter
    gc.setStroke(AccentColor)
    gc.setLineWidth(2.0)
    gc.strokeRect(0, 0, canvas.getWidth, canvas.getHeight)

    // Draw grid
    val gridSize = 20
    gc.setStroke(Color.LightGray)
    gc.setLineWidth(0.5)
    for (i <- 0 until canvas.getWidth.toInt by gridSize) {
      gc.strokeLine(i, 0, i, canvas.getHeight)
    }
    for (i <- 0 until canvas.getHeight.toInt by gridSize) {
      gc.strokeLine(0, i, canvas.getWidth, i)
    }

    // Run trough commands
    for (command <- commands) {
      // println(command)

      // Define a regular expression pattern to match the command and its parameters
      val line_pattern = """(LineCommand)\(([^)]*)\)""".r
      val rectangle_pattern = """(RectangleCommand)\(([^)]*)\)""".r
      val circle_pattern = """(CircleCommand)\(([^)]*)\)""".r

      // Extract the command and parameters using pattern matching
      command match {
        case line_pattern(command, params) =>
          // println(s"Command: $command")
          val parameters = params.split(",").map(_.trim)

          val lineCommandInstance =LineCommand(parameters(0).toInt, parameters(1).toInt, parameters(2).toInt, parameters(3).toInt, parameters(4))
          val array: Array[(Double, Double)] = lineCommandInstance.draw(Color.Black)

          val pixelWriter: PixelWriter = gc.pixelWriter
          for (i <- array.indices) {
            val (map_x, map_y) = coordinate_to_canvas.mapToCanvasSpace(array(i)._1, array(i)._2)
            pixelWriter.setColor(map_x.toInt, map_y.toInt, Color.Black)
          }

        case rectangle_pattern(command, params) =>
          // println(s"Command: $command")

          // Split the parameters by commas and trim any whitespace
          val parameters = params.split(",").map(_.trim)

          // Print each parameter
          // parameters.foreach(println)
          val rectangleCommandInstance = RectangleCommand(parameters(0).toInt, parameters(1).toInt, parameters(2).toInt, parameters(3).toInt, parameters(4))
          val array: Array[(Double, Double)] = rectangleCommandInstance.draw(Color.Black)

          val pixelWriter: PixelWriter = gc.pixelWriter
          for (i <- array.indices) {
            val (map_x, map_y) = coordinate_to_canvas.mapToCanvasSpace(array(i)._1, array(i)._2)
            pixelWriter.setColor(map_x.toInt, map_y.toInt, Color.Black)
          }

        case circle_pattern(command, params) =>
          //println(s"Command: $command")

          // Split the parameters by commas and trim any whitespace
          val parameters = params.split(",").map(_.trim)

          // Print each parameter
          // parameters.foreach(println)
          val circleCommandInstance = CircleCommand(parameters(0).toInt, parameters(1).toInt, parameters(2).toInt, parameters(3))

          val array: Array[(Double, Double)] = circleCommandInstance.draw(Color.Black)

          val pixelWriter: PixelWriter = gc.pixelWriter
          for (i <- array.indices) {
            val (map_x, map_y) = coordinate_to_canvas.mapToCanvasSpace(array(i)._1, array(i)._2)
            pixelWriter.setColor(map_x.toInt, map_y.toInt, Color.Black)
          }

        case _ => println("Invalid command format")
      }

    }

  }
}