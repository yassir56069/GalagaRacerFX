package application.Entities.GroupedEntities;

import java.util.ArrayList;

import javafx.geometry.Point3D;
import java.util.Random;

import application.Entities.PlayerShip;
import javafx.scene.Group;
import javafx.scene.Node;

/**
 * The {@code StaticEntity} class represents a container for entities statically placed within the game environment.
 * These entities, represented by spheres, are organized in a 2D ArrayList named {@code entityVelList}. This list not only stores the entities
 * themselves but also their respective velocities, facilitating controlled gliding movements across the screen.
 * <p>
 * Each entity is emitted randomly within a specified range, determined by the {@code coordinateSpread} property (X spread, Y spread, Z spread).
 * The {@code velocity} property, represented by a {@code Point3D}, influences the range of velocities for each entity, adding variability to their motion.
 * <p>
 * The {@code material} property, of type {@code PhongMaterial}, is responsible for determining the appearance of the emitted models. This allows for a diverse
 * and visually engaging array of entities within the game environment.
 * <p>
 * For simplicity, the current implementation uses spheres as the visual representation of the emitted models, and the {@code radius} property controls
 * the size of these spheres.
 * <p>
 * The class employs a {@code Random} variable named 'random' to introduce a degree of randomness in the emission and motion of entities, contributing
 * to a dynamic and unpredictable game environment.
 * <p>
 * The {@code playerReference} field holds a reference to the player's ship, of type {@code PlayerShip}, enabling interactions and coordination
 * between the static entities and the player's ship.
 * <p>
 * <b>Usage Example:</b>
 * <pre>{@code
 * // Example instantiation of StaticEntity
 * ArrayList<ArrayList<Sphere>> entityVelList = new ArrayList<>();
 * PhongMaterial material = new PhongMaterial(Color.BLUE);
 * Point3D coordinateSpread = new Point3D(100, 50, 200);
 * Point3D velocity = new Point3D(0.5, 0, 0);
 * double radius = 10.0;
 * PlayerShip playerShip = new PlayerShip(); // Assuming a PlayerShip class exists
 * StaticEntity staticEntity = new StaticEntity(entityVelList, material, coordinateSpread, velocity, radius, playerShip);
 * }</pre>
 * 
 * <p><b>Fields:</b>
 * <ul>
 *   <li>{@code entityVelList}: 2D ArrayList containing entities (spheres) and their velocities.
 *   Used for managing the position and movement of entities in the game environment.</li>
 * 
 *   <li>{@code material}: {@code PhongMaterial} determining the appearance of the emitted models.
 *   Specifies the visual properties such as color, shininess, and texture.</li>
 * 
 *   <li>{@code coordinateSpread}: {@code Point3D} specifying the range for emitting entities (X, Y, Z).
 *   Defines the area within which entities are randomly placed.</li>
 * 
 *   <li>{@code velocity}: {@code Point3D} specifying the range for velocities (X, Y, Z).
 *   Influences the speed and direction of entities' gliding movements.</li>
 * 
 *   <li>{@code radius}: Radius of the sphere representing the emitted models.
 *   Controls the size of the spheres used as the visual representation of entities.</li>
 * 
 *   <li>{@code random}: {@code Random} variable introducing randomness in emission and motion.
 *   Used for generating random values for entity positions and velocities.</li>
 * 
 *   <li>{@code playerReference}: Reference to the player's ship, of type {@code PlayerShip}.
 *   Enables interactions and coordination between static entities and the player's ship.</li>
 * </ul>
 * 
 * @author Yassir Hossan Buksh
 * @version 1.0
 */
public class StaticEntity {
	protected static final Random random = new Random();
	
	
	protected Point3D coordinateSpread = new Point3D(0,0,0);
	protected Point3D velocitySpread;
	
	protected PlayerShip playerReference;
	
	
    public 	Group entityGroup = new Group();
    
    protected ArrayList<ArrayList<Object>> entityVelList = new ArrayList<>();
    
    
    
    protected int entityVelSize;
    
    //constructor

	public StaticEntity(PlayerShip playerReference, int numOfEntities, Point3D coordParam, Point3D velParam) {
		
		this.playerReference = playerReference;
		this.entityVelSize = numOfEntities;
		
		this.coordinateSpread = coordParam;
		this.velocitySpread = velParam;

	}
    
    
    //private methods
	
	protected Node getIndexEntityList(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	protected void moveEntities(int index) {
		// TODO Auto-generated method stub
		
	}

	
	protected void placeEntity(int index) {
		// TODO Auto-generated method stub
		
	}

	
	
	// protected methods

	protected Point3D generateVelSpread() {
		return new Point3D(((random.nextDouble() - 0.5) * velocitySpread.getX()), (random.nextDouble() - 0.5) * velocitySpread.getY(), (random.nextDouble() - 0.5) * velocitySpread.getZ());
	}
	

	protected void setEntityList(Node shape) {
		// TODO Auto-generated method stub
		
	}

	
	// public methods



	public ArrayList<Object> getEntityList(){
		return entityVelList.get(0);
		
	}

	
	
	public Point3D accessVelList(int index)
	{
		return (Point3D) entityVelList.get(1).get(index);
	}
	
	public void addVelList(Point3D velocity)
	{
		entityVelList.get(1).add(velocity);
	}
	
	public void setVelList(int index, Point3D velocity)
	{
		entityVelList.get(1).set(index, velocity);
	}
	
	
	public void addEntitiesToGroup()
	{
		for (int entity = 0; entity < entityVelSize; entity++) {
			entityGroup.getChildren().add(getIndexEntityList(entity));
		}
	}
	

	
	public void updateEntitiesPosition()
	{
		Point3D entDistance; 
		
			
		for (int index = 0; index < entityVelSize; index++) {
			moveEntities(index);
			entDistance = new Point3D( playerReference.getCurrPosition().getX() - getIndexEntityList(index).getTranslateX(), playerReference.getCurrPosition().getY() - getIndexEntityList(index).getTranslateY(),playerReference.getCurrPosition().getZ() - getIndexEntityList(index).getTranslateZ());

			if (entDistance.getZ() > 300) {
				placeEntity(index);
				setVelList(index, generateVelSpread());
			}
		}
		
		
	}

	public void updateEntitiesPositionObstacle()
	{
		Point3D entDistance; 
		
		
		for (int index = 0; index < entityVelSize; index++) {
			moveEntities(index);
			entDistance = new Point3D( playerReference.getCurrPosition().getX() - getIndexEntityList(index).getTranslateX(), playerReference.getCurrPosition().getY() - getIndexEntityList(index).getTranslateY(),playerReference.getCurrPosition().getZ() - getIndexEntityList(index).getTranslateZ());

			if (entDistance.getX() > 100) {
				placeEntity(index);
				setVelList(index, generateVelSpread());
			}
			
			if (entDistance.getY() > 100) {
				placeEntity(index);
				setVelList(index, generateVelSpread());
			}
			
			if (entDistance.getZ() > 300) {
				placeEntity(index);
				setVelList(index, generateVelSpread());
			}
		}
		
		
	}

	public void changeSpread(Point3D rndValues) {
		this.coordinateSpread = rndValues;
	}

	public Group getEntityGroup() {
		return entityGroup;
	}

	
}
