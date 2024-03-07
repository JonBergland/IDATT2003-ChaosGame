package edu.ntnu.stud.math;

import edu.ntnu.stud.utils.Verification;

/**
 * Represents a 2x2 matrix with double precision floating-point values.
 * This class provides methods to perform basic matrix operations.
 */
public class Matrix2x2 {
  /**
   * The element at row 0, column 0.
   */
  private double a00;

  /**
   * The element at row 0, column 1.
   */
  private double a01;

  /**
   * The element at row 1, column 0.
   */
  private double a10;

  /**
   * The element at row 1, column 1.
   */
  private double a11;

  /** The exception message used in IllegalArgumentExceptions */
  private static final String EXCEPTION_MESSAGE = "The value has to be a valid number";

  /**
   * Constructs a 2x2 matrix with the provided elements.
   *
   * @param a00 Element at row 0, column 0.
   * @param a01 Element at row 0, column 1.
   * @param a10 Element at row 1, column 0.
   * @param a11 Element at row 1, column 1.
   */
  public Matrix2x2(double a00, double a01, double a10, double a11) throws IllegalArgumentException {
    try {
      setA00(a00);
      setA01(a01);
      setA10(a10);
      setA11(a11);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * A copy constructor for Matrix2x2
   *
   * @param matrix The matrix to be copied.
   */
  public Matrix2x2(Matrix2x2 matrix) throws IllegalArgumentException {
    if (matrix != null) {
      this.a00 = matrix.getA00();
      this.a01 = matrix.getA01();
      this.a10 = matrix.getA10();
      this.a11 = matrix.getA11();
    } else {
      throw new IllegalArgumentException("Matrix cannot be null");
    }
  }

  /**
   * Multiplies this matrix by a 2D vector.
   *
   * @param vector The vector to be multiplied.
   * @return The resulting vector after multiplication.
   */
  public Vector2D multiply(Vector2D vector) {
    double x0 = a00 * vector.getX0() + a01 * vector.getX1();
    double x1 = a10 * vector.getX0() + a11 * vector.getX1();
    return new Vector2D(x0, x1);
  }
  /**
   * Gets the element at row 0, column 0.
   *
   * @return The value of the element at row 0, column 0.
   */
  public double getA00() {
    return a00;
  }

  /**
   * Gets the element at row 0, column 1.
   *
   * @return The value of the element at row 0, column 1.
   */
  public double getA01() {
    return a01;
  }

  /**
   * Gets the element at row 1, column 0.
   *
   * @return The value of the element at row 1, column 0.
   */
  public double getA10() {
    return a10;
  }

  /**
   * Gets the element at row 1, column 1.
   *
   * @return The value of the element at row 1, column 1.
   */
  public double getA11() {
    return a11;
  }

  /**
   * Sets the element at row 0, column 0.
   *
   * @param a00 The value to set.
   * @throws IllegalArgumentException if the value is not a valid number
   */
  public void setA00(double a00) throws IllegalArgumentException {
    try {
      Verification.requireANumber(a00);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(EXCEPTION_MESSAGE + e.getMessage());
    }
    this.a00 = a00;
  }

  /**
   * Sets the element at row 0, column 1.
   *
   * @param a01 The value to set.
   * @throws IllegalArgumentException if the value is not a valid number
   */
  public void setA01(double a01) throws IllegalArgumentException {
    try {
      Verification.requireANumber(a01);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(EXCEPTION_MESSAGE + e.getMessage());
    }
    this.a01 = a01;
  }

  /**
   * Sets the element at row 1, column 0.
   *
   * @param a10 The value to set.
   * @throws IllegalArgumentException if the value is not a valid number
   */
  public void setA10(double a10) throws IllegalArgumentException {
    try {
      Verification.requireANumber(a10);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(EXCEPTION_MESSAGE + e.getMessage());
    }
    this.a10 = a10;
  }

  /**
   * Sets the element at row 1, column 1.
   *
   * @param a11 The value to set.
   * @throws IllegalArgumentException if the value is not a valid number
   */
  public void setA11(double a11) throws IllegalArgumentException {
    try {
      Verification.requireANumber(a11);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(EXCEPTION_MESSAGE + e.getMessage());
    }
    this.a11 = a11;
  }

  /**
   * Returns a string representation of this matrix.
   */
  @Override
  public String toString() {
    return "[" + a00 + " " + a01 +
        "\n " + a10 + " " + a11 + "]";
  }
}
