package edu.ntnu.stud.transform;

import edu.ntnu.stud.math.Matrix2x2;
import edu.ntnu.stud.math.Vector2D;

/**
 * Represents a 2D affine transformation.
 * This transformation consists of a 2x2 matrix representing the linear transformation
 * and a translation vector representing the displacement.
 */
public class AffineTransform2D implements Transform2D {

  /** The transformation matrix from {@link Matrix2x2}. */
  private final Matrix2x2 matrix;

  /** The translation vector from {@link Vector2D}. */
  private final Vector2D vector;

  /**
   * Constructs a new AffineTransform2D object with the specified matrix and vector.
   *
   * @param matrix the 2x2 matrix representing the linear transformation.
   * @param vector the translation vector representing the displacement.
   */
  public AffineTransform2D(Matrix2x2 matrix, Vector2D vector) {
    this.matrix = matrix;
    this.vector = vector;
  }

  /**
   * Transforms a 2D point using this affine transformation.
   *
   * @param point the 2D point to be transformed.
   * @return the transformed 2D point.
   */
  public Vector2D transform(Vector2D point) {
    return this.matrix.multiply(point).add(this.vector);
  }

  public String toString() {
    return matrix.getA00() + ", " + matrix.getA01() + ", " + matrix.getA10() + ", " + matrix.getA11()
        + ", " + vector.getX0() + ", " + vector.getX1();
  }
}
