package edu.ntnu.stud.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the fractal types.
 * It contains the fractal types Sierpinski, Barnsley and Julia.
 */
public class FractalType {

  /** Represents the Sierpinski fractal type. */
  public static final String SIERPINSKI = "sierpinski";

  /** Represents the Barnsley fractal type. */
  public static final String BARNSLEY = "barnsley";

  /** Represents the Julia fractal type. */
  public static final String JULIA = "julia";

  /** Represents the Affine2D fractal type. */
  public static final String AFFINE2D = "affine2d";

  /** Represents the Mandelbrot fractal type. */
  public static final String MANDELBROT = "mandelbrot";

  protected static Set<String> fractalSet =
      new HashSet<>(Arrays.asList(SIERPINSKI, BARNSLEY, JULIA, MANDELBROT));

  /**
   * Constructs a new FractalType. This constructor is empty because it is not needed.
   * The class is a utility class and should not be instantiated.
   */
  private FractalType() {
    //This constructor is empty
  }

  /**
   * Get a set of the fractal types.
   *
   * @return a set of the fractal types.
   */
  public static Set<String> getFractalSet() {
    return fractalSet;
  }

  /**
   * Add a fractal to the set of fractals.
   *
   * @param fractal the string representing the name of the fractal to add.
   */
  public static void addFractal(String fractal) {
    if (!fractalSet.contains(fractal) && !fractal.isBlank()) {
      fractalSet.add(fractal);
    }
  }
}