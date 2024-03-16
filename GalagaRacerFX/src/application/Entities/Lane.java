package application;

import java.io.File;
import java.util.ArrayList;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/**
 * The {@code Lane} class represents a lane in a space racing game, consisting of a series of pillars
 * on the left and right for the player's ship to navigate through.
 * 
 * <p>The lane is defined by alternating pillars, forming a pathway for the player's ship. It is
 * characterized by various properties, including the color of the alternating pillars, the list
 * of pillars, dimensions of the pillars, depth of the pillars, inner space between left and right
 * pillars, total size of the lane, tails of the left and right lanes, and the depth of the lane in
 * the Z-axis.
 * 
 * <p>The alternating color of the pillars is specified using a {@link javafx.scene.paint.PhongMaterial}
 * variable named {@code material}. The actual pillars are stored in an {@link java.util.ArrayList} of
 * {@link javafx.scene.shape.Box} objects, named {@code pillarsList}.
 * 
 * <p>The dimensions of the pillars are stored in a private {@link javafx.geometry.Point3D} variable
 * named {@code pillarDimensions}. Additionally, the depth of the pillars is redundantly stored in an
 * integer variable named {@code pillarDepth} for convenience when checking the Z value of
 * {@code pillarDimensions}.
 * 
 * <p>The inner space between the left and right pillars is specified by the private integer
 * variable {@code laneInnerSpace}. The total size of the lane, representing the number of pillars,
 * is stored in an integer variable named {@code laneSize}.
 * 
 * <p>The tail ends of the left and right lanes are represented by {@code rightTail} and {@code leftTail}
 * respectively. The overall length of the lane in the Z-axis is stored in a private integer variable
 * named {@code laneDepth}.
 * 
 * @author Yassir Hoossan Buksh
 * @version 1.0
 */
public class Lane {

	private PhongMaterial material = new PhongMaterial(Color.DARKCYAN);
	private PhongMaterial material_alt = new PhongMaterial(Color.GRAY);
	
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
	 * @param pillarRowSize - number of pillars used in ONE side of the lane.
	 * @param laneIS 		- The inner spacing of the lanes between each other.
	 * @param nodeDim 		- dimensions of each individual node.
	 */
	public Lane(int pillarRowSize, int laneIS, Point3D pillarDim) {
		this.laneInnerSpace 	= laneIS;
		this.laneSize 			= pillarRowSize * 2;
		this.rightTail 			= (laneSize/2) - 1;
		this.leftTail 			= laneSize - 1;
		this.pillarDimensions 	= pillarDim;
		
		this.pillarDepth 		= (int) pillarDimensions.getZ();
		
		this.laneDepth 			= (int) (pillarRowSize * pillarDepth );
		
		

		Image bumpImage = new Image(String.valueOf(new File("file:./src/application/Assets/hexBump.png")));
		Image specImage = new Image(String.valueOf(new File("file:./src/application/Assets/hexSpec.png")));
		Image diffImage = new Image(String.valueOf(new File("file:./src/application/Assets/hexDiff.png")));
//		Image dispImage = new Image(String.valueOf(new File("file:./src/application/Assets/hexIllum.png")));
		
		material.setBumpMap(bumpImage);
		material.setSpecularMap(specImage);
		material.setDiffuseMap(diffImage);
//		material.setSelfIlluminationMap(dispImage);
		
		Image bumpImage_alt = new Image(String.valueOf(new File("file:./src/application/Assets/tileBump.png")));
		Image specImage_alt = new Image(String.valueOf(new File("file:./src/application/Assets/tileSpec.png")));
		Image diffImage_alt = new Image(String.valueOf(new File("file:./src/application/Assets/tileDiff.png")));
//		Image dispImage_alt = new Image(String.valueOf(new File("file:./src/application/Assets/tileIllum.png")));
		
		material_alt.setBumpMap(bumpImage_alt);
		material_alt.setSpecularMap(specImage_alt);
		material_alt.setDiffuseMap(diffImage_alt);
//		material_alt.setSelfIlluminationMap(dispImage_alt);
		
		
		for (int i = 0; i < pillarRowSize * 2; i++ ) {
			pillarsList.add(i, new Box(pillarDim.getX(), pillarDim.getY(), pillarDim.getZ()));
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
			else 
				currentNode.setMaterial(material_alt);
		}
		
		newDepth =  (int) this.pillarDimensions.getZ(); 
		
		for (int i = this.laneSize; i-- > (this.laneSize/2); ) {
			Box currentNode = pillarsList.get(i);
			
			currentNode.translateXProperty().set(-this.laneInnerSpace);
			currentNode.translateZProperty().set(newDepth);
			
			newDepth = newDepth + (int) this.pillarDimensions.getZ(); ; //update box depth
			
			
			if (Util.isEven(i)) currentNode.setMaterial(material); // color box in checkered fashion
			else 
				currentNode.setMaterial(material_alt);
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
	

	public int getPillarDepth() {
		return pillarDepth;
	}
	
	public ArrayList<Box> getPillarsList() {
		return pillarsList;
	}

	public int getLaneSize() {
		return laneSize;
	}

	public Box getRightTail() {
		return pillarsList.get(rightTail);
	}

	public Box getLeftTail() {
		return pillarsList.get(rightTail);
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

	
}
