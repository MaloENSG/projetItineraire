package ElementInterface;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class TextPan extends JPanel{
	private void doDrawing(Graphics g,String texte) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawString(texte, 50, 50);
    }

    
    public void paintComponent(Graphics g,String texte) {

        super.paintComponent(g);
        doDrawing(g,texte);
    }

}
