// TODO Create a parent class "Obstaces/Objects" that contains Lane & (in the future) Asteroids. 
// 		Use this for collisions.

package application;
	
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
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
			
		    
			player.setCameraOffset(new Point3D(0, -20, -150));
			group.getChildren().add(player.getShipModel());
			

			ControlShip controller = new ControlShip(player, scene, 10, 50.0, 0.05);
			
			
			LightInstance headlight = LightHandler.addLightInstance(group, Color.WHITE, player.getCurrPosition());
			
			LightHandler.bindLightToObject(headlight, player.getShipModel(), new Point3D(0,0,-100));
//			group.getChildren().add(LightHandler.addLightSource(Color.WHITE, null));		

			group.getChildren().add(controller.particleGroup);
			
			
			primaryStage.setTitle("GalaRacerFx");
			primaryStage.setScene(scene);
			primaryStage.show();
			

			LightHandler.getAllLightSources(primaryStage);
			controller.startGameLoop(lane, player);
	
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	


	
	public static void main(String[] args) {
		launch(args);
	}
}
	