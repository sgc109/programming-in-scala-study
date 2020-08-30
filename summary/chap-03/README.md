# Chapter 3. 스칼라 두 번째 걸음


## 01. 7단계: 배열에 타입 파라미터를 지정해보자


- new: 객체를 인스턴스화 할 수 있다.
    - 클래스의 인스턴스를 만들 수 있고, 객체를 인스턴스화할 때 값과 타입을 파라미터로 넘길 수 있다.
    - 파라미터화
        - 인스턴스를 생성할 때 그 인스턴스를 '설정한다'는 뜻

- 배열을 타입으로 파라미터화 하기

    ```scala
    val greetStrings = new Array[String](3)

    greetStrings(0) = "Hello"
    greetStrings(1) = ", "
    greetStrings(2) = "world!\n"

    for (i <- 0 to 2)
    	print(greetStrings(i))
    ```

    - 스칼라에서는 배열 인덱스를 괄호에 넣어서 배열 원소에 접근.
    - val 변수를 재할당할 수는 없지만, 그 변수가 나타내는 객체는 잠재적으로 변경 가능하다.

- 스칼라에는 전통적인 의미의 연산자가 없다.
    - 메소드가 파라미터를 하나만 요구하는 경우, 그 메소드를 점과 괄호 없이 호출할 수 있다.

        ```scala
        1 + 2
        (1).+(2)
        ```

    - 스칼라에서 왜 괄호를 사용해 배열에 접근할 수 있는가?
        - 스칼라 배열도 평범한 클래스의 인스턴스다.
        - 변수 뒤에 하나 이상의 값을 괄호로 둘러싸서 호출하면
            - 스칼라는 그 코드를 변수에 대해 apply 메소드를 호출하는 것으로 바꾼다.

            ```scala
            greetStrings(i)
            greetStrings.apply(i)
            ```

        - 원소에 접근하는 것은 일반적인 메소드 호출과 같다.
    - 변수 뒤에 괄호로 둘러싼 인자들이 있는 표현식에 할당하면
        - 컴파일러는 괄호 안의 인자와 등호 오른쪽의 값을 모두 인자로 넣어서 update 메소드 호출.

            ```scala
            greetStrings(0) = "Hello"
            greetStrings.update(0, "Hello")
            ```

    - 자바는 원시타입과 그를 감싸는 래퍼타입이 다르고, 배열과 일반적인 객체가 다르다.
        - 이런 균일성에도 불구하고 심각한 성능상 비용이 들지 않는다.
        - 스칼라 컴파일러는 컴파일한 코드에서 가능하면 자바 배열, 원시 타입, 네이티브 연산을 사용한다.

- 배열의 apply 팩토리 메소드

    ```scala
    val numNames = Array("zero", "one", "two")
    val numNames2 = Array.apply("zero", "one", "two")
    ```

    - 새로운 배열을 만들어서 반환한다.
    - 임의 개수의 인자를 받을 수 있으며, Array 의 동반 객체에 정의가 들어있다.
    - 자바러는 Array 클래스의 정적 메소드 호출로 생각할 수도 있지만,
        - To be continued..

## 02. 8단계: 리스트를 사용해보자


- 함수형 프로그래밍은 메소드에 부수 효과가 없어야 한다.
    - 메소드의 유일한 동작은 계산을 해서 값을 반환하는 것뿐이어야 한다.
        - 메소드가 덜 얼기설기 얽히기 때문에, 더 많이 신뢰할 수 있고 재사용할 수 있다.
        - (정적 타입 언어에서는) 어떤 메소드에 들어가고 나오는 모든 것을 타입 검사기가 검사하기 때문에 논리적인 오류가 타입 오류라는 형태로 드러날 확률이 더 높아진다.

- 스칼라의 리스트 scala.List
    - 변경 불가능하다.

    - 자바의 java.util.List 타입과 다르다.
        - 자바의 리스트는 원소를 바꿀 수 있다.
    - 리스트 내용을 변경하는 것 같아 보이는 메소드를 호출하면
        - 새 값을 갖는 리스트를 새로 만들어서 반환한다.

- ::: 메소드

    ```scala
    val oneTwo = List(1, 2)
    val threeFour = List(3, 4)
    val oneTwoThreeFour = oneTwo ::: threeFour // List(1, 2, 3, 4)
    ```

    - 두 리스트를 이어붙인다.

- :: 연산자
    - 콘즈(cons): 새 원소를 기존 리스트의 맨 앞에 추가한 새 리스트를 반환한다.

