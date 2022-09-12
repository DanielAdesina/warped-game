package shadowGame;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StatsController {
	@FXML Button powerStat;
	@FXML Button defenseStat;
	@FXML Button speedStat;
	@FXML Text text;
	@FXML ImageView power1;
	@FXML ImageView power2;
	@FXML ImageView power3;
	
	@FXML ImageView defense1;
	@FXML ImageView defense2;
	@FXML ImageView defense3;
	
	@FXML ImageView speed1;
	@FXML ImageView speed2;
	@FXML ImageView speed3;
	ImageView[] power_images = {power1, power2, power3};
	Image squareRed = new Image("UI/squareRed.png");
	boolean F = true;
	Character gamePlayer;
	
	public void setChar(Character player) {
		gamePlayer = player;
	}
	
//	powerStat.setStyle("  -fx-border-style: none; -fx-border-width: 2px; -fx-border-insets: 0; -fx-font-size:4px; -fx-background-image: url('image.png')");
	/**
	 * This is the button click handler, it manages button clicks
	 * @param evt This is the event parameter that is placed into the method
	 */
	public void buttonClickHandler(ActionEvent evt) {
		Button clickedButton = (Button) evt.getTarget();
		String buttonLabel = clickedButton.getText();
		
		if(clickedButton == powerStat) {  //This section checks if the power stat was pressed
			if(gamePlayer.power < 3 && gamePlayer.abilityPoints > 0) {
				gamePlayer.power += 1;
				gamePlayer.abilityPoints -= 1;
				System.out.println(gamePlayer.power);
				if(gamePlayer.power == 1) {
					power1.setImage(squareRed);
				}
				else if(gamePlayer.power == 2) {
					power2.setImage(squareRed);
				}
				else if(gamePlayer.power == 3) {
					power3.setImage(squareRed);
				}
				
			}
			
		}
		
		//this section checks if defense
		if(clickedButton == defenseStat) {
			if(gamePlayer.defense < 3 && gamePlayer.abilityPoints > 0) {
				gamePlayer.defense += 1;
				gamePlayer.abilityPoints -= 1;
				System.out.println(gamePlayer.defense);
				if(gamePlayer.defense == 1) {
					defense1.setImage(squareRed);
				}
				else if(gamePlayer.defense == 2) {
					defense2.setImage(squareRed);
				}
				else if(gamePlayer.defense == 3) {
					defense3.setImage(squareRed);
				}
				
			}
		}
		//this section checks speed
		if(clickedButton == speedStat) {
			if(gamePlayer.speed < 3 && gamePlayer.abilityPoints > 0) {
				gamePlayer.speed += 1;
				gamePlayer.abilityPoints -= 1;
				System.out.println(gamePlayer.speed);
				if(gamePlayer.speed == 1) {
					speed1.setImage(squareRed);
				}
				else if(gamePlayer.speed == 2) {
					speed2.setImage(squareRed);
				}
				else if(gamePlayer.speed == 3) {
					speed3.setImage(squareRed);
				}
				
			}
		}
		
	}
	
	
	
	/**
	 * This is the stats manager, it updates the amount of stats live
	 */
	public void statsManager() {
		  Thread thread = new Thread(() -> {  //this manages the amount of stats that the character has, plus updating them regularyl
			  while(F) {
		            try {
		                Thread.sleep(5);
		            } catch (InterruptedException exc) {
		                throw new Error("Unexpected interruption", exc);
		            }
		            Platform.runLater(() -> text.setText(Integer.toString(gamePlayer.abilityPoints)));
		            //System.out.println(gamePlayer.power);
			  }
	        });
	        thread.setDaemon(true);
	        thread.start();
	}    
		
	
}
