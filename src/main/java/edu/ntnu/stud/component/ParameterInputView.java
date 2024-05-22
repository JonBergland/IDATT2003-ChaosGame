package edu.ntnu.stud.component;

import static javafx.stage.Screen.getPrimary;

import edu.ntnu.stud.chaos.ChaosGame;
import edu.ntnu.stud.math.Complex;
import edu.ntnu.stud.observer.Observer;
import edu.ntnu.stud.transform.AffineTransform2D;
import edu.ntnu.stud.transform.JuliaTransform;
import edu.ntnu.stud.utils.ButtonEnum;
import edu.ntnu.stud.utils.FillerPane;
import edu.ntnu.stud.utils.FractalType;
import edu.ntnu.stud.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


/**
 * This class represents the parameter input view.
 * It contains the input fields for the chaos game parameters.
 * Goal: act as a view for the chaos game parameters.
 */
public class ParameterInputView extends View implements Observer {

  /** The ChaosGame instance this view is associated with.*/
  private final ChaosGame chaosGame;

  /** The main pane for organizing the parameter input view. */
  protected BorderPane borderPane;

  /** The main container for all visual elements of the parameter input view. */
  protected StackPane stackPane;

  /** Input fields for the minimum coordinates of the chaos game. */
  private List<TextField> minCoordsInput;

  /** Input fields for the maximum coordinates of the chaos game. */
  private List<TextField> maxCoordsInput;

  /** Input fields for the transformation matrix values of the chaos game. */
  private List<List<TextField>> matrixInputs;

  /** Example Julia values for demonstration purposes. */
  private Map<String, Complex> juliaExampleValues;

  /** Input field for setting the number of steps in the chaos game. */
  private TextField stepsInput;

  /** Button to update the parameters of the chaos game. */
  private Button updateButton;

  /** Container for organizing parameter input elements. */
  private VBox parameterVbox;

  /** Container for organizing transformation selection elements. */
  private HBox transformsVbox;

  /** Combo box for selecting the type of fractal. */
  private ComboBox<String> fractalComboBox;

  /** Label for displaying error messages. */
  private Label errorLabel;

  /** Style class for input box elements. */
  private static final String INPUT_BOX_STYLE = "input-box";

  /** Style class for headline text elements. */
  private static final String HEADLINE_TEXT_STYLE = "headline-text";

  private static final String BROWN = "brown";

  /** The type of transformation (e.g., Julia, Affine). */
  private String transformation;

  /** Color picker for selecting colors in the chaos game. */
  private ColorPicker colorPicker;

  /**
   * Constructor for the ParameterInputView class.
   *
   * @param chaosGame the ChaosGame instance to associate with this view.
   */
  public ParameterInputView(ChaosGame chaosGame) {
    if (chaosGame == null) {
      throw new IllegalArgumentException("ChaosGame cannot be null");
    }
    borderPane = new BorderPane();
    stackPane = new StackPane();
    borderPane.setCenter(stackPane);
    this.chaosGame = chaosGame;
    setup();
  }

  /**
   * Get the pane containing the parameter input view.
   *
   * @return The pane containing the parameter input view.
   */
  @Override
  public Pane getPane() {
    return stackPane;
  }

