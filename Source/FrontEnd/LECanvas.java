package FrontEnd;

import BackEnd.CarColours;
import BackEnd.PlaceCoords;
import BackEnd.Player;
import BackEnd.TileType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

import java.io.File;
import java.io.IOException;

/**
 * LECanvas class is used to interact with the canvas(also reffered as render) on the Level Editor view.
 * The LECanvas enables to draw the board when required to display the editted level to the user.
 * Methods various methods available such as loading the board into the canvas, resizing the board and etc.
 *
 * @author Adilet Eshimkanov
 * @author Tom Cresswell
 * @author Pat Sambor
 * @version 1.0
 */
public class LECanvas {

    Image imageEmpty;
    Image imageGoal;
    Image imageStraight;
    Image imageTShape;
    Image imageCorner;
    Image imageUpgrade;

    Image imagePlGrey;
    Image imagePlPink;
    Image imagePlBlack;
    Image imagePlWhite;
    Image imagePlOrange;
    Image imagePlYellow;
    Image imagePlBurgundy;
    Image imagePlGlaucous;
    Image imagePlTurquiose;
    Image imagePlAquamarine;

    private final Canvas canvas;

    Slot[][] arrayBoard = new Slot[9][9];
    double tileSize;
    Label lblstatus;

    int topLeftX = 0;
    int topLeftY = 0;
    int topRightX = 0;
    int boardSizePx;
    boolean widthCentre;


    Player player1; // (Tom) only made to help getSlotInfo method, change if needed.
    Player player2;
    Player player3;
    Player player4;

    PlaceCoords player1Coords = new PlaceCoords(0,0);
    PlaceCoords player2Coords = new PlaceCoords(0,0);
    PlaceCoords player3Coords = new PlaceCoords(0,0);
    PlaceCoords player4Coords = new PlaceCoords(0,0);

    CarColours playerColor1;
    CarColours playerColor2;
    CarColours playerColor3;
    CarColours playerColor4;
    TileType tileTemp;
    private File slotInfoFile;

    //variables for tile selected if there to swap with below tile.
    TileType tempTileType;
    int tempTileTypeRotation;
    int tempTileX = 99; // refers to empty/not set
    int tempTileY = 99;

    //variables for the tile swap, below being under the mouse if a tile is selected.
    TileType belowTileType;
    int belowTileTypeRotation;
    int belowTileX;
    int belowTileY;

    //to check if a swap is needed(a tile is taken or not)
    boolean prevTileTaken = false;


    /**
     * Constructor for LECanvas to intialise the canvas and label.
     *
     * @param impCanvas canvas for rendering the board
     * @param lblStatus label for status updates
     */
    public LECanvas(Canvas impCanvas, Label lblStatus) {
        canvas = impCanvas;
        this.lblstatus = lblStatus;

        imageEmpty = new Image("empty.png");
        imageGoal = new Image("goal.png");
        imageStraight = new Image("straight.png");
        imageTShape = new Image("t_shape.png");
        imageCorner = new Image("corner.png");
        imageUpgrade = new Image("upgrade.png");

        imagePlGrey = new Image("playerGREY.png");
        imagePlPink = new Image("playerPINK.png");
        imagePlBlack = new Image("playerBLACK.png");
        imagePlWhite = new Image("playerWHITE.png");
        imagePlOrange = new Image("playerORANGE.png");
        imagePlYellow = new Image("playerYELLOW.png");
        imagePlBurgundy = new Image("playerBURGUNDY.png");
        imagePlGlaucous = new Image("playerGLAUCOUS.png");
        imagePlTurquiose = new Image("playerTURQUOISE.png");
        imagePlAquamarine = new Image("playerAQUAMARINE.png");
    }

