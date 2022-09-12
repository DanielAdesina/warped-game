package shadowGame;
/**
 * This is a timed event (timer) class meant to monitor the passing of indexes that increase every .update loop
 * @author Daniel Adesina
 *
 */
public class TimedEvent {
	int timer = 0;
	int timerIndex;
	int range;
	int addedOn;
	boolean random;
	boolean toStart = true;
	/**
	 * This is a timed event method, creates an event that happens every 
	 * @param range This decides the range based off an integer that will be pre-selected
	 */
	public TimedEvent(int range) {
		super();
		this.range = range;
		this.timer = range;
	}
	/**
	 * This is a timed event class constructor, this accepts more variables to allow for variety
	 * @param range  gives it a range as well
	 * @param addedOn added on value to the random number
	 * @param random A random number for the timer
	 */
	public TimedEvent(int range, int addedOn, boolean random) {
		super();
		this.range = range; //ran
		this.timer = range;
		this.addedOn = addedOn;
		this.random = random;
	}
	/**
	 * This is an update method, it is meant to constantly update the timer, once called it will add plus one to the index and if the index has reached the maximum
	 * it will return it to zero
	 * @return If the timer has reached its maximum it will return a boolean value of true, otherwise it will return false
	 */
	public boolean update() {
		if(this.toStart) { //if start then the timer can increment
			if(this.timerIndex > this.timer) { //checks if the timerIndex is more than the timer, if so, set it to 0
				if(this.random) {
					this.timer = (int) (Math.random()*range + this.addedOn);
					this.timerIndex = 0;
				}
				else {
					this.timer = range;
					this.timerIndex = 0;
				}
				return true;
				
			}
			else { //otherwise, add 1
				this.timerIndex += 1;
				return false;
			}
		}
		else { //otherwise return false for update
			return false;
		}
	}
	
	//variables to manage whether it is running
	/**
	 * Start method to control the timed event
	 */
	public void start() {
		this.toStart = true;
		this.timerIndex = 0;
	}
	
	/**
	 * End method to control the timed event
	 */
	public void end() {
		this.toStart = false;
		this.timerIndex = 0;
	}
}
