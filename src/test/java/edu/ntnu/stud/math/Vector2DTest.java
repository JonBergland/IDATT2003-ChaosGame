package edu.ntnu.stud.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Vector2D.
 */
@DisplayName("Tests for Vector2D class")
class Vector2DTest {
  // Arrange
  /** A {@code double} variable used for testing */
  double x0;
  /** A {@code double} variable used for testing */
  double x1;
  /** The {@link Vector2D} variable used for testing. */
  Vector2D vector2D;
  /** Another {@link Vector2D} variable used for testing. */
  Vector2D testVector;

  /**
   * Set up before each test.
   */
  @BeforeEach
  void setUp() {
    // Arrange
    x0 = 3;
    x1 = 4;
    vector2D = new Vector2D(x0, x1);
    testVector = new Vector2D(2.0,2.0);
  }


  /**
   * Nested test class for validating the constructors of the {@link Vector2D} class.
   */
  @Nested
  @DisplayName("Vector2D constructor")
  class Constructor {

    /**
     * Test the constructor of the Vector2D class.
     * Verifies that the constructor initializes the vector components
     * with the correct values provided during construction.
     */
    @Test
    @DisplayName("Test of constructor")
    void testOfConstructor() {

      // Assert
      assertAll("Constructor",
          () -> assertEquals(x0, vector2D.getX0(), "Should be the correct value."),
          () -> assertEquals(x1, vector2D.getX1(), "Should be the correct value.")
      );
    }
  }

  /**
   * Nested test class for testing get methods of Vector2D.
   */
  @DisplayName("Vector2D get-methods")
  @Nested
  class GetMethods {
    /**
     * Test for getX0 method.
     */
    @Test
    @DisplayName("Test getX0 method")
    void getX0() {
      // Assert
      assertEquals(x0, vector2D.getX0(), "getX0 should return the value of X0");
    }

    /**
     * Test for getX1 method.
     */
    @Test
    @DisplayName("Test getX1 method")
    void getX1() {
      // Assert
      assertEquals(x1, vector2D.getX1(), "getX1 should return the value of X1");
    }
  }

  /**
   * Nested test class for testing add method of Vector2D.
   */
  @DisplayName("Vector2D add-method")
  @Nested
  class AddMethod {
    /**
     * Test for add method.
     */
    @Test
    @DisplayName("Test add method")
    void add() {

      // Act
      Vector2D resultVector = vector2D.add(testVector);

      // Assert
      assertAll("Addition",
          () -> assertEquals(5.0, resultVector.getX0(), "Expected x0 component of the result vector to be correct."),
          () -> assertEquals(6.0, resultVector.getX1(), "Expected x1 component of the result vector to be correct.")
      );
    }
  }

  /**
   * Nested test class for testing subtract method of Vector2D.
   */
  @DisplayName("Vector2D subtract-method")
  @Nested
  class SubtractMethod {
    /**
     * Test for subtract method.
     */
    @Test
    @DisplayName("Test subtract method")
    void subtract() {

      // Act
      Vector2D resultVector = vector2D.subtract(testVector);

      // Assert
      assertAll("Subtraction",
          () -> assertEquals(1.0, resultVector.getX0(), "Expected x0 component of the result vector to be correct."),
          () -> assertEquals(2.0, resultVector.getX1(), "Expected x1 component of the result vector to be correct.")
      );
    }
  }


  /**
   * Nested test class for {@link Vector2D#toString()} method.
   */
  @Nested
  @DisplayName("toString method")
  class toStringMethod {

    /**
     * Test method for the {@link Vector2D#toString()} method.
     * Verifies that the toString method correctly generates the string representation
     * of the vector in the expected format.
     */
    @Test
    @DisplayName("Test toString method")
    void testToString() {
      // Arrange
      String expectedString = "[3.0\n 4.0]";

      // Act
      String actualString = vector2D.toString();

      // Assert
      assertEquals(expectedString, actualString, "Expected toString output doesn't match actual toString output.");
    }
  }
}
