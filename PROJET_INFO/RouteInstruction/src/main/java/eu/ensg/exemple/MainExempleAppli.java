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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.ParserConfigurationException;

import eu.ensg.ign.Itineraire;
import eu.ensg.ign.Resultat;
import eu.ensg.osm.OSM;
import eu.ensg.portail.MapPanel;



//import Affichage.MyButton;



/**
 * 
 */
public class MainExempleAppli {
	
	public static void main(final String[] args) throws InterruptedException, ParserConfigurationException {
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
		JLabel titreInstru = new JLabel();
		titreInstru.setText("   ");
		JLabel titreInstru2 = new JLabel();
		titreInstru2.setText("   Instruction de déplacement");
		JLabel titreInstru3 = new JLabel();
		titreInstru3.setText("   ");
		final JTextArea instructiondéplacement = new JTextArea(2,30);
		instructiondéplacement.setPreferredSize(new Dimension(200, 100));
		
		JButton btn = new JButton("Calculer l'itinéraire");
		btn.setPreferredSize(new Dimension(280,30));
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
			Itineraire iti = new Itineraire(mapPanel.depart,mapPanel.arrivée);
			OSM osm = new OSM(iti);
			try {
				mapPanel.txtlst = osm.getInstructions();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		            }
			instructiondéplacement.setText(String.valueOf(mapPanel.i+1) +". " +mapPanel.txtlst.get(mapPanel.i));
		}
		        });
		
	
		JButton btn2 = new JButton("Nouvel itinéraire");
		btn2.setPreferredSize(new Dimension(280,30));
		btn2.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			textdepart.setText("");
			textarrivée.setText("");
			mapPanel.PointDepartChoisi=false;
			mapPanel.PointArrivéeChoisi=false;
	
			}
		
		});

		JButton btn3 = new JButton("Point Suivant");
		btn3.setPreferredSize(new Dimension(140,30));
	
		btn3.addActionListener(new ActionListener() {

			

			public void actionPerformed(ActionEvent e) {

				mapPanel.i=mapPanel.i+1;
				instructiondéplacement.setText(String.valueOf(mapPanel.i+1) +". " +mapPanel.txtlst.get(mapPanel.i));

				
			if (mapPanel.i>mapPanel.txtlst.size()-1) {
				instructiondéplacement.setText("fin de l'itinéraire");
			}
				
			}
		});
		JButton btn4 = new JButton("Point Précédent");
		btn4.setPreferredSize(new Dimension(140,30));
		
		btn4.addActionListener(new ActionListener() {
//			Resultat resultat= iti.getResultat();
//			List<Double[]> coords = resultat.getGeometry().getCoordinates();


			public void actionPerformed(ActionEvent e) {
//					mapPanel.setCenterPosition(mapPanel.PointGéo2PointFenêtre(coords.get(0)))
				mapPanel.i=mapPanel.i-1;
				instructiondéplacement.setText(String.valueOf(mapPanel.i+1)+". "+mapPanel.txtlst.get(mapPanel.i));
				
			}
		});
			
				
		JTextArea credit = new JTextArea();
		credit.setText(" Projet Java Itineraire 2022 "+"\n"+"       Réalisé par"+"\n"+"Noé LANGLAIS"+"\n"+"Simon DE JAEGHERE"+"\n"
				+ "Thomas DARDE"+"\n"+"Malo DE LACOUR");
				
		fen.setLocationRelativeTo(null);
		fen.setResizable(true);
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.setTitle("Route instructions");
				
		JPanel paramFenD = new JPanel(new BorderLayout());
		paramFenD.setOpaque(false);
		paramFenD.setPreferredSize(new Dimension(280, 500));
		
		JPanel paramCoord = new JPanel(new BorderLayout());
		paramCoord.setOpaque(false);
		JPanel paramBtn = new JPanel(new BorderLayout());
		paramBtn.setOpaque(false);
		JPanel paramEntre = new JPanel(new BorderLayout());
		paramEntre.setOpaque(false);
		
		paramEntre.add(paramCoord, BorderLayout.NORTH);
		paramEntre.add(paramBtn, BorderLayout.SOUTH);
		
		JPanel btnNav = new JPanel(new BorderLayout());
		btnNav.setOpaque(false);
		JPanel affInstru = new JPanel(new BorderLayout());
		affInstru.setOpaque(false);
		
		JPanel instructions = new JPanel(new BorderLayout());
		instructions.setOpaque(false);
		JPanel infoComp = new JPanel(new BorderLayout());
		infoComp.setOpaque(false);
		
				
		paramCoord.add(textdepart, BorderLayout.NORTH);
		paramCoord.add(textarrivée, BorderLayout.CENTER);

		paramBtn.add(btn, BorderLayout.NORTH);
		paramBtn.add(btn2, BorderLayout.SOUTH);
		paramCoord.add(textdepart, BorderLayout.NORTH);
		paramCoord.add(textarrivée, BorderLayout.SOUTH);
		
		btnNav.add(btn4, BorderLayout.WEST);
		btnNav.add(btn3, BorderLayout.EAST);
		affInstru.add(titreInstru2, BorderLayout.NORTH);
		affInstru.add(instructiondéplacement , BorderLayout.CENTER);
		affInstru.add(titreInstru3, BorderLayout.SOUTH);

				
		//instructions.add(instruAvance, BorderLayout.CENTER);
		instructions.add(titreInstru, BorderLayout.NORTH);
		instructions.add(btnNav, BorderLayout.CENTER);
		
		instructions.add(affInstru, BorderLayout.SOUTH);
		infoComp.add(credit, BorderLayout.NORTH);
				
		paramFenD.add(paramEntre, BorderLayout.NORTH);
		paramFenD.add(instructions, BorderLayout.CENTER);
		paramFenD.add(infoComp, BorderLayout.SOUTH);
		fen.add(paramFenD, BorderLayout.EAST);


		
		
		JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Fichier");
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
					textdepart.setText(String.valueOf(pointdépart.x).substring(0,8)+";"+String.valueOf(pointdépart.y).substring(0,8));
					mapPanel. PointDepartChoisi =true;
				}
				else if (mapPanel. PointArrivéeChoisi==false) {

					Point centre=mapPanel.getCenterPosition();
					Point addition= new Point(p.x+centre.x-mapPanel.getWidth()/2, p.y+centre.y-mapPanel.getHeight()/2);
					Point.Double pointarrivée = mapPanel.getLongitudeLatitude(addition);
					textarrivée.setText(String.valueOf(pointarrivée.x).substring(0,8)+";"+String.valueOf(pointarrivée.y).substring(0,8));

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
	
