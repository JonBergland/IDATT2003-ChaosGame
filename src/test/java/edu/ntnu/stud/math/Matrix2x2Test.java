package edu.ntnu.stud.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Matrix2x2} class.
 */
@DisplayName("Tests for Matrix2x2 class")
class Matrix2x2Test {

  // Arrange
  /** The {@link Matrix2x2} variable used for testing. */
  Matrix2x2 matrix2x2;
  /** A {@code double} variable used for testing */
  double a00;
  /** A {@code double} variable used for testing */
  double a01;
  /** A {@code double} variable used for testing */
  double a10;
  /** A {@code double} variable used for testing */
  double a11;

  /**
   * Set up before each test.
   */
  @BeforeEach
  void setUp() {

    // Arrange
    a00 = 2;
    a01 = 1;
    a10 = 1;
    a11 = 2;
    matrix2x2 = new Matrix2x2(a00, a01, a10, a11);
  }

  /**
   * Nested test class for the constructor.
   */
  @Nested
  @DisplayName("Constructor")
  class constructorMatrix2x2 {

    /**
     * Positive test case for the constructor.
     */
    @Test
    @DisplayName("Test positive constructor")
    void constructor() {

      // Assert
      assertAll("Constructor",
          () -> assertEquals(2, matrix2x2.getA00(), "Expected value of A00 should be correct."),
          () -> assertEquals(1, matrix2x2.getA01(), "Expected value of A01 should be correct."),
          () -> assertEquals(1, matrix2x2.getA10(), "Expected value of A10 should be correct."),
          () -> assertEquals(2, matrix2x2.getA11(), "Expected value of A11 should be correct.")
      );
    }

    /**
     * Negative test case for the constructor.
     */
    @Test
    @DisplayName("Test of constructor with not correct values")
    void constructorNegative() {
      a00 = 3;
      a01 = 2;
      a10 = 2;
      a11 = 3;

      // Assert
      assertAll("Constructor",
          () -> assertNotEquals(3, matrix2x2.getA00(), "Expected value of A00 should not be correct."),
          () -> assertNotEquals(2, matrix2x2.getA01(), "Expected value of A01 should not be correct."),
          () -> assertNotEquals(2, matrix2x2.getA10(), "Expected value of A10 should not be correct."),
          () -> assertNotEquals(3, matrix2x2.getA11(), "Expected value of A11 should not be correct.")
      );
    }

    /**
     * Test case for the copy constructor.
     */
    @Test
    @DisplayName("Test copy constructor")
    void copyConstructor() {

      // Arrange
      Matrix2x2 copyMatrix = new Matrix2x2(matrix2x2);

      // Assert
      assertAll("Copy constructor",
          () -> assertNotEquals(matrix2x2, copyMatrix, "Expected copyMatrix to be a new instance."),
          () -> assertEquals(matrix2x2.getA00(), copyMatrix.getA00(), "Expected value of A00 should be correct."),
          () -> assertEquals(matrix2x2.getA01(), copyMatrix.getA01(), "Expected value of A01 should be correct."),
          () -> assertEquals(matrix2x2.getA10(), copyMatrix.getA10(), "Expected value of A10 should be correct."),
          () -> assertEquals(matrix2x2.getA11(), copyMatrix.getA11(), "Expected value of A11 should be correct.")
      );
    }

    /**
     * Negative test case for the constructor
     */
    @Test
    @DisplayName("Test of constructor with invalid values")
    void constructorInvalidValues() {
      double wrongNan = Double.NaN;
      double wrongInfinity = Double.POSITIVE_INFINITY;

      // Assert
      assertAll("Constructor",
          () -> assertThrows(IllegalArgumentException.class, () -> new Matrix2x2(wrongNan, a01, a10, a11), "Expected IllegalArgumentException to be thrown."),
          () -> assertThrows(IllegalArgumentException.class, () -> new Matrix2x2(a00, wrongNan, a10, a11), "Expected IllegalArgumentException to be thrown."),
          () -> assertThrows(IllegalArgumentException.class, () -> new Matrix2x2(a00, a01, wrongNan, a11), "Expected IllegalArgumentException to be thrown."),
          () -> assertThrows(IllegalArgumentException.class, () -> new Matrix2x2(a00, a01, a10, wrongNan), "Expected IllegalArgumentException to be thrown."),
          () -> assertThrows(IllegalArgumentException.class, () -> new Matrix2x2(wrongInfinity, a01, a10, a11), "Expected IllegalArgumentException to be thrown."),
          () -> assertThrows(IllegalArgumentException.class, () -> new Matrix2x2(a00, wrongInfinity, a10, a11), "Expected IllegalArgumentException to be thrown."),
          () -> assertThrows(IllegalArgumentException.class, () -> new Matrix2x2(a00, a01, wrongInfinity, a11), "Expected IllegalArgumentException to be thrown."),
          () -> assertThrows(IllegalArgumentException.class, () -> new Matrix2x2(a00, a01, a10, wrongInfinity), "Expected IllegalArgumentException to be thrown.")
      );
    }

