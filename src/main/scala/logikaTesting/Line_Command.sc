// #Sireum #Logika
import org.sireum._

@pure def mapToCanvasSpace(xWindow: Z, yWindow: Z, canvasWidth: Z, canvasHeight: Z,
                           windowXMax:Z, windowYMax : Z, windowXMin:Z , windowYMin :Z): (Z, Z) = {
  Contract(
    Requires(windowXMax > windowXMin & windowYMax > windowYMin, canvasWidth >0, canvasHeight > 0)
  )
  Deduce (|- (windowXMax - windowXMin > 0))
  Deduce (|- (windowYMax - windowYMin > 0))

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

@pure def calculate_boundaries(x1:Z, y1:Z, x2:Z, y2:Z, canvasWidth:Z, canvasHeight:Z, windowXMax:Z, windowYMax : Z, windowXMin:Z , windowYMin :Z): Unit ={
  Contract(
    Requires(windowXMax > windowXMin & windowYMax > windowYMin, canvasWidth > 0, canvasHeight > 0),
  )

  val point_1 = mapToCanvasSpace(x1, y1, canvasWidth, canvasHeight, windowXMax, windowYMax, windowXMin, windowYMin)
  val point_2 = mapToCanvasSpace(x2, y2, canvasWidth, canvasHeight, windowXMax, windowYMax, windowXMin, windowYMin)

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

  draw(x1, y1, x2, y2, x_upper_bound, x_lower_bound, y_upper_bound, y_lower_bound)
}

@pure def draw(x1:Z, y1:Z, x2:Z, y2:Z, x_upper_bound:Z, x_lower_bound:Z, y_upper_bound:Z, y_lower_bound:Z): ISZ[ISZ[Z]] = {
  Contract(
    Ensures(
      All(0 until Res.size)(i =>
        Res(i)(1) >= y_lower_bound
          & Res(i)(1) <= y_upper_bound
          & Res(i)(0) >= x_lower_bound
          & Res(i)(0) <= x_upper_bound
      ),
      Res.size >= 0)
  )


  var points : ISZ[ISZ[Z]] = ISZ()
  Deduce(|- (points.size == 0))

  var x = x_lower_bound
  var y = y_lower_bound

  val dx = absoluteValue(x_upper_bound - x_lower_bound)
  val dy = absoluteValue(y_upper_bound - y_lower_bound)

  var sx : Z = 0
  var sy : Z = 0

  if (x1 < x2){
    sx = 1
  }
  else{
    sx = -1
  }

  if (y1 < y2){
    sy = 1
  }
  else{
    sy = -1
  }
  Deduce(|- (sx == 1 || sx == -1))
  Deduce(|- (sy == 1 || sy == -1))

  var err = dx - dy

  var counter = 0

  while (x <= x_upper_bound && x >= x_lower_bound && y <= y_upper_bound  && y >= y_lower_bound) {
    Invariant(
      Modifies(points, err, x, y, counter, sx, sy),
      points.size >= 0,
      All(0 until points.size)(i => points(i).size == 2),
      All(0 until points.size)(i => points(i)(0) <= x_upper_bound & points(i)(0) >= x_lower_bound
        & points(i)(1) <= y_upper_bound & points(i)(1) >= y_lower_bound),
    )
    Deduce(|- (x >= x_lower_bound & x <= x_upper_bound))
    Deduce(|- (y >= y_lower_bound & y <= y_upper_bound))
    Deduce(|- (ISZ(x, y)(0) <= x_upper_bound & ISZ(x, y)(1) <= y_upper_bound))
    Deduce(|- (ISZ(x, y)(0) >= x_lower_bound & ISZ(x, y)(1) >= y_lower_bound))

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
    counter = counter + 1
  }

  return points
}