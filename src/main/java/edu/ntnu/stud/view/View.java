package edu.ntnu.stud.view;

import javafx.scene.layout.Pane;


public abstract class View {

  public abstract Pane getPane();

  public abstract void setup();

  abstract void resetPane();
}