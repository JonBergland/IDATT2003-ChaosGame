package edu.ntnu.stud.math;

import edu.ntnu.stud.utils.Verification;

/**
 * Represents a two-dimensional vector with double precision floating-point components.
 */
public class Vector2D {
  /**
   * The first component of the vector.
   */
  private double x0;

  /**
   * The second component of the vector.
   */
  private double x1;

  /** The exception message used in IllegalArgumentExceptions */
  private static final String EXCEPTION_MESSAGE = "The value has to be a valid number";

  /**
   * Constructs a Vector2D with the specified components.
   *
   * @param x0 The first element of the vector.
   * @param x1 The second element of the vector.
   */
  public Vector2D(double x0, double x1) throws IllegalArgumentException {
    try {
      setX0(x0);
      setX1(x1);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Vector cannot be null");
    }
  }

  /**
   * A copy constructor for Vector2D
   *
   * @param vector The vector to be copied.
   */
  public Vector2D(Vector2D vector) throws IllegalArgumentException {
    if (vector != null) {
      this.x0 = vector.getX0();
      this.x1 = vector.getX1();
    } else {
      throw new IllegalArgumentException("Vector cannot be null.");
    }
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
   * Sets the element at x0.
   *
   * @param x0 The value to set.
   * @throws IllegalArgumentException if the value is not a valid number.
   */
  public void setX0(double x0) throws IllegalArgumentException {
    try {
      Verification.requireANumber(x0);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(EXCEPTION_MESSAGE + e.getMessage());
    }
    this.x0 = x0;
  }

  /**
   * Sets the element at x1.
   *
   * @param x1 The value to set.
   * @throws IllegalArgumentException if the value is not a valid number.
   */
  public void setX1(double x1) throws IllegalArgumentException {
    try {
      Verification.requireANumber(x1);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(EXCEPTION_MESSAGE + e.getMessage());
    }
    this.x1 = x1;
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
