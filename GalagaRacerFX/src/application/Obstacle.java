package application;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;

public class Obstacle {
	private PhongMaterial material = new PhongMaterial(Color.GRAY);
	private List<List<Point3D>> obstacle   = new ArrayList<List<Point3D>> ();
	private Shape3D shipModel;
 
	
}
