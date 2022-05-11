package eu.ensg.exemple;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

public class BtnPane {
	public JButton makeBtn(Action action) {
		JButton b = new JButton(action);
		return b;
	}
	
	public BtnPane() {
		
		Action btnAction = new AbstractAction() {
			{
				String text = "Calculer le chemin";
				putValue(Action.NAME, text);
				putValue(Action.SHORT_DESCRIPTION, text);
			}

			public void actionPerformed(ActionEvent e) {
				System.out.println("ceci est un msg !");
			}
		};
		
	}

}
