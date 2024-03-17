package application.UI;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class HUD {
	public BorderPane screen;
	public int score;
	public Point3D pos;
	public Label score_label = new Label("Score: " + 0);;
	
	private Font font;
	
	public HUD(Point3D p)
	{
		screen = new BorderPane();
		score = 0;
		pos = p;
		
		font = Font.loadFont(String.valueOf(new File("file:./src/application/Assets/VT323-Regular.ttf")), 34);
        score_label.setStyle("-fx-font-family: 'VT323'; -fx-text-fill: white;"); // Apply the custom font using CSS
        score_label.setFont(font);
	}
	
    
	public void setScore(int score) {
		this.score = score;
		updateScoreLabel();
	}
	
	public int getScore() {
		return score;
	}
	
	public void depleteScore(int score) {
		setScore(this.score - score);
		
		updateScoreLabel();
//		
//	    // Change box color for a short duration
//	    screen.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4); -fx-background-radius: 10 0 10 10;" ); // Change color to red temporarily
//
//	    // Create a timeline to revert the color after 200ms
//	    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), new KeyValue(screen.styleProperty(),"-fx-background-color: rgba(0, 255, 255, 0.4); -fx-background-radius: 10 0 10 10;" 
//	    )));
//	    timeline.play();
	    
	}
	
    private void updateScoreLabel() {
        // Update the score label on the JavaFX Application Thread
        Platform.runLater(() -> {
            score_label.setText("Score: " + this.score);
        });
    }

}