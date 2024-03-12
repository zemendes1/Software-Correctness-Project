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


    def mapToCanvasSpace(xWindow: Double, yWindow: Double): (Int, Int) = {
      val xCanvas = ((xWindow - windowXMin) / (windowXMax - windowXMin)) * canvasWidth
      val yCanvas = canvasHeight - ((yWindow - windowYMin) / (windowYMax - windowYMin)) * canvasHeight

      (xCanvas.toInt, yCanvas.toInt)
    }

  def mapToCanvasSpace_radius(xWindow: Double, yWindow: Double, radius: Double): (Int, Int, Int) = {
    val xCanvas = ((xWindow - windowXMin) / (windowXMax - windowXMin)) * canvasWidth
    val yCanvas = canvasHeight - ((yWindow - windowYMin) / (windowYMax - windowYMin)) * canvasHeight
    val radiusCanvas = (radius / (windowXMax - windowXMin)) * canvasWidth // You can also use canvasHeight if the aspect ratio is different

    (xCanvas.toInt, yCanvas.toInt, radiusCanvas.toInt)
  }


}

