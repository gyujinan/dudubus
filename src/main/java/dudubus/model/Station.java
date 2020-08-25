package dudubus.model;

import lombok.Data;

@Data
public class Station {
	
	
	String stationId;
	String stationNm;
	String arsId;
	Coordinate crd;
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Station) {
			
			return arsId == ((Station)obj).arsId;
		}else {
			return false;
		}
			
	}
	

	
}
