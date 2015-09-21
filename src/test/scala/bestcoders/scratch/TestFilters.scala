import collection.mutable.Stack
import org.scalatest._


class TestFilters extends FlatSpec with Matchers {
  "A list" should "be limited" in {
    val f:Filter = new Filter()
    val l:List[Int] = List(1,2,3,4,5,0,-1,-2,-3,-4,-5,-6)
    val limit:Int   = 3

    val expected    = List(1,2,0,-1,-2,-3,-4,-5,-6)
    val result      = f.capListAt(l,limit)

    val expectedMax = 2
    val actualMax   = result.max

    actualMax should be (expectedMax)


  }
}
