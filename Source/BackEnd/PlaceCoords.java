package BackEnd;

public class PlaceCoords {

    int locX;
    int locY;
    boolean outOfBounds = false;

    /**
     * This constructor makes an object with x, y Coords.
     * @param x
     * @param y
     */
    public PlaceCoords(int x, int y) {
        locX = x;
        locY = y;
    }

    public void setLocY(int locY) {
        this.locY = locY;
    }

    public void setLocX(int locX) {
        this.locX = locX;
    }

    public int getLocY() {
        return locY;
    }

    public int getLocX() {
        return locX;
    }

    public void setOutOfBounds(boolean outOfBounds) {
        this.outOfBounds = outOfBounds;
    }

    public boolean getOutOfBounds() {
        return this.outOfBounds;
    }
}