    /**
     * Negative test case for the copy constructor
     */
    @Test
    @DisplayName("Test copy constructor with a null object")
    void copyConstructorNull() {

      // Assert
      assertThrows(IllegalArgumentException.class, () -> new Matrix2x2(null), "Expected IllegalArgumentException to be thrown.");
    }


  }

  /**
   * Nested test class for the multiply method.
   */
  @Nested
  @DisplayName("multiplyMethod")
  class multiplyMethod {
    /**
     * Test case for the multiply method.
     */
    @Test
    @DisplayName("Test multiply method")
    void multiply() {

      // Arrange
      Vector2D multiplyVector = new Vector2D(2, 1);

      // Act
      Vector2D resultVector = matrix2x2.multiply(multiplyVector);

      // Assert
      assertAll("Multiplication",
          () -> assertEquals(5.0, resultVector.getX0(), "Expected x0 component of the result vector to be correct."),
          () -> assertEquals(4.0, resultVector.getX1(), "Expected x1 component of the result vector to be correct.")
      );
    }

    /**
     * Test case for the multiply method with negative values.
     */
    @Test
    @DisplayName("Test multiply method with negative values")
    void multiplyNegative() {

      // Arrange
      Vector2D multiplyVector = new Vector2D(-2, -1);

      // Act
      Vector2D resultVector = matrix2x2.multiply(multiplyVector);

      // Assert
      assertAll("Multiplication",
          () -> assertEquals(-5.0, resultVector.getX0(), "Expected x0 component of the result vector to be correct."),
          () -> assertEquals(-4.0, resultVector.getX1(), "Expected x1 component of the result vector to be correct.")
      );
    }
  }

  /**
   * Nested test class for the get methods.
   */
  @Nested
  @DisplayName("getMethods")
  class getMethods{

    /**
     * Test case for the getA00 method.
     */
    @Test
    @DisplayName("Test getA00 method")
    void getA00() {

      // Assert
      assertEquals(a00, matrix2x2.getA00(), "Expected value of A00 to be correct.");
    }

    /**
     * Test case for the getA01 method.
     */
    @Test
    @DisplayName("Test getA01 method")
    void getA01() {

      // Assert
      assertEquals(a01, matrix2x2.getA01(), "Expected value of A01 to be correct.");
    }

    /**
     * Test case for the getA10 method.
     */
    @Test
    @DisplayName("Test getA10 method")
    void getA10() {

      // Assert
      assertEquals(a10, matrix2x2.getA10(), "Expected value of A10 to be correct.");
    }

    /**
     * Test case for the getA11 method.
     */
    @Test
    @DisplayName("Test getA11 method")
    void getA11() {

      // Assert
      assertEquals(a11, matrix2x2.getA11(), "Expected value of A11 to be correct.");
    }
  }

  /**
   * Nested test class for the set methods.
   */
  @Nested
  @DisplayName("setMethods")
  class setMethods{

    /**
     * Test case for the setA00 method.
     */
    @Test
    @DisplayName("Test setA00 method")
    void setA00() {

      // Arrange
      double newA00 = 0;

      // Act
      matrix2x2.setA00(newA00);

      // Assert
      assertEquals(newA00, matrix2x2.getA00(), "setA00 should set value correctly.");
    }

    /**
     * Test case for the setA01 method.
     */
    @Test
    @DisplayName("Test setA01 method")
    void setA01() {

      // Arrange
      double newA01 = 0;

      // Act
      matrix2x2.setA01(newA01);

      // Assert
      assertEquals(newA01, matrix2x2.getA01(), "setA01 should set value correctly.");
    }

    /**
     * Test case for the setA10 method.
     */
    @Test
    @DisplayName("Test setA10 method")
    void setA10() {

      // Arrange
      double newA10 = 0;

      // Act
      matrix2x2.setA10(newA10);

      // Assert
      assertEquals(newA10, matrix2x2.getA10(), "setA10 should set value correctly.");
    }

    /**
     * Test case for the setA11 method.
     */
    @Test
    @DisplayName("Test setA11 method")
    void setA11() {

      // Arrange
      double newA11 = 0;

      // Act
      matrix2x2.setA11(newA11);

      // Assert
      assertEquals(newA11, matrix2x2.getA11(), "setA11 should set value correctly.");
    }
  }

  /**
   * Nested test class for validating the {@link Matrix2x2#toString()} method.
   */
  @Nested
  @DisplayName("toString method")
  class toStringMethod {

    /**
     * Test method for the {@link Matrix2x2#toString()} method.
     * Verifies that the toString method correctly generates the string representation
     * of the matrix in the expected format.
     */
    @Test
    @DisplayName("Test toString method")
    void testToString() {
      // Arrange
      String expectedString = "[2.0 1.0\n 1.0 2.0]";

      // Act
      String actualString = matrix2x2.toString();

      // Assert
      assertEquals(expectedString, actualString, "Expected toString output doesn't match actual toString output.");
    }
  }

}