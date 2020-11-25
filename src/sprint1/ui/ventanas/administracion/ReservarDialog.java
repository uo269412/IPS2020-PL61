package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Alquiler;
import sprint1.business.clases.Instalacion;
import sprint1.business.clases.Programa;
import sprint1.business.clases.Tercero;

public class ReservarDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dia;
	private int mes;
	private int año;
	
	private Programa p;
	private Tercero t;
	
	private final JPanel contentPanel = new JPanel();
	private JLabel lblInstalacion;
	private JComboBox<Instalacion> cmbInstalaciones;
	private JLabel lblFechaInicio;
	private JTextField txtFechaInicio;
	private JPanel radioButtonPane;
	private JRadioButton rdbtnDiariamente;
	private JRadioButton rdbtnSemanalmente;
	private JRadioButton rdbtnUnaVez;
	private JLabel lblFechaFin;
	private JTextField txtFechaFin;
	private JPanel horaReservaPane;
	private JPanel horaInicioPane;
	private JPanel horaFinPane;
	private JLabel lblHoraInicio;
	private JTextField txtHoraInicio;
	private JLabel lblHoraFin;
	private JTextField txtHoraFin;
	private JButton btnReservar;
	private JLabel lblHorario;
	private ButtonGroup bg = new ButtonGroup();
	/**
	 * Create the dialog.
	 */
	public ReservarDialog(CalendarioTercero c, Programa p, Tercero t, int dia, int mes, int año) {
		setTitle("Reservar instalaci\u00F3n: " + dia + "/" + mes + "/" + año);
		this.dia = dia;
		this.mes = mes;
		this.año = año;
		this.t = t;
		this.p = p;
		setBounds(100, 100, 346, 514);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		contentPanel.add(getLblInstalacion());
		contentPanel.add(getCmbInstalaciones());
		contentPanel.add(getLblFechaInicio());
		contentPanel.add(getTxtFechaInicio());
		contentPanel.add(getRadioButtonPane());
		contentPanel.add(getLblFechaFin());
		contentPanel.add(getTxtFechaFin());
		contentPanel.add(getLblHorario());
		contentPanel.add(getHoraReservaPane());
		contentPanel.add(getBtnReservar());
	}
	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalaci\u00F3n a reservar:");
		}
		return lblInstalacion;
	}
	private JComboBox<Instalacion> getCmbInstalaciones() {
		if (cmbInstalaciones == null) {
			cmbInstalaciones = new JComboBox<Instalacion>();
			cmbInstalaciones.setModel(new DefaultComboBoxModel<Instalacion>(p.getInstalacionesDisponibles().toArray(new Instalacion[p.getInstalacionesDisponibles().size()])));
		}
		return cmbInstalaciones;
	}
	private JLabel getLblFechaInicio() {
		if (lblFechaInicio == null) {
			lblFechaInicio = new JLabel("Fecha inicio:");
		}
		return lblFechaInicio;
	}
	private JTextField getTxtFechaInicio() {
		if (txtFechaInicio == null) {
			txtFechaInicio = new JTextField();
			txtFechaInicio.setText(dia+"/"+mes+"/"+año);
			txtFechaInicio.setEditable(false);
			txtFechaInicio.setColumns(10);
		}
		return txtFechaInicio;
	}
	private JPanel getRadioButtonPane() {
		if (radioButtonPane == null) {
			radioButtonPane = new JPanel();
			radioButtonPane.add(getRdbtnDiariamente());
			radioButtonPane.add(getRdbtnSemanalmente());
			radioButtonPane.add(getRadioButton_1());
		}
		return radioButtonPane;
	}
	private JRadioButton getRdbtnDiariamente() {
		if (rdbtnDiariamente == null) {
			rdbtnDiariamente = new JRadioButton("Diariamente");
			rdbtnDiariamente.setActionCommand("diariamente");
			bg.add(rdbtnDiariamente);
		}
		return rdbtnDiariamente;
	}
	private JRadioButton getRdbtnSemanalmente() {
		if (rdbtnSemanalmente == null) {
			rdbtnSemanalmente = new JRadioButton("Semanalmente");
			rdbtnSemanalmente.setActionCommand("semanalmente");
			bg.add(rdbtnSemanalmente);
		}
		return rdbtnSemanalmente;
	}
	private JRadioButton getRadioButton_1() {
		if (rdbtnUnaVez == null) {
			rdbtnUnaVez = new JRadioButton("Una sola vez");
			rdbtnUnaVez.setActionCommand("una vez");
			rdbtnUnaVez.setSelected(true);
			bg.add(rdbtnUnaVez);
		}
		return rdbtnUnaVez;
	}
	private JLabel getLblFechaFin() {
		if (lblFechaFin == null) {
			lblFechaFin = new JLabel("Fecha fin:");
		}
		return lblFechaFin;
	}
	private JTextField getTxtFechaFin() {
		if (txtFechaFin == null) {
			txtFechaFin = new JTextField();
			txtFechaFin.setColumns(10);
		}
		return txtFechaFin;
	}
	private JPanel getHoraReservaPane() {
		if (horaReservaPane == null) {
			horaReservaPane = new JPanel();
			horaReservaPane.add(getHoraInicioPane());
			horaReservaPane.add(getHoraFinPane());
		}
		return horaReservaPane;
	}
	private JPanel getHoraInicioPane() {
		if (horaInicioPane == null) {
			horaInicioPane = new JPanel();
			horaInicioPane.setLayout(new GridLayout(0, 1, 0, 0));
			horaInicioPane.add(getLblHoraInicio());
			horaInicioPane.add(getTxtHoraInicio());
		}
		return horaInicioPane;
	}
	private JPanel getHoraFinPane() {
		if (horaFinPane == null) {
			horaFinPane = new JPanel();
			horaFinPane.setLayout(new GridLayout(0, 1, 0, 0));
			horaFinPane.add(getLblHoraFin());
			horaFinPane.add(getTextField_1());
		}
		return horaFinPane;
	}
	private JLabel getLblHoraInicio() {
		if (lblHoraInicio == null) {
			lblHoraInicio = new JLabel("Hora inicio:");
		}
		return lblHoraInicio;
	}
	private JTextField getTxtHoraInicio() {
		if (txtHoraInicio == null) {
			txtHoraInicio = new JTextField();
			txtHoraInicio.setColumns(10);
		}
		return txtHoraInicio;
	}
	private JLabel getLblHoraFin() {
		if (lblHoraFin == null) {
			lblHoraFin = new JLabel("Hora fin:");
		}
		return lblHoraFin;
	}
	private JTextField getTextField_1() {
		if (txtHoraFin == null) {
			txtHoraFin = new JTextField();
			txtHoraFin.setColumns(10);
		}
		return txtHoraFin;
	}
	private JButton getBtnReservar() {
		if (btnReservar == null) {
			btnReservar = new JButton("Reservar");
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(checkHoraInicio() && checkHoraFin() && checkFechaFin()) {
						int añoUltimaReserva = año;
						int mesUltimaReserva = mes;
						int diaUltimaReserva = dia;
						int diaFin = 0;
						int mesFin = 0;
						int añoFin = 0;
						if(!txtFechaFin.getText().equals("")) {
							diaFin = Integer.parseInt(txtFechaFin.getText().split("/")[0]);
							mesFin = Integer.parseInt(txtFechaFin.getText().split("/")[1]);
							añoFin = Integer.parseInt(txtFechaFin.getText().split("/")[2]);
						}
						
						int incremento = -1;
						switch(bg.getSelection().getActionCommand()) {
						case "diariamente":
							incremento = 1;
							break;
						case "semanalmente":
							incremento = 7;
							break;
						case "una vez":
							incremento = 0;
							break;
						}
						
						if(incremento == 0) {
							if(!isOcupado(diaUltimaReserva, mesUltimaReserva, añoUltimaReserva))
								createAlquiler(diaUltimaReserva, mesUltimaReserva, añoUltimaReserva);
							else
								JOptionPane.showMessageDialog(ReservarDialog.this, "La instalación donde estás intentando reservar está ocupada a esa hora, prueba otra hora o otro día");
						} else {
							while(LocalDate.of(añoUltimaReserva, mesUltimaReserva, diaUltimaReserva).isBefore(
									LocalDate.of(añoFin, mesFin, diaFin))) {
								
								if(mesUltimaReserva%2 == 1 || mesUltimaReserva == 12) {
									if(diaUltimaReserva > 31) {
										mesUltimaReserva++;
										diaUltimaReserva -= 31;
										if(mesUltimaReserva > 12) {
											mesUltimaReserva -= 12;
											añoUltimaReserva++;
										}
									}
								} else {
									if(diaUltimaReserva > 30) {
										mesUltimaReserva++;
										diaUltimaReserva -= 30;
										if(mesUltimaReserva > 12) {
											mesUltimaReserva -= 12;
											añoUltimaReserva++;
										}
									}
								}
								if(!isOcupado(diaUltimaReserva, mesUltimaReserva, añoUltimaReserva))
									createAlquiler(diaUltimaReserva, mesUltimaReserva, añoUltimaReserva);
								else {
									String msg = "La instalación que estás intentando reservar está ocupada para el día " + diaUltimaReserva + "/" + mesUltimaReserva + "/" + añoUltimaReserva + " a las " + txtHoraInicio.getText() + "\n";
									msg += "¿Quieres continuar planificando el resto de reservas?";
									int respuesta = JOptionPane.showConfirmDialog(ReservarDialog.this, msg, "Problema con la reserva", JOptionPane.YES_NO_OPTION);
									if(respuesta != JOptionPane.YES_OPTION) {
										break;
									}
								}
								
								diaUltimaReserva += incremento;
							}
						}
						dispose();
					}
						
				}
			});
			btnReservar.setBackground(new Color(60, 179, 113));
			btnReservar.setForeground(new Color(255, 255, 255));
		}
		return btnReservar;
	}
	
	private boolean isOcupado(int dia, int mes, int año) {
		
		List<ActividadPlanificada> actividadesEnInstalacion = new ArrayList<>();
		List<Alquiler> alquileresDeInstalacion = new ArrayList<>();
		
		for(ActividadPlanificada a: p.getActividadesPlanificadas(Integer.parseInt(txtHoraInicio.getText()), dia, mes, año)) {
			if(a.getCodigoInstalacion().equals(((Instalacion)cmbInstalaciones.getSelectedItem()).getCodigo())) {
				actividadesEnInstalacion.add(a);
			}
		}
		
		for(Alquiler a: p.getAlquileres(Integer.parseInt(txtHoraInicio.getText()), dia, mes, año)) {
			if(a.getId_instalacion().equals(((Instalacion)cmbInstalaciones.getSelectedItem()).getCodigo())) {
				alquileresDeInstalacion.add(a);
			}
		}
		
		if(actividadesEnInstalacion.size() != 0 || alquileresDeInstalacion.size() != 0) {
			return true;
		}
		
		return false;
	}
	
	private void createAlquiler(int dia, int mes, int año) {
		try {
			p.añadirAlquilerDia(t, p.obtenerInstalacionPorId(((Instalacion)cmbInstalaciones.getSelectedItem()).getCodigo()),
					dia, mes, año, Integer.parseInt(txtHoraInicio.getText()), Integer.parseInt(txtHoraFin.getText()));
		} catch (NumberFormatException | SQLException e) {
			JOptionPane.showMessageDialog(this, "Ha ocurrido un error con la base de datos");
		}
	}
	
	private JLabel getLblHorario() {
		if (lblHorario == null) {
			lblHorario = new JLabel("Horario:");
		}
		return lblHorario;
	}
	
	private boolean checkHoraInicio() {
		if (txtHoraInicio.getText().length() > 2) {
			txtHoraInicio.setBackground(Color.RED);
			txtHoraInicio.setForeground(Color.WHITE);
			return false;
		}

		try {
			Integer.parseInt(txtHoraInicio.getText());
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	private boolean checkHoraFin() {
		if (txtHoraFin.getText().length() > 2) {
			txtHoraFin.setBackground(Color.RED);
			txtHoraFin.setForeground(Color.WHITE);
			return false;
		}

		try {
			Integer.parseInt(txtHoraFin.getText());
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	private boolean checkFechaFin() {
		if(rdbtnUnaVez.isSelected()) {
			if(txtFechaFin.getText().equals("")) {
				return true;
			} else {
				JOptionPane.showMessageDialog(this, "La fecha de fin de reserva se debe dejar vacía para los alquileres de un solo día");
				return false;
			}
		} else {
			if (txtFechaFin.getText().split("/").length != 3) {
				txtFechaFin.setBackground(Color.RED);
				txtFechaFin.setForeground(Color.WHITE);
				return false;
			}
			try {
				for(String s: txtFechaFin.getText().split("/"))
					Integer.parseInt(s);
			} catch (NumberFormatException e) {
				txtFechaFin.setBackground(Color.RED);
				txtFechaFin.setForeground(Color.WHITE);
				return false;
			}
		}
		return true;
	}
	
}
