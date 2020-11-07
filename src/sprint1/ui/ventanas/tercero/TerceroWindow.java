package sprint1.ui.ventanas.tercero;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Programa;
import sprint1.business.clases.Reserva;
import sprint1.business.clases.Socio;
import sprint1.business.clases.Tercero;
import sprint1.ui.ventanas.MainWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TerceroWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private MainWindow parent = null;
	private JPanel pnTextos;
	private JLabel lblBienvenida;
	private JLabel lblAcciones;
	private JPanel pnFuncionalidad;
	private JPanel pnLogOut;
	private JPanel pnAcciones;
	private JButton btnLogOut;
	private JButton btnNewButton;
	
	Tercero t;
	
	/**
	 * Create the dialog.
	 */
	public TerceroWindow(MainWindow mainWindow, Tercero t) {
		
		setTitle("Centro de Deportes: Acceso como tercero");
		this.parent = mainWindow;
		this.t = t;
		
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
			lblBienvenida = new JLabel("Bienvenido, cliente " + t.getNombre());
			lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
			lblBienvenida.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return lblBienvenida;
	}

	private JLabel getLblAcciones() {
		if (lblAcciones == null) {
			lblAcciones = new JLabel("Acciones disponibles como tercero:");
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
			pnAcciones.add(getBtnNewButton());
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
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Alquilar una instalaci\u00F3n");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CalendarioTercero ct = new CalendarioTercero(TerceroWindow.this, t);
					ct.setLocationRelativeTo(TerceroWindow.this);
					ct.setModal(true);
					ct.setVisible(true);
				}
			});
		}
		return btnNewButton;
	}
}