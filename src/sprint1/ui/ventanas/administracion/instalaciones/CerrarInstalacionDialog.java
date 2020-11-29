package sprint1.ui.ventanas.administracion.instalaciones;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import sprint1.business.Programa;
import sprint1.business.dominio.centroDeportes.alquileres.Alquiler;
import sprint1.business.dominio.centroDeportes.instalaciones.Instalacion;
import sprint1.business.dominio.clientes.Cliente;
import sprint1.business.dominio.clientes.Socio;
import sprint1.business.dominio.clientes.Tercero;
import sprint1.ui.ventanas.administracion.AdminWindow;
import sprint1.ui.ventanas.administracion.estado.mostrar.MostrarAlquileresDeClienteDialog;
import java.awt.Toolkit;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(CerrarInstalacionDialog.class.getResource("/sprint1/ui/resources/titulo.png")));
		setTitle("Centro de deporte: Cerrando instalaciones");
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
						printClientesAfectados();
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
			listAfectados.addMouseListener(new MouseAdapter() {
			    public void mouseClicked(MouseEvent evt) {
			        @SuppressWarnings("unchecked")
					JList<Cliente> list = (JList<Cliente>)evt.getSource();
			        if (evt.getClickCount() == 2) {

			            // Double-click detected
			            int c = list.getSelectedIndex();
			            Cliente client = clientesAfectados.get(c);
			            Instalacion i = (Instalacion)comboBox.getSelectedItem();
			            MostrarAlquileresDeClienteDialog madcd = new MostrarAlquileresDeClienteDialog(client, i, p);
			            madcd.setLocationRelativeTo(list);
			            madcd.setModal(true);
			            madcd.setVisible(true);
			        }
			    }
			});
		}
		return listAfectados;
	}
	
	private void llenarModeloClientesAfectados() {
		clientesAfectados.clear();
		try {
			for(Cliente c: p.clientesAfectadosPorCierre((Instalacion)comboBox.getSelectedItem())) {
				clientesAfectados.add(clientesAfectados.size(), c);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Ha ocurrido un problema consultando los clientes en la base de datos");
		}
	}
	
	private void printClientesAfectados() {
		StringBuilder sb = new StringBuilder();
		sb.append("-------CLIENTES AFECTADOS POR EL CIERRE DE LA INSTALACIÓN " + ((Instalacion)comboBox.getSelectedItem()).getNombre() + "-------\n");
		try {
			for(Cliente c: p.clientesAfectadosPorCierre((Instalacion)comboBox.getSelectedItem())) {
				LocalDate diaCierre = LocalDate.now();
				Date date = new Date();
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(date);
				if(c instanceof Tercero) {
					sb.append("Tercero: " + c.getId_cliente() + " Nombre: ");
					sb.append(((Tercero)c).getNombre() + "\n");
					List<Alquiler> alquileresTercero = p.getAlquileresQueHaHechoClienteEnInstalacionAPartirDe((Tercero)c,
							(Instalacion)comboBox.getSelectedItem(),
							diaCierre.getDayOfMonth(),
							diaCierre.getMonthValue(),
							diaCierre.getYear(),
							calendar.get(Calendar.HOUR_OF_DAY));
					
					for(Alquiler a: alquileresTercero) {
						sb.append("\t-Alquiler:" + a.getId_alquiler() + " Fecha: " + a.getDia() + "/" + a.getMes() + "/" + a.getAño() + " Hora: " + a.getHoraInicio());
					}
				} else if(c instanceof Socio) {
					sb.append("Socio: " + c.getId_cliente() + " Nombre: ");
					sb.append(((Socio)c).getNombre() + "\n");
					List<Alquiler> alquileresSocio = p.getAlquileresQueHaHechoClienteEnInstalacionAPartirDe((Socio)c,
							(Instalacion)comboBox.getSelectedItem(),
							diaCierre.getDayOfMonth(),
							diaCierre.getMonthValue(),
							diaCierre.getYear(),
							calendar.get(Calendar.HOUR_OF_DAY));
					
					for(Alquiler a: alquileresSocio) {
						sb.append("\t-Alquiler:" + a.getId_alquiler() + " Fecha: " + a.getDia() + "/" + a.getMes() + "/" + a.getAño() + " Hora: " + a.getHoraInicio());
					}
				}
				
			}
			
			System.out.println(sb.toString());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Ha ocurrido un error accediendo a los clientes de la Base de Datos");
		}
	}
}
