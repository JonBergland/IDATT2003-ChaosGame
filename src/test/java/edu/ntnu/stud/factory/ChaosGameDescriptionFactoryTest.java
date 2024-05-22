package edu.ntnu.stud.factory;

import edu.ntnu.stud.chaos.ChaosGameDescription;
import edu.ntnu.stud.math.Complex;
import edu.ntnu.stud.transform.AffineTransform2D;
import edu.ntnu.stud.transform.JuliaTransform;
import edu.ntnu.stud.transform.Transform2D;
import edu.ntnu.stud.utils.FractalType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ChaosGameDescriptionFactory}.
 */
class ChaosGameDescriptionFactoryTest {
  Map<String, ChaosGameDescription> chaosGameDescriptionHashMap;

  @BeforeEach
  void setUp() {
    chaosGameDescriptionHashMap = new HashMap<>();
  }

  @Nested
  @DisplayName("Test of get-methods")
  class GetDescription {
    @Test
    @DisplayName("Test getSierpinskiDescription")
    void getSierpinskiDescription() {
      assertEquals(AffineTransform2D.class, ChaosGameDescriptionFactory.getDescription(FractalType.SIERPINSKI).getTransformationType());
    }

    @Test
    @DisplayName("Test getBarnsleyDescription")
    void getBarnsleyDescription() {
      assertEquals(AffineTransform2D.class, ChaosGameDescriptionFactory.getDescription(FractalType.BARNSLEY).getTransformationType());
    }

    @Test
    @DisplayName("Test getJuliaDescription")
    void getJuliaDescription() {
      assertEquals(JuliaTransform.class, ChaosGameDescriptionFactory.getDescription(FractalType.JULIA).getTransformationType());
    }

    @Test
    @DisplayName("Test getMandelbrotDescription")
    void getMandelbrotDescription() {
      assertEquals(JuliaTransform.class, ChaosGameDescriptionFactory.getDescription(FractalType.MANDELBROT).getTransformationType());
    }

    @Test
    @DisplayName("Test getInvalidDescription")
    void getInvalidDescription() {
      assertThrows(IllegalArgumentException.class, () -> ChaosGameDescriptionFactory.getDescription("Invalid"));
    }

    @Test
    @DisplayName("Test getDescriptionCanReadFromFile")
    void getDescriptionCanReadFromFile() {
      assertDoesNotThrow(() -> ChaosGameDescriptionFactory.getDescription(FractalType.SIERPINSKI));
    }
  }

  @Nested
  @DisplayName("Test of getJuliaDescriptionWithC-method")
  class GetJuliaDescriptionWithC {
    @Test
    @DisplayName("Test getJuliaDescriptionWithC")
    void getJuliaDescriptionWithC() {
      Complex c = new Complex(0, 0);

      assertAll(
        () -> assertEquals(JuliaTransform.class, ChaosGameDescriptionFactory.getJuliaDescriptionWithC(c).getTransformationType()),
        () -> assertEquals(2, ChaosGameDescriptionFactory.getJuliaDescriptionWithC(c).getTransforms().size())
      );
    }
  }

  @Test
  @DisplayName("Test getMandelbrotDescription")
  void testGetMandelbrotDescription() {
    JuliaTransform expectedJuliaTransform = new JuliaTransform(new Complex(0, 0), 1);
    List<Transform2D> expectedJuliaList = new ArrayList<>();
    expectedJuliaList.add(expectedJuliaTransform);

    Complex expectedMin = new Complex(-2.00, -1.12);
    Complex expectedMax = new Complex(0.47, 1.12);

    ChaosGameDescription result = ChaosGameDescriptionFactory.getDescription(FractalType.MANDELBROT);

    assertAll(
      () -> assertNotNull(result, "ChaosGameDescription should not be null"),
      () -> assertEquals(1, result.getTransforms().size(), "JuliaList should have one transform"),
      () -> assertEquals(expectedMin.getX0(), result.getMinCoords().getX0(), "Min complex value should match the expected value"),
      () -> assertEquals(expectedMax.getX1(), result.getMaxCoords().getX1(), "Max complex value should match the expected value")
    );
  }

  @Nested
  @DisplayName("Test of chaosGameDescriptionHashMap-methods")
  class ChaosGameDescriptionHashMap {
    @Test
    @DisplayName("Test checkMapForDescription")
    void checkMapForDescription() {
      ChaosGameDescription description = new ChaosGameDescription(new ArrayList<>(), new Complex(0, 0), new Complex(1, 1));
      ChaosGameDescriptionFactory.addNewFileDescription("description", description);
      assertSame(description, ChaosGameDescriptionFactory.getDescription("description"));
      ChaosGameDescriptionFactory.removeFileDescription("description");
    }

    @Test
    @DisplayName("Test checkMapForDescription throws exception")
    void checkMapForDescriptionThrowsException() {
      assertThrows(IllegalArgumentException.class, () -> ChaosGameDescriptionFactory.getDescription("description"));
    }

    @Test
    @DisplayName("Test removeFileDescription")
    void removeFileDescription() {
      ChaosGameDescription description = new ChaosGameDescription(new ArrayList<>(), new Complex(0, 0), new Complex(1, 1));
      ChaosGameDescriptionFactory.addNewFileDescription("description", description);
      ChaosGameDescriptionFactory.removeFileDescription("description");
      assertThrows(IllegalArgumentException.class, () -> ChaosGameDescriptionFactory.getDescription("description"));
    }

    @Test
    @DisplayName("Test addNewFileDescription throws exception")
    void addNewFileDescriptionThrowsException() {
      assertThrows(IllegalArgumentException.class, () -> ChaosGameDescriptionFactory.addNewFileDescription("description", null));
    }
  }
}