  /**
   * Set up the parameter input view.
   */
  @Override
  public void setup() {
    fractalComboBox = new ComboBox<>();
    fractalComboBox.setMinSize(180, 30);
    fractalComboBox.getStyleClass().add("blue");

    colorPicker = colorPicker();

    fractalComboBox.setOnAction(event ->
        notifyObservers(ButtonEnum.FRACTAL, fractalComboBox.getValue().toLowerCase())
    );

    errorLabel = createErrorLabel();

    stepsInput = createTextFields(10000);

    parameterVbox = new VBox();
    parameterVbox.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, null, new BorderWidths(2))));

    transformsVbox = new HBox();
    transformsVbox.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
    transformsVbox.setMinWidth(200);

    juliaExampleValues = createExampleJuliaValues();
  }

  /**
   * Reset the parameter input view.
   */
  @Override
  public void resetPane() {
    parameterVbox.getChildren().clear();
    transformsVbox.getChildren().clear();
    stackPane.getChildren().clear();
  }

  /**
   * Renders the parameter input view with various input fields and options.
   * The rendering includes setting up the fractal selection options, coordinate input fields,
   * step selection, transformation type selection, error label, and color picker.
   */
  @Override
  public void render() {

    if (fractalComboBox.getValue().equalsIgnoreCase(FractalType.MANDELBROT)) {
      transformsVbox.getChildren().addAll(new FillerPane(), new FillerPane(), new FillerPane());
    } else if (transformation.equals(FractalType.JULIA)) {
      transformsVbox.getChildren().addAll(new FillerPane(), juliaVbox(), new FillerPane());
    } else {
      transformsVbox.getChildren().addAll(new FillerPane(), createAffineTransformationVbox(),
          new FillerPane());
    }

    HBox minCoordsHbox = new HBox();
    minCoordsHbox.getChildren().addAll(minCoordsInput);
    minCoordsHbox.getChildren().addFirst(new FillerPane());
    minCoordsHbox.getChildren().add(new FillerPane());

    HBox maxCoordsHbox = new HBox();
    maxCoordsHbox.getChildren().addAll(maxCoordsInput);
    maxCoordsHbox.getChildren().addFirst(new FillerPane());
    maxCoordsHbox.getChildren().add(new FillerPane());

    HBox errorLabelBox = new HBox();
    errorLabelBox.getChildren().addAll(errorLabel);

    Text chooseSteps = createText("Choose Steps");
    Text minimalCoordinate = createText("Choose the Minimal Coordinate");
    Text height = createText("Choose the Maximal Coordinate");


    parameterVbox.setPadding(new Insets(10, 10, 10, 10));
    parameterVbox.setSpacing(15);
    parameterVbox.getChildren().addAll(new FillerPane(), createFractalVbox(),
        minimalCoordinate, minCoordsHbox,
        height, maxCoordsHbox,
        chooseSteps, stepsInput,
        transformsVbox, colorPicker(), updateButton, readToFromFile(),
        errorLabelBox);
    parameterVbox.setAlignment(Pos.CENTER);
    parameterVbox.setMaxHeight(getPrimary().getVisualBounds().getHeight() * 0.35);

    transformsVbox.setMinWidth(200);

    HBox mainBox = new HBox();
    mainBox.setSpacing(10);
    mainBox.getChildren().addAll(parameterVbox, transformsVbox);
    stackPane.getChildren().add(mainBox);
  }

  /**
   * Method to set the example Julia values.
   *
   * @return a map of example Julia values
   */
  public Map<String, Complex> createExampleJuliaValues() {
    Map<String, Complex> exampleValues = new HashMap<>();

    Complex c1 = new Complex(-0.70176, -0.3842);
    Complex c2 = new Complex(-0.835, -0.2321);
    Complex c3 = new Complex(-0.8, 0.156);
    Complex c4 = new Complex(-0.7269, 0.1889);
    Complex c5 = new Complex(0, 0.8);
    Complex c6 = new Complex(0.285, 0.01);
    Complex c7 = new Complex(0.35, 0.35);
    Complex c8 = new Complex(0.4, 0.4);

    exampleValues.put(c1.toString(), c1);
    exampleValues.put(c2.toString(), c2);
    exampleValues.put(c3.toString(), c3);
    exampleValues.put(c4.toString(), c4);
    exampleValues.put(c5.toString(), c5);
    exampleValues.put(c6.toString(), c6);
    exampleValues.put(c7.toString(), c7);
    exampleValues.put(c8.toString(), c8);

    return exampleValues;
  }

  /**
   * Method to create an error label used to display error messages.
   *
   * @return a Label object representing the error label
   */
  private Label createErrorLabel() {
    Label label = new Label("");
    label.setTextFill(Color.RED);
    label.setPrefWidth(200);
    label.setMinHeight(60);
    label.setWrapText(true);
    return label;
  }

  /**
   * Method to update input parameters for the chaos game with a new chaos game, steps
   * and type of fractal.
   *
   * @param chaosGame   the chaos game to update the parameters for
   */
  public void updatePlaceHolderParameters(ChaosGame chaosGame) {
    // Inputs
    minCoordsInput = new ArrayList<>();
    minCoordsInput.add(createTextFields(chaosGame.getDescription().getMinCoords().getX0()));
    minCoordsInput.add(createTextFields(chaosGame.getDescription().getMinCoords().getX1()));

    maxCoordsInput = new ArrayList<>();
    maxCoordsInput.add(createTextFields(chaosGame.getDescription().getMaxCoords().getX0()));
    maxCoordsInput.add(createTextFields(chaosGame.getDescription().getMaxCoords().getX1()));

    matrixInputs = new ArrayList<>();

    if (chaosGame.getDescription().getTransforms().isEmpty()) {
      matrixInputs = new ArrayList<>();

    } else if (Objects.equals(chaosGame.getDescription()
        .getTransformationType(), JuliaTransform.class)) {

      JuliaTransform juliaTransform = (JuliaTransform) chaosGame
          .getDescription().getTransforms().getFirst();
      List<TextField> juliaInputs = new ArrayList<>();
      juliaInputs.add(createTextFields(juliaTransform.getPoint().getX0()));
      juliaInputs.add(createTextFields(juliaTransform.getPoint().getX1()));
      matrixInputs.add(juliaInputs);
      transformation = FractalType.JULIA;
    } else {
      List<AffineTransform2D> affineTransforms = new ArrayList<>();
      chaosGame.getDescription().getTransforms().forEach(transform ->
          affineTransforms.add((AffineTransform2D) transform)
      );

      affineTransforms.forEach(transform -> {
        List<TextField> affineInputs = new ArrayList<>();
        affineInputs.add(createTextFields(transform.getMatrix().getA00()));
        affineInputs.add(createTextFields(transform.getMatrix().getA01()));
        affineInputs.add(createTextFields(transform.getMatrix().getA10()));
        affineInputs.add(createTextFields(transform.getMatrix().getA11()));

        affineInputs.add(createTextFields(transform.getVector().getX0()));
        affineInputs.add(createTextFields(transform.getVector().getX1()));

        matrixInputs.add(affineInputs);
        transformation = FractalType.AFFINE2D;
      });
    }

    updateButton = createUpdateButton();
  }

  /**
   * Create a Text object with the specified headline text.
   *
   * @param headline the headline text for the Text object
   * @return a Text object with the specified headline text
   */
  private Text createText(String headline) {
    Text text = new Text(headline);
    text.getStyleClass().add(HEADLINE_TEXT_STYLE);
    return text;
  }

  /**
   * Create a VBox containing fractal selection options.
   *
   * @return a VBox object representing fractal selection options
   */
  private VBox createFractalVbox() {
    Text chooseTransform = createText("Choose Transform");
    chooseTransform.getStyleClass().add(HEADLINE_TEXT_STYLE);

    FractalType.getFractalSet().forEach(fractalType -> {
      String fractalName = makeFirstLetterUpperCase(fractalType);
      if (!fractalComboBox.getItems().contains(fractalName)) {
        fractalComboBox.getItems().add(fractalName);
      }
    });


    VBox fractalVbox = new VBox(10);
    fractalVbox.getChildren().addAll(chooseTransform, fractalComboBox);
    fractalVbox.setAlignment(Pos.CENTER);

    return fractalVbox;
  }

  /**
   * Create a TextField with a specified placeholder value.
   *
   * @param placeholder the placeholder value for the text field
   * @return a TextField object with the specified placeholder value
   */
  public TextField createTextFields(double placeholder) {
    TextField textField = new TextField();
    textField.setText(String.valueOf(placeholder));
    textField.setMaxHeight(30);
    textField.setMaxWidth(80);
    textField.getStyleClass().add(INPUT_BOX_STYLE);
    addEnterKeyListener(textField);
    return textField;
  }

  /**
   * Create an "Update" button.
   *
   * @return a Button object representing the "Update" button
   */
  public Button createUpdateButton() {
    Button button = new Button("Update");
    button.getStyleClass().add(BROWN);
    button.setOnAction(event -> {
      try {
        int steps = (int) Double.parseDouble(stepsInput.getText());
        if (steps < 0) {
          throw new IllegalArgumentException("Steps must be a positive integer");
        } else if (steps > 1000000) {
          throw new IllegalArgumentException("Steps must be less than 1000000");
        } else {
          errorLabel.setText("");
          updateParameters();
        }
      } catch (NumberFormatException ex) {
        errorLabel.setText("Please enter a valid number: " + ex.getMessage());
      } catch (IllegalArgumentException ex) {
        errorLabel.setText(ex.getMessage());
      }
    });
    return button;
  }

  /**
   * Update the parameters of the chaos game.
   */
  private void updateParameters() {
    notifyObservers(ButtonEnum.STEPS, stepsInput.getText());

    String minCoords = minCoordsInput.get(0).getText() + ", "
        + minCoordsInput.get(1).getText() + "\n";

    String maxCoords = maxCoordsInput.get(0).getText() + ", "
        + maxCoordsInput.get(1).getText();

    notifyObservers(ButtonEnum.COORDS, minCoords + maxCoords);

    if (fractalComboBox.getValue().equalsIgnoreCase(FractalType.MANDELBROT)) {
      notifyObservers(ButtonEnum.TRANSFORM, FractalType.MANDELBROT);

    } else if (transformation.equals(FractalType.JULIA)) {
      String juliaValues = matrixInputs.getFirst().get(0).getText() + ", "
          + matrixInputs.getFirst().get(1).getText();
      notifyObservers(ButtonEnum.TRANSFORM, juliaValues);

    } else {
      StringBuilder affineValues = new StringBuilder();
      matrixInputs.forEach(transform -> {
        if (transform.size() != 6) {
          throw new IllegalArgumentException("Invalid number of affine transformation inputs");
        }
        String values = transform.get(0).getText() + ", " + transform.get(1).getText() + ", "
            + transform.get(2).getText() + ", " + transform.get(3).getText() + ", "
            + transform.get(4).getText() + ", " + transform.get(5).getText();
        affineValues.append(values).append("\n");
      });
      notifyObservers(ButtonEnum.TRANSFORM, affineValues.toString());
    }

    notifyObservers(ButtonEnum.PARAMETER_CHANGE, "Update");
  }

  /**
   * Method to simulate button click when Enter is pressed
   * in text fields.
   *
   * @param textField the TextField to which the Enter key listener is added
   */
  private void addEnterKeyListener(TextField textField) {
    textField.setOnKeyPressed(event -> {
      if (event.getCode().equals(KeyCode.ENTER)) {
        updateParameters();
      }
    });
  }

  /**
   * Create a VBox containing Julia transform options.
   *
   * @return a VBox object representing Julia transform options
   */
  private VBox juliaVbox() {
    // Create Combo box for showcasing different Julia Values
    ComboBox<String> exampleValues = new ComboBox<>();
    exampleValues.getItems().addAll(juliaExampleValues.keySet());

    exampleValues.setOnAction(event -> {
      String selectedValue = exampleValues.getValue();
      Complex complex = juliaExampleValues.get(selectedValue);
      matrixInputs.getFirst().get(0).setText(String.valueOf(complex.getX0()));
      matrixInputs.getFirst().get(1).setText(String.valueOf(complex.getX1()));
    });

    Text exampleValuesText = createText("Example Julia Values");
    exampleValuesText.getStyleClass().add(HEADLINE_TEXT_STYLE);

    Text complex = createText("Complex Constant:");
    complex.getStyleClass().add(INPUT_BOX_STYLE);
    Text real = createText("Real:");
    real.getStyleClass().add(INPUT_BOX_STYLE);
    Text imaginary = createText("Imaginary:");
    imaginary.getStyleClass().add(INPUT_BOX_STYLE);

    if (matrixInputs.size() != 1) {
      throw new IllegalArgumentException("Invalid number of Julia transforms");
    }

    VBox juliaVbox = new VBox();
    juliaVbox.setPadding(new Insets(10, 10, 10, 10));
    juliaVbox.setSpacing(10);
    juliaVbox.getChildren().addAll(new FillerPane(),
        exampleValuesText, exampleValues,
        complex, real, matrixInputs.getFirst().getFirst(),
        imaginary, matrixInputs.getFirst().get(1), new FillerPane());
    juliaVbox.setAlignment(Pos.TOP_CENTER);

    return juliaVbox;
  }

  /**
   * Create a VBox containing affine transformation options.
   *
   * @return a VBox object representing affine transformation options
   */
  private VBox createAffineTransformationVbox() {
    VBox affineVbox = new VBox();
    affineVbox.setPadding(new Insets(5, 10, 5, 10));
    affineVbox.setSpacing(5);
    affineVbox.setAlignment(Pos.TOP_CENTER);

    matrixInputs.forEach(transform -> {
      if (transform.size() != 6) {
        throw new IllegalArgumentException("Invalid number of affine transformation inputs");
      }

      HBox firstMatrixRow = new HBox();
      firstMatrixRow.getChildren().addAll(transform.get(0), transform.get(1));
      firstMatrixRow.setSpacing(0.5);
      HBox secondMatrixRow = new HBox();
      secondMatrixRow.getChildren().addAll(transform.get(2), transform.get(3));
      secondMatrixRow.setSpacing(0.5);

      HBox vectorInputsHbox = new HBox();
      vectorInputsHbox.getChildren().addAll(transform.get(4), transform.get(5));
      vectorInputsHbox.setSpacing(0.5);

      Text transformText = createText("Transformation: " + (matrixInputs.indexOf(transform) + 1));
      transformText.getStyleClass().add("transformation-text");

      Text matrixText = createText("Matrix: ");
      matrixText.getStyleClass().add("matrix-text");
      Text vectorText = createText("Vector:");
      vectorText.getStyleClass().add("matrix-text");

      VBox transformVbox = new VBox();
      transformVbox.getChildren().addAll(
          transformText,
          matrixText, firstMatrixRow, secondMatrixRow,
          vectorText, vectorInputsHbox);

      transformVbox.setSpacing(2);

      affineVbox.getChildren().add(transformVbox);
    });
    return affineVbox;
  }

  /**
   * Make the first letter of a string uppercase.
   *
   * @param   string the string to transform
   * @return  the transformed string
   */
  private String makeFirstLetterUpperCase(String string) {
    if (string == null || string.isEmpty()) {
      return string;
    }
    return string.substring(0, 1).toUpperCase() + string.substring(1);
  }

  /**
   * Create a VBox containing file reading and saving options.
   *
   * @return a VBox object representing file reading and saving options
   */
  private HBox readToFromFile() {
    Button saveButton = new Button("Save");
    Button uploadFileButton = new Button("Upload File");

    saveButton.getStyleClass().add(BROWN);
    uploadFileButton.getStyleClass().add(BROWN);

    saveButton.setOnAction(e -> {
      updateParameters();
      notifyObservers(ButtonEnum.SAVEFILE, "Save");

    });

    uploadFileButton.setOnAction(e ->
        notifyObservers(ButtonEnum.READFILE, "Upload File"));

    HBox hbox = new HBox(15);

    hbox.getChildren().addAll(saveButton, uploadFileButton);
    hbox.setAlignment(Pos.CENTER);

    return hbox;
  }

  /**
   * Updates the view based on the specified button action and additional string parameter.
   * This method is invoked when an observable notifies its observers of a change.
   *
   * @param buttonEnum the type of button action that triggered the update
   * @param string     additional string parameter associated with the button action
   */
  @Override
  public void update(ButtonEnum buttonEnum, String string) {
    switch (buttonEnum) {
      case COORDS:
        resetPane();
        updatePlaceHolderParameters(chaosGame);
        break;
      case TRANSFORM:
        resetPane();
        updatePlaceHolderParameters(chaosGame);
        render();
        break;
      case GAME_NAME:
        fractalComboBox.setValue(makeFirstLetterUpperCase(chaosGame.getChaosGameName()));
        break;
      default:
        throw new UnsupportedOperationException("ButtonEnum not supported: " + buttonEnum);
    }
  }

  /**
   * Create a ColorPicker for selecting colors.
   *
   * @return a ColorPicker object for selecting colors
   */
  private ColorPicker colorPicker() {
    colorPicker = new ColorPicker(Color.BLUE);
    colorPicker.setPadding(new Insets(5, 5, 5, 5));
    colorPicker.setMinHeight(40);
    colorPicker.getStyleClass().add("blue");
    colorPicker.setOnAction(e -> {
      Color selectedColor = colorPicker.getValue();
      notifyObservers(ButtonEnum.COLORPICKER, selectedColor.toString());
    });

    return colorPicker;
  }
}
