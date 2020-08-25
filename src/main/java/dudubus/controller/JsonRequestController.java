package dudubus.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dudubus.model.Coordinate;
import dudubus.model.Route;
import dudubus.model.Station;
import dudubus.util.Collecter;

@Controller
public class JsonRequestController {
	
	@GetMapping("/getStaionsByPos.json")
	public @ResponseBody List<Station> getStaionsByPos(@RequestParam("lat") String lat, @RequestParam("lng") String lng) {
		System.out.println("lat: "+lat+"lng: " +lng);
		List<Station> stations = null;
		try {			
			stations = Collecter.getStaionsByPos(lat, lng);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stations;
		
	}
	
	@GetMapping("/getRouteByStation.json")
	public @ResponseBody List<Route>getRouteByStation(@RequestParam("arsId") String arsId){
		List<Route> routes = null;
		try {			
			routes = Collecter.getRouteByStation(arsId);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return routes;
		
	}
	
	
    @GetMapping("/getRoutePath.json")
    public @ResponseBody List<Coordinate> routeCoordinate(@RequestParam("busRouteId") String busRouteId){
    	
    	List<Coordinate> route = null;
		try {
			route = Collecter.getRoutePath(busRouteId);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
   
    	return route;
    }
    

    
}


