package eu.ensg.osm;

public class Distance {
	
	public static double toRad(double angdeg) {
		return Math.toRadians(angdeg);
	}
	
	public static double dist(double lat1, double lon1, double lat2, double lon2) { 
		
		//  
		double dLat = toRad(lat2 - lat1); 
		double dLon = toRad(lon2 - lon1); 

		// convertit en radians les latitudes 
		lat1 = toRad(lat1); 
		lat2 = toRad(lat2); 

		// On applique la formule
		double a = Math.pow(Math.sin(dLat / 2), 2) +  Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2); 
		double c = 2 * Math.asin(Math.sqrt(a));
		double rad = 6371; 
		
		return rad * c;
	} 
	
	public static double angl(double lat1, double lon1, double lat2, double lon2, double lat3, double lon3) {
		double a = dist(lat2,lon2,lat3,lon3);
		double b = dist(lat1,lon1,lat3,lon3);
		double c = dist(lat2,lon2,lat1,lon1);
		double calc1 = Math.pow(b, 2)+Math.pow(c, 2)-Math.pow(a, 2);
		double calc2 = 2*c*b;
		double fin = Math.acos(calc1/calc2);
		return fin;
	}
	
	public static double angleBetweenTwoPointsWithFixedPoint(double point1X, double point1Y, double point2X, double point2Y, double fixedX, double fixedY) {

	    double angle1 = Math.atan2(point1Y - fixedY, point1X - fixedX);
	    double angle2 = Math.atan2(point2Y - fixedY, point2X - fixedX);

	    double result = (angle1 - angle2);
	    if(result<0) {return result +2*Math.PI;}
	    else {return result;}
	}



}
