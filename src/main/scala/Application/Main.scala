package Application

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


object Main extends JFXApp3 {

  private val AccentColor: Paint = Color.RoyalBlue

  private val commandValidityTextProperty: StringProperty = StringProperty("Welcome to the Drawing App")
  private val commandValidityColorProperty: ObjectProperty[Paint] = ObjectProperty[Paint](AccentColor)

  private val executedCommands: ObservableBuffer[String] = ObservableBuffer[String]()

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
                  text = "Graph Creator Pro"
                  style = "-fx-font: bold 40pt Montserrat"
                  fill = AccentColor
                }
              )
            },
            new HBox{
              val canvas: Canvas = new Canvas(600, 600)
              val gc: GraphicsContext = canvas.graphicsContext2D

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


              /*
              // Add your drawing logic here using gc
              gc.fill = Color.Yellow
              gc.stroke = Color.Black
              gc.lineWidth = 2.0

              // Draw head
              gc.fillOval(100, 100, 200, 200)


              // Draw eyes
              gc.fill = Color.Black
              gc.fillOval(150, 150, 20, 20)
              gc.fillOval(230, 150, 20, 20)

              // Draw mouth
              gc.strokeArc(150, 200, 100, 60, 0, -180, javafx.scene.shape.ArcType.OPEN)
              */


              val Command_Pane : ScrollPane= new ScrollPane {
                content = new ListView[String] {
                  items = executedCommands
                  prefHeight = 600
                  prefWidth = 200
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
                prefWidth = 700
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
    if (parsedCommand) {
      commandValidityTextProperty.value = ""
      commandValidityColorProperty.value = AccentColor
      executedCommands += command
    } else {
      commandValidityTextProperty.value = "Invalid command"
      commandValidityColorProperty.value = Color.Red
      println("Invalid command")
    }

    // Add your command execution logic here
  }
}
