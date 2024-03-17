package application.GameOverScreen;

import application.ControlShip;
import application.GameCamera;
import application.GameState;
import application.Lane;
import application.Main;
import application.ModelLoader;
import application.PlayerShip;
import application.UI.HUD;
import application.UI.PauseScreen;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 * @author Jameel Edoo
 * @version 1.0
 */

public class GameOverScreen extends VBox {
    public Button restartButton;

    public GameOverScreen(double width, double height) {
        setPrefSize(width, height);
        setStyle("-fx-background-color: RED;");
        setAlignment(Pos.CENTER);

        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.font("Arial", 72));
        gameOverText.setFill(Color.WHITE);

        restartButton = new Button("Restart");
        restartButton.setOnAction(e -> restartGame());

        getChildren().addAll(gameOverText, restartButton);
    }
    
    private void restartGame() {
        // Call the restart method of the Main class
        Main.getInstance().restart();
    }
}