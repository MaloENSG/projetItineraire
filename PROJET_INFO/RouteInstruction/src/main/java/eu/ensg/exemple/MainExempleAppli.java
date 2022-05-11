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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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

import ElementInterface.RectPanel;
//import Affichage.MyButton;
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
		fen.setSize(1200, 800);
		

		// just a JPanel extension, add to any swing/awt container
		final MapPanel mapPanel = new MapPanel(); 

		//fen.setContentPane(mapPanel);
		fen.add(mapPanel, BorderLayout.CENTER);
		
		JButton btn = new JButton("Afficher Message !");
		btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("ceci est un msg !");
            }
        });
		
		final JTextField text = new JTextField();
		text.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Wahou" + text.getText());
            }
        });
		
		JLabel titreInstru = new JLabel();
		titreInstru.setText("   Instruction de déplacement");
		
		
		
		
		JLabel credit = new JLabel();
		credit.setText("   Projet Java Itineraire 2022 "+"\n"+"   Réalisé par");
		
		fen.setLocationRelativeTo(null);
		fen.setResizable(false);
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
		paramFenD.setPreferredSize( new Dimension (200, 0));
		
		paramCoord.add(text, BorderLayout.NORTH);
		paramCoord.add(btn, BorderLayout.SOUTH);
		
		//instructions.add(instruAvance, BorderLayout.CENTER);
		instructions.add(titreInstru, BorderLayout.NORTH);
		
		infoComp.add(credit, BorderLayout.NORTH);
		
		paramFenD.add(paramCoord, BorderLayout.NORTH);
		paramFenD.add(instructions, BorderLayout.CENTER);
		paramFenD.add(infoComp, BorderLayout.SOUTH);
		fen.add(paramFenD, BorderLayout.EAST);
		
		
		
		//Menu de fenetre
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
        
        // Ajout de bouton sur la fenetre
        //JButton btn = new JButton("Afficher point");
	    //btn.setBounds(20,250,100,30);
        //fen.add(btn);
        
        // Ajout champs texte
        //JTextField text = new JTextField();
        //text.setBounds(20,160,100,20);
        //fen.add(text);
        
        

		mapPanel.setZoom(15); // set some zoom level (1-18 are valid)
		double lon = 0.199556;
		double lat = 48.00611;
		Point position = mapPanel.computePosition(new Point2D.Double(lon, lat));
		mapPanel.setCenterPosition(position); // sets to the computed position
		mapPanel.repaint(); // if already visible trigger a repaint here

		fen.setVisible(true);
		
		
		mapPanel.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				System.out.println("Les coordonnées graphiques de la souris: " + p.x + "," + p.y);
				// mapPanel.repaint();
				Point.Double k = mapPanel.getLongitudeLatitude(p);
				System.out.println(k);
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
