package shadowGame;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
/**
 * This is an Enemy class, it is meant to control the creation of enemies according to different types
 * @author Daniel Adesina
 * @version 2.0
 * @since 20th December 2018    
 */
public class Enemy {
	double x;
	double y;
	double original_x;
	double original_y;
	int type;
	Physics physics;
	GraphicsContext gc;
	@FXML Canvas canvas;
	int levelDx;
	int health = 200;
	boolean alive = true;
	boolean attackMode = false;
	boolean returned = true;
	boolean inContact = false;
	double targetX;
	HealthBar healthbar;
	Image[] octopus_right = new Image[5];
	Image[] octopus_left = new Image[5];
	
	Image[] crab_left = new Image[5];
	Image[] crab_right = new Image[5];
	
	Image[] crabWalk_right = new Image[5];
	Image[] crabWalk_left = new Image[5];
	
	Image[] houndWalk_right = new Image[13];
	Image[] houndWalk_left = new Image[12];
	
	Image[] houndAttack_right = new Image[5];
	Image[] houndAttack_left = new Image[5];

	Image[] houndJump_right = new Image[6];
	Image[] houndJump_left = new Image[6];
	
	Image[] death = new Image[6];
	ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	Image octopus_bullet = new Image("octopus_bullet.png");
	TimedEvent attack = new TimedEvent(100, 100, true);
	TimedEvent crabPatrol = new TimedEvent(200);
	TimedEvent octopusPatrol = new TimedEvent(100);
	TimedEvent houndPatrol = new TimedEvent(300);
	
	boolean crabOnPatrol = true;
	boolean houndOnPatrol = true;
	int deathTime = 0;
	
	int crabWalkTime = 0;
	int crabWalk_rightIndex = 0;
	int crabWalk_leftIndex = 0;
	
	int octopusTime = 0;
	int octopus_rightIndex = 0;
	int octopus_leftIndex = 0;
	
	int houndWalkTime = 0;
	int houndWalk_rightIndex = 0;
	int houndWalk_leftIndex = 0;
	
	int houndAttackTime = 0;
	int houndAttack_rightIndex = 0;
	int houndAttack_leftIndex = 0;
	
	int houndJumpTime = 0;
	int houndJump_rightIndex = 0;
	int houndJump_leftIndex = 0;
	
	int death_Index = 0;
	
	boolean direction = false;
	Image currentImage;
	
