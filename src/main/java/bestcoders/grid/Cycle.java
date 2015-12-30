
package bestcoders.grid;

public class Cycle{
  final int[][] grid;
  final int player;
  int col;
  int row;

  final java.util.Random rand = new java.util.Random();

  Cycle(final int[][] grid, final int c, final int r, final int player) {
    this.grid   = grid;
    this.col    = c;
    this.row    = r;
    this.player = player;
    this.grid[c][r] = player;
  }

  public void move(){
    final int[][] freeSpaces = look();
    int[] newTarget = new int[]{-1,-1};

    while (newTarget[0] == -1) {
      final int direction = rand.nextInt(4);
      newTarget = freeSpaces[direction];
      System.out.println(player + ": newTarget == ( " + newTarget[0] + "," + newTarget[1] + ")");
    }

    this.col = newTarget[0];
    this.row = newTarget[1];
    grid[col][row] = this.player;

  }


  private int[][] look() { 
    final int[][] freeSpaces = new int[][] {
      {-1,-1},
      {-1,-1},
      {-1,-1},
      {-1,-1}
    };

    System.out.println(player + ": currently at (" + col + ","+row+")");

    if(col != 0) { 
      if (grid[col-1][row] == 0 ) {
        freeSpaces[0][0] = col-1;
        freeSpaces[0][1] = row;
      }
    }
    
    if(col != 31) { 
      if (grid[col+1][row] == 0 ) {
        freeSpaces[1][0] = col+1;
        freeSpaces[1][1] = row;
      }
    }

    if(row != 0) { 
      if (grid[col][row-1] == 0 ) {
        freeSpaces[2][0] = col;
        freeSpaces[2][1] = row-1;
      }
    }
    
    if(row != 31) { 
      if (grid[col][row+1] == 0 ) {
        freeSpaces[3][0] = col;
        freeSpaces[3][1] = row+1;
      }
    }

    return freeSpaces;
  }
}
