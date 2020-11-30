package sprint1.ui.ventanas.administracion.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;
import sprint1.business.dominio.centroDeportes.alquileres.Alquiler;
import sprint1.business.dominio.centroDeportes.instalaciones.Instalacion;
import sprint1.ui.ventanas.administracion.AdminWindow;
import sprint1.ui.ventanas.administracion.actividades.EscogerConflictoAResolver;
import sprint1.ui.ventanas.administracion.actividades.ModificarPlanificacionDialog;
import sprint1.ui.ventanas.administracion.actividades.PlanificarActividadFlexibilidadDialog;
import sprint1.ui.ventanas.util.CalendarioSemanalBase;

public class CalendarioSemanalModificar extends CalendarioSemanalBase {
	
	private static final long serialVersionUID = 1L;

	public CalendarioSemanalModificar(AdminWindow parent) {
		super(parent.getParent().getPrograma());
		setTitle("Calendario Semanal");
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
				button.setEnabled(false);
				ActividadPlanificada ap = hayInstalacionAEsaHoraYEseDia(hora, instalacion);
				Alquiler al = hayInstalacionAEsaHoraYEseDiaAlquiler(hora, instalacion);
				if ((ap != null && al != null) || hayInstalacionAEsaHoraYEseDiaVariasLista(hora, instalacion).size() > 1) {
					button.setEnabled(true);
					button.setText("Conflicto");
					button.setFont(new Font("Tahoma", Font.PLAIN, 15));
					button.setOpaque(true);
					button.setHorizontalAlignment(SwingConstants.CENTER);
					button.setBackground(Color.CYAN);
					button.addActionListener(new ModificarActividades(hayInstalacionAEsaHoraYEseDiaVariasLista(hora, instalacion)));
				} else if( hayInstalacionAEsaHoraYEseDiaAlquilerVarias(hora, instalacion) > 1) {
					button.setEnabled(true);
					button.setText("Conflicto");
					button.setFont(new Font("Tahoma", Font.PLAIN, 15));
					button.setOpaque(true);
					button.setHorizontalAlignment(SwingConstants.CENTER);
					button.setBackground(Color.CYAN);
					button.addActionListener(new ModificarActividades(hayInstalacionAEsaHoraYEseDiaVariasLista(hora, instalacion)));
				} else if (ap != null) {
					button.setEnabled(true);
					button.setText(nombreInstalacion(instalacion, ap));
					button.setFont(new Font("Tahoma", Font.PLAIN, 15));
					button.setOpaque(true);
					button.setHorizontalAlignment(SwingConstants.CENTER);
					if (ap.getLimitePlazas() == 0) {
						button.setBackground(Color.red);
					} else {
						button.setBackground(Color.green);
					}
					button.addActionListener(new ModificarActividades(hayInstalacionAEsaHoraYEseDiaVariasLista(hora, instalacion)));
				} else if (al != null) {
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
				panel.add(button);
			}
		}
	}

	public List<ActividadPlanificada> hayInstalacionAEsaHoraYEseDiaVariasLista(int hora, Instalacion instalacion) {
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
		return actividadesADevolver;
	}
	
	private class ModificarActividades implements ActionListener {
		
		private List<ActividadPlanificada> actividades;
		
		public ModificarActividades(List<ActividadPlanificada> listaPlanificada) {
			this.actividades = listaPlanificada;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JButton button = (JButton) arg0.getSource();
			int[] fecha = parseFecha(button);
			int dia = fecha[0];
			int mes = fecha[1];
			int año = fecha[2];
			int horaInicio = fecha[3];
			JDialog window;
			if(actividades.size() > 1) {
				window = new EscogerConflictoAResolver(getMe(), actividades);
			} else {
				window = new ModificarPlanificacionDialog(getMe(), actividades.get(0));
			}
			window.setLocationRelativeTo(getMe());
			window.setModal(true);
			window.setVisible(true);			
		}
	}
	
	private CalendarioSemanalModificar getMe() {
		return this;
	}

	private int[] parseFecha(JButton b) {
		String[] dateInfo = b.getToolTipText().split("/");
		int[] fecha = new int[4];
		fecha[0] = Integer.parseInt(dateInfo[0]); //dia
		fecha[1] = Integer.parseInt(dateInfo[1]); //mes
		fecha[2] = Integer.parseInt(dateInfo[2]); //año
		fecha[3] = Integer.parseInt(dateInfo[3]); //horaInicio

		return fecha;
	}

}
