// TODO Create a parent class "Obstaces/Objects" that contains Lane & (in the future) Asteroids. 
// 		Use this for collisions.

package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;


public class Main extends Application {
	// General convention upheld without code; the FIRST HALF of the boxes are always the right ones.s
	// Constants
	public final int WIDTH 			= 1400;
	public final int HEIGHT 		= 800;
	

	@Override
	public void start(Stage primaryStage) {
		try {
			Lane lane = new Lane(30, 90, new Point3D(20, 100, 100));

			Group group = new Group();
			
			LightHandler.setAmbientLight(group, Color.BLACK);
			
			lane.addLaneToGroup(group);
			
			
			// Scene
			Scene scene = new Scene(group, WIDTH, HEIGHT, true);
			scene.setFill(Color.BLACK);
			
			//gameCamera
			GameCamera c = new GameCamera(scene);
			c.setCamera(0, 0, 0);
			c.setNearFarClip(1, 4000);
			
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
			

			ControlShip controller = new ControlShip(player, scene, 0, 50.0, 0.05);
			
			LightHandler.addLightInstance(group, Color.WHITE, new Point3D(0,0,-100));
			
			
			LightInstance headlight = LightHandler.addLightInstance(group, Color.WHITE,  player.getCurrPosition() );
			LightHandler.bindLightToObject(headlight, player.getShipModel(), new Point3D(0,0,5000));
			group.getChildren().add(controller.particleGroup);
			
			
			primaryStage.setTitle("GalaRacerFx");
			primaryStage.setScene(scene);
			primaryStage.show();
			

			LightHandler.getAllLightSources(primaryStage);
			controller.startGameLoop(lane, player, obstacle, star_particles);
	
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	


	
	public static void main(String[] args) {
		launch(args);
	}
}
	