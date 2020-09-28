# Chapter 10. 패키지와 임포트

## 13.1 패키지 안에 코드 작성하기
* 2가지 방법이 있음
  * 첫번째 방법
  * ```scala
      package creature.animal.cats
    
      class Tiger
    ```
    * 맨 윗줄에 패키지 적음
  * 두번째 방법 - Packaging
  * ```scala
      package creature {
        package animal {
          class Cat
          class Dog {
            val cat = new Cat // 1
            val bee = new Bee // 2
          }
        }
        package plant {
          class Tree
          class Flower
        }
        class Bee {
          val flower = new plant.Flower // 3
        }
      }
    ```
    * 1 - 같은 패키지에서는 접두사 필요 없음
    * 2 - 패키지 스코프 밖에서 접근 가능한 모든 이름을 쓸 수 있음
    * 3 - 공통된 조상 패키지 생략 가능

## 13.2 관련 코드에 간결하게 접근하기
```scala
package pack1 {
   
}

package pack2 {

}
```

## 13.3 임포트
```scala
// 1. Fruit 에 접근
import bobsdelights.Fruit

// 2. bobsdelights의 모든 멤버에 접근
import bobsdelights._

// 3. Fruits의 모든 멤버에 접근
import bobsdelights.Fruits._

// 4. fruit 객체의 모든 멤버에 접근
def showFruit(fruit: Fruit) = {
  import fruit._
  println(name + "s are " + color)
}
```
* 1번은 자바에서와 같이 Fruit 클래스를 임포트하는 것
* 2번은 자바에서 * 를 사용하여 패키지 내 모든 멤버를 임포트 하는것
* 3번은 자바에서의 static class field 임포트와 같이 object 
* 4번은 클래스 인스턴스의 멤버를 간단하게 접근하는데 사용 가능

* 자바의 임포트와 다른 점
  * 어느 곳에나 나타날 수 있음
  * 패키지뿐만 아니라 싱글톤이나 객체도 참조할 수 있음
  * 패키지 멤버 중 몇개를 선택하여 임포트 가능
    * ```scala
      import Fruits.{Apple, Orange}
      ```
    * ```scala
      // 둘은 같다
      import Fruits.Apple
      import Fruits.{Apple}
      ```
  * 임포트하는 패키지 멤버의 이름 변경 가능
    * ```scala
      import Fruits.{Apple => McIntosh, Orange}
      import Fruits.{Apple => McIntosh, _}
      ```
  * 패키지 내 특정 멤버를 제외하고 모든 멤버 임포트 가능
    * ```scala
      import Notebooks._
      import Fruits.{Apple => _, _}
      ``` 
    * `_` 는 항상 마지막에 와야됨
    * ```scala
      // 둘은 같다
      import Fruits._
      import Fruits.{_}
      ```
  * 패키지 자체도 임포트할 수 있음
    * ```scala
      import java.util.regex
      
      val pat = regex.Pattern.compile("a*b")
      ```
## 13.4 암시적 임포트
* 모든 스칼라 소스에는 암시적으로 다음 3가지 임포트 구문이 있다고 볼 수 있음
```scala
import java.lang._
import scala._
import Predef._
```
* 그래서 java.lang.Thread 대신 Thread, scala.List 대신 List, Predef.assert 대신 assert 라고 사용 가능
* 스칼라에서는 나중에 임포트한 것이 앞에서 임포트한 것을 가림
  * scala.StringBuilder 가 java.lang.StringBuilder 를 가림

## 13.5 접근 수식자
### Private Member
* private 멤버는 해당 클래스 안에서만 접근 가능하도록 하는 접근 수식자
* 스칼라는 자바보다 private 의 접근 가능 범위가 제한적
* 자바는 inner class 의 private 멤버에 접근 가능하지만, 스칼라는 불가능
```scala
class Outer {
  class Inner {
    private def f() = { println("f") }
    
    class InnerMost {
      f() // 문제 없음
    }
  }
  (new Inner).f() // 오류
}
```
### Protected Member
* 자바보다 접근 가능 범위가 제한적
* 자바와 달리 protected 멤버는 같은 패키지 내에서도 접근할 수 없음
* subclass 에서만 접근 가능

### Public Member
* acess modifier 가 없으면 package-private 인 자바와 달리 스칼라는 public

### Scope of Protection
* private[X] 나 protected[X] 의 형태로 쓰며, X내부에 있는 모든 객체와 클래스에서는 접근 가능하다는 뜻
* X 로는 패키지, 클래스, 싱글톤이 들어갈 수 있음
* 자바에는 이런 기능이 없어서 세밀한 제어가 불가능함
* 자바에서는 외부에 노출시키면 외부 세계 전체에 노출시켜야만 하는 반면, 스칼라는 특정 범위까지만 노출시키는 것이 가능
* `private[this]`는 companion object 에서도 접근할 수 없는 private 이라고 보면됨
  * 19.7장의 variance 관련하여 유즈케이스가 있는듯

## 13.6 패키지 객체
* 패키지의 최상위 레벨에 helper method 등을 두고 싶은 경우 사용
* 패키지명 앞에 `object` 키워드를 붙이면 됨
* package object 는 `package`가 아니라 `object`
* **`type alias` 나 `implicit conversion` 넣기 위해 package object 를 쓰는 경우가 많음**
* scala 패키지에도 패키지 객체가 있음
* package object 는 package.class 라는 이름의 클래스 파일로 컴파일 됨
  * **그러므로 이 관례를 따라 package.scala 파일로 저장하는 것이 좋음**
```scala
// bobsdelights/package.scala 파일
package object bobsdelights {
  def showFruit(fruit: Fruit) = {
    import fruit._
    println(name + "s are " + color)
  }
}
// PrintMenu.scala 파일
package printmenu
import bobsdelights.Fruits

object PrintMenu {
  def main(args: Array[String]) = {
    for (fruit <- Fruits.menu) {
      showFruit(fruit)
    }
  }
}
```
