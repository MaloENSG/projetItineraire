package eu.ensg.exemple;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.ensg.ign.HttpClientIgn;
import eu.ensg.ign.Portion;
import eu.ensg.ign.Resultat;
import eu.ensg.ign.Step;
import eu.ensg.osm.Distance;
import eu.ensg.osm.HttpClientOsm;

public class MainNoéGrosseEmprise {

		static Double[] pointdep = {0.2046,48.0137};
		static Double[] pointarr = {0.1839,48.0070};
		
		static double Es = Math.max(pointdep[0], pointarr[0])+0.05;
		static double Ou = Math.min(pointdep[0], pointarr[0])-0.05;
		static double Su = Math.min(pointdep[1], pointarr[1])-0.05;
		static double No = Math.max(pointdep[1], pointarr[1])+0.05;
		
		static double lonpro;
		static double latpro;
		static String clepro;
		static String valproc;
			    	
		static List<String> clelst = new ArrayList<String>();
		static List<String> vallst = new ArrayList<String>();
		static List<Double[]> coordlst = new ArrayList<Double[]>();
		
		static String[] amen = {"bar", "biergaten", "cafe", "fast_food", "ice_cream", "pub", "restaurant", "college", "driving_school", "kindergarten", "library", "music_school", "school", "university", "taxi", "bank", "clinic", "dentist", "doctors", "hospital", "nursing_home", "pharmacy", "veterinary", "arts_centre", "casino", "cinema", "fountain", "nightclub", "planetarium", "stripclub", "studio", "theatre", "courthouse", "embassy", "fire_station", "police", "post_box", "post_office", "prison", "townhall", "crematorium", "funeral_hall", "grave_yard", "gym", "marketplace", "monastery"};
		
		static String valpro="";
						    
		static double mini = 1000000000;
		static double latmin = 0;
		static double longmin = 0;
		

		
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
						
						clelst.add(cle);
						vallst.add(val);
						Double[] coord = {lat,lon};
						coordlst.add(coord);
							
			    	}
			    	
			    }
			String url = "https://wxs.ign.fr/calcul/geoportail/itineraire/rest/1.0.0/route?resource=bdtopo-pgr"
					+ "&profile=pedestrian&optimization=fastest"
					+ "&start="+pointarr[0]+","+pointarr[1]+"&end="+pointdep[0]+","+pointdep[1]
					+ "&intermediates=&constraints={\"constraintType\":\"banned\",\"key\":\"wayType\",\"operator\":\"=\",\"value\":\"tunnel\"}"
					+ "&geometryFormat=geojson&crs=EPSG:4326&getSteps=true&getBbox=true&waysAttributes=nature";
			String txtJson = HttpClientIgn.request(url);
			System.out.println(url);
			
			Gson gson = new GsonBuilder().create();
			Resultat itineraire = gson.fromJson(txtJson, Resultat.class);
			int p = -1;
			for (Portion portion : itineraire.getPortions()) {
				
				p += 1;
				
				String[] start = portion.getStart().split(",");
				double lon = Double.parseDouble(start[0]);
				double lat = Double.parseDouble(start[1]);
				String[] end = portion.getEnd().split(",");
				
				String nompro = "test";
				String natpro = "test";
				int o = 0;
				
				Double[] dercoord = {(double) 0,(double) 0};
				Double[] adercoord = {(double) 0,(double) 0};
				
				for (Step step : portion.getSteps()) {
					o += 1;
					
					String txt="";
					
					List<Double[]> coords = step.getGeometry().getCoordinates();
					double dist = step.getDistance();
					String nom = step.getAttributes().getNom();
					String nat = step.getAttributes().getNature();
					
					if(nom != "") {System.out.println("Avancer de "+dist+" m dans "+nom);}
					else {System.out.println("Avancer de "+dist+" m dans "+nat);}
					
					if(o == portion.getSteps().size()) {System.out.println("Vous êtes arrivés dans la bonne rue");}
					else {
					nompro = portion.getSteps().get(o).getAttributes().getNom();
					natpro = portion.getSteps().get(o).getAttributes().getNature();
					
					if(nom.equals(nompro) && nat.equals(natpro) ) {
						System.out.println("Continuer dans la rue");
					}else {
					
					//Choix batiment & determination de sa direction visuelle
					
					if(coords.size()<2) {
						adercoord = dercoord; 
					}else {
					adercoord = coords.get(coords.size()-2);
					}
					
					dercoord = coords.get(coords.size()-1);
					

								
						    	for (int j = 0; j < clelst.size(); j++) {
									String cl = clelst.get(j);
									String va = vallst.get(j);
									Double[] coo = coordlst.get(j);
									
									for (int u = 0; u<amen.length;u++) {
									if (cl.equals("name") || (cl.equals("amenity")&& va.equals(amen[u]))) {
										double distance = Distance.dist(coo[1], coo[0], dercoord[1], dercoord[0]);

										if(min>distance) {
											min = distance;
											valpro = va;
											latmin = coo[1];
											longmin = coo[2];
										}
									}
									}		
						    	}
						    	
						    }
//						    double angle1 = Distance.angl(dercoord[1],dercoord[0],latmin,longmin,adercoord[1],adercoord[0]);
//						    double angle2 = Distance.angl(dercoord[1],dercoord[0],itineraire.getPortions().get(p).getSteps().get(o).getGeometry().getCoordinates().get(0)[1],itineraire.getPortions().get(p).getSteps().get(o).getGeometry().getCoordinates().get(0)[0],adercoord[1],adercoord[0]);
//						    
//						    if((angle2*180/Math.PI)<160) {txt+= "Tourner à droite";}
//						    else if((angle2*180/Math.PI)>200){txt+= "Tourner à gauche";}
//						    else {txt += "Aller tout droit";}
//						    
//						    if(Math.abs(angle1*180/Math.PI)>100&&Math.abs(angle1*180/Math.PI)<260) {
//						    	txt += " avant " + valpro;
//						    }else if(Math.abs(angle1*180/Math.PI)<80||Math.abs(angle1*180/Math.PI)>280){
//						    	txt += " après " + valpro;
//						    }else {
//						    	txt += " au niveau de " + valpro;
//						    }
//						    System.out.println(txt);
						    
	}

}
				}} catch (Exception e) {
				e.printStackTrace();
			}
			}
		}


