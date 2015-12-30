package bestcoders.grid;


import java.util.*;


public class World{

  final static int[][] grid = new int[32][32];

  public static void main(final String[] ARGV) { 
    if(ARGV.length < 1) { 
      System.err.println("Expecting the number of players");
      System.exit(-1);
    }
    final Random rand = new java.util.Random();
    final int numberOfPlayers = Integer.valueOf(ARGV[0]).intValue();

    final List<Cycle> players = new ArrayList<Cycle>(numberOfPlayers);
    for( int i = 0 ; i < numberOfPlayers; i++){
      final int col = rand.nextInt(32);
      final int row = rand.nextInt(32);
      players.add(new Cycle(grid, col, row, i+1));
    }

    for(int turn = 10 ; --turn >= 0 ; ) { 
      for(final Cycle c : players) { 
        c.move();
      }
    
      showGrid();
    
    }

  }

  private static void showGrid(){
    for (int r = 0 ; r < 32 ; r++){
      System.out.print('-');
    }
    System.out.println("|");

    for (int c = 0 ; c < 32 ; c++){
      for (int r = 0 ; r < 32 ; r++){
        System.out.print(grid[c][r]);
      }
      System.out.println("|");
    }
    for (int r = 0 ; r < 32 ; r++){
      System.out.print('-');
    }
    System.out.println("|");
    System.out.println("\n");

  }


}
