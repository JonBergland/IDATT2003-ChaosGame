package edu.ntnu.stud.transform;

import edu.ntnu.stud.math.Complex;
import edu.ntnu.stud.math.Vector2D;

/**
 * This class represents a JuliaTransformation and
 * implements the Interface {@code Transform2D}
 */
public class JuliaTransform implements Transform2D {
  Complex point;
  int sign;

  /**
   * The constructor for the JuliaTransformation class.
   * This class implements the Interface {@code Transform2D}
   * and takes in a complex constant and the wanted sign for the
   * resulting transformation
   * @param point  The complex constant
   * @param sign   The wanted sign for the resulting transformation
   */
  public JuliaTransform(Complex point, int sign) {
    this.point = point;
    this.sign = sign;
  }

  /**
   * Transforms a vector using the Julia transformation defined by the complex constant and sign.
   *
   * @param point The vector number to be transformed.
   * @return      The transformed vector.
   */
  @Override
  public Vector2D transform(Vector2D point) {
      Complex subtract = this.point.subtract(point);

      Complex squareRoot = subtract.sqrt();

      return (sign == 1) ? squareRoot : new Complex(- squareRoot.getX0(), - squareRoot.getX1());
  }
}