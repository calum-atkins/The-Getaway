package FrontEnd;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import MessageOfTheDay.MessageOfTheDay;
import javafx.scene.control.*;
import javafx.scene.media.AudioClip;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static FrontEnd.Main.*;

/**
 * This class handles the Jukebox Scene
 * @author Aqiel PG Metusin
 * @author Mohammed Tukur
 * @author Pat Sambor
 */
public class JukeBoxController extends StateLoad{
    //FXML Stuff here
    @FXML
    Slider progressBar = new Slider();
    @FXML
    Slider musicSlider = new Slider();
    @FXML
    ComboBox<String> musicBox = new ComboBox<String>();
    @FXML
    private Button exitButton = new Button();
    @FXML
    Label MoTD = new Label();
    @FXML
    ImageView stopMusic;
    @FXML
    ImageView playMusic;
    @FXML
    ImageView pauseMusic;
    @FXML
    Label lblTime = new Label();

    static String currentlyPlaying;

    //private stuff here
    private final String MAIN_MENU_SFX = "Assets\\SFX\\mainmenu.mp3";
    private final AudioClip MAIN_MENU_AUDIO = new AudioClip(new File(MAIN_MENU_SFX).toURI().toString());
    private final String RETURN_SFX = "Assets\\SFX\\return.mp3";
    private final AudioClip RETURN_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());

    //Public stuff here
    public String mainTheme = "Assets\\Music\\MainTheme.mp3";
    public String battle = "Assets\\Music\\Battle.mp3";
    public String jump = "Assets\\Music\\Jump.mp3";
    public String pizza = "Assets\\Music\\pizza.mp3";
    public String tokyoDrift = "Assets\\Music\\TokyoDrift.mp3";
    public String menu = "Assets\\Music\\Menu.mp3";
    public Media sound2;
    public Duration total;
    public Duration length;
    public int pause_unpause;
    public String x;
    WindowLoader wl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String message;
        try {
            message = MessageOfTheDay.puzzle();
        } catch (Exception e) {
            message = "Error with Server" + e.getCause();
        }
        MoTD.setText(message);
        ArrayList<String> listMusic = new ArrayList<>();

        listMusic.add(mainTheme);
        listMusic.add(battle);
        listMusic.add(jump);
        listMusic.add(pizza);
        listMusic.add(tokyoDrift);
        listMusic.add(menu);
        ObservableList< String > oListMusic = FXCollections.observableArrayList(listMusic);
        musicBox.setItems(oListMusic);
        musicBox.setValue(currentlyPlaying);

        playMusic.setImage(new Image("playbtnV3.png"));
        stopMusic.setImage(new Image("stopbtnV3.png"));
        pauseMusic.setImage(new Image("pausebtnV3.png"));
        onBackgroundChange();
        getMusicProgressBar();
        total = sound.getDuration();
        progressBar.setMax(total.toSeconds());

    }


    /**
     * Method to exit out of the Jukebox if you don't like using the X button
     * @throws IOException
     */
    public void onExit() throws IOException {
        //RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        Stage thisStage = (Stage) exitButton.getScene().getWindow();
        thisStage.close();
    }

    /**
     * Method to change the Volume using the Slider.
     */
    public void onBackgroundChange()    {

        musicSlider.setValue(Main.mediaPlayer.getVolume() * 100);
        musicSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                Main.mediaPlayer.setVolume(musicSlider.getValue()/100);
            }
        });
    }

    /**
     * Method to get the music to play
     */
    public String getMusic(){
        //comboBox -> getMusic -> play
        String song = musicBox.getValue();
        return song;
    }

    /**
    *Method to play music that is selected from the ComboBox(musicBox)
    */
    public void onPlay(){
        Main.mediaPlayer.stop();
        //plays music
        playHelper();
    }

    /**
     *Method to pause music that is currently running
     */
    public void onPause(){
        //pause music
        Main.mediaPlayer.pause();
    }

    /**
     *Method to stop music that is currently running
     */
    public void onStop(){
        //stop music
        Main.mediaPlayer.stop();
        progressBar.setValue(0);
    }
    /**
     *Method that helps the onPlay() by getting the Progress Bar, plays the mediaPlayer and set the currently
     * playing song/music.
     */
    public void playHelper(){
        x = getMusic();

        for( int i = 0; i<musicFiles.length; i++){
            x = musicFiles[i];
            //if the combobox = the musicfiles[i] then create a new sound and play it
            if(x == musicBox.getValue()){
                sound = new Media(new File(musicFiles[i]).toURI().toString());
                mediaPlayer = new MediaPlayer(sound);
            }
        }
        Main.mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                total = sound.getDuration();
                progressBar.setMax(total.toSeconds());
            }
        });
        currentlyPlaying = musicBox.getValue();
        Main.mediaPlayer.play();
        getMusicProgressBar();
    }

    /**
     * Method that gets the Progress Bar of a song/music file.
     */
    public void getMusicProgressBar(){
        Main.mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                progressBar.setValue(newValue.toSeconds());
                int minutes = (int) newValue.toMinutes();
                int seconds = (int) newValue.toSeconds();
                seconds = seconds -(minutes*60);
                lblTime.setText(minutes + ":" + seconds);

            }
        });
    }

    /**
     * Method for the Music Bar(Progress Bar) when pressed will seek to that value.
     */
    public void onMousePressedMB(){
        progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Main.mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
            }
        });
    }

    /**
     * Method for the Music Bar(Progress Bar) when dragged will seek to that value.
     */
    public void onMouseDraggedMB(){
        progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Main.mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
            }
        });
    }


}

