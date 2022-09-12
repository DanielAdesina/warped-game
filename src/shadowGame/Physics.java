package shadowGame;

import javafx.geometry.Point2D;
/**
 * This is a Physics class, it is meant to create all the physics functions used in player and enemy classes
 * @author Daniel Adesina
 * @version 2.0
 * @since 20th December 2018    
 */
public class Physics {
	double x;
	double y;
	double grav = 2;
	double velY = 0;
	double velX = 0;
	boolean fall = true;
	Point2D[] points = {new Point2D(x, y)};
	
	/**
	 * Initializes the physics class
	 * @param x a double of x
	 * @param y a double of y
	 */
	public Physics(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Adding gravity in constantly to the vel y
	 * @param amount the set amount
	 */
	public void gravity(double amount) {
		this.velY += amount;
		
	}
	
	/**
	 * Slows down the x by a certain amount
	 * @param amount
	 */
	public void airResistance(double amount) {
		this.velX += amount;
	}
	
	/**
	 * Sets the velocity x
	 * @param amount a double amount
	 */
	public void setVelX(double amount) {
		this.velX = amount;
	}
	/**
	 * Sets the velocity y
	 * @param amount a double amount
	 */
	public void setVelY(double amount) {
		this.velY = amount;
	}
	/**
	 * adds to the velocity x
	 * @param amount a double amount
	 */
	public void addVelX(double amount) {
		this.velX += amount;
	}
	/**
	 * adds to the velocity y
	 * @param amount a double amount
	 */
	public void addVelY(double amount) {
		this.velY += amount;
	}
	
	/**
	 * Adds to the velocity x to the x
	 * @return A double of x
	 */
	public double moveX() {
		this.x += this.velX;
		return this.x;
	}
	/**
	 * Adds to the velocity y to the y
	 * @return A double of y
	 */
	public double moveY() {
		if(this.fall) {
			this.y += this.velY;
		}
		
		return this.y;
	}
}
