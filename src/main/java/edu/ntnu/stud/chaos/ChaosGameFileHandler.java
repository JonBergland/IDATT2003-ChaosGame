package edu.ntnu.stud.chaos;

import edu.ntnu.stud.math.Complex;
import edu.ntnu.stud.math.Matrix2x2;
import edu.ntnu.stud.math.Vector2D;
import edu.ntnu.stud.transform.AffineTransform2D;
import edu.ntnu.stud.transform.JuliaTransform;
import edu.ntnu.stud.transform.Transform2D;
import edu.ntnu.stud.utils.FractalType;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The ChaosGameFileHandler class provides methods for reading and
 * writing Chaos Game descriptions from/to files.
 * It supports two types of transformations: Julia and Affine2D.
 */
public class ChaosGameFileHandler {

  /**
   * Reads a Chaos Game description from a file.
   *
   * @param path The path to the file containing the Chaos Game description.
   * @return A ChaosGameDescription object representing the read Chaos Game description.
   * @throws IOException If the specified file is not found.
   */
  public ChaosGameDescription readFromFile(String path) throws IOException {
    Path filePath = Paths.get(path);
    List<Transform2D> transformation = new ArrayList<>();
    Vector2D minCoords = null;
    Vector2D maxCoords = null;

    try (Scanner scanner = new Scanner(filePath)) {

      scanner.useDelimiter("\n");

      if (!scanner.hasNext()) {
        throw new IOException("File is empty.");
      }

      // Read the transformation type
      String transformationType = scanner.next().split("#")[0].trim().toLowerCase();

      if (!scanner.hasNext()) {
        throw new IllegalArgumentException("Missing coordinates.");
      }

      minCoords = parseTheCoords(scanner.next());
      maxCoords = parseTheCoords(scanner.next());

      switch (transformationType) {
        case FractalType.JULIA:

          while (scanner.hasNext() && !scanner.hasNext("#")) {
            String line = scanner.next().split("#")[0].trim();
            if (!line.isEmpty() && !line.startsWith("#")) {
              String[] constantElement = line.split(", ");

              double realPart = Double.parseDouble(constantElement[0].trim());
              double imaginaryPart = Double.parseDouble(constantElement[1].trim());
              Complex constant = new Complex(realPart, imaginaryPart);

              int sign = 1;

              transformation.add(new JuliaTransform(constant, sign));
              transformation.add(new JuliaTransform(constant, -sign));
            }
          }
          break;
        case FractalType.AFFINE2D:
          // Skip the first line since it contains the type of transform
          scanner.nextLine();

          while (scanner.hasNext() && !scanner.hasNext("#")) {
            String line = scanner.next().split("#")[0].trim();
            if (!line.isEmpty() && !line.startsWith("#")) {
              double[] transform = Arrays.stream(line.split(","))
                  .mapToDouble(Double::parseDouble)
                  .toArray();

              // 4 first elements become a matrix
              Matrix2x2 matrix
                  = new Matrix2x2(transform[0], transform[1], transform[2], transform[3]);
              // Last 2 elements (b) become a vector
              Vector2D vector = new Vector2D(transform[4], transform[5]);
              // Then put them together into an AffineTransform2D
              AffineTransform2D affine2d = new AffineTransform2D(matrix, vector);
              transformation.add(affine2d);
            }
          }
          break;

        default:
          throw new IOException("Unknown transformation type: " + transformationType);
      }

    } catch (NoSuchElementException | IOException | IllegalStateException e) {
      throw new IOException("Error processing file.", e);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid format in the file.", e);
    }
    return new ChaosGameDescription(transformation, minCoords, maxCoords);
  }

  /**
   * Parses a line to extract coordinates and returns a Vector2D object.
   *
   * @param line The line containing the coordinates.
   * @return A Vector2D object representing the coordinates.
   * @throws IllegalArgumentException If the format of the coordinates is invalid.
   */
  private static Vector2D parseTheCoords(String line) throws IllegalArgumentException {
    String[] parts = line.split("#");
    double[] coords = Arrays.stream(parts[0].split(",")).mapToDouble(Double::parseDouble).toArray();
    if (coords.length != 2) {
      throw new IllegalArgumentException("Invalid coordinates format.");
    }
    return new Vector2D(coords[0], coords[1]);
  }

  /**
   * Writes a Chaos Game description to a file.
   *
   * @param path        The path to the file to which the Chaos Game description will be written.
   * @param description The ChaosGameDescription object to be written to the file.
   * @throws IOException If an I/O error occurs while writing to the file.
   */
  public void writeToFile(String path, ChaosGameDescription description) throws IOException {

    if (description == null) {
      throw new NullPointerException("ChaosGameDescription cannot be null.");
    }
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
      bufferedWriter.write(description.getTransforms().getFirst() instanceof AffineTransform2D
          ? FractalType.AFFINE2D : FractalType.JULIA);
      bufferedWriter.newLine();

      Vector2D minCoords = description.getMinCoords();
      bufferedWriter.write(minCoords.toString());
      bufferedWriter.newLine();

      Vector2D maxCoords = description.getMaxCoords();
      bufferedWriter.write(maxCoords.toString());
      bufferedWriter.newLine();

      for (Transform2D transform : description.getTransforms()) {
        bufferedWriter.write(transform.toString());
        bufferedWriter.newLine();
      }
    } catch (IOException | NoSuchElementException e) {
      throw new IOException("Error while writing input to the file" + e);
    }
  }
}