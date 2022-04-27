package eu.ensg.exemple;

import org.locationtech.jts.geom.Coordinate;

import eu.ensg.ign.Itineraire;

public class MainExempleItineraireIgn {

	public static void main(String[] args) {
		
		Coordinate C1= new Coordinate(0.2046,48.0137);
		Coordinate C2 = new Coordinate(0.1839,48.0070);
		Itineraire iti = new Itineraire(C1, C2);
		System.out.println(iti);
		
	}	
}
