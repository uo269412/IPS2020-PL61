package sprint1.ui.ventanas.socio.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import sprint1.business.dominio.clientes.Socio;
import sprint1.ui.ventanas.socio.SocioWindow;
import sprint1.ui.ventanas.socio.acciones.AlquilaSocioWindow;
import java.awt.Toolkit;

public class CalendarioAlquilerSocio extends JDialog {

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
	private ArrayList<String> todosLosMeses;
	private ArrayList<Integer> años;
	private JPanel pnNextMonth;
	private JButton btnNextMonth;
	private JPanel pnPreviousMonth;
	private JButton btnPreviousMonth;

	private int dia;
	private String mes;
	private int año;
	private int contadorDias = 7;
	private Map<String, JPanel> panelesMeses = new HashMap<>();
	private JLabel lblMes;
	private JLabel lblAño;
	private boolean quedanDias = true;
	private boolean mesSiguiente = false;
	private SocioWindow parent;
	private ActionListener aa;

	private AlquilaSocioWindow socioAlquila;
	private Socio socio;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CalendarioAlquilerAdmin frame = new CalendarioAlquilerAdmin();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public CalendarioAlquilerSocio(SocioWindow parent, Socio socio) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(CalendarioAlquilerSocio.class.getResource("/sprint1/ui/resources/titulo.png")));
		setTitle("Centro de deportes: Seleccionando d\u00EDa para alquilar");
		aa = new AlquilarSocio();
		this.parent = parent;
		this.socio = socio;
		Calendar calendar = Calendar.getInstance();
		dia = calendar.get(Calendar.DAY_OF_MONTH);
		mes = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
		mes = mes.substring(0, 1).toUpperCase() + mes.substring(1);
		año = calendar.get(Calendar.YEAR);
		llenarMesAux();
		llenarCombos();
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
		if (meses.size() == 1) {
			btnNextMonth.setEnabled(false);
			btnPreviousMonth.setEnabled(false);
		}
		for (String mes : todosLosMeses) {
			panelesMeses.put(mes, iniciarMesList(mes, año));
		}
		iniciarMes(mes, 2020);
	}

	private void iniciarMes(String mes, int año) {
		pnCenter.remove(1);
		lblMes.setText(mes);
		lblAño.setText(String.valueOf(año));
		if (quedanDias)
			disableButtons(panelesMeses.get(mes));
		pnCenter.add(panelesMeses.get(mes));
		pnCenter.repaint();
		pnCenter.validate();
	}

	private void disableButtons(JPanel panel) {
		int counter = 0;
		for (Component c : panel.getComponents()) {
			JButton b = (JButton) c;
			if (b.getText() != "")
				counter++;
			if (b.getText() != "" && contadorDias > 0 && (counter >= dia || mesSiguiente)) {
				contadorDias--;
			} else if (b.getText() != "") {
				b.setEnabled(false);
			}
		}
		if (contadorDias == 0)
			quedanDias = false;
	}

	private JPanel iniciarMesList(String mes, int año) {
		JPanel panel = new JPanel();
		if (año < 2020) {
			JOptionPane.showMessageDialog(this, "No puedes escoger un año previo a 2020", "Error en el calendario",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		int primerDiaDelMes = 3, diasDelMes = 0;

		for (int i = 2020; i < año; i++) {
			if (i % 4 == 0) {
				primerDiaDelMes = primerDiaDelMes + 2;
			} else
				primerDiaDelMes++;
		}
		diasDelMes = calcularDias(mes, año);

		for (int i = 0; i < 12; i++) {
			if (mes.equals(todosLosMeses.get(i)))
				break;
			else {
				switch (calcularDias(todosLosMeses.get(i), año)) {
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
			if (primerDiaDelMes != 7) {
				primerDiaDelMes = primerDiaDelMes % 7;
			}
		}
		int numeroDeBotones = 0;
		if (primerDiaDelMes == 1 && diasDelMes == 28) {
			panel.setLayout(new GridLayout(4, 7, 0, 0));
			numeroDeBotones = 28; // 4 semanas
		} else if (primerDiaDelMes < 6) {
			panel.setLayout(new GridLayout(5, 7, 0, 0));
			numeroDeBotones = 35; // 5 semanas
		} else {
			panel.setLayout(new GridLayout(6, 7, 0, 0));
			numeroDeBotones = 42; // 6 semanas
		}
		for (int i = 1; i <= numeroDeBotones; i++) {
			if (i < primerDiaDelMes || i + 1 - primerDiaDelMes > diasDelMes)
				panel.add(paintDia(-1));
			else
				panel.add(paintDia(i + 1 - primerDiaDelMes));
		}
		return panel;
	}

	private int calcularDias(String mes, int año) {
		switch (mes) {
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

	private void llenarCombos() {
		meses = new ArrayList<String>();
		años = new ArrayList<Integer>();
		meses.add(mes);
		años.add(año);
		if (!(calcularDias(mes, año) - dia >= 7)) {
			if (!mes.equals("Diciembre")) {
				meses.add(todosLosMeses.get(todosLosMeses.indexOf(mes) + 1));
			} else {
				meses.add(todosLosMeses.get(0));
				años.add(año + 1);
			}
		}
	}

	private void llenarMesAux() {
		todosLosMeses = new ArrayList<String>();
		todosLosMeses.add("Enero");
		todosLosMeses.add("Febrero");
		todosLosMeses.add("Marzo");
		todosLosMeses.add("Abril");
		todosLosMeses.add("Mayo");
		todosLosMeses.add("Junio");
		todosLosMeses.add("Julio");
		todosLosMeses.add("Agosto");
		todosLosMeses.add("Septiembre");
		todosLosMeses.add("Octubre");
		todosLosMeses.add("Noviembre");
		todosLosMeses.add("Diciembre");
	}

	private JPanel getPnNorth() {
		if (pnNorth == null) {
			pnNorth = new JPanel();
			pnNorth.setBackground(Color.WHITE);
			pnNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnNorth.add(getLblMes());
			pnNorth.add(getLblAño());
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

	/*
	 * private void generateButtons(int primerDia, int diasTotal) {
	 * pnDiasMes.removeAll(); int numeroDeBotones = 0; if (primerDia == 1 &&
	 * diasTotal == 28) { pnDiasMes.setLayout(new GridLayout(4, 7, 0, 0));
	 * numeroDeBotones = 28; // 4 semanas } else if (primerDia < 6) {
	 * pnDiasMes.setLayout(new GridLayout(5, 7, 0, 0)); numeroDeBotones = 35; // 5
	 * semanas } else { pnDiasMes.setLayout(new GridLayout(6, 7, 0, 0));
	 * numeroDeBotones = 42; // 6 semanas } for (int i = 1; i <= numeroDeBotones;
	 * i++) { if (i < primerDia || i + 1 - primerDia > diasTotal)
	 * pnDiasMes.add(paintDia(-1)); else pnDiasMes.add(paintDia(i + 1 - primerDia));
	 * } pnDiasMes.validate(); }
	 * 
	 * private void prepararBotones(String mes) { int diaParse = 0; String mesParse;
	 * for (Component c : pnDiasMes.getComponents()) { JButton b = (JButton) c; if
	 * (b.isEnabled()) { diaParse = Integer.parseInt(b.getText()); mesParse =
	 * lblMes.getText(); if (mesParse.equals(mes)) { if (diaParse < dia) {
	 * b.setEnabled(false); } else if (contadorDias > 0) { b.setEnabled(true);
	 * contadorDias--; System.out.println(contadorDias); } } else { if (contadorDias
	 * > 0) { b.setEnabled(true); System.out.println("hola"); contadorDias--;
	 * System.out.println(contadorDias); } else { b.setEnabled(false); } } }
	 * repaint(); } }
	 */
	private Component paintDia(int numeroDia) {
		JButton dia = new JButton();
		dia.setBorder(new LineBorder(Color.BLACK, 1));
		dia.setFont(new Font("Tahoma", Font.PLAIN, 16));
		if (numeroDia != -1) {
			dia.setText(String.valueOf(numeroDia));
			dia.addActionListener(aa);
		} else {
			dia.setEnabled(false);
		}
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
					mesSiguiente = true;
					siguienteMes();
					if (meses.contains(lblMes.getText())) {
						btnNextMonth.setEnabled(false);
						btnPreviousMonth.setEnabled(true);
					}
				}
			});
		}
		return btnNextMonth;
	}

	private void siguienteMes() {
		int pos = 0;
		String mes = lblMes.getText();
		int año = Integer.parseInt(lblAño.getText());
		for (int i = 0; i < meses.size(); i++) {
			if (mes.equals(meses.get(i))) {
				pos = i;
				break;
			}
		}
		if (pos == 11) {
			pos = 0;
			año++;
		} else
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
			btnPreviousMonth.setEnabled(false);
			btnPreviousMonth.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					anteriorMes();
					if (meses.contains(lblMes.getText())) {
						btnPreviousMonth.setEnabled(false);
						btnNextMonth.setEnabled(true);
					}
				}
			});
		}
		return btnPreviousMonth;
	}

	private void anteriorMes() {
		int pos = 0;
		String mes = lblMes.getText();
		int año = Integer.parseInt(lblAño.getText());
		for (int i = meses.size() - 1; i >= 0; i--) {
			if (mes.equals(meses.get(i))) {
				pos = i;
			}
		}
		if (pos == 0) {
			pos = 11;
			año--;
		} else
			pos = pos - 1;
		iniciarMes(meses.get(pos), año);
	}

	private JLabel getLblMes() {
		if (lblMes == null) {
			lblMes = new JLabel("");
		}
		return lblMes;
	}

	private JLabel getLblAño() {
		if (lblAño == null) {
			lblAño = new JLabel("");
		}
		return lblAño;
	}

	public SocioWindow getParent() {
		return this.parent;
	}

	private class AlquilarSocio implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JButton button = (JButton) arg0.getSource();
			int[] fecha = parseFecha(button);
			int dia = fecha[0];
			int mes = fecha[1];
			int año = fecha[2];
			openAlquilaSocioWindow(CalendarioAlquilerSocio.this, dia, mes, año, socio);

		}
	}

	private void openAlquilaSocioWindow(CalendarioAlquilerSocio parent, int dia, int mes, int año, Socio socio) {
		socioAlquila = new AlquilaSocioWindow(parent, dia, mes, año, socio);
		socioAlquila.setModal(true);
		socioAlquila.setLocationRelativeTo(this);
		socioAlquila.setVisible(true);
	}

	private int[] parseFecha(JButton b) {
		int[] fecha = new int[3];
		fecha[0] = Integer.parseInt(b.getText());
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat("MMM").parse(mes));
		} catch (ParseException e) {
			System.out.println("Error parseando el mes");
		}
		fecha[1] = cal.get(Calendar.MONTH) + 1;
		fecha[2] = año;

		return fecha;
	}
}