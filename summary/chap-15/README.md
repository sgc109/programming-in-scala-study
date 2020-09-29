# Chapter 15. 케이스 클래스와 패턴 매치


## 01. 간단한 예


- 케이스 클래스
    - 각 서브클래스 앞에 case 수식자가 있다.
    - 컴파일러는 클래스 이름과 같은 이름의 팩토리 메소드를 추가한다.

        ```scala
        val v = Var("x")
        ```

    - 케이스 클래스의 파라미터 목록에 있는 모든 인자에 암시적으로 val 접두사를 붙인다.
    - 컴파일러는 케이스 클래스에 toString, hashCode, equals 메소드의 일반적인 구현을 추가한다.
    - 컴파일러는 어떤 케이스 클래스에서 일부를 변경한 복사본을 생성하는 copy 메소드를 추가한다.
        - copy 메소드는 디폴트 파라미터와 이름 붙인 파라미터를 제공.
        - 변경하고 싶은 인자만 명시 가능. 명시하지 않은 인자의 값은 원본 객체꺼 사용.
- 패턴 매치
    - 상수 패턴
        - == 연산자를 적용
    - 변수만을 사용한 패턴
        - 모든 값과 매치할 수 있다.
        - 매치에 성공한 패턴에 바운드된 변수 e를 결과로 내놓는다.
    - 와일드카드 패턴
        - 모든 값과 매치할 수 있다.
        - 이름을 붙이지는 않는다.(매치된 값을 사용할 수 없다)
    - 생성자 패턴
        - 생성자 인자도 역시 패턴이다.
- swtich와 match의 비교
    - 스칼라의 match는 표현식이다. 그 식은 결과값을 내놓는다.
    - 스칼라의 대안 표현식(case)은 다음 케이스로 빠지지 않는다.
    - 매치에 성공하지 못하는 경우 MatchError 예외가 발생한다.(디폴트 케이스를 추가해야 한다)

## 02. 패턴의 종류


- 와일드캐드 패턴
    - 와일드카드 패턴(_)은 어떤 객체라도 매치할 수 있다.
- 상수 패턴
    - 자신과 똑같은 값과 매치된다.

    ```scala
    def describe(x: Any) = x match {
      case 5 => "five"
      case Nil => "the empty list"
      case sthElse => "not 5 or Nil, " + sthElse
      case _ => "something else"
    }
    ```

- 변수 패턴
    - 어떤 객체와도 매치된다.
    - 변수에 바인딩하고, 그 변수를 사용할 수 있다.
    - 소문자 이름을 상수 패턴으로 사용하고 싶다면
        - 어떤 객체의 필드인 경우 지정자(qulifier)를 앞에 붙일 수 있다.
        - 역따옴표를 사용해 변수 이름을 감쌀 수 있다.
- 생성자 패턴
    - 어떤 값이 해당 케이스 클래스의 멤버인지 검사한 다음, 그 객체의 생성자가 인자로 전달받은 값들이 괄호 안의 패턴과 정확히 매치될 수 있는지 검사한다.
    - 스칼라 패턴은 깊은 매치(deep match)를 지원한다. 원하는 깊이까지 객체 내부를 검사할 수 있다.

    ```scala
    expr match {
      case BinOp("+", e, Number(0)) => println("a deep match")
      case _ =>
    }
    ```

- 시퀀스 패턴
    - 배열이나 리스트 같은 시퀀스 타입에 대한 매치.
    - 패턴 내부에 원하는 개수만큼 원소를 명시할 수 있다.
    - 길이 상관없이 매치하고 싶다면, 패턴의 마지막 원소를 _*로 표시.(원소가 아예 없는 경우도 매치)

    ```scala
    def chk(expr: Any) = expr match {
      case List(0, _, _) => println("3 element list")
      case List(0, _*) => println("other length list")
      case _ =>
    }
    ```

- 튜플 패턴

    ```scala
    def tupleDemo(expr: Any) = expr match {
      case (a, b, c) => println("matched " + a + b + c)
      case _ =>
    }
    ```

- 타입 지정 패턴(typed pattern)
    - 타입 검사나 변환을 간편하게 하기 위해 사용.
    - isInstanceOf(체크), asInstanceOf(변환)와 동일

        ```scala
        def generalSize(x: Any) = x match {
          case s: String => s.length
          case m: Map[_, _] => m.size
          case _ => -1
        }
        ```

    - 타입 소거
        - 스칼라는 자바처럼 제네릭에서 타입 소거(type erasure) 모델을 사용.
        - 실행 시점에 타입 인자에 대한 정보를 유지하지 않는다.
        - 유일한 예외는 배열. 배열에서는 원소 타입과 값을 함께 저장한다.

            ```scala
            def isStringArray(x: Any) = x match {
              case a: Array[String] => "yes"
              case _ => "no"
            }

            val as = Array("abc")
            // yes
            isStringArray(as)
            val ai = Array(1, 2, 3)
            // no
            isStringArray(ai)
            ```

