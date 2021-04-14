package FrontEnd;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is used to control the starting positions page of the level editor guide.
 * @author Felix Ifrim
 */

public class LEStartingPositionsController extends StateLoad {

    //These variables are used to help with the display of the starting positions menu.
    @FXML
    private Button backButton;

    @FXML
    private ImageView imageview1, imageview2;

    /**
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageview1.setImage(new Image("LETutorial_Car_1.png"));
        imageview2.setImage(new Image("LETutorial_Car_2.png"));
    }

    /**
     * This method is used to go back to the guide menu and close the current window.
     */
    public void onBackButton() throws IOException {
        Stage menuStage = (Stage) backButton.getScene().getWindow();
        menuStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML\\LEHowToUse\\LEHowToPlay.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("How to use the level editor");
        primaryStage.show();
    }
}
