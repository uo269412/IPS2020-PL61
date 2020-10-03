package sprint1.ui;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import sprint1.business.clases.Actividad;
import sprint1.business.clases.Socio;

import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class SocioWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MainWindow parent = null;
	private JButton btnReturn;
	private JButton btnAnularReserva;
	private Socio socio;
	private JButton btnVerActividades;

	private ListaDeActividadesWindow listaDeActividadesWindow = null;

	/**
	 * Create the dialog.
	 */
	public SocioWindow(MainWindow mainWindow, Socio socio) {
		setTitle("Accediendo como socio..." + socio.getNombre());
		this.socio = socio;
		this.parent = mainWindow;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		getContentPane().add(getBtnAnularReserva());
		getContentPane().add(getBtnVerActividades());
		getContentPane().add(getBtnReturn());
	}

	private JButton getBtnReturn() {
		if (btnReturn == null) {
			btnReturn = new JButton("Return");
			btnReturn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnReturn;
	}

	private JButton getBtnAnularReserva() {
		if (btnAnularReserva == null) {
			btnAnularReserva = new JButton("Anular reserva");
			btnAnularReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String id_actividad;
					do {
						id_actividad = JOptionPane
								.showInputDialog("Introduce el id de la actividad que quieres cancelar");
					} while (parent.getPrograma().encontrarActividad(id_actividad) == null);
					socio.anularReserva(parent.getPrograma().encontrarActividad(id_actividad),
							parent.getPrograma().getReservas());
				}
			});
		}
		return btnAnularReserva;
	}

	private JButton getBtnVerActividades() {
		if (btnVerActividades == null) {
			btnVerActividades = new JButton("Ver actividades");
			btnVerActividades.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					openListaDeActividadesWindow();
				}
			});
		}
		return btnVerActividades;
	}

	private void openListaDeActividadesWindow() {
		listaDeActividadesWindow = new ListaDeActividadesWindow(this, socio);
		listaDeActividadesWindow.setModal(true);
		listaDeActividadesWindow.setLocationRelativeTo(this);
		listaDeActividadesWindow.setVisible(true);
	}

	public MainWindow getParent() {
		return parent;
	}
}
