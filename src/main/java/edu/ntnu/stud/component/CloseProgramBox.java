package edu.ntnu.stud.component;

import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * A specialized alert box for confirming the closure of the program. This class
 * extends the {@link AlertBox} class and provides a dialog box with "Yes" and "No"
 * buttons to confirm or cancel the program exit.
 */
public class CloseProgramBox extends AlertBox {

  /** The "Yes" button for confirming program closure. */
  private static Button yesButton;

  /** The "No" button for canceling program closure. */
  private static Button noButton;

  /**
   * Displays the close program confirmation dialog box.
   *
   * @param title The title of the close program dialog box window.
   * @return true if the "Yes" button is pressed, false otherwise.
   */
  @Override
  public boolean disPlay(String title) {
    alertBox(title, closeProgramBox());
    return buttonPressed();
  }

  /**
   * Creates the layout for the close program confirmation dialog box.
   *
   * @return The VBox layout containing the image, label, and buttons.
   */
  public static VBox closeProgramBox() {

    Label label = new Label();
    label.setText("Are you sure you want to exit?");
    label.getStyleClass().add("text");
    label.setAlignment(Pos.TOP_CENTER);

    Image image = new Image(Objects.requireNonNull(
        AlertBox.class.getResourceAsStream("/image/chaosGame_logo.jpg")));
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(100);
    imageView.setFitWidth(100);

    VBox layout = new VBox(10);
    layout.getChildren().addAll(imageView, label, buttonBox());
    return layout;
  }

  /**
   * Creates an HBox containing the "Yes" and "No" buttons, styled and with event
   * handlers for mouse interactions.
   *
   * @return The HBox layout containing the buttons.
   */
  private static HBox buttonBox() {
    yesButton = layoutButton("Yes");
    noButton = layoutButton("No");

    yesButton.getStyleClass().add("yes-button");
    noButton.getStyleClass().add("no-button");

    buttonPressed();

    HBox buttonBox = new HBox(10);
    buttonBox.getChildren().addAll(yesButton, noButton);
    buttonBox.setAlignment(Pos.BOTTOM_CENTER);

    return buttonBox;
  }

  /**
   * Sets up event handlers for the "Yes" and "No" buttons to handle user interaction.
   * The method updates the {@link AlertBox#answer} variable and closes the window.
   *
   * @return true if the "Yes" button is pressed, false otherwise.
   */
  private static boolean buttonPressed() {
    yesButton.setOnAction(e -> {
      answer = true;
      window.close();
    });

    noButton.setOnAction(e -> {
      answer = false;
      window.close();
    });
    return answer;
  }
}