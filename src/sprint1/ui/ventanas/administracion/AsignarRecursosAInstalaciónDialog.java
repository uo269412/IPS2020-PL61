package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sprint1.business.clases.Instalacion;
import sprint1.business.clases.Programa;
import sprint1.business.clases.Recurso;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AsignarRecursosAInstalaciónDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblInstalacion;
	private JComboBox<Instalacion> comboBox;
	private JLabel lblNombreRecurso;
	private JTextField txtRecurso;
	private JLabel lblUnidades;
	private JTextField txtUnits;
	private JPanel pnButtons;
	private JButton btnAñadir;
	private JButton btnCancelar;
	
	DefaultComboBoxModel<Instalacion> dcbm;
	Programa p;
	
	/**
	 * Create the dialog.
	 */
	public AsignarRecursosAInstalaciónDialog(Programa p) {
		this.p = p;
		dcbm = new DefaultComboBoxModel<>();
		rellenarComboBox();
		setTitle("AdminWindow: A\u00F1adir recurso a instalaci\u00F3n");
		setBounds(100, 100, 367, 271);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0,1,0,0));
		contentPanel.add(getLblInstalacion());
		contentPanel.add(getComboBox());
		contentPanel.add(getLblNombreRecurso());
		contentPanel.add(getTxtRecurso());
		contentPanel.add(getLabel_1());
		contentPanel.add(getTxtUnits());
		contentPanel.add(getPnButtons());
	}

	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalaci\u00F3n");
			lblInstalacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lblInstalacion;
	}
	private JComboBox<Instalacion> getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox<>();
		}
		return comboBox;
	}
	private JLabel getLblNombreRecurso() {
		if (lblNombreRecurso == null) {
			lblNombreRecurso = new JLabel("Nombre del recurso:");
			lblNombreRecurso.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lblNombreRecurso;
	}
	private JTextField getTxtRecurso() {
		if (txtRecurso == null) {
			txtRecurso = new JTextField();
			txtRecurso.setColumns(10);
		}
		return txtRecurso;
	}
	private JLabel getLabel_1() {
		if (lblUnidades == null) {
			lblUnidades = new JLabel("Unidades");
			lblUnidades.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lblUnidades;
	}
	private JTextField getTxtUnits() {
		if (txtUnits == null) {
			txtUnits = new JTextField();
			txtUnits.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					txtUnits.setBackground(Color.WHITE);
					txtUnits.setForeground(Color.BLACK);
				}
			});
			txtUnits.setColumns(10);
		}
		return txtUnits;
	}
	private JPanel getPnButtons() {
		if (pnButtons == null) {
			pnButtons = new JPanel();
			pnButtons.add(getBtnAñadir());
			pnButtons.add(getBtnCancelar());
		}
		return pnButtons;
	}
	private JButton getBtnAñadir() {
		if (btnAñadir == null) {
			btnAñadir = new JButton("A\u00F1adir");
			btnAñadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(checkUnits()) {
						Recurso r = new Recurso(txtRecurso.getText(), ((Instalacion)comboBox.getSelectedItem()).getCodigo(), Integer.parseInt(txtUnits.getText()));
						try {
							p.insertarRecurso(r);
							JOptionPane.showMessageDialog(AsignarRecursosAInstalaciónDialog.this, "El recurso se ha añadido "
									+ "correctamente");
							dispose();
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(AsignarRecursosAInstalaciónDialog.this, "Ha ocurrido un error"
									+ " tratando de añadir el recurso");
						}
					} else {
						txtUnits.setBackground(Color.RED);
						txtUnits.setForeground(Color.WHITE);
					}
				}
			});
			btnAñadir.setBackground(new Color(60, 179, 113));
			btnAñadir.setForeground(new Color(255, 255, 255));
		}
		return btnAñadir;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnCancelar.setForeground(new Color(255, 255, 255));
			btnCancelar.setBackground(new Color(255, 99, 71));
		}
		return btnCancelar;
	}
	
	private boolean checkUnits() {
		try {
			Integer.parseInt(txtUnits.getText());
		} catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	private void rellenarComboBox() {
		for(Instalacion i: p.getInstalacionesDisponibles()) {
			dcbm.addElement(i);
		}
	}
}
