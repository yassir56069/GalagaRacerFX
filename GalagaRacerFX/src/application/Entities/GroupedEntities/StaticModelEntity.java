package application.Entities.GroupedEntities;

import java.util.ArrayList;

import application.Entities.PlayerShip;
import application.Util.ModelLoader;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class StaticModelEntity extends StaticEntity {

	protected PhongMaterial material = new PhongMaterial();

	
	public StaticModelEntity(PhongMaterial material, int numOfEntities, PlayerShip playerReference,
			Point3D coordParam, Point3D velParam) {
		
		super(playerReference, numOfEntities, velParam, velParam);
		
		ArrayList<Object> entityList = new ArrayList<>();
	    ArrayList<Object> velocityList = new ArrayList<>();
	    
	    entityVelList.add(entityList);
	    entityVelList.add(velocityList);
		
		
		
		for (int entity = 0; entity < numOfEntities; entity++) {
            TriangleMesh asteroidMesh = AsteroidMesh.createAsteroidMesh();
            MeshView asteroidView = new MeshView(asteroidMesh);
            
            asteroidView.setScaleX(3.0); // Scale factor for the X-axis
            asteroidView.setScaleY(3.0); // Scale factor for the Y-axis
            asteroidView.setScaleZ(3.0); // Scale factor for the Z-axis
            asteroidView.setMaterial(material);
			
			entityList.add(asteroidView);
			velocityList.add(generateVelSpread());
			
			placeEntity(entity);
		}
		

		addEntitiesToGroup();
	}

	
	protected Node getIndexEntityList(int index)
	{
		return (Node) entityVelList.get(0).get(index);
	}


	@Override
	protected void moveEntities(int index) {
		
		MeshView currentEntity = (MeshView)  getIndexEntityList(index);
		Point3D velocity = accessVelList(index);
		
		currentEntity.setTranslateX(currentEntity.getTranslateX() + velocity.getX());
		currentEntity.setTranslateY(currentEntity.getTranslateY() + velocity.getY());
		currentEntity.setTranslateZ(currentEntity.getTranslateZ() - velocity.getZ());
	}
	
	@Override
	protected void placeEntity(int index) {
		MeshView  currentEntity = (MeshView) getIndexEntityList(index);
		
		currentEntity.setTranslateX(((random.nextDouble() - 0.5) * coordinateSpread.getX()));
		currentEntity.setTranslateY(((random.nextDouble() - 0.5) * coordinateSpread.getY()));
		currentEntity.setTranslateZ(Math.abs(playerReference.getCurrPosition().getZ() + (random.nextDouble() * coordinateSpread.getZ()) +7000));
	}
	
	
	@Override
	public void updateEntitiesPosition()
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
	
	@Override
	public void setEntityList(Node shape)
	{
		entityVelList.get(0).add( (Group) shape);
	}
	
}
