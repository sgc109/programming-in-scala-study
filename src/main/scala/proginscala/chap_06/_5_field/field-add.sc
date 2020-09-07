class Rational(n: Int, d: Int) {
  require(d != 0)

  val numer: Int = n
  val denom: Int = d

  override def toString = n + "/" + d

  def add(that: Rational): Rational = new Rational(numer * that.denom + that.numer * denom, denom * that.denom)
}

val x1 = new Rational(1, 3)
val x2 = new Rational(1, 4)

x1 add x2
x1.add(x2)