	public Enemy(GraphicsContext gc, Canvas canvas, int levelDx, int type, boolean direction, double x, double y) {
		super();
		this.x = x;
		this.y = y;
		this.original_x = x;
		this.original_y = y;
		this.gc = gc;
		this.canvas = canvas;
		this.type = type;
		this.direction = direction;
		this.levelDx = levelDx;
		physics = new Physics(x, y);
		for(int i = 0; i < death.length; i ++) {
			String stringIndex = Integer.toString(i + 1);
			String imageName = "enemy-death/enemy-death-" + stringIndex + ".png";
			death[i] = new Image(imageName);
		}
		if(this.type == 0) {
			this.physics.setVelY(-1);
			healthbar = new HealthBar(this.gc, this.canvas, this.x, this.y - 10, this.health);
			for(int i = 0; i < octopus_right.length; i ++) {
				String stringIndex = Integer.toString(i + 1);
				String imageName = "octopusRight - Copy/octopus-" + stringIndex + ".png";
				octopus_right[i] = new Image(imageName);
			}
			for(int i = 0; i < octopus_left.length; i ++) {
				String stringIndex = Integer.toString(i + 1);
				String imageName = "octopusLeft/octopus-" + stringIndex + ".png";
				octopus_left[i] = new Image(imageName);
			}
			this.currentImage = octopus_right[0];
		}
		if(this.type == 1) {
			healthbar = new HealthBar(this.gc, this.canvas, this.x + 30, this.y - 10, this.health);
			this.physics.setVelX(2);
			for(int i = 0; i < crabWalk_right.length; i ++) {
				String stringIndex = Integer.toString(i + 1);
				String imageName = "crab-walk-right - Copy/crab-walk-" + stringIndex + ".png";
				crabWalk_right[i] = new Image(imageName);
			}
			for(int i = 0; i < crabWalk_left.length; i ++) {
				String stringIndex = Integer.toString(i + 1);
				String imageName = "crab-walk - Copy/crab-walk-" + stringIndex + ".png";
				crabWalk_left[i] = new Image(imageName);
			}
			this.currentImage = octopus_right[0];
			

			
		}
		if(this.type == 2) {
			this.physics.setVelX(2);
			healthbar = new HealthBar(this.gc, this.canvas, this.x + 30, this.y - 10, this.health);
			for(int i = 0; i < houndWalk_left.length; i ++) {
				String stringIndex = Integer.toString(i + 1);
				String imageName = "HellHound - Copy - Copy/Walking/hell-hound-walking-" + stringIndex + ".png";
				houndWalk_left[i] = new Image(imageName);
				this.gc.drawImage(houndWalk_left[i], 0, this.y);
			}
			for(int i = 0; i < houndWalk_left.length; i ++) {
				String stringIndex = Integer.toString(i + 1);
				String imageName = "HellHound - Copy - Copy/WalkingRight/hell-hound-walking-" + stringIndex + ".png";
				houndWalk_right[i] = new Image(imageName);
				this.gc.drawImage(houndWalk_right[i], 0, this.y);
			}
			for(int i = 0; i < houndAttack_left.length; i ++) {
				String stringIndex = Integer.toString(i + 1);
				String imageName = "HellHound - Copy - Copy/Running/hell-hound-running-" + stringIndex + ".png";
				houndAttack_left[i] = new Image(imageName);
				this.gc.drawImage(houndAttack_left[i], 0, this.y);
			}
			for(int i = 0; i < houndAttack_left.length; i ++) {
				String stringIndex = Integer.toString(i + 1);
				String imageName = "HellHound - Copy - Copy/RunningRight/hell-hound-running-" + stringIndex + ".png";
				houndAttack_right[i] = new Image(imageName);
				
			}
			for(int i = 0; i < houndJump_left.length; i ++) {
				String stringIndex = Integer.toString(i + 1);
				String imageName = "HellHound - Copy - Copy/JumpingLeft/hell-hound-jump-" + stringIndex + ".png";
				houndJump_left[i] = new Image(imageName);
				this.gc.drawImage(houndJump_left[i], 0, this.y);
			}
			for(int i = 0; i < houndJump_left.length; i ++) {
				String stringIndex = Integer.toString(i + 1);
				String imageName = "HellHound - Copy - Copy/JumpingRight/hell-hound-jump-" + stringIndex + ".png";
				houndJump_right[i] = new Image(imageName);
				
			}
			//houndWalk_left[0] = new Image("HellHound - Copy/Walking/hell-hound-walking-1.png");
			this.currentImage = houndWalk_left[0];
		}
		
		
	}
	
	public void returnHome() {
		if(this.x > this.original_x && !this.returned) {
			this.physics.setVelX(-2);
		}
		else if(this.x < this.original_x && !this.returned) {
			this.physics.setVelX(2);
		}
		
		if(Math.abs(this.x - this.original_x) < 50 && !this.returned) {
			this.returned = true;
		}
	}
	
