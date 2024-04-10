package edu.ntnu.stud;

import edu.ntnu.stud.chaos.ChaosGame;
import edu.ntnu.stud.chaos.ChaosGameDescription;
import edu.ntnu.stud.chaos.ChaosGameFileHandler;
import java.io.IOException;

public class Main {
  public static void main(final String[] args) throws IOException {

    //App.main(args);

    ChaosGameFileHandler chaosGameFileHandler = new ChaosGameFileHandler();
    ChaosGameDescription chaosGameDescription = chaosGameFileHandler.readFromFile("JuliaSet.txt");
    ChaosGame chaosGame = new ChaosGame(chaosGameDescription,50, 50);

    chaosGame.runSteps(1000);
  }
}