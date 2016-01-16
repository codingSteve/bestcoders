

object P extends App {
  def p = new Powersets()
  
  println(p.sets(List()))
  println(p.sets(List(1)))
  println(p.sets(List(1,2)))
  println(p.sets(List(1,2,3)))
}

class Powersets {
  def emptySet:Set[Int] = Set()

  def sets(args: List[Int]): Set[Set[Int]] = { 
     args match{
       case x :: xs => sets(xs).flatMap(s1 => List(s1, s1 + x))
       case nil     => Set(emptySet)
     }
  }
}
