/*******************************************************************************
 * 
 * @copyright IGN - 2018
 * 
 * This software is released under the licence CeCILL
 * see <a href="https://fr.wikisource.org/wiki/Licence_CeCILL_version_2">
 * https://fr.wikisource.org/wiki/Licence_CeCILL_version_2</a>
 *
 ******************************************************************************/
package eu.ensg.exemple;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import eu.ensg.portail.MapPanel;



/**
 * 
 */
public class MainExempleAppli {
	
	public static void main(final String[] args) {
		try {
			String os = System.getProperty("os.name").toLowerCase();
			// For windows os
			if (os.contains("windows")) {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			}
			// For linux os
			if ((os.contains("linux")) || (os.contains("unix"))) {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			}
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		
        
        // ======================================================
        //    
		final JFrame fen = new JFrame();
		fen.setSize(500, 500);

		// just a JPanel extension, add to any swing/awt container
		final MapPanel mapPanel = new MapPanel(); 
		fen.setContentPane(mapPanel);
		fen.setLocationRelativeTo(null);
		fen.setResizable(false);
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.setTitle("Route instructions");
		
		
		JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem item = new JMenuItem("Exit");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fen != null) {
                    fen.dispose();
                }
            }
        });
        fileMenu.add(item);
        menuBar.add(fileMenu);
		
        
        JMenu tileServerMenu = new JMenu("Autres");
        menuBar.add(tileServerMenu);
        fen.setJMenuBar(menuBar);
    	fen.setVisible(true);

		mapPanel.setZoom(15); // set some zoom level (1-18 are valid)
		double lon =0.2046;
		double lat = 48.01376;
		Point position = mapPanel.computePosition(new Point2D.Double(lon, lat));
		System.out.println(position);

		mapPanel.setCenterPosition(position); // sets to the computed position

		Point centre=mapPanel.getCenterPosition();
		System.out.println(centre);
		//mapPanel.repaint(); // if already visible trigger a repaint here

	
		
		mapPanel.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				p.x=p.x;
				p.y=p.y;
				Graphics g =fen.getGraphics();

		
				System.out.println("Les coordonnées graphiques de la souris: " + p.x + "," + p.y);
				Point centre=mapPanel.getCenterPosition();
				Point addition= new Point(p.x+centre.x-mapPanel.getWidth()/2, p.y+centre.y-mapPanel.getHeight()/2);
				Point.Double geopoint = mapPanel.getLongitudeLatitude(addition);

				System.out.println(geopoint);
				mapPanel.repaint();
			}
			
			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});
		
		
		
	}

	
}