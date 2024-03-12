package Application

class CoordinateMapper {
  private var canvasWidth  : Int = _
  private var canvasHeight : Int = _
  private var windowXMin : Int = _
  private var windowXMax : Int = _
  private var windowYMin : Int = _
  private var windowYMax : Int = _

  def init(canvasWidth  : Int, canvasHeight : Int, windowXMin : Int, windowXMax : Int, windowYMin : Int, windowYMax : Int): Unit = {
    this.canvasWidth = canvasWidth
    this.canvasHeight = canvasHeight
    this.windowXMin = windowXMin
    this.windowXMax = windowXMax
    this.windowYMin = windowYMin
    this.windowYMax = windowYMax
  }


  def mapToCanvasSpace(xWindow: Int, yWindow: Int): (Int, Int) = {
    val xCanvas = ((xWindow - windowXMin) / (windowXMax - windowXMin)) * canvasWidth
    val yCanvas = canvasHeight - ((yWindow - windowYMin) / (windowYMax - windowYMin)) * canvasHeight

    (xCanvas, yCanvas)
  }

  def mapToCanvasSpace_radius(xWindow: Int, yWindow: Int, radius: Int): (Int, Int, Int) = {
    val xCanvas = ((xWindow - windowXMin) / (windowXMax - windowXMin)) * canvasWidth
    val yCanvas = canvasHeight - ((yWindow - windowYMin) / (windowYMax - windowYMin)) * canvasHeight
    val radiusCanvas = (radius / (windowXMax - windowXMin)) * canvasWidth // You can also use canvasHeight if the aspect ratio is different

    (xCanvas, yCanvas, radiusCanvas)
  }


}

