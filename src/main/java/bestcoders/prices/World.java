package bestcoders.prices;

import java.io.*;
import java.util.*;

public class World {
  private static final int bufferSize = (int) Math.pow(2,10);
  private static final int tickers    = 600;

  private static final List<Ticker>      allTickers = loadTickers();
  private static final List<Ticker>      universe   = allTickers.subList(0,tickers);
  private static final List<List<Price>> prices     = new ArrayList<List<Price>>();

  public static void main(final String[] ARGV) {

    generateTicks(bufferSize, tickers);
    readTicks(bufferSize, tickers);


  }

  private static void readTicks(final int bufferSize, final int tickers){
    for (int i = tickers; --i >= 0;){
      final Ticker t = getTicker(i);
      final List<Price> prices = getRecentPrices(i);

      double runningTotal = 0.0d;
      for(int j = bufferSize; --j >= 0;) {
        final Price p = prices.get(j);
        synchronized(p) { 
          runningTotal += p.getPrice();
        }

      }
      System.out.println(t.ticker + " - "  + runningTotal/tickers);


    }
  }


  private static List<Price> getRecentPrices(final int i) { 
    return prices.get(i);
  }

  private static Ticker getTicker(final int i){
      return universe.get(i);
  }

  private static List<Ticker> generateTicks(final int bufferSize, final int tickers){

    for(int i=0; i < tickers;i++) {
      final List<Price> l = new ArrayList<Price>();
      prices.add(l);
      
      final Ticker t = universe.get(i);
      
      for(int j= bufferSize; --j >= 0;){
        l.add(new Price(t));
      }
    }


    for (int i=0 ; i < universe.size();i++) {
      final List<Price> tickerPrices = prices.get(i);
      final Ticker t = universe.get(i);
      spinPrices(t, tickerPrices, universe);

    }

    return universe;
  }

  static void spinPrices(final Ticker t, final List<Price> prices, final Object synchOn){
  
    final int mask = prices.size() -1 ;
    
    Thread th = new Thread() { 
      public void run() { 
        for (int i =1000000 ; --i >=0; ) { 
          
           Price p = prices.get(i & mask);
           
            synchronized(p){
              p.setPrice(t.newPrice());
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


