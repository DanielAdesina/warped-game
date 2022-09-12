package shadowGame;


import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
/**
 * This is a PowerUp class, it is meant to control the creation of powerups
 * @author Daniel Adesina
 * @version 2.0
 * @since 20th December 2018    
 */

public class PowerUp {
	double x;
	double y;
	Image[] powerAnimation = new Image[7];
	Image currentImage;
	int swivelTime = 0;
	int coin_Index = 0;
	@FXML Canvas gameCanvas;
	GraphicsContext gc;
	
	
	/**
	 * A constructor for the power up class
	 * @param gc This is for the GraphicsContext to allow drawing on the canvas
	 * @param gameCanvas This is the canvas, and FXML object that is linked to the .fxml file as an ID for the canvas
	 * @param x A double for the x point
	 * @param y A double for y point
	 */
	public PowerUp(GraphicsContext gc, Canvas gameCanvas, double x, double y) {
		super();
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		this.x = x;
		this.y = y;
		
		for(int i = 0; i < powerAnimation.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "power-up - Copy/power-up-" + stringIndex + ".png";
			powerAnimation[i] = new Image(imageName);
		}
		this.currentImage = powerAnimation[0];

	}
	
	
	/**
	 * An update loop
	 */
	public void update() {
		if(this.swivelTime == 5) {
			this.swivelTime = 0;
			if(this.coin_Index < 6) {
				this.coin_Index += 1;
				
			}
			else {
				this.coin_Index = 0;
			}
		}
		else {
			this.swivelTime += 1;
		}
		
		this.gc.drawImage(this.powerAnimation[this.coin_Index], this.x, this.y);
		 
	   
		
		
	
		
	}
}