import scala.collection.mutable.ArrayBuffer

abstract class IntQueue {
  def get(): Int

  def put(x: Int)
}

class BasicIntQueue extends IntQueue {
  private val buf = new ArrayBuffer[Int]

  def get = buf.remove(0)

  def put(x: Int) = buf += x
}

trait Doubling extends IntQueue {
  abstract override def put(x: Int): Unit = super.put(2 * x)
}

class MyQueue extends BasicIntQueue with Doubling

trait Incrementing extends IntQueue {
  abstract override def put(x: Int) = super.put(x + 1)
}

trait Filtering extends IntQueue {
  abstract override def put(x: Int) = if (x >= 0) super.put(x)
}

// test StackableModification
val queue = new MyQueue
queue.put(10)
queue.get

val newQ = new BasicIntQueue with Doubling
newQ.put(10)
newQ.get

// test StackableModification2
val queue2 = new BasicIntQueue with Incrementing with Filtering
queue2.put(-1)
queue2.put(0)
queue2.put(1)
queue2.get
queue2.get
