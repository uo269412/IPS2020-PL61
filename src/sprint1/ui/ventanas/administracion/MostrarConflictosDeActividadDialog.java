package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Conflicto;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MostrarConflictosDeActividadDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblConflictos;
	private JScrollPane scrollPane;
	private JList<Conflicto> list;
	private JPanel pnButtons;
	private JButton btnNewButton;

	private DefaultListModel<Conflicto> lm;
	private ActividadPlanificada ap;
	
	/**
	 * Create the dialog.
	 */
	public MostrarConflictosDeActividadDialog(ActividadPlanificada ap) {
		this.ap = ap;
		this.lm = new DefaultListModel<>();
		rellenarModelo();
		setBounds(100, 100, 422, 328);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getLblConflictos(), BorderLayout.NORTH);
		getContentPane().add(getScrollPane(), BorderLayout.CENTER);
		getContentPane().add(getPnButtons(), BorderLayout.SOUTH);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	}

	private JLabel getLblConflictos() {
		if (lblConflictos == null) {
			lblConflictos = new JLabel("Conflictos de actividad:");
			lblConflictos.setFont(new Font("Arial Black", Font.PLAIN, 15));
		}
		return lblConflictos;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getList());
		}
		return scrollPane;
	}
	private JList<Conflicto> getList() {
		if (list == null) {
			list = new JList<>();
		}
		return list;
	}
	private JPanel getPnButtons() {
		if (pnButtons == null) {
			pnButtons = new JPanel();
			pnButtons.add(getBtnNewButton());
		}
		return pnButtons;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("New button");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					imprimirConflictos();
				}
			});
			btnNewButton.setBackground(new Color(65, 105, 225));
			btnNewButton.setForeground(new Color(255, 255, 255));
		}
		return btnNewButton;
	}
	
	private void rellenarModelo() {
		for(Conflicto c: ap.getConflictos()) {
			lm.addElement(c);
		}
		
	}
	
	private void imprimirConflictos() {
		ap.imprimirConflictos();
	}
}