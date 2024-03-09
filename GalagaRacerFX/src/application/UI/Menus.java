package application.UI;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class Menus {
	
	public static BorderPane createPauseScreen(PauseScreen p)
	{
		
		Label paused_text = new Label("PAUSED");
		
		Button rg = new Button("Resume Game");
		
		StackPane resume_game = createButton(rg);
		
		paused_text.setFont(Font.font("Arial", 60));

		paused_text.setSnapToPixel(true); // Enable snap to pixel
		paused_text.setCache(true);
		
		paused_text.setStyle("-fx-font-smoothing-type: gray;");
		

        paused_text.translateXProperty().add(-100);
        
        StackPane pauseContent = new StackPane(paused_text, resume_game);
        
        pauseContent.setStyle("-fx-background-color: rgba(255, 255, 255, 1);"); // Semi-transparent background
        
        
        p.screen = new BorderPane(pauseContent);
        
        
        
        p.screen.setPrefWidth(Main.WIDTH);
        p.screen.setPrefHeight(Main.HEIGHT);

        p.screen.setTranslateX(- Main.WIDTH);
        p.screen.setTranslateY(- Main.HEIGHT);
        
        moveButton(resume_game, rg, 160, 110);
        
        rg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	p.togglePause();
            }
        });
        p.screen.setVisible(p.isDisplayed);

        return p.screen;
	}
	
    private static void moveButton(StackPane stackPane, Button button, double xOffset, double yOffset) {
        // Set new position
        StackPane.setMargin(button, new javafx.geometry.Insets(yOffset, xOffset, 0, 0));
    }
	
	private static StackPane createButton(Button b) {
        // Create a StackPane and add the button to it
        StackPane container = new StackPane(b);

        // Set initial position (optional)
        StackPane.setMargin(b, new javafx.geometry.Insets(50, 50, 50, 50));
        
        return container;
	}
	
}
