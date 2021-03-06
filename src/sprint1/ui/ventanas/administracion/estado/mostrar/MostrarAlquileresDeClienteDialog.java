package sprint1.ui.ventanas.administracion.estado.mostrar;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDateTime;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sprint1.business.Programa;
import sprint1.business.dominio.centroDeportes.alquileres.Alquiler;
import sprint1.business.dominio.centroDeportes.instalaciones.Instalacion;
import sprint1.business.dominio.clientes.Cliente;
import sprint1.business.dominio.clientes.Socio;
import sprint1.business.dominio.clientes.Tercero;
import java.awt.Toolkit;

public class MostrarAlquileresDeClienteDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblCliente;
	private JList<Alquiler> list;
	
	private Cliente c;
	private Programa p;
	private Instalacion i;
	/**
	 * Create the dialog.
	 */
	public MostrarAlquileresDeClienteDialog(Cliente c, Instalacion i, Programa p) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MostrarAlquileresDeClienteDialog.class.getResource("/sprint1/ui/resources/titulo.png")));
		this.c = c;
		this.i = i;
		this.p = p;
		setTitle("Centro de deporte: Alquileres del cliente");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getLblCliente(), BorderLayout.NORTH);
		getContentPane().add(getList(), BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	}

	private JLabel getLblCliente() {
		if (lblCliente == null) {
			if(c instanceof Tercero) {
				lblCliente = new JLabel("Alquileres de tercero " + ((Tercero)c).getNombre() + ":");
			} else if(c instanceof Socio) {
				lblCliente = new JLabel("Alquileres de socio " + ((Socio)c).getNombre() + ":");
			}
			
			lblCliente.setFont(new Font("Tahoma", Font.BOLD, 18));
		}
		return lblCliente;
	}
	private JList<Alquiler> getList() {
		if (list == null) {
			int dia = LocalDateTime.now().getDayOfMonth();
			int mes = LocalDateTime.now().getMonthValue();
			int a�o = LocalDateTime.now().getYear();
			int hora = LocalDateTime.now().getHour();
			DefaultListModel<Alquiler> lm = new DefaultListModel<>();
			for(Alquiler a: p.getAlquileresQueHaHechoClienteEnInstalacionAPartirDe(c, i, dia, mes, a�o, hora)) {
				lm.addElement(a);
			}
			list = new JList<>(lm);
		}
		return list;
	}
}
