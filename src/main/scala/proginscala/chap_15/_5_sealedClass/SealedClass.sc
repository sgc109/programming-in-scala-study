sealed abstract class Expr

case class Var(name: String) extends Expr

case class Number(num: Double) extends Expr

case class UnOp(operator: String, arg: Expr) extends Expr

case class BinOp(operator: String, left: Expr, right: Expr) extends Expr

def describe(e: Expr): String = e match {
  case Number(_) => "number"
  case Var(_) => "variable"
  case _ => throw new RuntimeException
}

def describe2(e: Expr): String = (e: @unchecked) match {
  case Number(_) => "number"
  case Var(_) => "variable"
}