	public void attackChar(double targetX) {
		this.targetX = targetX;
		if(targetX < this.x + this.currentImage.getWidth()) {
			this.direction = false;
		}
		else {
			this.direction = true;
		}
		if(this.type == 0) {
			//System.out.println(attack.timerIndex);
			if(Math.abs(targetX - this.x) < 400) {
				if(attack.update()) {
					if(this.direction) {
						bulletList.add(new Bullet(this.gc, this.canvas, this.x, this.y + 50, this.direction, "octopus_bullet.png", 5));
					}
					else {
						bulletList.add(new Bullet(this.gc, this.canvas, this.x, this.y + 50, this.direction, "octopus_bullet.png", -5));
					}
				}
			}
		}
		else if(this.type == 1) {
			//System.out.println("cool");
		}
		else if(this.type == 2) {
			//System.out.println("coooool");
			if(Math.abs(this.original_x - targetX) < 300) {
				if(targetX < this.x) {
					this.direction = false;
					this.physics.setVelX(-4);
				}
				else {
					this.direction = true;
					this.physics.setVelX(4);
				}
				this.attackMode = true;
				//System.out.println("Attack Mode");
			}
			else {
				this.attackMode = false;
			}
			
		}
		//System.out.println(this.attackMode);
	}
	
	public void update() {
		if(octopusPatrol.update() && this.type == 0) {
			this.physics.velY *= -1;
		}
		else if(crabPatrol.update() && this.type == 1) {
			this.physics.velX *= -1;
		}
		else if(this.x < this.original_x - 300 
				&& this.type == 2 && !this.attackMode) {
			this.physics.setVelX(2);
		}
		else if(this.x > this.original_x + 300
				&& this.type == 2 && !this.attackMode) {
			this.physics.setVelX(-2);
		}
		//if(this.x < this.original_x - 50)
		
		if(this.alive) {
			if(this.type == 0) {
				if(this.octopusTime == 12) {
					this.octopusTime = 0;
					if(this.direction) {
						if(this.octopus_rightIndex < 4) {
							this.octopus_rightIndex += 1;
							
						}
						else {
							this.octopus_rightIndex = 0;
						}
					}
					else {
						if(this.octopus_leftIndex < 4) {
							this.octopus_leftIndex += 1;
							
						}
						else {
							this.octopus_leftIndex = 0;
						}
					}
				}
					
				else {
					this.octopusTime += 1;
				}
				if(this.direction) {
					currentImage = octopus_right[this.octopus_rightIndex];
					this.gc.drawImage(this.octopus_right[this.octopus_rightIndex], this.x, this.y);
				}
				else {
					currentImage = octopus_left[this.octopus_leftIndex];
					this.gc.drawImage(this.octopus_left[this.octopus_leftIndex], this.x, this.y);
				}
				this.healthbar.update(this.health/20);
				this.y += this.physics.velY;
				this.healthbar.y += this.physics.velY;
			}
			if(this.type == 1) {
				if(this.physics.velX > 0) {
					this.direction = true;
				}
				else if(this.physics.velX < 0) {
					this.direction = false;
				}
				if(this.crabOnPatrol) {
					if(this.crabWalkTime == 5) {
						this.crabWalkTime = 0;
						if(this.direction) {
							if(this.crabWalk_rightIndex < 4) {
								this.crabWalk_rightIndex += 1;
								
							}
							else {
								this.crabWalk_rightIndex = 0;
							}
						}
						else {
							if(this.crabWalk_leftIndex < 4) {
								this.crabWalk_leftIndex += 1;
								
							}
							else {
								this.crabWalk_leftIndex = 0;
							}
						}
					}
						
					else {
						this.crabWalkTime += 1;
					}
					if(this.direction) {
						currentImage = crabWalk_right[this.crabWalk_rightIndex];
						this.gc.drawImage(this.crabWalk_right[this.crabWalk_rightIndex], this.x, this.y);
					}
					else {
						
						currentImage = crabWalk_left[this.crabWalk_leftIndex];
						this.gc.drawImage(this.crabWalk_left[this.crabWalk_leftIndex], this.x, this.y);
					}
					this.healthbar.update(this.health/20);
					
				}

				
				this.x += this.physics.velX;
				this.healthbar.x += this.physics.velX;
			}
			else if(this.type == 2) {
				if(this.physics.velX > 0) {
					this.direction = true;
					this.physics.velX = 2;
				}
				else if(this.physics.velX < 0) {
					this.direction = false;
					this.physics.setVelX(-2);
				}
				if(!this.attackMode) {
					if(this.houndWalkTime == 5) {
						this.houndWalkTime = 0;
						if(this.direction) {
							if(this.houndWalk_rightIndex < 4) {
								this.houndWalk_rightIndex += 1;
								
							}
							else {
								this.houndWalk_rightIndex = 0;
							}
						}
						else {
							if(this.houndWalk_leftIndex < 4) {
								this.houndWalk_leftIndex += 1;
								
							}
							else {
								this.houndWalk_leftIndex = 0;
							}
						}
					}
						
					else {
						this.houndWalkTime += 1;
					}
					if(this.direction) {
						currentImage = houndWalk_right[this.houndWalk_rightIndex];
						this.gc.drawImage(this.houndWalk_right[this.houndWalk_rightIndex], this.x, this.y);
						
					}
					else {
						currentImage = houndWalk_left[this.houndWalk_leftIndex];
						this.gc.drawImage(this.houndWalk_left[this.houndWalk_leftIndex], this.x, this.y);
						
					}
					this.healthbar.update(this.health/20);
					
				}
				
				else {
					if(this.inContact) {
						if(this.houndJumpTime == 5) {
							this.houndJumpTime = 0;
							if(this.direction) {
								if(this.houndJump_rightIndex < 5) {
									this.houndJump_rightIndex += 1;
									
								}
								else {
									this.houndJump_rightIndex = 0;
								}
							}
							else {
								if(this.houndJump_leftIndex < 5) {
									this.houndJump_leftIndex += 1;
									
								}
								else {
									this.houndJump_leftIndex = 0;
								}
							}
						}
							
						else {
							this.houndJumpTime += 1;
						}
						if(this.direction) {
							currentImage = houndJump_right[this.houndJump_rightIndex];
							this.gc.drawImage(this.houndJump_right[this.houndJump_rightIndex], this.x, this.y);
							this.physics.setVelX(4);
						}
						else {
							
							currentImage = houndJump_left[this.houndJump_leftIndex];
							this.gc.drawImage(this.houndJump_left[this.houndJump_leftIndex], this.x, this.y);
							this.physics.setVelX(-4);
						}
					}
					else {
						if(this.houndAttackTime == 5) {
							this.houndAttackTime = 0;
							if(this.direction) {
								if(this.houndAttack_rightIndex < 4) {
									this.houndAttack_rightIndex += 1;
									
								}
								else {
									this.houndAttack_rightIndex = 0;
								}
							}
							else {
								if(this.houndAttack_leftIndex < 4) {
									this.houndAttack_leftIndex += 1;
									
								}
								else {
									this.houndAttack_leftIndex = 0;
								}
							}
						}
							
						else {
							this.houndAttackTime += 1;
						}
						if(this.direction) {
							currentImage = houndAttack_right[this.houndAttack_rightIndex];
							this.gc.drawImage(this.houndAttack_right[this.houndAttack_rightIndex], this.x, this.y);
							this.physics.setVelX(4);
						}
						else {
							
							currentImage = houndAttack_left[this.houndAttack_leftIndex];
							this.gc.drawImage(this.houndAttack_left[this.houndAttack_leftIndex], this.x, this.y);
							this.physics.setVelX(-4);
						}
					}
					this.healthbar.update(this.health/20);
				}
				this.x += this.physics.velX;
				this.healthbar.x += this.physics.velX;
			}
		}
		else {
			if(this.deathTime == 5) {
				this.deathTime = 0;
				if(this.death_Index < 5) {
					this.death_Index += 1;
					
				}
				else {
					this.death_Index = 0;
				}
			}
			else {
				this.deathTime += 1;
			}
			currentImage = death[this.death_Index];
			this.gc.drawImage(this.death[this.death_Index], this.x + 25.35, this.y);
		}
		
	}
}
