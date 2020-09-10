# Chapter 5 - 기본 타입과 연산

## 5-1 기본 타입
기가막히게 자바랑 같다.

* 정수 타입
  * Int
  * Long
  * Short
  * Byte
  * Char

* 수 타입
  * Float
  * Double

* Boolean

## 5-2 리터럴

###  정수 리터럴

* 10진 리터럴
```scala
scala> val dec1 = 31
val dec1: Int = 31

scala> val dec2 = 255
val dec2: Int = 255

scala> val dec3 = 20
val dec3: Int = 20
```
* 16진 리터럴 
```scala
scala> val hex = 0x5
val hex: Int = 5

scala> val hex2 = 0x00FF
val hex2: Int = 255

scala> val magic = 0xcafebabe
val magic: Int = -889275714
```

* Long

소문자는 혼구녕이 난다.(헷갈리나봄)
```scala
scala> val prog = 0XCAFEBABEL
val prog: Long = 3405691582

scala> val tower = 35L
val tower: Long = 35

scala> val of = 31l
                  ^
       warning: Lowercase el for long is not recommended because it is easy to confuse with numeral 1; use uppercase L instead
val of: Long = 31
```

* Type
```scala
scala> val little: Short = 367
val little: Short = 367

scala> val littler: Byte = 38
val littler: Byte = 38
```

### 부동소수점 리터럴
* Double
```scala
scala> val big = 1.2345
val big: Double = 1.2345

scala> val bigger = 1.2345e1
val bigger: Double = 12.345

scala> val biggerStill = 123E45
val biggerStill: Double = 1.23E47
```

* Float
```scala
scala> val little = 1.2345F
val little: Float = 1.2345

scala> val littleBigger = 3e5f
val littleBigger: Float = 300000.0
```

### 문자 리터럴
* Simple
```scala
scala> val a = 'A'
val a: Char = A
```

* Unicode code point
```scala
scala> val d = '\u0041'
val d: Char = A

scala> val d = '\u0044'
val d: Char = D
```

* identifier
무슨 옵션을 켜야하나봄 (이렇게 쓰는건 혼나니 스킵)
```
error: postfix operator u0041 needs to be enabled
by making the implicit value scala.language.postfixOps visible.
This can be achieved by adding the import clause 'import scala.language.postfixOps'
or by setting the compiler option -language:postfixOps.
See the Scaladoc for value scala.language.postfixOps for a discussion
why the feature needs to be explicitly enabled.
```

* escape sequence
```scala
scala> val backslash = '\\'
val backslash: Char = \
```
  * \n : 줄바꿈(\u000A)
  * \b : 백스페이스(\u0008)
  * \t : 탭(\u0009)
  * \f : 페이지 넘김(\u000C)
  * \r : 줄 맨 앞으로(\u000D)
  * " : 큰따옴표(\u0022)
  * ' : 작은따옴표(\u0027)
  * \ : 역슬래스(\u005C)
  
 ### 문자열 리터럴
 ```scala
scala> val hello = "hello"
val hello: String = hello

scala> val escapes = "\\\"\'"
val escapes: String = \"'
```

* multi line 
```scala
scala> println("""Welcome to Ultamix 3000.
     | Type "HELP" for help.""".stripMargin)
Welcome to Ultamix 3000.
Type "HELP" for help.
```

### 심볼 리터럴
intern 된다 메모리 올라감
```scala
scala> val s = 'aSymbol
               ^
       warning: symbol literal is deprecated; use Symbol("aSymbol") instead
val s: Symbol = Symbol(aSymbol)

scala> val s = Symbol("aSymbol")
val s: Symbol = Symbol(aSymbol)
```

### Boolean 리터럴
```scala
scala> val bool = true
val bool: Boolean = true

scala> val fool = false
val fool: Boolean = false
```

이제 리터럴 전문가다

## 5-3 문자열 인터폴레이션
* string interpolator
```scala
scala> val name = "reader"
val name: String = reader

scala> println(s"Hello, $name!")
Hello, reader!

scala> s"The answer is ${6 * 7}."
val res9: String = The answer is 42.
```

* raw
```scala
scala> println(raw"No\\\\escape!")
No\\\\escape!
```

* format
```scala
scala> f"${math.Pi}%.5f"
val res14: String = 3.14159

scala> val pi = "Pi"
val pi: String = Pi

scala> f"$pi is approximately ${math.Pi}%.8f."
val res13: String = Pi is approximately 3.14159265.
```

## 5-4 연산자는 메소드다
* method
```scala
scala> val sum = 1 + 2
val sum: Int = 3

scala> val sumMore = 1.+(2)
val sumMore: Int = 3
```

* overload
```scala
scala> val longSum = 1 + 2L
val longSum: Long = 3
```

