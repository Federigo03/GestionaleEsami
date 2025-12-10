package esame;

/**
 * Classe figlia di Esame che permette di salvare esami composti da 2 o più prove intermedie 
 * @author Federico Rigolon
 */

public class Esame_composto extends Esame {
    
    private int nProve;
    private int[] Voti;
    private int[] Pesi;

    /**
     * Costruttore.
     * @param studente nome e cognome dello studente che ha sostenuto l'esame
     * @param insegnamento nome del corso
     * @param lode
     * @param crediti cfu che accredita l'esame
     * @param nProve numero di prove intermedie
     * @param Voti array contenente i voti delle prove intermedie
     * @param Pesi array contenente i pesi in percentuale delle prove intermedie
     * @throws Exception se viene immesso un dato incorretto (es. voto = 10)
     */

    public Esame_composto(String studente, String insegnamento, boolean lode, int crediti, int nProve, int[] Voti, int[] Pesi) throws Exception {
        super(studente, insegnamento, 18, false, crediti);
        setNProve(nProve);
        setVoti(Voti);
        setPesi(Pesi);
        int voto = 0;
        for(int i = 0; i < nProve; i++)
            voto += Voti[i] * Pesi[i];
        voto /= 100;
        setVoto(voto);
        setLode(lode);
    }

    /**
     * Costruttore.
     * @param e esame semplice da cui vengono presi i dati che servono
     * @param nProve numero di prove intermedie
     * @param Voti array contenente i voti delle prove intermedie
     * @param Pesi array contenente i pesi in percentuale delle prove intermedie
     * @throws Exception se viene immesso un dato incorretto (es. voto = 10)
     */
    
    public Esame_composto(Esame e, int nProve, int[] Voti, int[] Pesi) throws Exception {
    	super(e.getStudente(), e.getInsegnamento(), 18, false, e.getCrediti());
    	setNProve(nProve);
    	setVoti(Voti);
    	setPesi(Pesi);
        int voto = 0;
        for(int i = 0; i < nProve; i++)
            voto += Voti[i] * Pesi[i];
        voto /= 100;
        setVoto(voto);
        setLode(e.isLode());
    }

    /**
     * Getter
     * @return numero prove intermedie
     */

    public int getNProve() {
        return this.nProve;
    }

    /**
     * Setter
     * @param nProve numero prove intermedie
     * @throws Exception se sono minori di 2
     */

    public void setNProve(int nProve) throws Exception{
        if(nProve < 2)
            createError("Le prove intermedie devono essere almeno 2");
        else
        	this.nProve = nProve;
    }

    /**
     * Getter
     * @return array dei voti delle prove intermedie
     */

    public int[] getVoti() {
        return this.Voti;
    }

    /**
     * Setter
     * @param Voti array dei voti delle prove intermedie
     * @throws Exception se almeno un voto è più alto di 30 o minore di 18
     */

    public void setVoti(int[] Voti) throws Exception {
    	boolean possible = true;
        if(Voti.length != nProve) {
            createError("Numero di voti diverso dal numero di prove");
            possible = false;
        }
        for(int i = 0; i < nProve && possible; i++){
            if(Voti[i] < 18) {
                createError("Prova intermedia insufficiente");
                possible = false;
            }
            else if(Voti[i] > 30) {
                createError("Voto prova intermedia troppo alto");
                possible = false;
            }
        }
        if(possible)
        	this.Voti = Voti;
    }

    /**
     * Getter
     * @return array dei pesi percentuali delle prove intermedie
     */

    public int[] getPesi() {
        return this.Pesi;
    }

    /**
     * Setter
     * @param Pesi array dei pesi percentuali delle prove intermedie
     * @throws Exception se qualche peso è minore di 1 o maggiore di 99 o se la somma di tutti non fa 100 
     */

    public void setPesi(int[] Pesi) throws Exception {
    	boolean possible = true;
        if(Pesi.length != nProve) {
            createError("Numero di pesi diverso dal numero di prove");
            possible = false;
        }
        int somma = 0;
        for(int i = 0; i < nProve && possible; i++){
            if(Pesi[i] < 1) {
                createError("Peso prova intermedia troppo basso");
                possible = false;
            }
            else if(Pesi[i] > 99) {
                createError("Peso prova intermedia troppo alto");
                possible = false;
            }
            somma += Pesi[i];
        }
        if(somma != 100) {
            createError("Somma dei pesi diversa da 100");
            possible = false;
        }
        if(possible)
        	this.Pesi = Pesi;
    }
}
