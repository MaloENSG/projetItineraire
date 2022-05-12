package eu.ensg.appli;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import eu.ensg.ign.Itineraire;
import eu.ensg.portail.MapPanel;
import java.awt.Point;

public class ChoixDepartArrivée implements MouseListener{
	
	private Point.Double depart;
	private Point.Double arrivée;
	private boolean PointDepartChoisi;
	private boolean PointArrivéeChoisi;
	private MapPanel mapPanel;
	private Itineraire itineraire;
	
	public ChoixDepartArrivée(MapPanel mapPanel) {
		this.depart=new Point.Double();
		this.arrivée= new Point.Double();
		this.PointArrivéeChoisi= false;
		this.PointDepartChoisi=false;
		this.mapPanel=mapPanel;
		this.itineraire=new Itineraire(depart, arrivée);
	}
	
	public void mouseClicked(MouseEvent e) {
		if (this.PointDepartChoisi==false) {
			Point p = e.getPoint();

			this.PointDepartChoisi=true ;
			Point centre=mapPanel.getCenterPosition();
			Point addition= new Point(p.x+centre.x-mapPanel.getWidth()/2, p.y+centre.y-mapPanel.getHeight()/2);
			this.depart=mapPanel.getLongitudeLatitude(addition);

			System.out.println("point de départ choisi"+depart);

		}
		else if (this.PointArrivéeChoisi==false) {
			this.PointArrivéeChoisi=true ;
			this.arrivée=mapPanel.getLongitudeLatitude(mapPanel.getCursorPosition());
			System.out.println("point d'arrivée choisi"+arrivée);
		}
		this.itineraire=new Itineraire(depart,arrivée);
		
	}
	
	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public Point.Double getDepart() {
		return depart;
		
	}
	public Point.Double getArrivée() {
		return arrivée;
	}
	
	public boolean PointDepartChoisi() {
		return PointDepartChoisi;
	}
	public boolean PointArrivéeChoisi() {
		return PointArrivéeChoisi;
	}
	
	public Itineraire getItineraire() {
		return this.itineraire;
	}
}