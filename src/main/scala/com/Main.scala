package com

import scala.swing._
import scala.swing.event.ButtonClicked

object Main extends SimpleSwingApplication {
  def top: Frame = new MainFrame {
    title = "Simple Swing Demo"
    preferredSize = new Dimension(300, 200)

    val button: Button = new Button {
      text = "Click me!"
    }

    val label: Label = new Label {
      text = "Button not clicked yet."
    }

    contents = new BoxPanel(Orientation.Vertical) {
      contents += button
      contents += Swing.VStrut(10)
      contents += label
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    listenTo(button)

    reactions += {
      case ButtonClicked(`button`) =>
        label.text = "Button clicked!"
    }
  }
}
