package application.UI;

import application.Game;
import application.Main;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TutorialScreen {


    private static int currentImageIndex = 0;

	
    public static void startTutorial(Stage primaryStage) {
        // Load images
        Image image1 = new Image("file:./src/application/Assets/Tutorial/image1.png");
        Image image2 = new Image("file:./src/application/Assets/Tutorial/image2.png");
        Image image3 = new Image("file:./src/application/Assets/Tutorial/image3.png");
        Image image4 = new Image("file:./src/application/Assets/Tutorial/image4.png");
        Image image5 = new Image("file:./src/application/Assets/Tutorial/image5.png");
        Image image6 = new Image("file:./src/application/Assets/Tutorial/image6.png");

        // Initialise image views
        // Initialise image views
        ImageView[] imageViews = {
            createResizedImageView(image1, primaryStage),
            createResizedImageView(image2, primaryStage),
            createResizedImageView(image3, primaryStage),
            createResizedImageView(image4, primaryStage),
            createResizedImageView(image5, primaryStage),
            createResizedImageView(image6, primaryStage)
        };

        // Show initial image
        
        imageViews[currentImageIndex].setLayoutX(Main.WIDTH);
        imageViews[currentImageIndex].setLayoutX(Main.HEIGHT);
        
        StackPane root = new StackPane(imageViews[currentImageIndex]);
        primaryStage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
        primaryStage.show();

        // Handle key events
        primaryStage.getScene().setOnKeyPressed(event -> {
            if (event.getText().equalsIgnoreCase("n")) {
                // Show next image
                currentImageIndex++;
                if (currentImageIndex < imageViews.length) {
                    root.getChildren().setAll(imageViews[currentImageIndex]);
                } else {
                    // If all images are displayed, start displaying the game
                    Game.startGame(primaryStage);

                }
            }
        });
    }    
    
    private static ImageView createResizedImageView(Image image, Stage primaryStage) {
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(primaryStage.getWidth());
        imageView.setFitHeight(primaryStage.getHeight());
        return imageView;
    }
    
	
}
