package sprint1.ui.ventanas.administracion;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Alquiler;
import sprint1.business.clases.Instalacion;
import sprint1.business.clases.Programa;
import sprint1.ui.ventanas.CalendarioSemanalBase;
import sprint1.ui.ventanas.socio.Calendario;

public class CalendarioSemanalPlanificar extends CalendarioSemanalBase {
	
	private static final long serialVersionUID = 1L;
	private Programa p;
	private ActionListener listener;

	public CalendarioSemanalPlanificar(AdminWindow parent) {
		super(parent.getParent().getPrograma());
		setTitle("Calendario Semanal");
		this.p = parent.getParent().getPrograma();
		listener = new AsignarActividades();
		setLocationRelativeTo(parent);
	}
	
	@Override
	protected void addHorarios(JPanel panel, int col) {
		Instalacion instalacion = (Instalacion) cbInstalacionSeleccionada.getSelectedItem();
		if (col == 0) {
			for(int i = 8; i < 23; i++) {//i = hora
				JLabel label = new JLabel();
				label.setFont(new Font("Tahoma", Font.BOLD, 15));
				label.setText(String.valueOf(i) + ":00 - " + String.valueOf(i+1) + ":00");
				panel.add(label);
			}
		}
		else {
			for (int hora = 8; hora < 23; hora++) {
				JButton button = new JButton();
				ActividadPlanificada ap = hayInstalacionAEsaHoraYEseDia(hora, instalacion);
				if ( ap != null) {
					button.setText(nombreInstalacion(instalacion, ap));
					button.setFont(new Font("Tahoma", Font.PLAIN, 15));
					button.setOpaque(true);
					button.setHorizontalAlignment(SwingConstants.CENTER);
					if (ap.getLimitePlazas() == 0)
						button.setBackground(Color.red);
					else
						button.setBackground(Color.green);
				}
				Alquiler al = hayInstalacionAEsaHoraYEseDiaAlquiler(hora, instalacion);
				if ( al != null) {
					button.setText("Alquiler - " + nombreInstalacionAlquiler(instalacion, al));
					button.setFont(new Font("Tahoma", Font.PLAIN, 15));
					button.setOpaque(true);
					button.setHorizontalAlignment(SwingConstants.CENTER);
					button.setBackground(Color.LIGHT_GRAY);
				}
				Calendar cal = Calendar.getInstance();
				cal.setTime(getDate());
				String info = String.valueOf(cal.get(Calendar.DAY_OF_MONTH))
						+ "/" + String.valueOf(cal.get(Calendar.MONTH))
						+ "/" + String.valueOf(cal.get(Calendar.YEAR))
						+ "/" + hora;
				button.setToolTipText(info);
				button.addActionListener(listener);
				panel.add(button);
			}
		}
	}

	private class AsignarActividades implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JButton button = (JButton) arg0.getSource();
			int[] fecha = parseFecha(button);
			int horaInicio = fecha[0];
			int dia = fecha[1];
			int mes = fecha[2];
			int a�o = fecha[3];
			AsignarActividadVariosDiasDialog aad = new AsignarActividadVariosDiasDialog(getMe(), getPrograma(), dia, mes, a�o, horaInicio);
			aad.setLocationRelativeTo(getMe());
			aad.setModal(true);
			aad.setVisible(true);			
		}
	}
	
	private CalendarioSemanalPlanificar getMe() {
		return this;
	}

	private int[] parseFecha(JButton b) {
		String[] dateInfo = b.getToolTipText().split("/");
		int[] fecha = new int[4];
		fecha[0] = Integer.parseInt(dateInfo[0]);
		fecha[1] = Integer.parseInt(dateInfo[1]);
		fecha[2] = Integer.parseInt(dateInfo[2]);
		fecha[3] = Integer.parseInt(dateInfo[3]);

		return fecha;
	}

}
