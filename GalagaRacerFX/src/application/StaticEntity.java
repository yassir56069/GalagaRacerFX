package application;

import java.util.ArrayList;

import javafx.geometry.Point3D;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;

public class StaticEntity {
	private static final Random random = new Random();
	private PhongMaterial material = new PhongMaterial(Color.GRAY);
//	private ArrayList<Shape3D> entityList = new ArrayList<Shape3D>();
	private Point3D coordinateSpread = new Point3D(0,0,0);
	private Point3D velocity = new Point3D(0,0,0);
	private double radius;
	private PlayerShip playerReference;
	
    public Group entityGroup = new Group();
    
    ArrayList<ArrayList<Object>> entityVelList = new ArrayList<>();
    private int entityVelSize;
    
    
	public StaticEntity(PhongMaterial material,double radius, int numOfEntities, PlayerShip playerReference, Point3D equParam, Point3D velParam) {
		super();
		this.material = material;
		this.radius = radius;
		this.playerReference = playerReference;
		this.entityVelSize = numOfEntities;
		
		coordinateSpread = equParam;
		
		
	    
	    ArrayList<Object> entityList = new ArrayList<>();
	    ArrayList<Object> velocityList = new ArrayList<>();
	    
	    entityVelList.add(entityList);
	    entityVelList.add(velocityList);
		
		for (int entity = 0; entity < numOfEntities; entity++) {
			Sphere currentSphere = new Sphere(radius);
			currentSphere.setMaterial(material);
			
			entityList.add(currentSphere);
			
			placeEntity(entity);
		}
		

		
		addEntitiesToGroup();
		
	}

	public ArrayList<Object> getEntityList(){
		return entityVelList.get(0);
		
	}
	
	public Shape3D getIndexEntityList(int index)
	{
		return (Shape3D) entityVelList.get(0).get(index);
	}
	
	public void setEntityList(Shape3D shape)
	{
		entityVelList.get(0).add(shape);
	}
	
	public Point3D accessVelList(int index)
	{
		return (Point3D) entityVelList.get(1).get(index);
	}
	
	public void setVelList(Point3D velocity)
	{
		entityVelList.get(1).add(velocity);
	}
	
	
	public void addEntitiesToGroup()
	{
		for (int entity = 0; entity < entityVelSize; entity++) {
			entityGroup.getChildren().add(getIndexEntityList(entity));
		}
	}
	
	
	private void moveEntities(int index) {
		
		Shape3D currentEntity = getIndexEntityList(index);
		
		currentEntity.setTranslateX(currentEntity.getTranslateX() + velocity.getX());
		currentEntity.setTranslateY(currentEntity.getTranslateY() + velocity.getX());
		currentEntity.setTranslateZ(currentEntity.getTranslateZ() + velocity.getX());
	}
	
	

	public void placeEntity(int index) {
		Shape3D currentEntity = getIndexEntityList(index);
		
		currentEntity.setTranslateX(currentEntity.getTranslateX() + ((random.nextDouble() - 0.5) * coordinateSpread.getX()));
		currentEntity.setTranslateY(currentEntity.getTranslateY() + ((random.nextDouble() - 0.5) * coordinateSpread.getY()));
		currentEntity.setTranslateZ(currentEntity.getTranslateZ() + Math.abs(playerReference.getCurrPosition().getZ() + random.nextDouble() * coordinateSpread.getZ()));
	}
	
	public void updateEntitiesPosition()
	{
		double entDistance; 
		
		
		for (int index = 0; index < entityVelSize; index++) {
			moveEntities(index);
			entDistance = playerReference.getCurrPosition().getZ() - getIndexEntityList(index).getTranslateZ();
			
			if (entDistance > 300) {
				placeEntity(index);
			}
		}
		
		
	}
	
	public void setVelocity(Point3D velocity) {
		this.velocity = velocity;
	}

	public void changeSpread(Point3D rndValues) {
		this.coordinateSpread = rndValues;
	}

	public Group getEntityGroup() {
		return entityGroup;
	}

}
