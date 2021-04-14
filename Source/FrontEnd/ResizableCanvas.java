package FrontEnd;

import BackEnd.TileType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * This is a small class that allows for the Canvas to be bound to the size of the screen
 * Some of this is code from StackOverflow, some of it is all me
 * @author Pat Sambor
 */
public class ResizableCanvas extends Canvas {

    int width;
    int height;

    GraphicsContext gc = getGraphicsContext2D();

        public ResizableCanvas() {
            //redrawing the BG on a size change, in practice this is only on the intiialise because the window can't change size
            //Unless maybe you change resolution? But that's a weird edge case
            widthProperty().addListener(evt -> draw());
            heightProperty().addListener(evt -> draw());

        }


        public void draw() {
            Image background = new Image("LEBackground.jpg");
            gc.drawImage(background, 0, 0, this.getWidth(), this.getHeight());
        }

    /**
     * This class was meant to draw the board of empty tiles during the initialise step
     * I'm leaving this here because I put way too much work for it to be lost to the ages
     * @deprecated
     */
    public void drawForReal()   {
            int boardSizePx;
            int topLeftX = 0;
            int topLeftY = 0;
            if (this.getHeight() >= this.getWidth()) {
                boardSizePx = (int) this.getWidth();
                topLeftY = (int) ((this.getHeight()-boardSizePx)/2);
            } else if (this.getWidth() >= this.getHeight()) {
                boardSizePx = (int) this.getHeight();
                topLeftX = (int) ((this.getWidth()-boardSizePx)/2);
            }



            int tileSize = 0;
            if (height == 9 || width == 9) {
                tileSize = 900/9;
                System.out.println("9x9 board");
            } else if (height == 8 || width == 8) {
                tileSize = 900/8;
            } else if (height == 7 || width == 7) {
                tileSize = 900/7;
            } else if (height == 6 || width == 6) {
                tileSize = 900/6;
            } else if (height == 5 || width == 5) {
                tileSize = 900/5;
            } else if (height == 4 || width == 4) {
                tileSize = 900/4;
            } else if (height == 3 || width == 3) {
                tileSize = 900/3;
            }
            Slot[][] arrayBoard = new Slot[9][9];
            Image image;
            String imageStr;

            image = new Image("empty.png");
            imageStr = "empty";
            height = height + 1;
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {


                    Slot slot = new Slot(0, TileType.EMPTY, i,j,0);
                    arrayBoard[i][j] = slot;

                    System.out.println("Placed a new slot in arrayBoard " + i + " " + j);
                    double whereXDrawn = (i*tileSize)-tileSize + topLeftX;
                    double whereYDrawn = (j*tileSize)-tileSize + topLeftY;
                    gc.save();

                    gc.drawImage(image, whereXDrawn, whereYDrawn, tileSize, tileSize);
                    System.out.println("Image " + imageStr + " is Drawn at " + whereXDrawn + " " + whereYDrawn);
                }
            }


            //gc.drawImage(background, 0, 0, width, height);
            System.out.println("drawOnCanvas method called");
        }

        @Override
        public boolean isResizable() {
            return true;
        }

        @Override
        public double prefWidth(double height) {
            return getWidth();
        }

        @Override
        public double prefHeight(double width) {
            return getHeight();
        }

        public void setBoardWidth(int theWidth)  {
            width = theWidth;
        }

        public void setBoardHeight(int theHeight)  {
            height = theHeight;
        }
}

