package graphic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

/**
 * StatsPanel estende JPanel. è un pannello che permette la vista di un istogramma statistico degli esami di un unico studente o di un unico insegnamento
 * @author Federico Rigolon
 */

public class StatsPanel extends JPanel {

	JTable table;
	String studOrIns;
	int nRows, votoMax = 18, voti = 31 - 18;
	int[] FreqVoti;

	/**
	 * Costruttore. calcola le frequenze dei voti
	 * @param table tabella contenente i soli dati filtrati
	 */
	
	public StatsPanel(JTable table) {
		this.table = table;
		nRows = table.getRowCount();
		if(nRows > 1) {
			if(table.getValueAt(0, 1).toString().compareToIgnoreCase(table.getValueAt(1, 1).toString()) == 0)
				studOrIns = table.getValueAt(0, 1).toString();
			else
				studOrIns = table.getValueAt(0, 0).toString();
		}
		else
			studOrIns = table.getValueAt(0, 0).toString();
		setLayout(new BorderLayout());
		JLabel label = new JLabel(studOrIns);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		add(label, BorderLayout.NORTH);
		FreqVoti = new int[voti];
		for(int i = 18; i < 31; i++) {
			FreqVoti[i - 18] = 0;
			for(int j = 0; j < nRows; j++)
				if(i == (Integer) table.getValueAt(j, 2))
					FreqVoti[i - 18]++;
		}
	}

	/**
	 * Disegna le barre dell'istogramma e le etichette
	 * @param g
	 */
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int width = getWidth() * 9 / 10;
		int height = getHeight() * 9 / 10;
        int widthBar = width / voti;
        for(int i = 0; i < voti; i++) {
        	int heightBar = (int) ((double) FreqVoti[i] * height / nRows);
        	int x = i * widthBar + getWidth() / 20, y = height - heightBar ;
        	g2d.setColor(Color.GREEN);
        	g2d.fillRect(x, y, widthBar, heightBar);
        	g2d.setColor(Color.BLACK);
        	g2d.drawString(String.valueOf(i + 18), x + (widthBar / 2), height - height / 50);
        }
        int x = widthBar / 2;
        int ySpan = nRows / 8;
        if(ySpan < 1)
        	ySpan = 1;
        for(int i = 1; i < nRows; i += ySpan) {
        	int y = height - height * i / nRows;
        	g2d.setColor(Color.BLACK);
        	g2d.drawString(String.valueOf(i), x, y);
        }
	}
}
