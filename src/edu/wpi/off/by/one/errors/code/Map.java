package edu.wpi.off.by.one.errors.code;

public class Map {
	
	String name;
	String imagePath;
	Coordinate center;
	float rotation;
	float scale;
	public Map(String path, Coordinate coordinate, float rotation, float scale){
		this.imagePath = path;
		this.center = coordinate;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public Map(){
		
	}
	
	public Map (String name, String imagePath, float rotation, float scale){
		this.name = name;
		this.imagePath = imagePath;
		this.center = new Coordinate(0, 0, 0);
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public Map (String name, String imagePath, Coordinate center, float rotation, float scale){
		this.name = name;
		this.imagePath = imagePath;
		this.center = center;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public void setImgUrl(String path) { this.imagePath = path; }
	public void setCenter(Coordinate coordinate) { this.center = coordinate; }
	public void setRotation(float rotation) { this.rotation = rotation; }
	public void setScale(float scale) { this.scale = scale; }
	public String getImgUrl() { return this.imagePath; }
	public Coordinate getCenter() { return this.center;}
	public float getRotation() { return this.rotation; }
	public float getScale() { return this.scale; }

}
