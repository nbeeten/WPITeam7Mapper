package edu.wpi.off.by.one.errors.code;

public class Map {
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
	
	public void setImgUrl(String path) { this.imagePath = path; }
	public void setCoordinate(Coordinate coordinate) { this.center = coordinate; }
	public void setRotation(float rotation) { this.rotation = rotation; }
	public void setScale(float scale) { this.scale = scale; }
	
}
