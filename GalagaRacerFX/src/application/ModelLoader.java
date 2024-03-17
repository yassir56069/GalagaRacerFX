package application;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.scene.Group;
import javafx.scene.shape.MeshView;

public class ModelLoader {

    public static Group loadModel(String modelPath) {
        ObjModelImporter importer = new ObjModelImporter();
        importer.read(modelPath);

        Group modelRoot = new Group();

        for (MeshView meshView : importer.getImport()) {
            modelRoot.getChildren().add(meshView);
        }

        return modelRoot;
    }
}