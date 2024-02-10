package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;


public class Main extends Application {
	// General convention upheld without code; the FIRST HALF of the boxes are always the right ones.s
	// Constants
	public final int WIDTH 			= 1400;
	public final int HEIGHT 		= 800;
	
	public final int BOX_WIDTH  	= 20;
	public final int BOX_HEIGHT 	= 100;
	public final int BOX_DEPTH  	= 100;
	

	
	public final int NUM_OF_BOXES = 20;
	public final int DISTANCE_FROM_CENTER = 100;
	
	public final int DEPTH_TOTAL    = BOX_DEPTH * (NUM_OF_BOXES/2);
	
	public int rightTail = (NUM_OF_BOXES/2) - 1;
	public int leftTail = NUM_OF_BOXES - 1;
	
	
	public int total_updated_depth = DEPTH_TOTAL;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Sphere cockpit = new Sphere(10);
			
			ArrayList<Box> boxes = new ArrayList<Box>();
			
			for (int i = 0; i < NUM_OF_BOXES; i++ ) {
				boxes.add(i, new Box(BOX_WIDTH, BOX_HEIGHT, BOX_DEPTH));
			}
			
			Group group = new Group();

			addBoxesToGroup(group, boxes);
			
			
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
			
			alignBoxesTolane(DISTANCE_FROM_CENTER, boxes);
			
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
						addBoxToLane(boxes);
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
	
	private void addBoxToLane(ArrayList<Box> boxes) {
		total_updated_depth += BOX_DEPTH;
		
		// right
		
		Box lastBoxR = boxes.get(rightTail);
		lastBoxR.translateZProperty().set(total_updated_depth);
		rightTail --;
		
		if (rightTail == -1 ) {
			rightTail = (NUM_OF_BOXES/2) - 1;
		}
		
		// left
		
		Box lastBoxL = boxes.get(leftTail);
		lastBoxL.translateZProperty().set(total_updated_depth);
		leftTail --;
		
		if (leftTail == ((NUM_OF_BOXES/2) - 1)) {
			leftTail = NUM_OF_BOXES - 1;
		}
		
		System.out.println("test");
		
	}

	boolean isEven(double num) { return ((num % 2) == 0); }

	private void alignBoxesTolane(int distanceFromCenter, ArrayList<Box> boxes) {
		PhongMaterial material = new PhongMaterial(Color.DARKCYAN);
		
		int newDepth = BOX_DEPTH;
		
		for (int i = (NUM_OF_BOXES/2); i-- > 0; ) {
			Box currentBox = boxes.get(i);
			
			currentBox.translateXProperty().set(distanceFromCenter);
			currentBox.translateZProperty().set(newDepth);
			
			newDepth = newDepth + BOX_DEPTH; //update box depth
			
			
			if (isEven(i)) currentBox.setMaterial(material); // color box in checkered fashion
		}
		
		newDepth = BOX_DEPTH;
		
		for (int i = NUM_OF_BOXES; i-- > (NUM_OF_BOXES/2); ) {
			Box currentBox = boxes.get(i);
			
			currentBox.translateXProperty().set(-distanceFromCenter);
			currentBox.translateZProperty().set(newDepth);
			
			newDepth = newDepth + BOX_DEPTH; //update box depth
			
			
			if (isEven(i)) currentBox.setMaterial(material); // color box in checkered fashion
		}
	}

	private void addBoxesToGroup(Group group, ArrayList<Box> boxes) {
	    for (Box b : boxes) {
	    	group.getChildren().add(b);
	      }
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
	