package esame;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import graphic.MyFrame;

/**
 * La classe esame contiene tutti i dati di un esame semplice e ne controlla la correttezza.
 * @author Federico Rigolon
 */

public class Esame {

    private String studente;
    private String insegnamento;
    private int voto;
    private boolean lode;
    private int crediti;
    
    /**
     * Costruttore.
     * @param studente nome e cognome dello studente che ha sostenuto l'esame
     * @param insegnamento nome del corso
     * @param voto voto finale
     * @param lode
     * @param crediti cfu che accredita l'esame
     * @throws Exception se viene immesso un dato incorretto (es. voto = 10)
     */

    public Esame(String studente, String insegnamento, int voto, boolean lode, int crediti) throws Exception {
        this.studente = studente;
        this.insegnamento = insegnamento;
        setVoto(voto);
        setLode(lode);
        setCrediti(crediti);
    }
    
    /**
     * Getter
     * @return nome e cognome dello studente che ha sostenuto l'esame
     */

    public String getStudente() {
        return this.studente;
    }

    /**
     * Setter
     * @param studente nome e cognome dello studente che ha sostenuto l'esame
     */

    public void setStudente(String studente) {
        this.studente = studente;
    }

    /**
     * Getter
     * @return nome del corso
     */

    public String getInsegnamento() {
        return this.insegnamento;
    }

    /**
     * Setter
     * @param nome nome del corso
     */

    public void setInsegnamento(String insegnamento) {
        this.insegnamento = insegnamento;
    }

    /**
     * Getter
     * @return voto finale
     */

    public int getVoto() {
        return this.voto;
    }

    /**
     * Setter
     * @param voto voto finale
     * @throws Exception se il voto è minore di 18 o più di 30.
     */

    public void setVoto(int voto) throws Exception{
        if(voto < 18)
            createError("Voto insufficiente");
        else if(voto > 30)
            createError("Voto troppo alto");
        else
        	this.voto = voto;
    }

    /**
     * Getter
     * @return true se c'è la lode, false altrimenti
     */

    public boolean isLode() {
        return this.lode;
    }

    /**
     * Setter
     * @param lode 
     * @throws Exception se lode è true ma il voto non è 30
     */

    public void setLode(boolean lode) throws Exception {
        if(lode && voto != 30)
            createError("Lode inammissibile");
        else
        	this.lode = lode;
    }

    /**
     * Getter
     * @return cfu
     */

    public int getCrediti() {
        return this.crediti;
    }

    /**
     * Setter
     * @param crediti cfu
     * @throws Exception se sono negativi
     */

    public void setCrediti(int crediti) throws Exception {
        if(crediti < 0)
            createError("Crediti negativi");
        else
        	this.crediti = crediti;
    }

    /**
     * Crea una finestra in cui viene segnalato un messaggio d'errore quando i dati sono incorretti. Ferma la creazione di una nuova istanza di Esame.
     * @param alert messaggio di errore
     * @throws Exception se dei parametri dell'esame che si sta cercando di creare sono errati
     */
    
    protected void createError(String alert) throws Exception {
		MyFrame f = new MyFrame(null, 0, 0, (int)(Toolkit.getDefaultToolkit().getScreenSize().width/3), (int)(Toolkit.getDefaultToolkit().getScreenSize().height/8));
		JPanel pan = new JPanel();
		f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		f.getContentPane().add(pan);
		JLabel a = new JLabel(alert);
		a.setForeground(Color.RED);
		pan.add(a);
		throw new Exception("Errore nei parametri dell'esame");
	}
}