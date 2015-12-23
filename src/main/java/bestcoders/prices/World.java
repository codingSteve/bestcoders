package bestcoders.prices;



import java.io.*;
import java.util.*;

public class World {

  public static void main(final String[] ARGV) {




    final int bufferSize = 600;
    final int tickers    = 600;


    final ArrayList<Integer> a = new ArrayList<Integer>(600);
    a.set(0,1);
    a.set(1,1);
    System.out.println(a.size());
    System.exit(0);

    generateTicks(bufferSize, tickers);


  }


  private static void generateTicks(final int bufferSize, final int tickers){

    final List<Ticker>       allTickers = loadTickers();
    final List<Ticker>       universe   = allTickers.subList(0,tickers);
    final List<List<Double>> prices     = new ArrayList<List<Double>>();

    System.out.println(bufferSize);
    System.out.println(tickers);

    System.out.println(allTickers.size());
    System.out.println(universe.size());
    System.out.println(prices.size());

    for(int i=0; i < tickers;i++) {
      prices.add(new ArrayList<Double>());
    }


    for (int i=0 ; i < universe.size();i++) {
      final List<Double> tickerPrices = prices.get(i);
      final Ticker t = universe.get(i);
      spinPrices(t, tickerPrices);

    }

  }

  static void spinPrices(final Ticker t, final List<Double> prices){
    Thread th = new Thread() { 
      public void run() { 
        for (int i =0 ; ++i < 1000000; ) { 
          if (i < 600) {
            prices.add(t.newPrice());
          }
          else {
            prices.set((i % prices.size()), t.newPrice());
          }
        }
      }
    };
      th.start();
  }

  private static List<Ticker> loadTickers() { 
    final String symFile = "./CXESymbols-PROD.csv";
    final int    chunkSize = 1000000;
    final char[] cbuf      = new char[chunkSize];
    final String recDelim  = "\n";
    final String fldDelim  = ",";

    final ArrayList<Ticker> allTickers = new ArrayList<Ticker>();

    try { 
      final BufferedReader b = new BufferedReader(new FileReader(symFile), chunkSize);
      b.read(cbuf, 0, chunkSize);

      int fldNumber = 0;
      int recNumber = 0;

      final String all = new String(cbuf);
      final String[] records = all.split(recDelim);

      for(int i =2 ; ++i < records.length;) {
        final String r = records[i];
        final String[] fields = r.split(fldDelim);
        if (fields.length >=1) {
          try{
            allTickers.add(new Ticker(fields[1],Double.valueOf( fields[9])));
          }
          catch(Exception e) {
          }
        }
      }



    } 
    catch(Exception e){
      e.printStackTrace();
    }

    return allTickers;
    
  }



}


