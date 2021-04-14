package FrontEnd;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is used to control the Level editor guide.
 * @author Felix Ifrim
 */

public class LEHowToPlayController extends StateLoad {

    //These variables are used to help with the display of the guide menu.
    @FXML
    private Button leChangeColorButton;

    @FXML
    private Button leTilesButton;

    @FXML
    private Button leStartingPositionsButton;

    @FXML
    private Button leActionTilesButton;

    @FXML
    private Button backToLE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    /**
     * This method is used to open the change color guide.
     */
    public void onChangeColorButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML\\LEHowToUse\\LEColor.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("How to use the level editor");
        primaryStage.show();
        Stage menuStage = (Stage) leChangeColorButton.getScene().getWindow();
        menuStage.close();
    }

    /**
     * This method is used to open the tiles guide.
     */
    public void onLETilesButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML\\LEHowToUse\\LETiles.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("How to use the level editor");
        primaryStage.show();
        Stage menuStage = (Stage) leTilesButton.getScene().getWindow();
        menuStage.close();
    }

    /**
     * This method is used to open the starrting positions guide.
     */
    public void onLEStartingPositions() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML\\LEHowToUse\\StartingPositions.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("How to use the level editor");
        primaryStage.show();
        Stage menuStage = (Stage) leStartingPositionsButton.getScene().getWindow();
        menuStage.close();
    }

    /**
     * This method is used to open the action tiles guide
     */
    public void onLEActionTiles() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML\\LEHowToUse\\LEActionTiles.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("How to use the level editor");
        primaryStage.show();
        Stage menuStage = (Stage) leActionTilesButton.getScene().getWindow();
        menuStage.close();
    }

    /**
     * This method is used to go back to the level editor and close the guide window
     */
    public void onBackToLE() throws IOException {
        Stage menuStage = (Stage) backToLE.getScene().getWindow();
        menuStage.close();
    }
}
