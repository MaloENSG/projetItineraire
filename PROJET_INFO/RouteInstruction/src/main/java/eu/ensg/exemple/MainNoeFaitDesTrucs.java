package eu.ensg.exemple;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.ensg.ign.Geometry;
import eu.ensg.ign.HttpClientIgn;
import eu.ensg.ign.Portion;
import eu.ensg.ign.Resultat;
import eu.ensg.ign.Step;

public class MainNoeFaitDesTrucs {

	public static void main(String[] args) {
		
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
				if(nom != null) {System.out.println("Avancer de "+dist+" m dans "+nom);}
				else {System.out.println("Avancer de "+dist+" m dans "+step.getAttributes().getNature());}
			}
		}

	}

}
