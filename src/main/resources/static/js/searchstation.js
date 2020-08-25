/**
 * 
 */
		function clickPoint(e){

			let point = new google.maps.LatLng(e.latLng.lat(), e.latLng.lng());
			marker.setPosition(point);
			marker.setMap(map);
			searchStations(e.latLng.lat(), e.latLng.lng());
		}
		
		function searchStations(lat, lng){
			if(stationMarkers.length != 0){
				clearMarkers();
			}
			if(stationRoutes.length != 0){
				clearRoutes();
			}
			console.log(lat,lng);
			const xhr = new XMLHttpRequest(); 
			xhr.open("GET","getStaionsByPos.json?lat=" + lat +"&lng=" + lng,true);
			xhr.responseType='json'; 
			xhr.onload = () => { 
				var data = xhr.response;
				for(var i=0; i<data.length;i++){
					console.log(data[i]);
					let tmpSta = {
							stationId : data[i].stationId,
							stationNm : data[i].stationNm,
							arsId : data[i].arsId,
							lat : data[i].crd.lat,
							lng : data[i].crd.lng
					}
					
					const tmpPos = new google.maps.LatLng(data[i].crd.lat,data[i].crd.lng);
					const tmpMarker = new google.maps.Marker({
						position : tmpPos,
						map : map,
						//label : data[i].stationNm
					});
					tmpMarker.addListener("click", () => {
						clickStation(tmpSta);
					});
					stationMarkers.push(tmpMarker);
				}
			}
			xhr.send(); 
		}
		
		function setMapOnAll(map) {
			  for (let i = 0; i < stationMarkers.length; i++) {
				  stationMarkers[i].setMap(map);
			  }
		}
		
		function clearMarkers() {
			  setMapOnAll(null);
		}
		