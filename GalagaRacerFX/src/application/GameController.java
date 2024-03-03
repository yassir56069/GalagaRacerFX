package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * The {@code GameController} class handles control input and manages the game loop for the player's ship in the game.
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
 * @version 1.1
 */
public class GameController {

	//object references
	private PlayerShip player;
	private Lane lane;
	private StaticEntity obstacle;
	private StaticEntity stars;
	
	
	
	
	private double minSpeed; // gliding speed
	private double maxSpeed;
	private double currSpeed;
	private double shiftProp; // proportion of speed for left and right movement

	private boolean movingZ, movingL, movingR;

	private GameState gs = GameState.RUNNING;

	//particles
    public List<Particle> particles = new ArrayList<>();
    public Group particleGroup = new Group();
    
    private Emitter e = new ThrustEmitter(particles, particleGroup);
    
	
	public GameController(PlayerShip player, Lane lane, StaticEntity obstacle, StaticEntity stars, Scene scene, double minSpeed, double maxSpeed, double shiftProp)
	{

        
      
//        pauseScene = new Scene(gamePausedStackPane, Main.WIDTH, Main.HEIGHT);
        
		this.player = player;
		this.lane = lane;
		this.obstacle = obstacle;
		this.stars = stars;
		
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

    public void startGameLoop(StackPane gameGroup) {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
           
            	if (gs.equals(GameState.RUNNING)) {
            		updateGameLogic();
            	}
            	else {
            		pauseScreen(gameGroup);
            	}
            }
        };

        gameLoop.start();
        
    }
    
    private void pauseScreen( StackPane gameGroup) {
    	if (gs.equals(GameState.PAUSED)) {
            StackPane overlayPane = new StackPane();
            overlayPane.setStyle("-fx-background-color: black;");
            overlayPane.setVisible(false);

            // Create a label for the paused menu
            Label pausedLabel = new Label("Game Paused");
            pausedLabel.setStyle("-fx-font-size: 24; -fx-text-fill: white;");
            StackPane.setAlignment(pausedLabel, javafx.geometry.Pos.CENTER);
            overlayPane.getChildren().add(pausedLabel);
            
            
            gameGroup.getChildren().add(overlayPane);
            overlayPane.setVisible(true);
    	}
    	else {
            // Remove the pause screen when the game is resumed
            gameGroup.getChildren().removeIf(node -> node instanceof StackPane);
        }
    }

    public void updateGameLogic() {
    	double particleSpeed = currSpeed / 10;
        
        // Update game logic in each frame
    	updateParticles(); //particles
    	e.emit(new Point3D(player.getCurrPosition().getX(),player.getCurrPosition().getY() + 5,(player.getCurrPosition().getZ() - 100) + currSpeed),  10 + (int) (currSpeed * 0.7), new Point3D(particleSpeed * 0.4, particleSpeed * 0.4, currSpeed * 2));
    	
    	//collision
    	if (player.hasCollided(lane))
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
        player.updateLanePillarsPosition(lane);
        obstacle.updateEntitiesPosition();
        
    
  
        stars.updateEntitiesPosition();
    }
    
    public void updateParticles() {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.update();

            // Remove particles that are no longer visible or active
            if (!particle.isVisible()) {
                iterator.remove();
                particleGroup.getChildren().remove(particle.getSphere());
            }
        }
    }
	
	private void moveShip()
	{	if (movingZ)
		{
			player.MovePlayerZ(currSpeed);
		}
		
		if (movingL)
		{
			player.MovePlayerLeftRight(currSpeed * -shiftProp);
		}
		
		if (movingR)
		{
			player.MovePlayerLeftRight(currSpeed * shiftProp);
		}
		
		if (currSpeed < maxSpeed) currSpeed += 0.2;
	}
	
	private void stopShip()
	{	if (currSpeed > minSpeed) currSpeed -= 0.5;

			player.MovePlayerZ(currSpeed);

		if (movingL)
		{
			player.MovePlayerLeftRight(currSpeed * -shiftProp);
		}
		
		if (movingR)
		{
			player.MovePlayerLeftRight(currSpeed * shiftProp);
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
            case ESCAPE:
            	gamePause();
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

    private void gamePause() {
    	switch (gs){
    		case RUNNING:
    			gs = GameState.PAUSED;
    			break;
    		case PAUSED:
    			gs = GameState.RUNNING;
    			
    		default:
    			break;
    	}
    }
}
