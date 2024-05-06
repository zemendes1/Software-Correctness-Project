// #Sireum #Logika
import org.sireum._

@pure def mapToCanvasSpace(xWindow: Z, yWindow: Z, canvasWidth: Z, canvasHeight: Z,
                           windowXMax:Z, windowYMax : Z, windowXMin:Z , windowYMin :Z): (Z, Z) = {
  Contract(
    Requires(windowXMax > windowXMin & windowYMax > windowYMin, canvasWidth >0, canvasHeight > 0,
      xWindow >= windowXMin & xWindow <= windowXMax, yWindow >= windowYMin & yWindow <= windowYMax),
    Ensures(Res._1 <= canvasWidth, Res._2 <= canvasHeight, Res._1 >= 0, Res._2 >= 0)
  )
  Deduce (|- (windowXMax - windowXMin > 0))
  Deduce (|- (windowYMax - windowYMin > 0))

  val xCanvas = ((xWindow - windowXMin) / (windowXMax - windowXMin)) * canvasWidth
  Deduce (|- (xCanvas <= canvasWidth & xCanvas >= 0))
  val yCanvas = canvasHeight - ((yWindow - windowYMin) / (windowYMax - windowYMin)) * canvasHeight
  Deduce (|- (yCanvas <= canvasHeight & yCanvas >= 0))

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

def draw(x1:Z, y1:Z, x2:Z, y2:Z, canvasWidth:Z, canvasHeight:Z,
         windowXMax:Z, windowYMax : Z, windowXMin:Z , windowYMin :Z): ISZ[ISZ[Z]] = {
  Contract(
    Requires(windowXMax > windowXMin & windowYMax > windowYMin, canvasWidth > 0, canvasHeight > 0,
      x1 >= windowXMin & x1 <= windowXMax, y1 >= windowYMin & y1 <= windowYMax,
      x2 >= windowXMin & x2 <= windowXMax, y2 >= windowYMin & y2 <= windowYMax),
    Ensures(All(0 until Res.size)(i => Res(i)(0) <= canvasWidth) & All(0 until Res.size)(i => Res(i)(1) <= canvasHeight))
  )
  val point_1 = mapToCanvasSpace(x1, y1, canvasWidth, canvasHeight, windowXMax, windowYMax, windowXMin, windowYMin)
  val point_2 = mapToCanvasSpace(x2, y2, canvasWidth, canvasHeight, windowXMax, windowYMax, windowXMin, windowYMin)
  Deduce(|- (point_1._1 <= canvasWidth & point_1._2 <= canvasHeight))
  Deduce(|- (point_2._1 <= canvasWidth & point_2._2 <= canvasHeight))

  var points : ISZ[ISZ[Z]] = ISZ()
  Deduce(|- (points.size == 0))

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
  Deduce(|- (sx == 1 || sx == -1))
  Deduce(|- (sy == 1 || sy == -1))

  var err = dx - dy

  var x_upper_bound = 0
  var x_lower_bound = 0
  var y_upper_bound = 0
  var y_lower_bound = 0

  if (point_1._1 < point_2._1) {
    x_upper_bound = point_2._1
    x_lower_bound = point_1._1
  } else {
    x_upper_bound = point_1._1
    x_lower_bound = point_2._1
  }

  if (point_1._2 < point_2._2) {
    y_upper_bound = point_2._2
    y_lower_bound = point_1._2
  } else {
    y_upper_bound = point_1._2
    y_lower_bound = point_2._2
  }
  Deduce(|- (x_upper_bound <= canvasWidth & x_lower_bound >= 0))
  Deduce(|- (y_upper_bound <= canvasHeight & y_lower_bound >= 0))

  var counter = 0

  while (x <= x_upper_bound && x >= x_lower_bound && y <= y_upper_bound  && y >= y_lower_bound) {
    Invariant(
      Modifies(points, err, x, y, counter, sx, sy),
      points.size >= 0,
      All(0 until points.size)(i => points(i).size == 2),
      All(0 until points.size)(i => points(i)(0) <= canvasWidth),
      All(0 until points.size)(i => points(i)(1) <= canvasHeight),
    )
    Deduce(|- (x <= canvasWidth & x >= 0))
    Deduce(|- (y <= canvasHeight & y >= 0))
    Deduce(|- (ISZ(x, y)(0) <= canvasWidth & ISZ(x, y)(1) <= canvasHeight))
    Deduce(|- (ISZ(x, y)(0) >= 0 & ISZ(x, y)(1) >= 0))

    points = points :+ ISZ(x, y)

    Deduce(|- ((x >= point_1._1 & x <= point_2._1) | (x <= point_1._1 & x >= point_2._1)))
    Deduce(|- ((y >= point_1._2 & y <= point_2._2) | (y <= point_1._2 & y >= point_2._2)))

    val e2 = 2 * err

    if (e2 > -dy) {
      err = err - dy
      x = x + sx
    }

    if (e2 < dx) {
      err = err + dx
      y = y + sy
    }
    counter = counter + 1
  }

  return points
}