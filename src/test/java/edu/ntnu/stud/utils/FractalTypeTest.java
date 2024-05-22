package edu.ntnu.stud.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link FractalType} class.
 */
class FractalTypeTest {

  @Test
  void getFractalSet() {
    assertAll("FractalType",
        () -> assertNotNull(FractalType.getFractalSet()),
        () -> assertEquals(4, FractalType.getFractalSet().size(),
            "The set should contain 4 elements"),
        () -> assertTrue(FractalType.getFractalSet().contains(FractalType.SIERPINSKI)),
        () -> assertTrue(FractalType.getFractalSet().contains(FractalType.BARNSLEY)),
        () -> assertTrue(FractalType.getFractalSet().contains(FractalType.JULIA)),
        () -> assertTrue(FractalType.getFractalSet().contains(FractalType.MANDELBROT))
        );
  }

  @Test
  @DisplayName("Add a fractal to the set of fractals")
  void addFractal() {
    FractalType.addFractal("affine2d");
    assertTrue(FractalType.getFractalSet().contains("affine2d"));
  }

  @Test
  @DisplayName("Add a already existing fractal to the set of fractals")
  void addExistingFractal() {
    FractalType.addFractal(FractalType.SIERPINSKI);
    assertEquals(4, FractalType.getFractalSet().size());
  }

  @Test
  @DisplayName("Add a blank fractal to the set of fractals")
  void addBlankFractal() {
    FractalType.addFractal("");
    assertEquals(4, FractalType.getFractalSet().size());
  }
}