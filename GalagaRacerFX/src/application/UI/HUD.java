package application.UI;

import java.io.File;

import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

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

	
    private void updateScoreLabel() {
        // Update the score label on the JavaFX Application Thread
        Platform.runLater(() -> {
            score_label.setText("Score: " + score);
        });
    }

}
