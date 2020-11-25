package sprint1.ui.ventanas.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import sprint1.business.Programa;
import sprint1.business.dominio.centroDeportes.actividades.Actividad;
import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;
import sprint1.business.dominio.centroDeportes.alquileres.Alquiler;
import sprint1.business.dominio.centroDeportes.instalaciones.Instalacion;
import sprint1.business.dominio.clientes.Socio;
import java.awt.Toolkit;

public class CalendarioSemanalBase extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnPrincipal;
	private JPanel pnNorth;
	protected JComboBox<Instalacion> cbInstalacionSeleccionada;
	private JButton btnMostrarOcupacion;
	private Programa programa = null;
	private ArrayList<String> dias = new ArrayList<String>();
	private JPanel pnTopCenter;
	private JPanel pnTopEast;
	private JTextPane txtLeyenda;
	private JPanel pnCentro;
	private JPanel pnCentralDias;
	private JButton btnPreviousWeek;
	private JButton btnNextWeek;

	private Date date;

	private static final int DIA_EN_MILLIS = 1000 * 24 * 60 * 60;
	private JLabel lblNombreMes;

	/**
	 * Create the frame.
	 */
	public CalendarioSemanalBase(Programa p) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(CalendarioSemanalBase.class.getResource("/sprint1/ui/resources/titulo.png")));
		setTitle("Centro de deportes: Calendario semanal");
		this.programa = p;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1335, 792);
		pnPrincipal = new JPanel();
		pnPrincipal.setBackground(Color.WHITE);
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnPrincipal.setLayout(new BorderLayout(0, 0));
		setContentPane(pnPrincipal);
		pnPrincipal.add(getPnNorth(), BorderLayout.NORTH);
		pnPrincipal.add(getPnCentro(), BorderLayout.CENTER);
		date = new Date();
		generarPaneles();
	}

	public Programa getPrograma() {
		return this.programa;
	}

	public Date getDate() {
		return this.date;
	}

	private JPanel getPnNorth() {
		if (pnNorth == null) {
			pnNorth = new JPanel();
			pnNorth.setBorder(null);
			pnNorth.setBackground(Color.WHITE);
			pnNorth.setLayout(new BorderLayout(0, 0));
			pnNorth.add(getPnTopCenter(), BorderLayout.CENTER);
			pnNorth.add(getPnTopEast(), BorderLayout.EAST);
			pnNorth.add(getLblNombreMes(), BorderLayout.WEST);
		}
		return pnNorth;
	}

	private JComboBox<Instalacion> getCbInstalacionSeleccionada() {
		if (cbInstalacionSeleccionada == null) {
			cbInstalacionSeleccionada = new JComboBox<Instalacion>();
			List<Instalacion> instalaciones = programa.getInstalaciones();
			Instalacion[] arrayInstalaciones = new Instalacion[instalaciones.size()];
			arrayInstalaciones = instalaciones.toArray(arrayInstalaciones);
			cbInstalacionSeleccionada.setModel(new DefaultComboBoxModel<Instalacion>(arrayInstalaciones));
			cbInstalacionSeleccionada.setSelectedIndex(0);
		}
		return cbInstalacionSeleccionada;
	}

	private JButton getBtnMostrarOcupacion() {
		if (btnMostrarOcupacion == null) {
			btnMostrarOcupacion = new JButton("Mostrar");
			btnMostrarOcupacion.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnMostrarOcupacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					generarPaneles();
				}
			});
		}
		return btnMostrarOcupacion;
	}

	public void generarPaneles() {
		pnCentralDias.removeAll();
		for (int i = 0; i <= 7; i++) {
			pnCentralDias.add(newDay(i));
		}
		lblNombreMes.setText(getNombreMes());
		pnCentralDias.repaint();
		pnCentralDias.validate();
	}

	private JPanel newDay(int i) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(17, 1));
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		String titulo = generarTitulo(i);
		JLabel lblTitulo = new JLabel();
		lblTitulo.setOpaque(true);
		lblTitulo.setBackground(Color.LIGHT_GRAY);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitulo.setText(titulo);
		panel.add(lblTitulo);
		panel.add(crearSeparador());
		addHorarios(panel, i);
		return panel;
	}

	protected void addHorarios(JPanel panel, int col) {
		Instalacion instalacion = (Instalacion) cbInstalacionSeleccionada.getSelectedItem();
		if (col == 0) {
			for (int i = 8; i < 23; i++) {// i = hora
				JLabel label = new JLabel();
				label.setFont(new Font("Tahoma", Font.BOLD, 15));
				label.setText(String.valueOf(i) + ":00 - " + String.valueOf(i + 1) + ":00");
				panel.add(label);
//				if (i < 22)
//					panel.add(crearSeparador());
			}
		} else {
			for (int hora = 8; hora < 23; hora++) {
				JButton button = new JButton();
				ActividadPlanificada ap = hayInstalacionAEsaHoraYEseDia(hora, instalacion);
				Alquiler al = hayInstalacionAEsaHoraYEseDiaAlquiler(hora, instalacion);
				if ((ap != null && al != null) || hayInstalacionAEsaHoraYEseDiaVarias(hora, instalacion) > 1
						|| hayInstalacionAEsaHoraYEseDiaAlquilerVarias(hora, instalacion) > 1) {
					button.setText("Conflicto");
					button.setFont(new Font("Tahoma", Font.PLAIN, 15));
					button.setOpaque(true);
					button.setHorizontalAlignment(SwingConstants.CENTER);
					button.setBackground(Color.CYAN);
				} else if (ap != null) {
					button.setText(nombreInstalacion(instalacion, ap));
					button.setFont(new Font("Tahoma", Font.PLAIN, 15));
					button.setOpaque(true);
					button.setHorizontalAlignment(SwingConstants.CENTER);
					if (ap.getLimitePlazas() == 0) {
						button.setBackground(Color.red);
					} else {
						button.setBackground(Color.green);
					}
				} else if (al != null) {
					button.setText("Alquiler - " + nombreInstalacionAlquiler(instalacion, al));
					button.setFont(new Font("Tahoma", Font.PLAIN, 15));
					button.setOpaque(true);
					button.setHorizontalAlignment(SwingConstants.CENTER);
					button.setBackground(Color.LIGHT_GRAY);
				}

				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				String info = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + "/"
						+ String.valueOf(cal.get(Calendar.MONTH)) + "/" + String.valueOf(cal.get(Calendar.YEAR)) + "/"
						+ hora;
				button.setToolTipText(info);
				// TODO añadir funciones a los botones aqui
				button.setToolTipText(button.getText());
				panel.add(button);
			}
		}
	}

	protected ActividadPlanificada hayInstalacionAEsaHoraYEseDia(int hora, Instalacion instalacion) {
		List<ActividadPlanificada> actividades = programa.getActividadesPlanificadas();
		for (ActividadPlanificada ap : actividades) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int dia = cal.get(Calendar.DAY_OF_MONTH);
			int mes = cal.get(Calendar.MONTH) + 1;
			int año = cal.get(Calendar.YEAR);
			if (ap.getHoraInicio() <= hora && ap.getHoraFin() > hora
					&& ap.getCodigoInstalacion().equals(instalacion.getCodigo()) && dia == ap.getDia()
					&& mes == ap.getMes() && año == ap.getAño())
				return ap;
		}
		return null;
	}

	protected int hayInstalacionAEsaHoraYEseDiaVarias(int hora, Instalacion instalacion) {
		List<ActividadPlanificada> actividadesADevolver = new ArrayList<>();
		List<ActividadPlanificada> actividades = programa.getActividadesPlanificadas();
		for (ActividadPlanificada ap : actividades) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int dia = cal.get(Calendar.DAY_OF_MONTH);
			int mes = cal.get(Calendar.MONTH) + 1;
			int año = cal.get(Calendar.YEAR);
			if (ap.getHoraInicio() <= hora && ap.getHoraFin() > hora
					&& ap.getCodigoInstalacion().equals(instalacion.getCodigo()) && dia == ap.getDia()
					&& mes == ap.getMes() && año == ap.getAño())
				actividadesADevolver.add(ap);
		}
		return actividadesADevolver.size();
	}

	protected int hayInstalacionAEsaHoraYEseDiaAlquilerVarias(int hora, Instalacion instalacion) {
		List<Alquiler> alquileresADevolver = new ArrayList<>();
		List<Alquiler> alquileres = programa.getAlquileres();
		for (Alquiler al : alquileres) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int dia = cal.get(Calendar.DAY_OF_MONTH);
			int mes = cal.get(Calendar.MONTH) + 1;
			int año = cal.get(Calendar.YEAR);
			if (al.getHoraInicio() <= hora && al.getHoraFin() > hora
					&& al.getId_instalacion().equals(instalacion.getCodigo()) && dia == al.getDia()
					&& mes == al.getMes() && año == al.getAño())
				alquileresADevolver.add(al);
		}
		return alquileresADevolver.size();
	}

	protected Alquiler hayInstalacionAEsaHoraYEseDiaAlquiler(int hora, Instalacion instalacion) {
		List<Alquiler> alquileres = programa.getAlquileres();
		for (Alquiler al : alquileres) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int dia = cal.get(Calendar.DAY_OF_MONTH);
			int mes = cal.get(Calendar.MONTH) + 1;
			int año = cal.get(Calendar.YEAR);
			if (al.getHoraInicio() <= hora && al.getHoraFin() > hora
					&& al.getId_instalacion().equals(instalacion.getCodigo()) && dia == al.getDia()
					&& mes == al.getMes() && año == al.getAño())
				return al;
		}
		return null;
	}

	protected String nombreInstalacionAlquiler(Instalacion instalacion, Alquiler al) {
		if (al.getId_instalacion().equals(instalacion.getCodigo()))
			return getNombreCliente(al);
		return null;
	}

	private String getNombreCliente(Alquiler al) {
		List<Socio> socios = programa.getSocios();
		for (Socio s : socios) {
			if (al.getId_cliente().equals(s.getId_cliente())) {
				return s.getNombre() + " " + s.getApellido();
			}
		}
		return null;
	}

	protected String nombreInstalacion(Instalacion instalacion, ActividadPlanificada ap) {
		if (ap.getCodigoInstalacion().equals(instalacion.getCodigo()))
			return nombreActividad(ap);
		return null;
	}

	private String nombreActividad(ActividadPlanificada ap) {
		for (Actividad a : programa.getActividades()) {
			if (a.getCodigo().equals(ap.getCodigoActividad()))
				return a.getNombre() + " - " + a.getIntensidad() + " (P.L.:" + ap.getLimitePlazas() + ")";
		}
		return "-";
	}

	private JSeparator crearSeparador() {
		JSeparator separador = new JSeparator();
		separador.setForeground(Color.BLACK);
		return separador;
	}

