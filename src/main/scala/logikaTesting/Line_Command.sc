// #Sireum #Logika
import org.sireum._

@pure def mapToCanvasSpace(xWindow: Z, yWindow: Z, canvasWidth: Z, canvasHeight: Z,
                           windowXMax:Z, windowYMax : Z, windowXMin:Z , windowYMin :Z): (Z, Z) = {
  Contract(
    Requires(windowXMax > windowXMin & windowYMax > windowYMin, canvasWidth >0, canvasHeight > 0)
  )
  Deduce (|- (windowXMax - windowXMin > 0))
  val xCanvas = ((xWindow - windowXMin) / (windowXMax - windowXMin)) * canvasWidth
  val yCanvas = canvasHeight - ((yWindow - windowYMin) / (windowYMax - windowYMin)) * canvasHeight

  return (xCanvas, yCanvas)
}

@pure def absoluteValue(num: Z): Z = {
  Contract(
    Ensures((Res == num || Res == -num) & (Res >= 0))
  )
  var res : Z = 0

  if (num < 0){
    Deduce(|- (num < 0))
    res =  -num
  }
  else{
    Deduce(|- (num >= 0))
    res = num
  }
  Deduce(|- ((res == num || res == -num) & (res >= 0)))
  return res
}

@pure def draw(x1:Z, y1:Z, x2:Z, y2:Z, canvasWidth:Z, canvasHeight:Z,
               windowXMax:Z, windowYMax : Z, windowXMin:Z , windowYMin :Z): ISZ[ISZ[Z]] = {
  Contract(
    Requires(windowXMax > windowXMin & windowYMax > windowYMin, canvasWidth > 0, canvasHeight > 0),
  )
  val point_1 = mapToCanvasSpace(x1, y1, canvasWidth, canvasHeight, windowXMax, windowYMax, windowXMin, windowYMin)
  val point_2 = mapToCanvasSpace(x2, y2, canvasWidth, canvasHeight, windowXMax, windowYMax, windowXMin, windowYMin)

  var points : ISZ[ISZ[Z]] = ISZ(ISZ())

  var x = point_1._1
  var y = point_1._2

  val dx = absoluteValue(point_2._1 - point_1._1)
  val dy = absoluteValue(point_2._2 - point_1._2)

  var sx : Z = 0
  var sy : Z = 0

  if (point_1._1 < point_2._1){
    sx = 1
  }
  else{
    sx = -1
  }

  if (point_1._2 < point_2._2){
    sy = 1
  }
  else{
    sy = -1
  }

  var err = dx - dy

  while (x != point_2._1 | y != point_2._2) {
    Invariant(
      Modifies(points, err, x, y),
    )
    points = points :+ ISZ(x, y)

    val e2 = 2 * err

    if (e2 > -dy) {
      err = err - dy
      x = x + sx
    }

    if (e2 < dx) {
      err = err + dx
      y = y + sy
    }
  }

  return points
}
