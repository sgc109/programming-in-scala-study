# Chapter 9. 흐름 제어 추상화


## 01. 코드 중복 줄이기


- 고차 함수(higher-order function)
    - 함수를 인자로 받는 함수
    - 클로저가 자유 변수(ex> query) 포획하게 하여 중복 코드를 줄일 수 있다.

        ```scala
        object ListMatcher {
          private def lists = List("gift dev", "jenny", "sungho", "probe")

          private def listsMatching(matcher: String => Boolean) = for (l <- lists; if matcher(l)) yield l

          def listsEnding(query: String) = listsMatching(_.endsWith(query))
          def listsContaining(query: String) = listsMatching(_.contains(query))
          def listsRegex(query: String) = listsMatching(_.matches(query))
        }

        // res0: List[String] = List(sungho)
        ListMatcher.listsEnding("o")
        // res1: List[String] = List(probe)
        ListMatcher.listsContaining("p")
        //res2: List[String] = List(gift dev, jenny)
        ListMatcher.listsRegex(".*y|g.*")
        ```

## 03. 커링


- 커링(currying)
    - 커링한 함수는 인자 목록이 여럿이다.

        ```scala
        // normal
        def plainSum(x: Int, y: Int) = x + y
        // val f1 = plainSum(1) _  // Unspecified value parameter: y

        // currying
        def curriedSum(x: Int)(y: Int) = x + y

        def first(x: Int) = (y: Int) => x + y  // curriedSum 같은 일을 한다.
        val f3 = first(1)
        f3(3)
        ```

    - 부분 적용 함수로 사용할 수 있다.

        ```scala
        val f2 = curriedSum(1) _
        f2(3)
        ```

## 04. 새로운 제어 구조 작성


- 인자 목록을 감쌀 때 소괄호가 아닌 중괄호 사용
    - 스칼라에서는 인자를 단 하나만 전달하는 경우 소괄호 대신 중괄호를 사용할 수 있다.

        ```scala
        println("Hello, world!")
        println { "Hello, world!" }
        ```

    - 중괄호 내부에 함수 리터럴을 사용하도록 하기 위해 사용(loan pattern)

        ```scala
        def lists = List("gift dev", "jenny", "sungho", "probe")

        def withListsMatcher(lists: List[String])(matcher: String => Boolean) = {
          for (l <- lists if matcher(l)) yield l
        }

        // res0: List[String] = List(gift dev, jenny)
        withListsMatcher(lists) {
          _.matches(".*y|g.*")
        }
        ```

## 05. 이름에 의한 호출 파라미터


- 이름에 의한 호출 파라미터(by-name parameter)
    - 중괄호 사이에 값을 전달하는 내용이 없는 형태로 구현하고 싶을 때 사용
    - 이름으로 전달하는 변수, 필드 는 존재 x
    - 빈 파라미터 목록 () 생략은 파라미터에서만 사용할 수 있다.

        ```scala
        def byNameAssert(predicate: => Boolean) =
          if (assertionsEnabled && !predicate)
            throw new AssertionError
        ```

https://www.notion.so/Chapter-9-5ef3c7617eec4f9388c6113813769ce4
