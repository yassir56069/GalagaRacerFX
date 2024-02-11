package application;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;


/**
 * The {@code ControlShip} class handles control input and manages the game loop for the player's ship in the game.
 * It encapsulates functionality related to ship speed control, direction, and game state.
 * 
 * <p>This class is responsible for interpreting user input, updating the ship's speed and direction, and managing the game loop.
 * It interacts with the {@link PlayerShip} class to control the ship's behavior.
 * 
 * <p>The ship's speed is controlled by the {@code minSpeed}, {@code maxSpeed}, and {@code currSpeed} parameters.
 * The {@code shiftProp} is a proportional value of the current speed, influencing the ship's lateral movement.
 * 
 * <p>Additionally, three boolean values, {@code movingZ}, {@code movingL}, and {@code movingR}, indicate the ship's movement status.
 * {@code movingZ} represents forward or backward movement, while {@code movingL} and {@code movingR} indicate left and right gliding.
 * 
 * <p>The game loop is managed within this class, providing a continuous update of the ship's state and responding to user input.
 * 
 * <p>Example usage:
 * <pre>
 * {@code
 * // Create an instance of ControlShip
 * ControlShip controlShip = new ControlShip(playerShip, minSpeed, maxSpeed);
 * 
 * // Set initial ship speed
 * controlShip.setCurrSpeed(50.0);
 * 
 * // Start the game loop
 * controlShip.startGameLoop();
 * }
 * </pre>
 * 
 * @author Your Name
 * @version 1.0
 */
public class ControlShip {

	private PlayerShip playerReference;
	
	private double minSpeed; // gliding speed
	private double maxSpeed;
	private double currSpeed;
	private double shiftProp; // proportion of speed for left and right movement

	private boolean movingZ, movingL, movingR;

	
	public ControlShip(PlayerShip player, Scene scene, double minSpeed, double maxSpeed, double shiftProp)
	{
		this.playerReference = player;
		this.movingZ = false;
		this.movingL = false;
		this.movingR = false;
		this.maxSpeed = maxSpeed;
		this.minSpeed = minSpeed;
		this.currSpeed = minSpeed;
		this.shiftProp = shiftProp;
		
		// handle keyboard
        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
        scene.setOnKeyReleased(event -> handleKeyRelease(event.getCode()));

	}

    public void startGameLoop(Lane lane) {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	
                // Update game logic in each frame
            	
            	//collision
            	if (playerReference.hasCollided(lane))
            	{
            		System.out.println("Collision Detected!");
            	}
            	
            	// movement
                if (movingZ) {
                	moveShip();
                }
                else
                {
                	stopShip();
                }
                playerReference.updateLanePillarsPosition(lane);
            }
        };
        gameLoop.start();
    }

	private void moveShip()
	{	if (movingZ)
		{
			playerReference.MovePlayerZ(currSpeed);
		}
		
		if (movingL)
		{
			playerReference.MovePlayerLeftRight(currSpeed * -shiftProp);
		}
		
		if (movingR)
		{
			playerReference.MovePlayerLeftRight(currSpeed * shiftProp);
		}
		
		if (currSpeed < maxSpeed) currSpeed += 0.2;
	}
	
	private void stopShip()
	{	if (currSpeed > minSpeed) currSpeed -= 0.5;

			playerReference.MovePlayerZ(currSpeed);

		if (movingL)
		{
			playerReference.MovePlayerLeftRight(currSpeed * -shiftProp);
		}
		
		if (movingR)
		{
			playerReference.MovePlayerLeftRight(currSpeed * shiftProp);
		}
		if ( (int) currSpeed == minSpeed) currSpeed = minSpeed;
	}
      
    private void handleKeyPress(KeyCode code) {
        switch (code) {
            case W:
                this.movingZ = true;
                break;
            case A:
                this.movingL = true;
                break;
            case D:
                this.movingR = true;
                break;
            default:
            	break;
        }
    }
    
    private void handleKeyRelease(KeyCode code) {
        switch (code) {
        case W:
            this.movingZ = false;
            break;
        case A:
            this.movingL = false;
            break;
        case D:
            this.movingR = false;
            break;
        default:
        	break;
    }
    }

}
