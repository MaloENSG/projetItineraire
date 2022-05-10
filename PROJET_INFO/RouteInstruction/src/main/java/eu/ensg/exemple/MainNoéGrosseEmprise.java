package eu.ensg.exemple;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import eu.ensg.osm.Distance;
import eu.ensg.osm.HttpClientOsm;

public class MainNoéGrosseEmprise {

		static double Es = 0.205542;
		static double Ou = 0.203542;
		static double Su = 48.012701;
		static double No = 48.014701;
		
		static double lonpro;
		static double latpro;
		static String clepro;
		static String valproc;

		public static void main(String[] args) throws ParserConfigurationException {
			
			String dataRequest = "<osm-script>"
					+ "<union>"
					+ "<query type=\"node\">"
					+ "<bbox-query e=\"" + Es + "\" n=\"" + No + "\" s=\"" + Su + "\" w=\"" + Ou + "\" />"
					+ "</query>"
					+ "</union>"
					+ "<print mode=\"meta\"/>"
					+ "</osm-script>";
			String xmldata = HttpClientOsm.getOsmXML(dataRequest);
			// System.out.println(xml);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			try {
				Document doc = builder.parse(new ByteArrayInputStream(xmldata.getBytes()));
				doc.getDocumentElement().normalize();
			    Element root = (Element) doc.getElementsByTagName("osm").item(0);
			    
				double min = Distance.dist(Su,Es,No,Ou);
			    double middlon = (Es + Ou)/2;
				double middlat = (Su + No)/2;
					
			    int nbNoeuds = root.getElementsByTagName("node").getLength();
			    for (int i = 0; i < nbNoeuds; i++) {

			    	Element elem = (Element) root.getElementsByTagName("node").item(i);

			    	// On récupère son ID
			    	long id = Long.valueOf(elem.getAttribute("id"));

			    	// on récupère sa géométrie
			    	double lat = Double.valueOf(elem.getAttribute("lat"));
			    	double lon = Double.valueOf(elem.getAttribute("lon"));
			    	

					
			    	for (int j = 0; j < elem.getElementsByTagName("tag").getLength(); j++) {
						Element tagElem = (Element) elem.getElementsByTagName("tag").item(j);
						String cle = tagElem.getAttribute("k");
						String val = tagElem.getAttribute("v");
						String[] amen = {"bar", "biergaten", "cafe", "fast_food", "ice_cream", "pub", "restaurant", "college", "driving_school", "kindergarten", "library", "music_school", "school", "university", "taxi", "bank", "clinic", "dentist", "doctors", "hospital", "nursing_home", "pharmacy", "veterinary", "arts_centre", "casino", "cinema", "fountain", "nightclub", "planetarium", "stripclub", "studio", "theatre", "courthouse", "embassy", "fire_station", "police", "post_box", "post_office", "prison", "townhall", "crematorium", "funeral_hall", "grave_yard", "gym", "marketplace", "monastery"};
						
//						for (int u = 0; u<amen.length;u++) {
//						if (cle.equals("name") || (cle.equals("amenity")&& val.equals(amen[u]))) {
//							System.out.println(cle + "--" + val);
//							System.out.println(lon + "," + lat);
//							
//							double distance = Distance.dist(lat, lon, middlat, middlon);
//							System.out.println(min);
//							System.out.println(distance);
//
//							if(min>distance) {
//								min = distance;
//								System.out.println(min);
//								lonpro = lon;
//								latpro = lat;
//								clepro = cle;
//								valproc = val;
//							}
//						}
//						}
							
			    	}
			    	
			    }
			    System.out.println(clepro + "--" + valproc);
				System.out.println(lonpro + "," + latpro);
			} catch (Exception e) {
				e.printStackTrace();
			}

	}

}

