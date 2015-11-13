package dataclasses;

public class Coordinate {
	/* The X part of the coordinate */
	private float x;
	
	/* The Y part of the coordinate */
	private float y;
	
	/* The Z part of the coordinate */
	private float z;
	
	/* Constructor 1
	 * This constructor takes three floats values and initializes the x, y and z coordinates 
	 * @param x The given x-coordinate
	 * @param y The given y-coordinate
	 * @param z The given z-coordinate
	 * */
	public Coordinate(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/* Constructor 2
	 * This constructor takes in 1 float value and initializes the x, y and z coordinates with the given value
	 * @param value Single float number to initialize x, y and z
	 * */
	public Coordinate(float value){
		x = value;
		y = value;
		z = value;
	}
	
	/* 
	 * Getter for the x-coordinate
	 * @return x The x-coordinate
	 * */
	public float getX(){
		return this.x;
	}
	
	/* 
	 * Getter for the y-coordinate
	 * @return y The y-coordinate
	 * */
	public float getY(){
		return this.y;
	}
	
	/* 
	 * Getter for the z-coordinate
	 * @return z The z-coordinate
	 * */
	public float getZ(){
		return this.x;
	}
	
	
	
	
	
	
}
