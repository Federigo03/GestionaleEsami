package graphic;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * SavePanel estende JPanel e implementa ActionListener. permette attraverso 2 pulsanti di scegliere se salvare le ultime modifiche prima di arrestare il programma oppure no
 * @author Federico Rigolon
 */

public class SavePanel extends JPanel implements ActionListener {
	
	private MyFrame mainFrame, frame;
	private MainPanel pan;
	private JButton save, close;

	/**
	 * Costruttore
	 * @param mainFrame frame principale
	 * @param frame frame che contiene il SavePanel corrente
	 * @param pan pannello contenuto nel frame principale e che contiene la tabella
	 */
	
	public SavePanel(MyFrame mainFrame, MyFrame frame, MainPanel pan) {
		this.mainFrame = mainFrame;
		this.frame = frame;
		this.pan = pan;
		setLayout(new BorderLayout());
		JLabel text = new JLabel("Desideri salvare le modifiche?");
		text.setAlignmentX(CENTER_ALIGNMENT);
		text.setAlignmentY(CENTER_ALIGNMENT);
		add(text, BorderLayout.NORTH);
		save = new JButton("Sì");
		close = new JButton("No");
		save.setAlignmentX(RIGHT_ALIGNMENT);
		save.setAlignmentY(CENTER_ALIGNMENT);
		close.setAlignmentX(LEFT_ALIGNMENT);
		close.setAlignmentY(CENTER_ALIGNMENT);
		save.addActionListener(this);
		close.addActionListener(this);
		add(save, BorderLayout.WEST);
		add(close, BorderLayout.EAST);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == save)
			if(pan.saveTable()) {
				frame.dispose();
				mainFrame.dispose();
			}
			else
				frame.dispose();
		else if(e.getSource() == close) {
			frame.dispose();
			mainFrame.dispose();
		}
	}
}
