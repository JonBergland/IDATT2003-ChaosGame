package edu.ntnu.stud.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link Vector2D} class.
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
    void constructor() {

      // Assert
      assertAll("Constructor",
          () -> assertEquals(x0, vector2D.getX0(), "Should be the correct value."),
          () -> assertEquals(x1, vector2D.getX1(), "Should be the correct value.")
      );
    }


    /**
     * Negative test case for the constructor.
     */
    @Test
    @DisplayName("Negative test of constructor")
    void constructorNegativeTest() {
      // Arrange
      x0 = 1;
      x1 = 2;

      // Assert
      assertAll("Constructor Negative",
          () -> assertNotEquals(1, vector2D.getX0(), "Expected value of x0 should not be correct"),
          () -> assertNotEquals(2, vector2D.getX1(), "Expected value of x1 should not be correct")
      );
    }


    /**
     * Test case for the copy constructor.
     */
    @Test
    @DisplayName("Test copy constructor")
    void copyConstructor() {

      // Act
      Vector2D copyVector = new Vector2D(vector2D);

      // Assert
      assertAll("Copy constructor",
          () -> assertNotEquals(vector2D, copyVector, "Expected copyVector to be a new instance."),
          () -> assertEquals(vector2D.getX0(), copyVector.getX0(), "Expected value of x0 should be correct."),
          () -> assertEquals(vector2D.getX1(), copyVector.getX1(), "Expected value of x1 should be correct.")
      );
    }

    /**
     * Negative test case for the constructor
     */
    @Test
    @DisplayName("Test of constructor with invalid values")
    void constructorInvalidValues() {
      //Arrange
      double wrongNan = Double.NaN;
      double wrongInfinity = Double.POSITIVE_INFINITY;

      // Assert
      assertAll("Constructor",
          () -> assertThrows(IllegalArgumentException.class, () -> new Vector2D(wrongNan, x1), "Expected IllegalArgumentException to be thrown."),
          () -> assertThrows(IllegalArgumentException.class, () -> new Vector2D(x0, wrongNan), "Expected IllegalArgumentException to be thrown."),
          () -> assertThrows(IllegalArgumentException.class, () -> new Vector2D(wrongInfinity, x1), "Expected IllegalArgumentException to be thrown."),
          () -> assertThrows(IllegalArgumentException.class, () -> new Vector2D(x0, wrongInfinity), "Expected IllegalArgumentException to be thrown.")
      );
    }

    /**
     * Negative test case for the copy constructor
     */
    @Test
    @DisplayName("Test copy constructor with a null object")
    void copyConstructorNull() {
      // Assert
      assertThrows(IllegalArgumentException.class, () -> new Vector2D(null), "Expected IllegalArgumentException to be thrown.");
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
      String expectedString = "3.0, 4.0";

      // Act
      String actualString = vector2D.toString();

      // Assert
      assertEquals(expectedString, actualString, "Expected toString output doesn't match actual toString output.");
    }
  }
}
