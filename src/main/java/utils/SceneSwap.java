package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The SceneSwap class is a utility class with a single method that swaps the current scene of a JavaFX application to a new FXML file.
 */
public class SceneSwap {

    // Declare Fields

    private static Stage stage;

    // Declare Methods

    /**
     * The swapScene method can be called to load a different FXML file in response to an actionEvent. Does not include functionality for passing data between controllers.
     * @param fxmlFile The path and name for the fxml file to be loaded.
     * @param eventSource The fx:id of the source of the actionEvent. Null if no actionEvent to be used.
     */
    public static void swapScene(String fxmlFile, Object eventSource) {
        try {
            // Create a new FXMLLoader instance and set the location of the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(SceneSwap.class.getResource(fxmlFile));

            // Load the FXML file and get the root node
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            // Get the current stage and set the new scene
            if (stage == null) {
                stage = (Stage) ((javafx.scene.Node) eventSource).getScene().getWindow();
            }
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            // If there was an error, print the stack trace
            System.out.println("An error occurred while swapping scenes: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


