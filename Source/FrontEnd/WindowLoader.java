package FrontEnd;

import BackEnd.Coordinate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

/***
 * This class is used to load new windows.
 * @author Christian Sanger
 */
public class WindowLoader {
    private static final String fileLocation = "FrontEnd\\FXML\\";
    // Reference to the primary stage
    private static Stage w;
	FXMLLoader loader;
    /***
     * Creates a window loader that changes the scene shown to the user.
     * @param window any Node object on the stage that you wish control.
     */
    public WindowLoader(Node window) {
        w = (Stage) window.getScene().getWindow();
    }

	/**
	 * Creates a new window loader that can change the current scene in this window
	 * @param primaryStage primary stage as reference to which window to change
	 */
	public WindowLoader(Stage primaryStage) {
		w = primaryStage;
	}

	public LEMainController getController()	{ return loader.getController();}

	/***
	 * swaps the scene for the given scene. Window should be the scene file name
	 * i.e. to swap to MenuScreen.fxml use "MenuScreen"
	 *
	 * @param window scene name
	 * @param initData state of application
	 */
	public void load(String window, HashMap<String, String> initData) {
		Parent root = null;
		try {
			loader = new FXMLLoader();
			loader.setLocation(Objects.requireNonNull(getClass().getClassLoader().getResource(fileLocation + window + ".fxml")));
			root = loader.load();
			StateLoad controller = loader.getController();
			controller.setInitData(initData);
			controller.initialize(null, null);
		} catch (IOException e) {
			System.out.println(window + " Failed to load due to " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		if (root == null) {
			System.out.print("Scene loading failed, " + window + " could not be loaded");
			System.exit(1);
		} else {
			if (w.getScene() == null) {
				w.setFullScreen(true);
				w.setScene(new Scene(root));
			} else {
				w.setFullScreen(true);
				w.getScene().setRoot(root);
			}
		}
	}

	public void loadCustom(String window, HashMap<String, String> initData, Slot[][] slots, String[] startLocationX, String[] startLocationY, ArrayList<Coordinate> upgradePos) {
		Parent root = null;
		try {
			loader = new FXMLLoader();
			loader.setLocation(Objects.requireNonNull(getClass().getClassLoader().getResource(fileLocation + window + ".fxml")));
			root = loader.load();
			LEMainController controller = loader.getController();
			controller.setUpgrades(upgradePos);
			System.out.println(upgradePos.get(0).getY());
			controller.setInitData(initData);
			controller.setArrayBoard(slots);
			controller.setPlayerCoords(startLocationX, startLocationY);
			controller.initialize(null, null);
		} catch (IOException e) {
			System.out.println(window + " Failed to load due to " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		if (root == null) {
			System.out.print("Scene loading failed, " + window + " could not be loaded");
			System.exit(1);
		} else {
			if (w.getScene() == null) {
				w.setFullScreen(true);
				w.setScene(new Scene(root));
			} else {
				w.setFullScreen(true);
				w.getScene().setRoot(root);
			}
		}
	}


}
