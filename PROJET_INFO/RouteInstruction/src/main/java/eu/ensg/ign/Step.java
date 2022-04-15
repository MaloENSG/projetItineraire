package eu.ensg.ign;

import java.util.List;

public class Step {

	private double distance;
	public double getDistance() { return this.distance; }
	public void setDistance(double distance) { this.distance = distance; }
	
	private double duration;
	public double getDuration() { return this.duration; }
	public void setDuration(double duration) {this.duration = duration; }
	
	private Geometry geometry;
	public Geometry getGeometry() { return this.geometry; }
	public void setGeometry(Geometry geometry) { this.geometry = geometry; }
	
	@Override
	public String toString() {
		String txt = "La step est :";
		txt+= this.geometry.toString();
		return txt;
	}
}
