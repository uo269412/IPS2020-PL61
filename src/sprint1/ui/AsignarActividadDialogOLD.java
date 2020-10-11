package sprint1.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import sprint1.business.clases.Actividad;
import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Programa;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AsignarActividadDialogOLD extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton parent;
	private Programa programa;
	private JTextField txtHoraInicio;
	private JTextField txtHoraFin;
	private JTextField textField;
	private DefaultListModel<ActividadPlanificada> modeloLista;
	JList<ActividadPlanificada> listActividadesPlanificadas;
	
	private int dia;
	private int mes;
	private int año;
	/**
	 * Create the dialog.
	 */
	public AsignarActividadDialogOLD(JButton parentButton, Programa p, int dia, int mes, int año) {
		this.parent = parentButton;
		this.programa = p;
		this.dia = dia;
		this.mes = mes;
		this.año = año;
		this.modeloLista = new DefaultListModel<ActividadPlanificada>();
		setLocationRelativeTo(parent);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		{
			JPanel pnProgramarActividad = new JPanel();
			contentPanel.add(pnProgramarActividad);
			pnProgramarActividad.setLayout(new BoxLayout(pnProgramarActividad, BoxLayout.PAGE_AXIS));
			{
				JLabel lblActividad = new JLabel("Actividad a asignar:");
				pnProgramarActividad.add(lblActividad);
			}
			{
				JComboBox<Actividad> comboBox = new JComboBox<>();
				comboBox.setModel(new DefaultComboBoxModel(p.getActividades().toArray(new Actividad[p.getActividades().size()])));
				pnProgramarActividad.add(comboBox);
			}
			{
				JLabel lblHora = new JLabel("Hora inicio/Hora fin (formato HH)");
				pnProgramarActividad.add(lblHora);
			}
			{
				JPanel pnHoras = new JPanel();
				pnProgramarActividad.add(pnHoras);
				{
					txtHoraInicio = new JTextField();
					pnHoras.add(txtHoraInicio);
					txtHoraInicio.setColumns(10);
				}
				{
					txtHoraFin = new JTextField();
					pnHoras.add(txtHoraFin);
					txtHoraFin.setColumns(10);
				}
			}
			{
				JLabel lblLimite = new JLabel("Limite de plazas:");
				pnProgramarActividad.add(lblLimite);
			}
			{
				textField = new JTextField();
				pnProgramarActividad.add(textField);
				textField.setColumns(10);
			}
			{
				JLabel lblMonitor = new JLabel("Monitor para la actividad:");
				pnProgramarActividad.add(lblMonitor);
			}
			{
				JComboBox comboBox = new JComboBox();
				pnProgramarActividad.add(comboBox);
			}
			{
				JLabel lblNewLabel = new JLabel("Recursos (separados por \", \"):");
				pnProgramarActividad.add(lblNewLabel);
			}
			{
				JTextPane textPane = new JTextPane();
				pnProgramarActividad.add(textPane);
			}
			{
				JButton btnAñadir = new JButton("A\u00F1adir");
				btnAñadir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
					}
				});
				btnAñadir.setForeground(Color.WHITE);
				btnAñadir.setBackground(new Color(60, 179, 113));
				pnProgramarActividad.add(btnAñadir);
			}
		
		}
		{
			JPanel pnActividadesPlanificadas = new JPanel();
			contentPanel.add(pnActividadesPlanificadas);
			pnActividadesPlanificadas.setLayout(new BorderLayout(0, 0));
			{
				mostrarActividadesPlanificadasDia();
				listActividadesPlanificadas = new JList<ActividadPlanificada>();
				pnActividadesPlanificadas.add(listActividadesPlanificadas, BorderLayout.CENTER);
				listActividadesPlanificadas.setModel(modeloLista);
			}
			{
				JButton btnEliminar = new JButton("Eliminar seleccionada");
				btnEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							programa.eliminarActividadPlanificada(listActividadesPlanificadas.getSelectedValue());
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(getMe(), "Ha ocurrido un error eliminando la actividad. Póngase en contacto con el desarrollador");
						}
						
						mostrarActividadesPlanificadasDia();
					}
				});
				btnEliminar.setForeground(Color.WHITE);
				btnEliminar.setBackground(new Color(255, 99, 71));
				pnActividadesPlanificadas.add(btnEliminar, BorderLayout.SOUTH);
			}
			{
				JLabel lblDia = new JLabel("Actividades d\u00EDa:");
				pnActividadesPlanificadas.add(lblDia, BorderLayout.NORTH);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private JDialog getMe() {
		return this;
	}
	
	private void mostrarActividadesPlanificadasDia() {
		try {
			for(ActividadPlanificada a: programa.getActividadesPlanificadasDia(dia, mes, año)) {
				modeloLista.addElement(a);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Ha ocurrido un error cargando las actividades para este día, por favor contacte con el desarrollador");
		}
	}

}