- 변수 바인딩 패턴
    - 다른 패턴에 변수를 추가할 수도 있다.
    - 변수 이름 다음에 @ 기호를 넣고 패턴 쓴다.
    - 패턴에 대해 일반적인 방법대로 매치를 시도하고, 그 패턴 매치에 성공하면 변수 패턴에서처럼 매치된 객체를 변수에 저장한다.

    ```scala
    case class Test(x: String, t: Test)

    def variableBinding(expr: Any) = expr match {
      case Test("str", a@Test(_, _)) => "yes, " + a
      case _ => "no"
    }
    val t1 = Test("str", Test(null, null))
    // yes, Test(null,null)
    variableBinding(t1)
    // no
    variableBinding(2)
    ```

## 03. 패턴 가드


- 선형 패턴 제한
    - 어떤 패턴 변수가 한 패턴 안에 오직 한 번만 나와야 한다.
- 패턴 가드
    - 패턴 뒤에 오고 if로 시작한다.

    ```scala
    def multiply(e: Expr) = e match {
      case e if e == 2 => e * 2
      case _ => e
    }
    ```

## 05. 봉인된 클래스


- 봉인된 클래스(sealed class)
    - match 식에서 놓친 패턴 조합이 있는지 찾도록 컴파일러에게 도움 요청하기
        - 케이스 클래스의 슈퍼클래스를 봉인된 클래스로 만든다.
    - 봉인된 클래스는 그 클래스와 같은 파일이 아닌 곳에서 새 서브클래스 만들 수 없다.
        - 패턴 매치를 위한 클래스 계층을 작성한다면 그 계층에 속한 클래스 봉인을 고려하자.
    - sealed 키워드

        ```scala
        sealed abstract class Expr
        case class Var(name: String) extends Expr
        case class Number(num: Double) extends Expr
        case class UnOp(operator: String, arg: Expr) extends Expr
        case class BinOp(operator: String, left: Expr, right: Expr) extends Expr

        // warning: match may not be exhaustive.
        // It would fail on the following inputs: BinOp(_, _, _), UnOp(_, _)
        def describe(e: Expr): String = e match {
          case Number(_) => "number"
          case Var(_) => "variable"
        //  case _ => throw new RuntimeException
        }
        ```

    - @unchecked
        - 매치 셀렉터에 추가하면, 컴파일러는 그 match 문의 case 문이 모든 패턴을 다루는지 검사 생략.

            ```scala
            def describe2(e: Expr): String = (e: @unchecked) match {
              case Number(_) => "number"
              case Var(_) => "variable"
            }
            ```

## 06. Option 타입


- Option 타입
    - 선택적인 값을 표현한다.
    - Some(x)
        - x가 실제 값일 경우 값이 있음을 표현
    - None
        - 값 없음

    ```scala
    def show(x: Option[String]) = x match {
      case Some(s) => s
      case None => "?"
    }
    val company = Map("Jenny" -> "Amazon", "Sungho" -> "Google", "Probe" -> "Facebook")

    // Amazon
    show(company get "Jenny")
    // Google
    show(company get "Sungho")
    // Facebook
    show(company get "Probe")
    ```

## 07. 패턴은 어디에나


- 변수 정의에서 패턴 사용하기
    - val, var 정의할 때, 단순 식별자 대신 패턴을 사용할 수 있다.

    ```scala
    val myTuple = (123, "abc")
    val (n, s) = myTuple
    //n = 3    // error: reassignment to val
    ```

- case를 나열해서 부분 함수 만들기
    - 함수 리터럴이 쓰일 수 있는 곳이라면, 중괄호 사이에 case 나열한 표현식(부분 함수) 쓸 수 있다.

        ```scala
        var sum = 0
        def receive = {
          case byte: Int => sum += byte  // 부분함수 1
          case GetChecksum(requester) => val checksum = ~(sum & 0xFF) + 1  // 부분함수 2
        }
        ```

    - 부분함수(partial function)
        - isDefinedAt 메소드
            - 부분 함수가 어떤 값에 대해 결과 값을 정의하고 있는지를 알려준다.

            ```scala
            // true
            second.isDefinedAt(List(5, 6, 7))
            ```

        - 타입이 Function1 이거나 타입 표기가 없으면 함수 리터럴을 완전한 함수(complete function)로 변환한다.
        - 가능하다면 완전한 함수를 사용하는 편이 좋다.
            - 컴파일러가 도와줄 수 없는 실행 시점 오류가 발생할 수도 있기 때문이다.
- for 표현식에서 패턴 사용하기
    - for 표현식 안에 패턴을 사용할 수 있다.
    - 생성한 값 중 패턴과 일치하지 않는 값은 버린다.

    ```scala
    val results = List(Some("apple"), None, Some("orange"))
    // apple, orange
    for (Some(fruit) <- results) println(fruit)
    ```

https://www.notion.so/Chapter-15-f754572b1d164651946a9a3c42ba599e
