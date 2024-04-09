package Application

class CoordinateMapper {

  private var canvasWidth: Double = _
  private var canvasHeight: Double = _
  private var windowXMin: Double = _
  var windowXMax: Double = _
  private var windowYMin: Double = _
  var windowYMax: Double = _

    def canvas_init (canvasWidth : Double, canvasHeight : Double) : Unit = {
      this.canvasWidth = canvasWidth
      this.canvasHeight = canvasHeight
    }

    def init(windowXMin : Double, windowXMax : Double, windowYMin : Double, windowYMax : Double): Unit = {
      this.windowXMin = windowXMin
      this.windowXMax = windowXMax
      this.windowYMin = windowYMin
      this.windowYMax = windowYMax
    }

    def gridSize(): (Int, Int) = {

     if (canvasWidth.toInt % (windowXMax.toInt - windowXMin.toInt) != 0) {
       while (canvasWidth.toInt % (windowXMax.toInt - windowXMin.toInt) != 0) {
         this.windowXMax = windowXMax + 1
       }
      }
     if (canvasHeight.toInt % (windowYMax.toInt - windowYMin.toInt) != 0) {
       while (canvasWidth.toInt % (windowYMax.toInt - windowYMin.toInt) != 0) {
         this.windowYMax = windowYMax + 1
       }
      }

      (canvasWidth.toInt / (windowXMax.toInt - windowXMin.toInt), canvasHeight.toInt / (windowYMax.toInt - windowYMin.toInt))
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

