package BackEnd;

import FrontEnd.CustomAlerts;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This class is a file reader class which will take in a given level file format text file, verify it
 * and create the gameboard, along with the fixed tiles in their correct locations, the players in their correct
 * locations, and the amount and type of each floor/action tile that will populate the silk bag.
 *
 * @author Christian Sanger, Atif Ishaq and Joshua Oladitan.
 * @version 1.0
 */

public class FileReader {

    /*
    These static variables hold information about the number of players and the number
    of different tile types.
     */
    private static final int NUM_OF_TILE_TYPES = TileType.values().length;
    private static final int MAX_NUM_OF_PLAYERS = 4;

    /**
     * This method takes in the given level format file,
     * and create a gameboard and players for that game.
     *
     * @param filename    The name of the level file format text file.
     * @param silkBagSeed The seed used for this game.
     * @return pair where first element is the gameboard and second is the players.
     * @throws Exception issue with gameboard file
     */
    public static Pair<Gameboard, Player[]> gameSetup(String filename, int silkBagSeed) throws Exception {
        File input = new File("Gameboards\\" + filename);
        if (!input.exists()) {
            input = new File("SaveData\\CustomLevels\\" + filename);
            if (!input.exists()) {
                throw new FileNotFoundException(filename);
            }
            Scanner in = new Scanner(input);
            Scanner currentLine;
            SilkBag silkBag = new SilkBag(silkBagSeed);

            //// board config
            String line = in.nextLine();
            currentLine = new Scanner(line);
            String[] heightWidth = line.split(",");
            int height = Integer.parseInt(heightWidth[0]);
            int width = Integer.parseInt(heightWidth[1]);
            Gameboard gameboard = new Gameboard(width, height, silkBag);

            //// Filling SilkBag
            int[] tileTypeCount = new int[NUM_OF_TILE_TYPES];
            // Reading how many of each tile
            for (int tileType = 0; tileType < NUM_OF_TILE_TYPES - 1; tileType++) {
                tileTypeCount[tileType] = 100;
            }
            // putting them in the bag
            // for each tile type
            for (int tileType = 0; tileType < NUM_OF_TILE_TYPES - 1; tileType++) {
                int numberOfThisTile = tileTypeCount[tileType];
                // for each tile that need to be added to silkbag
                for (int i = 0; i < numberOfThisTile; i++) {
                    Tile newTile = Tile.createTile(TileType.values()[tileType]);
                    silkBag.insertTile(newTile);
                }
            }

            ////Fixed tiles
            for (int i = 0; i < height; i++) {
                line = in.nextLine();
                String[] character = line.split(",");
                for (int j = 0; j < width; j++) {
                    int x = j;
                    int y = i;
                    Coordinate location = new Coordinate(y, x);
                    TileType tileType = TileType.EMPTY;
                    Rotation rotation = Rotation.values()[0];
                    switch (character[j]) {
                        case "_": tileType = TileType.EMPTY; rotation = Rotation.UP; break;
                        case "`": tileType = TileType.CORNER; rotation = Rotation.LEFT; break;
                        case "¬": tileType = TileType.CORNER; rotation = Rotation.UP; break;
                        case "'": tileType = TileType.CORNER; rotation = Rotation.RIGHT; break;
                        case "&": tileType = TileType.CORNER; rotation = Rotation.DOWN; break;
                        case "3": tileType = TileType.STRAIGHT; rotation = Rotation.UP; break;
                        case "2": tileType = TileType.STRAIGHT; rotation = Rotation.RIGHT; break;
                        case "%": tileType = TileType.STRAIGHT; rotation = Rotation.DOWN; break;
                        case "1": tileType = TileType.STRAIGHT; rotation = Rotation.LEFT; break;
                        case "£": tileType = TileType.T_SHAPE; rotation = Rotation.DOWN; break;
                        case "4": tileType = TileType.T_SHAPE; rotation = Rotation.LEFT; break;
                        case "$": tileType = TileType.T_SHAPE; rotation = Rotation.UP; break;
                        case "5": tileType = TileType.T_SHAPE; rotation = Rotation.RIGHT; break;
                        case "*": tileType = TileType.GOAL; rotation = Rotation.UP; break;
                        default: CustomAlerts.Warning("User Alert", "Something is wrong with the structure of the tiles in your saved level, please try a different one.");
                    }
                    FloorTile tile = new FloorTile(tileType, rotation);

                    if (i == 0 && j == 0) {
                        gameboard.placeFixedTile(tile, location);
                    } else if (i == height - 1 && j == 0) {
                        gameboard.placeFixedTile(tile, location);
                    } else if (i == 0 && j == width - 1) {
                        gameboard.placeFixedTile(tile, location);
                    } else if (i == height - 1 && j == width - 1) {
                        gameboard.placeFixedTile(tile, location);
                    } else if (tile.getType() == TileType.GOAL) {
                        gameboard.placeFixedTile(tile, location);
                    } else {
                        gameboard.placeUnfixedTile(tile, location);
                    }
                }
            }
            //// Creating players
            Player[] players = new Player[MAX_NUM_OF_PLAYERS];
            Coordinate[] playerPos = new Coordinate[MAX_NUM_OF_PLAYERS];
            gameboard.setNumOfPlayers(MAX_NUM_OF_PLAYERS);
            for (int i = 0; i < MAX_NUM_OF_PLAYERS; i++) {
                String nextLine = in.nextLine();
                String[] pos = nextLine.split(",");
                int x = Integer.parseInt(pos[0]);
                int y = Integer.parseInt(pos[1]);
                playerPos[i] = new Coordinate(x, y);
                gameboard.setPlayerPos(i, new Coordinate(x, y));
                players[i] = new Player(silkBag, gameboard);
            }

            // Place players back in correct location as they would have been shifted when tiles have been placed
            for (int i = 0; i < MAX_NUM_OF_PLAYERS; i++) {
                for (int j = 0; j < 4; j++) {
                    gameboard.setPlayerPos(i, playerPos[i]);
                }
            }

            // Random generation of car upgrade tokens
//            Random rand = new Random();
//            for (int i = 0; i < gameboard.getNumOfPlayers(); i++) {
//                int max = rand.nextInt(gameboard.getHeight());
//                int min = rand.nextInt(gameboard.getWidth());
//                gameboard.addUpgradeToken(max, min);
//            }
            gameboard.addUpgradeToken(new Coordinate(1, 1));
            gameboard.addUpgradeToken(new Coordinate(5, 5));
            gameboard.addUpgradeToken(new Coordinate(6, 6));
            gameboard.addUpgradeToken(new Coordinate(0, 1));


            return new Pair<>(gameboard, players);


        }
        Scanner in = new Scanner(input);
        Scanner currentLine;
        SilkBag silkBag = new SilkBag(silkBagSeed);

        //// board config
        currentLine = new Scanner(in.nextLine());
        int width = currentLine.nextInt();
        int height = currentLine.nextInt();
        Gameboard gameboard = new Gameboard(width, height, silkBag);

        //// Creating players
        Player[] players = new Player[MAX_NUM_OF_PLAYERS];
        Coordinate[] playerPos = new Coordinate[MAX_NUM_OF_PLAYERS];
        gameboard.setNumOfPlayers(MAX_NUM_OF_PLAYERS);
        for (int i = 0; i < MAX_NUM_OF_PLAYERS; i++) {
            String nextLine = in.nextLine();
            currentLine = new Scanner(nextLine);
            int x = currentLine.nextInt();
            int y = currentLine.nextInt();
            playerPos[i] = new Coordinate(x, y);
            gameboard.setPlayerPos(i, new Coordinate(x, y));
            players[i] = new Player(silkBag, gameboard);
        }

        //// Filling SilkBag
        int[] tileTypeCount = new int[NUM_OF_TILE_TYPES];
        // Reading how many of each tile
        for (int tileType = 0; tileType < NUM_OF_TILE_TYPES - 1; tileType++) {
            currentLine = new Scanner(in.nextLine());
            tileTypeCount[tileType] = currentLine.nextInt();
        }
        // putting them in the bag
        // for each tile type
        for (int tileType = 0; tileType < NUM_OF_TILE_TYPES - 1; tileType++) {
            int numberOfThisTile = tileTypeCount[tileType];
            // for each tile that need to be added to silkbag
            for (int i = 0; i < numberOfThisTile; i++) {
                Tile newTile = Tile.createTile(TileType.values()[tileType]);
                silkBag.insertTile(newTile);
            }
        }

        //// Fill with random tiles
        Random r = new Random(silkBagSeed);
        ArrayList<Coordinate> slideLocations = gameboard.getSlideLocations();

        while (gameboard.isBoardNotFull()) {
            Coordinate toSlide = null;
            if (slideLocations.size() == 0) {
                throw new Exception("No slide locations");
            }

            for (int i = 0; i < slideLocations.size(); i++) {
                FloorTile tile = silkBag.getFloorTile();
                tile.setRotation(Rotation.values()[r.nextInt(4)]);
                toSlide = slideLocations.get(r.nextInt(slideLocations.size()));
                if (toSlide == null) {
                    throw new Exception("Null Slide location");
                }
                gameboard.playFloorTile(toSlide, tile);
            }
        }
        //// Fixed tiles
        currentLine = new Scanner(in.nextLine());
        int numberOfFixedTiles = currentLine.nextInt();
        for (int i = 0; i < numberOfFixedTiles; i++) {
            currentLine = new Scanner(in.nextLine());
            TileType tileType = TileType.valueOf(currentLine.next().toUpperCase());
            int x = currentLine.nextInt();
            int y = currentLine.nextInt();
            Coordinate location = new Coordinate(x, y);
            int rotationInt = currentLine.nextInt();
            Rotation rotation = Rotation.values()[rotationInt];
            FloorTile tile = new FloorTile(tileType, rotation);
            gameboard.placeFixedTile(tile, location);
        }

        // Place players back in correct location as they would have been shifted when tiles have been placed
        for (int i = 0; i < MAX_NUM_OF_PLAYERS; i++) {
            for (int j = 0; j < 4; j++) {
                gameboard.setPlayerPos(i, playerPos[i]);
            }
        }

        // Random generation of car upgrade tokens
//        Random rand = new Random();
//        for (int i = 0; i < gameboard.getNumOfPlayers(); i++) {
//            int max = rand.nextInt(gameboard.getHeight());
//            int min = rand.nextInt(gameboard.getWidth());
//            gameboard.addUpgradeToken(max, min);
//        }

        return new Pair<>(gameboard, players);
    }

    /**
     * This method takes in the given level format file, and checks to see that the file exists.
     *
     * @param gameBoard The name of the level file format text file.
     * @return in The scanner that iterates through the file.
     * @throws Exception if cannot create gameSetup
     */
    public static Pair<Gameboard, Player[]> gameSetup(String gameBoard) throws Exception {
        return gameSetup(gameBoard, (new Random()).nextInt());
    }
}