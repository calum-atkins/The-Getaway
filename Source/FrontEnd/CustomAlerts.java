package FrontEnd;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;

import java.util.Optional;

/**
 * This class is a place to store common popup dialogs and display them in the program
 * @author Pat Sambor
 */
public class CustomAlerts {
    /**
     * A method to display a dialogue box with Yes, No anc Cancel
     * @param title The title of the dialog
     * @param bodyText The body text of the dialog
     * @return Whatever the user pressed
     */
    public static String YesNoCancel(String title, String bodyText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(bodyText);

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

        Optional< ButtonType > result = alert.showAndWait();
            if (result.get() == buttonTypeYes) {
                return "yes";
            } else if (result.get() == buttonTypeNo) {
                return "no";
            } else {
                return "cancel";
            }
    }

    public static void Warning(String title, String content)    {
        Alert alertNo = new Alert(Alert.AlertType.WARNING);
        alertNo.setTitle(title);
        alertNo.setHeaderText(null);
        alertNo.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alertNo.setContentText(content);

        alertNo.showAndWait();
    }


}
