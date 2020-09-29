class Point(val x: Int, val y: Int)

trait Rectangular {
  def topLeft: Point

  def bottomRight: Point

  def left = topLeft.x

  def right = bottomRight.x

  def width = right - left
}

abstract class Component extends Rectangular {
  def etc: Unit
}

class Rectangle(val topLeft: Point, val bottomRight: Point) extends Rectangular {
  def etcRec: Unit = print("test")
}

// test trait
val rect = new Rectangle(new Point(1, 1), new Point(4, 4))

rect.left
rect.right
rect.width
