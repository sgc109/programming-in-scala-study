# Chapter 8 - 함수와 클로저
## 8.1 Method
* 객체의 멤버인 함수를 method 라 한다.
```scala
import scala.io.Source

object LongLines {
  def processFile(filename: String, width: Int) = {
    val source = Source.fromFile(filename)
    for (line <- source.getLines()) {
      processLine(filename, width, line)
    }
  }
  
  private def processLine(filename: String, width: Int, line: String) = {
    if (line.length > width) {
      println(filename + ": " + line.trim)
    }
  }
}
```

## 8.2 Local Functions
* 조립 가능한 building block 제공
* 블록 내에서만 접근 가능한 function
```scala
def processFile(filename: String, width: Int) {
    def processLine(filename: String, width: Int, line: String) { 
        if (line.length > width)
            println(filename +": "+ line)
    }

    val source = Source.fromFile(filename)
    for (line <- source.getLines())
        processLine(line)
}
```

* 바깥 function 의 파라미터를 사용

## 8.3 First-Class Function
* 함수 정의, 호출, 리터럴로 표기해 값처럼 쓸수 있다.
* 컴파일 : 함수 리터럴 -> 클래스, 런타임 : 인스턴스화 -> 함수 값 

```scala
(x: Int) => x + 1
```

```scala
scala> var increase = (x: Int) => x + 1
var increase: Int => Int = $Lambda$1076/0x0000000801107040@62de73eb

scala> increase(10)
val res0: Int = 11

scala> increase = (x: Int) => x + 9999 
// mutated increase

scala> increase(10)
val res1: Int = 10009
```

* 문장이 둘 이상인 경우 처리
```scala
scala> increase = (x: Int) => {
     |   println("We")
     |   println("are")
     |   println("here!")
     |   x+1
     | }

val increase: Int => Int = $Lambda$1074/0x0000000801102040@bd273b2
```

* foreach 함수에 적용해보기
```scala
scala> val someNumbers = List(-11, -10, -5, 0, 5, 10)
val someNumbers: List[Int] = List(-11, -10, -5, 0, 5, 10)

scala> someNumbers
val someNumbers: List[Int]
scala> someNumbers.foreach((x: Int) => println(x))
-11
-10
-5
0
5
10
```

## 8.4 Short forms of function literals
* 코드 줄여보자
* 인자타입 제거
* 타입추론에 의거한 Target typing (컴파일러가 혼란해 하면 적어주자)

```scala
scala> someNumbers.filter((x) => x > 0)
val res6: List[Int] = List(5, 10)
```
```scala
scala> someNumbers.filter(x => x > 0)
val res7: List[Int] = List(5, 10)
```

## 8.5 Placeholder syntax (위치 표시자 문법)
* 더 간결해지자
* 하나 이상의 파라미터에 대한 위치 표시자로 사용할 수 있다.
```scala
scala> someNumbers.filter(_ > 0)
val res8: List[Int] = List(5, 10)
```
* 컴파일러가 헷갈려 하면 타입을 적어준다.
```scala
scala> val f = _ + _
               ^
       error: missing parameter type for expanded function ((<x$1: error>, x$2) => x$1.$plus(x$2))
                   ^
       error: missing parameter type for expanded function ((<x$1: error>, <x$2: error>) => x$1.$plus(x$2))
scala> val f = (_: Int) + (_: Int)
     | f(5, 10)
val f: (Int, Int) => Int = $Lambda$1226/0x0000000801188840@2a01c55
val res9: Int = 15
```

## 8.6 Partially applied functions (부분 적용한 함수)
* 전체 파라미터를 placeholder 로 바꿈
* 아래 두개는 동치
```scala
scala> someNumbers.foreach(println _)
-11
-10
-5
0
5
10

scala> someNumbers.foreach(x => println(x))
-11
-10
-5
0
5
10
```

* 인자를 전부 적용하지 않은 표현식
* sum _ 는 인자 3개를 받는 apply method
* 책은 function3 이라는데 람다 식으로 변경된거 같다
```scala
scala> def sum(a: Int, b: Int, c: Int) = a + b + c
def sum(a: Int, b: Int, c: Int): Int

scala> sum(1, 2, 3)
val res12: Int = 6

scala> val a = sum _
val a: (Int, Int, Int) => Int = $Lambda$1252/0x0000000801195440@8be5379

scala> a(1, 2, 3)
val res14: Int = 6

scala> a.apply(1, 2, 3)
val res15: Int = 6
```

* 지역함수를 반환할수는 없고 부분 적용한 함수로 리턴 가능
* 부분 적용한 함수
```scala
scala> val b = sum(1, _: Int, 3)
val b: Int => Int = $Lambda$1253/0x0000000801196840@26309e6f

scala> b(2)
val res17: Int = 6
```

