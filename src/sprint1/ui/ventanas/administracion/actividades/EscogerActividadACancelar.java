package sprint1.ui.ventanas.administracion.actividades;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;
import sprint1.ui.ventanas.administracion.util.CalendarioSemanalCancelar;

public class EscogerActividadACancelar extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JList<ActividadPlanificada> list;

	private DefaultListModel<ActividadPlanificada> lm;
	private CalendarioSemanalCancelar parent;
	
	/**
	 * Create the dialog.
	 */
	public EscogerActividadACancelar(CalendarioSemanalCancelar parent, List<ActividadPlanificada> actividades) {
		lm = new DefaultListModel<>();
		for(ActividadPlanificada a: actividades) {
			lm.addElement(a);
		}
		this.parent = parent;
		setBounds(100, 100, 447, 339);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getLblNewLabel(), BorderLayout.NORTH);
		getContentPane().add(getScrollPane(), BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Cancelar planificaci\u00F3n en actividad:");
			lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 16));
		}
		return lblNewLabel;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getList());
		}
		return scrollPane;
	}
	private JList<ActividadPlanificada> getList() {
		if (list == null) {
			list = new JList<>(this.lm);
			list.addMouseListener(new MouseAdapter() {
			    public void mouseClicked(MouseEvent evt) {
			        @SuppressWarnings("unchecked")
					JList<ActividadPlanificada> list = (JList<ActividadPlanificada>)evt.getSource();
			        if (evt.getClickCount() == 2) {

			            // Double-click detected
			            int c = list.getSelectedIndex();
			            ActividadPlanificada ap = lm.get(c);
			            
			            CancelarActividadDialog cad = new CancelarActividadDialog(parent, ap);
			            cad.setLocationRelativeTo(list);
			            cad.setModal(true);
			            cad.setVisible(true);
			            dispose();
			        }
			    }
			});
		}
		return list;
	}
}
