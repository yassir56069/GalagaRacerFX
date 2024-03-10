package application.UI;

import application.GameState;
import application.Main;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class PauseScreen {
	
	public BorderPane screen;	
	public Boolean isDisplayed = false;
	
	// references
	
	private Group gameGroup;
	
	
	public PauseScreen(Group gameGroup) {
		this.screen = new BorderPane();
		this.isDisplayed = false;
		
		this.gameGroup = gameGroup;
		
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
        double pauseScreenZ = screen.getTranslateZ() + 50;

        for (Node node : gameGroup.getChildren()) {
            if (node != screen ) { // Skip the pause screen itself
                double nodeZ = node.getTranslateZ();

                // Assuming the Z-axis decreases towards the camera
                 
                if (isDisplayed)
                {
                    if (nodeZ > pauseScreenZ) {
                        node.setVisible(true);
                    } else {
                        node.setVisible(false);
                    }
                }
                else
                {
                	node.setVisible(true);
                }
            }
        }
    }
}


