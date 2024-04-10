package edu.ntnu.stud.chaos;

import edu.ntnu.stud.math.Complex;
import edu.ntnu.stud.math.Matrix2x2;
import edu.ntnu.stud.math.Vector2D;
import edu.ntnu.stud.transform.AffineTransform2D;
import edu.ntnu.stud.transform.JuliaTransform;
import edu.ntnu.stud.transform.Transform2D;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
    Path filePath = Paths.get("src/main/resources/file/" + path);
    try(Scanner scanner = new Scanner(filePath)) {

      scanner.useDelimiter("#.*(\r\n|\r|\n|$)");

      /** Read the transformation type */
      String transformationType = scanner.next().trim();

      // Read the lower left coordinates
      String lowerLeftLine = scanner.next().trim();
      String[] lowerLeftCoords = lowerLeftLine.split(", ");
      double lowerLeftX = Double.parseDouble(lowerLeftCoords[0].trim());
      double lowerLeftY = Double.parseDouble(lowerLeftCoords[1].trim());

      // Read the upper right coordinates
      String upperRightLine = scanner.next().trim();
      String[] upperRightCoords = upperRightLine.split(", ");
      double upperRightX = Double.parseDouble(upperRightCoords[0].trim());
      double upperRightY = Double.parseDouble(upperRightCoords[1].trim());

      Vector2D minCoords = new Vector2D(lowerLeftX, lowerLeftY);
      Vector2D maxCoords = new Vector2D(upperRightX, upperRightY);

      List<Transform2D> transformation = new ArrayList<>();
      switch (transformationType) {
        case "Julia":

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
        case "Affine2D":
          // Skip the first line since it contains the type of transform
          scanner.nextLine();

          while (scanner.hasNext() && !scanner.hasNext("#")) {
            String line = scanner.next().split("#")[0].trim();
            if (!line.isEmpty() && !line.startsWith("#")) {
              double[] transform = Arrays.stream(line.split(","))
                  .mapToDouble(Double::parseDouble)
                  .toArray();

              // 4 first elements become a matrix
              Matrix2x2 matrix = new Matrix2x2(transform[0], transform[1], transform[2], transform[3]);
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

      return new ChaosGameDescription(transformation, minCoords, maxCoords);

    } catch (NumberFormatException | NoSuchElementException | IOException e) {
      throw new IOException("Wrong format, no such element or file not found:" + e + path);
    }
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
      bufferedWriter.write(description.getTransforms().getFirst() instanceof AffineTransform2D ? "Affine2D" : "Julia");
      bufferedWriter.newLine();

      Vector2D minCoords = description.getMinCoords();
      bufferedWriter.write(minCoords.toString());
      bufferedWriter.newLine();

      Vector2D maxCoords = description.getMaxCoords();
      bufferedWriter.write(maxCoords.toString());
      bufferedWriter.newLine();

      for (Transform2D transform : description.getTransforms()) {
        for (int i = 0; i < 6; i++) {
          bufferedWriter.write(transform.toString());
        }
        bufferedWriter.newLine();
      }
    } catch (IOException | NoSuchElementException e) {
      throw new IOException("Error while writing input to the file" + e);
    }
  }
}