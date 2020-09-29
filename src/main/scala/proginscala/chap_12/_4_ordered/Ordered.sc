class Point(val x: Int, val y: Int)

trait Rectangular {
  def topLeft: Point

  def bottomRight: Point

  def left = topLeft.x

  def right = bottomRight.x

  def width = right - left
}

class Rational(val topLeft: Point, val bottomRight: Point) extends Ordered[Rational] with Rectangular {
  def compare(that: Rational) = this.width - that.width

  override def equals(that: Any): Boolean = {
    that match {
      case t: Rational => this.width == t.width
      case _ => false
    }
  }
}

// test Ordered
val r1 = new Rational(new Point(1, 1), new Point(4, 4))
val r2 = new Rational(new Point(2, 2), new Point(5, 5))

print(s"r1 > r2: ${r1 > r2}")
print(s"r1 > r2: ${r1 < r2}")
print(s"r1 > r2: ${r1 == r2}")
