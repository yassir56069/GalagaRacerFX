package application.Light;

import javafx.geometry.Point3D;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;

public class LightInstance {
	
	private PointLight light; 
	private Point3D currentPos;
	
	public LightInstance(Color c, Point3D pos)
	{
		light = new PointLight();
		
		this.setCurrentPos(pos);
		light.setColor(c);
		light.getTransforms().add(new Translate(pos.getX(), pos.getY(), pos.getZ()));
	}

	public PointLight getLight() {
		return light;
	}

	public Point3D getCurrentPos() {
		return currentPos;
	}

	public void setCurrentPos(Point3D currentPos) {
		this.light.translateXProperty().add(currentPos.getX());
		this.light.translateYProperty().add(currentPos.getY());
		this.light.translateZProperty().add(currentPos.getZ());
		this.currentPos = currentPos;
	}

	
}
