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
