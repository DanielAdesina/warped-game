package shadowGame;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StoreController {
	@FXML Button buyLaser;
	@FXML Button buyGravityGun;
	@FXML Button buyMissile;
	@FXML Button parry;
	@FXML Button closeButton;
	@FXML Text currencyAmount;
	boolean run = true;
	
	Character gamePlayer;
	
	public void setChar(Character player) {
		gamePlayer = player;
	}
	/**
	 * This is a button click handler, it manages button clicks 
	 * @param evt A parameter for the event
	 */
	public void buttonClickHandler(ActionEvent evt) {
		Button clickedButton = (Button) evt.getTarget();
		String buttonLabel = clickedButton.getText();
		
		if(clickedButton == buyLaser) {  //checks if the button pressed is the buy laser according to fx:id, it then turns it to an integer
			System.out.println("cool"); //checks if it is more than the cost
			if(Integer.parseInt(gamePlayer.currency) >= 20) {
				buyLaser.setText("PURCHASED"); //it then changes the text to say purchased
				gamePlayer.laserUnlocked = true;
				gamePlayer.currency = Integer.toString(Integer.parseInt(gamePlayer.currency) - 20); //subtracts 20 from it
			}
			
		}
		
		if(clickedButton == buyGravityGun) {  //checks if the button pressed is the buy laser according to fx:id, it then turns it to an integer
			System.out.println("cool"); //checks if it is more than the cost
			if(Integer.parseInt(gamePlayer.currency) >= 50) {
				buyGravityGun.setText("PURCHASED"); //it then changes the text to say purchased
				gamePlayer.gravityGunUnlocked = true;
				gamePlayer.currency = Integer.toString(Integer.parseInt(gamePlayer.currency) - 50); //subtracts 20 from it
			}
			
		}
		
		if(clickedButton == closeButton) {
			final Node source = (Node) evt.getSource();
			final Stage stage =(Stage)source.getScene().getWindow();
			stage.hide();
		}
	}
	//this updates the amount of currency live
	/**
	 * This is a method that uses threading to update the amount of currency live
	 */
	public void storeManager() {
		  Thread thread = new Thread(() -> {
			  while(run) {
		            try {
		                Thread.sleep(5);
		            } catch (InterruptedException exc) {
		                throw new Error("Unexpected interruption", exc);
		            }
		            Platform.runLater(() -> currencyAmount.setText(gamePlayer.currency));  //it sets the text to that of the player's current currency
		            //System.out.println(gamePlayer.power);
			  }
	        });
	        thread.setDaemon(true);
	        thread.start();
	}
}
