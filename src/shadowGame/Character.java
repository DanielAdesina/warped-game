package shadowGame;


import java.io.File;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * This is a Character class, it is meant to control the creation of the player
 * @author Daniel Adesina
 * @version 2.0
 * @since 20th December 2018    
 */
public class Character {
	double x = 500; //This is a variable for the x position and y position
	double y = 235;
	Physics physics = new Physics(x, y); // This is a n instance of the physics class
	Point2D[] points = new Point2D[4];
	String currency = "0"; //  A string value for the amount of currency contained
	boolean isTransparent; //An unused isTransparent variable for the pixel Reader
	boolean isFalling = true; //A variable to check whether the character is in the midst of falling
	boolean hurt = false; //Checks if the character has collied with any hostile objects
	int abilityPoints = 0; // These are the stats of the character, with ability Points the currency in which one updgrades their stats
	int power = 0;
	int defense = 0;
	int speed = 0;
	int health = 332;
	int maxHealth = 524; // These are the health variables for the character
	int laserCharge = 0; //These are the laser charge variables
	int maxLaserCharge = 500; 
	boolean laserUnlocked = false;
	boolean gravityGunUnlocked = false;
	boolean missileUnlocked = false;
	boolean parryUnlocked = false;
	String soundTrack = "src/laser_effect.wav";
    AudioClip laserSoundClip = new AudioClip((new File(soundTrack).toURI().toString()));
	@FXML Canvas gameCanvas; //Canvas and GraphicsContext placeholders, with the canvas having and fxml id
	GraphicsContext gc;
	String left = "A"; //Strings for keyboard manipulation
	String right = "D";
	String up = "W";
	String down = "S";
	String space = "SPACE";
	Scene gameScene; //The game scene for passing parameters
	Image healthBar = new Image("UI/player_health.png");  // Loading the separate images for the components of the healthbar 
	Image healthBar_mid = new Image("UI/healthBar_mid.png");
	Image healthBar_left = new Image("UI/healthBar_left.png");
	Image healthBar_right = new Image("UI/healthBar_right.png");
	Image[] coinAnimation = new Image[8];  // Creating all the arrays for storing all the possible images for keyframe animations 
	Image[] idle_right = new Image[6];
	Image[] idle_left = new Image[6];
	Image[] walking_right = new Image[9];
	Image[] walking_left = new Image[9];
	Image[] attack_right = new Image[21];
	Image[] attack_left = new Image[21];
	Image[] jump_right = new Image[8];
	Image[] jump_left = new Image[8];
	Image[] stand_right = new Image[4];
	Image[] stand_left = new Image[4];
	Image[] walkAttack_right = new Image[11];
	Image[] walkAttack_left = new Image[11];
	Image[] hurt_right = new Image[3];
	Image[] hurt_left = new Image[3];
	Image[] laser = new Image[11];
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();  //Creating an arraylist for the manaagement of bullets
	TimedEvent bulletFire = new TimedEvent(25); // Timed Events to stop the using of abilities with impunity
	TimedEvent laserFire = new TimedEvent(1000);
	boolean direction = true; // a boolean for direction and checking whether the laser has been fired
	boolean laserFired = false;
	Level gameLevel; //This is a placeholder for the level value, this is for the sake of passing parameters
	Image duckImage = new Image("player-duck.png"); // loading in the images for the player crouch positions
	Image duckImageLeft = new Image("player-duck-left.png");
	Image currentImage; //This is a placeholder for the current image value
	ArrayList<String> input; // An arraylist for the player input
	PowerUp abilityPointIndicator; // Creating an instance of the powerup class to allow for a display
	int swivelTime = 0; //These are the indexes and time value for the animation
	int coin_Index = 0;
	int hurtTime = 0;
	int walkTime = 0;
	int attackTime = 0;
	int idleTime = 0;
	int jumpTime = 0;
	int standTime = 0;
	int laserTime = 0;
	int walkAttackTime = 0;
	int hurt_rightIndex = 0;
	int hurt_leftIndex = 0;
	int laser_Index;
	int walkingAttack_rightIndex = 0;
	int walkingAttack_leftIndex = 0;
	int walking_rightIndex = 0;
	int walking_leftIndex = 0;
	int stand_rightIndex = 0;
	int stand_leftIndex = 0;
	int attack_leftIndex = 0;
	int attack_rightIndex = 0;
	int idle_leftIndex = 0;
	int idle_rightIndex = 0;
	int jump_rightIndex = 0;
	int jump_leftIndex = 0;
	