//	private String generarTitulo(int i) {
//		if (i == 0) {
//			dias.add("Horarios");
//			return "Horarios";
//		}
//		Calendar cal = Calendar.getInstance();
//		int diaDeLaSemana = (cal.get(Calendar.DAY_OF_WEEK) + i - 2) % 7;
//		int diaDelMes = cal.get(Calendar.DAY_OF_MONTH);
//		String dia = "";
//		switch(diaDeLaSemana) {
//		case 0:
//			dia = "Domingo";
//			break;
//		case 1:
//			dia = "Lunes";
//			break;
//		case 2:
//			dia = "Martes";
//			break;
//		case 3:
//			dia = "Miércoles";
//			break;
//		case 4:
//			dia = "Jueves";
//			break;
//		case 5:
//			dia = "Viernes";
//			break;
//		case 6:
//			dia = "Sábado";
//			break;
//		}
//		YearMonth yearMonthObject = YearMonth.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
//		int daysInMonth = yearMonthObject.lengthOfMonth();
//		String toRet = dia + " " + ((diaDelMes + i - 2) %daysInMonth+1);
//		dias.add(toRet);
//		return toRet;
//	}

	private String generarTitulo(int i) {
		String[] dateToArray = date.toString().split(" ");
		String diaDeLaSemana = dateToArray[0];
		long primerDiaDeLaSemanaMillis = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		switch (diaDeLaSemana) {
		case "Mon":
			primerDiaDeLaSemanaMillis = date.getTime();
			break;
		case "Tue":
			primerDiaDeLaSemanaMillis = date.getTime() - DIA_EN_MILLIS;
			break;
		case "Wed":
			primerDiaDeLaSemanaMillis = date.getTime() - 2 * DIA_EN_MILLIS;
			break;
		case "Thu":
			primerDiaDeLaSemanaMillis = date.getTime() - 3 * DIA_EN_MILLIS;
			break;
		case "Fri":
			primerDiaDeLaSemanaMillis = date.getTime() - 4 * DIA_EN_MILLIS;
			break;
		case "Sat":
			primerDiaDeLaSemanaMillis = date.getTime() - 5 * DIA_EN_MILLIS;
			break;
		case "Sun":
			primerDiaDeLaSemanaMillis = date.getTime() - 6 * DIA_EN_MILLIS;
			break;
		}

		String toRet = "";
		switch (i) {
		case 0:
			toRet = "Horarios";
			break;
		case 1:
			date = new Date(primerDiaDeLaSemanaMillis);
			toRet = "Lunes ";
			break;
		case 2:
			date = new Date(primerDiaDeLaSemanaMillis + DIA_EN_MILLIS);
			toRet = "Martes ";
			break;
		case 3:
			date = new Date(primerDiaDeLaSemanaMillis + 2 * DIA_EN_MILLIS);
			toRet = "Miércoles ";
			break;
		case 4:
			date = new Date(primerDiaDeLaSemanaMillis + 3 * DIA_EN_MILLIS);
			toRet = "Jueves ";
			break;
		case 5:
			date = new Date(primerDiaDeLaSemanaMillis + 4 * DIA_EN_MILLIS);
			toRet = "Viernes ";
			break;
		case 6:
			date = new Date(primerDiaDeLaSemanaMillis + 5 * DIA_EN_MILLIS);
			toRet = "Sábado ";
			break;
		case 7:
			date = new Date(primerDiaDeLaSemanaMillis + 6 * DIA_EN_MILLIS);
			toRet = "Domingo ";
			break;
		}
		cal.setTime(date);
		if (i != 0)
			toRet += String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		dias.add(toRet);

		return toRet;
	}

	private JPanel getPnTopCenter() {
		if (pnTopCenter == null) {
			pnTopCenter = new JPanel();
			pnTopCenter.setBackground(Color.WHITE);
			pnTopCenter.add(getCbInstalacionSeleccionada());
			pnTopCenter.add(getBtnMostrarOcupacion());
		}
		return pnTopCenter;
	}

	private JPanel getPnTopEast() {
		if (pnTopEast == null) {
			pnTopEast = new JPanel();
			pnTopEast.setBackground(Color.WHITE);
			pnTopEast.setLayout(new GridLayout(0, 1, 0, 0));
			pnTopEast.add(getTxtLeyenda());
		}
		return pnTopEast;
	}

	private JTextPane getTxtLeyenda() {
		if (txtLeyenda == null) {
			txtLeyenda = new JTextPane();
			txtLeyenda.setEditable(false);
			txtLeyenda.setText("Verde: Reserva disponible\r\nRojo: Reserva completa\r\nGris: Alquiler\r\nCian: Conflicto");
		}
		return txtLeyenda;
	}

	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setLayout(new BorderLayout(0, 0));
			pnCentro.add(getPnCentralDias_1());
			pnCentro.add(getBtnPreviousWeek(), BorderLayout.WEST);
			pnCentro.add(getBtnNextWeek(), BorderLayout.EAST);
		}
		return pnCentro;
	}

	private JPanel getPnCentralDias_1() {
		if (pnCentralDias == null) {
			pnCentralDias = new JPanel();
			pnCentralDias.setBackground(Color.WHITE);
			pnCentralDias.setLayout(new GridLayout(1, 0, 1, 1));
		}
		return pnCentralDias;
	}

	private JButton getBtnPreviousWeek() {
		if (btnPreviousWeek == null) {
			btnPreviousWeek = new JButton("\u25C4");
			btnPreviousWeek.setToolTipText("Semana anterior");
			btnPreviousWeek.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					long millis = date.getTime();
					date = new Date(millis - 7 * DIA_EN_MILLIS);
					generarPaneles();
				}
			});
		}
		return btnPreviousWeek;
	}

	private JButton getBtnNextWeek() {
		if (btnNextWeek == null) {
			btnNextWeek = new JButton("\u25BA");
			btnNextWeek.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					long millis = date.getTime();
					date = new Date(millis + 7 * DIA_EN_MILLIS);
					generarPaneles();
				}
			});
		}
		return btnNextWeek;
	}

	private JLabel getLblNombreMes() {
		if (lblNombreMes == null) {
			lblNombreMes = new JLabel("");
			lblNombreMes.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblNombreMes.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNombreMes;
	}

	private String getNombreMes() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String toRet = "";
		switch (cal.get(Calendar.MONTH)) {
		case Calendar.JANUARY:
			toRet = "Enero ";
			break;
		case Calendar.FEBRUARY:
			toRet = "Febrero ";
			break;
		case Calendar.MARCH:
			toRet = "Marzo ";
			break;
		case Calendar.APRIL:
			toRet = "Abril ";
			break;
		case Calendar.MAY:
			toRet = "Mayo ";
			break;
		case Calendar.JUNE:
			toRet = "Junio ";
			break;
		case Calendar.JULY:
			toRet = "Julio ";
			break;
		case Calendar.AUGUST:
			toRet = "Agosto ";
			break;
		case Calendar.SEPTEMBER:
			toRet = "Septiembre ";
			break;
		case Calendar.OCTOBER:
			toRet = "Octubre ";
			break;
		case Calendar.NOVEMBER:
			toRet = "Noviembre ";
			break;
		case Calendar.DECEMBER:
			toRet = "Diciembre ";
			break;
		}
		toRet += cal.get(Calendar.YEAR);
		return toRet;
	}
}
