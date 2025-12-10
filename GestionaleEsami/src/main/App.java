package main;

import graphic.*;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Federico Rigolon
 */

public class App {
	/**
	 * è il main dell'applicazione. Crea il frame principale e gestisce la terminazione del programma
	 * chiedendo all'utente se vuole salvare le ultime modifiche.
	 */
    public static void main(String[] args) throws Exception {
        MyFrame mainFrame = new MyFrame("Gestione Esami", 0, 0, (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.5), (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.5));
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        MainPanel panel = new MainPanel();
        mainFrame.add(panel);
        mainFrame.revalidate();
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	if(panel.getModel().isSaved())
            		mainFrame.dispose();
            	else {
            		MyFrame saveFrame = new MyFrame(null, 0, 0, (int)(Toolkit.getDefaultToolkit().getScreenSize().width/3), (int)(Toolkit.getDefaultToolkit().getScreenSize().height/7));
	            	JPanel savePanel = new SavePanel(mainFrame, saveFrame, panel);
	        		saveFrame.getContentPane().add(savePanel);
            	}
            }
        });
    }
}
