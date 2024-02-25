package application;


import javafx.geometry.Point3D;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

/**
 * The {@code Particle} class represents an individual particle in a particle system,
 * used to simulate a thruster effect or exhaust behind a game entity.
 * Particles have a limited lifespan, velocity, and gradually fade out over time.
 *
 * <p>Gravity is applied to the particle along the Z-axis, determining its downward acceleration.
 * The maximum lifespan of a particle is capped at {@code MAX_LIFE}.
 * The maximum speed of the particle along any axis is defined by {@code MAX_SPEED}.
 *
 * <p>The {@code Particle} class utilizes a JavaFX {@code Sphere} to visually represent the particle in 3D space.
 * The remaining lifespan of the particle is tracked by the {@code life} variable.
 * Velocity components along the X, Y, and Z axes are stored in the {@code speedX}, {@code speedY}, and {@code speedZ} variables, respectively.
 * These components determine the particle's movement direction and rate in 3D space.
 * 
 * @author Yassir Hossan Buksh
 * @version 1.0
 */
public class Particle {

    private double gravity; 	// 0.01
    private double maxLife; 	// 50

    private Sphere sphere;
    private double life;
    private double speedX, speedY, speedZ;
    private PhongMaterial material = new PhongMaterial();

    /**
     * Constructs a new {@code Particle} with a random initial velocity.
     * The constructor initializes the particle's graphical representation and sets a random velocity.
     */
    public Particle(Point3D coord, Color c, BlendMode blendMode, double maxLife, double gravity, double speedX, double speedY, double speedZ) {
    	
    	this.maxLife = maxLife;
    	this.gravity = gravity;
    	
    	
        material.setDiffuseColor(c);
        material.setSpecularColor(Color.PURPLE);
        
        sphere = new Sphere(0.1);

       
        
        sphere.setMaterial(material);
        sphere.setBlendMode(blendMode);
        sphere.setTranslateX(coord.getX());
        sphere.setTranslateY(coord.getY());
        sphere.setTranslateZ(coord.getZ());
        life = maxLife;

        this.speedX = speedX; //((Math.random() - 0.5) * this.maxSpeed) * 0.05;
        this.speedY = speedY; //((Math.random() - 0.5) * this.maxSpeed) * 0.05;
        this.speedZ = speedZ; //-this.maxSpeed;
    }
    
    Sphere getSphere() {
        return sphere;
    }

    void update() {
        life--;

        // Update position based on velocity
        sphere.setTranslateX(sphere.getTranslateX() + this.speedX);
        sphere.setTranslateY(sphere.getTranslateY() + this.speedY);
        sphere.setTranslateZ(sphere.getTranslateZ() + this.speedZ);

        // Apply gravity
        speedZ += this.gravity;

        // Fade out particle over time
        double opacity = life / this.maxLife;
        sphere.setOpacity(opacity);
    }

    boolean isVisible() {
        return life > 0;
    }
    

}
