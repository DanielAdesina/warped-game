package shadowGame;

import java.io.File;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.management.timer.Timer;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;



/**
 * This is a GameScreenController class, it is meant to control the management of the game window.
 * @author Daniel Adesina
 * @version 2.0
 * @since 20th December 2018    
 */

public class GameScreenController {
	@FXML Canvas gameCanvas;
	Scene gameScene;
	GraphicsContext gc;  //these are all variables for the stage, controllers and music files
	long lastLoopTime = 0;
	boolean statsOpen = false;
	Stage statsStage;
	StatsController controller;
	Stage pauseStage;
	PauseMenuController pauseController;
	boolean pauseOpen = false;
	String musicFile = "src/music.mp3";
	Media sound = new Media(new File(musicFile).toURI().toString());
	MediaPlayer mediaPlayer = new MediaPlayer(sound);
	
	public void getScene(Stage gameStage) {  //passing parameters
		gameScene = gameStage.getScene();
	}
	
//	public boolean pixelChecker(Character player, Level level) {
//		PixelReader pixelReader = player.currentImage.getPixelReader();
//		int pixel = pixelReader.getArgb((int) (player.x), (int) player.y);
//		if((pixel >> 24) == 0x00) {
//			player.isTransparent = true;
//		}
//		else {
	
//			player.isTransparent = false;
//		}
//		if(player.isTransparent){
//			System.out.println(player.x);
//		}
//		return player.isTransparent;
//		
//	}
	
	
	public boolean collisionDetection(double image1_x, double image1_y, Image image1, double image2_x, double image2_y, Image image2) {
		if (image2_y + image2.getHeight() > image1_y && image2_y < image1_y + image1.getHeight()) {  //this is checking if the y added to the height is within the
			if (image2_x + image2.getWidth() > image1_x && image2_x < image1_x + image1.getWidth()) { //range of the other image, then it checks if the x
				//once added to the width is more than that of the image and if the x hasn't passed the boundaries of the other image
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This is the method for the gameloop
	 */
	public void gameLoop() {
//		mediaPlayer.setStartTime(Duration.seconds(0));
//		mediaPlayer.setStopTime(Duration.seconds(5));
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play(); // this starts the media layer
		System.out.println(mediaPlayer.getStatus());
		ArrayList<String> input = new ArrayList<String>(); //sets arraylist for input
		gc = gameCanvas.getGraphicsContext2D(); //creating a Graphics Context
		PowerUp power = new PowerUp(this.gc, this.gameCanvas, 50, 300);
		Level level = new Level(gc, gameCanvas); //creates level
		Character player = new Character(gc, gameCanvas, level); ///creates player
		try {  //This is the usual fxml loader, loads from the .fxml file
			FXMLLoader loader = new FXMLLoader(getClass().getResource("stats.fxml")); 
			Parent root = (Parent)loader.load(); //Loads up the Border Pane using the .fxml file
			Scene scene = new Scene(root, 200, 200);
			scene.getStylesheets().add(getClass().getResource("stats.css").toExternalForm()); //applies the css to it
			statsStage = new Stage();
			controller = loader.getController(); //passes the controller
			controller.statsManager(); //starts the manager loop
			controller.setChar(player);
			statsStage.initStyle(StageStyle.TRANSPARENT); //sets the stage to be transparent
			scene.setFill(Color.TRANSPARENT);
			statsStage.setScene(scene);
			

			scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>
			
			  () {

			        @Override
			        public void handle(KeyEvent t) {
			          if(t.getCode()==KeyCode.TAB)
			          {
				          statsStage.hide();
				          statsOpen = false;
			          
			          }
			        }
			    });
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {  //This is the usual fxml loader, loads from the .fxml file
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PauseMenu.fxml")); 
			Parent root = (Parent)loader.load(); //Loads up the Border Pane using the .fxml file
			Scene scene = new Scene(root, 1000, 400);
			scene.getStylesheets().add(getClass().getResource("pause.css").toExternalForm()); //applies the css to it
			pauseStage = new Stage();
			pauseController = loader.getController();
			pauseController.setChar(player);
			pauseStage.initStyle(StageStyle.TRANSPARENT);
			scene.setFill(Color.TRANSPARENT);
			pauseStage.setScene(scene);
			Image image = new Image("pauseButton.png");
			//pauseController.resume.setGraphic(new ImageView(image));

			scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>
			
			  () {

			        @Override
			        public void handle(KeyEvent t) {
			          if(t.getCode()==KeyCode.ESCAPE)
			          {
				          pauseStage.hide();
				          pauseOpen = false;
			          
			          }
			        }
			    });
		} catch(Exception e) {
			e.printStackTrace();
		}
		
 //This is a key handler to monitor keys pressed and add them to an input stream
		
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				if(!input.contains(code)) {
					input.add(code);
				}
			}
			
		});
		
