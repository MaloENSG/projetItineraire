
package ElementInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class RectPanel extends JPanel{
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int w = this.getWidth();
		int h = this.getHeight ();
		int sizeW = 200; // largeur du rectangle qu'on va acher
		int sizeh = 800; // hauteur du rectangle qu'on va acher
		g2d.setPaint (Color.blue);
		g2d.fillRect (20, 0, 400, 800 );
		
		//Test push git
		
	}
}