	/**
	 * This is a constructor for the character class, it loads in all the images and initializes the gameCanvas, level and gc
	 * @param gc This is for the GraphicsContext to allow drawing on the canvas
	 * @param gameCanvas This is the canvas, and FXML object that is linked to the .fxml file as an ID for the canvas
	 * @param level This is the level, this is where everything is drawn and the centralDx is decided
	 */
	public Character(GraphicsContext gc, Canvas gameCanvas, Level level) {
		super();
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		this.gameLevel = level;
		laserSoundClip.setVolume(0.3);
		this.laserFire.end(); // This stops the laserFire timer so as to allow for accuate cooldowns
		abilityPointIndicator = new PowerUp(this.gc, this.gameCanvas, 0, 80);
		//This is the section of code in which all images that reuqire animation are loaded in
		//I do this by creating an array of possible keynote frames of animation from the source folders located in the /src destination
		// I do this for every type of animation, this takes up less memory than it otherwise would doing them from a sprite sheet
		// WALKING ------------------------------------------------------------------
		for(int i = 0; i < walking_right.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "player-run/player-run-" + stringIndex + ".png";
			walking_right[i] = new Image(imageName);
		}
		for(int i = 0; i < walking_left.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "player-run-left - Copy/player-run-" + stringIndex + ".png";
			walking_left[i] = new Image(imageName);
			
		}
	// JUMPING ------------------------------------------------------------------
		for(int i = 0; i < jump_right.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "player-jump - Copy/player-jump-" + stringIndex + ".png";
			jump_right[i] = new Image(imageName);
		}
		for(int i = 0; i < jump_left.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "player-jump-left - Copy/player-jump-" + stringIndex + ".png";
			jump_left[i] = new Image(imageName);
			
		}
	// ATTACKING -----------------------------------------------------------------
		for(int i = 0; i < stand_right.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "player-stand-right/player-stand-" + stringIndex + ".png";
			stand_right[i] = new Image(imageName);
		}
		for(int i = 0; i < stand_left.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "player-stand-left/player-stand-" + stringIndex + ".png";
			stand_left[i] = new Image(imageName);
			
		}
		for(int i = 0; i < walkAttack_right.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "player-run-shot/player-run-shot-" + stringIndex + ".png";
			walkAttack_right[i] = new Image(imageName);
		}
		for(int i = 0; i < walkAttack_left.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "player-run-shot-left/player-run-shot-" + stringIndex + ".png";
			walkAttack_left[i] = new Image(imageName);
		}
		// IDLE ----------------------------------------------------------------------
		for(int i = 0; i < idle_right.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "player-idle - Copy - Copy/player-idle-" + stringIndex + ".png";
			idle_right[i] = new Image(imageName);
		}
		for(int i = 0; i < idle_left.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "player-idle-left/player-idle-" + stringIndex + ".png";
			idle_left[i] = new Image(imageName);
		}
		for(int i = 0; i < hurt_right.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "player-hurt/player-hurt-" + stringIndex + ".png";
			hurt_right[i] = new Image(imageName);
		}
		for(int i = 0; i < hurt_left.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "player-hurt-left/player-hurt-" + stringIndex + ".png";
			hurt_left[i] = new Image(imageName);
		}
		for(int i = 0; i < laser.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "Laser/charge" + stringIndex + ".png";
			laser[i] = new Image(imageName);
		}
		for(int i = 0; i < coinAnimation.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "CoinAnimation/" + stringIndex + ".gif";
			coinAnimation[i] = new Image(imageName);
		}
		this.currentImage = idle_right[0];

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
     * A method to set the input to one passes onto it
     * @param input gets the input array list from the controller
     */
	public void setInput(ArrayList<String> input) {
		this.input = input;
	}
	
	/**
	 * This is a method to return the current input list
	 * @param input takes the input array list from the controller and passes it on
	 * @return the array list
	 */
	public ArrayList<String> getInput(ArrayList<String> input) {
		return input;
	}
	
	
	/**
	 * This is the method in which where everything happens, this allows for all the animations to occur once called
	 */
	public void update() {
		
	    //This is the animation section for the amount of currency
		if(this.swivelTime == 5) { //checks if the timer is more than a certain amount, if it is then it adds to the index of 
			this.swivelTime = 0; //animations as long it is below the amount of animations in htat Image list
			if(this.coin_Index < 7) {
				this.coin_Index += 1;
				
			}
			else {
				this.coin_Index = 0;
			}
		}
		else {
			this.swivelTime += 1;
		}
		
		this.gc.drawImage(this.coinAnimation[this.coin_Index], 0, 50); //this draws it all onto the screen based off the current Index that is being added to 
		 this.gc.setFont(Font.font("Kenney Mini",FontWeight.BOLD,18));//Setting the font type for this occasion
	    this.gc.setFill(Color.WHITE);//Setting the colour fill
	    this.gc.fillText("x" + currency, 30, 75);//Drawing the text onto the screen
		
	    //If the cooldown timer updates, stop it and make it able to fire another laser
		if(this.laserFire.update()) {
			laserFired = false;
			this.laserFire.end();
			//System.out.println("cooldown is up pals");
		}
		
		//this draw the sections for the healthbar depending on the amount of health left
		if(this.health > 0) {
			this.gc.drawImage(this.healthBar_left, 0, 0);
		}
		if(this.health == 332) {
			this.gc.drawImage(this.healthBar_right, 326, 0);
		}
		for(int i = 0; i < (this.health - 12)/16; i ++) {
			this.gc.drawImage(this.healthBar_mid, 6 + i*16, 0);
		}
		if(this.input.contains("MINUS")) {
			this.health -= 1;
		}
		
		//If neither of the right or left keys are being pressed, set the velocity in x to zero
		if(!this.input.contains(right) || !this.input.contains(left)) {
			this.physics.setVelX(0);
		}
		
		//If the character has been hurt
		if(this.hurt) {
			//It goes through the animation rames for being hurt
			if(this.hurtTime == 5) {
				this.hurtTime = 0;
				if(this.direction) { //depending on the direction the player was moving, it makes the animation play depending on that
					if(this.hurt_rightIndex < 2) {
						this.hurt_rightIndex += 1;
						
					}
					else {
						this.hurt_rightIndex = 0;
						this.hurt = false;
					}
				}
				else {
					if(this.hurt_leftIndex < 2) {//depending on the direction the player was moving, it makes the animation play depending on that
						this.hurt_leftIndex += 1;
						
					}
					else {
						this.hurt_leftIndex = 0;
						this.hurt = false;
					}
				}
				
			}
			else {
				this.hurtTime += 1;
			}
			//based on the direction and the index in question, it draw that animation frame onto the screen
			if(this.direction) {
				currentImage = hurt_right[this.hurt_rightIndex];
				this.gc.drawImage(this.hurt_right[this.hurt_rightIndex], this.x, this.y);
				this.physics.addVelX(-2);
			}
			else {
				currentImage = hurt_left[this.hurt_leftIndex];
				this.gc.drawImage(this.hurt_left[this.hurt_leftIndex], this.x, this.y);
				this.physics.addVelX(2);
			}
			
			
		}
		//if the character is falling
		if(this.isFalling) {
			if(this.input.contains(right)) {  //this is to allow for movement while mid-air, it checks for another key press in the even of the charactr
				this.direction = true; // already falling
				if (this.x > (1000/1.8)) {
					if (this.gameLevel.moveValueRight() == false) {  //if the x is more than a certain point, it will stop scrolling the level and start moving the player
						this.gameLevel.movingRight(0);
						this.physics.setVelX(6);
					}
					else { //otherwise, the level is the one that keeps scrolling
						this.physics.setVelX(0);
						this.gameLevel.movingRight(6);
					}
					
				}
				else {
					this.gameLevel.movingRight(0);
					this.physics.setVelX(6);
				}
			}
			//the left side input does the exact same but with the left
			else if(this.input.contains(left)) {
				this.direction = false;
				if (this.x < (1000/1.8) - 200) {
					if (this.gameLevel.moveValueLeft() == false) {
						this.gameLevel.movingLeft(0);
						this.physics.setVelX(-6);
					}
					else {
						this.gameLevel.movingLeft(4);
						this.physics.setVelX(0);
					}
					
				}
				else {
					this.gameLevel.movingLeft(0);
					this.physics.setVelX(-4);
				}
			}
			else {
				this.gameLevel.movingRight(0);
				this.gameLevel.movingLeft(0);
			}
			//this is the animation section for the jumping 
			if(this.jumpTime == 5) {
				this.jumpTime = 0;
				if(this.direction) {  //if the time is equal to 5, it add to the index depending on the current direction
					if(this.jump_rightIndex < 6) {
						this.jump_rightIndex += 1;
						
					}
					else {
						this.jump_rightIndex = 0;
					}
				}
				else {
					if(this.jump_leftIndex < 6) {
						this.jump_leftIndex += 1;
						
					}
					else {
						this.jump_leftIndex = 0;
					}
				}
				
			}
			else {
				this.jumpTime += 1;
			}
			//here it will draw the image depending on the current direction
			if(this.direction) {
				currentImage = jump_right[this.jump_rightIndex];
				this.gc.drawImage(this.jump_right[this.jump_rightIndex], this.x, this.y);
			}
			else {
				currentImage = jump_left[this.jump_leftIndex];
				this.gc.drawImage(this.jump_left[this.jump_leftIndex], this.x, this.y);
			}
			//this.physics.addVelY(5);
		}
		//if the character is not currently falling or hurt and, the jump key is pressed
		else if(this.input.contains(up) && !isFalling  && !this.hurt) {
			this.gameLevel.movingRight(0); // this freezes the level to stop the screen from scrolling while jumping
			this.gameLevel.movingLeft(0);
			this.physics.fall = true;
			if(this.jumpTime == 5) {
				this.jumpTime = 0;
				if(this.direction) { //this check the direction of the character, in the event of being either left/right it will increment the necessary counter
					if(this.jump_rightIndex < 6) {
						this.jump_rightIndex += 1;
						
					}
					else {
						this.jump_rightIndex = 0;
					}
				}
				else {
					if(this.jump_leftIndex < 6) {
						this.jump_leftIndex += 1;
						
					}
					else {
						this.jump_leftIndex = 0;
					}
				}
				
			}
			else {
				this.jumpTime += 1;
			}
			//based on direction, it will draw the necessary image and set the current image value to the required image value
			if(this.direction) {
				currentImage = jump_right[this.jump_rightIndex];
				this.gc.drawImage(this.jump_right[this.jump_rightIndex], this.x, this.y);
			}
			else {
				currentImage = jump_left[this.jump_leftIndex];
				this.gc.drawImage(this.jump_left[this.jump_leftIndex], this.x, this.y);
			}
			this.physics.setVelY(0); //makes sure no velocity y to begin with
			
			this.physics.addVelY(-25); //accelerates the  value by -25 for a brief period of time
		}
		
		//if the crouch key is being pressed
		else if(this.input.contains(down) && !isFalling && !this.hurt) {
			//it makes it so no more falling, the level and the player will stop moving as they are in a crouch
			this.isFalling = false;
			this.gameLevel.movingRight(0);
			this.gameLevel.movingLeft(0);
			this.physics.setVelX(0);
			this.physics.setVelY(0);
			//calculates the amount of change with the current image and the crouch image height, this means that the difference will be accounted for and the y value will change
			double change = this.currentImage.getHeight() - duckImage.getHeight(); //accordingly
			this.y += change;
			if(this.input.contains(space)  && !this.hurt) {
				if(this.direction) {
					
					//if the bullet timed event is updating & the user has pressed the space key while crouched, depending on direction a bullet is added to the arraylist
					if(bulletFire.update()) {laserSoundClip.play(); bullets.add(new Bullet(this.gc, this.gameCanvas, this.gameLevel, this.x + this.currentImage.getWidth(),
							this.y + 12, this.direction));}
				}
				else {
					if(bulletFire.update()) {laserSoundClip.play(); bullets.add(new Bullet(this.gc, this.gameCanvas, this.gameLevel, this.x,
							this.y + 12, this.direction));}
				}
			}
			//based off direction the current image is changed
			if(this.direction) {
				this.currentImage = duckImage;
				this.gc.drawImage(duckImage, this.x, this.y);
			}
			else {
				this.currentImage = duckImageLeft;
				this.gc.drawImage(duckImageLeft, this.x, this.y);
			}
		}
		//this is code for the movement of the character in the direction to the right
		else if(this.input.contains(right) && !this.hurt) {
			this.direction = true;
			if (this.x > (1000/1.8)) { //checks if the x is more than a certain value to stop level scrolling or to start it otherwise
				if (this.gameLevel.moveValueRight() == false) {
					this.gameLevel.movingRight(0);
					this.physics.setVelX(4);
				}
				else {
					this.gameLevel.movingRight(4);
					this.physics.setVelX(0);
				}
				
			}
			else {
				this.gameLevel.movingRight(0);
				this.physics.setVelX(4);
			}
			// -------------------------------------------------------------------------
			//this block of code is for if there is a combination of both running and firing, it changes the image being drawn accordingly
			//otherwise the normal animation frame is drawn 
			if(this.input.contains(space)  && !this.hurt) {
				
				if(bulletFire.update()) {laserSoundClip.play(); bullets.add(new Bullet(this.gc, this.gameCanvas, this.gameLevel, this.x + this.currentImage.getWidth(),
						this.y + 12, this.direction));}
				if(this.walkAttackTime == 3) {
					this.walkAttackTime = 0;
					if(this.walkingAttack_rightIndex < 10) {
						this.walkingAttack_rightIndex += 1;
						
					}
					else {
						this.walkingAttack_rightIndex = 0;
					}
					
				}
				else {
					this.walkAttackTime += 1;
				}
				this.gc.drawImage(walkAttack_right[this.walkingAttack_rightIndex], this.x, this.y);
			}
			else {
				if(this.walkTime == 5) {
					this.walkTime = 0;
					if(this.walking_rightIndex < 7) {
						this.walking_rightIndex += 1;
						
					}
					else {
						this.walking_rightIndex = 0;
					}
					
				}
				else {
					this.walkTime += 1;
				}
				currentImage = walking_right[this.walking_rightIndex];
				this.gc.drawImage(walking_right[this.walking_rightIndex], this.x, this.y);
			}
		}
		//this checks if the player has ran to the left in the case of not being hurt
		else if(this.input.contains(left)  && !this.hurt) {
			this.physics.setVelX(0);  //this block of code, decided the direction the screen will be moving in respect to the velocity x, if 
			this.direction = false; //past a certain section it moves otherwise the velocity x is what moves
			if (this.x < (1000/1.8) - 200) {
				if (this.gameLevel.moveValueLeft() == false) {
					this.gameLevel.movingLeft(0);
					this.physics.setVelX(-4);
				}
				else {
					this.gameLevel.movingLeft(4);
					this.physics.setVelX(0);
				}
				
			}
			else {
				this.gameLevel.movingLeft(0);
				this.physics.setVelX(-4);
			}
			// -------------------------------------------------------------------------
			//this block of code is for if there is a combination of both running and firing, it changes the image being drawn accordingly
			//otherwise the normal animation frame is drawn 
			if(this.input.contains(space)  && !this.hurt) {
				if(bulletFire.update()) {laserSoundClip.play(); bullets.add(new Bullet(this.gc, this.gameCanvas, this.gameLevel, this.x, this.y + 12, this.direction));}
				if(this.walkAttackTime == 3) {
					this.walkAttackTime = 0;
					if(this.walkingAttack_leftIndex < 10) {
						this.walkingAttack_leftIndex += 1;
						
					}
					else {
						this.walkingAttack_leftIndex = 0;
					}
					
				}
				else {
					this.walkAttackTime += 1;
				}
				this.gc.drawImage(walkAttack_left[this.walkingAttack_leftIndex], this.x, this.y);
			}
			else {
				if(this.walkTime == 5) {
					this.walkTime = 0;
					if(this.walking_leftIndex < 7) {
						this.walking_leftIndex += 1;
						
					}
					else {
						this.walking_leftIndex = 0;
					}
					
				}
				else {
					this.walkTime += 1;
				}
				currentImage = walking_left[this.walking_leftIndex];
				this.gc.drawImage(walking_left[this.walking_leftIndex], this.x, this.y);
			}
		}
		//This block of code checks if the character is standing still, is not hurt and is trying to shoot
		else if(this.input.contains(space)  && !this.hurt) {
			
			this.gameLevel.movingRight(0);
			this.gameLevel.movingLeft(0);
			if(this.direction) {
				//if that is the case, there are bullets added to the arraylist accordingly
				if(bulletFire.update()) {laserSoundClip.play(); bullets.add(new Bullet(this.gc, this.gameCanvas, this.gameLevel, this.x + this.currentImage.getWidth(),
						this.y + 12, this.direction));}
			}
			else {
				if(bulletFire.update()) {bullets.add(new Bullet(this.gc, this.gameCanvas, this.gameLevel, this.x,
						this.y + 12, this.direction));}
			}
			//this is the animation section, same as usual
			if(this.standTime == 8) {
				this.standTime = 0;
				if(this.direction) {
					if(this.stand_rightIndex < 3) {
						this.stand_rightIndex += 1;
						
					}
					else {
						this.stand_rightIndex = 0;
					}
				}
				else {
					if(this.stand_leftIndex < 3) {
						this.stand_leftIndex += 1;
						
					}
					else {
						this.stand_leftIndex = 0;
					}
				}
				
			}
			else {
				this.standTime += 1;
			}
			if(this.direction) {
				currentImage = stand_right[this.stand_rightIndex];
				this.gc.drawImage(this.stand_right[this.stand_rightIndex], this.x, this.y);
			}
			else {
				currentImage = stand_left[this.stand_leftIndex];
				this.gc.drawImage(this.stand_left[this.stand_leftIndex], this.x, this.y);
			}
		}
		//this checks if the I key is being pressed, you are not falling, not hurt or the key has not just been presed
		else if(this.input.contains("I") && !this.isFalling && !this.hurt && !this.laserFired && this.laserUnlocked) {
			//System.out.println();
			
			this.gameLevel.movingRight(0);
			this.gameLevel.movingLeft(0);
			this.physics.setVelX(0);
			//self.healthbar = pygame.Rect(4, 10, ((500 / self.maxhealth) * self.health), 30)
		    //self.healthbaroutline = pygame.draw.rect(screen, WHITE, (0, 6, (500 / self.maxhealth * self.maxhealth) + 8, 38), 7)
			//this increments the timer
			if(this.laserTime == 20) {
				this.laserTime = 0;
				if(this.laser_Index < 10) {
					this.laser_Index += 1;
					
				}
				else {
					//in the case all the animations are done for the charging of the laser, a bullet of type 2 is created and added to the arraylist
					this.laserFired = true;
					this.laserFire.start(); //the countdown begins again
					this.laserCharge = 0;
					this.laser_Index = 0;
					if(this.direction) {this.bullets.add(new Bullet(this.gc, this.gameCanvas, this.x, this.y, this.direction, "Laser/laser.png", 4));}
					else {this.bullets.add(new Bullet(this.gc, this.gameCanvas, this.x, this.y, this.direction, "Laser/laser.png", -4));}
					System.out.println(this.bullets.get(0));
				}
				
			}
			else {
				this.laserTime += 1;
			}
			//this draw a laser charging bar at the bottom of the screen
			this.gc.setFill(Color.RED);
		    this.gc.fillRect(500, 350, ((500/this.maxLaserCharge*this.laserCharge)), 15);
		    this.laserCharge += 1;	
			if(this.direction) {
				currentImage = stand_right[0];
				this.gc.drawImage(this.stand_right[0], this.x, this.y);
				this.gc.drawImage(this.laser[this.laser_Index], this.x + this.currentImage.getWidth()/2 + 10, this.y + 5);
			}
			else {
				currentImage = stand_left[0];
				this.gc.drawImage(this.stand_left[0], this.x, this.y);
				this.gc.drawImage(this.laser[this.laser_Index], this.x - 10, this.y);
			}	

		}
		
		else if(this.input.contains("K") && !this.isFalling && !this.hurt && this.gravityGunUnlocked) {
			//System.out.println();
			System.out.println("ooo");
			this.gameLevel.movingRight(0);
			this.gameLevel.movingLeft(0);
			this.physics.setVelX(0);
			//self.healthbar = pygame.Rect(4, 10, ((500 / self.maxhealth) * self.health), 30)
		    //self.healthbaroutline = pygame.draw.rect(screen, WHITE, (0, 6, (500 / self.maxhealth * self.maxhealth) + 8, 38), 7)
			//this increments the timer
			if(this.direction) {
				if(this.bulletFire.update()) {this.bullets.add(new Bullet(this.gc, this.gameCanvas, this.gameLevel, this.x + this.currentImage.getWidth(), this.y, true, 4));}
			}
			else {
				if(this.bulletFire.update()) {this.bullets.add(new Bullet(this.gc, this.gameCanvas, this.gameLevel, this.x, this.y, false, -4));}
			}
			//this draw a laser charging bar at the bottom of the screen
			if(this.direction) {
				currentImage = stand_right[0];
				this.gc.drawImage(this.stand_right[0], this.x, this.y);
			}
			else {
				currentImage = stand_left[0];
				this.gc.drawImage(this.stand_left[0], this.x, this.y);
			}	

		}
		//if the arraylist is empty, it sets the velocity in x and y to zero
		else if(this.input.isEmpty()  && !this.hurt){
			this.laserCharge = 0;
			this.laser_Index = 0;
			this.physics.setVelX(0);
			this.physics.setVelY(0);
			this.gameLevel.movingRight(0);
			this.gameLevel.movingLeft(0);
			//makes the idle animation run
			if(this.idleTime == 12) {
				this.idleTime = 0;
				if(this.direction) {
					if(this.idle_rightIndex < 4) {
						this.idle_rightIndex += 1;
						
					}
					else {
						this.idle_rightIndex = 0;
					}
				}
				else {
					if(this.idle_leftIndex < 4) {
						this.idle_leftIndex += 1;
						
					}
					else {
						this.idle_leftIndex = 0;
					}
				}
				
			}
			else {
				this.idleTime += 1;
			}
			if(this.direction) {
				currentImage = idle_right[this.idle_rightIndex];
				this.gc.drawImage(this.idle_right[this.idle_rightIndex], this.x, this.y);
			}
			else {
				currentImage = idle_left[this.idle_leftIndex];
				this.gc.drawImage(this.idle_left[this.idle_leftIndex], this.x, this.y);
			}
		}
		//in all other cases, set the images to idle so no other button press affects the animation frames
		else { 
			if(!this.isFalling  && !this.hurt) {
				this.physics.setVelY(0);
				this.physics.fall = false;
			this.gameLevel.movingRight(0);
			this.gameLevel.movingLeft(0);
			if(this.idleTime == 12) {
				this.idleTime = 0;
				if(this.direction) {
					if(this.idle_rightIndex < 4) {
						this.idle_rightIndex += 1;
						
					}
					else {
						this.idle_rightIndex = 0;
					}
				}
				
				else {
					if(this.idle_leftIndex < 4) {
						this.idle_leftIndex += 1;
						
					}
					else {
						this.idle_leftIndex = 0;
					}
				}
				
			}
			else {
				this.idleTime += 1;
			}
			if(this.direction) {
				currentImage = idle_right[this.idle_rightIndex];
				this.gc.drawImage(this.idle_right[this.idle_rightIndex], this.x, this.y);
			}
			else {
				currentImage = idle_left[this.idle_leftIndex];
				this.gc.drawImage(this.idle_left[this.idle_leftIndex], this.x, this.y);
			}
		}
		}
		//this applies all the physics done before to the x position
		this.x = this.physics.moveX();
		this.y = this.physics.moveY();
		this.gc.drawImage(this.healthBar, 0, 0);
		points[0] = new Point2D(this.x + (this.currentImage.getWidth()/2), this.y);
		points[1] = new Point2D(this.x + (this.currentImage.getWidth()/2), this.y + this.currentImage.getHeight());
		points[2] = new Point2D(this.x, this.y + (this.currentImage.getHeight()/2));
		points[3] = new Point2D(this.x + (this.currentImage.getWidth()), this.y + (this.currentImage.getHeight()/2));
	
		
	}
}