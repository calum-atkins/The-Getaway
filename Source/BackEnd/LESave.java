package BackEnd;

import FrontEnd.CustomAlerts;
import FrontEnd.LECanvas;
import FrontEnd.Slot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * The Level Editor Save class. It will save the custom designed board.
 * @author Calum Atkins, Ventsislav Yordanov
 */
public class LESave {
    private File gameFile;
    private LECanvas canvas;
    private Slot[][] slots;
    private int height;
    private int width;
    private String player1Location;
    private String player2Location;
    private String player3Location;
    private String player4Location;
    private String player1Colour;
    private String player2Colour;
    private String player3Colour;
    private String player4Colour;
    private int playerCounter = 0;
    private boolean boolIce = false;
    private boolean boolDouble = false;
    private boolean boolFire = false;
    private boolean boolBack = false;
    private String boardAuthor;
    private String fileName;
    private HashMap<String, String> initData;

    /**
     * Method to save the custom board with the player colors and the enabled/disabled action tiles.
     * @param c the canvas
     * @param id Hashmap id for all the variables needed to save the board.
     */
    public LESave(LECanvas c, HashMap<String, String> id) {
        canvas = c;
        initData = id;
        fileName = id.get("name");
        height = Integer.parseInt(id.get("height"));
        width = Integer.parseInt(id.get("width"));
        boardAuthor = id.get("author");
        player1Colour = id.get("player1col");
        player2Colour = id.get("player2col");
        player3Colour = id.get("player3col");
        player4Colour = id.get("player4col");
        boolIce = Boolean.parseBoolean(id.get("boolIce"));
        boolFire = Boolean.parseBoolean(id.get("boolFire"));
        boolDouble = Boolean.parseBoolean(id.get("boolDouble"));
        boolBack = Boolean.parseBoolean(id.get("boolBack"));
    }

    /**
     * This class will write all the data about a custom board from the level editor to a txt file.
     */
    public void Save() {
        System.out.println("Saving Board : " + fileName);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("SaveData\\CustomLevels\\" + fileName + ".txt"));

            slots = canvas.getArrayBoard();

            System.out.println(height + "," + width);
            bw.write(height + "," + width);
            bw.newLine();

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    String currentTile = null;

                    if (slots[i][j] != null) {
                        Slot tile = slots[i][j];
                        String tileType = tile.getTileType().toString();
                        int rotation = tile.getRotate();

                        if (tileType.equals("EMPTY")) {
                            bw.write("_");
                        } else if (tileType.equals("CORNER") && rotation == 270) {
                            bw.write("`");
                        } else if (tileType.equals("CORNER") && rotation == 0) {
                            bw.write("¬");
                        } else if (tileType.equals("CORNER") && rotation == 90) {
                            bw.write("'");
                        } else if (tileType.equals("CORNER") && rotation == 180) {
                            bw.write("&");
                        } else if (tileType.equals("STRAIGHT") && rotation == 0) {
                            bw.write("3");
                        } else if (tileType.equals("STRAIGHT") && rotation == 90) {
                            bw.write("2");
                        } else if (tileType.equals("STRAIGHT") && rotation == 180) {
                            bw.write("%");
                        } else if (tileType.equals("STRAIGHT") && rotation == 270) {
                            bw.write("1");
                        } else if (tileType.equals("T_SHAPE") && rotation == 180) {
                            bw.write("£");
                        } else if (tileType.equals("T_SHAPE") && rotation == 270) {
                            bw.write("4");
                        } else if (tileType.equals("T_SHAPE") && rotation == 0) {
                            bw.write("$");
                        } else if (tileType.equals("T_SHAPE") && rotation == 90) {
                            bw.write("5");
                        } else if (tileType.equals("GOAL")) {
                            bw.write("*");
                        } else if (tileType.equals("UPGRADE")) {
                            bw.write("u");
                        }
                        bw.write(",");
                        if (tile.getWhichPlayer() != 0) {
                            switch (tile.getWhichPlayer()) {
                                case 1: player1Location = tile.getLocX() + "," + tile.getLocY(); playerCounter++; break;
                                case 2: player2Location = tile.getLocX() + "," + tile.getLocY(); playerCounter++; break;
                                case 3: player3Location = tile.getLocX() + "," + tile.getLocY(); playerCounter++; break;
                                case 4: player4Location = tile.getLocX() + "," + tile.getLocY(); break;
                                default: break;
                            }
                        }
                    }
                }
                bw.newLine();
            }

            // Writing the 4 player start locations to the txt file
            bw.write(player1Location); bw.newLine();
            bw.write(player2Location); bw.newLine();
            bw.write(player3Location); bw.newLine();
            bw.write(player4Location); bw.newLine();

            // Writing the author to the file
            bw.write(boardAuthor); bw.newLine();

            // Writing the boolean values for the 4 different action tiles
            bw.write(String.valueOf(boolIce)); bw.newLine();
            bw.write(String.valueOf(boolFire)); bw.newLine();
            bw.write(String.valueOf(boolDouble)); bw.newLine();
            bw.write(String.valueOf(boolBack)); bw.newLine();

            //The following block will write the colour's for each of the players car.
            bw.write(player1Colour); bw.newLine();
            bw.write(player2Colour); bw.newLine();
            bw.write(player3Colour); bw.newLine();
            bw.write(player4Colour); bw.newLine();

            bw.write(Integer.toString(canvas.getNumberUpgrade()));
            for  (int i = 0; i < canvas.getNumberUpgrade(); i++) {
                bw.newLine();
                bw.write(canvas.getUpgradePos(i).getX() + "," + canvas.getUpgradePos(i).getY());
            }

            CustomAlerts.Warning("Saving File", "File " + fileName + " successfully saved.");
            bw.close();
        } catch (IOException e) {
            CustomAlerts.Warning("Saving File", "Error when saving " + fileName + ".");
            e.printStackTrace();
        }
    }
}