    /**
     * Method to load in a board.
     *
     * @param loadArrayBoard saved board to load
     * @param width          width of the board
     * @param height         height of the board
     * @param player1CoordsLoad  Player 1 coordinates.
     * @param player2CoordsLoad  Player 2 coordinates.
     * @param player3CoordsLoad  Player 3 coordinates.
     * @param player4CoordsLoad  Player 4 coordinates.
     * @param playerColor1   Player 1 colour.
     * @param playerColor2   Player 2 colour.
     * @param playerColor3   Player 3 colour.
     * @param playerColor4   Player 4 colour.
     */
    public void loadBoard(Slot[][] loadArrayBoard, int width, int height, PlaceCoords player1CoordsLoad,
                          PlaceCoords player2CoordsLoad, PlaceCoords player3CoordsLoad, PlaceCoords player4CoordsLoad,
                          CarColours playerColor1, CarColours playerColor2, CarColours playerColor3, CarColours playerColor4) {

        //loading board
        arrayBoard = loadArrayBoard;

        if (canvas.getHeight() >= canvas.getWidth()) {
            boardSizePx = (int) canvas.getWidth();
            topLeftY = (int) (canvas.getHeight() - boardSizePx) / 2;
        } else if (canvas.getHeight() <= canvas.getWidth()) {
            boardSizePx = (int) canvas.getHeight();
            topLeftX = (int) (canvas.getWidth() - boardSizePx) / 2;
            topRightX = (int) ((canvas.getWidth() - boardSizePx) / 2 + boardSizePx);
        }

        for (int i = width; i <= 8; i++) {
            for (int j = height; j <= 8; j++) {

                System.out.println("Placed a new slot in arrayBoard " + i + " " + j);
            }
        }

        //intermediary values
        Slider sliWidth = new Slider();
        Slider sliHeight = new Slider();
        sliWidth.setValue(width);
        sliHeight.setValue(height);

        //check what is the max height and width and use the highest.
        if (sliHeight.getValue() == 9 || sliWidth.getValue() == 9) {
            tileSize = boardSizePx / 9;
            System.out.println("9x9 board");
        } else if (sliHeight.getValue() == 8 || sliWidth.getValue() == 8) {
            tileSize = boardSizePx / 8;
        } else if (sliHeight.getValue() == 7 || sliWidth.getValue() == 7) {
            tileSize = boardSizePx / 7;
        } else if (sliHeight.getValue() == 6 || sliWidth.getValue() == 6) {
            tileSize = boardSizePx / 6;
        } else if (sliHeight.getValue() == 5 || sliWidth.getValue() == 5) {
            tileSize = boardSizePx / 5;
        } else if (sliHeight.getValue() == 4 || sliWidth.getValue() == 4) {
            tileSize = boardSizePx / 4;
        } else if (sliHeight.getValue() == 3 || sliWidth.getValue() == 3) {
            tileSize = boardSizePx / 3;
        }


        drawBoard(sliWidth, sliHeight, tileSize);
        //adding players
        arrayBoard[player1CoordsLoad.getLocX()][player1CoordsLoad.getLocY()].setWhichPlayer(1);
        arrayBoard[player1CoordsLoad.getLocX()][player1CoordsLoad.getLocY()].setCarColours(playerColor1);
        drawPlayer(player1CoordsLoad.getLocX()+1, player1CoordsLoad.getLocY()+1, playerColor1, 0);

        arrayBoard[player2CoordsLoad.getLocX()][player2CoordsLoad.getLocY()].setWhichPlayer(2);
        arrayBoard[player2CoordsLoad.getLocX()][player2CoordsLoad.getLocY()].setCarColours(playerColor2);
        drawPlayer(player2CoordsLoad.getLocX()+1, player2CoordsLoad.getLocY()+1, playerColor2, 0);

        arrayBoard[player3CoordsLoad.getLocX()][player3CoordsLoad.getLocY()].setWhichPlayer(3);
        arrayBoard[player3CoordsLoad.getLocX()][player3CoordsLoad.getLocY()].setCarColours(playerColor3);
        drawPlayer(player3CoordsLoad.getLocX()+1, player3CoordsLoad.getLocY()+1, playerColor3, 0);

        arrayBoard[player4CoordsLoad.getLocX()][player4CoordsLoad.getLocY()].setWhichPlayer(4);
        arrayBoard[player4CoordsLoad.getLocX()][player4CoordsLoad.getLocY()].setCarColours(playerColor4);
        drawPlayer(player4CoordsLoad.getLocX()+1, player4CoordsLoad.getLocY()+1, playerColor4, 0);

    }

