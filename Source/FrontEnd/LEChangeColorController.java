package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is used to control the change color window.
 */

public class LEChangeColorController extends StateLoad {

    //These variables are used to help with the display of the Change Color window.
    @FXML
    private ImageView color1;

    @FXML
    private ImageView color2;

    @FXML
    private ImageView color3;

    @FXML
    private ImageView color4;

    @FXML
    private ImageView color5;

    @FXML
    private ImageView color6;

    @FXML
    private ImageView color7;

    @FXML
    private ImageView color8;

    @FXML
    private ImageView color9;

    @FXML
    private ImageView color10;
    @FXML
    private Button changeColorButton = new Button();

    // These boolean variables are used to check which color has been chosen
    private boolean color1Chosen = false;
    private boolean color2Chosen = false;
    private boolean color3Chosen = false;
    private boolean color4Chosen = false;
    private boolean color5Chosen = false;
    private boolean color6Chosen = false;
    private boolean color7Chosen = false;
    private boolean color8Chosen = false;
    private boolean color9Chosen = false;
    private boolean color10Chosen = false;

    // This variable stores the current selected color name.
    private String selectedColorName;

    // Will hold a reference to the change color controller, allowing us to access the methods found there.
    private final LEMainController controller1;

    public LEChangeColorController(LEMainController controller1) {
        this.controller1 = controller1;
    }

    @Override
    // Initialise the ImageView components with the car images
    public void initialize(URL location, ResourceBundle resources) {
        color1.setImage(new Image("playerYELLOW.png"));
        color2.setImage(new Image("playerTURQUOISE.png"));
        color3.setImage(new Image("playerPINK.png"));
        color4.setImage(new Image("playerORANGE.png"));
        color5.setImage(new Image("playerAQUAMARINE.png"));
        color6.setImage(new Image("playerGREY.png"));
        color7.setImage(new Image("playerBURGUNDY.png"));
        color8.setImage(new Image("playerGLAUCOUS.png"));
        color9.setImage(new Image("playerWHITE.png"));
        color10.setImage(new Image("playerBLACK.png"));
    }

    /**
     * This method shows the yellow car as selected.
     */
    public void onColor1Select() {
        if(color1Chosen == true) {
            color1.setImage(new Image("playerYELLOW.png"));
            color1Chosen = false;
        }
        else {
            // Checks if other colors are pressed and deselect them
            deselectColors();
            // Set the selected color
            color1.setImage(new Image("playerYELLOW-selected.png"));
            color1Chosen = true;
            selectedColorName = "playerYELLOW";
        }

    }

    /**
     * This method shows the turquoise car as selected.
     */
    public void onColor2Select() {
        if(color1Chosen == true) {
            color2.setImage(new Image("playerTURQUOISE.png"));
            color2Chosen = false;
        }
        else {
            // Checks if other colors are pressed and deselect them
            deselectColors();
            // Set the selected color
            color2.setImage(new Image("playerTURQUOISE-selected.png"));
            color2Chosen = true;
            selectedColorName = "playerTURQUOISE";
        }
    }

    /**
     * This method shows the pink car as selected.
     */
    public void onColor3Select() {
        if(color3Chosen == true) {
            color3.setImage(new Image("playerPINK.png"));
            color3Chosen = false;
        }
        else {
            // Checks if other colors are pressed and deselect them
            deselectColors();
            // Set the selected color
            color3.setImage(new Image("playerPINK-selected.png"));
            color3Chosen = true;
            selectedColorName = "playerPINK";
        }
    }

    /**
     * This method shows the orange car as selected.
     */
    public void onColor4Select() {
        if(color4Chosen == true) {
            color4.setImage(new Image("playerORANGE.png"));
            color4Chosen = false;
        }
        else {
            // Checks if other colors are pressed and deselect them
            deselectColors();
            // Set the selected color
            color4.setImage(new Image("playerORANGE-selected.png"));
            color4Chosen = true;
            selectedColorName = "playerORANGE";
        }
    }

    /**
     * This method shows the aquamarine car as selected.
     */
    public void onColor5Select() {
        if(color5Chosen == true) {
            color5.setImage(new Image("playerAQUAMARINE.png"));
            color5Chosen = false;
        }
        else {
            // Checks if other colors are pressed and deselect them
            deselectColors();
            // Set the selected color
            color5.setImage(new Image("playerAQUAMARINE-selected.png"));
            color5Chosen = true;
            selectedColorName = "playerAQUAMARINE";
        }
    }

