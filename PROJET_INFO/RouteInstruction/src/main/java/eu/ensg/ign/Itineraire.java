package eu.ensg.ign;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import org.locationtech.jts.geom.Coordinate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import eu.ensg.portail.MapPanel;

public class Itineraire {
	
	
	private Point.Double pointdepart;
	private  Point.Double pointarrivee;	

	
	public Itineraire( Point.Double pointdepart,Point.Double pointarrivee) {

			this.pointdepart=pointdepart ;
			this.pointarrivee =pointarrivee;
	}

	
	public Itineraire() {
		this.pointdepart=new Point.Double(0.2046,48.0137) ;
		this.pointarrivee =new Point.Double(0.1839,48.0070);
	}	
	
	public Resultat getResultat() {

		String url = "https://wxs.ign.fr/calcul/geoportail/itineraire/rest/1.0.0/route?resource=bdtopo-pgr"
			+ "&profile=pedestrian&optimization=fastest"
			+ "&start=" + pointdepart.x +"," +pointdepart.y + "&end=" +  pointarrivee.x +"," +pointarrivee.y
			+ "&intermediates=&constraints={\"constraintType\":\"banned\",\"key\":\"wayType\",\"operator\":\"=\",\"value\":\"tunnel\"}"
			+ "&geometryFormat=geojson&crs=EPSG:4326&getSteps=true&getBbox=true&waysAttributes=nature";
		String txtJson = HttpClientIgn.request(url);
	
		Gson gson = new GsonBuilder().create();
		Resultat itineraire = gson.fromJson(txtJson, Resultat.class);
		return itineraire;
		
	}
	@Override
	public String toString() {
		String str="";
		for (Portion portion : this.getResultat().getPortions()) {

			String[] start = portion.getStart().split(",");
			double lon = Double.parseDouble(start[0]);
			double lat = Double.parseDouble(start[1]);
			String[] end = portion.getEnd().split(",");
			

			for (Step step : portion.getSteps()) {
				str+=(step.toString())+"\n";
			}
		}
	return str	;
	}
}
