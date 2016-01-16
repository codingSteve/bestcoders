

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
      args match {
        case x :: xs => {
          val subsets = sets(xs)
          val headOption = Set(emptySet, Set(x))
          
          //Cartesian product
          for {
            s1 <- headOption
            s2 <- subsets
          } yield s1 ++ s2
        }
        case nil     => Set(emptySet)
      }
      
  }

}
