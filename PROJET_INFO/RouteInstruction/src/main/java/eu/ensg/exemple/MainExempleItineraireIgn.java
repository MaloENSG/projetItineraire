package eu.ensg.exemple;

import java.util.Arrays;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.ensg.ign.Geometry;
import eu.ensg.ign.HttpClientIgn;
import eu.ensg.ign.Itineraire;
import eu.ensg.ign.Portion;
import eu.ensg.ign.Resultat;
import eu.ensg.ign.Step;


public class MainExempleItineraireIgn {

	public static void main(String[] args) {
		
		Coordinate C1= new Coordinate(0.2046,48.0137);
		Coordinate C2 = new Coordinate(0.1839,48.0070);
		Itineraire iti = new Itineraire(C1, C2);
		System.out.println(iti);
		
	}	
}
