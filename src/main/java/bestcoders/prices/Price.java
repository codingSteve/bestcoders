package bestcoders.prices;


class Price{

private final Ticker ticker;
private Double price;

Price( final Ticker t){ this.ticker =t;}

Ticker getTicker(){ return ticker;}
Double getPrice(){ return price;}
void   setPrice(final Double price) { 
    this.price = price;
  }


}