package eu.ensg.exemple;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.ensg.ign.Geometry;
import eu.ensg.ign.HttpClientIgn;
import eu.ensg.ign.Portion;
import eu.ensg.ign.Resultat;
import eu.ensg.ign.Step;
import eu.ensg.osm.Distance;
import eu.ensg.osm.HttpClientOsm;

public class MainNoeFaitDesTrucs {

	public static void main(String[] args) throws ParserConfigurationException, InterruptedException {
		
		String url = "https://wxs.ign.fr/calcul/geoportail/itineraire/rest/1.0.0/route?resource=bdtopo-pgr"
				+ "&profile=pedestrian&optimization=fastest"
				+ "&start=0.2046,48.0137&end=0.1839,48.0070"
				+ "&intermediates=&constraints={\"constraintType\":\"banned\",\"key\":\"wayType\",\"operator\":\"=\",\"value\":\"tunnel\"}"
				+ "&geometryFormat=geojson&crs=EPSG:4326&getSteps=true&getBbox=true&waysAttributes=nature";
		String txtJson = HttpClientIgn.request(url);
		System.out.println(url);
		
		Gson gson = new GsonBuilder().create();
		Resultat itineraire = gson.fromJson(txtJson, Resultat.class);
		for (Portion portion : itineraire.getPortions()) {
			
			String[] start = portion.getStart().split(",");
			double lon = Double.parseDouble(start[0]);
			double lat = Double.parseDouble(start[1]);
			String[] end = portion.getEnd().split(",");
			
			for (Step step : portion.getSteps()) {
				List<Double[]> coords = step.getGeometry().getCoordinates();
				double dist = step.getDistance();
				String nom = step.getAttributes().getNom();
				String nat = step.getAttributes().getNature();
				if(nom != "") {System.out.println("Avancer de "+dist+" m dans "+nom);}
				else {System.out.println("Avancer de "+dist+" m dans "+nat);}
				
				//Choix batiment & determination de sa direction visuelle
				Double[] dercoord = coords.get(coords.size()-1);
				
				double E = dercoord[0]+0.002;
				double O = dercoord[0]-0.002;
				double S = dercoord[1]-0.002;
				double N = dercoord[1]+0.002;
				System.out.println(E);
				System.out.println(O);
				System.out.println(S);
				System.out.println(N);
				
				String valpro="";

					
					String dataRequest = "<osm-script>"
							+ "<union>"
							+ "<query type=\"node\">"
							+ "<bbox-query e=\"" + E + "\" n=\"" + N + "\" s=\"" + S + "\" w=\"" + O + "\" />"
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
					    
						double min = Distance.dist(S,E,N,O);
					    double middlon = (E + O)/2;
						double middlat = (S + N)/2;
							
					    int nbNoeuds = root.getElementsByTagName("node").getLength();
					    for (int i = 0; i < nbNoeuds; i++) {

					    	Element elem = (Element) root.getElementsByTagName("node").item(i);

					    	// On récupère son ID
					    	long id = Long.valueOf(elem.getAttribute("id"));

					    	// on récupère sa géométrie
					    	double latit = Double.valueOf(elem.getAttribute("lat"));
					    	double longit = Double.valueOf(elem.getAttribute("lon"));
					    	
							
					    	for (int j = 0; j < elem.getElementsByTagName("tag").getLength(); j++) {
								Element tagElem = (Element) elem.getElementsByTagName("tag").item(j);
								String cle = tagElem.getAttribute("k");
								String val = tagElem.getAttribute("v");
								
								if (cle.equals("name")) {
									double distance = Distance.dist(latit, longit, middlat, middlon);

									if(min>distance) {
										min = distance;
										valpro = val;
									}
								}
									
					    	}
					    	
					    }
					    System.out.println("Tourner au niveau de " + valpro);
					} catch (Exception e) {
						e.printStackTrace();
					}
			Thread.sleep(9000);	//Faut ajouter du temps
			}
			}
		}

	}


