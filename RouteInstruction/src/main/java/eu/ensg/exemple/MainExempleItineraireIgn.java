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


public class MainExempleItineraireIgn {

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
			System.out.println(portion.toString());
			
			for (Step step : portion.getSteps()) {
				Geometry coords = step.getGeometry();
				System.out.println(coords.toString());
			}
			
			System.out.println(lon + "," + lat + "--" + Arrays.toString(end));
		}
	}
	
}