    /**
     * Method to set the initial empty board for Level Editor.
     *
     * @param sliHeight slider of the height
     * @param sliWidth  slider of the width
     */
    public void setEmptyBoard(Slider sliHeight, Slider sliWidth) {

        //find if the height or the width is bigger to align the render to the centre and
        //determine top left corner of the board for drawing tiles
        if (canvas.getHeight() >= canvas.getWidth()) {
            boardSizePx = (int) canvas.getWidth();
            topLeftY = (int) (canvas.getHeight() - boardSizePx) / 2;
        } else if (canvas.getHeight() <= canvas.getWidth()) {
            boardSizePx = (int) canvas.getHeight();
            topLeftX = (int) (canvas.getWidth() - boardSizePx) / 2;
            topRightX = (int) ((canvas.getWidth() - boardSizePx) / 2 + boardSizePx);
        }

        //check what is the max height and width and use the highest.
        if (sliHeight.getValue() == 9 || sliWidth.getValue() == 9) {
            tileSize = boardSizePx / 9;
            System.out.println("9x9 board");
        } else if (sliHeight.getValue() == 8 || sliWidth.getValue() == 8) {
            tileSize = boardSizePx / 8;
        } else if (sliHeight.getValue() == 7 || sliWidth.getValue() == 7) {
            tileSize = boardSizePx / 7;
        } else if (sliHeight.getValue() == 6 || sliWidth.getValue() == 6) {
            tileSize = boardSizePx / 6;
        } else if (sliHeight.getValue() == 5 || sliWidth.getValue() == 5) {
            tileSize = boardSizePx / 5;
        } else if (sliHeight.getValue() == 4 || sliWidth.getValue() == 4) {
            tileSize = boardSizePx / 4;
        } else if (sliHeight.getValue() == 3 || sliWidth.getValue() == 3) {
            tileSize = boardSizePx / 3;
        }

        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j <= 8; j++) {

                Slot slot = new Slot(0, TileType.EMPTY, i, j, 0);
                arrayBoard[i][j] = slot;

                System.out.println("Placed a new slot in arrayBoard " + i + " " + j);
            }
        }

        drawBoard(sliWidth, sliHeight, tileSize);

        //check the values for arrayboard in regards to 1-9

        arrayBoard[0][0].setWhichPlayer(1);
        arrayBoard[0][0].setCarColours(CarColours.PINK);
        player1Coords.setLocX(1);
        player1Coords.setLocY(1);
        drawPlayer(1, 1, arrayBoard[0][0].getCarColours(), 0);
        System.out.println("Player at " + 0 + " " + 0 + " " + arrayBoard[0][0].getWhichPlayer());

        arrayBoard[(int) sliWidth.getValue() - 1][0].setWhichPlayer(2);
        arrayBoard[(int) sliWidth.getValue() - 1][0].setCarColours(CarColours.YELLOW);
        player3Coords.setLocX((int) sliWidth.getValue());
        player3Coords.setLocY(1);
        drawPlayer((int) sliWidth.getValue(), 1, arrayBoard[(int) sliWidth.getValue() - 1][0].getCarColours(), 0);
        System.out.println("Player at " + (int) sliWidth.getValue() + " " + 0 + " " + arrayBoard[(int) sliWidth.getValue() - 1][0].getWhichPlayer());

        arrayBoard[0][(int) sliHeight.getValue() - 1].setWhichPlayer(3);
        arrayBoard[0][(int) sliHeight.getValue() - 1].setCarColours(CarColours.TURQUOISE);
        player2Coords.setLocX(1);
        player2Coords.setLocY((int) (sliHeight.getValue()));
        drawPlayer(1, (int) sliHeight.getValue(), arrayBoard[0][(int) sliHeight.getValue() - 1].getCarColours(), 0);
        System.out.println("Player at " + 0 + " " + sliHeight.getValue() + " " + arrayBoard[0][(int) sliHeight.getValue() - 1].getWhichPlayer());

        arrayBoard[(int) sliWidth.getValue()-1][(int) sliHeight.getValue()-1].setWhichPlayer(4);
        arrayBoard[(int) sliWidth.getValue()-1][(int) sliHeight.getValue()-1].setCarColours(CarColours.ORANGE);
        player4Coords.setLocX((int) sliWidth.getValue());
        player4Coords.setLocY((int) sliHeight.getValue());
        drawPlayer((int) sliWidth.getValue(), (int) sliHeight.getValue(),
                arrayBoard[(int) sliWidth.getValue()-1][(int) sliHeight.getValue()-1].getCarColours(), 0);
        System.out.println("Player at " + sliWidth.getValue() + " " + sliHeight.getValue() + " " + arrayBoard[(int) sliWidth.getValue() - 1][(int) sliHeight.getValue() - 1].getWhichPlayer());

    }

    /**
     * Method to resize the board, when the sliders are updated.
     *
     * @param width  new value of the slider for width
     * @param height new value of the slider for height
     */
    public void resizeBoard(int width, int height, CarColours enumColor1, CarColours enumColor2, CarColours enumColor3, CarColours enumColor4 ) {

        Slider sliWidth = new Slider();
        Slider sliHeight = new Slider();
        sliWidth.setValue(width);
        sliHeight.setValue(height);

        //check what is the max height and width and use the highest.
        if (sliHeight.getValue() == 9 || sliWidth.getValue() == 9) {
            tileSize = boardSizePx / 9;
            System.out.println("9x9 board");
        } else if (sliHeight.getValue() == 8 || sliWidth.getValue() == 8) {
            tileSize = boardSizePx / 8;
        } else if (sliHeight.getValue() == 7 || sliWidth.getValue() == 7) {
            tileSize = boardSizePx / 7;
        } else if (sliHeight.getValue() == 6 || sliWidth.getValue() == 6) {
            tileSize = boardSizePx / 6;
        } else if (sliHeight.getValue() == 5 || sliWidth.getValue() == 5) {
            tileSize = boardSizePx / 5;
        } else if (sliHeight.getValue() == 4 || sliWidth.getValue() == 4) {
            tileSize = boardSizePx / 4;
        } else if (sliHeight.getValue() == 3 || sliWidth.getValue() == 3) {
            tileSize = boardSizePx / 3;
        }

        drawBoard(sliWidth, sliHeight, tileSize);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                arrayBoard[i][j].setWhichPlayer(0);
                arrayBoard[i][j].setCarColours(null);

            }
        }

        arrayBoard[0][0].setWhichPlayer(1);
        arrayBoard[0][0].setCarColours(enumColor1);
        drawPlayer(1,1,enumColor1,0);

        arrayBoard[width-1][0].setWhichPlayer(2);
        arrayBoard[width-1][0].setCarColours(enumColor2);
        drawPlayer(width,1,enumColor2,0);

        arrayBoard[0][height-1].setWhichPlayer(3);
        arrayBoard[0][height-1].setCarColours(enumColor3);
        drawPlayer(1,height,enumColor3,0);

        arrayBoard[width-1][height-1].setWhichPlayer(4);
        arrayBoard[width-1][height-1].setCarColours(enumColor4);
        drawPlayer(width,height,enumColor4,0);

    }

    /**
     * Method to draw a player at a specific coordinate on canvas from top left (0,0) origin.
     *
     * @param locX      location X of where to draw.
     * @param locY      location Y of where to draw.
     * @param carColour specified colour of the car to draw.
     */
    public void drawPlayer(int locX, int locY, CarColours carColour, int rotate) {

        System.out.println("drawPlayer method called");

        //check which car color image to draw
        Image image;

        switch (carColour) {
            case GREY:
                image = imagePlGrey;
                break;
            case PINK:
                image = imagePlPink;
                break;
            case BLACK:
                image = imagePlBlack;
                break;
            case WHITE:
                image = imagePlWhite;
                break;
            case ORANGE:
                image = imagePlOrange;
                break;
            case YELLOW:
                image = imagePlYellow;
                break;
            case BURGUNDY:
                image = imagePlBurgundy;
                break;
            case GLAUCOUS:
                image = imagePlGlaucous;
                break;
            case TURQUOISE:
                image = imagePlTurquiose;
                break;
            case AQUAMARINE:
                image = imagePlAquamarine;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + carColour);
        }

        System.out.println("Color of the car set.");

        double whereXDrawn = ((locX * tileSize) - tileSize) + topLeftX;
        double whereYDrawn = ((locY * tileSize) - tileSize) + topLeftY;
        System.out.println(topLeftX);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.save();
        rotateMethod(gc, rotate, whereXDrawn + tileSize / 2, whereYDrawn + tileSize / 2);
        System.out.println("carImage rotation is set");

        gc.drawImage(image, whereXDrawn, whereYDrawn, tileSize, tileSize);

        System.out.println("Car needed to be draw at " + locX + " " + locY);
        System.out.println("Drawn a car at " + whereXDrawn + " " + whereYDrawn);

        gc.restore();

    }

    /**
     * Method to draw the board on the canvas
     *
     * @param sliWidth  slider for width
     * @param sliHeight slider for height
     * @param tileSize  tile size on the canvas
     */
    public void drawBoard(Slider sliWidth, Slider sliHeight, double tileSize) {

        Image background = new Image("LEBackground.jpg");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < sliWidth.getValue(); i++) {

            for (int j = 0; j < sliHeight.getValue(); j++) {

                drawOnCanvas(arrayBoard[i][j].getTileType(), i + 1, j + 1, arrayBoard[i][j].getRotate());

                System.out.println("Placed a new slot in arrayBoard " + i + " " + j);
            }
        }

    }

    public void CanvasDraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //gc.setFill(Color.BLUE);
        //gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    //pretend its not here :)
    public void CanvasDrawPizza() {
        Image pizza = new Image("pizzaMain.png");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(pizza, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Method to save the player and tile info from the slot to a file
     *
     * @throws IOException issue with file
     */

    /*
    public void getSlotInfo(Slot slot) throws IOException {
        //int whichPlayer = slot.setWhichPlayer(0);
        tileTemp = slot.getTileType();

        slotInfoFile = new File("Assets\\SlotInfoFile.txt");

        FileWriter writer = new FileWriter(slotInfoFile);
        writer.write(which.toString() + tileTemp.toString());
        writer.flush();
        writer.close();
    }
     */
    public Slot[][] getArrayBoard() {
        return arrayBoard;
    }

    public void getAllVariable() {

    }

    public void setPlayerColor1(CarColours playerColor1) {
        this.playerColor1 = playerColor1;
    }

    public void setPlayerColor2(CarColours playerColor2) {
        this.playerColor2 = playerColor2;
    }

    public void setPlayerColor3(CarColours playerColor3) {
        this.playerColor3 = playerColor3;
    }

    public void setPlayerColor4(CarColours playerColor4) {
        this.playerColor4 = playerColor4;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Method to set a tile on a clicked location
     *
     * @param mouseX       x coordinate of the mouse location
     * @param mouseY       y coordinate of the mouse location
     * @param tempSel      the tile value to be set
     * @param sliWidth     slider for width
     * @param sliHeight    slider for height
     * @param tileSelected boolean to see if a tile is selected to place
     * @param intPlayerSel
     * @param rotate       value of the rotation of the tile
     */
    public void placeOnSlot(double mouseX, double mouseY, String tempSel, Slider sliWidth, Slider sliHeight, boolean tileSelected, int intPlayerSel, int rotate) {

        System.out.println("placeOnSlot method reached");
        //check if a player is being placed
        if (tempSel.equals("playerPINK") || tempSel.equals("playerYELLOW") || tempSel.equals("playerTURQUOISE") || tempSel.equals("playerORANGE") ||
                tempSel.equals("playerGREY") || tempSel.equals("playerBLACK") ||
                tempSel.equals("playerWHITE") || tempSel.equals("playerBURGUNDY") || tempSel.equals("playerGLACOUS") ||
                tempSel.equals("playerAQUAMARINE")) {

            CarColours tempCarColour = null;

            switch (tempSel) {
                case "playerPINK":
                    tempCarColour = CarColours.PINK;
                    break;
                case "playerYELLOW":
                    tempCarColour = CarColours.YELLOW;
                    break;
                case "playerTURQUOISE":
                    tempCarColour = CarColours.TURQUOISE;
                    break;
                case "playerORANGE":
                    tempCarColour = CarColours.ORANGE;
                    break;
                case "playerGREY":
                    tempCarColour = CarColours.GREY;
                    break;
                case "playerBLACK":
                    tempCarColour = CarColours.BLACK;
                    break;
                case "playerWHITE":
                    tempCarColour = CarColours.WHITE;
                    break;
                case "playerBURGUNDY":
                    tempCarColour = CarColours.BURGUNDY;
                    break;
                case "playerGLAUCOUS":
                    tempCarColour = CarColours.GLAUCOUS;
                    break;
                case "playerAQUAMARINE":
                    tempCarColour = CarColours.AQUAMARINE;
                    break;
                default:
                    System.out.println("Please select a correct color");
            }

            PlaceCoords placeCoords = checkWhereToPlace((int) tileSize, mouseX, mouseY, sliWidth, sliHeight);

            if (arrayBoard[placeCoords.getLocX()][placeCoords.getLocY()].getWhichPlayer() != intPlayerSel && arrayBoard[placeCoords.getLocX()][placeCoords.getLocY()].getWhichPlayer() != 0) {
                lblstatus.setText("Can't place " + "P" + intPlayerSel + " on " + "P" + arrayBoard[placeCoords.getLocX()][placeCoords.getLocY()].getWhichPlayer());
            } else {

                for (int i = 0; i < sliWidth.getValue(); i++) {
                    for (int j = 0; j < sliHeight.getValue(); j++) {

                        if (arrayBoard[i][j].getWhichPlayer() == intPlayerSel) {

                            arrayBoard[i][j].setCarColours(null);
                            arrayBoard[i][j].setWhichPlayer(0);


                            drawOnCanvas(arrayBoard[i][j].getTileType(), i + 1, j + 1, arrayBoard[i][j].getRotate());
                        } else {
                            System.out.println();
                        }

                    }
                }

                //placing a player into a slot needs to be done.
                arrayBoard[placeCoords.getLocX() - 1][placeCoords.getLocY() - 1].setWhichPlayer(intPlayerSel);
                arrayBoard[placeCoords.getLocX() - 1][placeCoords.getLocY() - 1].setCarColours(tempCarColour);

                drawPlayer(placeCoords.getLocX(), placeCoords.getLocY(), tempCarColour, rotate);

                lblstatus.setText("P" + intPlayerSel + " placed at " + placeCoords.getLocX() + " " + placeCoords.getLocY());
            }

        } else {
            //if a tile is being drawn then this else will execute

            //Convert the tempSel(String) to tileType(TileType)
            TileType tileType = null;

            switch (tempSel) {
                case "empty":
                    tileType = TileType.EMPTY;
                    break;
                case "straight":
                    tileType = TileType.STRAIGHT;
                    break;
                case "t_shape":
                    tileType = TileType.T_SHAPE;
                    break;
                case "goal":
                    tileType = TileType.GOAL;
                    break;
                case "corner":
                    tileType = TileType.CORNER;
                    break;
                case "upgrade" :
                    tileType = TileType.UPGRADE;
                    break;
                default:
                    break;
            }

            System.out.println("tiletype is set " + tileType.toString());

            //lblstatus.setText("Tile " + );

            //check if something is selected
            if (tileSelected == true) {
                System.out.println("tileSelected is true");

                //check the slot location from the mouse location
                PlaceCoords placeCoords = checkWhereToPlace((int) tileSize, mouseX, mouseY, sliWidth, sliHeight);
                if (placeCoords.getOutOfBounds() == false) {
                    System.out.println("Mouse location to Slot location, is complete.");

                    //Slot slot = new Slot(null, null, 0,0,0);
                    //arrayBoard[1][1] = slot;

                    //place the selected tile into the according arrayBoard place.
                    arrayBoard[placeCoords.getLocX() - 1][placeCoords.getLocY() - 1].setTileType(tileType);
                    arrayBoard[placeCoords.getLocX() - 1][placeCoords.getLocY() - 1].setRotate(rotate);
                    System.out.println("Set the new tile type, at Slot location" + placeCoords.getLocX() + " " + placeCoords.getLocY());

                    //draw the tile
                    drawOnCanvas(tileType, placeCoords.getLocX(), placeCoords.getLocY(), rotate);
                    System.out.println("drawOnCanvas method called, after Slot setting.");

                    lblstatus.setText(tempSel + " placed at " + placeCoords.getLocX() + " " + placeCoords.getLocY());
                } else {
                    System.out.println("Out Of Bounds, can't place");
                    lblstatus.setText("Out Of Bounds!");
                }
            }
        }
    }

    /**
     * Method to draw a tile on the canvas
     *
     * @param tempSel tile value to be drawn
     * @param locX    location x of the tile to be drawn on the board
     * @param locY    location y of the tile to be drawn on the board
     * @param rotate  rotation value of the tile
     */
    public void drawOnCanvas(TileType tempSel, int locX, int locY, int rotate) {

        System.out.println("drawOnCanvas method called");

        Image image;
        String imageStr;

        //determine image from tile type
        switch (tempSel) {
            case EMPTY:
                image = imageEmpty;
                imageStr = "empty";
                break;
            case CORNER:
                image = imageCorner;
                imageStr = "corner";
                break;
            case T_SHAPE:
                image = imageTShape;
                imageStr = "t_shape";
                break;
            case GOAL:
                image = imageGoal;
                imageStr = "goal";
                break;
            case STRAIGHT:
                image = imageStraight;
                imageStr = "straight";
                break;
            case UPGRADE:
                image = imageUpgrade;
                imageStr = "upgrade";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tempSel);
        }

        double whereXDrawn = ((locX * tileSize) - tileSize) + topLeftX;
        double whereYDrawn = ((locY * tileSize) - tileSize) + topLeftY;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.save();
        rotateMethod(gc, rotate, whereXDrawn + tileSize / 2, whereYDrawn + tileSize / 2);
        System.out.println("Image rotation is set");

        gc.drawImage(image, whereXDrawn, whereYDrawn, tileSize, tileSize);

        System.out.println("Image " + imageStr + " is Drawn at " + whereXDrawn + " " + whereYDrawn);

        gc.restore();
    }

    /**
     * Intermediary method used to rotate the images on canvas before drawing.
     *
     * @param gc    Graphics context
     * @param angle angle of rotation
     * @param px    location x to draw on
     * @param py    location y to draw on
     */
    public void rotateMethod(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    /**
     * Intermediary method used to determine the location of where to place the tile according to mouse cursor location and the size of the board
     *
     * @param tileSize size of the tile on the board
     * @param mouseX   mouse location x
     * @param mouseY   mouse location y
     * @param sliderW  slider for width
     * @param sliderH  slider for height
     * @return value returned for getting coordinates of the location of where to place the tile on board.
     */
    private PlaceCoords checkWhereToPlace(int tileSize, double mouseX, double mouseY, Slider sliderW, Slider sliderH) {

        PlaceCoords placeCoordsTemp = new PlaceCoords(0, 0);

        double pX = mouseX;
        double pY = mouseY;
        //checking for out of bounds and tile location
        if (pY < topLeftY || pX < topLeftX) {
            System.out.println("Out Of Bounds, can't place");
            placeCoordsTemp.setOutOfBounds(true);
        } else {
            if (pX <= (tileSize * 1) + topLeftX) {
                placeCoordsTemp.setLocX(1);
            } else if (pX <= (tileSize * 2) + topLeftX) {
                placeCoordsTemp.setLocX(2);
            } else if (pX <= (tileSize * 3) + topLeftX && sliderW.getValue() > 2) {
                placeCoordsTemp.setLocX(3);
            } else if (pX <= (tileSize * 4) + topLeftX && sliderW.getValue() > 3) {
                placeCoordsTemp.setLocX(4);
            } else if (pX <= (tileSize * 5) + topLeftX && sliderW.getValue() > 4) {
                placeCoordsTemp.setLocX(5);
            } else if (pX <= (tileSize * 6) + topLeftX && sliderW.getValue() > 5) {
                placeCoordsTemp.setLocX(6);
            } else if (pX <= (tileSize * 7) + topLeftX && sliderW.getValue() > 6) {
                placeCoordsTemp.setLocX(7);
            } else if (pX <= (tileSize * 8) + topLeftX && sliderW.getValue() > 7) {
                placeCoordsTemp.setLocX(8);
            } else if (pX <= (tileSize * 9) + topLeftX && sliderW.getValue() > 8) {
                placeCoordsTemp.setLocX(9);
            } else if (pX > boardSizePx + topLeftX) {
                System.out.println("Out Of Bounds, can't place");
                placeCoordsTemp.setOutOfBounds(true);
            }

            if (pY <= tileSize * 1 + topLeftY) {
                placeCoordsTemp.setLocY(1);
            } else if (pY <= tileSize * 2 + topLeftY) {
                placeCoordsTemp.setLocY(2);
            } else if (pY <= tileSize * 3 + topLeftY && sliderH.getValue() > 2) {
                placeCoordsTemp.setLocY(3);
            } else if (pY <= tileSize * 4 + topLeftY && sliderH.getValue() > 3) {
                placeCoordsTemp.setLocY(4);
            } else if (pY <= tileSize * 5 + topLeftY && sliderH.getValue() > 4) {
                placeCoordsTemp.setLocY(5);
            } else if (pY <= tileSize * 6 + topLeftY && sliderH.getValue() > 5) {
                placeCoordsTemp.setLocY(6);
            } else if (pY <= tileSize * 7 + topLeftY && sliderH.getValue() > 6) {
                placeCoordsTemp.setLocY(7);
            } else if (pY <= tileSize * 8 + topLeftY && sliderH.getValue() > 7) {
                placeCoordsTemp.setLocY(8);
            } else if (pY <= tileSize * 9 + topLeftY && sliderH.getValue() > 8) {
                placeCoordsTemp.setLocY(9);
            } else if (pY > boardSizePx + topLeftY) {
                System.out.println("Out Of Bounds, can't place");
                placeCoordsTemp.setOutOfBounds(true);
            }
        }


        return placeCoordsTemp;
    }
}
