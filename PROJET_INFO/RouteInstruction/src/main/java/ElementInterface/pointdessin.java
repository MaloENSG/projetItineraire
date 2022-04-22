package ElementInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class pointdessin extends JPanel{
	
	//@Override
	public void paintComponent(Graphics g,int x,int y) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setPaint (Color.red);
		g2d.drawLine(x, y, x, y);
	}

}
