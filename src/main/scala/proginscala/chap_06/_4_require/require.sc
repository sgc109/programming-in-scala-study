class Rational(n: Int, d: Int) {
  require(d != 0)

  override def toString = n + "/" + d
}

val x = new Rational(1, 0)
