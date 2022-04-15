package eu.ensg.ign;

import java.util.List;

public class Resultat {
	
	private String profile;
	public void setProfile(String profile) { this.profile = profile; }
	public String getProfile() { return this.profile; }
	
	// ....
	
	private List<Portion> portions;
	public List<Portion> getPortions() { return this.portions; }
	public void setPortions(List<Portion> portions) { this.portions = portions; }
	
	@Override
	public String toString() {
		String txt = "Les portions sont :";
		for(int i=0;i<this.portions.size();i++) {
			txt+= this.portions.get(i);
		}
		return txt;
	}
}
