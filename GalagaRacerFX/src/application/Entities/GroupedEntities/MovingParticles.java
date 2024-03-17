package application.Entities.GroupedEntities;

import java.util.ArrayList;

import application.Entities.PlayerShip;
import javafx.geometry.Point3D;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.Node;

public class MovingParticles extends StaticEntity {
	
	protected PhongMaterial material = new PhongMaterial();
	protected double radius;
	
	public MovingParticles(PhongMaterial material, double radius,  int numOfEntities, PlayerShip playerReference, Point3D coordParam, Point3D velParam) {
		
		super(playerReference, numOfEntities, coordParam, velParam);
		
	    
	    ArrayList<Object> entityList = new ArrayList<>();
	    ArrayList<Object> velocityList = new ArrayList<>();
	    
	    entityVelList.add(entityList);
	    entityVelList.add(velocityList);
		
		this.material = material;
		
		for (int entity = 0; entity < numOfEntities; entity++) {
			Sphere currentSphere = new Sphere(radius);
			currentSphere.setMaterial(material);
			
			entityList.add(currentSphere);
			velocityList.add(generateVelSpread());
			
			placeEntity(entity);
		}

		addEntitiesToGroup();
	}

	
	@Override
	protected Node getIndexEntityList(int index)
	{
		return (Shape3D) entityVelList.get(0).get(index);
	}

	@Override
	protected void moveEntities(int index) {
		
		Shape3D currentEntity = (Shape3D) getIndexEntityList(index);
		Point3D velocity = accessVelList(index);
		
		currentEntity.setTranslateX(currentEntity.getTranslateX() + velocity.getX());
		currentEntity.setTranslateY(currentEntity.getTranslateY() + velocity.getY());
		currentEntity.setTranslateZ(currentEntity.getTranslateZ() - velocity.getZ());
	}
	
	@Override
	protected void placeEntity(int index) {
		Shape3D currentEntity = (Shape3D) getIndexEntityList(index);
		
		currentEntity.setTranslateX(((random.nextDouble() - 0.5) * coordinateSpread.getX()));
		currentEntity.setTranslateY(((random.nextDouble() - 0.5) * coordinateSpread.getY()));
		currentEntity.setTranslateZ(Math.abs(playerReference.getCurrPosition().getZ() + (random.nextDouble() * coordinateSpread.getZ()) + 5000));
	}
	
	@Override
	public void setEntityList(Node shape)
	{
		entityVelList.get(0).add( (Shape3D) shape);
	}
	
}
