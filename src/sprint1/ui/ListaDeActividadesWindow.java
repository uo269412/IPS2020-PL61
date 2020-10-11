package sprint1.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import sprint1.business.clases.Actividad;
import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Reserva;
import sprint1.business.clases.Socio;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListaDeActividadesWindow extends JDialog {

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

	public ListaDeActividadesWindow(SocioWindow parent, Socio socio) {
		setTitle("Lista de actividades");
		this.parent = parent;
		this.socio = socio;
		setBounds(100, 100, 450, 425);
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
		for (Reserva reserva : parent.getParent().getPrograma().getReservas()) {
			if (reserva.getId_cliente().equals(socio.getId_cliente())) {
				codigosActividades.add(reserva.getCodigo_actividad());
			}
		}
		for (String codigo : codigosActividades) {
			for (ActividadPlanificada actividad : parent.getParent().getPrograma().getActividadesPlanificadas()) {
				if (codigo.equals(actividad.getCodigoPlanificada())) {
					modeloActividades.addElement(actividad);
				}
			}
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
			btnCancelarReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					socio.anularReserva(listActividades.getSelectedValue(), getParent().getParent().getPrograma().getReservas());
				}
			});
			btnCancelarReserva.setBackground(new Color(255, 0, 0));
			btnCancelarReserva.setForeground(new Color(255, 255, 255));
		}
		return btnCancelarReserva;
	}

	public SocioWindow getParent() {
		return this.parent;
	}
}
