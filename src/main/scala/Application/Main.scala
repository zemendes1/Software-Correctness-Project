package Application

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ListView, ScrollPane, TextArea}
import scalafx.scene.effect.DropShadow
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.{Color, LinearGradient, Paint, Stops}
import scalafx.scene.text.Text
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos


object Main extends JFXApp3 {

  private val commandValidityTextProperty: StringProperty = StringProperty("Welcome to the Drawing App")
  private val commandValidityColorProperty: ObjectProperty[Paint] = ObjectProperty[Paint](Color.Black)

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
                  text = "Drawing"
                  style = "-fx-font: normal bold 40pt sans-serif"
                  fill = new LinearGradient(endX = 0, stops = Stops(Color.Blue, Color.Green))
                },
                new Text {
                  text = "App"
                  style = "-fx-font: italic bold 40pt sans-serif"
                  fill = new LinearGradient(endX = 0, stops = Stops(Color.Red, Color.Orange))
                  effect = new DropShadow {
                    color = Color.DarkGray
                    radius = 15
                    spread = 0.25
                  }
                }
              )
            },
            new HBox{
              alignment = Pos.CenterRight
              spacing = 10 // You can adjust the spacing as needed
              children = Seq(
              new ScrollPane {
                content = new ListView[String] {
                  items = executedCommands
                  prefHeight = 200
                }
              }
              )
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
                wrapText = true
              }
              val Button_Text: Button = new Button {
                text = "Execute"
                onMouseClicked = (e: MouseEvent) => handleExecuteButtonClick(commandTextArea, e)
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
      commandValidityColorProperty.value = Color.Black
      executedCommands += command
    } else {
      commandValidityTextProperty.value = "Invalid command"
      commandValidityColorProperty.value = Color.Red
      println("Invalid command")
    }

    // Add your command execution logic here
  }
}
