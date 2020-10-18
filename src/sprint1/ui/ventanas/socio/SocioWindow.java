package sprint1.ui.ventanas.socio;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Reserva;
import sprint1.business.clases.Socio;
import sprint1.ui.ventanas.MainWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class SocioWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private MainWindow parent = null;
	private Socio socio;
	private JPanel pnTextos;
	private JLabel lblBienvenida;
	private JLabel lblAcciones;
	private JPanel pnFuncionalidad;
	private JPanel pnLogOut;
	private JPanel pnAcciones;
	private JButton btnLogOut;
	private JButton btnVerActividades;
	private ListaDeActividadesWindow listaDeActividadesWindow = null;
	private JButton btnVerHorariosSocios;
	private JButton btnReservar;
	
	/**
	 * Create the dialog.
	 */
	public SocioWindow(MainWindow mainWindow, Socio socio) {
		setTitle("Centro de Deportes: Acceso como socio");
		this.parent = mainWindow;
		this.socio = socio;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPnTextos(), BorderLayout.NORTH);
		getContentPane().add(getPnFuncionalidad(), BorderLayout.CENTER);
	}

	private JPanel getPnTextos() {
		if (pnTextos == null) {
			pnTextos = new JPanel();
			pnTextos.setBackground(Color.WHITE);
			pnTextos.setLayout(new BorderLayout(0, 0));
			pnTextos.add(getLblBienvenida(), BorderLayout.NORTH);
			pnTextos.add(getLblAcciones());
		}
		return pnTextos;
	}

	private JLabel getLblBienvenida() {
		if (lblBienvenida == null) {
			lblBienvenida = new JLabel("Bienvenido, socio " + socio.getId_cliente());
		}
		return lblBienvenida;
	}

	private JLabel getLblAcciones() {
		if (lblAcciones == null) {
			lblAcciones = new JLabel("Acciones disponibles como socio:");
			lblAcciones.setFont(new Font("Arial", Font.BOLD, 14));
			lblAcciones.setHorizontalAlignment(SwingConstants.CENTER);
			lblAcciones.setBackground(Color.WHITE);
		}
		return lblAcciones;
	}

	private JPanel getPnFuncionalidad() {
		if (pnFuncionalidad == null) {
			pnFuncionalidad = new JPanel();
			pnFuncionalidad.setLayout(new BorderLayout(0, 0));
			pnFuncionalidad.add(getPnLogOut(), BorderLayout.SOUTH);
			pnFuncionalidad.add(getPnAcciones(), BorderLayout.CENTER);
		}
		return pnFuncionalidad;
	}

	private JPanel getPnLogOut() {
		if (pnLogOut == null) {
			pnLogOut = new JPanel();
			pnLogOut.setBackground(new Color(255, 255, 255));
			pnLogOut.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnLogOut.add(getBtnLogOut());
		}
		return pnLogOut;
	}

	private JPanel getPnAcciones() {
		if (pnAcciones == null) {
			pnAcciones = new JPanel();
			pnAcciones.setBackground(new Color(255, 255, 255));
			pnAcciones.add(getButton_1());
			pnAcciones.add(getBtnVerActividades());
			pnAcciones.add(getBtnVerHorariosSocios());
		}
		return pnAcciones;
	}

	private JButton getBtnLogOut() {
		if (btnLogOut == null) {
			btnLogOut = new JButton("Log Out");
			btnLogOut.setMnemonic('O');
			btnLogOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnLogOut.setHorizontalAlignment(SwingConstants.RIGHT);
			btnLogOut.setForeground(Color.WHITE);
			btnLogOut.setBackground(new Color(255, 99, 71));
		}
		return btnLogOut;
	}

	public MainWindow getParent() {
		return parent;
	}

	private void openListaDeActividadesWindow() {
		if (checkIfListWillOpen()) {
			listaDeActividadesWindow = new ListaDeActividadesWindow(this, socio);
			listaDeActividadesWindow.setModal(true);
			listaDeActividadesWindow.setLocationRelativeTo(this);
			listaDeActividadesWindow.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(this, "No tienes ninguna reserva de ninguna actividad");
		}

	}

	public boolean checkIfListWillOpen() {
		List<String> codigosActividades = new ArrayList<String>();
		for (Reserva reserva : getParent().getPrograma().getReservas()) {
			if (reserva.getId_cliente().equals(socio.getId_cliente())) {
				codigosActividades.add(reserva.getCodigo_actividad());
			}
		}
		if (codigosActividades.isEmpty()) {
			return false;
		} else {
			for (String codigo : codigosActividades) {
				for (ActividadPlanificada actividad : getParent().getPrograma().getActividadesPlanificadas()) {
					if (codigo.equals(actividad.getCodigoPlanificada())
							&& getParent().getPrograma().comprobarActividadAntesQueFecha(actividad)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private JButton getBtnVerActividades() {
		if (btnVerActividades == null) {
			btnVerActividades = new JButton("Listado de reservas para ver o cancelar");
			btnVerActividades.setMnemonic('L');
			btnVerActividades.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					openListaDeActividadesWindow();
				}
			});
		}
		return btnVerActividades;
	}
	private JButton getBtnVerHorariosSocios() {
		if (btnVerHorariosSocios == null) {
			btnVerHorariosSocios = new JButton("Ver horarios");
			btnVerHorariosSocios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VerHorariosSocioWindow vosw = new VerHorariosSocioWindow(SocioWindow.this.getParent().getPrograma());
					vosw.setModal(true);
					vosw.setLocationRelativeTo(SocioWindow.this);
					vosw.setVisible(true);
				}
			});
		}
		return btnVerHorariosSocios;
	}
	private JButton getButton_1() {
		if (btnReservar == null) {
			btnReservar = new JButton("Hacer reserva");
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ReservaSocioWindow rsw = new ReservaSocioWindow(SocioWindow.this, socio);
					rsw.setModal(true);
					rsw.setLocationRelativeTo(SocioWindow.this);
					rsw.setVisible(true);
				}
			});
		}
		return btnReservar;
	}
}