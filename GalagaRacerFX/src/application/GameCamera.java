package application;

import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;

public class GameCamera {

	public Camera camera;
	private Point3D initialCameraPosition;
	
	/** Creates game camera for viewing the game
	 * 
	 * @param Scene s : the scene to render on the camera.
	 */
	public GameCamera(Scene s)
	{
		this.camera = new PerspectiveCamera(true);
		s.setCamera(camera);
	}
	
	/**
	 * Set camera in 3D space
	 * 
	 * @param X : coord (double)
	 * @param Y : coord (double)
	 * @param Z : coord (double)
	 */
	public void setCamera(double X, double Y, double Z)
	{
		this.initialCameraPosition = new Point3D(X,Y,Z);
		
		camera.translateXProperty().set(X);
		camera.translateYProperty().set(Y);
		camera.translateZProperty().set(Z);
	}
	
	/**
	 * Move camera in 3D space by (X,Y,Z).
	 * 
	 * @param X : coord (double)
	 * @param Y : coord (double)
	 * @param Z : coord (double)
	 */
	public void moveCamera(double X, double Y, double Z)
	{
		camera.translateXProperty().set(camera.getTranslateX() + X);
		camera.translateYProperty().set(camera.getTranslateY() + Y);
		camera.translateZProperty().set(camera.getTranslateZ() + Z);
	}
	

	/**setNearFarClip
	 * 
	 * sets the near far clip for the camera, leave to 0 if unchanged
	 * 
	 * @param nearClip
	 * @param farClip
	 */
	public void setNearFarClip(double nearClip, double farClip) {
		if (nearClip != 0) camera.setNearClip(nearClip);
		if (farClip != 0) camera.setFarClip(farClip);
	}

	public Point3D getInitialCameraPosition() {
		return initialCameraPosition;
	}
	
}
