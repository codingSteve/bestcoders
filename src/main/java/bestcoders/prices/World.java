package bestcoders.prices;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;


public class World {
  private static final int bufferSize = 1 << 10;
  private static final int tickers    = 1000;

  private static final List<Ticker>      allTickers = loadTickers();
  private static final List<Ticker>      universe   = allTickers.subList(0,tickers);
  private static final List<List<Price>> prices     = new ArrayList<List<Price>>();

  //effectively locking the entire table
  //lock striping needed
  private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  private static final Lock readLock  = readWriteLock.readLock();
  private static final Lock writeLock = readWriteLock.writeLock();

  private static final ExecutorService pool = Executors.newFixedThreadPool(8);

  public static void main(final String[] ARGV) {

    generateTicks(bufferSize, tickers);
    readTicks(bufferSize, tickers);

    while(!pool.isTerminated()) {
      nap(1000);
      System.out.println("pool still working");
    }


  }
  
  static void nap(final long n) {
    try{ Thread.sleep(n);} catch (Exception e) {}
  }

  private static void readTicks(final int bufferSize, final int tickers){
    for (;;) {
      for (int i = tickers; --i >= 0;){
        final Ticker t = getTicker(i);
        final List<Price> prices = getRecentPrices(i);

        double runningTotal = 0.0d;
        for(int j = bufferSize; --j >= 0;) {
          final Price p = prices.get(j);
          readLock.lock();
          //synchronized(p) { 
            runningTotal += p.getPrice();
          //}
          readLock.unlock();
  
  
        }
        System.out.println(t.ticker + " - " + t.referencePrice +  " - "  + runningTotal/bufferSize);


      }
      nap(1000);
    }
  }


  static List<Price> getRecentPrices(final int i) { 
    return prices.get(i);
  }

  static Ticker getTicker(final int i){
      return universe.get(i);
  }

  static List<Ticker> generateTicks(final int bufferSize, final int tickers){


    for(int i=tickers; --i >=0 ;) {
      final List<Price> l = new ArrayList<Price>();
      prices.add(l);
      
      final Ticker t = universe.get(i);
      
      for(int j= bufferSize; --j >= 0;){
        writeLock.lock();
        l.add(new Price(t));
        writeLock.unlock();
      }
    }

    final long generationStart = new java.util.Date().getTime();

      for (int i=0 ; i < universe.size();i++) {
        final List<Price> tickerPrices = prices.get(i);
        final Ticker t = universe.get(i);
        spinPrice(t, tickerPrices, pool);
      }

    System.out.println("tick generation complete in " + (new java.util.Date().getTime() - generationStart) + "ms");

    return universe;
  }

  static void spinPrice(final Ticker t, final List<Price> prices, final ExecutorService pool){
  
    final int mask = prices.size() -1 ;
    
    Runnable r = new Runnable() { 
      public void run() { 
        for (int i =1000000 ; --i >=0; ) { 
          final int index = i & mask;
          if(index == 0 ) nap(1);
          setPrice(t, index, prices);
        }
      }
    };

    pool.execute(r);

  }

  static void setPrice(final Ticker t, 
                       final int position, 
                       final List<Price> prices) { 
    Price p = prices.get(position);
    writeLock.lock();
    //synchronized(p){
      p.setPrice(t.newPrice());
    //}
    writeLock.unlock();
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


