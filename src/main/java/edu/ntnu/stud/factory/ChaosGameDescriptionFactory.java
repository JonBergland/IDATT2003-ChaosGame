package edu.ntnu.stud.factory;

import edu.ntnu.stud.chaos.ChaosGameDescription;
import edu.ntnu.stud.math.Complex;
import edu.ntnu.stud.math.Matrix2x2;
import edu.ntnu.stud.math.Vector2D;
import edu.ntnu.stud.transform.AffineTransform2D;
import edu.ntnu.stud.transform.JuliaTransform;
import edu.ntnu.stud.transform.Transform2D;
import edu.ntnu.stud.utils.FractalType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Factory class for creating ChaosGameDescription objects.
 * This class provides methods for creating different types of ChaosGameDescription objects
 * based on fractal types and configurations.
 */
public class ChaosGameDescriptionFactory {

  /** HashMap to store ChaosGameDescription objects. */
  private static final HashMap<String, ChaosGameDescription> chaosGameDescriptionHashMap
      = new HashMap<>();

  /** Private constructor to prevent instantiation. */
  private ChaosGameDescriptionFactory() {
  }

  /**
   * Get a ChaosGameDescription object based on the description type.
   *
   * @param description the description type of the chaos game.
   * @return a ChaosGameDescription object.
   * @throws IllegalArgumentException if the description type is invalid.
   */
  public static ChaosGameDescription getDescription(String description)
      throws IllegalArgumentException {
    return switch (description) {
      case FractalType.SIERPINSKI -> getSierpinskiDescription();
      case FractalType.BARNSLEY -> getBarnsleyDescription();
      case FractalType.JULIA -> getJuliaDescription();
      case FractalType.MANDELBROT -> getMandelbrotDescription();
      default -> checkMapForDescription(description);
    };
  }

  /**
   * Retrieves a ChaosGameDescription object from the
   * factory's map based on the given description name.
   * If the description name exists in the map, the
   * corresponding ChaosGameDescription object is returned.
   * If the description name does not exist in the map, an IllegalArgumentException is thrown.
   *
   * @param descriptionName the name of the description.
   * @return the ChaosGameDescription object corresponding to the description name.
   * @throws IllegalArgumentException if the description name does not exist in the map.
   */
  private static ChaosGameDescription checkMapForDescription(String descriptionName)
      throws IllegalArgumentException {
    String description = descriptionName.toLowerCase();
    if (chaosGameDescriptionHashMap.containsKey(description)) {
      return chaosGameDescriptionHashMap.get(description);
    } else {
      throw new IllegalArgumentException("Invalid description type");
    }
  }

  /**
   * Adds a new ChaosGameDescription object to the factory.
   *
   * @param descriptionName the name of the description.
   * @param description the ChaosGameDescription object to add.
   * @throws IllegalArgumentException if the ChaosGameDescription is null.
   */
  public static void addNewFileDescription(String descriptionName,
                                           ChaosGameDescription description)
      throws IllegalArgumentException {
    if (description == null) {
      throw new IllegalArgumentException("ChaosGameDescription cannot be null");
    }
    chaosGameDescriptionHashMap.put(descriptionName, description);
  }

  /**
   * Remove a ChaosGameDescription object from the HashMap.
   *
   * @param descriptionName the name of the description to remove.
   */
  public static void removeFileDescription(String descriptionName) {
    chaosGameDescriptionHashMap.remove(descriptionName);
  }


  /**
   * Get a ChaosGameDescription object for the Julia set with a given constant.
   *
   * @param c the complex number constant for the Julia set.
   * @return a ChaosGameDescription object for the Julia set with the given constant.
   */
  public static ChaosGameDescription getJuliaDescriptionWithC(Complex c) {
    ChaosGameDescription juliaDescription = getJuliaDescription();

    List<Transform2D> juliaTransforms = new ArrayList<>();
    juliaTransforms.add(new JuliaTransform(c, 1));
    juliaTransforms.add(new JuliaTransform(c, -1));
    juliaDescription.setTransforms(juliaTransforms);

    return juliaDescription;
  }

  /**
   * Creates and returns a ChaosGameDescription object for the Julia set.
   * The Julia set is generated using a complex number constant (-0.74543 + 0.11301i).
   *
   * @return a ChaosGameDescription object for the Julia set.
   */
  private static ChaosGameDescription getJuliaDescription() {
    Vector2D minCoords = new Vector2D(-1.6, -1);
    Vector2D maxCoords = new Vector2D(1.6, 1);

    Complex constant = new Complex(-0.74543, 0.11301);

    JuliaTransform transform1 = new JuliaTransform(constant, 1);
    JuliaTransform transform2 = new JuliaTransform(constant, -1);

    return new ChaosGameDescription(List.of(
        transform1, transform2), minCoords, maxCoords);
  }

  /**
   * Creates and returns a ChaosGameDescription object for the Sierpinski triangle.
   * The Sierpinski triangle is generated using three affine transformations.
   *
   * @return a ChaosGameDescription object for the Sierpinski triangle.
   */
  private static ChaosGameDescription getSierpinskiDescription() {
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(1, 1);

    AffineTransform2D transform1 = new AffineTransform2D(
        new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0, 0));
    AffineTransform2D transform2 = new AffineTransform2D(
        new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.25, 0.5));
    AffineTransform2D transform3 = new AffineTransform2D(
        new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.5, 0));

    return new ChaosGameDescription(Arrays.asList(
        transform1, transform2, transform3), minCoords, maxCoords);
  }

  /**
   * Creates and returns a ChaosGameDescription object for the Barnsley fern fractal.
   * The Barnsley fern is generated using four affine transformations.
   *
   * @return a ChaosGameDescription object for the Barnsley fern.
   */
  private static ChaosGameDescription getBarnsleyDescription() {
    Vector2D minCoords = new Vector2D(-2.65, 0);
    Vector2D maxCoords = new Vector2D(2.65, 10);

    AffineTransform2D transform1 = new AffineTransform2D(
        new Matrix2x2(0, 0, 0, 0.16), new Vector2D(0, 0));
    AffineTransform2D transform2 = new AffineTransform2D(
        new Matrix2x2(0.85, 0.04, -0.04, 0.85), new Vector2D(0, 1.6));
    AffineTransform2D transform3 = new AffineTransform2D(
        new Matrix2x2(0.2, -0.26, 0.23, 0.22), new Vector2D(0, 1.6));
    AffineTransform2D transform4 = new AffineTransform2D(
        new Matrix2x2(-0.15, 0.28, 0.26, 0.24), new Vector2D(0, 0.44));

    return new ChaosGameDescription(Arrays.asList(
        transform1, transform2, transform3, transform4), minCoords, maxCoords);

  }

  /**
   * Get a ChaosGameDescription object for the Mandelbrot set.
   *
   * @return a ChaosGameDescription object for the Mandelbrot set.
   */
  private static ChaosGameDescription getMandelbrotDescription() {
    JuliaTransform juliaTransform = new JuliaTransform(new Complex(0, 0), 1);
    List<Transform2D> juliaList = new ArrayList<>();
    juliaList.add(juliaTransform);
    return new ChaosGameDescription(
          juliaList,
        new Complex(-2.00, -1.12),
        new Complex(0.47, 1.12));
  }
}
