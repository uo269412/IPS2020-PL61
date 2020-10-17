package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import sprint1.business.clases.Actividad;
import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Programa;

import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class VerOcupacionWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnPrincipal;
	private JPanel pnNorth;
	private JComboBox<Actividad> cbActividadSeleccionada;
	private JButton btnMostrarOcupacion;
	private JPanel pnCentralDias;
	private Programa programa = null;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VerOcupacionWindow frame = new VerOcupacionWindow();
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
	public VerOcupacionWindow(Programa p) {
		this.programa = p;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 891, 560);
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
			pnNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnNorth.add(getCbActividadSeleccionada());
			pnNorth.add(getBtnMostrarOcupacion());
		}
		return pnNorth;
	}
	private JComboBox<Actividad> getCbActividadSeleccionada() {
		if (cbActividadSeleccionada == null) {
			cbActividadSeleccionada = new JComboBox<Actividad>();
			List<Actividad> actividades = programa.getActividades();
			Actividad[] arrayActividades = new Actividad[actividades.size()];
			arrayActividades = actividades.toArray(arrayActividades);
			cbActividadSeleccionada.setModel(new DefaultComboBoxModel<Actividad>(arrayActividades));
			cbActividadSeleccionada.setSelectedIndex(0);
		}
		return cbActividadSeleccionada;
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
		Actividad actividad = (Actividad) cbActividadSeleccionada.getSelectedItem();
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
				if (hayActividadAEsaHora(hora, actividad, col)) {
					label.setText(nombreActividad(actividad));
					label.setFont(new Font("Tahoma", Font.PLAIN, 15));
				}
				panel.add(label);
				if (hora < 22) //ultima hora no necesita separador
					panel.add(crearSeparador());
			}
		}
	}

	private boolean hayActividadAEsaHora(int hora, Actividad actividad, int dia) {
		List<ActividadPlanificada> actividades = programa.getActividadesPlanificadas();
		for (ActividadPlanificada ap : actividades) {
			if (ap.getHoraInicio() == hora /*&& dia == ap.getFecha().getDay()*/)
				return true;
		}
		return false;
	}

	private String nombreActividad(Actividad actividad) {
		List<ActividadPlanificada> actividades = programa.getActividadesPlanificadas();
		for (ActividadPlanificada ap : actividades) {
			if (ap.getCodigoActividad().equals(actividad.getCodigo()))
				return actividad.getNombre();
		}
		return null;
	}

	private JSeparator crearSeparador() {
		JSeparator separador = new JSeparator();
		separador.setForeground(Color.BLACK);
		return separador;
	}
	
	private String generarTitulo(int i) {
		Date date = new Date();
		String[] dateToArray = date.toString().split(" ");
		String diaDeLaSemana = dateToArray[0];
		int numeroDeDia = Integer.parseInt(dateToArray[2]);
		int primerDiaDeLaSemana = 0;
		
		switch (diaDeLaSemana) {
		case "Mon":
			primerDiaDeLaSemana = numeroDeDia;
			break;
		case "Tue":
			primerDiaDeLaSemana = numeroDeDia - 1;
			break;
		case "Wed":
			primerDiaDeLaSemana = numeroDeDia - 2;
			break;
		case "Thu":
			primerDiaDeLaSemana = numeroDeDia - 3;
			break;
		case "Fri":
			primerDiaDeLaSemana = numeroDeDia - 4;
			break;
		case "Sat":
			primerDiaDeLaSemana = numeroDeDia - 5;
			break;
		case "Sun":
			primerDiaDeLaSemana = numeroDeDia - 6;
			break;
		}
		
		String toRet = "";
		switch (i) {
		case 0:
			toRet = "Horarios";
			break;
		case 1:
			toRet = "Lunes " + String.valueOf(primerDiaDeLaSemana);
			break;
		case 2:
			toRet = "Martes " + String.valueOf(primerDiaDeLaSemana + 1);
			break;
		case 3:
			toRet = "Miércoles " + String.valueOf(primerDiaDeLaSemana+ 2);
			break;
		case 4:
			toRet = "Jueves " + String.valueOf(primerDiaDeLaSemana + 3);
			break;
		case 5:
			toRet = "Viernes " + String.valueOf(primerDiaDeLaSemana + 4);
			break;
		case 6:
			toRet = "Sábado " + String.valueOf(primerDiaDeLaSemana + 5);
			break;
		case 7:
			toRet = "Domingo " + String.valueOf(primerDiaDeLaSemana + 6);
			break;
		}		
		
		return toRet;
	}
}
