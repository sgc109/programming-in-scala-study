def show(x: Option[String]) = x match {
  case Some(s) => s
  case None => "?"
}
val company = Map("Jenny" -> "Amazon", "Sungho" -> "Google", "Probe" -> "Facebook")

show(company get "Jenny")
show(company get "Sungho")
show(company get "Probe")
