package edu.ntnu.stud.utils;

import edu.ntnu.stud.math.Complex;
import edu.ntnu.stud.math.Vector2D;

public class Verification {

  public static void isInstanceOfComplex(Vector2D verifyVector) {
    if (!(verifyVector instanceof Complex)) {
      throw new IllegalArgumentException("Argument is not an Complex number");
    }
  }

  /**
   * Checks if a double is a number and throws an IllegalArgumentException if it is not
   * @param number                    the number that is checked
   * @param notANumberMessage         the error message sent
   * @throws IllegalArgumentException the exception thrown when not a number
   */
  public static void requireANumber(double number, String notANumberMessage)
  throws IllegalArgumentException {
    if (Double.isNaN(number)) {
      throw new IllegalArgumentException(notANumberMessage);
    } else if (Double.isInfinite(number)) {
      throw new IllegalArgumentException(notANumberMessage);
    }
  }

  /**
   * Checks if a double is a number and throws an IllegalArgumentException if it is not.
   * @param number                      the number that is checked
   * @throws IllegalArgumentException   the exception thrown when not a number
   */
  public static void requireANumber(Double number)
  throws IllegalArgumentException {
    requireANumber(number, "The parameter was not a number");
  }


}
