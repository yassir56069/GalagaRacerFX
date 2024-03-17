package application.GameOverScreen;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class GameOverScreen extends Pane {
    public GameOverScreen(double width, double height) {
        setPrefSize(width, height);
        setStyle("-fx-background-color: RED;");

        
        StackPane root = new StackPane();

        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.font("Arial", 72));
        gameOverText.setFill(Color.WHITE);
        StackPane.setAlignment(gameOverText, Pos.CENTER);
        root.getChildren().add(gameOverText);

        getChildren().add(root);
    }
}

