package shadowGame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class StartScreenController {
	/**
	 * A game stage variable as a container for the game stage
	 */
	public static Stage gameStage;
	@FXML Button playGame; 
	/**
	 * This is a buttonclick handler to check the key presses in this window
	 * @param evt This passes the parameter of evt onto the class
	 */
	public void buttonClickHandler(ActionEvent evt) {
		Button clickedButton = (Button) evt.getTarget();
		String buttonLabel = clickedButton.getText();
		
		if (buttonLabel.equals("play game")) { //checks if the button pressed is that to play the game, in that case it launches it
			final Node source = (Node) evt.getSource();
			final Stage stage =(Stage)source.getScene().getWindow();
			System.out.println("oof");
			stage.close();
			openGameWindow();
		}
	}
	
	
	
	private void openGameWindow() {
		try { 
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));   //this loads the fxml
			BorderPane root = (BorderPane)loader.load(); //root pane
			Scene scene = new Scene(root, 1000, 400); //new scene
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); //css
			GameScreenController controller = loader.getController(); //getting the controller
			gameStage = new Stage(); //creating the stage
			gameStage.initStyle(StageStyle.TRANSPARENT); //making it invisible
			gameStage.setScene(scene); 
			controller.getScene(gameStage);
			controller.gameLoop();
			gameStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}