package edu.ntnu.stud.view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ChooseGameView extends View {

  protected BorderPane borderPane;
  protected StackPane stackPane;


  public ChooseGameView() {
    borderPane = new BorderPane();
    stackPane = new StackPane();
  }

  @Override
  public Pane getPane() {
    return this.borderPane;
  }

  @Override
  public void setup() {

    Button sierpinskiButton = new Button("Sierpinski Triangle");
    Button barnsleyButton = new Button("Barnsley");
    Button juliaButton = new Button("Julia Set");

    stackPane.getChildren().addAll(sierpinskiButton, barnsleyButton, juliaButton);
  }

  @Override
  void resetPane() {

  }
}
