package sprint1.ui.ventanas.socio;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sprint1.business.clases.Actividad;
import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.ComparadorPorDia;
import sprint1.business.clases.Programa;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;

public class VerHorariosSocioWindow extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTitulo;
	private JPanel pnCentro;
	private JPanel pnSur;
	private JButton btnVolver;
	private Programa programa;

	/**
	 * Create the frame.
	 */
	public VerHorariosSocioWindow(Programa p) {
		this.programa = p;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 734, 518);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getLblTitulo(), BorderLayout.NORTH);
		contentPane.add(getPnCentro(), BorderLayout.CENTER);
		contentPane.add(getPnSur(), BorderLayout.SOUTH);
		crearHorarios();
	}

	private JLabel getLblTitulo() {
		if (lblTitulo == null) {
			lblTitulo = new JLabel("Horarios:");
			lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 36));
		}
		return lblTitulo;
	}
	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setBackground(Color.WHITE);
			pnCentro.setLayout(new BoxLayout(pnCentro, BoxLayout.Y_AXIS));
		}
		return pnCentro;
	}
	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
			pnSur.setBackground(Color.WHITE);
			pnSur.add(getBtnVolver());
		}
		return pnSur;
	}
	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VerHorariosSocioWindow.this.dispose();
				}
			});
			btnVolver.setMnemonic('V');
			btnVolver.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return btnVolver;
	}
	private void crearHorarios() {
		Date hoy = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(hoy);
		int diaDeHoy = cal.get(Calendar.DAY_OF_MONTH);
		int mesActual = cal.get(Calendar.MONTH) + 1;
		int añoActual = cal.get(Calendar.YEAR);
		List<ActividadPlanificada> actividades = programa.getActividadesPlanificadas();
		actividades.sort(new ComparadorPorDia());
		boolean tituloPuesto = false;
		ActividadPlanificada previousAp = null;
		for (ActividadPlanificada ap : actividades) {
			if (previousAp != null && (ap.getDia() != previousAp.getDia() || ap.getMes() != previousAp.getMes() || ap.getAño() != previousAp.getAño())) {
				tituloPuesto = false;
			}
			if (!tituloPuesto && (ap.getAño() > añoActual || ap.getAño() == añoActual && ap.getMes() > mesActual ||
					ap.getAño() == añoActual && ap.getMes() == mesActual && ap.getDia() >= diaDeHoy)) {
				añadirLabelTituloDia(ap.getDia(), ap.getMes(), ap.getAño());
				tituloPuesto = true;
				añadirLabelActividad(ap);
			}
			previousAp = ap;
		}
	}

	private void añadirLabelTituloDia(int dia, int mes, int año) {
		String fechaString = dia + "/" + mes + "/" + año;
		Date fecha = null;
		try {
			fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		JLabel lblDia = new JLabel();
		lblDia.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDia.setText("■ " + obtenerDia(cal.get(Calendar.DAY_OF_WEEK)) + " " 
				+ dia + " de " + obtenerMes(mes) + ", " + año + ":");
		pnCentro.add(lblDia);
	}
	
	private void añadirLabelActividad(ActividadPlanificada ap) {
		JLabel lblActividad = new JLabel();
		List<Actividad> actividades = programa.getActividades();
		String labelText = "    ● ";
		for (Actividad a : actividades) {
			if (a.getCodigo().equals(ap.getCodigoActividad())) {
				labelText += a.getNombre() + " (" + ap.getHoraInicio() + ":00 - " + ap.getHoraFin() + ":00)";
				if (ap.esDeLibreAcceso()) {
					labelText += " - Límite de plazas: " + ap.getLimitePlazas();
				} else {
					labelText += " - Actividad de libre acceso.";
				}
				break;
			}
		}
		lblActividad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblActividad.setText(labelText);
		pnCentro.add(lblActividad);
	}

	private String obtenerDia(int numeroDiaSemana) {
		switch (numeroDiaSemana) {
		case Calendar.SUNDAY:
			return "Domingo";
		case Calendar.MONDAY:
			return "Lunes";
		case Calendar.TUESDAY:
			return "Martes";
		case Calendar.WEDNESDAY:
			return "Miércoles";
		case Calendar.THURSDAY:
			return "Jueves";
		case Calendar.FRIDAY:
			return "Viernes";
		case Calendar.SATURDAY:
			return "Sábado";
		}
		return null;
	}
	
	private String obtenerMes(int mes) {
		switch (mes) {
		case 1:
			return "Enero";
		case 2:
			return "Febrero";
		case 3: 
			return "Marzo";
		case 4:
			return "Abril";
		case 5:
			return "Mayo";
		case 6:
			return "Junio";
		case 7:
			return "Julio";
		case 8:
			return "Agosto";
		case 9:
			return "Septiembre";
		case 10:
			return "Octubre";
		case 11:
			return "Noviembre";
		case 12:
			return "Diciembre";
		}
		return null;
	}

}