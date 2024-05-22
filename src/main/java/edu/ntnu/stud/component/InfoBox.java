package edu.ntnu.stud.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A specialized alert box for displaying informational messages to the user
 * about the chaos game application.
 * This class extends the {@link AlertBox} class and provides a dialog box
 * with an "OK" button to acknowledge the information.
 */
public class InfoBox extends AlertBox {

  /**
   * Displays the information dialog box with the specified title.
   *
   * @param title The title of the information dialog box window.
   * @return Always returns true.
   */
  @Override
  public boolean disPlay(String title) {
    alertBox(title, infoBox());
    return true;
  }

  /**
   * Creates the layout for the information dialog box.
   *
   * @return The VBox layout containing the message and the "OK" button.
   */
  public static VBox infoBox() {
    Button okButton = layoutButton("OK");

    okButton.getStyleClass().add("ok-button");

    okButton.setOnAction(e -> window.close());

    HBox buttonBox = new HBox(10);
    buttonBox.setPadding(new Insets(10, 10, 10, 10));
    buttonBox.getChildren().addAll(okButton);
    buttonBox.setAlignment(Pos.BOTTOM_CENTER);

    Label label = getLabel();

    VBox layout = new VBox(10);
    layout.getChildren().addAll(label, buttonBox);
    return layout;
  }


  /**
   * Creates and returns a label with a predefined message.
   *
   * @return A Label object with the informational message.
   */
  private static Label getLabel() {
    String message = """
                                 This is the Chaos Game Application.
            On the main page you can choose between different fractals.
  Press one of the buttons "Sierpinski", "Barnsley", "Julia" or "Mandelbrot"
      and then press "Start Chaos Game" to start generating the fractals.
                            
        On the canvas page you can make multiple changes with parameters for the fractal.
       You can change the type of transformation, the coordinates within the fractal
         is drawn, the number of rounds to generate (does not work with Julia and
               Mandelbrot), and the transformations used to generate the fractal.
        You can also change the color of the fractal, save the fractal as a text file,
    or upload your own text file. To generate a new fractal click on the "Update" button.
                      
   Feel free to explore the application, but be aware that changing the coordinates or
                    transforms too much will most likely not result in a fractal.
                            """;

    StringBuilder centered = new StringBuilder();
    String[] lines = message.split("\n");

    for (String line : lines) {
      int leadingSpaces = (100 - line.trim().length()) / 2;
      String format = "%" + leadingSpaces + "s%s%n";
      centered.append(String.format(format, "", line.trim()));
    }

    Label label = new Label();
    label.setText(centered.toString());
    label.getStyleClass().add("text");
    label.setAlignment(Pos.TOP_CENTER);
    return label;
  }
}
