package eu.ensg.ign;

import java.util.List;

public class Portion {

	private double distance;
	public double getDistance() { return this.distance; }
	public void setDistance(double distance) { this.distance = distance; }
	
	private String start;
	public String getStart() { return this.start; }
	public void setStart(String start) { this.start = start; }
	
	private String end;
	public String getEnd() { return this.end; }
	public void setEnd(String end) { this.end = end; }

	private double duration;
	public double getDuration() { return this.duration; }
	public void setDuration(double duration) {this.duration = duration; }
	
	private List<Step> steps;
	public List<Step> getSteps() { return this.steps; }
	public void setSteps(List<Step> steps) { this.steps = steps; }
	
	@Override
	public String toString() {
		String txt = "La portion est :";
		for(int i=0;i<this.steps.size();i++) {
			txt+= this.steps.get(i).toString();
		}
		return txt;
	}
}
