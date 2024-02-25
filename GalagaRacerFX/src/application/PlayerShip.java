package application;

import java.io.File;

import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
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
	private PhongMaterial material = new PhongMaterial();
	
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
		this.currPosition = playerPos;
		this.shipModel = model;

		Image image = new Image(String.valueOf(new File("file:./src/application/spec.jpg")));
		material.setDiffuseColor(Color.GRAY);
		material.setSelfIlluminationMap(image);
		
		this.shipModel.setMaterial(material);

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
	public void MovePlayerPositionRAW(Lane laneReference, double X, double Y, double Z)
	{
		
		
		this.shipModel.translateXProperty().set(this.shipModel.getTranslateX() + X);
		this.shipModel.translateYProperty().set(this.shipModel.getTranslateY() + Y);
		this.shipModel.translateZProperty().set(this.shipModel.getTranslateZ() + Z);
		
		this.currPosition = new Point3D(this.currPosition.getX() + X, this.currPosition.getY() + Y, this.currPosition.getZ() + Z);
		
		
		bindCamera();
		updateLanePillarsPosition(laneReference);
	}
	
	
	public void MovePlayerPosition(double X, double Y, double Z)
	{
		this.shipModel.setTranslateX(this.shipModel.getTranslateX() + X);
		this.shipModel.setTranslateY(this.shipModel.getTranslateY() + Y);
		this.shipModel.setTranslateZ(this.shipModel.getTranslateZ() + Z);
		
		this.currPosition = new Point3D(this.currPosition.getX() + X, this.currPosition.getY() + Y, this.currPosition.getZ() + Z);
		
		
		bindCamera();
	
	}
	
	public void MovePlayerZ(double speed)
	{
		this.shipModel.setTranslateZ(this.shipModel.getTranslateZ() + speed);
		
		this.currPosition = new Point3D(this.currPosition.getX(), this.currPosition.getY(), this.currPosition.getZ() + speed);
		
		
		bindCamera();
	
	}
	
	public void MovePlayerLeftRight(double speed)
	{
		this.shipModel.setTranslateX(this.shipModel.getTranslateX() + speed);
		
		this.currPosition = new Point3D(this.currPosition.getX() + speed, this.currPosition.getY(), this.currPosition.getZ());
		
		
		bindCamera();
	
	}
	
	
	public void updateLanePillarsPosition(Lane laneReference)
	{
		double tailDistance = this.calculateDistanceToTail(laneReference.getLeftTail().getTranslateZ());
		
		if (tailDistance > 200) {
			laneReference.movePillarsZ();
		}
	}
	

    public boolean hasCollided(Lane lane)
    {
    	boolean hasCollided = false;
    	for (Box i:lane.getPillarsList()) {
    		if (i.getBoundsInParent().intersects(this.shipModel.getBoundsInParent())) {
    			hasCollided = true;
    			break;
    		}
    	}
    	
		return hasCollided;
    }
    
	
	
	/**
	 * Calculates the distance between the player's ship model and the tail pillars of the lane.
	 * 
	 * @param shipPosition The current position of the player's ship model.
	 * @param tailPositionZ The Z-axis position of the tail pillars.
	 * @return The distance between the ship model and the tail pillars in the Z-axis.
	 */
	private double calculateDistanceToTail(double tailPositionZ) {
	    return (currPosition.getZ() - tailPositionZ);
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
