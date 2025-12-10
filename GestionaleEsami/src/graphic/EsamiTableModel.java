package graphic;

import javax.swing.table.AbstractTableModel;

import esame.*;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Classe figlia di AbstractTableModel che serve a popolare una JTable. Ci si serve di una lista di esami.
 * @author Federico Rigolon
 */

public class EsamiTableModel extends AbstractTableModel {

    private ArrayList<Esame> Esami;
    private final String[] ColName = {"Studente", "Insegnamento", "Voto", "Lode", "CFU"};
    private final Class[] columnClass = new Class[] {String.class, String.class, Integer.class, Boolean.class, Integer.class};
    private boolean saved = true;

	/**
	 * Costruttore.
	 * @param Esami lista di esami iniziale
	 */

	public EsamiTableModel(ArrayList<Esame> Esami){
        this.Esami = Esami;
    }

	/**
	 * Costruttore vuoto
	 */
    
    public EsamiTableModel() {
    	this.Esami = new ArrayList<Esame>();
    }

	/**
	 * Getter
	 * @return lista degli esami
	 */
    
    public ArrayList<Esame> getEsami(){
    	return this.Esami;
    }

	/**
	 * Setter
	 * @param esami lista degli esami
	 */
    
    public void setEsami(ArrayList<Esame> esami) {
		this.Esami = esami;
	}

	/**
	 * Getter
	 * @return false se le ultime modifiche alla tabella non sono state salvate su un file
	 */
    
    public boolean isSaved() {
		return saved;
	}

	/**
	 * Setter
	 * @param saved false se le ultime modifiche alla tabella non sono state salvate su un file
	 */

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	/**
	 * Aggiunge un esame in fondo alla tabella
	 * @param e esame da aggiungere
	 */
	
    public void addEsame(Esame e) {
    	Esami.add(e);
    }

	/**
	 * Rimuove un esame dalla tabella
	 * @param rowIndex riga dell'esame da rimuovere
	 * @return esame rimosso
	 */
    
    public Esame removeEsame(int rowIndex){
    	return Esami.remove(rowIndex);
    }

	/**
	 * Svuota la tabella
	 */
    
    public void clear() {
    	Esami.clear();
    }

	/**
	 * Getter
	 * @return numero di esami
	 */

    @Override
    public int getRowCount() {
        return Esami.size();
    }

	/**
	 * Getter
	 * @return numero di colonne(dati degli esami)
	 */

    @Override
    public int getColumnCount() {
        return 5;
    }

	/**
	 * Getter
	 * @param rowIndex riga dell'oggetto
	 * @param columnIndex colonna dell'oggetto
	 * @return oggetto di posizione (rowIndex, columnIndex)
	 */

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
        case 0:
            return getEsameAt(rowIndex).getStudente();
        case 1:
            return getEsameAt(rowIndex).getInsegnamento();
        case 2:
            return getEsameAt(rowIndex).getVoto();
        case 3:
            return getEsameAt(rowIndex).isLode();
        case 4:
            return getEsameAt(rowIndex).getCrediti();
        }
        return null;
    }

	/**
	 * Modifica una cella della tabella
	 * @param aValue valore da inserire
	 * @param rowIndex riga della cella
	 * @param columnIndex colonna della cella
	 */
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    	try {
    		String past; int prev;
	    	switch(columnIndex) {
	    	case 0:
	    		past = getEsameAt(rowIndex).getStudente();
	    		getEsameAt(rowIndex).setStudente((String) aValue);
	    		if(past.compareTo(getEsameAt(rowIndex).getStudente()) != 0)
	    			saved = false;
	    		break;
	    	case 1:
	    		past = getEsameAt(rowIndex).getInsegnamento();
	    		getEsameAt(rowIndex).setInsegnamento((String) aValue);
	    		if(past.compareTo(getEsameAt(rowIndex).getInsegnamento()) != 0)
	    			saved = false;
	    		break;
	    	case 2:
	    		prev = getEsameAt(rowIndex).getVoto();
	    		getEsameAt(rowIndex).setVoto((Integer) aValue);
	    		if(prev != getEsameAt(rowIndex).getVoto())
	    			saved = false;
	    		break;
	    	case 3:
	    		boolean pre = getEsameAt(rowIndex).isLode();
	    		getEsameAt(rowIndex).setLode((Boolean) aValue);
	    		if(pre != getEsameAt(rowIndex).isLode())
	    			saved = false;
	    		break;
	    	case 4:
	    		prev = getEsameAt(rowIndex).getCrediti();
	    		getEsameAt(rowIndex).setCrediti((Integer) aValue);
	    		if(prev != getEsameAt(rowIndex).getCrediti())
	    			saved = false;
	    	}
    	} catch(Exception err) {
			err.printStackTrace();
		}
    }

	/**
	 * Getter
	 * @param columnIndex colonna
	 * @return classe della colonna
	 */
    
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClass[columnIndex];
    }

	/**
	 * Getter
	 * @param rowIndex riga della cella
	 * @param columnIndex colonna della cella
	 * @return true se la cella è editabile
	 */
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	return true;
    }

	/**
	 * Getter
	 * @param rowIndex riga dell'esame
	 * @return esame
	 */
    
    public Esame getEsameAt(int rowIndex) {
    	return Esami.get(rowIndex);
    }

	/**
	 * Getter
	 * @param col colonna
	 * @return Header della colonna
	 */

    @Override
    public String getColumnName(int col){
        return ColName[col];
    }

	/**
	 * Getter
	 * @return tutti i dati degli esami vettorizzati
	 */
    
    public Vector<String> getDataVector(){
    	Vector<String> data = new Vector<String>();
    	int n = getColumnCount();
    	int m = getRowCount();
    	for(int i = 0; i < m; i++) {
    		boolean composto = getEsameAt(i) instanceof Esame_composto;
    		data.add(String.valueOf(composto));
    		for(int j = 0; j < n; j++)
    			data.add(getValueAt(i,j).toString());
    		if(composto) {
    			Esame_composto c = (Esame_composto) getEsameAt(i);
    			int nProve = c.getNProve();
    			int[] Voti = c.getVoti();
    			int[] Pesi = c.getPesi();
    			data.add(String.valueOf(nProve));
    			for(int j = 0; j < nProve; j++) {
    				data.add(String.valueOf(Voti[j]));
    				data.add(String.valueOf(Pesi[j]));
    			}
    		}
    	}
    	return data;
    }
}
