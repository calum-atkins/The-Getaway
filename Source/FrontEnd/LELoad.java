package FrontEnd;

import BackEnd.Coordinate;
import BackEnd.Player;
import BackEnd.TileType;
import FrontEnd.Slot;
import FrontEnd.StateLoad;
import FrontEnd.WindowLoader;
import com.sun.corba.se.impl.encoding.CDROutputObject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

/**
 * The Level Editor Load class. It will load custom designed boards for a new game.
 * @author Calum Atkins, Ventsislav Yordanov
 */
public class LELoad {

    WindowLoader wl;
    private int height;
    private int width;

    private int numberOfPlayers;
    private HashMap<String, String> initData;
    private ArrayList<Coordinate> upgradePos;

    public void LELoad() {
    }

    /**
     * Method to read from a text file and load up a board based on the data.
     * @param fileName The name of file to be loaded.
     */
    public void load(String fileName, HashMap<String, String> id, Scene scene, Stage prevStage) throws IOException {
        upgradePos = new ArrayList<>();
        initData = id;
        System.out.println("Loading Board " + fileName);
        try {
            File fileToLoad = new File("SaveData\\CustomLevels\\" + fileName);
            Scanner reader = new Scanner(fileToLoad);
            String loadMsg = "Load Complete";

            initData.put("name", fileName);
            String line = reader.nextLine();
            String[] heightWidth = line.split(",");
            height = Integer.parseInt(heightWidth[0]);
            width = Integer.parseInt(heightWidth[1]);
            initData.put("height", String.valueOf(height));
            initData.put("width", String.valueOf(width));
            //reader.nextLine();

            System.out.println(height + " " + width);
            String character = null;
            Slot[][] slots = new Slot[9][9];
            for (int i = 0; i < height; i++) {
                Scanner lineScan = new Scanner(reader.nextLine());
                lineScan.useDelimiter(",");
                for (int j = 0; j < width; j++) {
                    character = lineScan.next();

                    switch (character) {
                        case "_": slots[i][j] = new Slot(0, TileType.EMPTY, i, j, 0); break;
                        case "`": slots[i][j] = new Slot(0, TileType.CORNER, i, j, 270); break;
                        case "¬": slots[i][j] = new Slot(0, TileType.CORNER, i, j, 0); break;
                        case "'": slots[i][j] = new Slot(0, TileType.CORNER, i, j, 90); break;
                        case "&": slots[i][j] = new Slot(0, TileType.CORNER, i, j, 180); break;
                        case "3": slots[i][j] = new Slot(0, TileType.STRAIGHT, i, j, 0); break;
                        case "2": slots[i][j] = new Slot(0, TileType.STRAIGHT, i, j, 90); break;
                        case "%": slots[i][j] = new Slot(0, TileType.STRAIGHT, i, j, 180); break;
                        case "1": slots[i][j] = new Slot(0, TileType.STRAIGHT, i, j, 270); break;
                        case "£": slots[i][j] = new Slot(0, TileType.T_SHAPE, i, j, 180); break;
                        case "4": slots[i][j] = new Slot(0, TileType.T_SHAPE, i, j, 270); break;
                        case "$": slots[i][j] = new Slot(0, TileType.T_SHAPE, i, j, 0); break;
                        case "5": slots[i][j] = new Slot(0, TileType.T_SHAPE, i, j, 90); break;
                        case "*": slots[i][j] = new Slot(0, TileType.GOAL, i, j, 0); break;
                        case "u" : slots[i][j] = new Slot(0, TileType.T_SHAPE, i, j, 0); break;
                        default: CustomAlerts.Warning("User Alert", "Something is wrong with the structure of the tiles in your saved level, please try a different one."); return;
                    }
                }
                lineScan.close();
            }


            numberOfPlayers = 4;
;
            String[] startLocationX = new String[4];
            String[] startLocationY = new String[4];
            for (int i = 0; i < numberOfPlayers; i++) {
                Scanner locScan = new Scanner(reader.nextLine());
                locScan.useDelimiter(",");
                startLocationX[i] = locScan.next();
                startLocationY[i] = locScan.next();
                locScan.close();

            }



            String author = reader.nextLine();
            initData.put("author", author);
            //System.out.println(author);
            String boolIce = reader.nextLine();
            String boolFire = reader.nextLine();
            String boolDouble = reader.nextLine();
            String boolBack = reader.nextLine();
            initData.put("boolIce", boolIce);
            initData.put("boolFire", boolFire);
            initData.put("boolDouble", boolDouble);
            initData.put("boolBack", boolBack);

            String player1Col = reader.nextLine();
            String player2Col = reader.nextLine();
            String player3Col = reader.nextLine();
            String player4Col = reader.nextLine();
            initData.put("player1col", player1Col);
            initData.put("player2col", player2Col);
            initData.put("player3col", player3Col);
            initData.put("player4col", player4Col);
            initData.put("loaded", "true");

            int numberUpgrades = Integer.parseInt(reader.nextLine());
            for (int i = 0; i < numberUpgrades; i++) {
                Scanner lineScan = new Scanner(reader.nextLine());
                lineScan.useDelimiter(",");
                int x = Integer.parseInt(lineScan.next());
                int y = Integer.parseInt(lineScan.next());
                upgradePos.add(new Coordinate(x, y));
            }

            //get values and return array board
            reader.close();
            WindowLoader wl;
            wl = new WindowLoader(prevStage);
            wl.loadCustom("LEMain", initData,slots, startLocationX,startLocationY, upgradePos);

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred while trying to load a game.");
            e.printStackTrace();
        }

    }

}
