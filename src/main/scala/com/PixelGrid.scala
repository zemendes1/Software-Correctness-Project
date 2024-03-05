package com

import java.awt.{Color, Graphics2D}
import scala.swing.Panel

class PixelGrid extends Panel {
  var pixels: Map[(Double, Double), Color] = Map.empty

  def updatePixel(x: Double, y: Double, color: Color): Unit = {
    pixels += (x -> y) -> color
    repaint()
  }

  override def paintComponent(g: Graphics2D): Unit = {
    super.paintComponent(g)

    pixels.foreach {
      case ((x, y), color) =>
        g.setColor(color)
        g.fillRect(x.toInt, y.toInt, 1, 1)
    }
  }
}