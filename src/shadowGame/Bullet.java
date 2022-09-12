package shadowGame;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This is a Bullet class, it is meant to control the creation of bullets
 * @author Daniel Adesina
 * @version 2.0
 * @since 20th December 2018    
 */
public class Bullet {
	double x;   //these are the x and y co-ordinates
	double y;
	double dx;  //this is the dx, the amount in which the x changes amounts
	boolean direction; //a direction variable, this is meant to decide whether the dx is positive or negative
	boolean collide; //checks if it has collided
	Image[] shotAnimation = new Image[3];  //these are the Image lists for animation
	Image[] impactAnimation = new Image[6];
	Image[] gravityGunAnimation = new Image[18];
	Level level; //Level placeholder
	int shotTime = 0; //Indexes and times for animation
	int shot_Index = 0;
	int impactTime = 0;
	int impact_Index = 0;
	int gravityTime = 0;
	int gravity_Index = 0;
	boolean type = true;
	boolean gravGun = false;
	Image currentImage;
	GraphicsContext gc; //Graphics Context and Canvas placeholders
	@FXML Canvas canvas;
	
	/**
	 * This is a constructor for the Bullet Class, it passes it multiple values.
	 * @param gc This is for the GraphicsContext to allow drawing on the canvas
	 * @param canvas This is the canvas, and FXML object that is linked to the .fxml file as an ID for the canvas
	 * @param level This is the level, this is where everything is drawn and the centralDx is decided
	 * @param x A double for the x point
	 * @param y A double for y point
	 * @param direction a boolean for direction
	 */
	public Bullet(GraphicsContext gc, Canvas canvas, Level level, double x, double y, boolean direction) {
		super();
		this.gc = gc;
		this.canvas = canvas;	
		this.x = x;
		this.y = y;
		
		this.level = level;
		this.direction = direction;
		//This is the section of code in which all images that reuqire animation are loaded in
		//I do this by creating an array of possible keynote frames of animation from the source folders located in the /src destination
		// I do this for every type of animation, this takes up less memory than it otherwise would doing them from a sprite sheet
		for(int i = 0; i < shotAnimation.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "shot - Copy/shot-" + stringIndex + ".png";
			shotAnimation[i] = new Image(imageName);
		}
		for(int i = 0; i < impactAnimation.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "impact/impact-" + stringIndex + ".png";
			impactAnimation[i] = new Image(imageName);
			this.currentImage = shotAnimation[0];
		}
	}
	
	 /**
     * a method to get x
     * @return x
     */
    public double getX() {
        return x;
    }
    /**
     * a method to set the x value of the portal
     * @param x a double type x to set the value of
     */
    public void setX(double x) {
        this.x = x;
    }
    /**
     * a method to return y
     * @return y
     */
    public double getY() {
        return y;
    }
    /**
     * a method to set the y value of the portal
     * @param y takes a double to set y as
     */
    public void setY(double y) {
        this.y = y;
    }
	
	/**
	 * This is a constructor for the Bullet Class, it passes it multiple values.
	 * @param gc This is for the GraphicsContext to allow drawing on the canvas
	 * @param canvas This is the canvas, and FXML object that is linked to the .fxml file as an ID for the canvas
	 * @param x A double for the x point
	 * @param y A double for y point
	 * @param direction a boolean for direction
	 * @param name A string value to open up the corresponding image, rather than preset animation frames
	 */
	public Bullet(GraphicsContext gc, Canvas canvas, double x, double y, boolean direction, String name, double dx) {
		super();
		this.gc = gc;
		this.canvas = canvas;	
		this.x = x;
		this.y = y;
		this.type = false;
		this.dx = dx;
		this.currentImage = new Image(name);
		
		
		
	}
	
	/**
	 * This is a constructor for the Bullet Class, it passes it multiple values.
	 * @param gc This is for the GraphicsContext to allow drawing on the canvas
	 * @param canvas This is the canvas, and FXML object that is linked to the .fxml file as an ID for the canvas
	 * @param x A double for the x point
	 * @param y A double for y point
	 * @param direction a boolean for direction
	 */
	public Bullet(GraphicsContext gc, Canvas canvas, Level level, double x, double y, boolean direction, double dx) {
		super();
		this.gc = gc;
		this.canvas = canvas;	
		this.x = x;
		this.y = y;
		this.type = false;
		this.dx = dx;
		this.gravGun = true;
		this.level = level;
		for(int i = 0; i < gravityGunAnimation.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "gravityBall/frame_" + stringIndex + "_delay-0.03s.gif";
			gravityGunAnimation[i] = new Image(imageName);
			this.currentImage = gravityGunAnimation[0];
		}
		
		
		
	}
	
	
	/**
	 * This is the update method, it is the backbone of all the classes in this game
	 * Modelled after the natural sprite update loop in pygame, it updates the animation, and collision checking of the bullet
	 */
	public void update() {
		if(this.type) {  //Checking if it is the first type, the type that needs animation
			if(!collide) {
				if(this.direction) {  //deciding direction
					this.dx = 8 + this.level.centralDx; //Adding the level dx to it to simulate scrolling
				}
				else {
					this.dx = -8 + this.level.centralDx;
				}
				if(this.shotTime == 5) { //This checks if the shot Time is equal to 5, if in the case that it is is add one to the index(if index is not the size
					this.shotTime = 0;  //of the image list, when it does this it spaces out the rate at which the animation changes
					if(this.shot_Index < 2) {
						this.shot_Index += 1;
						
					}
					else {
						this.shot_Index = 0;
					}
					
				}
				else {
					this.shotTime += 1;
				}
				this.currentImage = this.shotAnimation[this.shot_Index];
				this.gc.drawImage(this.shotAnimation[this.shot_Index], this.x, this.y);
	
			}
			else {
				this.dx = 0;
				if(this.impactTime == 5) {   //This checks if the shot Time is equal to 5, if in the case that it is is add one to the index(if index is not the size
					this.impactTime = 0;	 //of the image list, when it does this it spaces out the rate at which the animation changes
					if(this.impact_Index < 2) {
						this.impact_Index += 1;	//This is a different if loop, it checks if the collide variable is true, in that case it switches to the 
												//impactAnimation list
					}
					else {
						this.impact_Index = 0;
					}
				}
				else {
					this.impactTime += 1;
				}
				this.currentImage = this.impactAnimation[this.impact_Index];
				this.gc.drawImage(this.impactAnimation[this.impact_Index], this.x, this.y);
			}
		}
		else if(!this.type && !this.gravGun) {
			this.gc.drawImage(this.currentImage, x, y);
		}
		else if(!this.type && this.gravGun) {
			if(this.direction) {  //deciding direction
				this.dx = 8 + this.level.centralDx; //Adding the level dx to it to simulate scrolling
			}
			else {
				this.dx = -8 + this.level.centralDx;
			}
			if(this.gravityTime == 5) { //This checks if the shot Time is equal to 5, if in the case that it is is add one to the index(if index is not the size
				this.gravityTime = 0;  //of the image list, when it does this it spaces out the rate at which the animation changes
				if(this.gravity_Index < 16) {
					this.gravity_Index += 1;
					
				}
				else {
					this.gravity_Index = 0;
				}
				
			}
			else {
				this.gravityTime += 1;
			}
			this.currentImage = this.gravityGunAnimation[this.gravity_Index];
			this.gc.drawImage(this.gravityGunAnimation[this.gravity_Index], this.x, this.y);

		}
		this.x += this.dx;
	}

}
