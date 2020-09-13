def lists = List("gift dev", "jenny", "sungho", "probe")

def withListsMatcher(lists: List[String])(matcher: String => Boolean) = {
  for (l <- lists if matcher(l)) yield l
}

withListsMatcher(lists) {
  _.matches(".*y|g.*")
}
