package FrontEnd;

import BackEnd.*;

public class Slot {

    int whichPlayer;
    private TileType tileType;
    CarColours carColours;
    int LocX;
    int LocY;
    int rotate;

    Slot(int whichPlayer, TileType tileType, int LocX, int LocY, int rotate) {
        this.whichPlayer = whichPlayer;
        this.tileType = tileType;
        this.rotate = rotate;
        this.LocX = LocX;
        this.LocY = LocY;

    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public TileType getTileType() {
        return tileType;
    }

    public int getLocX() {
        return LocX;
    }

    public void setLocX(int locX) {
        LocX = locX;
    }

    public int getLocY() {
        return LocY;
    }

    public void setLocY(int locY) {
        LocY = locY;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }

    public int getRotate() {
        return rotate;
    }

    public void setCarColours(CarColours carColours) {
        this.carColours = carColours;
    }

    public CarColours getCarColours() {
        return carColours;
    }

    public int getWhichPlayer() {
        return whichPlayer;
    }

    public void setWhichPlayer(int whichPlayer) {
        this.whichPlayer = whichPlayer;
    }
}
