package me.chuwy.otusbats


trait Show[A] {
  def show(a: A): String
}

object Show {

  // 1.1 Instances (`Int`, `String`, `Boolean`)
  implicit val intShow: Show[Int] = fromJvm

  implicit val stringShow: Show[String] = fromFunction(a => a)

  implicit val boolShow: Show[Boolean] = fromFunction(a => if(a) "true" else "false!!!")



  // 1.2 Instances with conditional implicit

  implicit def listShow[A](implicit ev: Show[A]): Show[List[A]] = new Show[List[A]] {
    override def show(a: List[A]): String = a.map(ev.show).mkString
  }



  // 2. Summoner (apply)
  def apply[A](implicit ev: Show[A]): Show[A] =  ev

  // 3. Syntax extensions

  implicit class ShowOps[A](a: A) {
    def show(implicit ev: Show[A]): String = ev.show(a)
  }

  // 4. Helper constructors

  /** Just use JVM `toString` implementation, available on every object */
  def fromJvm[A]: Show[A] = (a: A) => a.toString

  /** Provide a custom function to avoid `new Show { ... }` machinery */
  def fromFunction[A](f: A => String): Show[A] = (a: A) => f(a)
}
