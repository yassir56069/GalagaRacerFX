/** ##Libraries Required:
 * ObjModelImporter http://www.interactivemesh.org/models/jfx3dimporter.html
 * JavaFX https://openjfx.io/openjfx-docs/
 * JRE System Library [JDK 18.0.1++]
 */

package application;
import java.util.Random;

//local
import application.State.GameState;
import application.UI.TutorialScreen;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

//jfx
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Main extends Application {
    // Constants
    public static final int WIDTH = 1400;
    public static final int HEIGHT = 800;
    public static final int NUM_STARS = 2000;
    


    public static GameState gameState = GameState.RUNNING;

    @Override
    public void start(Stage primaryStage) {
        try {
    
            // Create the root group for the title screen
            Group titleScreenGroup = new Group();

            // Add the "title-screen" class to the title screen group
            titleScreenGroup.getStyleClass().add("title-screen");

            // Set black background for the title screen
            titleScreenGroup.setStyle("-fx-background-color: black;");

            // Create falling stars
            createFallingStars(titleScreenGroup);

            
            Image titleImage = new Image("file:./src/application/Assets/galagaLogo.png");
            ImageView imageView = new ImageView(titleImage);
            imageView.setFitWidth(titleImage.getWidth() / 2);
            imageView.setFitHeight(titleImage.getHeight() / 2);
            imageView.setLayoutX(((WIDTH - titleImage.getWidth()) / 2) + 280) ;
            imageView.setLayoutY((HEIGHT - titleImage.getHeight()) / 2);
            
            // Add the image to the title screen group
            titleScreenGroup.getChildren().add(imageView);

            // Create the "Start" button
            Button startButton = new Button("Start");
            startButton.setStyle("-fx-font-size: 20px; -fx-background-color: #333333; -fx-text-fill: white;");
            startButton.setOnAction(event -> TutorialScreen.startTutorial(primaryStage));

            // Position the button in the center of the screen
            startButton.setLayoutX((WIDTH - 120) / 2);
            startButton.setLayoutY((HEIGHT + imageView.getLayoutY() + imageView.getBoundsInLocal().getHeight()) / 2); // Adjust Y position

            // Add the elements to the title screen group
            titleScreenGroup.getChildren().add(startButton);

            // Create the scene for the title screen
            Scene titleScreenScene = new Scene(titleScreenGroup, WIDTH, HEIGHT);

            // Set dark background color for the entire scene
            titleScreenScene.setFill(Color.BLACK);


            // Set the title screen scene
            primaryStage.setScene(titleScreenScene);
            primaryStage.setTitle("GalaRacerFx - Title Screen");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public static void main(String[] args) {
        launch(args);
    }
	
    // Method to create falling stars
    private void createFallingStars(Group group) {
        Random random = new Random();
        for (int i = 0; i < NUM_STARS; i++) {
            // Create a random position for the star
            double x = random.nextDouble() * WIDTH;
            double y = random.nextDouble() * HEIGHT;
            
         // Create a random radius for the star
            double radius = random.nextDouble() * 1 + 0.1; // Random radius between 1 and 4
            

            // Create a small circle representing the star
            Circle star = new Circle(radius, Color.WHITE);
            star.setTranslateX(x);
            star.setTranslateY(y);
            

            // Add the star to the group
            group.getChildren().add(star);

            // Define animation for the star
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(star.opacityProperty(), 0.5)),
                    new KeyFrame((Duration.seconds((random.nextDouble() * 20) + 3)), new KeyValue(star.opacityProperty(), 0))
            );

            // Set up animation to repeat indefinitely
            timeline.setCycleCount(Timeline.INDEFINITE);

            // Start the animation
            timeline.play();
        }
    }

}
