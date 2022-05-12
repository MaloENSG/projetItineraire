package eu.ensg.ign;

import java.util.Scanner;

public class PointGéo {
	
	private double Longitude;
	private double Latitude;

	public PointGéo(String texte) {
		Scanner scan = new Scanner(System.in);
		System.out.println(texte);
		String lon =scan.nextLine().split(";")[0];
		this.Longitude  = Double.parseDouble(lon);
		String lat =scan.nextLine().split(";")[1];
		this.Latitude  = Double.parseDouble(lat);
	}
	public double getLatitude() {
		return this.Latitude;
		
	}
	public double getLongitude() {
		return this.Longitude;
		
	}
}
