package application;

import java.util.ArrayList;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/**
 * Class denoting the placement of a lane of box objects (pillars) on the left and the right of the
 * Scene.
 */
public class Lane {

	private PhongMaterial material = new PhongMaterial(Color.DARKCYAN);
	
	public ArrayList<Box> pillarsList = new ArrayList<Box>();
	
	private Point3D pillarDimensions;
	
	private int pillarDepth; // for convenience
	
	private int laneInnerSpace;
	private int laneSize;
	private int rightTail; 
	private int leftTail;
	
	private int laneDepth;

	
	/**laneNodes
	 * populates the nodesList with nodes
	 * <p> nodesList is an Arraylist with (numberOfNodes * 2) number of elements</p>
	 * 
	 * @param laneIS 		- The inner spacing of the lanes between each other.
	 * @param numberOfNodes - number of nodes used in ONE side of the lane.
	 * @param nodeDim 		- dimensions of each individual node.
	 */
	public Lane(int pillarRowSize, int laneIS, Point3D nodeDim) {
		this.laneInnerSpace 	= laneIS;
		this.laneSize 			= pillarRowSize * 2;
		this.rightTail 			= (laneSize/2) - 1;
		this.leftTail 			= laneSize - 1;
		this.pillarDimensions 	= nodeDim;
		
		this.pillarDepth 		= (int) pillarDimensions.getZ();
		
		this.laneDepth 			= (int) (pillarRowSize * pillarDepth );
		
		
		for (int i = 0; i < pillarRowSize * 2; i++ ) {
			pillarsList.add(i, new Box(nodeDim.getX(), nodeDim.getY(), nodeDim.getZ()));
		}
		
		alignNodesToLane();
	}
	
	private void alignNodesToLane() {
		
		int newDepth = (int) this.pillarDimensions.getZ();
		
		for (int i = (this.laneSize/2); i-- > 0; ) {
			Box currentNode = pillarsList.get(i);
			
			currentNode.translateXProperty().set(this.laneInnerSpace);
			currentNode.translateZProperty().set(newDepth);
			
			newDepth = newDepth + (int) this.pillarDepth; //update box depth
			
			
			if (Util.isEven(i)) currentNode.setMaterial(material); // color box in checkered fashion
		}
		
		newDepth =  (int) this.pillarDimensions.getZ(); 
		
		for (int i = this.laneSize; i-- > (this.laneSize/2); ) {
			Box currentNode = pillarsList.get(i);
			
			currentNode.translateXProperty().set(-this.laneInnerSpace);
			currentNode.translateZProperty().set(newDepth);
			
			newDepth = newDepth + (int) this.pillarDimensions.getZ(); ; //update box depth
			
			
			if (Util.isEven(i)) currentNode.setMaterial(material); // color box in checkered fashion
		}
	}

	/**
	 * Move pillars from the lane into the Z axis (increase the depth of the lane)
	 */
	public void movePillarsZ() {
		this.laneDepth += this.pillarDepth;
		
		
		// right
		
		this.pillarsList.get(rightTail).translateZProperty().set(this.laneDepth);
		rightTail --;
		
		
		if (rightTail == -1 ) {
			rightTail = (laneSize/2) - 1;
		}
		
		// left
		this.pillarsList.get(leftTail).translateZProperty().set(this.laneDepth);
		leftTail --;

		
		if (leftTail == ((laneSize/2) - 1)) {
			leftTail = laneSize - 1;
		}
		
		
		System.out.println(this.laneDepth);
		
	}

	
	/**addBoxesToGroup
	 * adds the lane to group.
	 * @param group
	 */
	public void addLaneToGroup(Group group) {
	    for (Box node : pillarsList) {
	    	group.getChildren().add(node);
	      }
		
	}
	

	public ArrayList<Box> getPillarsList() {
		return pillarsList;
	}

	public void setPillarsList(ArrayList<Box> pillarsList) {
		this.pillarsList = pillarsList;
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
		return pillarDimensions;
	}

	public int getLaneInnerSpace() {
		return laneInnerSpace;
	}

	public PhongMaterial getMaterial() {
		return material;
	}

	public void setMaterial(PhongMaterial material) {
		this.material = material;
	}

	public int getLaneDepth() {
		return laneDepth;
	}


//	private void addBoxToLane(ArrayList<Box> boxes) {
//		total_updated_depth += BOX_DEPTH;
//		
//		// right
//		
//		Box lastBoxR = boxes.get(rightTail);
//		lastBoxR.translateZProperty().set(total_updated_depth);
//		rightTail --;
//		
//		if (rightTail == -1 ) {
//			rightTail = (NUM_OF_BOXES/2) - 1;
//		}
//		
//		// left
//		
//		Box lastBoxL = boxes.get(leftTail);
//		lastBoxL.translateZProperty().set(total_updated_depth);
//		leftTail --;
//		
//		if (leftTail == ((NUM_OF_BOXES/2) - 1)) {
//			leftTail = NUM_OF_BOXES - 1;
//		}
//		
//		System.out.println("test");
//		
//	}
	
}
