package application.UI;

import java.util.ArrayList;

import application.Main;
import application.Entities.PlayerShip;
import application.State.GameState;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Box;

public class PauseScreen {
	
	public BorderPane screen;	
	public Boolean isDisplayed = false;
	
	// references
	private PlayerShip player;
	private Group gameGroup;
	private ArrayList<Box> pillars;
	
	
	public PauseScreen(Group gameGroup, PlayerShip player, ArrayList<Box> pillars) {
		this.screen = new BorderPane();
		this.isDisplayed = false;
		
		this.gameGroup = gameGroup;
		this.player = player;
		this.pillars = pillars;
		
	}

	public void togglePause() {
	    if (Main.gameState == GameState.RUNNING) {
	    	Main.gameState = GameState.PAUSED;
	        isDisplayed = true;
	        screen.setVisible(isDisplayed); // Show pause screen
	        toggleNodesVisibility(gameGroup);
	        System.out.println("Pause Screen Bounds: " + screen.getBoundsInParent());
	    } else if (Main.gameState == GameState.PAUSED) {
	    	Main.gameState = GameState.RUNNING;
	        isDisplayed = false;
	        screen.setVisible(isDisplayed); // Hide pause screen
	        toggleNodesVisibility(gameGroup);
	    }
	    
	}

    public void toggleVisibility() {
        isDisplayed = !isDisplayed;
        updateScreenVisibility();
    }
	
    private void updateScreenVisibility() {
        screen.setVisible(isDisplayed);
    }
    
    private void toggleNodesVisibility(Group gameGroup) {	
        double pauseScreenZ = screen.getTranslateZ() - 200;

        for (int i = 0; i < gameGroup.getChildren().size(); i++) {
        	Node currentNode = gameGroup.getChildren().get(i);
        	
        	// we dont want to display the player when the pause screen is shown
        	
        	if ( (pillars.contains(currentNode) ||currentNode == player.getShipModel()) && currentNode != screen )
        	{
        		  double nodeZ = currentNode.getTranslateZ();
	              if (isDisplayed)
	              {
	                  if (nodeZ > pauseScreenZ) {
	                	  currentNode.setVisible(false);
	                  } else {
	                	  currentNode.setVisible(true);
	                  }
	              }
	              else
	              {
	            	  currentNode.setVisible(true);
	              }
	        	}
        }
        
    }
}


