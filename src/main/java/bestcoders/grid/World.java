package bestcoders.grid;


import java.util.*;
import java.util.concurrent.*;


public class World{

  final static int     COLS =  64;
  final static int     ROWS =  32;

  final static char[][] grid = new char[COLS][ROWS];

  public static void main(final String[] ARGV) { 
    for (int i = 0 ; i < ARGV.length ; i++) {
      System.out.print(ARGV[i] + " ");
    }
    System.out.println();

    if(ARGV.length < 1) { 
      System.err.println("Expecting the number of players");
      System.exit(-1);
    }

    final boolean locks ;
    if (ARGV.length < 2) { 
      locks = true;
    }
    else {
      final String lockArg = ARGV[1];
      System.out.println("About to check \"" + lockArg +"\"  == \"LOCKS\"");
      locks = ("LOCKS".equals(lockArg));
      System.out.println("synchronization block enabled == " + locks);
    }

    final boolean quiet;
    if(ARGV.length < 3) { 
      quiet = true;
    }
    else {
      quiet  = ("QUIET".equals(ARGV[2]));
    }

    final Random rand = new java.util.Random();
    final int numberOfPlayers = Integer.valueOf(ARGV[0]).intValue();

    final List<Cycle> players = new ArrayList<Cycle>(numberOfPlayers);
    for( int i = 0 ; i < numberOfPlayers; i++){
      final int col = rand.nextInt(COLS);
      final int row = rand.nextInt(ROWS);
      players.add(new Cycle(grid, col, row, i+1, locks));
    }

    final ExecutorService es = Executors.newFixedThreadPool(6);
    final CyclicBarrier    b = new CyclicBarrier(numberOfPlayers);

    for(int turn = 1000 ; --turn >= 0 ; ) { 
      for(final Cycle c : players) { 
        es.submit(new Runnable(){
          public void run(){
            try{c.move() ;} catch (Exception e) {}
            //try{b.await();} catch (Exception e) {}
          }

        });
      }

      if(!quiet) {
        for(int i = 44 ; --i >=0 ; ) System.out.print('');
        showGrid();
        try{Thread.sleep(500);}catch(Exception e){}
      }
    }
    es.shutdown();

    while(!es.isTerminated()){ try{Thread.sleep(100);}catch(Exception e){}}

    showGrid();

  }

  private static void showGrid(){
    for (int c = 0 ; c < COLS ; c++){
      System.out.print('-');
    }
    System.out.println("|");

    for (int r = 0 ; r < ROWS ; r++){
      for (int c = 0 ; c < COLS ; c++){
        if(grid[c][r] == '\0') {
          System.out.print(' ');
        }
        else {
          System.out.print(grid[c][r]);
        }
      }
      System.out.println("|");
    }
    for (int c = 0 ; c < COLS ; c++){
      System.out.print('-');
    }
    System.out.println("|");
    System.out.println("\n");

  }


}
