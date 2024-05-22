package edu.ntnu.stud.component;

import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Utility class for displaying alert boxes in a JavaFX application. This class
 * provides methods to display a modal alert box with a specified title and layout,
 * as well as to create a styled button for use in the alert box.
 */
public class AlertBox {

  /** Static variable to store the user's response from the alert box. */
  protected static boolean answer;

  /** The stage representing the alert box window. */
  protected static Stage window;

  /** The layout for the alert box content. */
  private VBox layout;

  /**
   * Displays an alert box with the specified title.
   *
   * @param title The title of the alert box window.
   * @return Always returns true.
   */
  public boolean disPlay(String title) {
    alertBox(title, layout);
    return true;
  }

  /**
   * Creates and displays a modal alert box with the specified title and layout.
   *
   * @param title  The title of the alert box window.
   * @param layout The VBox layout containing the alert box content.
   */
  public static void alertBox(String title, VBox layout) {
    window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(400);
    window.setMinHeight(300);

    Image image = new Image(Objects.requireNonNull(
        AlertBox.class.getResourceAsStream("/image/chaosGame_logo.jpg")));
    window.getIcons().add(image);

    layout.setAlignment(Pos.CENTER);
    layout.getStyleClass().add("blue");

    Scene scene = new Scene(layout);
    scene.getStylesheets().add(Objects.requireNonNull(AlertBox.class
        .getResource("/style/alertboxes.css")).toExternalForm());
    window.setScene(scene);
    window.showAndWait();
  }

  /**
   * Creates and returns a button with the specified text. The button is styled
   * with a minimum size and a specific font.
   *
   * @param buttonText The text to display on the button.
   * @return The styled Button object.
   */
  public static Button layoutButton(String buttonText) {
    Button button = new Button(buttonText);
    button.setMinSize(60, 60);
    button.setFont(Font.font("Arial", 20));

    return button;
  }
}