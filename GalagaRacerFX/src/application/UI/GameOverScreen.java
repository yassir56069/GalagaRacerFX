package application.UI;



import java.io.File;

import application.UI.HUD;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 * @author Jameel Edoo
 * @version 1.0
 */

public class GameOverScreen extends VBox {

    public GameOverScreen(double width, double height, HUD hud) {
        setPrefSize(width, height);
        setStyle("-fx-background-color: RED;");
        setAlignment(Pos.CENTER);

        Font font = Font.loadFont(String.valueOf(new File("file:./src/application/Assets/VT323-Regular.ttf")), 34);
        
        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(font);
        gameOverText.setFill(Color.WHITE);
        
        Label score =  new Label("Score: " + hud.score);
        score.setFont(font);
        score.setTranslateY(30);

        getChildren().addAll(gameOverText, score);
    }
}