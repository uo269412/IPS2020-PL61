package sprint1.ui.ventanas.socio.acciones;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import sprint1.business.Programa;
import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;
import sprint1.business.dominio.centroDeportes.reservas.Reserva;
import sprint1.business.dominio.clientes.Socio;
import sprint1.ui.ventanas.socio.SocioWindow;
import java.awt.Toolkit;

public class ListaDeReservasWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SocioWindow parent = null;
	private Socio socio = null;
	private JScrollPane scpActividades;
	private JList<ActividadPlanificada> listActividades;
	private DefaultListModel<ActividadPlanificada> modeloActividades = null;
	private JPanel pnBotones;
	private JButton btnVolver;
	private JButton btnCancelarReserva;

	public ListaDeReservasWindow(SocioWindow parent, Socio socio) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ListaDeReservasWindow.class.getResource("/sprint1/ui/resources/titulo.png")));
		setTitle("Centro de deportes: Listado de reservas");
		this.parent = parent;
		this.socio = socio;
		setBounds(100, 100, 681, 588);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getScpActividades(), BorderLayout.CENTER);
		getContentPane().add(getPnBotones(), BorderLayout.SOUTH);
		cargarActividades();
	}

	private JScrollPane getScpActividades() {
		if (scpActividades == null) {
			scpActividades = new JScrollPane();
			scpActividades.setViewportView(getListActividades());
		}
		return scpActividades;
	}

	private JList<ActividadPlanificada> getListActividades() {
		if (listActividades == null) {
			modeloActividades = new DefaultListModel<ActividadPlanificada>();
			listActividades = new JList<ActividadPlanificada>(modeloActividades);
			listActividades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return listActividades;
	}

	// CAMBIAR A RESERVA
	private void cargarActividades() {
		modeloActividades.clear();
		List<String> codigosActividades = new ArrayList<String>();
		for (Reserva reserva : getPrograma().getReservas()) {
			if (reserva.getId_cliente().equals(socio.getId_cliente())) {
				codigosActividades.add(reserva.getCodigo_actividad());
			}
		}
		if (!codigosActividades.isEmpty()) {
			for (String codigo : codigosActividades) {
				for (ActividadPlanificada actividad : getPrograma().getActividadesPlanificadas()) {
					if (codigo.equals(actividad.getCodigoPlanificada())
							&& getPrograma().comprobarActividadAntesQueFecha(actividad)) {
						modeloActividades.addElement(actividad);
					}
				}
			}
		}
		if (modeloActividades.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No queda ninguna reserva que ver ni anular para este socio");
			dispose();
		}
	}

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
			pnBotones.add(getBtnCancelarReserva());
			pnBotones.add(getBtnVolver());
		}
		return pnBotones;
	}

	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.setMnemonic('V');
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnVolver.setForeground(Color.WHITE);
			btnVolver.setBackground(new Color(30, 144, 255));
			btnVolver.setActionCommand("Cancel");
		}
		return btnVolver;
	}

	private JButton getBtnCancelarReserva() {
		if (btnCancelarReserva == null) {
			btnCancelarReserva = new JButton("Cancelar reserva");
			btnCancelarReserva.setMnemonic('C');
			btnCancelarReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (listActividades.isSelectionEmpty()) {
						JOptionPane.showMessageDialog(null, "Por favor, selecciona una actividad");
					} else {
						int yesNo = JOptionPane.showConfirmDialog(null,
								"¿Seguro de que quiere cancelar su reserva en la actividad "
										+ listActividades.getSelectedValue().getCodigoActividad() + " ?");
						if (yesNo == JOptionPane.YES_OPTION) {
							getPrograma().anularReserva(socio, listActividades.getSelectedValue());
							JOptionPane.showMessageDialog(null, "Se ha borrado la reserva correctamente");
							cargarActividades();
						}
					}
				}
			});
			btnCancelarReserva.setBackground(new Color(255, 0, 0));
			btnCancelarReserva.setForeground(new Color(255, 255, 255));
		}
		return btnCancelarReserva;
	}

	public void eliminarReservaLista() {
		getPrograma().eliminarReserva(listActividades.getSelectedValue().getCodigoPlanificada());
	}

	public SocioWindow getParent() {
		return this.parent;
	}

	public Programa getPrograma() {
		return getParent().getParent().getPrograma();
	}
}
