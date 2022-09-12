package shadowGame;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
/**
 * This is a Level class, it is meant to draw out all of the level items oto the gc
 * @author Daniel Adesina
 * @version 2.0
 * @since 20th December 2018    
 */
public class Level {
	double x = 0;
	double y = 0;
	double x2 = 0;
	double blockPosX = 0;
	int centralDx = 0;
	boolean movingRight = true;
	boolean movingLeft = true;
	GraphicsContext gc;
	@FXML Canvas canvas;
	// This block of code creates all the images onto their respective variable holders
	Image background = new Image("true_background.png");
	Image platforms = new Image("temporary_test.png");
	Image block1 = new Image("block1.png");
	Image raw_level = new Image("raw_level_background.png");
	Image ceiling = new Image("ceiling.png");
	Image first_ground = new Image("first_ground.png");
	Image first_ceiling = new Image("first_ceiling.png");
	Image top_right_ceiling = new Image("top_right_ceiling.png");
	Image first_portal = new Image("Starting_Entrance.png");
	Image pillar_left = new Image("pillar-left.png");
	Image first_side = new Image("first_side.png");
	Image bottom_left_ceiling = new Image("bottom_left_ceiling.png");
	Image bottom_left_ground = new Image("bottom_left_ground.png");
	Image bottom_right_ground = new Image("bottom_right_ground.png");
	Image pillar = new Image("upright_pillar.png");
	Image second_wall = new Image("second_wall.png");
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
	int[] octopus_point_1 = {900, 250};
	int[] octopus_point_2 = {900, 250};
	int[][] octopus_points = {octopus_point_1};
	
	public Level(GraphicsContext gc, Canvas canvas) {
		super();
		this.gc = gc;
		this.canvas = canvas;	
		this.x2 = this.background.getWidth();
		//This populates the arrays with all the items necessary to fill the level (including all the enemies)
		powerUps.add(new PowerUp(this.gc, this.canvas, 1000, 100));
		powerUps.add(new PowerUp(this.gc, this.canvas, 700, 300));
		powerUps.add(new PowerUp(this.gc, this.canvas, 600, 600));
		enemies.add(new Enemy(gc, canvas, this.centralDx, 1, false, 600, 275));
		enemies.add(new Enemy(gc, canvas, this.centralDx, 2, false, 800, 730));
		enemies.add(new Enemy(gc, canvas, this.centralDx, 0, false, 900, 250));
		enemies.add(new Enemy(gc, canvas, this.centralDx, 0, false, 2000, 250));
		
	}
	
	/**
	 * There is a centralDx to implement sidescrolling, this method causes it to move by a set amount to the left
	 * @param amount An integer amount to decide the dx
	 */
	public void movingRight(int amount) {
		if(movingRight == false) {
			this.centralDx = 0;
		}
		else {
			this.centralDx = -amount;
		}
	}
	
	/**
	  * There is a centralDx to implement sidescrolling, this method causes it to move by a set amount to the left 
	 * @param amount An integer amunt to decide the dx
	 */
	public void movingLeft(int amount) {
		if(movingLeft == false) {
			this.centralDx = 0;
		}
		else {
			this.centralDx = amount;
		}
	}
	
	/**
	 * Returns whether it is moving right or not
	 * @return movingRight a boolean
	 */
	public boolean moveValueRight() {
		return movingRight;
	}
	
	/**
	 * Returns whether it is moving left or not
	 * @return movingRight a boolean
	 */
	public boolean moveValueLeft() {
		return movingLeft;
	}
	
	/**
	 * This is an update loop, this causes the level to scroll and draws the images onto the screen
	 */
	public void update() {
		if(this.x >= -10) {
			this.movingLeft = false;
		}
		else {
			this.movingLeft = true;
		}
		//this stops the level from scrolling past necessary
		if(this.x + this.background.getWidth() <= this.canvas.getWidth()){
			this.movingRight = false;
		}
		else {
			this.movingRight = true;
		}
		//This draws all the level aspects onto the screen
		this.gc.drawImage(this.background, this.x, this.y);
		this.gc.drawImage(this.raw_level, this.x, this.y);
		this.gc.drawImage(this.first_ground, this.x + 246, this.y + 321);
		this.gc.drawImage(this.first_ground, this.x + 1674, this.y + 321);
		this.gc.drawImage(this.first_portal, this.x, this.y + 123);
		this.gc.drawImage(this.ceiling, this.x + 232, this.y + 30);
		this.gc.drawImage(this.first_ceiling, this.x + 820, this.y + 20);
		this.gc.drawImage(this.pillar_left, this.x + 1369, this.y + 360);
		this.gc.drawImage(this.first_side, this.x + 1346, this.y + 330);
		this.gc.drawImage(this.bottom_left_ceiling, this.x, this.y + 450);
		this.gc.drawImage(this.bottom_left_ceiling, this.x + 1670, this.y + 450);
		this.gc.drawImage(this.bottom_left_ground, this.x, this.y + 762);
		this.gc.drawImage(this.top_right_ceiling, this.x + 1338, this.y);
		this.gc.drawImage(this.bottom_right_ground, this.x + 1554, this.y + 762);
		this.gc.drawImage(this.pillar, this.x + 1492, this.y + 680);
		this.gc.drawImage(this.second_wall, this.x + 1668, this.y + 330);
		//this causes the level to scroll
		this.x += centralDx;
		for(int i = 0; i < powerUps.size(); i ++) {
			powerUps.get(i).update();
			powerUps.get(i).x += centralDx;
		}
		//updates the enemies in the arry
		for(int i = 0; i < enemies.size(); i ++) {
			enemies.get(i).x += centralDx;
			enemies.get(i).original_x += centralDx;
			enemies.get(i).healthbar.x += centralDx;
			for(int j = 0; j < enemies.get(i).bulletList.size(); j ++){
				//if(enemies.get(i).type == 0){System.out.println("OCTOPUS" + enemies.get(i).x + ", " + enemies.get(i).y);}
				enemies.get(i).bulletList.get(j).x += centralDx;
			}
			//enemies.get(i).attackChar(targetX);
			enemies.get(i).update();
		}
		this.blockPosX += centralDx;
		
	}
	
	
}
