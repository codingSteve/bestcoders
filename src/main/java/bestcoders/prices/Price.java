package bestcoders.prices;


class Price{

private final Ticker ticker;
private double price;

Price( final Ticker t){ this.ticker =t;}

  Ticker getTicker(){
    return ticker;
  }

  double getPrice(){
    return price;
  }

  void   setPrice(final double price) { 
    this.price = price;
  }

}
