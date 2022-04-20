package ElementInterface;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class TextPan extends JPanel{
	private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawString("Départ", 50, 50);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

}
