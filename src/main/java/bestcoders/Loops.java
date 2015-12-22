


public class Loops {

  public static void main(final String[] ARGV){
    loopOne();
    loopTwo();
  }


  /**
   * Saving one bytecode by comparing to zero.
   *
   * See: https://en.wikipedia.org/wiki/Java_bytecode_instruction_listings
   * See: http://cory.li/bytecode-hacking/
   *
   *         3: iinc          0, -1
   *         6: iload_0
   *         7: iflt          20     // here we only need to load i from #6 as we're counting up
   *        10: getstatic     #4     // Field java/lang/System.out:Ljava/io/PrintStream;
   *        13: iload_0
   *        14: invokevirtual #5     // Method java/io/PrintStream.println:(I)V
   *        17: goto          3
   *
   */
  public static void loopOne(){
    for (int i = 10 ; --i >= 0 ;) { 
      System.out.println(i);
    }
  }


  /**
   * Incurring one extra bytecode by using a normal Loop.
   *
   * See: https://en.wikipedia.org/wiki/Java_bytecode_instruction_listings
   * See: http://cory.li/bytecode-hacking/
   *
   *     2: iload_0
   *     3: bipush        10
   *     5: if_icmpge     21    // here we need to load i from #2 and 10 from #3
   *     8: getstatic     #4    // Field java/lang/System.out:Ljava/io/PrintStream;
   *    11: iload_0
   *    12: invokevirtual #5    // Method java/io/PrintStream.println:(I)V
   *    15: iinc          0, 1
   *    18: goto          2
   *
   */
  public static void loopTwo(){
    for (int i = 0 ; i < 10 ;i++) { 
      System.out.println(i);
    }
  }





}

