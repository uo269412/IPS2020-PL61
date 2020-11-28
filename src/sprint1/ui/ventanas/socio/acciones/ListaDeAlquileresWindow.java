package sprint1.ui.ventanas.socio.acciones;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import sprint1.business.Programa;
import sprint1.business.dominio.centroDeportes.alquileres.Alquiler;
import sprint1.business.dominio.clientes.Socio;
import sprint1.ui.ventanas.socio.SocioWindow;
import java.awt.Toolkit;

public class ListaDeAlquileresWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SocioWindow parent = null;
	private Socio socio = null;
	private JScrollPane scpAlquileres;
	private JList<Alquiler> listAlquileres;
	private DefaultListModel<Alquiler> modeloAlquileres = null;
	private JPanel pnBotones;
	private JButton btnVolver;
	private JButton btnCancelarAlquiler;

	public ListaDeAlquileresWindow(SocioWindow parent, Socio socio) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ListaDeAlquileresWindow.class.getResource("/sprint1/ui/resources/titulo.png")));
		setTitle("Centro de deportes: Listado de alquileres");
		this.parent = parent;
		this.socio = socio;
		setBounds(100, 100, 681, 588);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getScpAlquileres(), BorderLayout.CENTER);
		getContentPane().add(getPnBotones(), BorderLayout.SOUTH);
		cargarAlquileres();
	}

	private JScrollPane getScpAlquileres() {
		if (scpAlquileres == null) {
			scpAlquileres = new JScrollPane();
			scpAlquileres.setViewportView(getListAlquileres());
		}
		return scpAlquileres;
	}

	private JList<Alquiler> getListAlquileres() {
		if (listAlquileres == null) {
			modeloAlquileres = new DefaultListModel<Alquiler>();
			listAlquileres = new JList<Alquiler>(modeloAlquileres);
			listAlquileres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return listAlquileres;
	}

	public void cargarAlquileres() {
		modeloAlquileres.clear();
		for (Alquiler alquiler : getParent().getParent().getPrograma().getAlquileres()) {
			if (alquiler.getId_cliente().equals(socio.getId_cliente())) {
				if (getParent().getParent().getPrograma().comprobarAlquilerAPartirDeHoy(alquiler)) {
					modeloAlquileres.addElement(alquiler);
				}
			}
		}
		if (modeloAlquileres.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No queda ningun alquiler que ver ni anular para este socio");
			dispose();
		}
	}

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
			pnBotones.add(getBtnCancelarAlquiler());
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

	private JButton getBtnCancelarAlquiler() {
		if (btnCancelarAlquiler == null) {
			btnCancelarAlquiler = new JButton("Cancelar alquiler");
			btnCancelarAlquiler.setMnemonic('C');
			btnCancelarAlquiler.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (listAlquileres.isSelectionEmpty()) {
						JOptionPane.showMessageDialog(null, "Por favor, selecciona una actividad");
					} else {
						int yesNo = JOptionPane.showConfirmDialog(null,
								"¿Seguro de que quiere cancelar su alquiler en la instalacion "
										+ listAlquileres.getSelectedValue().getId_instalacion() + " ?");
						if (yesNo == JOptionPane.YES_OPTION) {
							getPrograma().anularAlquiler(listAlquileres.getSelectedValue());
							JOptionPane.showMessageDialog(null, "Se ha borrado el alquiler correctamente");
							cargarAlquileres();
						}
					}
				}
			});
			btnCancelarAlquiler.setBackground(new Color(255, 0, 0));
			btnCancelarAlquiler.setForeground(new Color(255, 255, 255));
		}
		return btnCancelarAlquiler;
	}

	public SocioWindow getParent() {
		return this.parent;
	}

	public Programa getPrograma() {
		return getParent().getParent().getPrograma();
	}
}
