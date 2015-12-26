
import java.util.*;

public class Mods { 
  public static void main(final String[] ARGV) { 
    final int size  = (int) Math.pow(2,10);
    final int mask  = size -1;
    final int counter = 1934;

    final List<Integer> l = new ArrayList<Integer>(size);
    for(int i = size; --i >=0;) { l.add(null);}


    for (int c = 100 ; --c >=0;){
      final long startMod = new java.util.Date().getTime();
      for (int i = 10000; --i >=0;) { 
        l.set(mo(i, size), i);
      }
      System.out.println("Mod took " + (new java.util.Date().getTime() - startMod));
  
      final long startMask = new java.util.Date().getTime();
      for (int i = 10000; --i >=0;) { 
        l.set(mo(i, mask), i);
      }
      System.out.println("mask took " + (new java.util.Date().getTime() - startMask));
    }


  }

  static public int mo(final int counter , final int size){
    return counter % size;
  }
    
  static public int ma(final int counter, final int mask) {
    return counter & mask;
  }
}
