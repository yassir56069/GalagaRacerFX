package application;

import java.util.List;

import javafx.geometry.Point3D;
import javafx.scene.effect.BlendMode;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class ThrustEmitter extends Emitter {

	
	public ThrustEmitter(List<Particle> particles, Group particleGroup) {
		super(particles, particleGroup);
	}

	@Override
	public List<Particle> emit(Point3D coords, int numberOfParticles, Point3D Velocity) {
        for (int i = 0; i < numberOfParticles; i++) {
        	int randomGreen =  110 + (int) (Math.random() * 150);
        	if (randomGreen > 255) randomGreen = 255;
        	
            Particle particle = new Particle(
            		new Point3D(coords.getX(), coords.getY(), coords.getZ()),
            		Color.rgb(247, randomGreen, 40),
            		BlendMode.ADD,
            		80,
            		-0.2,
            		(Math.random() -0.5) * Velocity.getX(),
            		(Math.random() - 0.5) * Velocity.getY(),
            		(Math.random() - 0.5) * -Velocity.getZ()
            		);
            		
            particles.add(particle);
            particleGroup.getChildren().add(particle.getSphere());
        }
		return particles;
	}

}
