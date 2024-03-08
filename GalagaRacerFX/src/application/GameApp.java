
package application;
	
import java.io.File;

import javafx.animation.AnimationTimer;
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


public class GameApp extends Application {
	// General convention upheld without code; the FIRST HALF of the boxes are always the right ones.s
	// Constants
	public final int WIDTH 			= 1400;
	public final int HEIGHT 		= 800;
	
	private Lane lane;
	private Group group;
	private Scene root_scene;
	private GameCamera camera;
	private PlayerShip player;
	private PhongMaterial asteroidMat;
	private StaticEntity obstacle;	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			lane = new Lane(30, 90, new Point3D(20, 100, 100));
			group = new Group();
			
			LightHandler.setAmbientLight(group, Color.BLACK);
			
			lane.addLaneToGroup(group);
			
			
			// Scene
			root_scene = new Scene(group, WIDTH, HEIGHT, true);
			root_scene.setFill(Color.BLACK);
			
			//gameCamera
			camera = new GameCamera(root_scene);
			camera.setCamera(0, 0, 0);
			camera.setNearFarClip(1, 4000);
			
			// player
			player = new PlayerShip(camera, new Point3D(0,0,140), new Sphere(10));
			
			
			asteroidMat = new PhongMaterial();

			asteroidMat.setBumpMap(new Image(String.valueOf(new File("file:./src/application/Assets/asteroidBump.png"))));
			asteroidMat.setDiffuseMap(new Image(String.valueOf(new File("file:./src/application/Assets/asteroidDiff.png"))));
			
			// obstacles
			obstacle = new StaticEntity(
					asteroidMat,							//material
					10, 									//radius
					30, 									//numOfEntities
					player, 								//playerReference
					new Point3D(200, 150, 100000),			//coordinateSpread
					new Point3D(0,0,0)						//velocitySpread
					);
			
			// star particle effect
			star_particles = new StaticEntity(
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
			

//			GameController controller = new GameController(player, scene, 0, 50.0, 0.05);
			
			LightHandler.addLightInstance(group, Color.WHITE, new Point3D(0,0,-100));
			
			
			LightInstance headlight = LightHandler.addLightInstance(group, Color.WHITE,  player.getCurrPosition() );
			LightHandler.bindLightToObject(headlight, player.getShipModel(), new Point3D(0,0,5000));

//			group.getChildren().add(controller.particleGroup);
			
			
			primaryStage.setTitle("GalaRacerFx");
			primaryStage.setScene(root_scene);
			primaryStage.show();
			

			LightHandler.getAllLightSources(primaryStage);
//			controller.initGameLoop(lane, player, obstacle, star_particles);
	
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Lane getLane() {
		return lane;
	}

	public Scene getRoot_scene() {
		return root_scene;
	}

	public GameCamera getCamera() {
		return camera;
	}

	public PlayerShip getPlayer() {
		return player;
	}

	public PhongMaterial getAsteroidMat() {
		return asteroidMat;
	}

	public StaticEntity getObstacle() {
		return obstacle;
	}

	public StaticEntity getStar_particles() {
		return star_particles;
	}

	private StaticEntity star_particles;
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
	