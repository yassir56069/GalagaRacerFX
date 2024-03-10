
package application;
import java.io.File;

import application.UI.HUD;
import application.UI.Menus;
import application.UI.PauseScreen;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;


public class Main extends Application {
	// General convention upheld without code; the FIRST HALF of the boxes are always the right ones.s
	// Constants
	public static final int WIDTH 			= 1400;
	public static final int HEIGHT 			= 800;
	
	public static GameState gameState = GameState.RUNNING;


	
	@Override
	public void start(Stage primaryStage) {
		try {
			Lane lane = new Lane(30, 90, new Point3D(20, 100, 100));

			Group group = new Group();
			
			
			LightHandler.setAmbientLight(group, Color.BLACK);
			
			lane.addLaneToGroup(group);
			
			//gameCamera
			GameCamera c = new GameCamera();
			c.setCamera(0, 0, -10);
			c.setNearFarClip(1, 4000);
			
			
			// GUI
			
			
			HUD hud = new HUD(new Point3D(((-Main.WIDTH * 0.5) + 100), (- Main.HEIGHT * 0.5) + 65, 1250));
			
			
			PauseScreen pause = new PauseScreen(group);
			
			hud = Menus.createHUD(hud);
			pause.screen = Menus.createPauseScreen(pause);
			
			group.getChildren().addAll(pause.screen, hud.screen);
	        
			
			// Scene
		
			Scene scene = new Scene(group, WIDTH, HEIGHT, true);
			scene.setFill(Color.BLACK);
			scene.setCamera(c.camera);
			
	        
	        pause.screen.setLayoutX(scene.getWidth() - pause.screen.getWidth() / 2);
	        pause.screen.setLayoutY(scene.getHeight() - pause.screen.getHeight() / 2);
			
	        
			// player
			PlayerShip player = new PlayerShip(c, new Point3D(0,0,140), new Sphere(10));
			
			
			
			PhongMaterial asteroidMat = new PhongMaterial();

			asteroidMat.setBumpMap(new Image(String.valueOf(new File("file:./src/application/Assets/asteroidBump.png"))));
			asteroidMat.setDiffuseMap(new Image(String.valueOf(new File("file:./src/application/Assets/asteroidDiff.png"))));
			
			// obstacles
			StaticEntity obstacle = new StaticEntity(
					asteroidMat,							//material
					10, 									//radius
					30, 									//numOfEntities
					player, 								//playerReference
					new Point3D(200, 150, 100000),			//coordinateSpread
					new Point3D(0,0,0)						//velocitySpread
					);
			
			// star particle effect
			StaticEntity star_particles = new StaticEntity(
					new PhongMaterial(Color.WHITE),			//material
					0.4, 									//radius
					700, 									//numOfEntities
					player, 								//playerReference
					new Point3D(500, 500, 100000),			//coordinateSpread
					new Point3D(0,0,0)						//velocitySpread
					);

			group.getChildren().add(obstacle.getEntityGroup());
			group.getChildren().add(star_particles.getEntityGroup());
			
			player.setCameraOffset(new Point3D(0, -20, -150));
			
			group.getChildren().add(player.getShipModel());
			

			ControlShip controller = new ControlShip(player, group, hud, pause, scene, 10, 50.0, 0.05);
			
			LightHandler.addLightInstance(group, Color.WHITE, new Point3D(0,0,-100));
			
			
			LightInstance headlight = LightHandler.addLightInstance(group, Color.WHITE,  player.getCurrPosition() );
			LightHandler.bindLightToObject(headlight, player.getShipModel(), new Point3D(0,0,5000));

			group.getChildren().add(controller.particleGroup);
			
			// UI Offset
			Point3D UI_Offset = new Point3D(-WIDTH * 1.5, -HEIGHT * 1.5, 1000);

//			LightHandler.getAllLightSources(primaryStage);
			controller.startGameLoop(lane, UI_Offset, c, obstacle, star_particles);
			
			primaryStage.setTitle("GalaRacerFx");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
	