* infix operator
arguments 2개는 혼난다
```scala
scala> val s = "Hello, world"
val s: String = Hello, world

scala> s indexOf 'o'
val res15: Int = 4

scala> s indexOf ('o', 5)
         ^
       warning: multiarg infix syntax looks like a tuple and will be deprecated
val res16: Int = 8
```

* unary [발음](https://www.google.com/search?q=unary+pronounce&oq=unary+pron&aqs=chrome.1.69i57j0l6.4703j0j7&sourceid=chrome&ie=UTF-8)
```scala
scala> -2.0
val res17: Double = -2.0

scala> (2.0).unary_-
val res18: Double = -2.0
```

* prefix operator
  * "+ - ! ~" 이것만 가능
  
* postfix operator
```scala
scala> val s = "Hello, world!"
val s: String = Hello, world!

scala> s.toLowerCase
val res19: String = hello, world!

scala> s toLowerCase
         ^
       error: postfix operator toLowerCase needs to be enabled
```

## 5-5 산술연산
```scala
scala> 1.2 + 2.3
val res21: Double = 3.5

scala> 3 - 1
val res22: Int = 2

scala> 'b' - 'a'
val res23: Int = 1

scala> 2L * 3L
val res24: Long = 6

scala> 11 / 4
val res25: Int = 2

scala> 11 % 4
val res26: Int = 3

scala> 11.0f / 4.0f
val res27: Float = 2.75

scala> 11.0 % 4.0
val res28: Double = 3.0
```

* IEEE 754
```scala
scala> math.IEEEremainder(11.0, 4.0)
val res32: Double = -1.0
```

* unary +, - operator
```scala
scala> val neg = 1 + -3
val neg: Int = -2

scala> val y = +3
val y: Int = 3

scala> -neg
val res33: Int = 2
```

## 5-6 관계 연산과 논리 연산
* 관계연산
```scala
scala> 1 > 2
val res34: Boolean = false

scala> 1 < 2
val res35: Boolean = true

scala> 1.0 <= 1.0
val res36: Boolean = true

scala> 3.5f >= 3.6f
val res37: Boolean = false

scala> 'a' >= 'A'
val res38: Boolean = true

scala> val untrue = !true
val untrue: Boolean = false
```

* 논리곱 논리합

```scala
scala> val toBe = true
val toBe: Boolean = true

scala> val question = toBe || !toBe
val question: Boolean = true

scala> val paradox = toBe && !toBe
val paradox: Boolean = false
```

쇼트 서킷 : 필요한 부분 까지만 계산한다

```scala
scala> def salt() = { println("salt"); false }
def salt(): Boolean

scala> def pepper() = { println("pepper"); true }
def pepper(): Boolean

scala> pepper() && salt()
pepper
salt
val res39: Boolean = false

scala> salt() && pepper()
salt
val res40: Boolean = false
```

모두 평가하기
```scala
scala> salt() & pepper()
salt
pepper
val res41: Boolean = false
```

## 5-7 비트 연산
```scala
scala> 1 & 2
val res42: Int = 0

scala> 1 | 2
val res43: Int = 3

scala> 1 ^ 3
val res44: Int = 2

scala> ~1
val res45: Int = -2
```

* shift
```scala
scala> -1 >> 31 // 1 로 채움
val res46: Int = -1

scala> -1 >>> 31 // 0 으로 채움
val res47: Int = 1

scala> 1 << 2 // 0 으로 채움
val res48: Int = 4
```

## 5-8 객체 동일성
equals 를 자동 호출
```scala
scala> 1 == 2
val res51: Boolean = false

scala> 1 != 2
val res52: Boolean = true

scala> 2 == 2
val res53: Boolean = true
```

```scala
scala> List(1, 2, 3) == List(1, 2, 3)
val res54: Boolean = true

scala> List(1, 2, 3) == List(4, 5, 6)
val res55: Boolean = false

scala> 1 == 1.0
val res56: Boolean = true

scala> List(1, 2, 3) == "hello"
val res57: Boolean = false
```

null safe
```scala
scala> List(1, 2, 3) == null
val res58: Boolean = false

scala> null == List(1, 2, 3)
val res59: Boolean = false
```

```scala
scala> ("he" + "llo") == "hello"
val res60: Boolean = true
```

## 5-9 연산자 우선순위와 결합 법칙
* 우선순위 판단 순서
  * (그 밖의 모든 특수 문자)
  * */%
  * +-
  * :
  * =! <> &
  * ˆ
  * |
  * (모든 문자)
  * (모든 할당 연산자)
  
* 할당 연산자의 경우는 예외 
```scala
x *= y + 1

x *= (y + 1)
```
* 메소드가 ':' 으로 끝나면 오른쪽에서 왼쪽 else 왼쪽에서 오른쪽
* 대부분은 괄호쳐라.. 

## 풍부한 래퍼 자랑
* implicit conversion 기법 활용
* 각종 Rich Class