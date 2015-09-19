

import java.util.stream.*;
import java.util.List;
import java.util.Arrays;

class Lambdas { 
  void filterUsingLambdas(final List<Integer>l) { 
    Stream<Integer> filtered = l.stream().filter(x -> x < 1);

    filtered.map(x -> {
      System.out.println(x);
      return x;
    }
    );
  }

  void filterUsingIf(final List<Integer> l){
    final Integer threshold = new Integer(1);
    for (final Integer i : l ) {
      if (threshold.compareTo(i) < 0){
        System.out.println(i);
      }
    }
  }



}
