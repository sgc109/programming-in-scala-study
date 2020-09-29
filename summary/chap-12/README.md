# Chapter 12. 트레이트


## 01. 트레이트의 동작 원리


- 트레이트
    - 타입을 정의한다.
    - 자바 인터페이스와 다른 점
        - 필드를 선언해 상태를 유지할 수 있다.
        - '클래스' 파라미터, 클래스의 주 생성자에 전달할 파라미터 를 가질 수 없다.
        - 클래스에서는 super 호출을 정적으로 바인딩하지만, 트레이트에서는 동적으로 바인딩한다.
            - super가 특이하게 동작함으로써, 변경 위에 변경을 쌓아 올리는(stackable modification) 일을 가능하게 만든다.
    - 믹스인(mixin)
        - extends, with 키워드를 사용해 클래스에 조합하여 사용
        - extends
            - 트레이트 슈퍼클래스를 암시적으로 상속
        - with
            - 여러 트레이트를 믹스인

## 02. 간결한 인터페이스와 풍부한 인터페이스


- 간결한 인터페이스(thin interface)
    - 자바의 인터페이스.
- 풍부한 인터페이스(rich interface)
    - 스칼라의 트레이트는 메소드의 구현을 넣을 수 있기 때문에, 풍부한 인터페이스를 편하게 만들 수 있다.
    - 자바와 달리, 스칼라의 트레이트에는 메소드 구현을 추가하는 일은 한 번만 하면 된다.
        - 트레이트를 믹스인한 모든 클래스마다 메소드를 다시 구현하는 대신, 트레이트 내부에 한 번만 구현하면 된다.

```scala
trait CharSequen {
  def charAt(index: Int): Char
  def length: Int
  def subSequence(start: Int, end: Int): CharSequence
  def toString(): String
}
```

## 03. 예제: 직사각형 객체


```scala
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

val rect = new Rectangle(new Point(1, 1), new Point(4, 4))

rect.left
rect.right
rect.width
```

## 04. Ordered 트레이트


- Ordered 트레이트
    - 풍부한 인터페이스를 이용하면 편리해지는 또 다른 영역, 비교
    - 하나의 비교 연산자만 작성하면 모든 비교 연산자(<, >, ≤, ≥) 구현을 대신할 수 있다.
    - compare 메소드만 구현하면 Ordered 트레이트가 비교 메소드를 제공해 클래스를 풍부하게 해준다.
        - 동일하면 0, 호출 대상 객체 자신이 인자보다 작으면 음수, 더 크면 양수를 반환해야 한다.
        - 비교 관점에서 equals를 구현하려면 전달받을 객체의 타입을 알아야 한다. 하지만 타입 소거(type erasure) 때문에 Ordered 트레이트는 이러한 검사를 수행할 수 없어, Ordered를 상속하더라도 equals는 직접 정의해야 한다.
    - 믹스인할 때 Ordered[C]와 같이 비교하고자 하는 클래스 C를 명시해야 한다.

    ```scala
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
    ```

## 05. 트레이트를 이용해 변경 쌓아 올리기


- 쌓을 수 있는 변경

    ```scala
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

    val queue = new MyQueue
    queue.put(10)
    queue.get
    ```

    - trait 에 클래스 상속이 있으면, 그 클래스를 상속한 클래스에만 믹스인될 수 있다.
    - 트레이트의 추상 메소드가 super 를 호출할 수 있다.(동적 바인딩)
        - 컴파일러에게 의도적으로 super의 메소드를 호출했다는 사실을 알려주기 위해 abstract override로 표시해야 한다.
        - abstract override는 클래스에는 사용할 수 없고, 트레이트의 멤버에만 사용할 수 있다.
    - 새로운 코드가 없을 때, 단순 트레이트 믹스인일 때 클래스 이름을 따로 정의하는 대신 BasicIntQueue with Doubling 을 바로 new 키워드와 함께 사용할 수도 있다.

        ```scala
        val newQ = new BasicIntQueue with Doubling
        newQ.put(10)
        newQ.get
        ```

- 믹스인
    - 가장 오른쪽 트레이트 메소드를 가장 먼저 호출.
    - 만일 그 메소드가 super 호출하면 왼쪽에 있는 다음 트레이트를 호출.

        ```scala
        trait Incrementing extends IntQueue {
          abstract override def put(x: Int) = super.put(x + 1)
        }

        trait Filtering extends IntQueue {
          abstract override def put(x: Int) = if (x >= 0) super.put(x)
        }

        val queue2 = new BasicIntQueue with Incrementing with Filtering
        queue2.put(-1)
        queue2.put(0)
        queue2.put(1)
        // 1
        queue2.get
        // 2
        queue2.get
        ```

## 06. 왜 다중 상속은 안 되는가?


- 선형화(linearization)
    - 트레이트 사용할 때는 특정 클래스에 믹스인한 클래스, 트레이트를 선형화해서 어떤 메소드 호출할지 결정한다.
    - 모든 선형화에서 어떤 클래스는 자신의 슈퍼클래스나 믹스인해넣은 트레이트보다 앞에 위치한다.
    - Cat → FourLegged → HasLegs → Furry → Animal → AnyRef → Any

        ```scala
        class Animal
        trait Furry extends Animal
        trait HasLegs extends Animal
        trait FourLegged extends HasLegs

        class Cat extends Animal with Furry with FourLegged
        ```

## 07. 트레이트냐 아니냐, 이것이 문제로다


- 트레이트
    - 서로 관련이 없는 클래스에서 어떤 행위를 여러 번 재사용 할 때
        - 클래스 계층의 각기 다른 부분에 믹스인할 수 있는 것은 트레이트 뿐이다.
    - 컴파일한 바이너리 형태로 배포할 예정일 때
- 추상 클래스
    - 스칼라에서 정의한 내용을 자바 코드에서 상속할 때
        - 자바에는 코드가 들어있는 트레이트와 유사한 개념이 없기 때문에..
        - 단, 구현 코드 없이 추상 메소드만 들어있는 스칼라 트레이트는 내부적으로  자바 인터페이스를 만들어내기 때문에 자바 코드에서 얼마든지 상속해도 좋다.
    
https://www.notion.so/Chapter-12-596ae7ad4ab44bbbb4cd4bf69546fe1e
