package shadowGame;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * This is a pause menu controller class, it is meant to control the pause menu
 * @author Daniel Adesina
 * @version 2.0
 * @since 20th December 2018    
 */
public class PauseMenuController {
	@FXML Stage stage = new Stage();
	@FXML Button howtoplay;
	@FXML Button store;
	@FXML Button settings;
	@FXML Button quit;
	@FXML Button closeButton;
	Stage howToPlayStage;
	Character gamePlayer;
	boolean run = true;
	public void getScene(Stage gameStage) {  //passing parameters
		stage = gameStage;
	}
	
	public void setChar(Character player) {
		gamePlayer = player;
	}
	/*
	 * A button Click handler to be used in an .fxml file, takes in an event value and checks buttons based off it.
	 * @param evt An ActionEvent used in javafx.event
	 */
	public void ButtonClickHandler(ActionEvent evt) {
		Button clickedButton = (Button) evt.getTarget();
		String buttonLabel = clickedButton.getText();
		
		if(clickedButton == quit) {
			System.out.println("EXTERMINATE PROGRAM");
			Platform.exit();
		}
		
		if(clickedButton == howtoplay) {
			System.out.println("cool");
			System.out.println(" o p e n  u p");
			openHowToWindow();
		}
		
		if(clickedButton == store) {
			openStore();
		}
	}
	
	/**
	 * This closes the current window open based off the fx:id it is attached to
	 * @param evt
	 */
	public void closeWindowButtonClickHandler(ActionEvent evt) {
		final Node source = (Node) evt.getSource();
		final Stage stage =(Stage)source.getScene().getWindow();
		stage.hide();
	}
	
	/**
	 * THREADING: Updates the amount of stats manager
	 */
	public void statsManager() {
		  Thread thread = new Thread(() -> {
			  while(run) {
		            try {
		                Thread.sleep(5000);
		            } catch (InterruptedException exc) {
		                throw new Error("Unexpected interruption", exc);
		            }
//		            Platform.runLater(() -> text.setText(Integer.toString(gamePlayer.power)));
//		            System.out.println(gamePlayer.power);
			  }
	        });
	        thread.setDaemon(true);
	        thread.start();
	}


	
	private void openHowToWindow() {
		try {
		BorderPane howTo = (BorderPane)FXMLLoader.load(getClass().getResource("Instruction.fxml"));
		//howToPlayStage.initStyle(StageStyle.TRANSPARENT);
		howToPlayStage = new Stage();
		Scene howToScene = new Scene(howTo,442,201);
		howToPlayStage.initStyle(StageStyle.TRANSPARENT);
		howToScene.setFill(Color.TRANSPARENT);
		howToScene.getStylesheets().add(getClass().getResource("instruction.css").toExternalForm());
		
		howToPlayStage.setScene(howToScene);
		howToPlayStage.setResizable(false);
		howToPlayStage.showAndWait();
		howToScene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>
		
		  () {

		        @Override
		        public void handle(KeyEvent t) {
		          if(t.getCode()==KeyCode.ESCAPE)
		          {
		        	  System.out.println("col");
			          
		          
		          }
		        }
		    });
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}


	private void openStore() {
		try {
			//AnchorPane store = (AnchorPane)FXMLLoader.load(getClass().getResource("Store.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Store.fxml")); 
			Parent root = (Parent)loader.load(); //Loads up the Border Pane using the .fxml file
			//howToPlayStage.initStyle(StageStyle.TRANSPARENT);
			Stage storeStage = new Stage();
			Scene storeScene = new Scene(root,1000,400);
			storeStage.initStyle(StageStyle.TRANSPARENT);
			storeScene.setFill(Color.TRANSPARENT);
			storeScene.getStylesheets().add(getClass().getResource("store.css").toExternalForm());
			StoreController controller = loader.getController();
			controller.storeManager();
			controller.setChar(gamePlayer);
			storeStage.setScene(storeScene);
			storeStage.setResizable(false);
			storeStage.showAndWait();
		
		storeScene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>
		
		  () {

		        @Override
		        public void handle(KeyEvent t) {
		          if(t.getCode()==KeyCode.ESCAPE)
		          {
		        	  System.out.println("col");
			          
		          
		          }
		        }
		    });
		} catch(Exception e) { 
			e.printStackTrace();
		}
	
	}
}
