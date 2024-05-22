package edu.ntnu.stud.transform;

import edu.ntnu.stud.math.Matrix2x2;
import edu.ntnu.stud.math.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link AffineTransform2D} class.
 * It verifies the transformation behavior of the Affine set.
 */
class AffineTransform2DTest {

  // Arrange
  /** The {@link Matrix2x2} variable used for testing. */
  Matrix2x2 matrix2x2;

  /** The {@link Vector2D} variable used for testing. */
  Vector2D vector2D;

  /** The instance of {@link AffineTransform2D} to be tested. */
  AffineTransform2D affineTransform2D;

  /**
   * Sets up the test environment before each test method.
   */
  @BeforeEach
  void setUp() {

    // Arrange
    matrix2x2 = new Matrix2x2(0.5, 1.0, 1.0, 0.5);
    vector2D = new Vector2D(3, 1);
    affineTransform2D = new AffineTransform2D(matrix2x2, vector2D);

  }

  /**
   * Test the {@link AffineTransform2D#transform(Vector2D)} method.
   * It verifies the transformation behavior of the Affine set.
   */
  @Test
  @DisplayName("Test transform method")
  void transform() {
    // Arrange
    Vector2D x = new Vector2D(1,2);

    // Act
    Vector2D actual = affineTransform2D.transform(x);

    // Assert
    assertAll("Transformation",
        () -> assertEquals(5.5, actual.getX0(), "Expected x0 component of the result vector to be correct."),
        () -> assertEquals(3, actual.getX1(), "Expected x1 component of the result vector to be correct.")
    );
  }

  /**
   * Tests the {@code getMatrix} method.
   * Ensures that the returned matrix is not null and matches the expected matrix.
   */
  @Test
  @DisplayName("Test get matrix")
  void testGetMatrix() {
    Matrix2x2 result = affineTransform2D.getMatrix();
    assertNotNull(result, "Matrix should not be null");
    assertEquals(matrix2x2, result,"Matrix should be the same as the expected matrix");
  }

  /**
   * Tests the {@code getVector} method.
   * Ensures that the returned vector is not null and matches the expected vector.
   */
  @Test
  @DisplayName("Test get vector")
  void testGetVector() {
    Vector2D result = affineTransform2D.getVector();
    Vector2D expectedVector = vector2D;
    assertNotNull(result, "Vector should not be null");
    assertEquals(expectedVector, result, "Vector should be the same as the expected vector");
  }
}