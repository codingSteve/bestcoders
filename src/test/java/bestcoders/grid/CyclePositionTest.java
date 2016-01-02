
package bestcoders.grid;

import org.junit.*;
import static org.junit.Assert.*;


public class CyclePositionTest {
  final static char EMPTY = '\0';

  @Test
  public void testMove(){

    final char[][] grid = new char[][] {
      {EMPTY,EMPTY},
      {'1'  ,'1'}
    };

    final int playerNumber = 1;
    final Cycle player = new Cycle(grid, 0,0, playerNumber, true);

    player.move();

    assertEquals((char) 48+playerNumber, (char) grid[1][0]);
    


  }
}
