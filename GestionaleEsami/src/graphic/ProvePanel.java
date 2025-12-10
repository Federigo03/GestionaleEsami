package graphic;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import esame.*;

/**
 * ProvePanel estende JPanel e permette di visualizzare i dati delle prove intermedie degli esami composti
 * @author Federico Rigolon
 */

public class ProvePanel extends JPanel {

	/**
	 * Crea il pannello e tutti i testi
	 * @param e esame composto da cui prendere i dati
	 */

	public ProvePanel(Esame_composto e) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel text = new JLabel("Esame di " + e.getStudente() + " di " + e.getInsegnamento());
		text.setAlignmentX(CENTER_ALIGNMENT);
		add(text);
		int nProve = e.getNProve();
		for(int i = 0; i < nProve; i++) {
			JLabel peso = new JLabel("Peso prova " + (i + 1) + ": " + e.getPesi()[i] + "%");
			peso.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(peso);
			JLabel voto = new JLabel("Voto prova " + (i + 1) + ": " + e.getVoti()[i]);
			voto.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(voto);
		}
	}

}
