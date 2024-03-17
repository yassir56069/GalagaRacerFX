package application;

import javafx.scene.Group;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import java.util.Random;

public class RandomShapes {

    private static final int NUM_VERTICES = 6;
    private static final double MAX_SIZE = 50.0;

    public RandomShapes(Group meshGroup) {

            TriangleMesh mesh = createRandomMesh();
            MeshView meshView = new MeshView(mesh);
            meshGroup.getChildren().add(meshView);
    }

    private TriangleMesh createRandomMesh() {
        TriangleMesh mesh = new TriangleMesh();

        Random random = new Random();
        for (int i = 0; i < NUM_VERTICES; i++) {
            double x = random.nextDouble() * MAX_SIZE;
            double y = random.nextDouble() * MAX_SIZE;
            double z = random.nextDouble() * MAX_SIZE;
            mesh.getPoints().addAll((float) x, (float) y, (float) z);
        }

        int[] faces = {0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0}; // Define a face with all vertices
        mesh.getFaces().addAll(faces);

        return mesh;
    }
}