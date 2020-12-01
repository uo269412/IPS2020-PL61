package sprint1.ui.ventanas.administracion.actividades;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;
import sprint1.ui.ventanas.administracion.util.CalendarioSemanalCancelar;

public class CancelarFranjaWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private CalendarioSemanalCancelar parent;
	private JLabel lblCancelando;
	private String titulo;
	private JPanel pnHorarios;
	private JComboBox<Integer> cbHoraInicio;
	private JComboBox<Integer> cbHoraFin;
	private JLabel lblHoraInicio;
	private JLabel lblHoraFin;
	private DefaultComboBoxModel<Integer> cbInicioModelo;
	private DefaultComboBoxModel<Integer> cbFinModelo;
	private JScrollPane scrollPane;
	private DefaultListModel<ActividadPlanificada> listaModelo;
	private String mes;
	private JButton btnRefrescar;

	/**
	 * Create the dialog.
	 */
	public CancelarFranjaWindow(CalendarioSemanalCancelar parent, String titulo, String mes) {
		setResizable(false);
		this.parent = parent;
		this.titulo = titulo;
		this.mes = mes;
		this.scrollPane = new JScrollPane();
		this.listaModelo = new DefaultListModel<ActividadPlanificada>();
		creaModelos();
		setTitle("Cancelar actividad");
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 448, 461);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		contentPanel.add(getLblCancelando());
		contentPanel.add(getPnHorarios());
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
		updateHoraFin();
		muestraActividades();
	}

	private void creaModelos() {
		cbInicioModelo = new DefaultComboBoxModel<Integer>();
		cbFinModelo = new DefaultComboBoxModel<Integer>();
		for (Integer i = 8; i < 23; i++)
			cbInicioModelo.addElement(i);
	}

	protected void cancelarActividades() {
		if (JOptionPane.showConfirmDialog(this, "¿Seguro que quiere eliminar esta actividad y las actividades y reservas relacionadas?") == JOptionPane.YES_OPTION) {
			for(int i = 0; i < listaModelo.getSize(); i++) {
				try {
					parent.getPrograma().eliminarActividadPlanificada(listaModelo.get(i));
					parent.getPrograma().eliminarReserva(listaModelo.get(i).getCodigoActividad());
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(this, "Ha habido un error eliminando la actividad planificada. Por favor, póngase en contacto con el administrador");
				}
			}
			JOptionPane.showMessageDialog(this, "Actividades y reservas eliminadas correctamente.");
			dispose();
		}
	}

	private JLabel getLblCancelando() {
		if (lblCancelando == null) {
			lblCancelando = new JLabel("Cancelando actividades para franja horaria el dia " + titulo);
			lblCancelando.setFont(new Font("Tahoma", Font.BOLD, 13));
		}
		return lblCancelando;
	}
	private JPanel getPnHorarios() {
		if (pnHorarios == null) {
			pnHorarios = new JPanel();
			pnHorarios.setBackground(Color.WHITE);
			GridBagLayout gbl_pnHorarios = new GridBagLayout();
			gbl_pnHorarios.columnWidths = new int[] {100, 100, 0};
			gbl_pnHorarios.rowHeights = new int[] {30, 0, 0};
			gbl_pnHorarios.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			gbl_pnHorarios.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			pnHorarios.setLayout(gbl_pnHorarios);
			GridBagConstraints gbc_lblHoraInicio = new GridBagConstraints();
			gbc_lblHoraInicio.insets = new Insets(0, 0, 5, 5);
			gbc_lblHoraInicio.gridx = 0;
			gbc_lblHoraInicio.gridy = 0;
			pnHorarios.add(getLblHoraInicio(), gbc_lblHoraInicio);
			GridBagConstraints gbc_lblHoraFin = new GridBagConstraints();
			gbc_lblHoraFin.insets = new Insets(0, 0, 5, 0);
			gbc_lblHoraFin.gridx = 1;
			gbc_lblHoraFin.gridy = 0;
			pnHorarios.add(getLblHoraFin(), gbc_lblHoraFin);
			GridBagConstraints gbc_cbHoraInicio = new GridBagConstraints();
			gbc_cbHoraInicio.insets = new Insets(0, 0, 0, 5);
			gbc_cbHoraInicio.gridx = 0;
			gbc_cbHoraInicio.gridy = 1;
			pnHorarios.add(getCbHoraInicio(), gbc_cbHoraInicio);
			GridBagConstraints gbc_cbHoraFin = new GridBagConstraints();
			gbc_cbHoraFin.gridx = 1;
			gbc_cbHoraFin.gridy = 1;
			pnHorarios.add(getCbHoraFin(), gbc_cbHoraFin);
		}
		return pnHorarios;
	}
	private JComboBox<Integer> getCbHoraInicio() {
		if (cbHoraInicio == null) {
			cbHoraInicio = new JComboBox<Integer>(cbInicioModelo);
			cbHoraInicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					updateHoraFin();
				}
			});
			cbHoraInicio.setAlignmentX(Component.RIGHT_ALIGNMENT);
		}
		return cbHoraInicio;
	}
	protected void updateHoraFin() {
		Integer selected = (Integer) cbHoraInicio.getSelectedItem();
		cbFinModelo.removeAllElements();
		for (Integer i = selected + 1 ; i <= 23; i++) {
			cbFinModelo.addElement(i);
		}
	}

	private JComboBox<Integer> getCbHoraFin() {
		if (cbHoraFin == null) {
			cbHoraFin = new JComboBox<Integer>(cbFinModelo);
		}
		return cbHoraFin;
	}
	private JLabel getLblHoraInicio() {
		if (lblHoraInicio == null) {
			lblHoraInicio = new JLabel("Hora inicio");
		}
		return lblHoraInicio;
	}
	private JLabel getLblHoraFin() {
		if (lblHoraFin == null) {
			lblHoraFin = new JLabel("Hora fin");
		}
		return lblHoraFin;
	}
	protected void muestraActividades() {
		listaModelo.removeAllElements();
		scrollPane.setBackground(Color.white);
		for (ActividadPlanificada ap : parent.getPrograma().getActividadesPlanificadas()) {
			if (comprobaciones(ap)) {
				listaModelo.addElement(ap);
			}
		}
		contentPanel.add(getBtnRefrescar());
		JList<ActividadPlanificada> lista = new JList<ActividadPlanificada>(listaModelo);
		scrollPane.setViewportView(lista);
		scrollPane.setPreferredSize(new Dimension(395,250));
		contentPanel.add(scrollPane);
		contentPanel.validate();
		contentPanel.repaint();
	}

	private boolean comprobaciones(ActividadPlanificada ap) {
		int dia = ap.getDia();
		int mes = ap.getMes();
		int año = ap.getAño();
		int diaSeleccionado = Integer.parseInt(titulo.split(" ")[1]);
		int mesSeleccionado = mesSeleccionado();
		int añoSeleccionado = añoSeleccionado();
		if (dia == diaSeleccionado && mes == mesSeleccionado && año == añoSeleccionado) {
			return compruebaHoras(ap);
		}
		return false;
	}

	private boolean compruebaHoras(ActividadPlanificada ap) {
		int horaInicio = ap.getHoraInicio();
		int horaFin = ap.getHoraFin();
		int horaInicioSeleccionada = (int) cbHoraInicio.getSelectedItem();
		int horaFinSeleccionada = (int) cbHoraFin.getSelectedItem();
		if (horaInicio > horaInicioSeleccionada && horaInicio < horaFinSeleccionada)
			return true;
		if (horaFin > horaInicioSeleccionada && horaFin < horaFinSeleccionada)
			return true;
		if (horaInicio <= horaInicioSeleccionada && horaFin >= horaFinSeleccionada)
			return true;
		return false;
	}

	private int mesSeleccionado() {
		switch (mes.split(" ")[0]) {
		case "Enero":
			return 1;
		case "Febrero":
			return 2;
		case "Marzo":
			return 3;
		case "Abril":
			return 4;
		case "Mayo":
			return 5;
		case "Junio":
			return 6;
		case "Julio":
			return 7;
		case "Agosto":
			return 8;
		case "Septiembre":
			return 9;
		case "Octubre":
			return 10;
		case "Noviembre":
			return 11;
		case "Diciembre":
			return 12;
		}
		return 0;
	}
	
	private int añoSeleccionado() {
		return Integer.parseInt(mes.split(" ")[1]);
	}
	private JButton getBtnRefrescar() {
		if (btnRefrescar == null) {
			btnRefrescar = new JButton("Refrescar lista");
			btnRefrescar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					muestraActividades();
				}
			});
		}
		return btnRefrescar;
	}
}