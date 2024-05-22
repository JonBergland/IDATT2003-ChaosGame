package edu.ntnu.stud.view;

import static javafx.stage.Screen.getPrimary;

import edu.ntnu.stud.utils.ButtonEnum;
import edu.ntnu.stud.utils.FillerPane;
import edu.ntnu.stud.utils.FractalType;
import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Represents the view for the home page of the application.
 * This view contains buttons for selecting different chaos game types.
 */
public class HomePageView extends View {

  /** The border pane for organizing the layout. */
  protected BorderPane borderPane;

  /** The stack pane for overlaying elements. */
  protected StackPane stackPane;

  /** The button for selecting the Sierpinski triangle game. */
  private final Button sierpinskiTriangleButton;

  /** The button for selecting the Barnsley fern game. */
  private final Button barnsleyFernButton;

  /** The button for selecting the Julia set game. */
  private final Button juliaSetButton;

  /** The button for selecting the Mandelbrot set game. */
  private final Button mandelbrotButton;

  /** The chosen game type. */
  private String chosenGameType;

  /** The label for displaying warnings or messages. */
  private Label warningLabel;

  /** The CSS style for buttons. */
  private static final String BUTTON_STYLE = "chaos-game-button";

  /** The CSS style for checked buttons. */
  private static final String BUTTON_STYLE_CHECKED = "chaos-game-button-checked";

  /**
   * Constructs a new HomePageView.
   * Initializes the layout components and creates buttons for selecting different game types.
   */
  public HomePageView() {
    borderPane = new BorderPane();
    stackPane = new StackPane();
    borderPane.setCenter(stackPane);
    chosenGameType = "";

    sierpinskiTriangleButton = createButton("Sierpinski");
    barnsleyFernButton = createButton("Barnsley");
    juliaSetButton = createButton("Julia");
    mandelbrotButton = createButton("Mandelbrot");
  }

  /**
   * Retrieves the main pane of the home page view.
   *
   * @return the main pane of the home page view
   */
  @Override
  public Pane getPane() {
    return this.borderPane;
  }

  /**
   * Sets up the layout of the home page view, including buttons and images.
   */
  @Override
  public void setup() {
    borderPane.setCenter(centerPane());
    borderPane.getStyleClass().add("background");
  }

  /**
   * Renders the home page view by setting up the layout again.
   */
  @Override
  public void render() {
    setButtonChecked(chosenGameType);
  }

  /**
   * Sets the chosen game type.
   *
   * @param chosenGameType the chosen game type
   */
  public void setChosenGameType(String chosenGameType) {
    this.chosenGameType = chosenGameType;
  }

  /**
   * Constructs the center pane of the home page view,
   * which contains a title, images, buttons, and a start button.
   *
   * @return the constructed VBox representing the center pane
   */
  private VBox centerPane() {
    // Borderpane for the center of the window
    VBox centerVbox = new VBox();
    centerVbox.setSpacing(50);
    centerVbox.setMaxHeight(getPrimary().getVisualBounds().getHeight() * 0.65);

    // The start ChaosGame-button for the bottom of the center pane
    Button startButton = createStartGameButton();

    HBox startHbox = new HBox();
    startHbox.getChildren().addAll(new FillerPane(), startButton, new FillerPane());

    warningLabel = new Label("");
    warningLabel.getStyleClass().add("warning-label");
    warningLabel.setAlignment(Pos.CENTER);
    HBox warningBox = new HBox();
    warningBox.getChildren().addAll(new FillerPane(), warningLabel, new FillerPane());

    // Title for in the top of the center pane
    Text centerTitle = new Text("Select a Chaos Game");
    centerTitle.getStyleClass().add("center-title");

    HBox centerTitleBox = new HBox(new FillerPane(), centerTitle, new FillerPane());

    centerVbox.getChildren().addAll(centerTitleBox, imageAndButtons(),
        startHbox, warningBox, new FillerPane());

    return centerVbox;
  }

  /**
   * Constructs the HBox containing images and buttons in the center pane.
   *
   * @return the constructed HBox representing images and buttons
   */
  private HBox imageAndButtons() {
    // The Images and buttons for in the center of the center pane
    HBox centerHbox = new HBox();
    centerHbox.setSpacing(50);

    VBox sierpinskiBox = createImageBox(
        new Image(Objects.requireNonNull(getClass()
            .getResourceAsStream("/image/sierpinski_triangle.jpg"))));
    sierpinskiBox.getChildren().add(sierpinskiTriangleButton);


    VBox barnsleyBox = createImageBox(
        new Image(Objects.requireNonNull(getClass()
            .getResourceAsStream("/image/barnsley_fern.jpg"))));
    barnsleyBox.getChildren().add(barnsleyFernButton);

    VBox juliaBox = createImageBox(
        new Image(Objects.requireNonNull(getClass()
            .getResourceAsStream("/image/julia_set.jpg"))));
    juliaBox.getChildren().add(juliaSetButton);

    VBox mandelbrotBox = createImageBox(
        new Image(Objects.requireNonNull(getClass()
            .getResourceAsStream("/image/mandelbrot_set.jpg"))));
    mandelbrotBox.getChildren().add(mandelbrotButton);

    centerHbox.getChildren().addAll(new FillerPane(),
        sierpinskiBox, barnsleyBox, juliaBox, mandelbrotBox, new FillerPane());

    return centerHbox;
  }

