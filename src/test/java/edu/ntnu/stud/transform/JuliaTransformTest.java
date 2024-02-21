package edu.ntnu.stud.transform;

import edu.ntnu.stud.math.Complex;
import edu.ntnu.stud.math.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the {@link JuliaTransform} class.
 * It tests the transformation behavior of the Julia set.
 */
@DisplayName("Test JuliaTransform class")
class JuliaTransformTest {

  // Arrange
  /** The instance of {@link JuliaTransform} to be tested. */
  JuliaTransform juliaTransform;

  /** The {@link Complex} number used for testing. */
  Complex complex;

  /**
   * Sets up the necessary objects and configurations before each test method.
   */
  @BeforeEach
  void setUp() {

    // Arrange
    double real = 0.4;
    double imaginary = 0.2;
    complex = new Complex(real, imaginary);
    juliaTransform = new JuliaTransform(complex, 1);
  }

  /**
   * Tests the {@link JuliaTransform#transform(Vector2D)} method.
   * It verifies the transformation behavior of the Julia set.
   */
  @Test
  @DisplayName("Test transform method")
  void transform() {
    // Arrange
    Complex complexC = new Complex(0.3, 0.6);

    // Act
    Vector2D result = juliaTransform.transform(complexC);

    // Assert
    assertAll("Transformation",
        () -> assertEquals(0.506, result.getX0(), 0.01, "Expected x0 component of the result vector to be correct."),
        () -> assertEquals(-0.395, result.getX1(), 0.01, "Expected x1 component of the result vector to be correct.")
    );
  }
}
