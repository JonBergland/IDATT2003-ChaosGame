package edu.ntnu.stud;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.util.Objects;

import static javafx.stage.Screen.getPrimary;

public class App extends Application {

  public static Stage primaryStage;

  @Override
  public void start(Stage primaryStage) throws Exception {
    App.primaryStage = primaryStage;
    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/chaosGame_logo.jpg")));
    primaryStage.getIcons().add(image);
    primaryStage.setTitle("Chaos Game");
    ImageView imageView = new ImageView();
    imageView.setImage(image);
    imageView.setFitHeight(100);
    imageView.setFitWidth(100);

    Text text = new Text("Chaos Game");
    text.getStyleClass().add("title");

    StackPane titlePane = new StackPane();
    titlePane.getChildren().addAll(text);
    titlePane.setPadding(new Insets(10));

    HBox titleBox = new HBox();

    titleBox.getChildren().addAll(createFillerPane(), titlePane, imageView, createFillerPane());

    BorderPane borderPane = new BorderPane();
    borderPane.setTop(titleBox);

    // Borderpane for the center of the window
    VBox centerVBox = new VBox();
    centerVBox.setSpacing(50);
    centerVBox.setMaxHeight(getPrimary().getVisualBounds().getHeight() * 0.65);

    // Title for in the top of the center pane
    Text centerTitle = new Text("Select a Chaos Game");
    centerTitle.getStyleClass().add("center-title");

    HBox centerTitleBox = new HBox(createFillerPane(), centerTitle, createFillerPane());
    centerVBox.getChildren().add(centerTitleBox);

    // The Images and buttons for in the center of the center pane
    HBox centerHBox = new HBox();
    centerHBox.setSpacing(50);

    VBox imageButtonBox1 = createImageButtonBox(
        new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/sierpinski_triangle.jpg"))),
            "Sierpinski Triangle",
            true);

    VBox imageButtonBox2 = createImageButtonBox(
        new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/barnsley_fern.jpg"))),
        "Barnsley Fern",
        false);

    VBox imageButtonBox3 = createImageButtonBox(
        new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/julia_set.jpg"))),
        "Julia Set",
        false);

    centerHBox.getChildren().addAll(createFillerPane(), imageButtonBox1, imageButtonBox2, imageButtonBox3, createFillerPane());
    centerVBox.getChildren().add(centerHBox);

    // The start ChaosGame-button for the bottom of the center pane
    Button startButton = new Button("Start Chaos Game");
    startButton.getStyleClass().add("start-button");

    HBox bottomHBox = new HBox();
    bottomHBox.getChildren().addAll(createFillerPane(), startButton, createFillerPane());

    centerVBox.getChildren().add(bottomHBox);

    centerVBox.getChildren().add(createFillerPane());
    borderPane.setCenter(centerVBox);

    Scene scene = new Scene(borderPane, getPrimary().getVisualBounds().getWidth(),
        getPrimary().getVisualBounds().getHeight() - 10);
    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/components.css")).toExternalForm());
    primaryStage.setScene(scene);

    primaryStage.show();
  }

  private Pane createFillerPane() {
    Pane fillerPane = new Pane();
    HBox.setHgrow(fillerPane, Priority.ALWAYS);
    VBox.setVgrow(fillerPane, Priority.ALWAYS);
    return fillerPane;
  }

  private VBox createImageButtonBox(Image image, String buttonText, boolean checked) {
    VBox imageButtonBox = new VBox();
    imageButtonBox.setSpacing(10);
    imageButtonBox.setAlignment(Pos.CENTER);

    ImageView imageView = new ImageView();
    imageView.setImage(image);
    imageView.setFitHeight(150);
    imageView.setFitWidth(150);

    Button button = new Button(buttonText);

    if (checked) {
      button.getStyleClass().add("chaos-game-button-checked");
    } else {
      button.getStyleClass().add("chaos-game-button");
    }

    imageButtonBox.getChildren().addAll(imageView, button);

    return imageButtonBox;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
