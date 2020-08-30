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

// scala 2.8에서 List 의 sort 메소드는 deprecated 됨
// res11: List[String] = List(fill, until, Will)
thrill.sortWith((s, t) => s.charAt(0).toLower < t.charAt(0).toLower)

// res12: List[String] = List(fill, until)
thrill.tail
