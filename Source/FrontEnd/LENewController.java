package FrontEnd;

import BackEnd.StringTrimmer;
import MessageOfTheDay.MessageOfTheDay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Class that manages the Create New Level screen
 *
 * @author Pat Sambor
 */
public class LENewController extends StateLoad {
    @FXML
    Button getStart = new Button();
    @FXML
    Label MoTD = new Label();
    @FXML
    ComboBox selNumPlayers = new ComboBox();
    @FXML
    Spinner spnX = new Spinner();
    @FXML
    Spinner spnY = new Spinner();
    @FXML
    TextArea txtName = new TextArea();
    @FXML
    ComboBox selAuthor = new ComboBox();

    //Boolean made here to ensure all 3 boxes are set before you can start
    Boolean boolPlayers = false;
    Boolean boolAuthor = false;
    Boolean boolText = false;
    Boolean boolPizza = false;

    String tempText = "";

    private final String MAIN_MENU_SFX = "Assets\\SFX\\mainmenu.mp3";
    private final AudioClip MAIN_MENU_AUDIO = new AudioClip(new File(MAIN_MENU_SFX).toURI().toString());
    private final String RETURN_SFX = "Assets\\SFX\\return.mp3";
    private final AudioClip RETURN_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());

    WindowLoader wl;

    /**
     * Initialisation class for LENew Controller, only thing of note here that isn't just a setter
     * is the fact that I'm chopping off .txt with the stringtrimmer class for the combobox
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String message;
        try {
            message = MessageOfTheDay.puzzle();
        } catch (Exception e) {
            message = "Error with Server" + e.getCause();
        }
        MoTD.setText(message);
        getStart.setDisable(true);
        //Setting all the combobox elements

        File playerLocation = new File("SaveData\\UserData\\");
        String[] players = playerLocation.list();

        ArrayList<String> listPlayers = new ArrayList<>(Arrays.asList(players));
        for (int p = 0; p < listPlayers.size(); p++) {
            listPlayers.set(p, StringTrimmer.trim(listPlayers.get(p), ".txt"));
        }
        ObservableList<String> oListPlayer = FXCollections.observableArrayList(listPlayers);
        selNumPlayers.setItems(oListPlayer);

        selAuthor.setItems(oListPlayer);

        //Same in spinner form
        SpinnerValueFactory factoryX = new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 9, 1);
        factoryX.setWrapAround(false);
        SpinnerValueFactory factoryY = new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 9, 1);
        factoryY.setWrapAround(false);
        spnX.setValueFactory(factoryX);
        spnY.setValueFactory(factoryY);

    }

    /**
     * Runs when you press the Get Started button, sets all appropriate values for a new game in LEMain
     */
    public void onGetStart() {
        boolean fileExist = false;
        String[] fileNames;
        File folder = new File("SaveData\\CustomLevels\\");

        fileNames = folder.list();

        for (String name : fileNames) {
            if (name.endsWith(".txt")) {
                name = name.substring(0, name.length() - 4);
            }
            if (name.equals(txtName.getText())) {
                fileExist = true;
            }
        }

        //It's you who's out Gobby, outta your mind!
        if (fileExist == false) {
            if (txtName.getText().equals("")) {
                boolPizza = true;
            }

            wl = new WindowLoader(getStart);
            HashMap<String, String> initData = getInitData();
            initData.put("name", txtName.getText());
            initData.put("width", String.valueOf(spnX.getValue()));
            initData.put("height", String.valueOf(spnY.getValue()));
            initData.put("author", (String) selAuthor.getValue());
            initData.put("loaded", "false");

            if (boolPizza) {
                initData.put("pizza", "true");
            } else {
                initData.put("pizza", "false");
            }

            wl.load("LEMain", initData);
            MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        } else {
            CustomAlerts.Warning("Error", "File name already exists, please enter a different file name.");
        }
    }

    /**
     * (Shockingly) Takes you back to the previous screen
     */

    public void onBack() {
        wl = new WindowLoader(getStart);
        wl.load("LEStart", getInitData());
        RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    /**
     * Runs when you select a person in the "author" combobox
     */
    public void onAuthor() {
        boolAuthor = true;
        if (boolAuthor && boolText) {
            getStart.setDisable(false);
        }
    }

    /**
     * Runs everytime the text field is updated and sets the get started button to disabled
     */
    public void onText() {

        boolText = !txtName.getText().equals("");

        getStart.setDisable(!boolAuthor || !boolText);

    }

    /**
     * Creates a new window that shows the jukebox
     *
     * @throws IOException
     */
    public void onJukebox() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML\\JukeBox.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("The Getaway Jukebox!");
        primaryStage.show();
    }

}