- List 메소드와 사용법

    ```scala
    // thrill: List[String] = List(Will, fill, until)
    var thrill = "Will" :: "fill" :: "until" :: Nil

    // res0: List[String] = List(a, b, c, d)
    List("a", "b") ::: List("c", "d")

    // res1: String = until
    thrill(2)

    // res2: List[String] = List(until)
    thrill.drop(2)

    // res3: List[String] = List(Will)
    thrill.dropRight(2)

    // res4: Boolean = true
    thrill.forall(s => s.endsWith("l"))

    // res5: String = Will
    thrill.head

    // res6: List[String] = List(Will, fill)
    thrill.init

    // res7: String = until
    thrill.last

    // res8: String = Will, fill, until
    thrill.mkString(", ")

    // res9: List[String] = List(until)
    thrill.filterNot(s => s.length == 4)

    // res10: List[String] = List(until, fill, Will)
    thrill.reverse

    // scala 2.8에서 List 의 sort 메소드는 deprecated 됨.
    // res11: List[String] = List(fill, until, Will)
    thrill.sortWith((s, t) => s.charAt(0).toLower < t.charAt(0).toLower)

    // res12: List[String] = List(fill, until)
    thrill.tail
    ```

## 03. 9단계: 튜플을 사용해보자


- 튜플
    - 리스트와 마찬가지로 변경 불가능하지만 튜플에는 각기 다른 타입의 원소를 넣을 수 있다.
    - 메소드에서 여러 객체를 반환해야 하는 경우 유용.
    - 새 튜플을 인스턴스화해서 객체를 담으려면, 넣을 객체들을 쉼표로 구분해 괄호로 둘러싸면 된다.

        ```scala
        val pair = (99, "Luftballons")
        println(pair._1)
        println(pair._2)  // Tuple2[Int, String] 타입
        ```

    - 튜플의 실제 타입은 내부에 들어있는 원소의 개수와 각 타입에 따라 바뀐다.
    - pair(0) 처럼 접근할 수 없는 이유
        - List 의 apply 메소드는 항상 동일 타입의 객체를 반환.
        - 튜플의 각 원소들은 타입이 각기 다를 수 있다.
        - 인덱스가 1부터 시작하는 이유는 전통적으로 튜플의 인덱스를 1부터 세어왔기 때문.

## 04. 10단계: 집합과 맵을 써보자


- 배열은 항상 변경 가능 vs 리스트는 항상 변경 불가능
- 집합, 맵: 변경 가능한 것과 변경 불가능한 것 모두 제공.
    - 집합
        - 집합을 위한 기반 트레이트가 있고, 두 서브트레이트(변경 가능 집합, 변경 불가능 집합)가 있다.
        - 변경 불가능한 집합

            ```scala
            var jetSet = Set("Boeing", "Airbus")
            jetSet += "Lear"
            println(jetSet.contains("Cessna"))
            ```

        - 변경 가능한 집합

            ```scala
            import scala.collection.mutable

            var jetSet = mutable.Set("Boeing", "Airbus")
            jetSet += "Lear"
            ```

        - += 메소드
            - 변경 불가능한 집합: 아래 코드를 짧게 적은 것

                ```scala
                jetSet = jetSet + "Lear"
                ```

            - 변경 가능한 집합: += 메소드를 제공한다. 집합에 인자를 추가한다.

                ```scala
                jetSet += "Lear"
                jetSet.+=("Lear")
                ```

    - 맵
        - 변경 불가능한 맵

            ```scala
            val romanNumeral = Map(1 -> "I", 2 -> "II")
            println(romanNumeral(0))
            ```

        - 변경 가능한 맵

            ```scala
            import scala.collection.mutable

            val treasureMap = mutable.Map[Int, String]()
            treasureMap += (1 -> "Go To Island")
            treasureMap += (2 -> "Find big X on ground.")
            println(treasureMap(2))
            ```

        - -> 메소드
            - 해당 객체를 키로 하고 인자로 받은 다른 객체를 값으로 하는 원소가 2개인 튜플을 만든다.

            ```scala
            1 -> "Go to island."
            (1).->("Go to island.")
            ```

## 05. 11단계: 함수형 스타일을 인식하는 법을 배우자


- 코드에 var 가 있다면 아마도 명령형 스타일일 것이다.

    ```scala
    def printArgs(args: Array[String]): Unit = {
    	var i = 0
    	while (i < args.length) {
    		println(args(i))
    		i += 1
    	}
    }
    ```

    ```scala
    def printArgs(args: Array[String]): Unit = {
    	for (arg <- args) {
    		println(args)
    	}
    }
    ```

    ```scala
    def printArgs(args: Array[String]): Unit = {
    	args.foreach(println)
    }
    ```

    - 마지막 리팩토링 메소드도 완전히 함수형 코드는 아니다.
        - 내부에 부수 효과가 있기 때문.
        - 부수 효과가 없는 메소드는 테스트하기가 쉽다.

        ```scala
        def formatArgs(args: Array[String]) = args.mkString("\n")

        println(formatArgs(args))

        // Test
        val res = formatArgs(Array("zero", "one"))
        assert(res == "zero\none")
        ```

https://www.notion.so/Chapter-3-8422c0ccb77a47d19e6cd2ba915a0123
