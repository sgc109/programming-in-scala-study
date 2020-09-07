class Vehicle(price: Int) {
  println("super 주 생성자 호출하니?")
}

class Car(price: Int) extends Vehicle(price) {
  println("this 주 생성자 입니다.")
}

val car = new Car(10)
val vehicle = new Vehicle(100)
