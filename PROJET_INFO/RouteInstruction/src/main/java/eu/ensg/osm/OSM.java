package eu.ensg.osm;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import eu.ensg.ign.Itineraire;
import eu.ensg.ign.Portion;
import eu.ensg.ign.Resultat;
import eu.ensg.ign.Step;

public class OSM {
	
	private List<String> txtlst = new ArrayList<String>(); 
	private Resultat resultat;
	
	// Affectation liste instructions et affectation des valeurs du gson
	public OSM (Itineraire iti) {
		this.txtlst=new ArrayList<String>();
		this.resultat=iti.getResultat();
	}

	// Creation des instructions pour l'itineraire
	public List<String> getInstructions() throws InterruptedException, ParserConfigurationException {
		int p = -1;
	
		
		for (Portion portion : this.resultat.getPortions()) {
			p+=1;
			
			String[] start = portion.getStart().split(",");
			double lon = Double.parseDouble(start[0]);
			double lat = Double.parseDouble(start[1]);
			String[] end = portion.getEnd().split(",");
			
			String nompro = "test";
			String natpro = "test";
			double o = 0;
			
			double pourcentage = 0;
			
			Double[] dercoord = {(double) 0,(double) 0};
			Double[] adercoord = {(double) 0,(double) 0};
			
		
			for (Step step : portion.getSteps()) {
				o += 1;
				String txt = "";
				pourcentage +=Math.rint(( o/portion.getSteps().size())*100);
				System.out.println(pourcentage);
				
				List<Double[]> coords = step.getGeometry().getCoordinates();
				double dist = step.getDistance();
				String nom = step.getAttributes().getNom();
				String nat = step.getAttributes().getNature();
				
				if(nom != "") {txtlst.add("Avancer de "+dist+" m \ndans "+nom+". ");}
				else {txtlst.add("Avancer de "+dist+" m \ndans "+nat+". ");}
				
				if(o == portion.getSteps().size()) {txtlst.add("Vous êtes arrivés \nà destination");}
				else {
				nompro = portion.getSteps().get((int) o).getAttributes().getNom();
				natpro = portion.getSteps().get((int) o).getAttributes().getNature();
				
				if(nom.equals(nompro) && nat.equals(natpro) ) {
					double angle2 = Distance.angleBetweenTwoPointsWithFixedPoint(resultat.getPortions().get(p).getSteps().get((int) o).getGeometry().getCoordinates().get(0)[1],resultat.getPortions().get(p).getSteps().get((int) o).getGeometry().getCoordinates().get(0)[0],adercoord[1],adercoord[0],dercoord[1],dercoord[0]);
					if((angle2*180/Math.PI)>200) {txt += "Continuer à droite \ndans la même rue";}
					else if((angle2*180/Math.PI)<160){txt += "Continuer à gauche \ndans la même rue";}
					else {txt += "Continuer tout droit \ndans la même rue";}
//					System.out.println(txt);
				}else {
				
				//Choix batiment & determination de sa direction visuelle
				
				if(coords.size()<2) {
					adercoord = dercoord; 
				}else {
				adercoord = coords.get(coords.size()-2);
				}
				
				dercoord = coords.get(coords.size()-1);
				
				for(int k = 0;k<coords.size();k++) {if(nom != "") {txtlst.add("Il reste "+Math.round(Distance.dist(coords.get(k)[1], coords.get(k)[0], dercoord[1], dercoord[0]))+" m à parcourir \ndans "+nom+". ");}else {txtlst.add("Il reste "+Math.round(Distance.dist(coords.get(k)[1], coords.get(k)[0], dercoord[1], dercoord[0]))+" m à parcourir \ndans "+nat+". ");}}
				
				double E = dercoord[0]+0.002;
				double O = dercoord[0]-0.002;
				double S = dercoord[1]-0.002;
				double N = dercoord[1]+0.002;
				
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
						double latmin = 0;
						double longmin = 0;
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
								String[] amen = {"bar", "biergaten", "cafe", "fast_food", "ice_cream", "pub", "restaurant", "college", "driving_school", "kindergarten", "library", "music_school", "school", "university", "taxi", "bank", "clinic", "dentist", "doctors", "hospital", "nursing_home", "pharmacy", "veterinary", "arts_centre", "casino", "cinema", "fountain", "nightclub", "planetarium", "stripclub", "studio", "theatre", "courthouse", "embassy", "fire_station", "police", "post_box", "post_office", "prison", "townhall", "crematorium", "funeral_hall", "grave_yard", "gym", "marketplace", "monastery"};
								
								for (int u = 0; u<amen.length;u++) {
								if (cle.equals("name") || (cle.equals("amenity")&& val.equals(amen[u]))) {
									double distance = Distance.dist(latit, longit, middlat, middlon);

									if(min>distance) {
										min = distance;
										valpro = val;
										latmin = latit;
										longmin = longit;
									}
								}
								}		
					    	}
					    	
					    }
					    double angle1 = Distance.angleBetweenTwoPointsWithFixedPoint(latmin,longmin,adercoord[1],adercoord[0],dercoord[1],dercoord[0]);
					    double angle2 = Distance.angleBetweenTwoPointsWithFixedPoint(this.resultat.getPortions().get(p).getSteps().get((int) o).getGeometry().getCoordinates().get(0)[1],this.resultat.getPortions().get(p).getSteps().get((int) o).getGeometry().getCoordinates().get(0)[0],adercoord[1],adercoord[0],dercoord[1],dercoord[0]);
					    
//					    System.out.println(Math.toDegrees(angle1));
//					    System.out.println(Math.toDegrees(angle2));
					    
					    if((angle2*180/Math.PI)>200) {txt+= "\nTourner à droite";}
					    else if((angle2*180/Math.PI)<160){txt+= "\nTourner à gauche";}
					    else {txt += "\nAller tout droit";}
					    
					    if(Math.abs(angle1*180/Math.PI)>100&&Math.abs(angle1*180/Math.PI)<260) {
					    	txt += " avant " + valpro;
					    }else if(Math.abs(angle1*180/Math.PI)<80||Math.abs(angle1*180/Math.PI)>280){
					    	txt += " après " + valpro;
					    }else {
					    	txt += " au niveau de " + valpro;
					    }
					    txtlst.add(txt);
					    
					    
					} catch (Exception e) {
						e.printStackTrace();
					}
			Thread.sleep(25000);
			}
			}
			}
			}
		

		return txtlst;
		
	}
}
