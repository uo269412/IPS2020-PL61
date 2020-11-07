package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sprint1.business.clases.Actividad;
import sprint1.business.clases.Alquiler;
import sprint1.business.clases.Cliente;
import sprint1.business.clases.Instalacion;
import sprint1.business.clases.Programa;
import sprint1.business.clases.Tercero;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CerrarInstalacionDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Programa p;

	private final JPanel contentPanel = new JPanel();
	private JComboBox<Instalacion> comboBox;
	private JScrollPane scrollPane;
	private JButton btnCerrar;
	private JList<Cliente> listAfectados;

	private DefaultListModel<Cliente> clientesAfectados;
	/**
	 * Create the dialog.
	 */
	public CerrarInstalacionDialog(AdminWindow parent) {
		//crear programa
		this.p = parent.getParent().getPrograma();
		this.clientesAfectados = new DefaultListModel<>();
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getComboBox(), BorderLayout.NORTH);
		contentPanel.add(getScrollPane(), BorderLayout.CENTER);
		contentPanel.add(getBtnCerrar(), BorderLayout.SOUTH);
	}
	private JComboBox<Instalacion> getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox<>();
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					llenarModeloClientesAfectados();
				}
			});
			comboBox.setModel(new DefaultComboBoxModel<Instalacion>(p.getInstalacionesDisponibles().toArray(new Instalacion[p.getInstalacionesDisponibles().size()])));
		}
		return comboBox;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.add(getListAfectados());
			scrollPane.setViewportView(getListAfectados());
		}
		return scrollPane;
	}
	private JButton getBtnCerrar() {
		if (btnCerrar == null) {
			btnCerrar = new JButton("Cerrar Instalaci\u00F3n");
			btnCerrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Instalacion i = (Instalacion)comboBox.getSelectedItem();
					i.setEstado(Instalacion.CERRADA);
					try {
						printSociosAfectados();
						p.eliminarAlquileresNoDisponibles();
						p.updateInstalacion(i);
						dispose();
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(CerrarInstalacionDialog.this, "Ha habido un error actualizando los alquileres de la base de datos");
					}
				}
			});
		}
		return btnCerrar;
	}
	private JList<Cliente> getListAfectados() {
		if (listAfectados == null) {
			listAfectados = new JList<Cliente>();
			listAfectados.setModel(clientesAfectados);
			llenarModeloClientesAfectados();
		}
		return listAfectados;
	}
	
	private void llenarModeloClientesAfectados() {
		try {
			for(Cliente c: p.clientesAfectadosPorCierre((Instalacion)comboBox.getSelectedItem())) {
				clientesAfectados.add(clientesAfectados.size(), c);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Ha ocurrido un problema consultando los clientes en la base de datos");
		}
	}
	
	private void printSociosAfectados() {
		StringBuilder sb = new StringBuilder();
		sb.append("-------CLIENTES AFECTADOS POR EL CIERRE DE LA INSTALACI�N " + ((Instalacion)comboBox.getSelectedItem()).getNombre() + "-------\n");
		try {
			for(Cliente c: p.clientesAfectadosPorCierre((Instalacion)comboBox.getSelectedItem())) {
				LocalDate diaCierre = LocalDate.now();
				Date date = new Date();
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(date);
				sb.append("Tercero: " + c.getId_cliente() + " Nombre: ");
				sb.append(((Tercero)c).getNombre() + "\n");
				List<Alquiler> alquileresTercero = p.getAlquileresQueHaHechoTerceroEnInstalacionAPartirDe((Tercero)c,
						(Instalacion)comboBox.getSelectedItem(),
						diaCierre.getDayOfMonth(),
						diaCierre.getMonthValue(),
						diaCierre.getYear(),
						calendar.get(Calendar.HOUR_OF_DAY));
				
				for(Alquiler a: alquileresTercero) {
					sb.append("\t-Alquiler:" + a.getId_alquiler() + " Fecha: " + a.getDia() + "/" + a.getMes() + "/" + a.getA�o() + " Hora: " + a.getHoraInicio());
				}
			}
			
			System.out.println(sb.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "Ha ocurrido accediendo a los clientes de la Base de Datos");
		}
	}
}