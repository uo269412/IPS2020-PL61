package sprint1.ui.ventanas.administracion.estado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
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

public class VerOcupacionDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnPrincipal;
	private JPanel pnNorth;
	private JComboBox<Instalacion> cbInstalacionSeleccionada;
	private JButton btnMostrarOcupacion;
	private JPanel pnCentralDias;
	private Programa programa = null;
	private ArrayList<String> dias = new ArrayList<String>();
	private JPanel pnTopCenter;
	private JPanel pnTopEast;
	private JTextPane txtLeyenda;

	/**
	 * Create the frame.
	 */
	public VerOcupacionDialog(Programa p) {
		setTitle("Ver ocupación");
		this.programa = p;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1335, 792);
		pnPrincipal = new JPanel();
		pnPrincipal.setBackground(Color.WHITE);
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnPrincipal.setLayout(new BorderLayout(0, 0));
		setContentPane(pnPrincipal);
		pnPrincipal.add(getPnNorth(), BorderLayout.NORTH);
		pnPrincipal.add(getPnCentralDias(), BorderLayout.CENTER);
		generarPaneles();
	}

	private JPanel getPnNorth() {
		if (pnNorth == null) {
			pnNorth = new JPanel();
			pnNorth.setBorder(null);
			pnNorth.setBackground(Color.WHITE);
			pnNorth.setLayout(new BorderLayout(0, 0));
			pnNorth.add(getPnTopCenter(), BorderLayout.CENTER);
			pnNorth.add(getPnTopEast(), BorderLayout.EAST);
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
	private JPanel getPnCentralDias() {
		if (pnCentralDias == null) {
			pnCentralDias = new JPanel();
			pnCentralDias.setBackground(Color.WHITE);
			pnCentralDias.setLayout(new GridLayout(1, 0, 1, 1));
		}
		return pnCentralDias;
	}
	
	private void generarPaneles() {
		pnCentralDias.removeAll();
		for (int i = 0; i <= 7; i++) {
			pnCentralDias.add(newDay(i));
		}
		pnCentralDias.validate();
	}

	private JPanel newDay(int i) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(31, 1));
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

	private void addHorarios(JPanel panel, int col) {
		Instalacion instalacion = (Instalacion) cbInstalacionSeleccionada.getSelectedItem();
		if (col == 0) {
			for(int i = 8; i < 23; i++) {//i = hora
				JLabel label = new JLabel();
				label.setFont(new Font("Tahoma", Font.BOLD, 15));
				label.setText(String.valueOf(i) + ":00 - " + String.valueOf(i+1) + ":00");
				panel.add(label);
				if (i < 22)
					panel.add(crearSeparador());
			}
		}
		else {
			for (int hora = 8; hora < 23; hora++) {
				JLabel label = new JLabel();
				ActividadPlanificada ap = hayInstalacionAEsaHoraYEseDia(hora, instalacion, col);
				if ( ap != null) {
					label.setText(nombreInstalacion(instalacion, ap));
					label.setFont(new Font("Tahoma", Font.PLAIN, 15));
					label.setOpaque(true);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					if (ap.getLimitePlazas() == 0)
						label.setBackground(Color.red);
					else
						label.setBackground(Color.green);
				}
				Alquiler al = hayInstalacionAEsaHoraYEseDiaAlquiler(hora, instalacion, col);
				if ( al != null) {
					label.setText("Alquiler - " + nombreInstalacionAlquiler(instalacion, al));
					label.setFont(new Font("Tahoma", Font.PLAIN, 15));
					label.setOpaque(true);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setBackground(Color.LIGHT_GRAY);
				}
				panel.add(label);
				if (hora < 22) //ultima hora no necesita separador
					panel.add(crearSeparador());
			}
		}
	}

	private ActividadPlanificada hayInstalacionAEsaHoraYEseDia(int hora, Instalacion instalacion, int col) {
		List<ActividadPlanificada> actividades = programa.getActividadesPlanificadas();
		for (ActividadPlanificada ap : actividades) {
			int dia = Integer.parseInt(dias.get(col).split(" ")[1]);
			if (ap.getHoraInicio() <= hora && ap.getHoraFin() > hora && ap.getCodigoInstalacion().equals(instalacion.getCodigo()) && dia == ap.getDia())
				return ap;
		}
		return null;
	}
	
	private Alquiler hayInstalacionAEsaHoraYEseDiaAlquiler(int hora, Instalacion instalacion, int col) {
		List<Alquiler> alquileres = programa.getAlquileres();
		for (Alquiler al : alquileres) {
			int dia = Integer.parseInt(dias.get(col).split(" ")[1]);
			if (al.getHoraInicio() <= hora && al.getHoraFin() > hora && al.getId_instalacion().equals(instalacion.getCodigo()) && dia == al.getDia())
				return al;
		}
		return null;
	}
	
	private String nombreInstalacionAlquiler(Instalacion instalacion, Alquiler al) {
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

	private String nombreInstalacion(Instalacion instalacion, ActividadPlanificada ap) {
		if (ap.getCodigoInstalacion().equals(instalacion.getCodigo()))
			return nombreActividad(ap);
		return null;
	}

	private String nombreActividad(ActividadPlanificada ap) {
		for (Actividad a : programa.getActividades()) {
			if(a.getCodigo().equals(ap.getCodigoActividad()))
				return a.getNombre() + " - " + a.getIntensidad() + " (P.L.:" + ap.getLimitePlazas() + ")";
		}
		return "-";
	}

	private JSeparator crearSeparador() {
		JSeparator separador = new JSeparator();
		separador.setForeground(Color.BLACK);
		return separador;
	}
	
	private String generarTitulo(int i) {
		if (i == 0) {
			dias.add("Horarios");
			return "Horarios";
		}
		Calendar cal = Calendar.getInstance();
		int diaDeLaSemana = (cal.get(Calendar.DAY_OF_WEEK) + i - 2) % 7;
		int diaDelMes = cal.get(Calendar.DAY_OF_MONTH);
		String dia = "";
		switch(diaDeLaSemana) {
		case 0:
			dia = "Domingo";
			break;
		case 1:
			dia = "Lunes";
			break;
		case 2:
			dia = "Martes";
			break;
		case 3:
			dia = "Miércoles";
			break;
		case 4:
			dia = "Jueves";
			break;
		case 5:
			dia = "Viernes";
			break;
		case 6:
			dia = "Sábado";
			break;
		}
		YearMonth yearMonthObject = YearMonth.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
		int daysInMonth = yearMonthObject.lengthOfMonth();
		String toRet = dia + " " + ((diaDelMes + i - 2) %daysInMonth+1);
		dias.add(toRet);
		return toRet;
	}
	
//	private String generarTitulo(int i) {
//		Date date = new Date();
//		String[] dateToArray = date.toString().split(" ");
//		String diaDeLaSemana = dateToArray[0];
//		int numeroDeDia = Integer.parseInt(dateToArray[2]);
//		int primerDiaDeLaSemana = 0;
//		
//		switch (diaDeLaSemana) {
//		case "Mon":
//			primerDiaDeLaSemana = numeroDeDia;
//			break;
//		case "Tue":
//			primerDiaDeLaSemana = numeroDeDia - 1;
//			break;
//		case "Wed":
//			primerDiaDeLaSemana = numeroDeDia - 2;
//			break;
//		case "Thu":
//			primerDiaDeLaSemana = numeroDeDia - 3;
//			break;
//		case "Fri":
//			primerDiaDeLaSemana = numeroDeDia - 4;
//			break;
//		case "Sat":
//			primerDiaDeLaSemana = numeroDeDia - 5;
//			break;
//		case "Sun":
//			primerDiaDeLaSemana = numeroDeDia - 6;
//			break;
//		}
//		
//		String toRet = "";
//		switch (i) {
//		case 0:
//			toRet = "Horarios";
//			break;
//		case 1:
//			toRet = "Domingo " + String.valueOf(primerDiaDeLaSemana + 6);
//			break;
//		case 2:
//			toRet = "Lunes " + String.valueOf(primerDiaDeLaSemana);
//			break;
//		case 3:
//			toRet = "Martes " + String.valueOf(primerDiaDeLaSemana+ 1);
//			break;
//		case 4:
//			toRet = "Miércoles " + String.valueOf(primerDiaDeLaSemana + 2);
//			break;
//		case 5:
//			toRet = "Jueves " + String.valueOf(primerDiaDeLaSemana + 3);
//			break;
//		case 6:
//			toRet = "Viernes " + String.valueOf(primerDiaDeLaSemana + 4);
//			break;
//		case 7:
//			toRet = "Sábado " + String.valueOf(primerDiaDeLaSemana + 5);
//			break;
//		}
//		dias.add(toRet);
//		
//		return toRet;
//	}
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
			txtLeyenda.setText("Verde: Reserva disponible\r\nRojo: Reserva completa\r\nGris: Alquiler\r\n");
		}
		return txtLeyenda;
	}
}
