package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sprint1.business.clases.Programa;
import sprint1.business.clases.Socio;

import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;

public class ListaSociosConImpagos extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel pnLista;
	private JLabel lblImpagos;
	private JList<Socio> list;
	private JPanel pnSeleccionMes;
	private JComboBox<String> cmbMes;
	private JComboBox<Integer> cmbA�o;
	private ArrayList<String> meses;
	private ArrayList<Integer> a�os;
	
	private Programa p;
	private DefaultListModel<Socio> dflm;
	private HashMap<String, Integer> mes;

	private ActionListener aa = new RefreshList();
	/**
	 * Create the dialog.
	 */
	public ListaSociosConImpagos(Programa p) {
		this.p = p;
		this.dflm = new DefaultListModel<>();
		this.mes = new HashMap<>();
		llenarMeses();
		llenarA�os();
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getPnLista(), BorderLayout.CENTER);
		getContentPane().add(getPnSeleccionMes(), BorderLayout.NORTH);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	}
	private JPanel getPnLista() {
		if (pnLista == null) {
			pnLista = new JPanel();
			pnLista.setLayout(new BorderLayout(0, 0));
			pnLista.add(getLblImpagos_1(), BorderLayout.NORTH);
			pnLista.add(getList_1(), BorderLayout.CENTER);
		}
		return pnLista;
	}
	private JLabel getLblImpagos_1() {
		if (lblImpagos == null) {
			lblImpagos = new JLabel("Listado de socios que no han pagado sus alquileres:");
			lblImpagos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lblImpagos;
	}
	private JList<Socio> getList_1() {
		if (list == null) {
			list = new JList<Socio>(dflm);
		}
		return list;
	}
	private JPanel getPnSeleccionMes() {
		if (pnSeleccionMes == null) {
			pnSeleccionMes = new JPanel();
			pnSeleccionMes.add(getCmbMes());
			pnSeleccionMes.add(getCmbA�o());
		}
		return pnSeleccionMes;
	}
	private JComboBox<String> getCmbMes() {
		if (cmbMes == null) {
			cmbMes = new JComboBox<String>();
			cmbMes.addActionListener(aa);
		}
		cmbMes.setModel(new DefaultComboBoxModel<String>(meses.toArray(new String[0])));
		return cmbMes;
	}
	private JComboBox<Integer> getCmbA�o() {
		if (cmbA�o == null) {
			cmbA�o = new JComboBox<Integer>();
			cmbA�o.addActionListener(aa);
			cmbA�o.setModel(new DefaultComboBoxModel<Integer>(a�os.toArray(new Integer[0])));
		}
		return cmbA�o;
	}
	
	private class RefreshList implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			cargarListado(mes.get((String)cmbMes.getSelectedItem()), (Integer)cmbA�o.getSelectedItem());
		}
		
	}
	
	private void llenarMeses() {
		meses = new ArrayList<String>();
		meses.add("Enero");
		mes.put("Enero", 1);
		meses.add("Febrero");
		mes.put("Febrero", 2);
		meses.add("Marzo");
		mes.put("Marzo", 3);
		meses.add("Abril");
		mes.put("Abril", 4);
		meses.add("Mayo");
		mes.put("Mayo", 5);
		meses.add("Junio");
		mes.put("Junio", 6);
		meses.add("Julio");
		mes.put("Julio", 7);
		meses.add("Agosto");
		mes.put("Agosto", 8);
		meses.add("Septiembre");
		mes.put("Septiembre", 9);
		meses.add("Octubre");
		mes.put("Octubre", 10);
		meses.add("Noviembre");
		mes.put("Noviembre", 11);
		meses.add("Diciembre");
		mes.put("Diciembre", 12);
	}

	private void llenarA�os() {
		a�os = new ArrayList<Integer>();
		for (int i = 0; i < 100; i++) {
			a�os.add(i + 2020);
		}
	}
	
	private void cargarListado(int mes, int a�o) {
		dflm.clear();
		List<Socio> sociosMorosos = new LinkedList<>();
		for(Socio s: p.sociosQueNoHanPagadoAlquilerMes(mes, a�o)) {
			sociosMorosos.add(s);
		}
		
		Collections.sort(sociosMorosos);
		
		for(Socio s: sociosMorosos) {
			dflm.addElement(s);
		}
	}
}
