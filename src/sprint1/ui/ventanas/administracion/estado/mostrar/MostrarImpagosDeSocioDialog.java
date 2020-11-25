package sprint1.ui.ventanas.administracion.estado.mostrar;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sprint1.business.Programa;
import sprint1.business.dominio.centroDeportes.alquileres.Alquiler;
import sprint1.business.dominio.clientes.Socio;

public class MostrarImpagosDeSocioDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblCliente;
	private JList<Alquiler> list;
	
	private Socio s;
	private Programa p;
	private JLabel lblCuota;
	/**
	 * Create the dialog.
	 */
	public MostrarImpagosDeSocioDialog(Socio s, Programa p) {
		this.s = s;
		this.p = p;
		setTitle("Listar socios con impagos: Impagos de socio");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getLblCliente(), BorderLayout.NORTH);
		getContentPane().add(getList(), BorderLayout.CENTER);
		getContentPane().add(getLblCuota(), BorderLayout.SOUTH);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	}

	private JLabel getLblCliente() {
		if (lblCliente == null) {
			lblCliente = new JLabel("Impagos de socio " + s.getNombre() + ":");
			lblCliente.setFont(new Font("Tahoma", Font.BOLD, 18));
		}
		return lblCliente;
	}
	private JList<Alquiler> getList() {
		if (list == null) {
			DefaultListModel<Alquiler> lm = new DefaultListModel<>();
			try {
				for(Alquiler a: p.alquileresNoPagadosSocio(s)) {
					lm.addElement(a);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "Ha ocurrido un error recuperando la información del socio");
			}
			list = new JList<>(lm);
		}
		return list;
	}
	private JLabel getLblCuota() {
		if (lblCuota == null) {
			try {
				lblCuota = new JLabel("Deuda total en la cuota: " + p.getCuotaSocio(s));
				lblCuota.setFont(new Font("Tahoma", Font.BOLD, 18));
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "Ha ocurrido un error recuperando la información del socio");
			}
			
		}
		return lblCuota;
	}
}
