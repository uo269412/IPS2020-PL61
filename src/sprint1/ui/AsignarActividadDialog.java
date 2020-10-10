package sprint1.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import sprint1.business.clases.Actividad;
import sprint1.business.clases.Programa;

public class AsignarActividadDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton parent;
	private Programa programa;
	private JTextField txtHoraInicio;
	private JTextField txtHoraFin;
	private JTextField textField;
	
	private int dia;
	private int mes;
	private int a�o;
	/**
	 * Create the dialog.
	 */
	public AsignarActividadDialog(JButton parentButton, Programa p, int dia, int mes, int a�o) {
		this.parent = parentButton;
		this.programa = p;
		this.dia = dia;
		this.mes = mes;
		this.a�o = a�o;
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
				JButton btnA�adir = new JButton("A\u00F1adir");
				btnA�adir.setForeground(Color.WHITE);
				btnA�adir.setBackground(new Color(60, 179, 113));
				pnProgramarActividad.add(btnA�adir);
			}
		
		}
		{
			JPanel pnActividadesPlanificadas = new JPanel();
			contentPanel.add(pnActividadesPlanificadas);
			pnActividadesPlanificadas.setLayout(new BorderLayout(0, 0));
			{
				JList<? extends E> listActividadesPlanificadas = new JList();
				pnActividadesPlanificadas.add(list, BorderLayout.CENTER);
			}
			{
				JButton btnEliminar = new JButton("Eliminar seleccionada");
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

}
