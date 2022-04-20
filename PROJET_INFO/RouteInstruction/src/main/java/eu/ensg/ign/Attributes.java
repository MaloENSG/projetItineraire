package eu.ensg.ign;

public class Attributes {
	
	private String nom_1_gauche;
	
	public String getNom() { return this.nom_1_gauche; }
	public void setNom(String nom_1_gauche) { 
		this.nom_1_gauche = nom_1_gauche; 
		}
	
	private String nature;
	public String getNature() {
		return this.nature;
		}
	
	public void setStart(String nature) { 
		this.nature = nature; 
		}

}