    /**
     * This method shows the grey car as selected.
     */
    public void onColor6Select() {
        if(color6Chosen == true) {
            color6.setImage(new Image("playerGREY.png"));
            color6Chosen = false;
        }
        else {
            // Checks if other colors are pressed and deselect them
            deselectColors();
            // Set the selected color
            color6.setImage(new Image("playerGREY-selected.png"));
            color6Chosen = true;
            selectedColorName = "playerGREY";
        }
    }

    /**
     * This method shows the burgundy car as selected.
     */
    public void onColor7Select() {
        if(color7Chosen == true) {
            color7.setImage(new Image("playerBURGUNDY.png"));
            color7Chosen = false;
        }
        else {
            // Checks if other colors are pressed and deselect them
            deselectColors();
            // Set the selected color
            color7.setImage(new Image("playerBURGUNDY-selected.png"));
            color7Chosen = true;
            selectedColorName = "playerBURGUNDY";
        }
    }

    /**
     * This method shows the glaucous car as selected.
     */
    public void onColor8Select() {
        if(color8Chosen == true) {
            color8.setImage(new Image("playerGLAUCOUS.png"));
            color8Chosen = false;
        }
        else {
            // Checks if other colors are pressed and deselect them
            deselectColors();
            // Set the selected color
            color8.setImage(new Image("playerGLAUCOUS-selected.png"));
            color8Chosen = true;
            selectedColorName = "playerGLAUCOUS";
        }
    }

    /**
     * This method shows the white car as selected.
     */
    public void onColor9Select() {
        if(color9Chosen == true) {
            color9.setImage(new Image("playerWHITE.png"));
            color9Chosen = false;
        }
        else {
            // Checks if other colors are pressed and deselect them
            deselectColors();
            // Set the selected color
            color9.setImage(new Image("playerWHITE-selected.png"));
            color9Chosen = true;
            selectedColorName = "playerWHITE";
        }
    }

    /**
     * This method shows the black car as selected.
     */
    public void onColor10Select() {
        if(color10Chosen == true) {
            color10.setImage(new Image("playerBLACK.png"));
            color10Chosen = false;
        }
        else {
            // Checks if other colors are pressed and deselect them
            deselectColors();
            // Set the selected color
            color10.setImage(new Image("playerBLACK-selected.png"));
            color10Chosen = true;
            selectedColorName = "playerBLACK";
        }
    }

    /**
     * This method will deselect all the selected colors.
     */
    public void deselectColors() {
        if (color1Chosen == true) {
            color1Chosen = false;
            color1.setImage(new Image("playerYellow.png"));
        }
        if (color2Chosen == true) {
            color2Chosen = false;
            color2.setImage(new Image("playerTURQUOISE.png"));
        }
        if (color3Chosen == true) {
            color3Chosen = false;
            color3.setImage(new Image("playerPINK.png"));
        }
        if (color4Chosen == true) {
            color4Chosen = false;
            color4.setImage(new Image("playerORANGE.png"));
        }
        if (color5Chosen == true) {
            color5Chosen = false;
            color5.setImage(new Image("playerAQUAMARINE.png"));
        }
        if (color6Chosen == true) {
            color6Chosen = false;
            color6.setImage(new Image("playerGREY.png"));
        }
        if (color7Chosen == true) {
            color7Chosen = false;
            color7.setImage(new Image("playerBURGUNDY.png"));
        }
        if (color8Chosen == true) {
            color8Chosen = false;
            color8.setImage(new Image("playerGLAUCOUS.png"));
        }
        if (color9Chosen == true) {
            color9Chosen = false;
            color9.setImage(new Image("playerWHITE.png"));
        }
        if (color10Chosen == true) {
            color10Chosen = false;
            color10.setImage(new Image("playerBLACK.png"));
        }
        selectedColorName = "";
    }

    /**
     * This method is used to confirm the color change (via the "Change Color" button) and updates the color
     * of the car.
     */
    public void onChangeColor() {
        if (controller1.getColor1EditStatus() == true) {
            controller1.updateColor1(selectedColorName);
        }
        if (controller1.getColor2EditStatus() == true) {
            controller1.updateColor2(selectedColorName);
        }
        if (controller1.getColor3EditStatus() == true) {
            controller1.updateColor3(selectedColorName);
        }
        if (controller1.getColor4EditStatus() == true) {
            controller1.updateColor4(selectedColorName);
        }
        Stage menuStage = (Stage) changeColorButton.getScene().getWindow();
        menuStage.close();
    }
}