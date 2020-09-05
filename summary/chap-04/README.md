# Chapter 4 - 클래스와 객체

* 객체의 상태를 변경하는 함수, 변수는 private 으로 선언하여 robustness 를 높임
* Scala 의 기본 access modifier 는 public
* Scala 의 메소드 파라미터는 무조건 val 이다. 재할당 시도 시, 컴파일에러 발생함
* return 생략가능
* 하나의 표현식만 있는경우 중괄호 생략가능
* 메소드의 부수효과는 일반적으로
    * 메소드 밖의 상태를 변경하거나,
    * I/O를 수행하는 것으로 정의됨
* 결과값 없이 부수효과만을 위해 실행되는 메소드를 **프로시저**라고 부름
* x
* +y
* 는 x 와 +y 로 파싱하기 때문에,
* (x
* +y)
* 로 입력하거나, 연산자를 줄의 끝에 넣어야 함(Scala 스타일)
* x +
* y
* Scala 는 다음의 경우를 제외하고는 끝에 세미콜론이 있다고 추론한다
    * 줄의 끝이 명령을 끝낼 수 없는 단어로 끝남(중위연산자 등)
    * 줄의 맨 앞이 문장을 시작할 수 없는 단어로 시작함
    * 줄이 괄호나 각괄호 사이에서 끝남
* 스칼라가 자바보다 더 객체지향인 이유는 static 멤버가 없기 때문
* 대신 싱글톤 객체(object)
* 싱글톤 객체의 이름이 어떤 클래스와 같다면
    * 객체를 클래스의 **companion object**,
    * 클래스를 객체의 **companion class** 라고 부름
* 서로는 서로의 private 멤버에 접근 가능
* 싱글톤은 타입을 정의하지 않음
* 싱글톤은 superclass 를 extend 하거나 trait 를 mix in 할 수 있음
* 싱글톤은 인스턴스화할 수 없기 때문에 클래스와 달리 파라미터를 받을 수 없음
* Scala 컴파일러는 싱글톤의 이름뒤에 $ 를 붙여 자동으로 클래스를 만들어 객체를 초기화 함
* 싱글톤 객체의 초기화는 이 객체에 처음 접근할 때 일어남
* companion class 가 없는 singleton object 를 standalone object(독립 객체)라고 함(혹은 standalone singleton object)
* standalone object 는 util method 를 모아두거나 스칼라 애플리케이션의 진입점을 만들 때 활용 가능
* Array[String] 을 유일한 인자로 받고, Unit 을 반환하는 main 이라는 메소드만 있으면 어떤 standalone object 든 애플리케이션의 시작점 역할이 가능
* Scala 에서는 어느 객체에서라도 멤버를 임포트할 수 있음
* Scala 는 항상 암시적으로 java.lang, scala 패키지와 scala.Predef 싱글톤 객체를 임포트함
* Scala 에서는 자바의 public class 와 달리, 안에 담고 있는 클래스나 싱글톤 객체와 파일명을 꼭 같게 만들 필요는 없지만, 스크립트가 아닌이상 같게 만드는걸 권장
* 스크립트는 파일 안에 정의 뿐만 아니라 표현식도 존재하는 파일임
* 스크립트가 아닌데 인터프리터로 실행하면 오류 발생
* 스크립트가 아닌경우 scalac 로 컴파일하여 실행 가능
* scalac 는 초기화 작업 때문에 오래걸림
* fsc(fast Scala compiler) 라는 데몬을 사용하면 더 빠름
* scalac/fsc 를 사용하면 자바 클래스 파일이 생성
* 이제 scala 명령으로 실행 가능
* main 메소드를 정의하는 대신 App 트레이트를 extends 하여 코드 정의 가능. args 로 명령행인자 접근가능
