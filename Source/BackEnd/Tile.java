package BackEnd;

import static BackEnd.TileType.*;

/**
 * This represents an abstract class, tile to be put on the which represent tiles to be put on to the Gameboard.
 *
 * @author James Sam, Christian Sanger, Atif Ishaq and Joshua Oladitan.
 * @version 1.0
 */
public abstract class Tile {

    /**
     * Creates any type of tile.
     *
     * @param type the type of the tile
     * @return Floor or Action tile cast to Tile.
     */
    public static Tile createTile(TileType type) {
        Tile result = null;
        if (isFloorTile(type)) {
            result = new FloorTile(type);
        } else {
            result = new ActionTile(type);
        }
        return result;
    }

    /**
     * This method checks to see if the tile given is a floor tile or not.
     *
     * @param tile, the tile to be checked.
     * @return true, if it is a floor tile, else, false.
     */
    static boolean isFloorTile(Tile tile) {
        return tile instanceof FloorTile;
    }

    static boolean isFloorTile(TileType type) {
        return type == CORNER || type == T_SHAPE || type == STRAIGHT || type == GOAL;
    }

    /**
     * This method returns the type of tile.
     *
     * @return the type of tile it is.
     */
    public abstract TileType getType();
}