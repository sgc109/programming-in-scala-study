# Chapter 7 - 내장 제어 구문

* 스칼라는 내장 제어 구문이 몇 가지 없음
  - *if, while, for, try, match, 함수 호출*이 전부임
  - *break, continue*도 없음
* 스칼라의 제어 구문은 대부분 어떤 값을 결과로 내놓음
  - 프로그램 전체를 값을 계산하는 관점에서 바라보고,
  - 프로그램의 각 구성요소 또한 값을 도출해야 한다는 함수 언어적 접근을 채용한 결과임
  - *if, for, try, match* 모두 결과로 값을 내놓음
  - 이런 방식을 채용하면 임시 변수를 사용하지 않아도 되기 때문에 버그를 줄이고 간결한 코드를 작성할 수 있음

## 01. if 표현식
* Java 의 기능을 모두 가지지만 한가지 다른 점은 결과로 값을 내놓는다는 점
* 이것을 이용하면 var 를 쓰지 않아도 되기 때문에 functional 하게 코드를 작성할 수 있음
  - 명령형 프로그래밍 방식
  ```scala
  var filename = "default.txt"
  if (!args.isEmpty) {
    filename = args(0)
  }
  ```
  - 함수형 프로그래밍 방식
  ```scala
  val filename = 
    if (!args.isEmpty) args(0)
    else "default.txt"
  ```

## 02. while 루프
* while 와 do-while 은 수행 결과에는 관심이 없으므로 expression 이 아닌 loop 라고 부름
* 결과값은 Unit 타입의 `()` 임
  - Unit 타입의 값을 반환 한다는 것 자체가 Java 의 void 와는 다름
  ```scala
  scala> def unitFunc() = {}
  unitFunc: ()Unit

  scala> () == unitFunc()
  <console>:13: warning: comparing values of types Unit and Unit using `==' will always yield true
         () == unitFunc()
            ^
  res4: Boolean = true
  ```
* 결과 값이 **있으면 함수**, **없으면 프로시저**라고 부름
  - Scala 에서는 물론 Unit 타입의 결과값이 있긴 하지만 쓸모가 없기에 Scala 에서도 프로시저라고 부름
* Scala 에서 할당(assign)의 결과는 Java 와는 달리 Unit 타입인 `()` 임
* while 을 쓴다는 것은 곧 while 문 내에서 **var**에 값을 갱신하거나 I/O 를 수행(부수효과)한다는 뜻
* 그래서 일부 함수형 언어는 while 이 없음
* 하지만 Scala 는 imperative 방식에 더 익숙한 프로그래머를 위해 명령형 스타일인 while 도 제공
* 가능한 한 while/do-while 을 쓰지말자

## 03. for 표현식
* Java 처럼 사용할 수 있음
  - a to b 는 [a, b], a until b 는 [a, b)
  - 세미콜론(';')으로 구분하여 nested for loop 표현 가능
```scala
for (i <- 0 to 10;
     j <- 0 until 10) {
  println(i + j)
}
```
* 반복되는 값에 filter 적용 가능
  - `if <조건식>` 형태로
  - 한 값에 filter 를 여러개 적용할 수도 있음 
```scala
for (i <- 0 to 10 if i % 2 == 0 if i % 3 == 0;
     j <- 0 until 10 if j % 2 == 1) {
  println(i + j)
}
```
```scala
// 이렇게 쓰면 좀 더 깔끔. 세미콜론은 잊으면 안됨

for (
  i <- 0 to 10
  if i % 2 == 0
  if i % 3 == 0;
  j <- 0 until 10
  if j % 2 == 1) {
  println(i + j)
}
```
* 세미콜론(';')으로 구분하기 싫으면 괄호('()')대신 중괄호('{}') 사용
```scala
for {
  i <- 0 to 10 if i % 2 == 0 if i % 3 == 0
  j <- 0 until 10 if j % 2 == 1
} {
  println(i + j)
}
```
```scala
// 마찬가지로 이렇게하면 좀 더 깔끔

for {
  i <- 0 to 10
  if i % 2 == 0
  if i % 3 == 0
  j <- 0 until 10
  if j % 2 == 1
} {
  println(i + j)
}
```
* for 문 내에서 변수를 사용할 수도 있음
  - for 키워드 다음 나오는 절 내 변수들은 모두 *val* 임
```scala
for {
  i <- 0 to 10
  ix10 = i * 10
  if ix10 % 3 == 0
} println(ix10)
```
* 값(컬렉션)을 반환하게 할 수 있음(**yield** 사용)
```scala
val vector = 
  for (i <- 0 to 10) 
    yield i * 10
