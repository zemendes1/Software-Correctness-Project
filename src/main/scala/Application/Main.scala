package Application

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.control.ListView.EditEvent
import scalafx.scene.control.{Button, ListView, ScrollPane, TextArea}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.{Color, Paint}
import scalafx.scene.text.{Font, Text}
import scalafx.scene.control.cell.TextFieldListCell

object Main extends JFXApp3 {

  val AccentColor: Paint = Color.RoyalBlue

  private val commandValidityTextProperty: StringProperty = StringProperty("Welcome to the Drawing App")
  private val commandValidityColorProperty: ObjectProperty[Paint] = ObjectProperty[Paint](AccentColor)

  private val parsedCommands: ObservableBuffer[String] = ObservableBuffer[String]()
  private val executedCommands: ObservableBuffer[String] = ObservableBuffer[String]()

  val canvas: Canvas = new Canvas(660, 660) // Has to be a multiple of |XMax - XMin| and |YMax - YMin|
  val gc: GraphicsContext = canvas.graphicsContext2D
  private val font = new Font("Arial", 20)

  // Window Space for the plotter
  private val XMin: Int = -1
  private val XMax: Int = 10
  private val YMin: Int = -1
  private val YMax: Int = 10

  val coordinate_to_canvas = new CoordinateMapper
  coordinate_to_canvas.init(canvas.getWidth.toInt, canvas.getHeight.toInt, XMin, XMax, YMin, YMax)

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
                  prefHeight = canvas.getWidth.toInt
                  prefWidth = canvas.getHeight.toInt/3
                  editable = true
                  cellFactory = TextFieldListCell.forListView()
                  onEditCommit = e => {
                    handleEditCommit(executedCommands.get(e.index) , e.newValue, e)
                  }
                }
              }
              alignment = Pos.CenterRight
              spacing = 10
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
    draw_pixels_on_canvas(parsedCommands)
  }

  private def handleEditCommit(previous_command:String, command: String, event:EditEvent[String]): Unit = {

    val parsedCommand = CommandValidator().parseCommand(command, null)
    if (parsedCommand != "") {
      parsedCommands.update(event.index, parsedCommand)
      executedCommands.update(event.index, command)
    } else {
      // Revert the changes
      executedCommands.update(event.index, previous_command)
    }
    draw_pixels_on_canvas(parsedCommands)
  }
}