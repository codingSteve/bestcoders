package bestcoders.prices;



import java.io.*;
import java.util.*;

public class World {

  public static void main(final String[] ARGV) {
    final int bufferSize = 600;
    final int tickers    = 600;

    generateTicks(bufferSize, tickers);


  }


  private static void generateTicks(final int bufferSize, final int tickers){

    final List<Ticker>       allTickers = loadTickers();
    final List<Ticker>       universe   = allTickers.subList(0,tickers);
    final List<List<Price>> prices     = new ArrayList<List<Price>>();

   

    for(int i=0; i < tickers;i++) {
      final List<Price> l = new ArrayList<Price>();
      prices.add(l);
      
      final Ticker t = universe.get(i);
      
      for(int j= bufferSize; --j >= 0;){
        l.set(j, new Price(t));
      }
    }


    for (int i=0 ; i < universe.size();i++) {
      final List<Price> tickerPrices = prices.get(i);
      final Ticker t = universe.get(i);
      spinPrices(t, tickerPrices, universe);

    }

  }

  static void spinPrices(final Ticker t, final List<Double> prices, final Object synchOn){
  
    final int s = prices.size();
    
    Thread th = new Thread() { 
      public void run() { 
        for (int i =1000000 ; --i >=0; ) { 
          
           Price p = prices.get(i % s);
           
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


