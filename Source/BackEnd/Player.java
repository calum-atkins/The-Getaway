package BackEnd;

import FrontEnd.GameScreenController;

import java.awt.*;
import java.util.ArrayList;

/**
 * Stores details about the player while they are in-game, for example their inventory or player number.
 *
 * @author Brandon Chan
 * @version 1.0
 */
public class Player {
    /*
     * Location variable will hold the players location.
     * lastDrawnTile is the players last drawn tile.
     * inventory variable is a list of the player tiles that he holds in his inventory.
     * silkbag is an instance of the SilkBag class
     * playerNumber is used to distinguish which player it is.
     * backTracked is used to check if the player has backtracked.
     * gameboard is an instance of the gameBoard class.
     */
    private Tile lastDrawnTile;
    private ArrayList<ActionTile> inventory = new ArrayList<>();
    private SilkBag silkBag = new SilkBag(1237912379);
    private boolean backTracked;
    private Gameboard gameboard = new Gameboard(0,0,null);
    private boolean upgrade;
    private CarColours colour;
    private static int counter;

    /**
     * Create a player and give them the silk bag and gameboard references.
     *
     * @param silkBag      Reference to the game's silk bag object.
     * @param gameboard    Reference to the game's gameboard object.
     */
    public Player(SilkBag silkBag, Gameboard gameboard) {
        this.silkBag = silkBag;
        this.inventory = new ArrayList<>();
        this.gameboard = gameboard;
        this.backTracked = false;
        this.upgrade = false;
        switch (counter) {
            case 0 : GameScreenController.addColour(CarColours.PINK); break;
            case 1 : GameScreenController.addColour(CarColours.YELLOW); break;
            case 2 : GameScreenController.addColour(CarColours.TURQUOISE); break;
            case 3 : GameScreenController.addColour(CarColours.ORANGE); break;
        }
        counter++;
    }

    public Player() {

    }

    /**
     * @return colour
     */
    public CarColours getColour() {
        return colour;
    }

    /**
     * @param colour
     */
    public void setColour(CarColours colour) {
        this.colour = colour;
    }

    public void setColour(String colour) {
        switch (colour) {
            case "playerPINK" : this.colour = CarColours.PINK; break;
            case "playerYELLOW" : this.colour = CarColours.YELLOW; break;
            case "playerORANGE" : this.colour = CarColours.ORANGE; break;
            case "playerTURQUOISE" : this.colour = CarColours.TURQUOISE; break;
            case "playerBURGUNDY" : this.colour = CarColours.BURGUNDY; break;
            case "playerAQUAMARINE" : this.colour = CarColours.AQUAMARINE; break;
            case "playerGLAUCOUS" : this.colour = CarColours.GLAUCOUS; break;
            case "playerBLACK" : this.colour = CarColours.BLACK; break;
            case "playerWHITE" : this.colour = CarColours.WHITE; break;
            case "playerGREY" : this.colour = CarColours.GREY; break;
        }
    }

    /**
     *
     * @return Upgraded car
     */
    public boolean getUpgrade() {
        return upgrade;
    }

    /**
     * @param upgrade upgraded boolean
     */
    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
    }

    /**
     * Method to get the action tiles in the player's inventory.
     *
     * @return inventory The action tiles in the player's inventory.
     */
    public ArrayList<ActionTile> getInventory() {
        return this.inventory;
    }


    /**
     * Draw a tile - if the player's previous action card draw is still in its grace period, add it to inventory first.
     */
    public void drawTile() {
        if (isHolding() != null) {
            if (isHolding() instanceof ActionTile) {
                inventory.add((ActionTile) isHolding());
            } else {
                System.out.println("Left over floor tile");
            }
        }
        lastDrawnTile = silkBag.getTile();
        while (lastDrawnTile.getType() == TileType.GOAL) {
            lastDrawnTile = silkBag.getTile();
        }
    }


    /**
     * Play a floor tile in a given location and rotation.
     *
     * @param slideLocations Where the player wants to slide a tile in from
     * @param tile           What tile the player wants to play
     */
    public void playFloorTile(Coordinate slideLocations, FloorTile tile) throws Exception {
        gameboard.playFloorTile(slideLocations, tile);
        lastDrawnTile = null;
    }


    /**
     * Method for playing a freeze or fire tile
     *
     * @param location     The center of the freeze/fire location null if action doesn't has a location
     * @param tile         The freeze or fire action tile
     * @param playerNumber the player that this is played on. ignored if action isn't played on a player.
     */
    public void playActionTile(Coordinate location, ActionTile tile, int playerNumber) throws Exception {
        gameboard.playActionTile(location, tile, playerNumber);
        removeFromInventory(tile);
    }


    /**
     * Returns the player's last drawn tile
     *
     * @return lastDrawnTile The player's last drawn tile
     */
    public Tile isHolding() {
        return lastDrawnTile;
    }

    /**
     * Set that this player cannot be backtracked again.
     */
    public void setBeenBackTracked() {
        this.backTracked = true;
    }

    /**
     * Gets whether the player has had the backtrack action used on them in
     * the history of the current game.
     *
     * @return backTracked Record of if backtrack action has been used on this player
     */
    public boolean hasBeenBacktracked() {
        return backTracked;
    }

    /**
     * Takes an action tile out of the inventory
     *
     * @param tile The Action Tile to be removed
     */
    private void removeFromInventory(ActionTile tile) {
        inventory.remove(tile);
    }
}
