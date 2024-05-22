package edu.ntnu.stud.utils;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * A class that extends Pane and creates a FillerPane.
 * This class is used to create a Pane that fills the remaining space in a layout.
 */
public class FillerPane extends Pane {
  /**
   * Constructor for FillerPane.
   */
  public FillerPane() {
    super();
    HBox.setHgrow(this, Priority.ALWAYS);
    VBox.setVgrow(this, Priority.ALWAYS);
  }
}
