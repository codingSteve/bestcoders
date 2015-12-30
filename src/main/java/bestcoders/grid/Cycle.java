
package bestcoders.grid;

public class Cycle{
  final static char EMPTY = '\0';

  final char[][] grid;
  final int player;
  int[] direction = new int[]{0,1}; //initalize down
  int col;
  int row;
  final boolean locks;

  final int ROWS;
  final int COLS;

  final java.util.Random rand = new java.util.Random();

  Cycle(final char[][] grid, final int c, final int r, final int player, final boolean locks) {
    this.grid       = grid;
    this.col        = c;
    this.row        = r;
    this.player     = player;
    this.locks      = locks;

    // lay initial placement
    this.grid[c][r] = '.';

    // set grid limits
    this.COLS = grid.length;
    this.ROWS = grid[0].length;
  }

  public void move(){
    
    final Object token = ((this.locks) ? grid : new Object() );
    synchronized(token){
      final int[][] freeSpaces = look();
      int[] newTarget = new int[]{-1,-1};
     
      int tries = 100;
      while ((newTarget[0] == -1 ) && --tries > 0) {
        final int direction = rand.nextInt(4);
        newTarget = freeSpaces[direction];
      }
      System.err.println(player + ": newTarget == (" + newTarget[0] + "," + newTarget[1] + ")");
     
      if (tries == 0 ) {
        System.err.println(player + ": blocked");
        grid[col][row] = 'x';
        throw new RuntimeException(player + ": blocked");
      }

      direction[0] = newTarget[0] - this.col;
      direction[1] = newTarget[1] - this.row;


      this.col = newTarget[0];
      this.row = newTarget[1];
      grid[col][row] = (char) ( 48 + this.player);
    }

  }


  private int[][] look() { 
    final int[][] freeSpaces = new int[][] {
      {-1,-1},
      {-1,-1},
      {-1,-1},
      {-1,-1}
    };

    System.err.println(player + ": currently at (" + col + ","+row+")");
    //Check current direction first 
    try{ 
      if (grid[col + direction[0]][row + direction[1]] == EMPTY) {
        freeSpaces[0][0] = col + direction[0];
        freeSpaces[0][1] = row + direction[1];
      }
    }
    catch(Exception e) {
    }

    if (rand.nextInt(100) <= 1 || freeSpaces[0][0] == -1) {
      lookProperly(freeSpaces);
    }

    return freeSpaces;
  }

  private int[][] lookProperly(int[][] freeSpaces){
    System.err.println(player+ ": About to look around");

    if(col != 0) { 
      if (grid[col-1][row] == EMPTY ) {
        System.err.println(player +": L available");

        freeSpaces[0][0] = col-1;
        freeSpaces[0][1] = row;
      }
    }
    
    if(col != COLS-1) { 
      if (grid[col+1][row] == EMPTY ) {
        System.err.println(player +": R available");

        freeSpaces[1][0] = col+1;
        freeSpaces[1][1] = row;
      }
    }
   
    if(row != 0) { 
      if (grid[col][row-1] == EMPTY ) {
        System.err.println(player +": U available");
        freeSpaces[2][0] = col;
        freeSpaces[2][1] = row-1;
      }
    }
    
    if(row != ROWS-1) { 
      if (grid[col][row+1] == EMPTY ) {
        System.err.println(player +": D available");
        freeSpaces[3][0] = col;
        freeSpaces[3][1] = row+1;
      }
    }
    return freeSpaces;
  }
}
