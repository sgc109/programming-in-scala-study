# Chapter 10. 상속과 구성 

## 10.1 2차원 레이아웃 라이브러리
```scala
val column1 = elem("hello") above elem("***")
val column2 = elem("***") above elem("world")
column1 beside column2

// 출력 결과
// hello ***
//  *** world
``` 
* 위와같은 조립 연산자는 특정 도메인의 요소를 결합해 새로운 요소를 만들어내기 때문에 콤비네이터 라고도 부른다.
* 콤비네이터의 관점에서 생각하는 것은 라이브러리 설계 시 좋은 접근 방법이다.
* 가장 일반적인 콤비네이터는 어떤 것이고 어떻게 이들을 조합할지를 생각한다면 라이브러리를 제대로 만들고 있는 것

## 10.2 추상 클래스
```scala
abstract class Element {
  def contents: Array[String]
}
```
* contents 는 **abstract member** 라고 부른다.
* abstract member 가 있는 클래스는 **abstract class** 로 선언해야한다.
* abstract class 는 내부에 abstract member 가 있다는 의미이므로 인스턴스화할 수 없다.
  * 자바와 동일
* 자바와 달리 abstract method 에 abstract 수식자를 추가하지 않는다.
  * 위 Element 클래스처럼 그냥 def 로 시작
* 구현이 없으면 abstract method, 있으면 concrete method 다.
  * 자바와 동일
* abstract method 는 declare 하는 것이고, concrete method 는 define 하는 것이다.
  * 자바와 동일

## 10.3 파라미터 없는 메소드 정의 
* 파라미터 목록이 없는 경우엔 괄호('()') 를 쓸 수도 있고 안 쓸 수도 있다.
* 괄호를 쓰면 empty-paren method 라고 부르고, 안쓰면 parameterless method 라고 부른다.
```scala
abstract class MyClass {
  var num = 1
  
  def func1() = {
    num = 2
  }
  
  def func2 = num
}
```
* side effect 가 없다면 parameterless method 로 작성하는것이 관례다.
  * 필드로 정의하는 것과 결과과 완전히 동일하기 때문
* 이러한 관례는 단일 접근 워칙(uniform access principle)에 부합한다.
* parameterless method 대신 필드를 사용할 수도 있다.
* 둘은 결과는 완전히 같지만 동작 방식이 조금 다름
  * 필드는 클래스 초기화 시에 값을 미리 계산하기 때문에 자주 사용하는 경우엔 좀 더 빠름
  * 대신 추가적인 메모리를 사용
  * 고로 어떻게 사용하는지에 따라 어떤 방식이 좋은지 달라짐
* 함수가 순수하다면 필드와 parameterless method 방식 간에 변경은 아무런 차이도 만들어내지 않음
* I/O 를 수행하거나, var 변수를 변경하거나, mutable 객체를 사용하는 함수를 호출할 땐 인자가 없어도 괄호를 붙이자
* 요약: **파라미터가 없는 함수의 경우, 순수함수라면 정의부와 호출부 모두에서 괄호를 떼고, 순수하지 않은 함수라면 정의부와 호출부 모두에서 괄호를 붙여라**

## 10.4 클래스 확장
* 자바의 java.lang.Object 는 스칼라의 scala.AnyRef 와 같다고 볼 수 있다.
* 아무 클래스도 상속하지 않는 클래스는 암묵적으로 AnyRef 를 상속한다.
* 그러므로 모든 클래스는 AnyRef 의 서브타입이 된다.
* 슈퍼클래스의 멤버를 서브클래스에서 재정의하면 오버라이드 했다고 함
* 슈퍼클래스의 추상 멤버를 서브클래스에서 구체화하는 경우에는 구현했다고 하기도 한다.
  * 이 떄는 override 수식자를 생략 가능
```scala
scala> abstract class AbstractClass {
     | def func: Int
     | }
defined class AbstractClass

scala> class ChildClass extends AbstractClass {
     | def func = 1
     | }
defined class ChildClass

scala> abstract class AbstractClass {
     | def func = 1
     | }
defined class AbstractClass

scala> class ChildClass extends AbstractClass {
     | def func = 2
     | }
<console>:13: error: overriding method func in class AbstractClass of type => Int;
 method func needs `override' modifier
       def func = 2
