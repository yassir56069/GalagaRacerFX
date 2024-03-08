package application.UI;

import application.Main;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class Menus {

	public static Group createPauseScreen()
	{
        Group pauseScreenGroup = new Group();

        // Create a transparent background for the pause screen
        BorderPane background = new BorderPane();
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // Semi-transparent black background
        background.setMinSize(Main.WIDTH, Main.HEIGHT); // Set the size to cover the whole scene

        // Create a label with "PAUSED" in the center
        Label pausedLabel = new Label("PAUSED");
        pausedLabel.setStyle("-fx-font-size: 36; -fx-text-fill: white;");
        BorderPane.setAlignment(pausedLabel, Pos.CENTER);

        pauseScreenGroup.getChildren().addAll(background, pausedLabel);
        pauseScreenGroup.setVisible(false); // Initially, hide the pause screen


        return pauseScreenGroup;
	}
}
