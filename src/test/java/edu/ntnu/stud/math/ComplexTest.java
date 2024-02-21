package edu.ntnu.stud.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the {@link Complex} class.
 * It tests the functionality of methods such as addition, subtraction, and square root.
 */
@DisplayName("Tests for Complex class")
class ComplexTest {
  // Arrange
  Complex c1;
  Complex c2;

  /**
   * Sets up complex numbers for each test method.
   */
  @BeforeEach
  void setUp() {
    // Arrange
    c1 = new Complex(4, 5);
    c2 = new Complex(2, 3);
  }

  /**
   * Tests the addition method of the Complex class.
   * Performs addition operation and verify results.
   */
  @Test
  @DisplayName("Test add method")
  void add() {

    // Act
    Vector2D result = c1.add(c2);

    // Assert
    assertAll("Addition",
        () -> assertEquals(6.0, result.getX0(), "Incorrect X0 value after addition"),
        () -> assertEquals(8.0, result.getX1(), "Incorrect X1 value after addition")
    );
  }

  /**
   * Tests the subtraction method of the Complex class.
   * Performs subtraction operation and verify results.
   */
  @Test
  @DisplayName("Test subtract method")
  void subtract() {

    // Act
    Vector2D result = c1.subtract(c2);

    // Assert
    assertAll("Subtraction",
        () -> assertEquals(2.0, result.getX0(), "Incorrect X0 value after subtraction"),
        () -> assertEquals(2.0, result.getX1(), "Incorrect X1 value after subtraction")
    );
  }

  /**
   * Tests the square root method of the Complex class.
   * Creates a complex number for testing square root.
   * Compute square root and verify results.
   */
  @Test
  @DisplayName("Test sqrt method")
  void sqrt() {

    // Arrange
    Complex c = new Complex(3, 4);

    // Act
    Complex sqrt = c.sqrt();

    // Assert
    assertAll("Square root",
        () -> assertEquals(2.0, sqrt.getX0(), "Real part of square root should be correct."),
        () -> assertEquals(1.0, sqrt.getX1(), "Imaginary part of square root should be correct.")
    );
  }
}