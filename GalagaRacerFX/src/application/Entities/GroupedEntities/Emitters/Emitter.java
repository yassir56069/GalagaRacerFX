package application;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point3D;
import javafx.scene.Group;

public abstract class Emitter {
	


	public List<Particle> particles = new ArrayList<>();
    public Group particleGroup = new Group();
	
    public Emitter(List<Particle> particles, Group particleGroup) {
		this.particles = particles;
		this.particleGroup = particleGroup;
	}
    
	public abstract List<Particle> emit(Point3D coords, int numberOfParticles, Point3D Velocity);
 }
