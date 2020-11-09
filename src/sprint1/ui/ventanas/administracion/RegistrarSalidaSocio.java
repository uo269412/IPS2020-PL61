package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sprint1.business.clases.Alquiler;
import sprint1.business.clases.Instalacion;
import sprint1.business.clases.Programa;
import sprint1.business.clases.Registro;
import sprint1.business.clases.Socio;
import javax.swing.JTextField;

public class RegistrarSalidaSocio extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AdminWindow parent = null;
	private Socio socio = null;
	private DefaultComboBoxModel<Socio> modeloSocios = new DefaultComboBoxModel<Socio>();
	private JPanel pnBotones;
	private JButton btnVolver;
	private JButton btnReservar;
	private JPanel pnSocio;
	private JPanel panel;
	private JPanel pnSeleccionSocio;
	private JLabel lblSocio;
	private JComboBox<Socio> comboBox;
	private JPanel pnInfo;
	private JLabel lblInfoAlquiler;
	private JTextField textField;

	private Instalacion seleccionada;

	public RegistrarSalidaSocio(AdminWindow adminWindow) {
		setTitle("Administraci\u00F3n: Registrando salida del socio");
		this.parent = adminWindow;
		setBounds(100, 100, 620, 169);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getPnBotones(), BorderLayout.SOUTH);
		getContentPane().add(getPnSocio(), BorderLayout.NORTH);
		getContentPane().add(getPanel(), BorderLayout.CENTER);
		cargarSocio();
	}

