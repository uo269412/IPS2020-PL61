package sprint1.ui.ventanas.administracion.actividades;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import sprint1.business.dominio.centroDeportes.actividades.Actividad;
import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;
import sprint1.business.dominio.centroDeportes.reservas.Reserva;
import sprint1.ui.ventanas.administracion.util.CalendarioSemanalCancelar;

public class CancelarActividadDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private CalendarioSemanalCancelar parent;
	private JPanel pnActividad;
	private JLabel lblActividad;
	private JLabel lblDia;
	private JLabel lblHora;
	private JLabel lblNombreActividad;
	private JLabel lblDiaActividad;
	private JLabel lblHoraActividad;
	private ActividadPlanificada actividad;
	private JPanel pnButtons;
	private JRadioButton rdbtnDiaConcreto;
	private JLabel lblOpcion;

	/**
	 * Create the dialog.
	 */
	public CancelarActividadDialog(CalendarioSemanalCancelar parent, ActividadPlanificada actividadPlanificada) {
		this.parent = parent;
		this.actividad = actividadPlanificada;
		setTitle("Cancelar actividad");
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 440, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		FlowLayout fl_contentPanel = new FlowLayout();
		fl_contentPanel.setAlignment(FlowLayout.LEFT);
		contentPanel.setLayout(fl_contentPanel);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.add(getPnActividad());
		contentPanel.add(getPnButtons());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.WHITE);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Cancelar actividad");
				okButton.setForeground(Color.WHITE);
				okButton.setBackground(new Color(34, 139, 34));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						cancelarActividad();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Volver");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setForeground(Color.WHITE);
				cancelButton.setBackground(new Color(30, 144, 255));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnDiaConcreto);
	}
	protected void cancelarActividad() {
		if (JOptionPane.showConfirmDialog(this, "¿Seguro que quiere eliminar esta actividad y las reservas relacionadas?") == JOptionPane.YES_OPTION) {
			try {
				parent.getPrograma().eliminarActividadPlanificada(actividad);
				parent.getPrograma().eliminarReserva(actividad.getCodigoPlanificada());
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "Ha habido un error eliminando la actividad planificada. Por favor, póngase en contacto con el administrador");
			}
			JOptionPane.showMessageDialog(this, "Actividad y reservas eliminadas correctamente.");
			dispose();
		}
	}
	private JPanel getPnActividad() {
		if (pnActividad == null) {
			pnActividad = new JPanel();
			pnActividad.setBackground(Color.WHITE);
			pnActividad.setLayout(new GridLayout(2, 3, 10, 5));
			pnActividad.add(getLblActividad());
			pnActividad.add(getLblDia());
			pnActividad.add(getLblHora());
			pnActividad.add(getLblNombreActividad());
			pnActividad.add(getLblDiaActividad());
			pnActividad.add(getLblHoraActividad());
		}
		return pnActividad;
	}
	private JLabel getLblActividad() {
		if (lblActividad == null) {
			lblActividad = new JLabel("Actividad:");
			lblActividad.setFont(new Font("Tahoma", Font.BOLD, 16));
		}
		return lblActividad;
	}
	private JLabel getLblDia() {
		if (lblDia == null) {
			lblDia = new JLabel("D\u00EDa:");
			lblDia.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return lblDia;
	}
	private JLabel getLblHora() {
		if (lblHora == null) {
			lblHora = new JLabel("Hora:");
			lblHora.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return lblHora;
	}
	private JLabel getLblNombreActividad() {
		if (lblNombreActividad == null) {
			lblNombreActividad = new JLabel(nombreActividad());
		}
		return lblNombreActividad;
	}
	private String nombreActividad() {
		for (Actividad a : parent.getPrograma().getActividades()) {
			if (a.getCodigo().equals(actividad.getCodigoActividad())) {
				return a.getNombre();
			}
		}
		return null;
	}
	private JLabel getLblDiaActividad() {
		if (lblDiaActividad == null) {
			lblDiaActividad = new JLabel(actividad.getFecha());
		}
		return lblDiaActividad;
	}
	private JLabel getLblHoraActividad() {
		if (lblHoraActividad == null) {
			lblHoraActividad = new JLabel(actividad.getHoraInicio() + ":00-" + actividad.getHoraFin() + ":00");
		}
		return lblHoraActividad;
	}
	private JPanel getPnButtons() {
		if (pnButtons == null) {
			pnButtons = new JPanel();
			pnButtons.setBackground(Color.WHITE);
			pnButtons.setLayout(new GridLayout(0, 1, 0, 0));
			pnButtons.add(getLblOpcion());
			pnButtons.add(getRdbtnDiaConcreto());
		}
		return pnButtons;
	}
	private JRadioButton getRdbtnDiaConcreto() {
		if (rdbtnDiaConcreto == null) {
			rdbtnDiaConcreto = new JRadioButton("Solo el d\u00EDa seleccionado");
			rdbtnDiaConcreto.setSelected(true);
			rdbtnDiaConcreto.setBackground(Color.WHITE);
		}
		return rdbtnDiaConcreto;
	}
	private JLabel getLblOpcion() {
		if (lblOpcion == null) {
			lblOpcion = new JLabel("Cancelar para:");
			lblOpcion.setFont(new Font("Tahoma", Font.BOLD, 16));
		}
		return lblOpcion;
	}
}
