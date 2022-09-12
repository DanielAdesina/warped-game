package shadowGame;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class HealthBar {
	double x;
	double y;
	int health = 10;
	GraphicsContext gc;
	@FXML Canvas canvas;
	Enemy enemy;
	Image[] healthBar = new Image[11];
	/**
	 * This is the healthbar constructor, it creates an instance of the healthbar class
	 * @param gc This is for the GraphicsContext to allow drawing on the canvas
	 * @param canvas This is the canvas, and FXML object that is linked to the .fxml file as an ID for the canvas
	 * @param x an x position
	 * @param y a y position
	 * @param health The amount of health
	 */
	public HealthBar(GraphicsContext gc, Canvas canvas, double x, double y, int health) {
		super();
		this.gc = gc;
		this.canvas = canvas;
		this.x = x - 13;
		this.y = y - 30;
		this.health = health;
		for(int i = 0; i < healthBar.length; i ++) {
			String stringIndex = Integer.toString(i);
			String imageName = "health_bar - Copy/health_" + stringIndex + ".png";
			healthBar[i] = new Image(imageName);
		}
	}
	/**
	 * This sets the amount of health
	 * @param amount an integer that sets the amount of health
	 */
	public void setHealth(int amount) {
		this.health = amount;
	}
	
	/**
	 * Drawing an updated healthbard
	 * @param amount draws it based on the give integer amount
	 */
	public void update(int amount) {
		this.gc.drawImage(this.healthBar[amount], this.x, this.y);
	}
}
