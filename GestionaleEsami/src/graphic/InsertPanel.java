package graphic;

import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import esame.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Dimension;

/**
 * Pannello che estende JPanel e implemente ActionListener. Gestisce e permette l'inserimento di nuovi esami.
 * @author Federico Rigolon
 */

public class InsertPanel extends JPanel implements ActionListener{
    
    private JLabel studenteLab, insegnamentoLab, votoLab, lodeLab, creditiLab;
    private JTextField studente, insegnamento, voto, crediti;
    private JRadioButton lodeSi, lodeNo;
    private ButtonGroup lode;
    private JButton semplice, composto, inserisci, inserisciC, prova;
    private EsamiTableModel model;
    private int count, nProve;
    private int[] Pesi, Voti;
    private String stud;
    private MyFrame frame;

	/**
	 * Il costruttore crea e posiziona i due bottoni per scegliere di inserire un esame semplice o composto
	 * @param model modello sui quali verranno aggiunti gli esami
	 * @param frame il frame contenente il pannello. serve per permettere la chiusura a fine operazione
	 */

    public InsertPanel(EsamiTableModel model, MyFrame frame){
        super();
        this.model = model;
        this.frame = frame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        studenteLab = new JLabel("Esame semplice o composto?");
        studenteLab.setAlignmentX(Component.CENTER_ALIGNMENT);
        semplice = new JButton("Semplice");
        semplice.setAlignmentX(Component.CENTER_ALIGNMENT);
        composto = new JButton("Composto");
        composto.setAlignmentX(Component.CENTER_ALIGNMENT);
        semplice.addActionListener(this);
        composto.addActionListener(this);
        add(studenteLab);
        add(semplice);
        add(composto);
        insegnamentoLab = new JLabel("Nome insegnamento");
        lodeLab = new JLabel("Lode");
        creditiLab = new JLabel("CFU esame");
        studente = new JTextField();
        studente.setMaximumSize(new Dimension(Integer.MAX_VALUE, studente.getPreferredSize().height));
        insegnamento = new JTextField();
        insegnamento.setMaximumSize(new Dimension(Integer.MAX_VALUE, insegnamento.getPreferredSize().height));
        voto = new JTextField();
        voto.setMaximumSize(new Dimension(Integer.MAX_VALUE, voto.getPreferredSize().height));
        crediti = new JTextField();
        crediti.setMaximumSize(new Dimension(Integer.MAX_VALUE, crediti.getPreferredSize().height));
        lodeSi = new JRadioButton("Sì");
        lodeNo = new JRadioButton("No");
        lode = new ButtonGroup();
        lode.add(lodeSi);
        lode.add(lodeNo);
    }

	/**
	 * Implementa tutti gli usi dei vari bottoni.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
        if(e.getSource() == semplice){
        	votoLab = new JLabel("Voto finale");
        	addComponents();
        	inserisci = new JButton("Inserisci");
        	inserisci.addActionListener(this);
            add(inserisci);
        }
        else if(e.getSource() == composto){
        	votoLab = new JLabel("Numero di prove intermedie");
        	addComponents();
        	inserisciC = new JButton("Inserisci");
        	inserisciC.addActionListener(this);
            add(inserisciC);
        }
        else if(e.getSource() == inserisci) {
        	if(studente.getText() != null && insegnamento.getText() != null && voto.getText() != null && crediti.getText()
        			!= null && (lodeSi.isSelected() || lodeNo.isSelected())) {
        		try {
		        	model.addEsame(new Esame(studente.getText(), insegnamento.getText(), Integer.parseInt(voto.getText()),
		        			lodeSi.isSelected(), Integer.parseInt(crediti.getText())));
		        	model.setSaved(false);
		        	model.fireTableDataChanged();
		        	frame.dispose();
        		} catch(NumberFormatException err) {
        			createError("Non inserire caratteri che non siano cifre nel campo voto e crediti");
        		} catch (Exception e1) {
					e1.printStackTrace();
				}
        	}
        	else
        		createError("Tutti i campi devono essere compilati");
        }
        else if(e.getSource() == inserisciC) {
        	if(studente.getText() != null && insegnamento.getText() != null && voto.getText() != null && crediti.getText()!= null) {
        		try {
		        	nProve = Integer.parseInt(voto.getText());
		        	stud = studente.getText();
		        	removeAll();
		        	revalidate();
		        	prova = new JButton("Inserisci prova");
		        	prova.addActionListener(this);
		        	Pesi = new int[nProve];
		        	Voti = new int[nProve];
		        	studenteLab.setText("Peso della prova " + (count + 1) + " in percentuale");
		        	votoLab.setText("Voto della prova " + (count + 1));
		        	studente.setText("");
		        	voto.setText("");
		       		frame.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width/3, Toolkit.getDefaultToolkit().getScreenSize().height/8);
		       		frame.setLocationRelativeTo(null);
		       		add(studenteLab);
		        	add(studente);
		        	add(votoLab);
		       		add(voto);
		       		add(prova);
        		} catch(NumberFormatException err) {
        			createError("Non inserire caratteri che non siano cifre nel campo numero di prove");
        		}
        	}
        	else
        		createError("Tutti i campi devono essere compilati");
        }
        else if(e.getSource() == prova) {
        	try {
        		Pesi[count] = Integer.parseInt(studente.getText());
        		Voti[count] = Integer.parseInt(voto.getText());
        		count++;
        		if(count == nProve) {
		        	model.addEsame(new Esame_composto(stud, insegnamento.getText(), lodeSi.isSelected(),
		        			Integer.parseInt(crediti.getText()), nProve, Voti, Pesi));
		        	model.setSaved(false);
		        	model.fireTableDataChanged();
		        	frame.dispose();
        		}
        		studenteLab.setText("Peso della prova " + (count + 1) + " in percentuale");
	        	votoLab.setText("Voto della prova " + (count + 1));
	        	studente.setText("");
	        	voto.setText("");
        	} catch(NumberFormatException err) {
    			createError("Non inserire caratteri che non siano cifre");
    		} catch (Exception e1) {
				e1.printStackTrace();
			}
        }
    }

	/**
     * Crea una finestra in cui viene segnalato un messaggio d'errore quando i dati sono incorretti. Ferma la creazione di una nuova istanza di Esame e la sua aggiunta al modello.
     * @param alert messaggio di errore
     * @throws Exception se dei parametri dell'esame che si sta cercando di creare sono errati
     */
	
	private void createError(String alert) {
		MyFrame f = new MyFrame(null, 0, 0, (int)(Toolkit.getDefaultToolkit().getScreenSize().width/3), (int)(Toolkit.getDefaultToolkit().getScreenSize().height/7));
		JPanel pan = new JPanel();
		f.getContentPane().add(pan);
		JLabel a = new JLabel(alert);
		a.setForeground(Color.RED);
		pan.add(a);
	}

	/**
	 * Aggiunge le label e i textField utili alla creazione di un nuovo esame
	 */
	
	private void addComponents() {
		remove(semplice);
        remove(composto);
        studenteLab.setText("Nome e Cognome studente");
        studenteLab.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(studenteLab);
        add(studente);
        add(insegnamentoLab);
        add(insegnamento);
        add(votoLab);
	    add(voto);
    	add(lodeLab);
        add(lodeSi);
        add(lodeNo);
        add(creditiLab);
        add(crediti);
	}
}
