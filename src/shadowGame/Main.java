package shadowGame;
	
import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.fxml.FXMLLoader;

/**
 * This is a Main class, it is meant to be the very first window and it launches all others
 * @author Daniel Adesina
 * @version 2.0
 * @since 20th December 2018    
 */
public class Main extends Application {
	@Override
	/**
	 * A start method to launch the primaryStage
	 */
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
			Scene scene = new Scene(root, 364, 209);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * The default main method to run the program
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
 