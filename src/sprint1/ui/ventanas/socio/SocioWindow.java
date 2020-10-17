package sprint1.ui.ventanas.socio;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sprint1.business.clases.Socio;
import sprint1.ui.ventanas.MainWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
	private JButton btnVerHorario;
	private VerHorariosSocioWindow vhsw;
	
	
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
			pnAcciones.add(getBtnVerActividades());
			pnAcciones.add(getBtnVerHorario());
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
		listaDeActividadesWindow = new ListaDeActividadesWindow(this, socio);
		listaDeActividadesWindow.setModal(true);
		listaDeActividadesWindow.setLocationRelativeTo(this);
		listaDeActividadesWindow.setVisible(true);
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
	private JButton getBtnVerHorario() {
		if (btnVerHorario == null) {
			btnVerHorario = new JButton("Ver horarios");
			btnVerHorario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					abrirHorariosSocio();
				}
			});
		}
		return btnVerHorario;
	}
	private void abrirHorariosSocio() {
		vhsw = new VerHorariosSocioWindow(socio, parent.getPrograma());
		vhsw.setModal(true);
		vhsw.setLocationRelativeTo(this);
		vhsw.setVisible(true);
	}
}