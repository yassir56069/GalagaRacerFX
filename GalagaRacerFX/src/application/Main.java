package application;

import java.io.File;
import java.util.Random;

import application.GameState.State;
import application.UI.HUD;
import application.UI.Menus;
import application.UI.PauseScreen;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    // Constants
    public static final int WIDTH = 1400;
    public static final int HEIGHT = 800;
    public static final int NUM_STARS = 600;

    public static State gameState = State.RUNNING;

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

            
            
            
            
            
            
            
            Image titleImage = new Image("file:./src/application/Assets/galrace.jpeg");
            ImageView imageView = new ImageView(titleImage);
            imageView.setFitWidth(titleImage.getWidth());
            imageView.setFitHeight(titleImage.getHeight());
            imageView.setLayoutX((WIDTH - titleImage.getWidth()) / 2);
            imageView.setLayoutY((HEIGHT - titleImage.getHeight()) / 2);
            
            // Add the image to the title screen group
            titleScreenGroup.getChildren().add(imageView);

            // Create the "Start" button
            Button startButton = new Button("Start");
            startButton.setStyle("-fx-font-size: 20px; -fx-background-color: #333333; -fx-text-fill: white;");
            startButton.setOnAction(event -> startGame(primaryStage));

            // Position the button in the center of the screen
            startButton.setLayoutX((WIDTH - 120) / 2);
            startButton.setLayoutY((HEIGHT + imageView.getLayoutY() + imageView.getBoundsInLocal().getHeight()) / 2); // Adjust Y position

            // Add the elements to the title screen group
            titleScreenGroup.getChildren().add(startButton);

            // Create the scene for the title screen
            Scene titleScreenScene = new Scene(titleScreenGroup, WIDTH, HEIGHT);

            // Set dark background color for the entire scene
            titleScreenScene.setFill(Color.BLACK);

            // Link the CSS file to the scene
            titleScreenScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            // Set the title screen scene
            primaryStage.setScene(titleScreenScene);
            primaryStage.setTitle("GalaRacerFx - Title Screen");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to create falling stars
    private void createFallingStars(Group group) {
        Random random = new Random();
        for (int i = 0; i < NUM_STARS; i++) {
            // Create a random position for the star
            double x = random.nextDouble() * WIDTH;
            double y = random.nextDouble() * HEIGHT;
            
            
            
         // Create a random radius for the star
            double radius = random.nextDouble() * 3 + 1; // Random radius between 1 and 4
            

            // Create a small circle representing the star
            Circle star = new Circle(1, Color.WHITE);
            star.setTranslateX(x);
            star.setTranslateY(y);

            // Add the star to the group
            group.getChildren().add(star);

            // Define animation for the star
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(star.opacityProperty(), 0.5)),
                    new KeyFrame(Duration.seconds(3), new KeyValue(star.opacityProperty(), 0))
            );

            // Set up animation to repeat indefinitely
            timeline.setCycleCount(Timeline.INDEFINITE);

            // Start the animation
            timeline.play();
        }
    }

    // Method to start the game
    private void startGame(Stage primaryStage) {
        try {
            Lane lane = new Lane(30, 90, new Point3D(20, 100, 100));
            Group group = new Group();
            LightHandler.setAmbientLight(group, Color.BLACK);
            lane.addLaneToGroup(group);

            GameCamera c = new GameCamera();
            c.setCamera(0, 0, -10);
            c.setNearFarClip(1, 4000);

            PlayerShip player = new PlayerShip(c, new Point3D(0, 0, 140), new Sphere(10));

            HUD hud = new HUD(new Point3D(((-Main.WIDTH * 0.5) + 100), (-Main.HEIGHT * 0.5) + 65, 1250));
            PauseScreen pause = new PauseScreen(group, player, lane.getPillarsList());
            hud = Menus.createHUD(hud);
            pause.screen = Menus.createPauseScreen(pause);
            group.getChildren().addAll(pause.screen, hud.screen);

            Scene scene = new Scene(group, WIDTH, HEIGHT, true);
            scene.setFill(Color.BLACK);
            scene.setCamera(c.camera);
            pause.screen.setLayoutX(scene.getWidth() - pause.screen.getWidth() / 2);
            pause.screen.setLayoutY(scene.getHeight() - pause.screen.getHeight() / 2);

            PhongMaterial asteroidMat = new PhongMaterial();
            asteroidMat.setBumpMap(new Image(String.valueOf(new File("file:./src/application/Assets/asteroidBump.png"))));
            asteroidMat.setDiffuseMap(new Image(String.valueOf(new File("file:./src/application/Assets/asteroidDiff.png"))));

            StaticEntity obstacle = new StaticEntity(
                    asteroidMat,
                    10,
                    30,
                    player,
                    new Point3D(200, 150, 100000),
                    new Point3D(0, 0, 0)
            );

            StaticEntity starParticles = new StaticEntity(
                    new PhongMaterial(Color.WHITE),
                    0.4,
                    700,
                    player,
                    new Point3D(500, 500, 100000),
                    new Point3D(0, 0, 0)
            );

            group.getChildren().add(obstacle.getEntityGroup());
            group.getChildren().add(starParticles.getEntityGroup());

            player.setCameraOffset(new Point3D(0, -20, -150));
            group.getChildren().add(player.getShipModel());

            ControlShip controller = new ControlShip(player, group, hud, pause, scene, 10, 50.0, 0.05);

            LightHandler.addLightInstance(group, Color.WHITE, new Point3D(0, 0, -100));

            LightInstance headlight = LightHandler.addLightInstance(group, Color.WHITE, player.getCurrPosition());
            LightHandler.bindLightToObject(headlight, player.getShipModel(), new Point3D(0, 0, 5000));

            group.getChildren().add(controller.particleGroup);

            Point3D UI_Offset = new Point3D((-WIDTH * 1.5) + 605, (-HEIGHT * 1.5) + 280, 500);
            controller.startGameLoop(lane, UI_Offset, c, obstacle, starParticles);

            primaryStage.setScene(scene);
            primaryStage.setTitle("GalaRacerFx");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
