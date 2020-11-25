package sprint1.ui.ventanas.administracion.estado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sprint1.business.Programa;
import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;
import sprint1.ui.ventanas.administracion.estado.mostrar.MostrarConflictosDeActividadDialog;
import java.awt.Toolkit;

public class VerConflictosDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblActividadesConflictos;
	private JList<ActividadPlanificada> list;
	private JPanel pnButtons;
	private JButton btnImprimir;

	private DefaultListModel<ActividadPlanificada> lm = new DefaultListModel<>();
	private Programa p;

	/**
	 * Create the dialog.
	 */
	public VerConflictosDialog(Programa p) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VerConflictosDialog.class.getResource("/sprint1/ui/resources/titulo.png")));
		this.p = p;
		rellenarModelo();
		setTitle("Centro de deporte: Actividades en conflicto");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getLblActividadesConflictos(), BorderLayout.NORTH);
		getContentPane().add(getList(), BorderLayout.CENTER);
		getContentPane().add(getPnButtons(), BorderLayout.SOUTH);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	}

	private JLabel getLblActividadesConflictos() {
		if (lblActividadesConflictos == null) {
			lblActividadesConflictos = new JLabel("Actividades que generan conflictos:");
			lblActividadesConflictos.setFont(new Font("Arial Black", Font.PLAIN, 16));
		}
		return lblActividadesConflictos;
	}
	private JList<ActividadPlanificada> getList() {
		if (list == null) {
			list = new JList<ActividadPlanificada>(lm);
			list.addMouseListener(new MouseAdapter() {
			    public void mouseClicked(MouseEvent evt) {
			        @SuppressWarnings("unchecked")
					JList<ActividadPlanificada> list = (JList<ActividadPlanificada>)evt.getSource();
			        if (evt.getClickCount() == 2) {

			            // Double-click detected
			            int c = list.getSelectedIndex();
			            ActividadPlanificada ap = lm.get(c);
			            
			            MostrarConflictosDeActividadDialog mcad = new MostrarConflictosDeActividadDialog(ap);
			            mcad.setLocationRelativeTo(list);
			            mcad.setModal(true);
			            mcad.setVisible(true);
			        }
			    }
			});
		}
		return list;
	}
	private JPanel getPnButtons() {
		if (pnButtons == null) {
			pnButtons = new JPanel();
			pnButtons.add(getBtnImprimir());
		}
		return pnButtons;
	}
	private JButton getBtnImprimir() {
		if (btnImprimir == null) {
			btnImprimir = new JButton("Imprimir");
			btnImprimir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for(int i = 0; i < lm.size(); i++) {
						ActividadPlanificada ap = lm.get(i);
						ap.imprimirConflictos();
						System.out.println();
					}
				}
			});
			btnImprimir.setForeground(new Color(255, 255, 255));
			btnImprimir.setBackground(new Color(65, 105, 225));
		}
		return btnImprimir;
	}
	
	private void rellenarModelo() {
		for(ActividadPlanificada ap: p.getActividadesPlanificadas()) {
			if(ap.getConflictos().size() > 0) {
				lm.addElement(ap);
			}
		}
	}
}
