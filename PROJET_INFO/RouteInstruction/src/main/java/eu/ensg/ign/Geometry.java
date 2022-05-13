package eu.ensg.ign;

import java.awt.Point;
import java.util.List;

public class Geometry {
	
	private String type;
	public String getType() { return this.type; }
	public void setType(String type) { this.type = type; }
	
	private List<Double[]> coordinates;
	public List<Double[]> getCoordinates() { return this.coordinates; }
	public void setCoordinates(List<Double[]> coordinates) { this.coordinates = coordinates; }
	
	@Override
	public String toString() {
		String txt = "Les coordonnées sont :";
		for(int i=0;i<this.coordinates.size();i++) {
			txt+= "["+this.coordinates.get(i)[0]+", ";
			txt+= this.coordinates.get(i)[1]+"] ; ";
		}
		return txt;
	}

}
