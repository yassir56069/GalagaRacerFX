package application;
	

import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
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
			Sphere cockpit = new Sphere(10);
			
			Lane lane = new Lane(10, 100, new Point3D(20, 100, 100));

			Group group = new Group();

			lane.addLaneToGroup(group);
			
			
			group.getChildren().add(cockpit);

			Scene scene = new Scene(group, WIDTH, HEIGHT, true);
			scene.setFill(Color.BLACK);
			
			//gameCamera
			gameCamera c = new gameCamera(scene);
			

			c.setCamera(0, 0, 0);
			c.setNearFarClip(1, 2000);
			
			cockpit.translateXProperty().set(0);
			cockpit.translateYProperty().set(25);
			cockpit.translateZProperty().set(140);
			
			
			primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
				switch(event.getCode())
				{

					case W:
						c.moveCamera(0, 0, 50);
						cockpit.translateZProperty().set(cockpit.getTranslateZ() + 50);
						break;
					case S:
						c.moveCamera(0, 0, -50);
						cockpit.translateZProperty().set(cockpit.getTranslateZ() - 50);
						break;
						
					case E:
						lane.movePillarsZ();
				default:
					break;
				}
			});
			
			
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
	