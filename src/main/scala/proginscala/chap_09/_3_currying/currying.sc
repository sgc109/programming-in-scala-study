// normal
def plainSum(x: Int, y: Int) = x + y
// val f1 = plainSum(1) _  // Unspecified value parameter: y

// currying
def curriedSum(x: Int)(y: Int) = x + y
def first(x: Int) = (y: Int) => x + y

val f2 = curriedSum(1) _
val f3 = first(1)

f2(3)
f3(3)
