package application.Light;

import javafx.geometry.Point3D;

import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.LightBase;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class LightHandler {

	
	public static void setAmbientLight(Group g, Color c) {
		AmbientLight light = new AmbientLight();
		light.setColor(c);
		
		g.getChildren().add(light);	
	}

	
	public static LightInstance addLightInstance(Group g, Color c, Point3D pos) {
		LightInstance newInstance = new LightInstance(c, pos);
		
		g.getChildren().add(newInstance.getLight());	
		return newInstance;
	}

	public static void bindLightToObject(LightInstance light, Group group, Point3D offset) {
		light.getLight().translateXProperty().bind(group.translateXProperty().add(offset.getX()));
		light.getLight().translateYProperty().bind(group.translateYProperty().add(offset.getY()));
		light.getLight().translateZProperty().bind(group.translateZProperty().add(offset.getZ()));
	}
	
	public static void getAllLightSources(Stage s) {
		for (Node node : s.getScene().getRoot().getChildrenUnmodifiable()) {
		    if (node instanceof LightBase) {
		        System.out.println("Light found: " + node);
		    }
		}
		
	}
}