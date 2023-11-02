package CEMS;

import CEMS.Common.Globals;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    Stage window;

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        setDatabase();
        launch(args);
    }

    public static void setDatabase() {
        Globals.setDb_name("CEMS");
        Globals.setDb_username("sa");
        Globals.setDb_pass("admin@123");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println("Loading main menu window");
        window = primaryStage;

        // start off with main menu
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("WelcomePage.fxml")));
        window.setTitle("CEMS - Welcome");
        window.setScene(new Scene(root, 900, 600));
        window.show();
    }
}