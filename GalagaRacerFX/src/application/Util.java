package application;

public final class Util {

    private Util(){}
    
    /**
     * Return boolean of whether or not the value is even
     * @param num
     * @return
     */
    public static boolean isEven(double num) { return ((num % 2) == 0); }
    
    
    public static int booleanToInt(boolean bool) {
        return (bool) ? 1 : 0;
    }

}