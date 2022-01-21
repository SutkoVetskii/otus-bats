package me.chuwy.otusbats

trait Monad[F[_]] extends Functor[F] { self =>
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def point[A](a: A): F[A]

  def flatten[A](fa: F[F[A]]): F[A] = flatMap(fa)(a => a)
}

object Monad {
  implicit val optMonad: Monad[Option] = new Monad[Option] {
    override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa.flatMap(f)

    override def point[A](a: A): Option[A] = Some(a)

    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
  }

  implicit val listMonad: Monad[List] = new Monad[List] {
    override def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] = fa.flatMap(f)

    override def point[A](a: A): List[A] = List(a)

    override def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
  }

  implicit val array: Monad[Array] = new Monad[Array] {
    override def flatMap[A, B](fa: Array[A])(f: A => Array[B]): Array[B] = fa.flatMap(f)

    override def point[A](a: A): Array[A] = Array(a)

    override def map[A, B](fa: Array[A])(f: A => B): Array[B] = fa.map(f)
  }

  def apply[F[_]](implicit ev: Monad[F]): Monad[F] = ev


  implicit class MonadOps[A](a: A){

    def flatten[F[_]](ev: Monad[F]): Monad[F] = a.flatten(ev)
  }
}
