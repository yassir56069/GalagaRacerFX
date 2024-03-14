package application;

import java.util.ArrayList;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;

public class StaticModelEntity extends StaticEntity {

	protected String seModelPath;

	
	public StaticModelEntity(String modelPath, int numOfEntities, PlayerShip playerReference,
			Point3D coordParam, Point3D velParam) {
		
		super(playerReference, numOfEntities, velParam, velParam);
		
		ArrayList<Object> entityList = new ArrayList<>();
	    ArrayList<Object> velocityList = new ArrayList<>();
	    
	    entityVelList.add(entityList);
	    entityVelList.add(velocityList);
		
		this.seModelPath = modelPath;
		
		Group model = ModelLoader.loadModel(seModelPath);
		
		
		for (int entity = 0; entity < numOfEntities; entity++) {
			Group currentModel = new Group();
			currentModel.getChildren().add(model);
			
			currentModel.setScaleX(4);
			currentModel.setScaleY(4);
			currentModel.setScaleZ(2);
			
			entityList.add(currentModel);
			velocityList.add(generateVelSpread());
			
			placeEntity(entity);
		}

		addEntitiesToGroup();
	}

	
	protected Node getIndexEntityList(int index)
	{
		return (Group) entityVelList.get(0).get(index);
	}

	@Override
	protected void moveEntities(int index) {
		
		Group currentEntity = (Group) getIndexEntityList(index);
		Point3D velocity = accessVelList(index);
		
		currentEntity.setTranslateX(currentEntity.getTranslateX() + velocity.getX());
		currentEntity.setTranslateY(currentEntity.getTranslateY() + velocity.getY());
//		currentEntity.setTranslateZ(currentEntity.getTranslateZ() - velocity.getZ());
	}
	
	@Override
	protected void placeEntity(int index) {
		Group currentEntity = (Group) getIndexEntityList(index);
		
		currentEntity.setTranslateX(((random.nextDouble() - 0.5) * coordinateSpread.getX()));
		currentEntity.setTranslateY(((random.nextDouble() - 0.5) * coordinateSpread.getY()));
		currentEntity.setTranslateZ(Math.abs(playerReference.getCurrPosition().getZ() + (random.nextDouble() * coordinateSpread.getZ()) + 5000));
	}
	
	@Override
	public void updateEntitiesPosition()
	{
		Point3D entDistance; 
		
			
		for (int index = 0; index < entityVelSize; index++) {
			moveEntities(index);
			entDistance = new Point3D( playerReference.getCurrPosition().getX() - getIndexEntityList(index).getTranslateX(), playerReference.getCurrPosition().getY() - getIndexEntityList(index).getTranslateY(),playerReference.getCurrPosition().getZ() - getIndexEntityList(index).getTranslateZ());

			if (entDistance.getX() > 500) {
				placeEntity(index);
				setVelList(index, generateVelSpread());
			}
			
			if (entDistance.getY() > 500) {
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
