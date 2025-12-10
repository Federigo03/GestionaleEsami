package graphic;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.io.*;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import esame.*;

import java.awt.Dimension;

/**
 * Pannello principale che estende JPanel e implemente ActionListener. Mostra la tabella e tutti i pulsanti coi quali ci si può interagire.
 * @author Federico Rigolon
 */

public class MainPanel extends JPanel implements ActionListener{

    private JTable table;
    private EsamiTableModel model;
    private JButton insertButton, saveButton, loadButton, removeButton, filterStudente, filterInsegnamento, filterReset, stats, parziali;
    private JTextField filter;
    private JLabel media;
    private JFileChooser myJFileChooser = new JFileChooser(new File("."));
    private TableRowSorter<EsamiTableModel> sorter;
    
    /**
     * Crea il pannello e tutti i suoi componenti, come la tabella e i bottoni
     */
    
    public MainPanel(){
        super(); 
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        model = new EsamiTableModel();
        table = new JTable(model);
        table.setCellSelectionEnabled(true);
        JScrollPane scroll = new JScrollPane(table);
        add(table.getTableHeader());
        table.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(scroll);
        sorter = new TableRowSorter<EsamiTableModel>(model);
        table.setRowSorter(sorter);
        add(Box.createGlue());
        media = new JLabel();
        media.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(media);
        insertButton = new JButton("Inserisci esame");
        insertButton.setAlignmentX(Component.CENTER_ALIGNMENT);                
        insertButton.addActionListener(this);
        add(insertButton);
        saveButton = new JButton("Salva");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);        
        saveButton.addActionListener(this);
        add(saveButton);
        loadButton = new JButton("Carica");
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);        
        loadButton.addActionListener(this);
        add(loadButton);
        removeButton = new JButton("Rimuovi");
        removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeButton.addActionListener(this);
        add(removeButton);
        filter = new JTextField("Filtra:");
        filter.setForeground(Color.GRAY);
        filter.setMaximumSize(new Dimension(Integer.MAX_VALUE, filter.getPreferredSize().height));
        filter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filter.setText("");
                filter.setForeground(Color.BLACK);
                filter.removeMouseListener(filter.getMouseListeners()[0]);
            }
        });;
        add(filter);
        filterStudente = new JButton("Filtra studenti");
        filterStudente.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterStudente.addActionListener(this);
        filterInsegnamento = new JButton("Filtra insegnamenti");
        filterInsegnamento.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterInsegnamento.addActionListener(this);
        add(filterStudente);
        add(filterInsegnamento);
        filterReset = new JButton("Azzera filtri");
        filterReset.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterReset.addActionListener(this);
        add(filterReset);
        stats = new JButton("Statistiche");
        stats.setAlignmentX(Component.CENTER_ALIGNMENT);
        stats.addActionListener(this);
        parziali = new JButton("Prove parziali");
        parziali.setAlignmentX(Component.CENTER_ALIGNMENT);
        parziali.addActionListener(this);
        add(parziali);
    }

    /**
     * Getter
     * @return modello che riempie la tabella
     */
    
    public EsamiTableModel getModel() {
    	return this.model;
    }

    /**
     * Implementa tutte le varie funzionalità dei pulsanti
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == insertButton){
            MyFrame insertFrame = new MyFrame("Nuovo esame", 0, 0, (int)(Toolkit.getDefaultToolkit().getScreenSize().width/2), (int)(Toolkit.getDefaultToolkit().getScreenSize().height/4));
            JPanel insertPanel = new InsertPanel(model, insertFrame);
            insertFrame.getContentPane().add(insertPanel);
        }
        else if(e.getSource() == saveButton) {
        	saveTable();
        }
        else if(e.getSource() == loadButton) {
        	loadTable();
        }
        else if(e.getSource() == removeButton) {
        	int n = table.getSelectedRowCount();
        	int[] Rows = table.getSelectedRows();
        	for(int i = 0; i < n; i++) {
        		for(int j = i + 1; j < n; j++)
        			if(Rows[j] > Rows[i])
        				Rows[j]--;
        		model.removeEsame(Rows[i]);
        	}
        	model.fireTableDataChanged();
        	model.setSaved(false);
        }
        else if(e.getSource() == filterStudente) {
        	media.setText("");
        	remove(stats);
        	setRowFilterOnCol(0);
        	int n = table.getRowCount();
        	if(n > 0) {
	        	boolean sameStudent = true;
	        	String student = table.getValueAt(0, 0).toString();
	        	for(int i = 0; i < n && sameStudent; i++) 
	        		if(student.compareToIgnoreCase(table.getValueAt(i, 0).toString()) != 0) 
	        			sameStudent = false;
	        	if(sameStudent) {
	        		double creditiStudente = 0, mediaStudente = 0;
	        		for(int i = 0; i < n; i++)
	        			creditiStudente += (Integer) table.getValueAt(i, 4);
	        		for(int i = 0; i < n; i++)
	        			mediaStudente += (Integer) table.getValueAt(i, 2) * (Integer) table.getValueAt(i, 4) / creditiStudente;
	        		media.setText("Media pesata di " + student + " : " + String.format("%.3f", mediaStudente));
	        		add(stats);
	        	}
        	}
        }
        else if(e.getSource() == filterInsegnamento) {
        	media.setText("");
        	remove(stats);
        	setRowFilterOnCol(1);
        	int n = table.getRowCount();
        	if(n > 0) {
	        	boolean sameCourse = true;
	        	String course = table.getValueAt(0, 1).toString();
	        	for(int i = 0; i < n && sameCourse; i++) 
	        		if(course.compareToIgnoreCase(table.getValueAt(i, 1).toString()) != 0) 
	        			sameCourse = false;
	        	if(sameCourse) {
	        		double mediaInsegnamento = 0, creditiInsegnamento = 0;
	        		for(int i = 0; i < n; i++)
	        			creditiInsegnamento += (Integer) table.getValueAt(i, 4);
	        		for(int i = 0; i < n; i++)
	        			mediaInsegnamento += (Integer) table.getValueAt(i, 2) * (Integer) table.getValueAt(i, 4) / creditiInsegnamento;
	        		media.setText("Media dei voti di " + course + " : " + String.format("%.3f", mediaInsegnamento));
	        		add(stats);
	        	}
        	}
        }
        else if(e.getSource() == filterReset) {
        	System.out.print(model.isSaved());
        	media.setText("");
        	remove(stats);
        	sorter.setRowFilter(null);
        }
        else if(e.getSource() == stats) {
        	 MyFrame statsFrame = new MyFrame("Statistiche Esami", 0, 0, (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.5), (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.5));
        	 statsFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
             JPanel statsPanel = new StatsPanel(table);
             statsFrame.getContentPane().add(statsPanel);
        }
        else if(e.getSource() == parziali) {
        	if(table.getSelectedRowCount() == 1) {
        		int row = table.getSelectedRow();
		    	if(model.getEsameAt(row) instanceof Esame_composto) {
		    		MyFrame proveFrame = new MyFrame("Prove parziali", 0, 0, (int)(Toolkit.getDefaultToolkit().getScreenSize().width/2), (int)(Toolkit.getDefaultToolkit().getScreenSize().height/4));
		            JPanel provePanel = new ProvePanel((Esame_composto) model.getEsameAt(row));
		            proveFrame.getContentPane().add(provePanel);
		    	}
        	}
        }
    }

    /**
     * Chiede in quale file si vuole salvare la tabella
     * @return true se la tabella è stata salvata con successo
     */
    
    public boolean saveTable() {
        if (myJFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
            return saveTable(myJFileChooser.getSelectedFile());
        return false;
    }

    /**
     * Salva la tabella in un file
     * @param file file di destinazione
     * @return true se la tabella è stata salvata con successo
     */
     
    private boolean saveTable(File file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(model.getDataVector());
            out.close();
            model.setSaved(true);
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Chiede da quale file si vuole caricare la tabella
     * @return true se la tabella è stata caricata con successo
     */
    
    private void loadTable() {
        if (myJFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            loadTable(myJFileChooser.getSelectedFile());
    }

    /**
     * Carica la tabella da un file
     * @param file file sorgente
     * @return true se la tabella è stata caricata con successo
     */
     
    private void loadTable(File file) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            Vector<String> rowData = (Vector<String>) in.readObject();
            in.close();
            setDataVector(rowData);
            model.setSaved(true);
            model.fireTableDataChanged();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * riempie il modello con gli esami che sono salvati in un vector
     * @param rowData dati degli esami
     * @throws Exception
     */
    
    private void setDataVector(Vector<String> rowData) throws Exception{
    	ArrayList<Esame> E = new ArrayList<Esame>();
    	model.setEsami(E);
    	while(!rowData.isEmpty()) {
    		boolean composto = Boolean.parseBoolean(rowData.removeFirst());
    		String studente = rowData.removeFirst();
    		String insegnamento = rowData.removeFirst();
    		int voto = Integer.parseInt(rowData.removeFirst());
    		boolean lode = Boolean.valueOf(rowData.removeFirst());
    		int crediti = Integer.parseInt(rowData.removeFirst());
    		if(composto) {
    			int nProve = Integer.parseInt(rowData.removeFirst());
    			int[] Voti = new int[nProve];
    			int[] Pesi = new int[nProve];
    			for(int i = 0; i < nProve; i++) {
    				Voti[i] = Integer.parseInt(rowData.removeFirst());
    				Pesi[i] = Integer.parseInt(rowData.removeFirst());
    			}
    			model.addEsame(new Esame_composto(studente, insegnamento, lode, crediti, nProve, Voti, Pesi));
    		}
    		else
    			model.addEsame(new Esame(studente, insegnamento, voto, lode, crediti));
    	}    	
    }

    /**
     * Setta un filtro case insensitive su una sola colonna 
     * @param colIndex colonna
     */
    
    private void setRowFilterOnCol(int colIndex) {
    	String text = filter.getText();
    	if(text.length() == 0)
    		sorter.setRowFilter(null);
    	else
    		sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, colIndex));	//(?i) = case insensitive
    }
}
