package Application

class CoordinateMapper {
  private var canvasWidth  : Double = _
  private var canvasHeight : Double = _
  private var windowXMin : Double = _
  private var windowXMax : Double = _
  private var windowYMin : Double = _
  private var windowYMax : Double = _

  def init(canvasWidth  : Double, canvasHeight : Double, windowXMin : Double, windowXMax : Double, windowYMin : Double, windowYMax : Double): Unit = {
    this.canvasWidth = canvasWidth
    this.canvasHeight = canvasHeight
    this.windowXMin = windowXMin
    this.windowXMax = windowXMax
    this.windowYMin = windowYMin
    this.windowYMax = windowYMax
  }


  def mapToCanvasSpace(xWindow: Double, yWindow: Double): (Double, Double) = {
    val xCanvas = ((xWindow - windowXMin) / (windowXMax - windowXMin)) * canvasWidth
    val yCanvas = canvasHeight - ((yWindow - windowYMin) / (windowYMax - windowYMin)) * canvasHeight

    (xCanvas, yCanvas)
  }
}

