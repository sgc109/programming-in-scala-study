# Chapter 6. 함수형 객체


## 01. 분수 클래스 명세


- Rational 클래스
    - 변경 불가능한 클래스.
    - 어떤 두 Rational 객체로 연산 후 새 Rational 객체를 만든다.

## 02. Rational 생성


- 클래스 파라미터
    - 스칼라 컴파일러는 내부적으로 두 클래스 파라미터를 받는 주 생성자를 만든다.
    - 자바: 클래스에 인자를 받는 생성자가 있다.
    - 스칼라: 클래스가 바로 인자를 받는다.

## 03. toString 메소드 다시 구현하기


- override
    - 오버라이드 수식자. 메소드 정의를 오버라이드하겠다.
    - toString 메소드를 클래스에 추가하면 기존의 구현을 오버라이드 할 수 있다.

        ```scala
        // x: Rational = Rational@2591e0c9

        // x: Rational = 1/3
        class Rational(n: Int, d: Int) {
          override def toString = n + "/" + d
        }
        ```

## 04. 선결 조건 확인


- 객체지향 프로그램의 이점
    - 정보를 객체 내부에 캡슐화해서, 객체의 일생 동안 그 정보가 유효하다고 확신할 수 있다는 점
    - 유효하지 않다면 객체 생성하면 안된다.

- require
    - 인자로 불리언 값 하나 받는다.
    - 참이 아니면 IllegalArgumentException 발생해 객체의 생성을 막는다.

        ```scala
        // java.lang.IllegalArgumentException: requirement failed
        class Rational(n: Int, d: Int) {
          require(d != 0)
          override def toString = n + "/" + d
        }
        ```

## 05. 필드 추가


- 다른 객체의 값에 접근하기 위해서는 필드로 만들어야 한다.

    ```scala
    class Rational(n: Int, d: Int) {
      require(d != 0)

      val numer: Int = n
      val denom: Int = d

      override def toString = n + "/" + d

      def add(that: Rational): Rational = new Rational(numer * that.denom + that.numer * denom, denom * that.denom)
    }

    val x1 = new Rational(1, 3)
    val x2 = new Rational(1, 4)

    x1 add x2  // res0: Rational = 7/12
    x1.add(x2) // res1: Rational = 7/12
    ```

## 06. 자기 참조


- 자기 참조
    - 현재 실행 중인 메소드의 호출 대상 인스턴스에 대한 참조
    - 생성자 내부에서는 생성중인 객체의 인스턴스를 가리킨다.
    - 반환할 값 아니면 this 빼고 써도 된다.

## 07. 보조 생성자


- 보조 생성자
    - 주 생성자가 아닌 다른 생성자
    - def this(...) 로 시작하고, 첫 구문은 this(...)여야 한다.
    - 반드시 같은 클래스에 속한 다른 생성자를 호출하는 코드로 시작해야 한다.

스칼라의 모든 생성자 호출을 올라가면 결국 주 생성자를 호출한다.

- 주 생성자
    - 주 생성자만이 슈퍼클래스의 생성자를 호출할 수 있다.

        ```scala
        class Vehicle(price: Int) {
          println("super 주 생성자 호출하니?")
        }

        class Car(price: Int) extends Vehicle(price) {
          println("this 주 생성자 입니다.")
        }

        // super 주 생성자 호출하니?
        // this 주 생성자 입니다.
        val car = new Car(10)

        // super 주 생성자 호출하니?
        val vehicle = new Vehicle(100)
        ```

    - 클래스의 유일한 진입점

## 08. 비공개 필드와 메소드


- private
    - 필드나 메소드를 비공개로 외부에서 접근 불가능하게 만든다.

## 09. 연산자 정의


- 연산자 우선순위
    - 연산자의 첫 번째 문자가 결정한다.

    ```scala
    class Rational(n: Int, d: Int) {
      require(d != 0)

      private val g = gcd(n.abs, d.abs)
      val numer = n / g
      val denom = d / g

      def this(n: Int) = this(n, 1)

      def `+add`(that: Rational): Rational = new Rational(numer * that.denom + that.numer * denom, denom * that.denom)

      def `*multiply`(that: Rational): Rational = new Rational(numer * that.numer, denom * that.denom)

      override def toString = n + "/" + d

      private def gcd(a: Int, b: Int): Int =
        if (b == 0) a else gcd(b, a % b)
    }

    val x1 = new Rational(1, 2)
    val x2 = new Rational(2, 3)

    // 5/6
    x1 `+add` x1 `*multiply` x2
    x1 `+add` (x1 `*multiply` x2)

    // 2/3
    (x1 `+add` x1) `*multiply` x2
    ```

## 10. 스칼라의 식별자


1. 영숫자 식별자
    - 문자나 밑줄로 시작. 두번째 글자부터 문자, 숫자, 밑줄 가능.
    - $는 충돌 가능성이 있어 식별자로 쓰면 안된다.
    - 식별자는 camel-case
        - 밑줄이 스칼라 코드에서는 식별자 외의 용도로 쓰이기 때문.
        - 필드, 메소드 인자, 지역 변수, 함수: 소문자로 시작
        - 클래스, 트레이트: 대문자로 시작
        - 상수
            - 단순 val 만 의미하지 않는다. val 은 여전히 변수다.
            - 코드 내의 쓰임만 보고 의미를 파악하기 어려운 값
            - 자바: 모두 대문자로 표기, 단어 사이는 밑줄로 구분
            - 스칼라: 첫 글자만 대문자로 표기
2. 연산자 식별자
    - 하나 이상의 연산자 문자로 이뤄져 있다.
    - 스칼라 컴파일러는 내부적으로 $를 사용해 연산자 식별자를 해체하여 적합한 자바 식별자로 다시 만드는 작업을 수행한다.

        ```scala
        :->
        $colon$minus$greater
        ```

    - 자바 코드에서 해당 식별자에 접근하려면 내부 변환 이름을 알고 있어야 함.
3. 혼합 식별자
    - 영문자와 숫자로 이뤄진 식별자 뒤에 밑줄이 오고, 그 뒤에 연산자 식별자가 온다.
    - 스칼라 컴파일러가 프로퍼티를 지원하기 위해 내부적으로 생성한다.

        ```scala
        unary_+  // 단항 연산자인 +를 정의하는 메소드 이름
        ```

4. 리터럴 식별자
    - 역따옴표로 둘러싼 임의의 문자열
    - 예시
        - 자바 Thread.yield() 의 yield 는 스칼라 예약어. 아래와 같이 사용 가능하다.

            ```scala
            Thread.`yield`()
            ```

## 12. 암시적 타입 변환


- implicit
    - 컴파일러에게 몇몇 상황에 해당 메소드를 활용해 변환을 수행하라고 알려준다.
    - 해당 스코프 안에 변환 메소드가 존재해야 한다.
    - 암시적 타입 변환 메소드를 클래스 내부에 정의하면 인터프리터의 스코프에는 존재하지 않아 변환이 이뤄지지 않는다.

https://www.notion.so/Chapter-6-d5a2c7f5c4b648d28827cca7dd239365
