package application;

import java.util.ArrayList;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class laneNodes {

	private PhongMaterial material = new PhongMaterial(Color.DARKCYAN);
	
	private ArrayList<Box> nodesList = new ArrayList<Box>();
	
	private Point3D nodeDimensions;
	
	private int laneDistance;
	private int laneSize;
	private int rightTail; 
	private int leftTail;

	
	/**laneNodes
	 * populates the nodesList with nodes
	 * <p> nodesList is an Arraylist with (numberOfNodes * 2) number of elements</p>
	 * @param numberOfNodes - number of nodes used in ONE side of the lane.
	 * @param nodeDim 		- dimensions of each individual node.
	 */
	public laneNodes(int numberOfNodes, int laneDist, Point3D nodeDim) {
		this.laneDistance = laneDist;
		this.laneSize = numberOfNodes * 2;
		this.rightTail = (laneSize/2) - 1;
		this.leftTail = laneSize - 1;
		this.nodeDimensions = nodeDim;
		
		for (int i = 0; i < numberOfNodes * 2; i++ ) {
			nodesList.add(i, new Box(nodeDim.getX(), nodeDim.getY(), nodeDim.getZ()));
		}
		
		alignNodesToLane();
	}
	
	private void alignNodesToLane() {
		
		int newDepth = (int) this.nodeDimensions.getZ();
		
		for (int i = (this.laneSize/2); i-- > 0; ) {
			Box currentNode = nodesList.get(i);
			
			currentNode.translateXProperty().set(this.laneDistance);
			currentNode.translateZProperty().set(newDepth);
			
			newDepth = newDepth + (int) this.nodeDimensions.getZ(); //update box depth
			
			
			if (Util.isEven(i)) currentNode.setMaterial(material); // color box in checkered fashion
		}
		
		newDepth =  (int) this.nodeDimensions.getZ(); 
		
		for (int i = this.laneSize; i-- > (this.laneSize/2); ) {
			Box currentNode = nodesList.get(i);
			
			currentNode.translateXProperty().set(-this.laneDistance);
			currentNode.translateZProperty().set(newDepth);
			
			newDepth = newDepth + (int) this.nodeDimensions.getZ(); ; //update box depth
			
			
			if (Util.isEven(i)) currentNode.setMaterial(material); // color box in checkered fashion
		}
	}

	
	/**addBoxesToGroup
	 * adds the lane to group.
	 * @param group
	 */
	public void addLaneToGroup(Group group) {
	    for (Box node : nodesList) {
	    	group.getChildren().add(node);
	      }
		
	}

	public ArrayList<Box> getNodesList() {
		return nodesList;
	}

	public int getLaneSize() {
		return laneSize;
	}

	public int getRightTail() {
		return rightTail;
	}

	public int getLeftTail() {
		return leftTail;
	}

	public Point3D getNodeDimensions() {
		return nodeDimensions;
	}

	public int getLaneDistance() {
		return laneDistance;
	}

	public PhongMaterial getMaterial() {
		return material;
	}

	public void setMaterial(PhongMaterial material) {
		this.material = material;
	}


}
