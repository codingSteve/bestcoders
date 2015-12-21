package bestcoders.gc;
import java.util.Random;

class Ticker { 
  private final Random r = new Random();

  public final String ticker;
  public final double referencePrice;

  Ticker(final String ticker, final double referencePrice) {
    this.ticker         = ticker;
    this.referencePrice = referencePrice;
  }

  public double newPrice(){
    return referencePrice *( 1 + ((0.5d + r.nextDouble())/10.0d));
  }


}
