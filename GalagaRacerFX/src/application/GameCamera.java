package application;

import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;

/**
 * The {@code GameCamera} class encapsulates a JavaFX camera object and a {@link javafx.geometry.Point3D}.
 * It is designed to handle setting and moving the camera within the game environment, as well as adjusting
 * the near clip and far clip properties of the camera for optimal viewing.
 * 
 * <p>The class provides a public {@link javafx.scene.Camera} object, named {@code camera}, which can be
 * accessed to manipulate the camera properties directly.
 * 
 * <p>Additionally, the class includes a {@code Point3D} variable named {@code position}, representing
 * the current position of the camera in the 3D space.
 * 
 * <p>Methods in this class allow for setting and moving the camera, as well as adjusting the near clip
 * and far clip properties. This class serves as a convenient abstraction for managing the camera within
 * a game environment.
 * 
 * <p>Example usage:
 * <pre>
 * {@code
 * // Create a GameCamera instance
 * GameCamera gameCamera = new GameCamera();
 * 
 * // Set the camera position
 * gameCamera.setPosition(new Point3D(0, 0, -200));
 * 
 * // Move the camera
 * gameCamera.moveCamera(10, 0, 0);
 * 
 * // Adjust near and far clip
 * gameCamera.setNearFarClip(1, 1000);
 * }
 * </pre>
 * 
 * @author Yassir Hoossan Buksh
 * @version 1.0
 */
public class GameCamera {

	public Camera camera;
	private Point3D initialCameraPosition;
	
	/** Creates game camera for viewing the game
	 * 
	 * @param Scene s : the scene to render on the camera.
	 */
	public GameCamera()
	{
		this.camera = new PerspectiveCamera(true);

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
	

	public void bindToCamera(BorderPane pauseScreen, Point3D offset) {
			pauseScreen.translateXProperty().bind(camera.translateXProperty().add(offset.getX()));
			pauseScreen.translateYProperty().bind(camera.translateYProperty().add(offset.getY()));
			pauseScreen.translateZProperty().bind(camera.translateZProperty().add(offset.getZ()));
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
