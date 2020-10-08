package sprint1.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class Calendario extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3415566910862546658L;
	private JPanel contentPane;
	private JPanel pnNorth;
	private JPanel pnCenter;
	private JPanel pnDias;
	private JLabel lblLunes;
	private JLabel lblMartes;
	private JLabel lblMiercoles;
	private JLabel lblJueves;
	private JLabel lblViernes;
	private JLabel lblSabado;
	private JLabel lblDomingo;
	private JPanel pnDiasMes;
	private ArrayList<String> meses;
	private ArrayList<Integer> años;
	private JPanel pnNextMonth;
	private JButton btnNextMonth;
	private JPanel pnPreviousMonth;
	private JButton btnPreviousMonth;
	private JComboBox<String> cbMeses;
	private JComboBox<Integer> cbAños;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calendario frame = new Calendario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Calendario() {
		llenarMeses();
		llenarAños();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 343);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPnNorth(), BorderLayout.NORTH);
		contentPane.add(getPanel_1(), BorderLayout.CENTER);
		contentPane.add(getPnNextMonth(), BorderLayout.EAST);
		contentPane.add(getPnPreviousMonth(), BorderLayout.WEST);
		iniciarMes("Octubre", 2020);
	}
	
	public Calendario(String mes, int año) {
		this();
		iniciarMes(mes,año);
	}

	private void iniciarMes(String mes, int año) {
		if (año < 2020) {
			JOptionPane.showMessageDialog(this, "No puedes escoger un año previo a 2020"
					,"Error en el calendario", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int primerDiaDelMes = 3 ,diasDelMes = 0;
		
		for (int i = 2020; i < año; i++) {
			if (i % 4 == 0) {
				primerDiaDelMes = primerDiaDelMes + 2;
			}
			else
				primerDiaDelMes++;
		}
		diasDelMes = calcularDias(mes, año);
		
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			if (mes == meses.get(i))
				break;
			else {
				switch(calcularDias(meses.get(i),año)) {
				case 31:
					primerDiaDelMes = primerDiaDelMes + 3;
					break;
				case 30:
					primerDiaDelMes = primerDiaDelMes + 2;
					break;
				case 29:
					primerDiaDelMes = primerDiaDelMes + 1;
					break;
				}
			}
			if(primerDiaDelMes != 7) {
				primerDiaDelMes = primerDiaDelMes % 7;
			}
		}
		generateButtons(primerDiaDelMes, diasDelMes);
		cbMeses.setSelectedItem(mes);
		cbAños.setSelectedItem(año);
	}

	private int calcularDias(String mes, int año) {
		switch(mes) {
		case "Enero":
		case "Marzo":
		case "Mayo":
		case "Julio":
		case "Agosto":
		case "Octubre":
		case "Diciembre":
			return 31;
		case "Abril":
		case "Junio":
		case "Septiembre":
		case "Noviembre":
			return 30;
		case "Febrero":
			if (año % 4 == 0)
				return 29;
			else
				return 28;
		default:
			throw new IllegalArgumentException();
		}
	}

	private void llenarMeses() {
		meses = new ArrayList<String>();
		meses.add("Enero");
		meses.add("Febrero");
		meses.add("Marzo");
		meses.add("Abril");
		meses.add("Mayo");
		meses.add("Junio");
		meses.add("Julio");
		meses.add("Agosto");
		meses.add("Septiembre");
		meses.add("Octubre");
		meses.add("Noviembre");
		meses.add("Diciembre");
	}
	
	private void llenarAños() {
		años = new ArrayList<Integer>();
		for (int i = 0; i < 100; i++) {
			años.add(i+2020);
		}
	}

	private JPanel getPnNorth() {
		if (pnNorth == null) {
			pnNorth = new JPanel();
			pnNorth.setBackground(Color.WHITE);
			pnNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnNorth.add(getCbMeses());
			pnNorth.add(getCbAños());
		}
		return pnNorth;
	}
	private JPanel getPanel_1() {
		if (pnCenter == null) {
			pnCenter = new JPanel();
			pnCenter.setBackground(Color.WHITE);
			pnCenter.setLayout(new BorderLayout(0, 0));
			pnCenter.add(getPanel_1_1(), BorderLayout.NORTH);
			pnCenter.add(getPnDiasMes(), BorderLayout.CENTER);
		}
		return pnCenter;
	}
	private JPanel getPanel_1_1() {
		if (pnDias == null) {
			pnDias = new JPanel();
			pnDias.setBackground(Color.WHITE);
			pnDias.setLayout(new GridLayout(0, 7, 0, 0));
			pnDias.add(getLabel_6());
			pnDias.add(getLabel_7());
			pnDias.add(getLabel_8());
			pnDias.add(getLabel_9());
			pnDias.add(getLabel_10());
			pnDias.add(getLabel_11());
			pnDias.add(getLabel_12());
		}
		return pnDias;
	}
	private JLabel getLabel_6() {
		if (lblLunes == null) {
			lblLunes = new JLabel("Lunes");
			lblLunes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblLunes;
	}
	private JLabel getLabel_7() {
		if (lblMartes == null) {
			lblMartes = new JLabel("Martes");
			lblMartes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblMartes;
	}
	private JLabel getLabel_8() {
		if (lblMiercoles == null) {
			lblMiercoles = new JLabel("Mi\u00E9rcoles");
			lblMiercoles.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblMiercoles;
	}
	private JLabel getLabel_9() {
		if (lblJueves == null) {
			lblJueves = new JLabel("Jueves");
			lblJueves.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblJueves;
	}
	private JLabel getLabel_10() {
		if (lblViernes == null) {
			lblViernes = new JLabel("Viernes");
			lblViernes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblViernes;
	}
	private JLabel getLabel_11() {
		if (lblSabado == null) {
			lblSabado = new JLabel("S\u00E1bado");
			lblSabado.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblSabado;
	}
	private JLabel getLabel_12() {
		if (lblDomingo == null) {
			lblDomingo = new JLabel("Domingo");
			lblDomingo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblDomingo;
	}
	private JPanel getPnDiasMes() {
		if (pnDiasMes == null) {
			pnDiasMes = new JPanel();
			pnDiasMes.setBackground(Color.WHITE);
			pnDiasMes.setLayout(new GridLayout(6, 7, 0, 0));
		}
		return pnDiasMes;
	}
	private void generateButtons(int primerDia, int diasTotal) {
		pnDiasMes.removeAll();
		int numeroDeBotones = 0;
		if (primerDia == 1 && diasTotal == 28) {
			pnDiasMes.setLayout(new GridLayout(4, 7, 0, 0));
			numeroDeBotones = 28; //4 semanas
		}
		else if (primerDia < 6) {
			pnDiasMes.setLayout(new GridLayout(5, 7, 0, 0));
			numeroDeBotones = 35; //5 semanas
		}
		else {
			pnDiasMes.setLayout(new GridLayout(6, 7, 0, 0));
			numeroDeBotones = 42; //6 semanas
		}
			
		for (int i = 1; i <= numeroDeBotones; i++) {
			if (i < primerDia || i + 1 - primerDia > diasTotal)
				pnDiasMes.add(paintDia(-1));
			else
				pnDiasMes.add(paintDia(i + 1 - primerDia));
		}
		pnDiasMes.validate();
	}

	private Component paintDia(int numeroDia) {
		JButton dia = new JButton();
		dia.setBorder(new LineBorder(Color.BLACK,1));
		dia.setFont(new Font("Tahoma", Font.PLAIN, 16));
		if (numeroDia != -1)
			dia.setText(String.valueOf(numeroDia));
		else
			dia.setEnabled(false);
		return dia;
	}
	private JPanel getPnNextMonth() {
		if (pnNextMonth == null) {
			pnNextMonth = new JPanel();
			pnNextMonth.setLayout(new BorderLayout(0, 0));
			pnNextMonth.add(getBtnNextMonth());
		}
		return pnNextMonth;
	}
	private JButton getBtnNextMonth() {
		if (btnNextMonth == null) {
			btnNextMonth = new JButton("\u25BA");
			btnNextMonth.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnNextMonth.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					siguienteMes();
				}
			});
		}
		return btnNextMonth;
	}
	
	private void siguienteMes() {
		int pos = 0;
		String mes = meses.get(cbMeses.getSelectedIndex());
		int año = (int) cbAños.getSelectedItem();
		for (int i = 0; i < meses.size(); i++) {
			if (mes.equals(meses.get(i))) {
				pos = i;
				break;
			}	
		}
		if (pos == 11) {
			pos = 0;
			año++;
		}
		else
			pos = pos + 1;
		iniciarMes(meses.get(pos), año);
	}
	
	private JPanel getPnPreviousMonth() {
		if (pnPreviousMonth == null) {
			pnPreviousMonth = new JPanel();
			pnPreviousMonth.setLayout(new BorderLayout(0, 0));
			pnPreviousMonth.add(getBtnPreviousMonth());
		}
		return pnPreviousMonth;
	}
	private JButton getBtnPreviousMonth() {
		if (btnPreviousMonth == null) {
			btnPreviousMonth = new JButton("\u25C4");
			btnPreviousMonth.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnPreviousMonth.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					anteriorMes();
				}
			});
		}
		return btnPreviousMonth;
	}
	
	private void anteriorMes() {
		int pos = 0;
		String mes = meses.get(cbMeses.getSelectedIndex());
		int año = (int) cbAños.getSelectedItem();
		for (int i = meses.size() - 1; i >= 0; i--) {
			if (mes.equals(meses.get(i))) {
				pos = i;
			}	
		}
		if (pos == 0) {
			pos = 11;
			año--;
		}
		else
			pos = pos - 1;
		iniciarMes(meses.get(pos), año);
	}
	private JComboBox<String> getCbMeses() {
		if (cbMeses == null) {
			cbMeses = new JComboBox<String>();
			cbMeses.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					iniciarMes((String)cbMeses.getSelectedItem(), (int)cbAños.getSelectedItem());
				}
			});
			cbMeses.setModel(new DefaultComboBoxModel<String>(meses.toArray(new String[0])));
		}
		return cbMeses;
	}
	private JComboBox<Integer> getCbAños() {
		if (cbAños == null) {
			cbAños = new JComboBox<Integer>();
			cbAños.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					iniciarMes((String)cbMeses.getSelectedItem(), (int)cbAños.getSelectedItem());
				}
			});
			cbAños.setModel(new DefaultComboBoxModel<Integer>(años.toArray(new Integer[0])));
		}
		return cbAños;
	}
}