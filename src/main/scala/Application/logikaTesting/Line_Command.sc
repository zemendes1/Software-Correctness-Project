// #Sireum #Logika
import org.sireum._

val canvasHeight : Z = 600
val canvasWidth : Z = 600

val windowXMin : Z = 0
val windowYMin : Z = 0
val windowXMax : Z = 20
val windowYMax : Z = 20

val x1 : Z = randomInt()
val x2 : Z = randomInt()
val y1 : Z = randomInt()
val y2 : Z = randomInt()


def mapToCanvasSpace(xWindow: Z, yWindow: Z): (Z, Z) = {
  val xCanvas = ((xWindow - windowXMin) / (windowXMax - windowXMin)) * canvasWidth
  val yCanvas = canvasHeight - ((yWindow - windowYMin) / (windowYMax - windowYMin)) * canvasHeight

  return (xCanvas, yCanvas)
}

def absoluteValue(num: Z): Z = {
  if (num < 0){
    return -num
  }
  else{
    return num
  }
}

def draw(): ISZ[ISZ[Z]] = {
  val point_1 = mapToCanvasSpace(x1, y1)
  val point_2 = mapToCanvasSpace(x2, y2)

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

  while (true) {
    Invariant(
      Modifies(points, err, x, y),
    )
    points = points :+ ISZ(x, y)

    if (x == point_2._1 && y == point_2._2) {
      return points
    }

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
