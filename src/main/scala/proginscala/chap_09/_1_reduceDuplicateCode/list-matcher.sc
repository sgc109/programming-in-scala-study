object ListMatcher {
  private def lists = List("gift dev", "jenny", "sungho", "probe")

  private def listsMatching(matcher: String => Boolean) = for (l <- lists; if matcher(l)) yield l

  def listsEnding(query: String) = listsMatching(_.endsWith(query))

  def listsContaining(query: String) = listsMatching(_.contains(query))

  def listsRegex(query: String) = listsMatching(_.matches(query))
}

ListMatcher.listsEnding("o")
ListMatcher.listsContaining("p")
ListMatcher.listsRegex(".*y|g.*")
