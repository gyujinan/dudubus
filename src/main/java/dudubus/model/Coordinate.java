package dudubus.model;

import lombok.Data;

@Data
public class Coordinate {
	String lat; 
	String lng; 
	public Coordinate(String lat, String lng){
		this.lat = lat;
		this.lng = lng;
	}
	
	
	
}
