package graphic;

import javax.swing.JFrame;

/**
 * MyFrame estende JFrame e permette di settare qualche opzione di base che servirà in tutte le finestre
 * @author Federico Rigolon
 */

public class MyFrame extends JFrame {

    /**
     * Crea una finestra visibile, dimensionata e che si chiude senza arrestare il programma
     * @param titolo titolo della finestra
     * @param x locazione sulle ascisse
     * @param y locazione sulle ordinate
     * @param width larghezza finestra
     * @param height altezza finestra
     */

    public MyFrame(String titolo, int x, int y, int width, int height){
        super(titolo);
        setBounds(x, y, width, height);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}