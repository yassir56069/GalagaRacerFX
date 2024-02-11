package application;

import javafx.geometry.Point3D;
import javafx.scene.shape.Shape3D;

/**
 * The {@code PlayerShip} class represents the player's spaceship in a space racing game.
 * It encapsulates the initial position, current position, camera offset in the Y and Z axes,
 * ship model, and a reference to the game camera.
 * 
 * <p>The ship's position is defined using {@link javafx.geometry.Point3D} variables, including
 * {@code initPosition} for the initial position and {@code currPosition} for the current position.
 * The camera offset in the Y and Z axes is stored in the {@code cameraOffset} variable as a
 * {@code Point3D}.
 * 
 * <p>The ship's model is represented by a {@link javafx.scene.shape.Shape3D} variable named
 * {@code shipModel}. This variable can hold any 3D shape, such as a sphere or a combination of shapes.
 * 
 * <p>The class also includes a reference to the game camera, represented by a
 * {@code GameCamera} variable named {@code cameraReference}.
 * 
 * @author Yassir Hossan Buksh
 * @version 1.0
 */
public class PlayerShip {

	private Point3D initPosition;
	private Point3D currPosition;
	private Point3D cameraOffset;

	private Shape3D shipModel;
	
	private GameCamera cameraReference;
	
	/**
	 * Initializes player ship & it's position.
	 * @param c 		- gamecamera for binding 
	 * @param playerPos - initial position of player
	 * @param model		- 3D model used to represent ship
	 */
	public PlayerShip(GameCamera c, Point3D playerPos, Shape3D model)
	{
		this.cameraReference = c;
		this.cameraOffset = new Point3D(0, -20, -120);
		
		this.initPosition = playerPos;
		this.shipModel = model;
		

		bindCamera();
		

	}
	
	private void bindCamera() {
//		this.cameraReference.camera.translateXProperty().bind(this.shipModel.translateXProperty().add(cameraOffset.getX()));
		this.cameraReference.camera.translateYProperty().bind(this.shipModel.translateYProperty().add(cameraOffset.getY()));
		this.cameraReference.camera.translateZProperty().bind(this.shipModel.translateZProperty().add(cameraOffset.getZ()));
	}

	/**
	 * NOTE: camera offset ignores X axis.
	 * @return
	 */
	public Point3D getCameraOffset() {
		return cameraOffset;
	}

	/**
	 * NOTE: camera offset ignores X axis.
	 * 
	 * @param cameraOffset
	 */
	public void setCameraOffset(Point3D cameraOffset) {
		this.cameraOffset = cameraOffset;
		bindCamera();
	}

	/**
	 * Set playerShip in 3D space.
	 * 
	 * @param X : coord (double)
	 * @param Y : coord (double)
	 * @param Z : coord (double)
	 */
	public void setPlayerPosition(double X, double Y, double Z)
	{
		this.currPosition = new Point3D(X,Y,Z);
		
		this.shipModel.translateXProperty().set(X);
		this.shipModel.translateYProperty().set(Y);
		this.shipModel.translateZProperty().set(Z);
		bindCamera();
	}
	
	/**
	 * Move playerShip in 3D space by (X,Y,Z).
	 * 
	 * @param X : coord (double)
	 * @param Y : coord (double)
	 * @param Z : coord (double)
	 */
	public void MovePlayerPosition(double X, double Y, double Z)
	{
		this.currPosition = new Point3D(X,Y,Z);
		
		this.shipModel.translateXProperty().set(this.shipModel.getTranslateX() + X);
		this.shipModel.translateYProperty().set(this.shipModel.getTranslateY() + Y);
		this.shipModel.translateZProperty().set(this.shipModel.getTranslateZ() + Z);
		bindCamera();
	}
	
	public Shape3D getShipModel() {
		return shipModel;
	}

	public Point3D getInitPosition() {
		return initPosition;
	}

	public Point3D getCurrPosition() {
		return currPosition;
	}
}
