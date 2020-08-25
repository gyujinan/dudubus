package dudubus.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dudubus.model.Coordinate;
import dudubus.model.Route;
import dudubus.model.Station;

public class Collecter {

	final private static String KEY = "api-key";
	final private static String radius = "150";

	private Collecter() {
	}

	private static String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue == null)
			return null;
		return nValue.getNodeValue();
	}

	public static List<Station> getStaionsByPos(String lat, String lng) throws UnsupportedEncodingException {
		StringBuilder urlBuilder = new StringBuilder(
				"http://ws.bus.go.kr/api/rest/stationinfo/getStationByPos"); /* URL */

		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + KEY);
		urlBuilder.append("&" + URLEncoder.encode("tmX", "UTF-8") + "=" + URLEncoder.encode(lng, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("tmY", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("radius", "UTF-8") + "=" + URLEncoder.encode(radius, "UTF-8"));

		String url = urlBuilder.toString();
		System.out.println(url);
		List<Station> sta = new ArrayList<Station>();
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("itemList");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Coordinate tmpCrd = new Coordinate(getTagValue("gpsY", eElement), getTagValue("gpsX", eElement));
					Station tmpSta = new Station();
					tmpSta.setArsId(getTagValue("arsId", eElement));
					tmpSta.setStationId(getTagValue("stationId", eElement));
					tmpSta.setStationNm(getTagValue("stationNm", eElement));
					tmpSta.setCrd(tmpCrd);
					sta.add(tmpSta);
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sta;
	}

	public static List<Route> getRouteByStation(String arsId) throws UnsupportedEncodingException {
		StringBuilder urlBuilder = new StringBuilder(
				"http://ws.bus.go.kr/api/rest/stationinfo/getRouteByStation"); /* URL */

		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + KEY);
		urlBuilder.append("&" + URLEncoder.encode("arsId", "UTF-8") + "=" + URLEncoder.encode(arsId, "UTF-8")); /**/

		String url = urlBuilder.toString();
		System.out.println(url);
		List<Route> rt = new ArrayList<Route>();
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("itemList");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Route tmp = new Route();
					tmp.setBusRouteId( getTagValue("busRouteId", eElement));
					tmp.setBusRouteNm( getTagValue("busRouteNm", eElement));
					tmp.setBusRouteType( getTagValue("busRouteType", eElement));
					tmp.setLength( getTagValue("length", eElement));
					tmp.setStBegin( getTagValue("stBegin", eElement));
					tmp.setStEnd( getTagValue("stEnd", eElement));
					tmp.setTerm( getTagValue("term", eElement));
					

					rt.add(tmp);

				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rt;
	}

	public static List<Coordinate> getRoutePath(String busRouteId) throws UnsupportedEncodingException {
		StringBuilder urlBuilder = new StringBuilder(
				"http://ws.bus.go.kr/api/rest/busRouteInfo/getRoutePath"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + KEY); /* Service Key */
		urlBuilder.append(
				"&" + URLEncoder.encode("busRouteId", "UTF-8") + "=" + URLEncoder.encode(busRouteId, "UTF-8")); /**/
		String url = urlBuilder.toString();
		System.out.println(url);

		List<Coordinate> crd = new ArrayList<Coordinate>();
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;

		try {
			dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("itemList");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					crd.add(new Coordinate(getTagValue("gpsY", eElement), getTagValue("gpsX", eElement)));
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return crd;

	}

}
