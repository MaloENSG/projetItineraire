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

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
		fen.setSize(800, 500);
		
		final MapPanel mapPanel = new MapPanel(); 


				//fen.setContentPane(mapPanel);
		fen.add(mapPanel, BorderLayout.CENTER);
		final JTextField textdepart = new JTextField();
	    
		final JTextField textarrivée = new JTextField();
				
		JButton btn = new JButton("Calculer l'itinéraire");
		btn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (textdepart.getText()!= null) {
				Double pointdepartx=Double.parseDouble(textdepart.getText().split(";")[0]);
				Double pointdeparty=Double.parseDouble(textdepart.getText().split(";")[1]);

				mapPanel.depart= new Point.Double(pointdepartx,pointdeparty);
				mapPanel.PointDepartChoisi=true;
			}
			if (textarrivée.getText()!= null) {
				Double pointarrivéex=Double.parseDouble(textarrivée.getText().split(";")[0]);
				Double pointarrivéey=Double.parseDouble(textarrivée.getText().split(";")[1]);

				mapPanel.arrivée= new Point.Double(pointarrivéex,pointarrivéey);
				mapPanel.CalculIti=true;
				mapPanel.PointArrivéeChoisi=true;
			}
		            }
		        });
				

     
		JLabel titreInstru = new JLabel();
		titreInstru.setText("   Instruction de déplacement");
				
				
				
		JLabel credit = new JLabel();
		credit.setText("   Projet Java Itineraire 2022 "+"\n"+"   Réalisé par");
				
		fen.setLocationRelativeTo(null);
		fen.setResizable(true);
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.setTitle("Route instructions");
				
		JPanel paramFenD = new JPanel(new BorderLayout());
		paramFenD.setOpaque(false);
		JPanel paramCoord = new JPanel(new BorderLayout());
		paramCoord.setOpaque(false);
		JPanel instructions = new JPanel(new BorderLayout());
		instructions.setOpaque(false);
		JPanel infoComp = new JPanel(new BorderLayout());
		infoComp.setOpaque(false);
				
		paramCoord.add(textdepart, BorderLayout.NORTH);
		paramCoord.add(textarrivée, BorderLayout.CENTER);

		paramCoord.add(btn, BorderLayout.SOUTH);
				
		//instructions.add(instruAvance, BorderLayout.CENTER);
		instructions.add(titreInstru, BorderLayout.NORTH);
		
		infoComp.add(credit, BorderLayout.NORTH);
				
		paramFenD.add(paramCoord, BorderLayout.NORTH);
		paramFenD.add(instructions, BorderLayout.CENTER);
		paramFenD.add(infoComp, BorderLayout.SOUTH);
		fen.add(paramFenD, BorderLayout.EAST);


		
		
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
		mapPanel.setCenterPosition(position); // sets to the computed position
		//mapPanel.repaint(); // if already visible trigger a repaint here
		
		
		mapPanel.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				if (mapPanel. PointDepartChoisi==false) {
					
					Point centre=mapPanel.getCenterPosition();
					Point addition= new Point(p.x+centre.x-mapPanel.getWidth()/2, p.y+centre.y-mapPanel.getHeight()/2);
					Point.Double pointdépart = mapPanel.getLongitudeLatitude(addition);
					textdepart.setText(String.valueOf(pointdépart.x)+";"+String.valueOf(pointdépart.y));
					mapPanel. PointDepartChoisi =true;
					System.out.println("point de départ choisi");
				}
				else if (mapPanel. PointArrivéeChoisi==false) {

					Point centre=mapPanel.getCenterPosition();
					Point addition= new Point(p.x+centre.x-mapPanel.getWidth()/2, p.y+centre.y-mapPanel.getHeight()/2);
					Point.Double pointarrivée = mapPanel.getLongitudeLatitude(addition);
					textarrivée.setText(String.valueOf(pointarrivée.x)+";"+String.valueOf(pointarrivée.y));

					System.out.println("point d'arrivée choisi");
				}
			
//				mapPanel.repaint();
	
			}
			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			
			}

			public void mouseClicked(MouseEvent e) {
			}
		});
//		

		
	}

	
}