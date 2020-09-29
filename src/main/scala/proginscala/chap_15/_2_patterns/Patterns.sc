def describe(x: Any) = x match {
  case 5 => "five"
  case Nil => "the empty list"
  case sthElse => "not 5 or Nil, " + sthElse
  case _ => "something else"
}

def chk(expr: Any) = expr match {
  case List(0, _, _) => println("3 element list")
  case List(0, _*) => println("other length list")
  case _ =>
}

def tupleDemo(expr: Any) = expr match {
  case (a, b, c) => println("matched " + a + b + c)
  case _ =>
}

def generalSize(x: Any) = x match {
  case s: String => s.length
  case m: Map[_, _] => m.size
  case _ => -1
}

def isStringArray(x: Any) = x match {
  case a: Array[String] => "yes"
  case _ => "no"
}

// Type erasure test in Array
val as = Array("abc")
isStringArray(as)
val ai = Array(1, 2, 3)
isStringArray(ai)


case class Test(x: String, t: Test)

def variableBinding(expr: Any) = expr match {
  case Test("str", a@Test(_, _)) => "yes, " + a
  case _ => "no"
}
val t1 = Test("str", Test(null, null))
variableBinding(t1)
variableBinding(2)