  /**
   * Constructs a VBox containing an image.
   *
   * @param image the image to be displayed in the VBox
   * @return the constructed VBox containing the image
   */
  private VBox createImageBox(Image image) {
    VBox imageButtonBox = new VBox();
    imageButtonBox.setSpacing(10);
    imageButtonBox.setAlignment(Pos.CENTER);

    ImageView imageView = new ImageView();
    imageView.setImage(image);
    imageView.setFitHeight(150);
    imageView.setFitWidth(150);

    imageButtonBox.getChildren().add(imageView);

    return imageButtonBox;
  }

  /**
   * Creates a button with the given text and adds transitions to it.
   *
   * @param buttonText the text to be displayed on the button
   * @return the created button
   */
  private Button createButton(String buttonText) {
    Button button = new Button(buttonText);
    addButtonTransitions(button);

    button.getStyleClass().add(BUTTON_STYLE);

    button.setOnMouseClicked(event -> {
      chosenGameType = button.getText();
      notifyObservers(ButtonEnum.GAME_TYPE, button.getText());
    });

    return button;
  }

  /**
   * Creates the start game button and adds transitions to it.
   *
   * @return the start game button
   */
  private Button createStartGameButton() {
    Button startButton = new Button("Start Chaos Game");
    startButton.getStyleClass().add("start-button-blue");
    addButtonTransitions(startButton);

    startButton.setOnAction(e -> {
      if (!chosenGameType.isEmpty()) {
        warningLabel.setText("");
        notifyObservers(ButtonEnum.START_GAME, chosenGameType);
      } else {
        warningLabel.setText("Please select a game type before starting the game.");
      }
    });
    return startButton;
  }

  /**
   * Adds Scale and Fade transitions to the button.
   *
   * @param button the button to add transitions to
   */
  private void addButtonTransitions(Button button) {
    ScaleTransition stGrow = new ScaleTransition(Duration.millis(500), button);
    stGrow.setToX(1.03);
    stGrow.setToY(1.03);

    ScaleTransition stShrink = new ScaleTransition(Duration.millis(500), button);
    stShrink.setToX(1);
    stShrink.setToY(1);

    FadeTransition ft = new FadeTransition(Duration.millis(1000), button);
    ft.setFromValue(0.9);
    ft.setToValue(1.0);

    button.hoverProperty().addListener((observable, wasHovered, isNowHovered) -> {
      if (Boolean.TRUE.equals(isNowHovered)) {
        stShrink.stop();
        stGrow.playFromStart();
        ft.playFromStart();
      } else {
        stGrow.stop();
        stShrink.playFromStart();
      }
    });
  }

  /**
   * Clears the selection styling from all buttons.
   */
  private void clearButtons() {
    sierpinskiTriangleButton.getStyleClass().remove(BUTTON_STYLE_CHECKED);
    barnsleyFernButton.getStyleClass().remove(BUTTON_STYLE_CHECKED);
    juliaSetButton.getStyleClass().remove(BUTTON_STYLE_CHECKED);
    mandelbrotButton.getStyleClass().remove(BUTTON_STYLE_CHECKED);
  }

  /**
   * Sets the specified button as checked.
   *
   * @param buttonToBeChecked the button to be checked
   */
  public void setButtonChecked(String buttonToBeChecked) {
    clearButtons();
    switch (buttonToBeChecked) {
      case FractalType.SIERPINSKI:
        sierpinskiTriangleButton.getStyleClass().addFirst(BUTTON_STYLE_CHECKED);
        break;
      case FractalType.BARNSLEY:
        barnsleyFernButton.getStyleClass().addFirst(BUTTON_STYLE_CHECKED);
        break;
      case FractalType.JULIA:
        juliaSetButton.getStyleClass().addFirst(BUTTON_STYLE_CHECKED);
        break;
      case FractalType.MANDELBROT:
        mandelbrotButton.getStyleClass().addFirst(BUTTON_STYLE_CHECKED);
        break;
      default:
        throw new IllegalArgumentException("Invalid button");
    }
  }

  /**
   * Resets the main pane of the home page view.
   */
  @Override
  public void resetPane() {
    stackPane.getChildren().clear();
  }
}
