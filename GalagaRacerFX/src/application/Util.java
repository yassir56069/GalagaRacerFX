package application;

/**
 * The {@code Util} class provides a collection of static utility methods
 * for various functionalities in the game. This class is designed as a utility class
 * and does not require instantiation.
 * <p>
 * All methods in this class are static, making them easily accessible without the need
 * to create an instance of the class.
 * </p>
 * <p>
 * Usage examples:
 * {@code Util.methodExample();}
 * </p>
 * 
 * @version 1.0
 * @since 2024-02-11
 */
public final class Util {

    private Util(){}
    
    public static boolean isEven(double num) { return ((num % 2) == 0); }
    
    public static int booleanToInt(boolean bool) {
        return (bool) ? 1 : 0;
    }

}