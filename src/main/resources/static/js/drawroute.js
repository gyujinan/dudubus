/**
 * 
 */

 		function clickStation(sta){
			clearMarkers();
			
			const tmpPos = new google.maps.LatLng(sta.lat,sta.lng);
			const tmpMarker = new google.maps.Marker({
				position : tmpPos,
				map : map,
				label : sta.stationNm
			});
			stationMarkers.push(tmpMarker);
			showRouteList(sta.arsId);
			
		}
		function showRouteList(arsId){
			if(document.getElementById("routelist")){
				let tag = document.getElementById("routelist");
				routeform.removeChild(tag);
			}
			var routelist = document.createElement("select");
			routelist.setAttribute("id","routelist");
			routelist.setAttribute("onchange","drawRoute(value)");
			
			routeform.appendChild(routelist);
			const xhr = new XMLHttpRequest(); 
			xhr.open("GET","getRouteByStation.json?arsId=" + arsId,true);
			xhr.responseType='json'; 
			xhr.onload = () => { 
				var data = xhr.response;
				routelist.setAttribute("size",data.length);
				for(var i =0; i<data.length; i++){
					var route = document.createElement("option");
					var node = document.createTextNode("노선명: " +data[i].busRouteNm+", 시작정류소: "+data[i].stBegin+ ", 마지막 정류소: "+data[i].stEnd);
					route.appendChild(node);
					route.setAttribute("value",data[i].busRouteId);
					routelist.appendChild(route);
				}
				
			}
			xhr.send(); 
		}
		
		function drawRoute(busRouteId){
			
			const poly = new google.maps.Polyline({
			    strokeColor: "#0033FF",
			    strokeOpacity: 1.0,
			    strokeWeight: 3
			});
			poly.setMap(map);
			stationRoutes.push(poly);
			let path = poly.getPath();
			const xhr = new XMLHttpRequest(); 
			xhr.open("GET","getRoutePath.json?busRouteId=" + busRouteId,true);
			xhr.responseType='json'; 
 			xhr.onload = () => { 
				var data = xhr.response;
  				for(var i =0; i<data.length; i++){
  					const tmp = new google.maps.LatLng(data[i].lat, data[i].lng);
  					path.push(tmp);
				}  
				
			} 
			xhr.send();  
			
		}
		
		function setMapOnAllRoute(map) {
			  for (let i = 0; i < stationRoutes.length; i++) {
				  stationRoutes[i].setMap(map);
			  }
		}
		
		function clearRoutes() {
			setMapOnAllRoute(null);
		}