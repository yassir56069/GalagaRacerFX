package application.UI;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Menus {

	public static HUD createHUD(HUD hud)
	{

        VBox vbox = new VBox(hud.score_label);
        
        vbox.setTranslateY(20);
        vbox.setTranslateX(20);
        vbox.setSpacing(100); // Set spacing between nodes
        
        vbox.setStyle("-fx-background-radius: 0 0 10 0;");
        vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(0, 0, 10, 0, false), Insets.EMPTY)));
        
        
        StackPane hudContent = new StackPane(vbox);
        
        hudContent.setStyle("-fx-background-color: rgba(0, 255, 255, 0.4); -fx-background-radius: 10 0 10 10;"); // Semi-transparent background

        
        BorderPane p = new BorderPane(hudContent);

        
        p.setPrefWidth(200);
        p.setPrefHeight(80);

        hud.screen = p;
        
        return hud;
	}
	

	public static BorderPane createPauseScreen(PauseScreen p)
	{
		
		Label paused_text = new Label("PAUSED");
		
		Button rg = new Button("Resume Game");

		rg.setMinSize(100, 24);  // Set your minimum width and height
		rg.setPrefSize(100, 24);
        
		StackPane resume_game = createButton(rg);
		
		paused_text.setFont(Font.font("Arial", 30));

		paused_text.setSnapToPixel(true); // Enable snap to pixel
		paused_text.setCache(true);
		
		paused_text.setStyle("-fx-font-smoothing-type: gray;");
		

        paused_text.translateXProperty().add(-100);
        
        StackPane pauseContent = new StackPane(paused_text, resume_game);
        
        pauseContent.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 20 20 20 20;"); 
        
        
        p.screen = new BorderPane(pauseContent);
        
        p.screen.setPrefSize(190, 190);
        p.screen.setMaxSize(190, 180);
        

        moveButton(resume_game, rg, 0, 100);
        
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
 
        
        return container;
	}
	

}
