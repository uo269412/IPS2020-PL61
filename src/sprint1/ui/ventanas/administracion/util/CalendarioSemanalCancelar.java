package sprint1.ui.ventanas.administracion.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;
import sprint1.business.dominio.centroDeportes.alquileres.Alquiler;
import sprint1.business.dominio.centroDeportes.instalaciones.Instalacion;
import sprint1.ui.ventanas.administracion.AdminWindow;
import sprint1.ui.ventanas.administracion.actividades.CancelarActividadDialog;
import sprint1.ui.ventanas.administracion.actividades.CancelarFranjaWindow;
import sprint1.ui.ventanas.administracion.actividades.EscogerActividadACancelar;

public class CalendarioSemanalCancelar extends CalendarioSemanalModificar{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CalendarioSemanalCancelar(AdminWindow parent) {
		super(parent);
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
	
	private class ModificarActividades implements ActionListener {
		
		private List<ActividadPlanificada> actividades;
		
		public ModificarActividades(List<ActividadPlanificada> listaPlanificada) {
			this.actividades = listaPlanificada;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JDialog window;
			if(actividades.size() > 1) {
				window = new EscogerActividadACancelar(getMe(), actividades);
			} else {
				window = new CancelarActividadDialog(getMe(), actividades.get(0));
			}
			window.setLocationRelativeTo(getMe());
			window.setModal(true);
			window.setVisible(true);
			generarPaneles();
		}
	}
	
	private CalendarioSemanalCancelar getMe() {
		return this;
	}
	
	@Override
	public JPanel newDay(int i) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(17, 1));
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		String titulo = generarTitulo(i);
		JButton btnTitulo = new JButton();
		btnTitulo.setOpaque(true);
		btnTitulo.setBackground(Color.LIGHT_GRAY);
		btnTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTitulo.setText(titulo);
		if (titulo.equals("Horarios"))
			btnTitulo.setEnabled(false);
		else {
			btnTitulo.addActionListener(new CancelarFranja(titulo));
		}
		panel.add(btnTitulo);
		panel.add(crearSeparador());
		addHorarios(panel, i);
		return panel;
	}
	
private class CancelarFranja implements ActionListener {
		
		private String titulo;
		
		public CancelarFranja(String titulo) {
			this.titulo = titulo;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			CancelarFranjaWindow cfw = new CancelarFranjaWindow(CalendarioSemanalCancelar.this, titulo, getLblNombreMes().getText());
			cfw.setModal(true);
			cfw.setLocationRelativeTo(CalendarioSemanalCancelar.this);
			cfw.setVisible(true);
			generarPaneles();
		}
	}

}