* placeholder 생략
```scala
scala> someNumbers.foreach(println)
```

```
scala> val c = sum
               ^
       error: missing argument list for method sum
       Unapplied methods are only converted to functions when a function type is expected.
       You can make this conversion explicit by writing `sum _` or `sum(_,_,_)` instead of `sum`.
```

## 8.7 Closures
* free variable (more)
* bound variable (x)
```scala
(x: Int) => x + more
```

* 주어진 함수 리터럴로부터 실행 시점에 만들어낸 객체인 함수 값 
```scala
scala> var more = 1
     | val addMore = (x: Int) => x + more
var more: Int = 1
val addMore: Int => Int = $Lambda$1258/0x00000008011a1840@2ca57834

scala> addMore(10)
val res20: Int = 11
```

* 자유 변수가 없는게 목표다 (closed term)
* 변수 자체를 capture 한다
```scala
scala> more = 9999
// mutated more

scala> addMore(10)
val res25: Int = 10009
```

* 반대 관점
```scala
scala> val someNumbers = List(-11, -10, -5, 0, 5, 10)
val someNumbers: List[Int] = List(-11, -10, -5, 0, 5, 10)

scala> var sum = 0
var sum: Int = 0

scala> someNumbers.foreach(sum += _)

scala> sum
val res27: Int = -11
```

* 복사본 테스트
```scala
scala> def makeIncreaser(more: Int) = (x: Int) => x + more
def makeIncreaser(more: Int): Int => Int

scala> val inc1 = makeIncreaser(1)
val inc1: Int => Int = $Lambda$1275/0x00000008011b7840@f057789

scala> val inc9999 = makeIncreaser(9999)
val inc9999: Int => Int = $Lambda$1275/0x00000008011b7840@57b97628

scala> inc1(10)
val res28: Int = 11

scala> inc9999(10)
val res29: Int = 10009
```

* 함수가 끝나면 원래 스택에서 나가야하지만 캡쳐된 내용은 살아있어야 하므로 힙에 이사간다.

## 8.8 Repeated parameters
* repeated parameter (반복 파라미터)
```scala
scala> def echo(args: String*) = for (arg <-args) println(arg)
def echo(args: String*): Unit

scala> echo ()

scala> echo("one")
one

scala> echo("hello", "world!")
hello
world!
```

* https://docs.scala-lang.org/overviews/core/collections-migration-213.html

```scala
scala> val arr = Array("What's", "up", "doc?")
val arr: Array[String] = Array(What's, up, doc?)

scala> echo(arr: _*)
            ^
       warning: Passing an explicit array value to a Scala varargs method is deprecated (since 2.13.0) and will result in a defensive copy; Use the more efficient non-copying ArraySeq.unsafeWrapArray or an explicit toIndexedSeq call
What's
up
doc?

scala> echo(arr.toSeq: _*)
What's
up
doc?
```

* named argument
```scala
scala> def speed(distance: Float, time: Float): Float = distance / time
def speed(distance: Float, time: Float): Float

scala> speed(100, 1)
val res47: Float = 100.0

scala> speed(distance = 100, time = 10)
val res48: Float = 10.0

scala> speed(time = 10, distance = 100)
val res49: Float = 10.0
```

* default argument
```scala
scala> def printTime(out: java.io.PrintStream = Console.out) = out.println("time = " + System.currentTimeMillis())
def printTime(out: java.io.PrintStream): Unit

scala> printTime()
time = 1600341968881
```

## 8.9 Tail recursion
* 최적화 한다.
```scala
def approximate(guess: Double): Double = {
  if (isGooeEnough(guess)) guess else approximate(improve(guess))
}
```

### Trace
* Tail recursion 이 아닌 경우 trace
```scala
scala> def boom(x: Int): Int = if (x == 0) throw new Exception("boom!") else boom(x - 1) + 1
def boom(x: Int): Int

scala> boom(3)
java.lang.Exception: boom!
  at boom(<console>:1)
  at boom(<console>:1)
  at boom(<console>:1)
  at boom(<console>:1)
  ... 32 elided
```

* Tail recursion trace
```scala
scala> def bang(x: Int): Int = if (x == 0) throw new Exception("bang!") else bang(x - 1)
def bang(x: Int): Int

scala> bang(5)
java.lang.Exception: bang!
  at bang(<console>:1)
  ... 32 elided
```

### Limitation 
* 최적화가 안되는 경우
```scala
def isEven(x: Int): Boolean = if (x == 0) true else isOdd(x - 1)
def isOdd(x: Int): Boolean = if (x == 0) false else isEven(x - 1)
```

* 수행 결과가 자신이 아니면 안된다.
```scala
val funValue = nestedFun _
def nestedFun(x: Int): Unit = {
  if (x != 0) { println(x); funValue(x - 1)}
}
```