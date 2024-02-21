package edu.ntnu.stud.math;

/**
 * Represents a two-dimensional vector with double precision floating-point components.
 */
public class Vector2D {
  private final double x0;
  private final double x1;

  /**
   * Constructs a Vector2D with the specified components.
   *
   * @param x0 The first element of the vector.
   * @param x1 The second element of the vector.
   */
  public Vector2D(double x0, double x1) {
    this.x0 = x0;
    this.x1 = x1;
  }

  /**
   * Retrieves the value of the first component (x0) of the vector.
   *
   * @return The value of the first component.
   */
  public double getX0() {
    return x0;
  }

  /**
   * Retrieves the value of the second component (x1) of the vector.
   *
   * @return The value of the second component.
   */
  public double getX1() {
    return x1;
  }

  /**
   * Adds another Vector2D to this vector.
   *
   * @param other The vector to be added.
   * @return A new Vector2D representing the result of the addition.
   */
  public Vector2D add(Vector2D other) {
    double sumX0 = this.getX0() + other.getX0();
    double sumX1 = this.getX1() + other.getX1();
    return new Vector2D(sumX0, sumX1);
  }

  /**
   * Subtracts another Vector2D from this vector.
   *
   * @param other The vector to be subtracted.
   * @return A new Vector2D representing the result of the subtraction.
   */
  public Vector2D subtract(Vector2D other) {
    double sumX0 = this.getX0() - other.getX0();
    double sumX1 = this.getX1() - other.getX1();
    return new Vector2D(sumX0, sumX1);
  }

  /**
   * Returns a string representation of this vector.
   */
  @Override
  public String toString() {
    return "[" + x0 + "\n " + x1 + "]";
  }
}
