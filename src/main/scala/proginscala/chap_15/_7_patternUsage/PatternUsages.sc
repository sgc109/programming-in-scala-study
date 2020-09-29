import proginscala.chap_15._7_patternUsage.GetChecksum

val myTuple = (123, "abc")
val (n, s) = myTuple
//n = 3

var sum: Int = 0
def receive = {
  case byte: Int => sum += byte
  case GetChecksum(requester) => val checksum = ~(sum & 0xFF) + 1
}

val results = List(Some("apple"), None, Some("orange"))
for (Some(fruit) <- results) println(fruit)
