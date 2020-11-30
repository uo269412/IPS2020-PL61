package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminCancelaActividad extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel pnNorth;
	private JLabel lblActividad;
	private JPanel pnComboBox;
	private JComboBox cbActividades;
	private JLabel lnlPeriodoDeActividad;
	private JPanel pnPeriodos;
	private JButton btnDiaConcreto;
	
	AdminWindow parent;

	/**
	 * Create the dialog.
	 */
	public AdminCancelaActividad(AdminWindow parent) {
		this.parent = parent;
		setTitle("Cancelar Actividad");
		setBounds(100, 100, 561, 256);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getPnNorth(), BorderLayout.NORTH);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.WHITE);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnCancelarActividad = new JButton("OK");
				btnCancelarActividad.setActionCommand("OK");
				buttonPane.add(btnCancelarActividad);
				getRootPane().setDefaultButton(btnCancelarActividad);
			}
			{
				JButton btnAtras = new JButton("Atras");
				btnAtras.setActionCommand("Cancel");
				buttonPane.add(btnAtras);
			}
		}
	}

	private JPanel getPnNorth() {
		if (pnNorth == null) {
			pnNorth = new JPanel();
			pnNorth.setBackground(Color.WHITE);
			pnNorth.setLayout(new GridLayout(0, 1, 0, 4));
			pnNorth.add(getLblActividad());
			pnNorth.add(getPnComboBox());
			pnNorth.add(getLnlPeriodoDeActividad());
			pnNorth.add(getPnPeriodos());
		}
		return pnNorth;
	}
	private JLabel getLblActividad() {
		if (lblActividad == null) {
			lblActividad = new JLabel("Actividad:");
			lblActividad.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblActividad.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return lblActividad;
	}
	private JPanel getPnComboBox() {
		if (pnComboBox == null) {
			pnComboBox = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnComboBox.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnComboBox.setBackground(Color.WHITE);
			pnComboBox.add(getCbActividades());
		}
		return pnComboBox;
	}
	private JComboBox getCbActividades() {
		if (cbActividades == null) {
			cbActividades = new JComboBox();
		}
		return cbActividades;
	}
	private JLabel getLnlPeriodoDeActividad() {
		if (lnlPeriodoDeActividad == null) {
			lnlPeriodoDeActividad = new JLabel("Periodo de actividad:");
			lnlPeriodoDeActividad.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lnlPeriodoDeActividad;
	}
	private JPanel getPnPeriodos() {
		if (pnPeriodos == null) {
			pnPeriodos = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnPeriodos.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnPeriodos.setBackground(Color.WHITE);
			pnPeriodos.add(getBtnDiaConcreto());
		}
		return pnPeriodos;
	}
	private JButton getBtnDiaConcreto() {
		if (btnDiaConcreto == null) {
			btnDiaConcreto = new JButton("D\u00EDa concreto");
			btnDiaConcreto.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CalendarioCancelarActividadDiaConcreto cal = new CalendarioCancelarActividadDiaConcreto(parent.getParent().getPrograma());
					cal.setVisible(true);
				}
			});
		}
		return btnDiaConcreto;
	}
}
