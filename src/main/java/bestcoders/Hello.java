package bestcoders;

class Hello {
  private  int a = 0;
  public String hello(final String name) { return "Hello, " + name;}

  class InnerClass {
    InnerClass() { 
      System.out.println(a);
    }
  }

  public static void main(final String[] ARGV) { System.out.println(new Hello().hello("Dave"));}



}
