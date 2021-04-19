package FrontEnd;

import BackEnd.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * This is the class responsible for managing the main view of the Level Editor as well as all the class interactions needed
 * to make it work
 *
 * @author Pat Sambor
 * @author Felix Ifrim
 */
public class LEMainController extends StateLoad {
    private boolean boolChanges = false;
    private HashMap<String, String> initData;
    private ArrayList<Coordinate> upgrades;
    int tempNo;
    //Blank ImageView to use when De-Selecting Items
    private final ImageView blankImage = new ImageView();
    //Boolean Variables for the action tiles
    private boolean boolFire = true, boolIce = true, boolDouble = true, boolBack = true;
    //You know, I'm something of a MediaPlayer myself
    MediaPlayer pizzaMusic, pizzaTime;
    Boolean boolPizza = false;
    private int intPlayerSel = 0;

    //Used for loading only as an intermediary
    private Slot[][] arrayBoard = new Slot[9][9];

    //Strings that store the names of the colour files for each of the cars
    String player1Color, player2Color, player3Color, player4Color;

    //Boolean variables for each edit button
    private boolean color1edit = false, color2edit = false, color3edit = false, color4edit = false;

    private String newColor1, newColor2, newColor3, newColor4;

    //Array list of menu items with history
    ArrayList<MenuItem> arrHistory = new ArrayList<>();
    //Making sure the listener doesn't do bad things during loading
    Boolean boolNowLoading = false;
    //Storing the references for the load here for access
    String enumdColor1;
    String enumdColor2;
    String enumdColor3;
    String enumdColor4;


    //
    /*
    //ArrayList of ImageViews for history purposes
    ArrayList<ImageView> arrImg = new ArrayList<>();
    //Array List of Integers so that rotation is properly set
    ArrayList<Integer> arrRotations = new ArrayList<>();
    //Array List of Strings to get the names back
    ArrayList<String> arrNames = new ArrayList<>();*/

    @FXML
    Button btnLeft = new Button(), btnRight = new Button();
    @FXML
    Button btnJukebox = new Button();
    //Creating these to bind the canvas to the size of the screen
    @FXML
    ResizableCanvas mainCanvas;
    @FXML
    ScrollPane paneCanv;
    @FXML
    ImageView imgPlayer1 = new ImageView(), imgPlayer2 = new ImageView(), imgPlayer3 = new ImageView(), imgPlayer4 = new ImageView(), imgCorner = new ImageView(), imgStraight = new ImageView(), imgT = new ImageView(), imgEmpty = new ImageView(), imgGoal = new ImageView(), imgUpgrade = new ImageView(), imgDouble = new ImageView(), imgFire = new ImageView(), imgIce = new ImageView(), imgBacktrack = new ImageView(), imgPlayerSel1 = new ImageView(), imgPlayerSel2 = new ImageView(), imgPlayerSel3 = new ImageView(), imgPlayerSel4 = new ImageView();
    @FXML
    Slider sliWidth = new Slider(), sliHeight = new Slider();
    @FXML
    Label lblWidth = new Label(), lblHeight = new Label(), lblStatus = new Label(), lblRotate = new Label();
    @FXML
    Menu menHistory = new Menu(), menHelp = new Menu(), menFile = new Menu(), menName = new Menu();
    @FXML
    MenuItem menAuthor = new MenuItem();
    @FXML
    Button btnEnableAction, btnDisableAction;
    @FXML
    MenuItem LEHelp;


    //Temp ImageView for Selected Floor Tiles
    String strName;
    String tempSel = "blank";
    ImageView imgVTemp = new ImageView();
    int intRotate = 0;