```
* 실수하기 쉬운 부분
  - for-yield 표현식은 *for 절 yield 본문* 형태를 가짐
  - 즉, 중괄호('{}')가 사용되더라도 yield 뒤에 나와야지, 중괄호 안에 yield 를 넣으면 컴파일 오류
  ```scala
  for (i <- 0 to 10) {
    val ix10 = i * 10
    yield ix10
  }
  ```
  - 가 아니라
  ```scala
  for (i <- 0 to 10) yield {
    val ix10 = i * 10
    ix10
  }
  ```  
## 04. try 표현식으로 예외 다루기
### 예외 발생시키기
* 자바와는 달리 스칼라에서 throw 구문은 Nothing 타입의 값을 반환
* 어차피 이 값을 쓸 수는 없지만 if, for 문과 같이 값을 반환하게 해서 일관된 방식을 사용하기 위함
### 발생한 예외 잡기
* catch 절은 pattern match 와의 일관성을 유지하기 위해 case 문을 사용
```scala
try {
  val f = new FileReader("input.txt")
} catch {
  case ex: FileNotFoundException => // 생략
  case ex: IOException => // 생략
}
```
* case 문은 위에서부터 차례로 수행하다가 적절한 타입을 만나면 이후 case 문은 무시
### finally 절
```scala
val v = try { // 여기서 v 는 2
  println("hi")
  throw new Exception
  1 // 예외가 발생하지 않을 때만 v 에 들어감
} catch {
  case e: Exception => {
    println("hey")
    2 // try 에서 예외가 발생하고, 해당 case 문에서 예외가 잡힐 때 v 에 들어감
  }
} finally {
  println("bye") // 그 어떤 상황에서도 마지막에 무조건 실행
  3 // 무조건 버려지므로 아무런 의미가 없음
}
println(v)
```
* finally 절은 try 문에서 예외가 발생하던 하지 않던(+발생한 예외가 잡히던 말던) 상관없이 무조건 실행됨
* 그래서 주로 자원을 반환하는데 사용
* 하지만 finally 블록의 결과값은 무조건 버려짐
* try-catch-finally 절의 경우, try 절 내에서 예외가 발생하지 않았을 때 전체 결과는 try 절의 결과임
* try 내에서 예외가 발생하는 경우, 만약 catch 절에서 예외가 잡힌다면 catch 절의 결과가 전체 결과이며, 안 잡힌다면 Nothing 을 반환

## 05. match 표현식
```scala
val drink = someValue match {
  case "pizza" => "coke"
  case "chicken" => "beer"
  case _ => "soju" // default case
}
```
* 자바의 switch 와는 달리 어떤 상수값도 쓸 수 있음
* case 문에 break 필요없음
* 다른 표현식과 마찬가지로 표현식의 결과가 값임

## 06. break 와 continue 없이 살기
* 스칼라에는 break 와 continue 가 없음
* scala.util.control.Breaks 클래스의 break 메소드와 breakable 메소드를 쓰면 되긴 하지만 권장하진 않음(쓰지 말자)
* break 와 continue 대신 if 문으로 감싸는 식으로 잘 구현하면 두 구문을 대체할 수 있음
* 예를 들어 다음과 같은 continue 사용 예는
```java
var i = 0
while(i < 10) {
    if(어쩌구) {
        i += 1;
        continue
    }
    // 저쩌구
    i += 1;
}
```
* 다음과 같이 작성하면 됨
```scala
var i = 0
while(i < 10) {
  if (!어쩌구) {
     // 저쩌구
  }
  i += 1
}
```
* var 도 안쓰려면 재귀로 구현하면 됨

## 07. 변수 스코프
* 다른 많은 언어와 같이 블락을 열 때마다 새로운 변수 스코프가 생김
* 내부 스코프에서는 외부 스코프에 정의된 변수와 같은 이름을 사용할 수 있음
* 이 때는 내부 스코프의 변수가 외부 스코프의 변수를 **가렸다**고 함
* 하지만 가능하면 다른 이름을 쓰자
* 스칼라 인터프리터에서는 같은 이름의 변수를 여러번 정의할 수 있는데, 그 이유는 실제로 다음과 같이 동작하기 때문
```scala
val a = 1;
{
  val a = 2;
  {
    println(a)
  }
}
```

## 08. 명령형 스타일 코드 리팩토링
* var, while 를 사용하여 명령형으로 코드를 작성하는 대신 val, for, 도우미 함수, 재귀 함수를 잘 활용하여 함수형을 작성하자  
