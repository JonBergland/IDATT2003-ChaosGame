/**
 * Test class for {@link ChaosGameFileHandler}.
 */

package edu.ntnu.stud.chaos;

import edu.ntnu.stud.math.Complex;
import edu.ntnu.stud.math.Matrix2x2;
import edu.ntnu.stud.math.Vector2D;
import edu.ntnu.stud.transform.AffineTransform2D;
import edu.ntnu.stud.transform.JuliaTransform;
import edu.ntnu.stud.transform.Transform2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ChaosGameFileHandler Tests")
class ChaosGameFileHandlerTest {

  /** Handles file operations related to the Chaos Game,
   * such as reading from and writing to files. */
  ChaosGameFileHandler fileHandler;

  /** Represents the file path for the Julia set configuration file. */
  String juliaFilePath;

  /** Represents the file path for the Sierpinski triangle configuration file. */
  String sierpinskiFilePath;

  /** Represents the file path for the Barnsley Fern configuration file. */
  String barnsleyFilePath;

  /** Represents the file path for a non-existent file
   * used for testing file handling exceptions. */
  String nonExistentFilePath;

  /** Represents the file path for a test file
   * used for simulating exceptions during file operations. */
  String testExceptionFile;

  /** Represents an affine transformation in 2D space. */
  AffineTransform2D affineTransform2D;

  /** Represents a Julia transformation in 2D space.*/
  JuliaTransform juliaTransform;

  /**
   * Sets up the test environment.
   */
  @BeforeEach
  void setUp() {
    fileHandler = new ChaosGameFileHandler();
    juliaFilePath = "JuliaSet.txt";
    sierpinskiFilePath = "SierpinskiTriangle.txt";
    barnsleyFilePath = "BarnsleyFern.txt";
    nonExistentFilePath = "NonExistent.txt";
    testExceptionFile = "TestExceptionFile.txt";
    affineTransform2D = new AffineTransform2D(null, null);
    juliaTransform = new JuliaTransform(null,1);
  }

  /**
   * Tests for reading from file.
   */
  @Nested
  @DisplayName("Reading from file")
  class ReadFromFileTests {

    /**
     * Tests reading from file for Julia set.
     */
    @Test
    @DisplayName("Read JuliaSet from File")
    void readFromFile_Julia() {
      try {
        ChaosGameDescription description = fileHandler.readFromFile(juliaFilePath);

        // Assert
        assertNotNull(description, "ChaosGameDescription should not be null");
        assertEquals(juliaTransform.getClass(), description.getTransformationType(), "Transformation type should be Julia");
        assertEquals(2, description.getTransforms().size(), "Expected number of transforms mismatch");

      } catch (IOException e) {
        fail("File not found: " + e.getMessage());
      }
    }

    /**
     * Tests reading from file for Affine transformation.
     */
    @Test
    @DisplayName("Read AffineTransformation from File")
    void readFromFile_Affine() {
      try {
        ChaosGameDescription description = fileHandler.readFromFile(sierpinskiFilePath);

        // Assert
        assertNotNull(description, "ChaosGameDescription should not be null");
        assertEquals(affineTransform2D.getClass(), description.getTransformationType(), "Transformation type should be Affine2D");
        assertTrue(description.getTransforms().get(0) instanceof AffineTransform2D,
            "Transform should be instance of AffineTransform2D");
        assertEquals(3, description.getTransforms().size(),
            "Expected number of transforms mismatch");

      } catch (IOException e) {
        fail("File not found: " + e.getMessage());
      }
    }

    /**
     * Tests reading from a non-existent file.
     */
    @Test
    @DisplayName("Read from Non-Existent File")
    void readFromFile_NonExistentFile() {
      assertThrows(IOException.class, () -> fileHandler.readFromFile(nonExistentFilePath),
          "Should throw IOException for non-existent file");
    }

    /**
     * Tests reading from a file with default parameters.
     */
    @Test
    @DisplayName("Read from Default File")
    void readFromFile_Default() {
      assertThrows(IOException.class, () -> fileHandler.readFromFile(testExceptionFile),
          "Should throw IOException for non-existent transformation type");
    }

  }

  /**
   * Tests for writing to file.
   */
  @Nested
  @DisplayName("Writing to file")
  class WriteToFileTests {
    /**
     * Tests writing to a file with error handling.
     */
    @Test
    @DisplayName("Write Error Handling")
    void writeToFile_Error() {
      ChaosGameDescription description = new ChaosGameDescription(new ArrayList<>(), new Vector2D(2,1), new Vector2D(2,4));
      assertThrows(IOException.class, () -> fileHandler.writeToFile(nonExistentFilePath, description),
          "Should throw IOException for non-existent file");
    }

    /**
     * Tests writing to a file with null parameters.
     */
    @Test
    @DisplayName("Write Null Parameters")
    void writeToFile_Null() {
      assertThrows(NullPointerException.class, () -> fileHandler.writeToFile(nonExistentFilePath, null),
          "Should throw NullPointerException for empty file.");
    }

    /**
     * Tests writing JuliaSet to a file.
     */
    @Test
    @DisplayName("Write JuliaSet to File")
    void writeToFile_Julia() {
      try {
        List<Transform2D> transform2DList = Arrays.asList(
            new JuliaTransform(new Complex(0.5,0.5),1),
            new JuliaTransform(new Complex(-0.5,-0.5),-1)
        );

        ChaosGameDescription description =
            new ChaosGameDescription(transform2DList,
                new Vector2D(-1,-1), new Vector2D(1,1));

        fileHandler.writeToFile(juliaFilePath, description);

        ChaosGameDescription readDescription = fileHandler.readFromFile(juliaFilePath);
        assertNotNull(readDescription);
        assertEquals(juliaTransform.getClass(), readDescription.getTransformationType());
        assertEquals(2, readDescription.getTransforms().size());
        assertTrue(Files.exists((Paths.get(juliaFilePath))));

      } catch (IOException e) {
        fail("Error writing to file: " + e.getMessage());

      } finally {

        try {
          Files.deleteIfExists(Paths.get(juliaFilePath));

        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    /**
     * Tests writing AffineTransformation to a file.
     */
    @Test
    @DisplayName("Write Affine Transformation to File")
    void writeToFile_Affine() {
      try {
        List<Transform2D> transform2DList = Arrays.asList(
            new AffineTransform2D(new Matrix2x2(1,0,0,1), new Vector2D(1,1)),
            new AffineTransform2D(new Matrix2x2(0,1,-1,0), new Vector2D(0,0))
        );

        ChaosGameDescription description =
            new ChaosGameDescription(transform2DList,
                new Vector2D(-1,-1), new Vector2D(1,1));

        fileHandler.writeToFile(sierpinskiFilePath, description);

        ChaosGameDescription readDescription = fileHandler.readFromFile(sierpinskiFilePath);
        assertNotNull(readDescription);
        assertEquals(affineTransform2D.getClass(), readDescription.getTransformationType());
        assertEquals(3, readDescription.getTransforms().size());
        assertTrue(Files.exists((Paths.get(sierpinskiFilePath))));

      } catch (IOException e) {
        fail("Error writing to file: " + e.getMessage());

      } finally {

        try {
          Files.deleteIfExists(Paths.get(sierpinskiFilePath));

        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
