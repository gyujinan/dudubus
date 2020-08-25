package dudubus.model;

import lombok.Data;

@Data
public class Route {
	String busRouteId;
	String busRouteNm;
	String busRouteType;
	String length;
	String stBegin;
	String stEnd;
	String term;	
}