    //Fields from previous Window
    String strAuthor;
    int width;
    int height;
    boolean loadAgain = false;
    private final String MAIN_MENU_SFX = "Assets\\SFX\\mainmenu.mp3";
    private final AudioClip MAIN_MENU_AUDIO = new AudioClip(new File(MAIN_MENU_SFX).toURI().toString());
    private final String RETURN_SFX = "Assets\\SFX\\return.mp3";
    private final AudioClip RETURN_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());
    private final String ZELDA_SFX = "Assets\\SFX\\zelda.mp3";
    private final AudioClip ZELDA_AUDIO = new AudioClip(new File(ZELDA_SFX).toURI().toString());
    //I think what's happening here is that the initialise method is being called twice by wl
    //So I'm setting this variable because the first time the method is called the setInitData method hasn't run yet
    //The initialize method is the easiest place to do this so \_()_/
    boolean boolLoaded = false;
    WindowLoader wl;

    //LE Canvas stuff
    LECanvas boardRender;
    PlaceCoords p1Coords;
    PlaceCoords p2Coords;
    PlaceCoords p3Coords;
    PlaceCoords p4Coords;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData = getInitData();

        mainCanvas.widthProperty().bind(
                paneCanv.widthProperty());
        mainCanvas.heightProperty().bind(
                paneCanv.heightProperty());


        //Weird reloading stuff
        if (boolLoaded) {
            if (initData.get("loaded").equals("true")) {
                setLEValuesLoad();

            } else {
                setLEValues();
            }

        }
        boolLoaded = true;

        //Initialisation stuff for the height slider
        sliHeight.setMin(3);
        sliHeight.setMax(9);
        sliHeight.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        String intConv = String.valueOf(newValue.intValue());
                        lblHeight.setText(intConv);
                        height = newValue.intValue();
                        lblStatus.setText("Height Changed to: " + intConv);
                        boolChanges = true;
                        if (!boolNowLoading) {
                            enumdColor1 = StringTrimmer.trim(player1Color, "player");
                            enumdColor2 = StringTrimmer.trim(player2Color, "player");
                            enumdColor3 = StringTrimmer.trim(player3Color, "player");
                            enumdColor4 = StringTrimmer.trim(player4Color, "player");

                            boardRender.resizeBoard(width, height, CarColours.valueOf(enumdColor1.toUpperCase()), CarColours.valueOf(enumdColor2.toUpperCase()), CarColours.valueOf(enumdColor3.toUpperCase()), CarColours.valueOf(enumdColor4.toUpperCase()) );
                        }

                    }
                });

        //Initialisation stuff for the width slider
        sliWidth.setMin(3);
        sliWidth.setMax(9);
        sliWidth.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number>
                                        observable, Number oldValue, Number newValue) {
                String intConv = String.valueOf(newValue.intValue());
                lblWidth.setText(intConv);
                width = newValue.intValue();
                lblStatus.setText("Width Changed to: " + intConv);
                boolChanges = true;
                if (!boolNowLoading) {
                    enumdColor1 = StringTrimmer.trim(player1Color, "player");
                    enumdColor2 = StringTrimmer.trim(player2Color, "player");
                    enumdColor3 = StringTrimmer.trim(player3Color, "player");
                    enumdColor4 = StringTrimmer.trim(player4Color, "player");

                    boardRender.resizeBoard(width, height, CarColours.valueOf(enumdColor1.toUpperCase()), CarColours.valueOf(enumdColor2.toUpperCase()), CarColours.valueOf(enumdColor3.toUpperCase()), CarColours.valueOf(enumdColor4.toUpperCase()));
                }
            }
        });
        if (boolPizza) {
            lblStatus.setText("Pizza is Selected");
        } else {
        }
        lblRotate.setDisable(true);
        btnRight.setDisable(true);
        btnLeft.setDisable(true);


    }

    /**
     * This class sets all the values that the LE Uses that are transferred over from the create new screen
     * Uses the InitData the previous group
     */
    private void setLEValues() {
        initData = getInitData();
        //Width Init
        width = Integer.parseInt(getInitData().get("width"));
        lblWidth.setText(initData.get("width"));
        sliWidth.setValue(width);

        //Height Init
        height = Integer.parseInt(getInitData().get("height"));
        lblHeight.setText(initData.get("height"));
        sliHeight.setValue(height);


        strAuthor = initData.get("author");
        //Setting the author as a menu item
        menAuthor.setText("Author: " + strAuthor);
        strName = initData.get("name");
        menName.setText("Currently Editing: " + strName);

        //Setting the red-highlighted backgrounds to be invisible on first start
        imgPlayerSel1.setVisible(false);
        imgPlayerSel2.setVisible(false);
        imgPlayerSel3.setVisible(false);
        imgPlayerSel4.setVisible(false);

        //Setting the String names of the player cars to what the defaults were before we added colour changing
        player1Color = "playerPINK";
        player2Color = "playerYELLOW";
        player3Color = "playerTURQUOISE";
        player4Color = "playerORANGE";

        upgrades = new ArrayList<>();
        boardRender = new LECanvas(mainCanvas, lblStatus);


        lblStatus.setText("Main View Loading...");
        //How'd that get in there?
        if (initData.get("pizza").equals("true")) {
            /*Brief rundown of what's actually happening here
            Triggered by holding backspace when creating a new level
            I tried to use the LECanvas class here to draw the secret image but I don't think that works, that's why onMouseCanvas also has a way to do this
            Everything else is just setting the text as well as setting the global boolean to know that the secret is active for later use
             */
            strName = "pizza";
            Main.mediaPlayer.stop();
            Image pizza = new Image("pizza.png");
            GraphicsContext gc = mainCanvas.getGraphicsContext2D();
            gc.drawImage(pizza, 50, 50);
            Media pizzaSound = new Media(new File("Assets\\Music\\pizzaTime.mp3").toURI().toString());
            Media sound = new Media(new File("Assets\\Music\\pizza.mp3").toURI().toString());
            pizzaTime = new MediaPlayer(pizzaSound);
            pizzaMusic = new MediaPlayer(sound);
            pizzaTime.play();
            pizzaMusic.play();
            //Some issues with the Jukebox interacting with the secret so I just disabled it
            btnJukebox.setDisable(true);
            menFile.setText("Pizza Time");
            menHelp.setText("");
            menHistory.setText("");
            menName.setText("");
            boolPizza = true;
            imgPlayer1.setImage(new Image("pizzaMan.png"));
            imgPlayer2.setImage(new Image("pizzaMan.png"));
            imgPlayer3.setImage(new Image("pizzaMan.png"));
            imgPlayer4.setImage(new Image("pizzaMan.png"));

            imgCorner.setImage(new Image("squarePizza.png"));
            imgStraight.setImage(new Image("squarePizza.png"));
            imgT.setImage(new Image("squarePizza.png"));
            imgEmpty.setImage(new Image("squarePizza.png"));
            imgGoal.setImage(new Image("squarePizza.png"));

            btnEnableAction.setText("Enable Pizza");
            btnDisableAction.setText("Disable Pizza");

            imgFire.setImage(new Image("firePizza.png"));
            imgIce.setImage(new Image("frozenPizza.jpg"));
            imgDouble.setImage(new Image("doublePizza.jpg"));
            imgBacktrack.setImage(new Image("backPizza.jpg"));

        }
    }

    /**
     * This class sets all the values fpr the level editor if a game is being loaded in
     */
    public void setLEValuesLoad() {
        //Making sure the sliders don't try to redraw the board
        boolNowLoading = true;

        boardRender = new LECanvas(mainCanvas, lblStatus);
        initData = getInitData();
        //Width Init
        width = Integer.parseInt(initData.get("width"));
        lblWidth.setText(initData.get("width"));
        sliWidth.setValue(width);
        //Height init
        height = Integer.parseInt(initData.get("height"));
        lblHeight.setText(initData.get("height"));

        sliHeight.setValue(height);

        //Name & Author Init
        strAuthor = initData.get("author");
        menAuthor.setText("Author: " + strAuthor);
        strName = initData.get("name");
        strName = StringTrimmer.trim(strName, ".txt");
        menName.setText("Currently Editing: " + strName);
        //String color init
        player1Color = initData.get("player1col");
        player2Color = initData.get("player2col");
        player3Color = initData.get("player3col");
        player4Color = initData.get("player4col");
        //Image View Init
        imgPlayer1.setImage(Assets.getUnSelected(player1Color));
        imgPlayer2.setImage(Assets.getUnSelected(player2Color));
        imgPlayer3.setImage(Assets.getUnSelected(player3Color));
        imgPlayer4.setImage(Assets.getUnSelected(player4Color));
        //Action Tile Init
        if (initData.get("boolIce").equals("true")) {
            boolIce = true;
            imgIce.setImage(Assets.getUnSelected("frozenEffect"));
        } else {
            boolIce = false;
            imgIce.setImage(Assets.getSelected("frozenEffect"));
        }

        if (initData.get("boolFire").equals("true")) {
            boolFire = true;
            imgFire.setImage(Assets.getUnSelected("fire"));
        } else {
            boolIce = false;
            imgFire.setImage(Assets.getSelected("fire"));
        }

        if (initData.get("boolDouble").equals("true")) {
            boolIce = true;
            imgDouble.setImage(Assets.getUnSelected("double_move"));
        } else {
            boolIce = true;
            imgDouble.setImage(Assets.getSelected("double_move"));
        }

        if (initData.get("boolBack").equals("true")) {
            boolIce = true;
            imgBacktrack.setImage(Assets.getUnSelected("backtrack"));
        } else {
            boolIce = true;
            imgBacktrack.setImage(Assets.getSelected("backtrack"));
        }

        //Trimming the string to remove the player bit (used for selections) to make the enum conversion work
        enumdColor1 = StringTrimmer.trim(player1Color, "player");
        enumdColor2 = StringTrimmer.trim(player2Color, "player");
        enumdColor3 = StringTrimmer.trim(player3Color, "player");
        enumdColor4 = StringTrimmer.trim(player4Color, "player");

        lblStatus.setText("Main View Loading...");
        boolNowLoading = false;
    }

    /**
     * This method and the ones that follow are the selection methods for all of the items you can insert into a board
     */
    public void onCorner() {
        setTemp(imgCorner, "corner", "Corner Tile Selected");
        intPlayerSel = 0;
    }

    public void onStraight() {
        setTemp(imgStraight, "straight", "Straight Tile Selected");
        intPlayerSel = 0;
    }

    public void onT() {
        setTemp(imgT, "t_shape", "T-Shaped Tile Selected");
        intPlayerSel = 0;
    }

    public void onEmpty() {
        setTemp(imgEmpty, "empty", "Empty Tile Selected");
        intPlayerSel = 0;
    }

    public void onGoal() {
        setTemp(imgGoal, "goal", "Goal Tile Selected");
        intPlayerSel = 0;
    }

    public void onUp() {
        setTemp(imgUpgrade, "upgrade", "Upgrade Token Selected");
        intPlayerSel = 0;
    }

    public void onPlayer1() {
        setTemp(imgPlayer1, player1Color, "Player 1 Selected");
        intPlayerSel = 1;
    }

    public void onPlayer2() {
        setTemp(imgPlayer2, player2Color, "Player 2 Selected");
        intPlayerSel = 2;
    }

    public void onPlayer3() {
        setTemp(imgPlayer3, player3Color, "Player 3 Selected");
        intPlayerSel = 3;
    }

    public void onPlayer4() {
        setTemp(imgPlayer4, player4Color, "Player 4 Selected");
        intPlayerSel = 4;
    }

    /**
     * This class and the ones that follow enable and disable the action tiles and set all appropriate values
     */
    public void onDoubleMove() {
        boolDouble = !boolDouble;
        setActionTemp(imgDouble, "double_move", boolDouble);
        if (boolDouble) {
            MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
            lblStatus.setText("Double Move Enabled");
        } else {
            lblStatus.setText("Double Move Disabled");
            RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        }
        intPlayerSel = 0;
    }

    public void onFire() {
        boolFire = !boolFire;
        setActionTemp(imgFire, "fire", boolFire);
        if (boolFire) {
            MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
            lblStatus.setText("Fire Enabled");
        } else {
            lblStatus.setText("Fire Disabled");
            RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        }
        intPlayerSel = 0;
    }

    public void onIce() {
        boolIce = !boolIce;
        setActionTemp(imgIce, "frozenEffect", boolIce);
        if (boolIce) {
            MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
            lblStatus.setText("Ice Enabled");
        } else {
            lblStatus.setText("Ice Disabled");
            RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        }
        intPlayerSel = 0;
    }

    public void onBackTrack() {
        boolBack = !boolBack;
        setActionTemp(imgBacktrack, "backtrack", boolBack);
        if (boolBack) {
            MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
            lblStatus.setText("Backtrack Enabled");
        } else {
            lblStatus.setText("Backtrack Disabled");
            RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        }
        intPlayerSel = 0;
    }

    /**
     * This class and onDisableAction enable all of the action tiles to be used in the silkbag at once
     */
    public void onEnableAction() {
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        boolIce = true;
        boolFire = true;
        boolDouble = true;
        boolBack = true;

        setActionTemp(imgIce, "frozenEffect", boolIce);
        setActionTemp(imgFire, "fire", boolFire);
        setActionTemp(imgDouble, "double_move", boolDouble);
        setActionTemp(imgBacktrack, "backtrack", boolBack);

        lblStatus.setText("All Action Tiles Enabled");
        setHistory();
        intPlayerSel = 0;
    }

    public void onDisableAction() {
        RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        boolIce = false;
        boolFire = false;
        boolDouble = false;
        boolBack = false;

        setActionTemp(imgIce, "frozenEffect", boolIce);
        setActionTemp(imgFire, "fire", boolFire);
        setActionTemp(imgDouble, "double_move", boolDouble);
        setActionTemp(imgBacktrack, "backtrack", boolBack);

        lblStatus.setText("All Action Tiles Disabled");
        setHistory();
        intPlayerSel = 0;
    }

    /**
     * This class and the other onColor classes show a new window to be able to select the color of the player
     *
     * @throws IOException
     */
    public void onColor1() throws IOException {
        color1edit = true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML\\ChangeCarColor.fxml"));
        LEChangeColorController controller2 = new LEChangeColorController(this);
        loader.setController(controller2);
        Scene scene = new Scene(loader.load());
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Change Player 1's Colour!");
        primaryStage.show();
    }

    public void onColor2() throws IOException {
        color2edit = true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML\\ChangeCarColor.fxml"));
        LEChangeColorController controller2 = new LEChangeColorController(this);
        loader.setController(controller2);
        Scene scene = new Scene(loader.load());
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Change Player 2's Colour!");
        primaryStage.show();
    }

    public void onColor3() throws IOException {
        color3edit = true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML\\ChangeCarColor.fxml"));
        LEChangeColorController controller2 = new LEChangeColorController(this);
        loader.setController(controller2);
        Scene scene = new Scene(loader.load());
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Change Player 3's Colour!");
        primaryStage.show();
    }

    public void onColor4() throws IOException {
        color4edit = true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML\\ChangeCarColor.fxml"));
        LEChangeColorController controller2 = new LEChangeColorController(this);
        loader.setController(controller2);
        Scene scene = new Scene(loader.load());
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Change Player 4's Colour!");
        primaryStage.show();
    }

    /**
     * This class was meant to take keyboard input for the tiles and call the rotate method but it doesn't work
     */
    public void onKeyboard() {
        new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        onLeft();
                        break;
                    case RIGHT:
                        onRight();
                        break;
                }
            }
        };
    }

    double mouseX;
    double mouseY;

    /**
     * This class checks for mouse movements on the canvas and finds the mouse coordinate for further use
     */
    public void onMouseCanvas() {

        paneCanv.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseX = event.getX();
                mouseY = event.getY();
                //System.out.println(mouseX + " " + mouseY);


            }
        });
        if (boolPizza) {
            boardRender.CanvasDrawPizza();
        }
    }

    //Boolean to check weather the mouse has entered once so the initial set of tiles isn't being constantly redrawn every time the mouse enters
    boolean boolEnteredOnce = false;

    /**
     * Known as onCheapHack because I really don't like this solution
     * Assigns itself to onMouseEntered in the FXML
     */
    public void onCanvasEntered() {
        initData = getInitData();
        if (!boolEnteredOnce) {

            if (initData.get("loaded").equals("true")) {
                boardRender.loadBoard(arrayBoard, width, height, p1Coords, p2Coords, p3Coords, p4Coords,
                        CarColours.valueOf(enumdColor1.toUpperCase()), CarColours.valueOf(enumdColor2.toUpperCase()),
                        CarColours.valueOf(enumdColor3.toUpperCase()), CarColours.valueOf(enumdColor4.toUpperCase()), upgrades);
                boolEnteredOnce = true;
                lblStatus.setText("Nothing is Selected");
            } else {
                boardRender.setEmptyBoard(sliHeight, sliWidth);
                boolEnteredOnce = true;
                lblStatus.setText("Nothing is Selected");
            }


        }
        //I have nothing left... Except Spider-Man.
        if (boolPizza) {
            boardRender.CanvasDrawPizza();
        }

    }

    /**
     * This class handles the logic for when you click inside of the Canvas to draw what you have selected
     */
    public void onCanvasClick() {
        boolChanges = true;
        boolean tileSelected;

        tileSelected = !tempSel.equals("blank");


        /*
        if (boolPizza)  {
            boardRender.CanvasDrawPizza();
        }else   {
            boardRender.CanvasDraw();
        }

         */

        //make a mouse listener and get the mouse location from mouse event.
        boardRender.placeOnSlot(mouseX, mouseY, tempSel, sliWidth, sliHeight, tileSelected, intPlayerSel, intRotate);

    }

    //keep the drawing on it(could also replace the cursor image)


    /**
     * This class and onRight are used to rotate the selected image
     */
    public void onLeft() {
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        intRotate = intRotate - 90;
        //Validation for goal and empty tile
        if (tempSel.equals("goal") || tempSel.equals("empty") || tempSel.equals("upgrade")) {
            lblStatus.setText("These tiles don't rotate");
            intRotate = 0;
            return;
        } else if (intRotate < 0) {
            intRotate = 270;
        }
        String strRotate = String.valueOf(intRotate);
        if (intPlayerSel == 0) {
            lblStatus.setText("Rotated " + tempSel + " to " + strRotate);
        } else {
            lblStatus.setText("Rotated Player " + intPlayerSel + " to " + strRotate);
        }

        imgVTemp.setRotate(intRotate);
        setHistory();
    }

    public void onRight() {
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        intRotate = intRotate + 90;
        if (tempSel.equals("goal") || tempSel.equals("empty") || tempSel.equals("upgrade")) {
            lblStatus.setText("These tiles don't rotate");
            intRotate = 0;
            return;
        } else if (intRotate > 270) {
            intRotate = 0;
        }
        String strRotate = String.valueOf(intRotate);
        if (intPlayerSel == 0) {
            lblStatus.setText("Rotated " + tempSel + " to " + strRotate);
        } else {
            lblStatus.setText("Rotated Player " + intPlayerSel + " to " + strRotate);
        }
        imgVTemp.setRotate(intRotate);
        setHistory();
    }

    /**
     * Calls the save function (after setting all the initdata values)
     */
    public void onSaveButton() {
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        initData.put("name", strName);
        initData.put("width", String.valueOf(width));
        initData.put("height", String.valueOf(width));
        initData.put("player1col", player1Color);
        initData.put("player2col", player2Color);
        initData.put("player3col", player3Color);
        initData.put("player4col", player4Color);
        initData.put("boolDouble", String.valueOf(boolDouble));
        initData.put("boolIce", String.valueOf(boolIce));
        initData.put("boolFire", String.valueOf(boolFire));
        initData.put("boolBack", String.valueOf(boolBack));
        LESave objSave = new LESave(boardRender, getInitData());
        objSave.Save();
        boolChanges = false;
    }

    /**
     * Quits the program back to the main menu (after checking if the user wishes to save)
     */
    public void onQuitButton() {
        RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        if (boolChanges) {
            String response;
            response = CustomAlerts.YesNoCancel("Unsaved Changes", "You have unsaved changes, would you like to save?");
            switch (response) {
                case "yes":
                    onSaveButton();
                    wl = new WindowLoader(btnJukebox);
                    wl.load("MenuScreen", getInitData());
                    break;
                case "no":
                    RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
                    wl = new WindowLoader(btnJukebox);
                    wl.load("MenuScreen", getInitData());
                    break;
                case "cancel":
                    MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
                    break;
                //This should never happen but I'm not a prophet so here it is just in case
                default:
                    ZELDA_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
                    CustomAlerts.Warning("You have discovered a new secret!", "I don't know how you did this but here's a haiku: \n Crusader Kings II \n It is really a good game \n Go ahead and play.");
                    break;
            }
        } else {
            wl = new WindowLoader(btnJukebox);
            wl.load("MenuScreen", getInitData());
        }

    }

    /**
     * Small method to change the cursor to a closed hand when held on the slider
     */
    public void handCursorChange() {
        sliHeight.setCursor(Cursor.CLOSED_HAND);
        sliWidth.setCursor(Cursor.CLOSED_HAND);
    }

    public void handCursorReset() {
        sliHeight.setCursor(Cursor.OPEN_HAND);
        sliWidth.setCursor(Cursor.OPEN_HAND);
    }

    /**
     * This class sets the temporary variables to the image just clicked
     * Custom variant for tiles
     *
     * @param newImage The new ImageView that will be set as the temp
     * @param newName  The name of the file for newImage
     * @param status   What the status bar will say has happened, in a human readable way
     */
    private void setTemp(ImageView newImage, String newName, String status) {
        //This block of code is for setting UI Elements

        imgVTemp.setImage(Assets.getUnSelected(tempSel));
        imgVTemp.setRotate(0);
        //Disabling Rotation for Goal and Empty Tile
        if (newName.equals("goal") || newName.equals("empty") || newName.equals("upgrade")) {
            lblRotate.setDisable(true);
            btnRight.setDisable(true);
            btnLeft.setDisable(true);
        } else {
            lblRotate.setDisable(false);
            btnLeft.setDisable(false);
            btnRight.setDisable(false);
        }

        //
        if (newName.equals(tempSel)) {
            imgVTemp = blankImage;
            tempSel = "blank";
            lblStatus.setText("Nothing is Selected");
            intPlayerSel = 0;
            return;
        }
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        newImage.setImage(Assets.getSelected(newName));
        tempSel = newName;
        imgVTemp = newImage;
        intRotate = 0;
        lblStatus.setText(status);
        setHistory();
    }


    /**
     * This class sets temporary variables if an ActionTile is clicked
     *
     * @param newImage    The ImageView that the user wishes to change
     * @param newName     The new name that the
     * @param boolEnabled Whether or not the action tile is enabled already or not
     */
    private void setActionTemp(ImageView newImage, String newName, Boolean boolEnabled) {
        //Resetting previously selected floor tile
        imgVTemp.setImage(Assets.getUnSelected(tempSel));
        imgVTemp.setRotate(0);
        lblRotate.setDisable(true);
        btnRight.setDisable(true);
        btnLeft.setDisable(true);
        tempSel = "blank";
        imgVTemp = blankImage;
        //Fades the image out if it's full and does the opposite if not
        if (!boolEnabled) {
            newImage.setImage(Assets.getSelected(newName));
        } else {
            newImage.setImage(Assets.getUnSelected(newName));
        }
        setHistory();
    }

    /**
     * Simple method here to show a history of actions taken, no undo functionality at the moment
     * Will just use the wording that the status did
     */
    private void setHistory() {
        MenuItem tempMenu = new MenuItem();
        tempMenu.setText(lblStatus.getText());
        arrHistory.add(tempMenu);
        menHistory.getItems().add(arrHistory.get(arrHistory.size() - 1));
    }

    // for some reason does'nt work.
    public void onJukebox() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML\\JukeBox.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("The Getaway Jukebox!");
        primaryStage.show();
    }

    /**
     * This method opens up a new window to show the level editor guide menu.
     *
     * @throws IOException
     */
    public void onLEHelp() throws IOException {
        /*
        wl = new WindowLoader(btnLeft);
        wl.load("/LEHowToUse/LEHowToPlay", getInitData());
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
         */

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML\\LEHowToUse\\LEHowToPlay.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("How to use the level editor");
        primaryStage.show();
    }

    /**
     * Update the color of Player 1.
     *
     * @param newColorName the new color name.
     */
    public void updateColor1(String newColorName) {
        switch (newColorName) {
            case "playerYellow":
                imgPlayer1.setImage(new Image("playerYELLOW.png"));
                player1Color = "playerYELLOW";
                break;
            case "playerTURQUOISE":
                imgPlayer1.setImage(new Image("playerTURQUOISE.png"));
                player1Color = "playerTURQUOISE";
                break;
            case "playerPINK":
                imgPlayer1.setImage(new Image("playerPINK.png"));
                player1Color = "playerPINK";
                break;
            case "playerORANGE":
                imgPlayer1.setImage(new Image("playerORANGE.png"));
                player1Color = "playerORANGE";
                break;
            case "playerAQUAMARINE":
                imgPlayer1.setImage(new Image("playerAQUAMARINE.png"));
                player1Color = "playerAQUAMARINE";
                break;
            case "playerGREY":
                imgPlayer1.setImage(new Image("playerGREY.png"));
                player1Color = "playerGREY";
                break;
            case "playerBURGUNDY":
                imgPlayer1.setImage(new Image("playerBURGUNDY.png"));
                player1Color = "playerBURGUNDY";
                break;
            case "playerGLAUCOUS":
                imgPlayer1.setImage(new Image("playerGLAUCOUS.png"));
                player1Color = "playerGLAUCOUS";
                break;
            case "playerWHITE":
                imgPlayer1.setImage(new Image("playerWHITE.png"));
                player1Color = "playerWHITE";
                break;
            case "playerBLACK":
                imgPlayer1.setImage(new Image("playerBLACK.png"));
                player1Color = "playerBLACK";
                break;
            default:
                break;
        }
        lblStatus.setText("P1 changed to " + player1Color.substring(6));
        setHistory();
        color1edit = false;
    }

    /**
     * Update the color of Player 2.
     *
     * @param newColorName the new color name.
     */
    public void updateColor2(String newColorName) {
        switch (newColorName) {
            case "playerYELLOW":
                imgPlayer2.setImage(new Image("playerYELLOW.png"));
                player2Color = "playerYELLOW";
                break;
            case "playerTURQUOISE":
                imgPlayer2.setImage(new Image("playerTURQUOISE.png"));
                player2Color = "playerTURQUOISE";
                break;
            case "playerPINK":
                imgPlayer2.setImage(new Image("playerPINK.png"));
                player2Color = "playerPINK";
                break;
            case "playerORANGE":
                imgPlayer2.setImage(new Image("playerORANGE.png"));
                player2Color = "playerORANGE";
                break;
            case "playerAQUAMARINE":
                imgPlayer2.setImage(new Image("playerAQUAMARINE.png"));
                player2Color = "playerAQUAMARINE";
                break;
            case "playerGREY":
                imgPlayer2.setImage(new Image("playerGREY.png"));
                player2Color = "playerGREY";
                break;
            case "playerBURGUNDY":
                imgPlayer2.setImage(new Image("playerBURGUNDY.png"));
                player2Color = "playerBURGUNDY";
                break;
            case "playerGLAUCOUS":
                imgPlayer2.setImage(new Image("playerGLAUCOUS.png"));
                player2Color = "playerGLAUCOUS";
                break;
            case "playerWHITE":
                imgPlayer2.setImage(new Image("playerWHITE.png"));
                player2Color = "playerWHITE";
                break;
            case "playerBLACK":
                imgPlayer2.setImage(new Image("playerBLACK.png"));
                player2Color = "playerBLACK";
                break;
            default:
                break;
        }
        lblStatus.setText("P2 changed to " + player2Color.substring(6));
        setHistory();
        color2edit = false;
    }

    /**
     * Update the color of Player 3.
     *
     * @param newColorName the new color name.
     */
    public void updateColor3(String newColorName) {
        switch (newColorName) {
            case "playerYellow":
                imgPlayer3.setImage(new Image("playerYELLOW.png"));
                player3Color = "playerYELLOW";
                break;
            case "playerTURQUOISE":
                imgPlayer3.setImage(new Image("playerTURQUOISE.png"));
                player3Color = "playerTURQUOISE";
                break;
            case "playerPINK":
                imgPlayer3.setImage(new Image("playerPINK.png"));
                player3Color = "playerPINK";
                break;
            case "playerORANGE":
                imgPlayer3.setImage(new Image("playerORANGE.png"));
                player3Color = "playerORANGE";
                break;
            case "playerAQUAMARINE":
                imgPlayer3.setImage(new Image("playerAQUAMARINE.png"));
                player3Color = "playerAQUAMARINE";
                break;
            case "playerGREY":
                imgPlayer3.setImage(new Image("playerGREY.png"));
                player3Color = "playerGREY";
                break;
            case "playerBURGUNDY":
                imgPlayer3.setImage(new Image("playerBURGUNDY.png"));
                player3Color = "playerBURGUNDY";
                break;
            case "playerGLAUCOUS":
                imgPlayer3.setImage(new Image("playerGLAUCOUS.png"));
                player3Color = "playerGLAUCOUS";
                break;
            case "playerWHITE":
                imgPlayer3.setImage(new Image("playerWHITE.png"));
                player3Color = "playerWHITE";
                break;
            case "playerBLACK":
                imgPlayer3.setImage(new Image("playerBLACK.png"));
                player3Color = "playerBLACK";
                break;
            default:
                break;
        }
        lblStatus.setText("P3 changed to " + player3Color.substring(6));
        setHistory();
        color3edit = false;
    }

    /**
     * Update the color of Player 4.
     *
     * @param newColorName the new color name.
     */
    public void updateColor4(String newColorName) {
        switch (newColorName) {
            case "playerYELLOW":
                imgPlayer4.setImage(new Image("playerYELLOW.png"));
                player4Color = "playerYELLOW";
                break;
            case "playerTURQUOISE":
                imgPlayer4.setImage(new Image("playerTURQUOISE.png"));
                player4Color = "playerTURQUOISE";
                break;
            case "playerPINK":
                imgPlayer4.setImage(new Image("playerPINK.png"));
                player4Color = "playerPINK";
                break;
            case "playerORANGE":
                imgPlayer4.setImage(new Image("playerORANGE.png"));
                player4Color = "playerORANGE";
                break;
            case "playerAQUAMARINE":
                imgPlayer4.setImage(new Image("playerAQUAMARINE.png"));
                player4Color = "playerAQUAMARINE";
                break;
            case "playerGREY":
                imgPlayer4.setImage(new Image("playerGREY.png"));
                player4Color = "playerGREY";
                break;
            case "playerBURGUNDY":
                imgPlayer4.setImage(new Image("playerBURGUNDY.png"));
                imgPlayer4.setImage(new Image("playerBURGUNDY.png"));
                player4Color = "playerBURGUNDY";
                break;
            case "playerGLAUCOUS":
                imgPlayer4.setImage(new Image("playerGLAUCOUS.png"));
                player4Color = "playerGLAUCOUS";
                break;
            case "playerWHITE":
                imgPlayer4.setImage(new Image("playerWHITE.png"));
                player4Color = "playerWHITE";
                break;
            case "playerBLACK":
                imgPlayer4.setImage(new Image("playerBLACK.png"));
                player4Color = "playerBLACK";
                break;
            default:
                break;
        }
        lblStatus.setText("P4 changed to " + player4Color.substring(6));
        setHistory();
        color4edit = false;
    }

    /**
     * Checks if the Player 1 color is currently edited or not.
     *
     * @return The status of player 1 color.
     */
    public boolean getColor1EditStatus() {
        return color1edit;
    }

    /**
     * Set a new value for Player 1 color.
     *
     * @param newEditValue The new color.
     */
    public void setColor1EditStatus(boolean newEditValue) {
        color1edit = newEditValue;
    }

    /**
     * Checks if the Player 2 color is currently edited or not.
     *
     * @return The status of player 2 color.
     */
    public boolean getColor2EditStatus() {
        return color2edit;
    }

    /**
     * Set a new value for Player 2 color.
     *
     * @param newEditValue The new color.
     */
    public void setColor2EditStatus(boolean newEditValue) {
        color2edit = newEditValue;
    }

    /**
     * Checks if the Player 3 color is currently edited or not.
     *
     * @return The status of player 3 color.
     */
    public boolean getColor3EditStatus() {
        return color3edit;
    }

    /**
     * Set a new value for Player 3 color.
     *
     * @param newEditValue The new color.
     */
    public void setColor3EditStatus(boolean newEditValue) {
        color3edit = newEditValue;
    }

    /**
     * Checks if the Player 4 color is currently edited or not.
     *
     * @return The status of player 4 color.
     */
    public boolean getColor4EditStatus() {
        return color4edit;
    }

    /**
     * Set a new value for Player 4 color.
     *
     * @param newEditValue The new color.
     */
    public void setColor4EditStatus(boolean newEditValue) {
        color4edit = newEditValue;
    }

    /**
     * Get the ImageView of Player 1 car.
     *
     * @return ImageView of Player 1 car.
     */
    public ImageView getCar1ImageView() {
        return imgPlayer1;
    }

    /**
     * Set the new color of Player 1 car.
     *
     * @param newColor The new color of Player 1 car.
     */
    public void setCar1ImageView(String newColor) {
        updateColor1(newColor);
    }

    /**
     * Get the ImageView of Player 2 car.
     *
     * @return ImageView of Player 2 car.
     */
    public ImageView getCar2ImageView() {
        return imgPlayer2;
    }

    /**
     * Set the new color of Player 2 car.
     *
     * @param newColor The new color of Player 2 car.
     */
    public void setCar2ImageView(String newColor) {
        updateColor2(newColor);
    }

    /**
     * Get the ImageView of Player 3 car.
     *
     * @return ImageView of Player 3 car.
     */
    public ImageView getCar3ImageView() {
        return imgPlayer3;
    }

    /**
     * Set the new color of Player 3 car.
     *
     * @param newColor The new color of Player 3 car.
     */
    public void setCar3ImageView(String newColor) {
        updateColor3(newColor);
    }

    /**
     * Get the ImageView of Player 4 car.
     *
     * @return ImageView of Player 4 car.
     */
    public ImageView getCar4ImageView() {
        return imgPlayer4;
    }

    /**
     * Set the new color of Player 4 car.
     *
     * @param newColor The new color of Player 4 car.
     */
    public void setCar4ImageView(String newColor) {
        updateColor4(newColor);
    }

    /**
     * End of section
     */

    public void setArrayBoard(Slot[][] arrayLoadedBoard) {
        arrayBoard = arrayLoadedBoard;
    }

    public void setPlayerCoords(String[] xCoords, String[] yCoords) {
        p1Coords = new PlaceCoords(Integer.parseInt(xCoords[0]), Integer.parseInt(yCoords[0]));
        p2Coords = new PlaceCoords(Integer.parseInt(xCoords[1]), Integer.parseInt(yCoords[1]));
        p3Coords = new PlaceCoords(Integer.parseInt(xCoords[2]), Integer.parseInt(yCoords[2]));
        p4Coords = new PlaceCoords(Integer.parseInt(xCoords[3]), Integer.parseInt(yCoords[3]));
    }

    public void setUpgrades(ArrayList<Coordinate> upgrades) {
        this.upgrades = upgrades;
    }
}