		// This checks which keys have been released and removes them from the input stream

		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override 
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				if(input.contains(code)) {
					input.remove(code);
				}
			}
		});
		
		 // An animation timer, this is a JavaFX method that allows one to create an instance of the animation timer to run events in sync
		 // the animation timer contains the handle method, this is the actual method that runs every nanosecond 
		 
		new AnimationTimer() {
			// actual game loop that repeats
			@SuppressWarnings("static-access")
			@Override
			/**
			 * This is a handle function, it comes with the animation timer and is the bit that runs every frame
			 * @param currentNanoTime this is a parameter for the game time
			 */
			
			public void handle(long currentNanoTime) {
				mediaPlayer.setAutoPlay(true);
				if(player.health == 0) {
					this.stop();
					mediaPlayer.stop();
					Stage stage = (Stage) gameScene.getWindow();
					stage.close();
					EndScreenWindow();
				}
				if(player.x > level.x + 1492 && player.x < level.x + 1492 + level.pillar.getWidth() && player.y > level.y + 660) {
					//System.out.println("cool");
					player.x = level.x + 1492;
				}
				//System.out.println(collisionDetection(player.x, player.y, player.currentImage, level.x, level.y, level.platforms)); 
			
				if(input.contains("TAB")) {
					//System.out.println("cool");
					input.remove("TAB");
					statsStage.show();
					statsOpen = true;
				}
				
				if(input.contains("ESCAPE")) {
					//System.out.println("cool");
					input.remove("ESCAPE");
					pauseStage.show();
					pauseOpen = true;
				}

				if(player.y > level.y + 360 && level.y + 450 > 0) {
					level.y -= 10;
					player.physics.setVelY(0);
					player.physics.fall = false;
					for(int i = 0; i < level.powerUps.size(); i ++) {
						level.powerUps.get(i).y -= 10;
					}
					for(int i = 0; i < level.enemies.size(); i ++) {
						level.enemies.get(i).y -= 10;
						level.enemies.get(i).healthbar.y -= 10;
						level.enemies.get(i).physics.velY = 0;
					}
				}
				
				
				// These blocks of code check collision against the level aspects
//				if(collisionDetection(player.x + (player.currentImage.getWidth()/2), player.y + 22, player.currentImage, level.x + 247, level.y + 311, level.first_ground)) {
//					player.isFalling = false;
//					player.physics.setVelY(0);
//					player.y = level.y + 311 - player.currentImage.getHeight() + 22;
//					
//				}
				else if(collisionDetection(player.x + (player.currentImage.getWidth()/2), player.y + 22, player.currentImage, level.x + 1674, level
						.y + 311, level.first_ground)) {
					player.isFalling = false;
					player.physics.setVelY(0);
					player.y = level.y + 311 - player.currentImage.getHeight() + 22;
				}
				else if(collisionDetection(player.x + (player.currentImage.getWidth()/2), player.y + 22, player.currentImage, level.x, level.y + 762, level.bottom_left_ground)) {
					player.isFalling = false;
					player.physics.setVelY(0);
					player.y = level.y + 762 - player.currentImage.getHeight() + 22;
				}
				else if(collisionDetection(player.x + (player.currentImage.getWidth()/2), player.y + 22, player.currentImage, level.x + 1554, level.y + 762, level.bottom_right_ground)) {
					player.isFalling = false;
					player.physics.setVelY(0);
					player.y = level.y + 762 - player.currentImage.getHeight() + 22;
				}
				else if(collisionDetection(player.x, player.y + 22, player.currentImage, level.x + 1492, level.y + 660, level.pillar)) {
					if(player.y + player.currentImage.getHeight() < level.y + 660) {
						player.physics.setVelY(0);
						player.y = level.y + 660 - player.currentImage.getHeight() + 22;
					}
					player.isFalling = false;
					
				}
				else if(collisionDetection(player.x, player.y, player.currentImage, level.x + 1369, level.y + 360, level.pillar_left)
						&& player.x + player.currentImage.getWidth() < level.x + 1369 + level.pillar_left.getWidth()) {
					player.isFalling = false;
					player.physics.setVelY(0);
					player.y = level.y + 360 - player.currentImage.getHeight() + 10;
					
					
				}
				
				else {
					//System.out.println(player.y);
					player.isFalling = true;
					player.physics.gravity(2);
				}
				gc.clearRect(0, 0,gameCanvas.getWidth(), gameCanvas.getHeight());
				player.setInput(input);
				level.update();
				player.update();
				//updates the powerups throughout the level
				for(int i = 0; i < level.powerUps.size(); i ++) {
					if(collisionDetection(player.x, player.y, player.currentImage, level.powerUps.get(i).x,
							level.powerUps.get(i).y, level.powerUps.get(i).currentImage)) {
						level.powerUps.remove(i);
						player.abilityPoints += 1;
						//System.out.println("H U R T");
					}
				}
				//this updates the level.enemies
				for(int i = 0; i < level.enemies.size(); i ++) {
					level.enemies.get(i).attackChar(player.x); //this sets the target to the player
					for(int j = 0; j < level.enemies.get(i).bulletList.size(); j ++) {
						if(collisionDetection(player.x, player.y, player.currentImage, level.enemies.get(i).bulletList.get(j).x,
								level.enemies.get(i).bulletList.get(j).y, level.enemies.get(i).bulletList.get(j).currentImage)) {
							player.health -= 1;
							player.hurt = true;
							level.enemies.get(i).bulletList.remove(j);
							//System.out.println("H U R T");
						}
						else {
							level.enemies.get(i).bulletList.get(j).update();
							//level.enemies.get(i).attackChar(player.x);
						}
						
					}
					//if the character collides with enemies
					if(collisionDetection(player.x, player.y, player.currentImage, level.enemies.get(i).x, level.enemies.get(i).y, level.enemies.get(i).currentImage)) {
						level.enemies.get(i).inContact = true;
						if(player.x > level.enemies.get(i).x + (level.enemies.get(i).currentImage.getWidth()/2)) {
							player.direction = false;
						}
						else {
							player.direction = true;
						}
						
						if(level.enemies.get(i).attackMode) {
						
							//level.enemies.get(i).attackMode = false;
							//level.enemies.get(i).physics.setVelX(0);
							//level.enemies.get(i).returnHome();
						}
						player.health -= 1;
						player.hurt = true;
						//System.out.println("H U R T");
						
					}
					else {
						level.enemies.get(i).inContact = false;
					}
					
				}
				//if the player bullets collides with the enemy, it 
				for(int j = 0; j < player.bullets.size(); j++) {
					player.bullets.get(j).update();
					if(player.bullets.get(j).impact_Index == 2) {
						player.bullets.remove(j);
					}
					else if(player.bullets.get(j).x > gameCanvas.getWidth() - player.bullets.get(j).currentImage.getWidth()) {
						player.bullets.remove(j);
					}
					else {
						for(int k = 0; k < level.enemies.size(); k ++) {
							if(level.enemies.get(k).death_Index == 5) {
								level.enemies.remove(k);
								int tempCurr = Integer.parseInt(player.currency);
								tempCurr += 10;
								player.currency = Integer.toString(tempCurr);
							}
							else {
								if(collisionDetection(player.bullets.get(j).x, player.bullets.get(j).y, player.bullets.get(j).currentImage, level.enemies.get(k).x,
										level.enemies.get(k).y, level.enemies.get(k).currentImage)) {
									player.bullets.get(j).collide = true;
									if(level.enemies.get(k).health > 1) {
										level.enemies.get(k).health -= (1*player.power + 1);
										//System.out.println(level.enemies.get(k));
									}
									else {
										level.enemies.get(k).alive = false;
									}
								}
								else {
									if(player.bullets.get(j).x > gameCanvas.getWidth()) {
										player.bullets.remove(j);
									}
								}		
							}
						}
					}
				}
				//System.out.println(pixelChecker(player, level));
				
				
				
				
				
				
			}
		}.start(); 
	}
	
	
	
	private void EndScreenWindow() {
		try {  //This is the usual fxml loader, loads from the .fxml file
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EndScreen.fxml")); 
			AnchorPane root = (AnchorPane)loader.load(); //Loads up the Border Pane using the .fxml file
			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("endscreen.css").toExternalForm()); //applies the css to it
			Stage primaryStage = new Stage();
			primaryStage.initStyle(StageStyle.TRANSPARENT); //making it invisible
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