```

## 10.5 메소드와 필드 오버라이드
* 스칼라에서는 필드와 메소드가 같은 네임스페이스에 있기 때문에, 필드로 파라미터 없는 메소드를 오버라이드할 수 있다.
```scala
scala> abstract class AbstractClass {
     | def func: Int
     | }
defined class AbstractClass

scala> class ChildClass extends AbstractClass {
     | val func = 1
     | }
defined class ChildClass

scala> (new ChildClass).func
res2: Int = 1
```
* 자바에서는 같은 이름의 메소드와 필드를 동시에 정의할수 있지만, 스칼라는 불가능하다.
```java
// Java Code
class CompilesFine {
  private int f = 0;
  public int f() {
    return 1;
  }
}
```
```scala
// Scala Code
class WontCompile {
  private var f = 0
  def f = 1
```

* 자바는 *필드, 메소드, 타입, 패키지*라는 **4가지** 네임스페이스가 있지만,
* 스칼라는 *값*(필드, 메소드, 패키지, 싱글톤 객체), *타입*(클래스와 트레이트 이름) 이렇게 **2가지** 네임스페이스가 있다.
* 스칼라에서 필드와 메소드를 동일한 네임스페이스로 취급하는 이유는 **파라미터 없는 메소드를 val 로 오버라이드 하기 위해서다.**
* 스칼라에서 패키지명이 필드 및 메소드와 네임스페이스를 공유하는 이유는, **필드와 메소드도 import 할 수 있게 하기 위해서다.**

## 10.6 파라미터 필드 정의
* 클래스 파라미터 앞에 val 을 붙이면 **parameter field** 가 된다.
  * 즉, parameter 의 역할과 동시에 field 의 역할도 할 수 있게 된다.
* val 대신 var 로 정의할 수도 있다.
* private, protected, override 같은 수식자를 추가할 수도 있다.

```scala
class Cat {
  val dangerous = false
}

class Tiger {
  override val dangerous: Boolean,
  private var age: Int
} extends Cat
```

* 위의 Tiger 클래스는 다음을 간략화한것이다.

```scala
class Tiger(param1: Boolean, param2: Int) extends Cat {
  override val dangerous = param1
  private var age = param2
```

### 10.9 다형성과 동적 바인딩
* 스칼라는 자바같은 언어에서도 흔히 있는 *subtyping polymorphism* 외에도 다른 다형성이 있다
  * 다른 다형성은 19장(타입 파라미터화)에서 다룸
* 스칼라는 메소드 호출을 동적으로 바인딩
  * 자바도 똑같지 않나..?

### 10.10 final 멤버 선언
* 서브클래스가 특정 멤버를 오버라이드 하지 못하게 막고 싶을 때는 자바와 마찬가지로 final 수식자를 추가한다.
```scala
class ArrayElement extends Element {
  final override def demo() = {
    println("ArrayElement's implementation invoked")   }
}
```
* 클래스 전체를 상속하지 못하게 하고 싶으면 class 앞에 final 수식자를 붙인다.
```scala
final class ArrayElement extends Element {
  override def demo() {
    println("ArrayElement's implementation invoked")
  }
}
```

### 10.12 above, beside, toString 구현
* Array(1, 2, 3) zip Array("a", "b") 는 Array((1, "a"), (2, "b"))
```scala
new ArrayElement(
  for (
    (line1, line2) <- this.contents zip that.contents
  ) yield line1 + line2
)

// *     *
// *     *
// *     *
//
// +
//
// **
// **
// **
```

### 10.13 팩토리 객체 정의
* 팩토리 객체는 다른 객체를 생성하는 메소드를 제공하는 객체다.
* 팩토리 객체를 만들려면 우선 팩토리 메소드의 위치를 정해야한다.
* 싱글톤 객체, 또는 클래스에 위치할 수 있다.
* Element 의 companion object 를 만들어 ArrayElement, LineElement, UniformElement 의 구현은 감출 수 있다. 그래서 변경에 유리하다.
```scala
object Element {
  def elem(contents: Array[String]): Element = new ArrayElement(contents)
  def elem(chr: Char, width: Int, height: Int): Element = new UniformElement(chr, width, height)
  def elem(line: String): Element = new LineElement(line)
```
* 이제 ArrayElement, LineElement, UniformElement class 를 직접 사용할 일이 없으니 팩토리 객체인 Element 안에 private 으로 넣는다
