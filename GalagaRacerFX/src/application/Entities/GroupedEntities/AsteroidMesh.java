package application.Entities.GroupedEntities;

import javafx.scene.shape.TriangleMesh;

public class AsteroidMesh {


    public static TriangleMesh createAsteroidMesh() {

        TriangleMesh mesh = new TriangleMesh();
        
        float[] vertices = generateVertexCoordinates();
        float[] texCoords = generateTextureCoordinates();
        int[] faces = generateFaces();
        
        mesh.getPoints().addAll(vertices);
        mesh.getTexCoords().addAll(texCoords);
        mesh.getFaces().addAll(faces);
        
        return mesh;
    }

    private static float[] generateVertexCoordinates() {
        // Define vertex coordinates randomly within a certain range
        int numVertices = 10; // specify the number of vertices
        float[] vertices = new float[numVertices * 3]; // 3 coordinates (x, y, z) per vertex
        
        // Populate the array with random coordinates
        for (int i = 0; i < numVertices * 3; i++) {
            vertices[i] = (float) (Math.random() * 10 - 5); // random value within a range (-5 to 5)
        }
        return vertices;
    }

    private static float[] generateTextureCoordinates() {
        // Define texture coordinates based on vertex coordinates
        // These can be simple calculations or random values
        int numTexCoords = 10; // specify the number of texture coordinates
        float[] texCoords = new float[numTexCoords * 2]; // 2 coordinates (u, v) per texture coordinate
        
        // Populate the array with calculated or random texture coordinates
        for (int i = 0; i < numTexCoords * 2; i++) {
            texCoords[i] = (float) Math.random(); // random value between 0 and 1
        }
        return texCoords;
    }

    private static int[] generateFaces() {
        int[] faces = {
            0, 0, 1, 1, 2, 2, // face 1
            3, 0, 4, 1, 5, 2  // face 2
        }; // indices to form triangles
        
        return faces;
    }
}