package sprint1.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import sprint1.business.clases.Actividad;
import sprint1.business.clases.Reserva;
import sprint1.business.clases.Socio;

public class ListaDeActividadesWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private SocioWindow parent = null;
	private Socio socio = null;
	private JScrollPane scpActividades;
	private JList<Actividad> listActividades;
	private DefaultListModel<Actividad> modeloActividades = null;

	public ListaDeActividadesWindow(SocioWindow parent, Socio socio) {
		setTitle("Lista de actividades");
		this.parent = parent;
		this.socio = socio;
		setBounds(100, 100, 450, 425);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getScpActividades(), BorderLayout.CENTER);
		cargarActividades();
	}

	private JScrollPane getScpActividades() {
		if (scpActividades == null) {
			scpActividades = new JScrollPane();
			scpActividades.setViewportView(getListActividades());
		}
		return scpActividades;
	}

	private JList<Actividad> getListActividades() {
		if (listActividades == null) {
			modeloActividades = new DefaultListModel<Actividad>();
			listActividades = new JList<Actividad>(modeloActividades);
			listActividades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return listActividades;
	}
	
	//CAMBIAR A RESERVA
	private void cargarActividades() {
		modeloActividades.clear();
//		List<String> codigosActividades = new ArrayList<String>();
//		for (Reserva reserva: parent.getParent().getPrograma().getReservas()) {
//			if (reserva.getId_cliente().equals(socio.getId_cliente())) {
//				codigosActividades.add(reserva.getCodigo_actividad());
//			}
//		}
//		for (String codigo: codigosActividades) {
//			for (Actividad actividad: parent.getParent().getPrograma().getActividades()) {
//				if (codigo.equals(actividad.getCodigo())) {
//					modeloActividades.addElement(actividad);
//				}
//			}
//		}
		for (Actividad actividad: parent.getParent().getPrograma().getActividades()) {
			modeloActividades.addElement(actividad);
		}
	}
	
}
