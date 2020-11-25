package sprint1.ui.ventanas.socio.util;

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
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sprint1.business.clases.Socio;
import sprint1.ui.ventanas.MainWindow;
import sprint1.ui.ventanas.socio.SocioWindow;

public class SeleccionSocioWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblEscogeSocio;
	private JComboBox<Socio> cbSocios;
	private MainWindow parent;
	private SocioWindow socioWindow;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			CbSociosWindow dialog = new CbSociosWindow();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public SeleccionSocioWindow(MainWindow parent) {
		this.parent = parent;
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 346, 186);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getLblEscogeSocio(), BorderLayout.NORTH);
		contentPanel.add(getCbSocios(), BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.WHITE);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						openSocioWindow((Socio) cbSocios.getSelectedItem()); 
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private JLabel getLblEscogeSocio() {
		if (lblEscogeSocio == null) {
			lblEscogeSocio = new JLabel("Escoge socio:");
			lblEscogeSocio.setFont(new Font("Tahoma", Font.BOLD, 18));
		}
		return lblEscogeSocio;
	}
	private JComboBox<Socio> getCbSocios() {
		if (cbSocios == null) {
			cbSocios = new JComboBox<Socio>(new DefaultComboBoxModel<Socio>(parent.getPrograma().getSocios().toArray(new Socio[0])));
			cbSocios.setFont(new Font("Tahoma", Font.PLAIN, 16));
			cbSocios.setBackground(Color.WHITE);
		}
		return cbSocios;
	}
	private void openSocioWindow(Socio socio) {
		socioWindow = new SocioWindow(parent, socio);
		socioWindow.setModal(true);
		socioWindow.setLocationRelativeTo(this);
		socioWindow.setVisible(true);
	}
}
