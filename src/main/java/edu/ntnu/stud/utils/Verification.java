package edu.ntnu.stud.utils;

/**
 * This class provides utility methods for verification purposes.
 */
public class Verification {

  /**
   * This constructor is empty because it is not needed.
   * The class is a utility class and should not be instantiated.
   */
  private Verification() {
  }

  /**
   * Checks if a double is a valid number.
   *
   * @param number                    the number that is checked
   * @param notNumberMessage         the error message sent
   * @throws IllegalArgumentException the exception thrown when not a number
   */
  public static void requireNumber(double number, String notNumberMessage)
      throws IllegalArgumentException {
    if (Double.isNaN(number)) {
      throw new IllegalArgumentException(notNumberMessage + " (NaN)");
    } else if (Double.isInfinite(number)) {
      throw new IllegalArgumentException(notNumberMessage + " (infinite)");
    }
  }

  /**
   * Checks if a Double object is a valid number.
   *
   * @param number                      the number that is checked
   * @throws IllegalArgumentException   the exception thrown when not a number
   */
  public static void requireNumber(Double number)
      throws IllegalArgumentException {
    requireNumber(number, "The parameter was not a number");
  }


}
