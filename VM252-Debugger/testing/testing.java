package testing;

public class testing {

    public static String myString;

    public void setString(String something) {
       myString = something;
    }

    public static String getString() {
        return myString;
    }

    public static void Printer(String other) {
        System.out.println(other);
    }
    public static String Print(String other) {
        return other;
    }

    public static void main(String[] args) {
        testing tester = new testing("hello");
        Printer(getString());
        // System.out.println(Print(tester.getString()));
        System.out.println(tester);
    }

    public testing(String other) {
        setString(other);
    }
}