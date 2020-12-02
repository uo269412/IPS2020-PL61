package sprint1.ui.ventanas.administracion.actividades;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import sprint1.business.dominio.centroDeportes.actividades.Actividad;
import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;
import sprint1.business.dominio.clientes.Socio;
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
	private JRadioButton rdbtnTodasActividades;
	private Actividad actividadBase;
	private JScrollPane scrollPane;
	private DefaultListModel<ActividadPlanificada> listaModelo;

	/**
	 * Create the dialog.
	 */
	public CancelarActividadDialog(CalendarioSemanalCancelar parent, ActividadPlanificada actividadPlanificada) {
		setResizable(false);
		this.parent = parent;
		this.actividad = actividadPlanificada;
		this.actividadBase = getActividadBase();
		this.scrollPane = new JScrollPane();
		this.listaModelo = new DefaultListModel<ActividadPlanificada>();
		setTitle("Cancelar actividad");
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 429, 461);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
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
						cancelarActividades();
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
		group.add(rdbtnTodasActividades);
	}
	protected void cancelarActividades() {
		if (rdbtnDiaConcreto.isSelected()) {
			cancelarActividad();
		}
		else if (rdbtnTodasActividades.isSelected()) {
			cancelarGrupoActividades();
		}
	}
	protected void cancelarActividad() {
		if (JOptionPane.showConfirmDialog(this, "¿Seguro que quiere eliminar esta actividad y las reservas relacionadas?") == JOptionPane.YES_OPTION) {
			try {
				List<Socio> afectados = parent.getPrograma().eliminarReserva(actividad.getCodigoPlanificada());
				parent.getPrograma().eliminarActividadPlanificada(actividad);
				printAfectados(afectados);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "Ha habido un error eliminando la actividad planificada. Por favor, póngase en contacto con el administrador");
			}
			JOptionPane.showMessageDialog(this, "Actividad y reservas eliminadas correctamente.");
			dispose();
		}
	}
	private void cancelarGrupoActividades() {
		if (JOptionPane.showConfirmDialog(this, "¿Seguro que quiere eliminar esta actividad y las actividades y reservas relacionadas?") == JOptionPane.YES_OPTION) {
			for(int i = 0; i < listaModelo.getSize(); i++) {
				try {
					List<Socio> afectados = parent.getPrograma().eliminarReserva(listaModelo.get(i).getCodigoPlanificada());
					parent.getPrograma().eliminarActividadPlanificada(listaModelo.get(i));
					printAfectados(afectados);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(this, "Ha habido un error eliminando la actividad planificada. Por favor, póngase en contacto con el administrador");
				}
			}
			JOptionPane.showMessageDialog(this, "Actividades y reservas eliminadas correctamente.");
			dispose();
		}
		
	}
	private void printAfectados(List<Socio> afectados) {
		if (!afectados.isEmpty()) {
			System.out.println("------------SOCIOS AFECTADOS------------");
			for (Socio s : afectados) {
				System.out.println("\t" + s.getNombre() + " " + s.getApellido());
			}
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
			lblNombreActividad = new JLabel(actividadBase.getNombre());
		}
		return lblNombreActividad;
	}
	private Actividad getActividadBase() {
		for (Actividad a : parent.getPrograma().getActividades()) {
			if (a.getCodigo().equals(actividad.getCodigoActividad())) {
				return a;
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
			GridBagLayout gbl_pnButtons = new GridBagLayout();
			gbl_pnButtons.columnWidths = new int[] {160, 200, 0};
			gbl_pnButtons.rowHeights = new int[]{25, 25, 0};
			gbl_pnButtons.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			gbl_pnButtons.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			pnButtons.setLayout(gbl_pnButtons);
			GridBagConstraints gbc_lblOpcion = new GridBagConstraints();
			gbc_lblOpcion.fill = GridBagConstraints.BOTH;
			gbc_lblOpcion.insets = new Insets(0, 0, 5, 5);
			gbc_lblOpcion.gridx = 0;
			gbc_lblOpcion.gridy = 0;
			pnButtons.add(getLblOpcion(), gbc_lblOpcion);
			GridBagConstraints gbc_rdbtnDiaConcreto = new GridBagConstraints();
			gbc_rdbtnDiaConcreto.fill = GridBagConstraints.BOTH;
			gbc_rdbtnDiaConcreto.insets = new Insets(0, 0, 0, 5);
			gbc_rdbtnDiaConcreto.gridx = 0;
			gbc_rdbtnDiaConcreto.gridy = 1;
			pnButtons.add(getRdbtnDiaConcreto(), gbc_rdbtnDiaConcreto);
			GridBagConstraints gbc_rdbtnTodasActividades = new GridBagConstraints();
			gbc_rdbtnTodasActividades.fill = GridBagConstraints.BOTH;
			gbc_rdbtnTodasActividades.gridx = 1;
			gbc_rdbtnTodasActividades.gridy = 1;
			pnButtons.add(getRdbtnTodasActividades(), gbc_rdbtnTodasActividades);
		}
		return pnButtons;
	}
	private JRadioButton getRdbtnDiaConcreto() {
		if (rdbtnDiaConcreto == null) {
			rdbtnDiaConcreto = new JRadioButton("Solo el d\u00EDa seleccionado");
			rdbtnDiaConcreto.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					contentPanel.remove(scrollPane);
					contentPanel.validate();
					contentPanel.repaint();
				}
			});
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
	private JRadioButton getRdbtnTodasActividades() {
		if (rdbtnTodasActividades == null) {
			rdbtnTodasActividades = new JRadioButton("Todas las actividades de este tipo");
			rdbtnTodasActividades.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					muestraActividades();
				}
			});
			rdbtnTodasActividades.setBackground(Color.WHITE);
		}
		return rdbtnTodasActividades;
	}
	protected void muestraActividades() {
		scrollPane.setBackground(Color.white);
		for (ActividadPlanificada ap : parent.getPrograma().getActividadesPlanificadas()) {
			if (ap.getCodigoActividad().equals(actividadBase.getCodigo())) {
				listaModelo.addElement(ap);
			}
		}
		JList<ActividadPlanificada> lista = new JList<ActividadPlanificada>(listaModelo);
		scrollPane.setViewportView(lista);
		scrollPane.setPreferredSize(new Dimension(395,250));
		contentPanel.add(scrollPane);
		contentPanel.validate();
		contentPanel.repaint();
	}
}
