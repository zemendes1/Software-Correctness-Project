import scala.swing._
import java.awt.{ Color}
import com.PixelGrid
import com.Draw

object Main extends SimpleSwingApplication {
  def top: Frame = new MainFrame {
    title = "Pixel Grid Demo"
    preferredSize = new Dimension(800, 800)

    val pixelGrid: PixelGrid = new PixelGrid
    val draw = new Draw
    val circle = draw.makeCircle(50, Array(50, 50))

    contents = new BoxPanel(Orientation.Vertical) {
      contents += Swing.VStrut(10)
      contents += pixelGrid
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    circle.foreach { case (x, y) =>
      pixelGrid.updatePixel(x, y, Color.BLACK)
    }
  }
}