//	private void cargarSocio() {
//		int[] fecha = getPrograma().obtenerHoraDiaMesAño();
//		List<Socio> socios = new ArrayList<>();
//		for (Alquiler alquiler : getPrograma().getAlquileresNoCancelados(fecha[0], fecha[1], fecha[2], fecha[3])) {
//			if (alquiler != null) {
//				if (getPrograma().encontrarRegistro(alquiler.getId_alquiler()) != null) {
//					Registro seleccionado = getPrograma().encontrarRegistro(alquiler.getId_alquiler());
//					if (seleccionado.isSocioPresentado() && (seleccionado.getHora_salida() == 0)) {
//						for (Socio socio : getPrograma().getSocios()) {
//							if (socio.getId_cliente().equals(alquiler.getId_cliente())) {
//								socios.add(socio);
//							}
//						}
//					}
//				}
//			}
//		}
//		Collections.sort(socios);
//		for (Socio socio : socios) {
//			modeloSocios.addElement(socio);
//		}
//	}

	private void cargarSocio() {
		for (Socio socio : getPrograma().getSocios()) {
			Alquiler alquiler = getPrograma().getAlquilerSocioAhoraNoCancelado(socio);
			if (alquiler != null) {
				if (getPrograma().encontrarRegistro(alquiler.getId_alquiler()) != null) {
					Registro seleccionado = getPrograma().encontrarRegistro(alquiler.getId_alquiler());
					if ((seleccionado.isSocioPresentado()) && (seleccionado.getHora_salida() == 0)) {
						modeloSocios.addElement(socio);
					}
				}
			}
		}
	}

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
			pnBotones.add(getBtnVolver());
			pnBotones.add(getBtnReservar());
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

	private JButton getBtnReservar() {
		if (btnReservar == null) {
			btnReservar = new JButton("Registrar salida");
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int yesNo = JOptionPane.showConfirmDialog(null,
							"¿Seguro de que quieres registrar la salida del cliente "
									+ ((Socio) comboBox.getSelectedItem()).getNombre() + " ?");
					if (yesNo == JOptionPane.YES_OPTION) {
						Alquiler alquiler = getPrograma().getAlquilerSocioAhora((Socio) comboBox.getSelectedItem());
						Registro registro = getPrograma().encontrarRegistro(alquiler.getId_alquiler());
						getPrograma().registrarHoraSalidaSocio(registro);
						JOptionPane.showMessageDialog(null,
								"Se ha registrado la salida de la instalación correctamente");
						generarRecibo();
						dispose();

					}
				}
			});
			btnReservar.setBackground(new Color(0, 128, 0));
			btnReservar.setForeground(new Color(255, 255, 255));
		}
		return btnReservar;
	}

	public AdminWindow getParent() {
		return this.parent;
	}

	public Programa getPrograma() {
		return getParent().getParent().getPrograma();
	}

	private JPanel getPnSocio() {
		if (pnSocio == null) {
			pnSocio = new JPanel();
			pnSocio.setLayout(new BorderLayout(0, 0));
		}
		return pnSocio;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getPnSeleccionSocio(), BorderLayout.CENTER);
			panel.add(getPnInfo(), BorderLayout.SOUTH);
		}
		return panel;
	}

	private JPanel getPnSeleccionSocio() {
		if (pnSeleccionSocio == null) {
			pnSeleccionSocio = new JPanel();
			pnSeleccionSocio.setLayout(new BorderLayout(0, 0));
			pnSeleccionSocio.add(getLblNewLabel_1(), BorderLayout.NORTH);
			pnSeleccionSocio.add(getComboBox(), BorderLayout.CENTER);
		}
		return pnSeleccionSocio;
	}

	private JLabel getLblNewLabel_1() {
		if (lblSocio == null) {
			lblSocio = new JLabel("Selecci\u00F3n de socio:   ");
			lblSocio.setHorizontalAlignment(SwingConstants.LEFT);
			lblSocio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblSocio;
	}

	private JComboBox<Socio> getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox<Socio>(modeloSocios);
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Instalacion instalacion = getPrograma().encontrarInstalacion((getPrograma()
							.getAlquilerSocioAhora((Socio) comboBox.getSelectedItem()).getId_instalacion()));
					textField.setText(getPrograma().getAlquilerSocioAhora((Socio) comboBox.getSelectedItem()).toString()
							+ " en la instalación " + instalacion.toString());

				}
			});
		}
		return comboBox;
	}

	private JPanel getPnInfo() {
		if (pnInfo == null) {
			pnInfo = new JPanel();
			pnInfo.setLayout(new BorderLayout(0, 0));
			pnInfo.add(getLblInfoAlquiler(), BorderLayout.NORTH);
			pnInfo.add(getTextField());
		}
		return pnInfo;
	}

	private JLabel getLblInfoAlquiler() {
		if (lblInfoAlquiler == null) {
			lblInfoAlquiler = new JLabel("Informaci\u00F3n del alquiler: ");
			lblInfoAlquiler.setHorizontalAlignment(SwingConstants.LEFT);
			lblInfoAlquiler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblInfoAlquiler;
	}

	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setEditable(false);
			textField.setColumns(10);
		}
		return textField;
	}
	
	private void generarRecibo() {
		Socio s = (Socio)comboBox.getSelectedItem();
		Alquiler alquiler = getPrograma().getAlquilerSocioAhoraNoCancelado(s);
		Registro registro = getPrograma().encontrarRegistro(alquiler.getId_alquiler());
		double aPagar = (registro.getHora_salida() - registro.getHora_entrada()) * getPrograma().encontrarInstalacion(alquiler.getId_instalacion()).getPrecioHora();
	
		StringBuilder sb = new StringBuilder();
		sb.append("-----RECIBO DE ALQUILER----\n");
		sb.append("Socio: " + s.getNombre() + " " + s.getApellido() + "\n");
		sb.append("Instalación: " + getPrograma().encontrarInstalacion(alquiler.getId_instalacion()).getNombre() + "\n");
		sb.append("\t hora entrada: " + registro.getHora_entrada() + "\n");
		sb.append("\t hora salida: " + registro.getHora_salida() + "\n");
		sb.append("\t\t\t\timporte a pagar: " + aPagar);
		
		System.out.println(sb.toString());
	}
}
