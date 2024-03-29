package application.State;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import application.Main;
import application.Entities.Lane;
import application.Entities.PlayerShip;
import application.Entities.GroupedEntities.Particle;
import application.Entities.GroupedEntities.StaticEntity;
import application.Entities.GroupedEntities.Emitters.Emitter;
import application.Entities.GroupedEntities.Emitters.ThrustEmitter;
import application.UI.GameOverScreen;
import application.UI.HUD;
import application.UI.PauseScreen;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


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

	private Stage primaryStage;
	
	private PlayerShip playerReference;
	
	private double minSpeed; // gliding speed
	private double maxSpeed;
	private double currSpeed;
	private double shiftProp; // proportion of speed for left and right movement

	private boolean movingZ, movingL, movingR, movingU, movingD;

	//particles
    public List<Particle> particles = new ArrayList<>();
    public Group particleGroup = new Group();
    
    private Emitter e = new ThrustEmitter(particles, particleGroup);

    

    // Interface References
    PauseScreen pause;
    HUD hud;

	private double newScore;
    
	
	public ControlShip(Stage primaryStage, PlayerShip player, HUD hud, PauseScreen pause, Scene scene, double minSpeed, double maxSpeed, double shiftProp)
	{
		this.primaryStage = primaryStage;
		this.pause = pause;
		this.hud = hud;
		this.playerReference = player;
		
		this.newScore = 0;
	
		this.movingZ = false;
		this.movingL = false;
		this.movingR = false;
        this.movingU = false;
        this.movingD = false;
        
		this.maxSpeed = maxSpeed;
		this.minSpeed = minSpeed;
		this.currSpeed = minSpeed;
		this.shiftProp = shiftProp;
		
		// handle keyboard -- move to a seperate function at some point maybe!
        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
        scene.setOnKeyReleased(event -> handleKeyRelease(event.getCode()));

	}

    public double getCurrSpeed() {
		return currSpeed;
	}

	public void startGameLoop(Lane lane, Point3D UI_Offset, GameCamera c, StaticEntity obstacle, StaticEntity stars, StaticEntity debris) {
		
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {

            	newScore = hud.getScore();
            	switch(Main.gameState) {
            	
            	case RUNNING:
            		
                	double particleSpeed = currSpeed / 10;
                	
                	
                    // Update game logic in each frame
                	updateParticles(); //particles
                	e.emit(new Point3D(playerReference.getCurrPosition().getX(),playerReference.getCurrPosition().getY(),(playerReference.getCurrPosition().getZ() - 100) + currSpeed),  10 + (int) (currSpeed * 0.7), new Point3D(particleSpeed * 0.4, particleSpeed * 0.4, currSpeed * 2));
                	
            		
                	
                	//collision
                	if (playerReference.hasCollided(lane) || playerReference.hasCollidedObstacle(obstacle))
                	{
                		System.out.println("Collision Detected!");
                		newScore -= 50;

                		 hud.flashRed();
                		
                		currSpeed -= 10;
                	}
                	else
                	{ 
                		newScore += 1;
                	}

                	playerReference.rotationLogicX();
                	// movement
                    if (movingZ) {
                    	newScore += 3;
                    	moveShip();
                    }
                    else
                    {
                    	stopShip();
                    }
                    playerReference.updateLanePillarsPosition(lane);
                                        
                    c.bindToCamera(pause.screen, UI_Offset);
                    c.bindToCamera(hud.screen, hud.pos);
                    
                    obstacle.updateEntitiesPositionObstacle();
                    
                    stars.updateEntitiesPosition();
                    debris.updateEntitiesPosition();
                    
                    if (newScore < 0 || currSpeed < 0) 
                    {
                    	Main.gameState = GameState.GAMEOVER;
                    }
                    else {
                    	hud.setScore((int) newScore);
                    }
                    
                    
                    break;
                case PAUSED:
                    // Additional actions when the game is paused
                    break;
                    
                case GAMEOVER:
            		// Transition to the game over screen
                    GameOverScreen gameOverScreen = new GameOverScreen(Main.WIDTH, Main.HEIGHT, hud);
                    Scene gameOverScene = new Scene(gameOverScreen);
                    primaryStage.setScene(gameOverScene);

                    
                    break;
            	
            	}
            	
            }
        };
        gameLoop.start();
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
	{	
		System.out.println(playerReference.getCurrPosition().getY());
		if (movingZ)
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
		
		if (movingU)
		{
			if (playerReference.getCurrPosition().getY() > -60)
			playerReference.MovePlayerUpDown((currSpeed * -shiftProp) * 0.25);
		}
		
		if (movingD)
		{if 
			(playerReference.getCurrPosition().getY() < 35)
			playerReference.MovePlayerUpDown((currSpeed * shiftProp) * 0.25);
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
                this.playerReference.setRotateLeft(true);
                break;
            case D:
                this.movingR = true;
                this.playerReference.setRotateRight(true);
                break;
            case E:
            	this.movingU = true;
            	this.playerReference.setRotateUp(true);
            	break;
            case Q:
            	this.movingD = true;
            	this.playerReference.setRotateDown(true);
            	break;                
                
            case ESCAPE:
            	pause.togglePause();
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
            this.playerReference.setRotateLeft(false);
            break;
        case D:
            this.movingR = false;
            this.playerReference.setRotateRight(false);
            break;
        case E:
        	this.movingU = false;
        	this.playerReference.setRotateUp(false);
        	break;
        case Q:
        	this.movingD = false;
        	this.playerReference.setRotateDown(false);
        	break;
        default:
        	break;
    }
    }

}
