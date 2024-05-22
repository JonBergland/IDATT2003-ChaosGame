package edu.ntnu.stud.transform;

import edu.ntnu.stud.math.Complex;
import edu.ntnu.stud.math.Vector2D;

/**
 * This class represents a JuliaTransformation and
 * implements the Interface {@code Transform2D}.
 */
public class JuliaTransform implements Transform2D {

  /** The complex constant used in the Julia Transformation. */
  private final Complex point;

  /** The sign used in the Julia Transformation. */
  private final int sign;

  /**
   * The constructor for the JuliaTransformation class.
   * This class implements the Interface {@code Transform2D}
   * and takes in a complex constant and the wanted sign for the
   * resulting transformation.
   *
   * @param point  The complex constant
   * @param sign   The wanted sign for the resulting transformation
   */
  public JuliaTransform(Complex point, int sign) {
    this.point = point;
    this.sign = sign;
  }

  /**
   * Get the complex constant used in the Julia Transformation.
   *
   * @return The complex constant used in the Julia Transformation.
   */
  public Complex getPoint() {
    return point;
  }

  /**
   * Transforms a vector using the Julia transformation defined by the complex constant and sign.
   *
   * @param point The vector number to be transformed.
   * @return      The transformed vector.
   */
  @Override
  public Vector2D transform(Vector2D point) {
    Complex pointComplex = new Complex(point.getX0(), point.getX1());
    Complex subtract = pointComplex.subtract(this.point);

    Complex squareRoot = subtract.sqrt();

    return (sign == 1) ? squareRoot : new Complex(- squareRoot.getX0(), - squareRoot.getX1());
  }

  /**
   * Returns a string representation of the JuliaTransformation.
   *
   * @return A string representation of the JuliaTransformation.
   */
  public String toString() {
    return point.getX0() + ", " + sign * point.getX1();
  }
}
