package sprint1.ui.ventanas.administracion.instalaciones;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import sprint1.business.Programa;
import sprint1.business.dominio.centroDeportes.actividades.Actividad;
import sprint1.business.dominio.centroDeportes.instalaciones.Instalacion;
import sprint1.ui.ventanas.administracion.actividades.CalendarioDisponibilidadInstalacion;

public class CerrarEspecificando extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel pnCerrarEnEspecifico;
	private JPanel pnInstalacionACerrar;
	private JComboBox<Instalacion> cbInstalacion;
	private JLabel lblInstalacion;
	private JButton btnNewButton;
	private JPanel pnActividades;
	private JLabel lblActividades;
	private JPanel pnAñadirActividades;
	private JPanel pnComboActividades;
	private JComboBox<Actividad> cbActividades;
	private JButton btnAñadir;
	private JScrollPane scrollPane;
	private JList<Actividad> listActividades;
	private JPanel pnPermitirAlquileres;
	private JLabel lblPermitir;
	private JPanel pnRadioAlquileres;
	private JRadioButton rdbtnSi;
	private JRadioButton rdbtnNo;
	private JButton btnQuitar;
	private JPanel pnBotones;
	private JButton btnCancelar;
	private JButton btnCerrar;

	private Programa p;
	private ButtonGroup bg;
	private DefaultListModel<Actividad> lm;

	/**
	 * Create the dialog.
	 */
	public CerrarEspecificando(Programa p) {
		this.p = p;
		this.bg = new ButtonGroup();
		this.lm = new DefaultListModel<>();
		setBounds(100, 100, 450, 369);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		getContentPane().add(getPnCerrarEnEspecifico());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	}

	private JPanel getPnCerrarEnEspecifico() {
		if (pnCerrarEnEspecifico == null) {
			pnCerrarEnEspecifico = new JPanel();
			pnCerrarEnEspecifico.setLayout(new BoxLayout(pnCerrarEnEspecifico, BoxLayout.Y_AXIS));
			pnCerrarEnEspecifico.add(getPnInstalacionACerrar());
			pnCerrarEnEspecifico.add(getPnActividades());
			pnCerrarEnEspecifico.add(getPnPermitirAlquileres());
			pnCerrarEnEspecifico.add(getPnBotones());
		}
		return pnCerrarEnEspecifico;
	}

	private JPanel getPnInstalacionACerrar() {
		if (pnInstalacionACerrar == null) {
			pnInstalacionACerrar = new JPanel();
			pnInstalacionACerrar.setLayout(new BorderLayout(0, 0));
			pnInstalacionACerrar.add(getCbInstalacion(), BorderLayout.CENTER);
			pnInstalacionACerrar.add(getLblInstalacion(), BorderLayout.NORTH);
			pnInstalacionACerrar.add(getBtnNewButton(), BorderLayout.SOUTH);
		}
		return pnInstalacionACerrar;
	}
	
	private void rellenarModeloParaInstalacion() {
		Instalacion i = (Instalacion)cbInstalacion.getSelectedItem();
		
		try {
			
			List<Actividad> actividadesCombo = new LinkedList<>();
			
			for(Actividad a: p.getActividadesDisponiblesParaInstalacion(i))
				actividadesCombo.add(a);
			
			cbActividades.setModel(new DefaultComboBoxModel<Actividad>(
					actividadesCombo.toArray(new Actividad[actividadesCombo.size()])));
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Ha ocurrido un error buscando las actividades disponibles para la instalacion seleccionada");
		}
	}

	private JComboBox<Instalacion> getCbInstalacion() {
		if (cbInstalacion == null) {
			cbInstalacion = new JComboBox<>();
			
			cbInstalacion.setModel(new DefaultComboBoxModel<Instalacion>(
					p.getInstalacionesDisponibles().toArray(new Instalacion[p.getInstalacionesDisponibles().size()])));
		
			cbInstalacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					rellenarModeloParaInstalacion();
				}
			});
		}
		return cbInstalacion;
	}

	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalaci\u00F3n a cerrar:");
		}
		return lblInstalacion;
	}

	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Ver disponibilidad instalaciones");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CalendarioDisponibilidadInstalacion cdi = new CalendarioDisponibilidadInstalacion(p);
					cdi.setLocationRelativeTo(CerrarEspecificando.this);
					cdi.setModal(true);
					cdi.setVisible(true);
				}
			});
			btnNewButton.setBackground(new Color(30, 144, 255));
			btnNewButton.setForeground(Color.WHITE);
		}
		return btnNewButton;
	}

	private JPanel getPnActividades() {
		if (pnActividades == null) {
			pnActividades = new JPanel();
			pnActividades.setLayout(new BorderLayout(0, 0));
			pnActividades.add(getLblActividades(), BorderLayout.NORTH);
			pnActividades.add(getPnAñadirActividades(), BorderLayout.CENTER);
			pnActividades.add(getBtnQuitar(), BorderLayout.SOUTH);
		}
		return pnActividades;
	}

	private JLabel getLblActividades() {
		if (lblActividades == null) {
			lblActividades = new JLabel("Actividades para las que cerrar la instalaci\u00F3n:");
		}
		return lblActividades;
	}

	private JPanel getPnAñadirActividades() {
		if (pnAñadirActividades == null) {
			pnAñadirActividades = new JPanel();
			pnAñadirActividades.setLayout(new BorderLayout(0, 0));
			pnAñadirActividades.add(getPnComboActividades(), BorderLayout.NORTH);
			pnAñadirActividades.add(getScrollPane(), BorderLayout.CENTER);
		}
		return pnAñadirActividades;
	}

	private JPanel getPnComboActividades() {
		if (pnComboActividades == null) {
			pnComboActividades = new JPanel();
			pnComboActividades.setLayout(new GridLayout(0, 1, 0, 0));
			pnComboActividades.add(getCbActividades());
			rellenarModeloParaInstalacion();
			pnComboActividades.add(getBtnAñadir());
		}
		return pnComboActividades;
	}

	private JComboBox<Actividad> getCbActividades() {
		if (cbActividades == null) {
			cbActividades = new JComboBox<>();
		}
		return cbActividades;
	}

	private JButton getBtnAñadir() {
		if (btnAñadir == null) {
			btnAñadir = new JButton("A\u00F1adir");
			btnAñadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Actividad selected = (Actividad) cbActividades.getSelectedItem();

					boolean alreadyInModel = false;
					for (int i = 0; i < lm.size(); i++) {
						if (selected.getCodigo().equals(lm.get(i).getCodigo())) {
							alreadyInModel = true;
						}
					}

					if (!alreadyInModel) {
						lm.addElement(selected);
					} else {
						JOptionPane.showMessageDialog(CerrarEspecificando.this,
								"La actividad a añadir ya está en la lista");
					}
				}
			});
			btnAñadir.setForeground(new Color(255, 255, 255));
			btnAñadir.setBackground(new Color(60, 179, 113));
		}
		return btnAñadir;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getListActividades());
		}
		return scrollPane;
	}

	private JList<Actividad> getListActividades() {
		if (listActividades == null) {
			listActividades = new JList<>(this.lm);
			listActividades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return listActividades;
	}

	private JPanel getPnPermitirAlquileres() {
		if (pnPermitirAlquileres == null) {
			pnPermitirAlquileres = new JPanel();
			pnPermitirAlquileres.setLayout(new BorderLayout(0, 0));
			pnPermitirAlquileres.add(getLblPermitir(), BorderLayout.NORTH);
			pnPermitirAlquileres.add(getPnRadioAlquileres(), BorderLayout.CENTER);
		}
		return pnPermitirAlquileres;
	}

	private JLabel getLblPermitir() {
		if (lblPermitir == null) {
			lblPermitir = new JLabel("Permitir alquileres en la instalaci\u00F3n:");
		}
		return lblPermitir;
	}

	private JPanel getPnRadioAlquileres() {
		if (pnRadioAlquileres == null) {
			pnRadioAlquileres = new JPanel();
			pnRadioAlquileres.add(getRdbtnSi());
			pnRadioAlquileres.add(getRdbtnNo());
		}
		return pnRadioAlquileres;
	}

	private JRadioButton getRdbtnSi() {
		if (rdbtnSi == null) {
			rdbtnSi = new JRadioButton("S\u00ED");
			bg.add(rdbtnSi);
			rdbtnSi.setSelected(true);
		}
		return rdbtnSi;
	}

	private JRadioButton getRdbtnNo() {
		if (rdbtnNo == null) {
			rdbtnNo = new JRadioButton("No");
			bg.add(rdbtnNo);
		}
		return rdbtnNo;
	}

	private JButton getBtnQuitar() {
		if (btnQuitar == null) {
			btnQuitar = new JButton("Quitar");
			btnQuitar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int aQuitar = listActividades.getSelectedIndex();

					lm.remove(aQuitar);
				}
			});
			btnQuitar.setForeground(new Color(255, 255, 255));
			btnQuitar.setBackground(new Color(255, 99, 71));
		}
		return btnQuitar;
	}

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.add(getBtnCancelar());
			pnBotones.add(getBtnCerrar());
		}
		return pnBotones;
	}

	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnCancelar.setForeground(new Color(255, 255, 255));
			btnCancelar.setBackground(new Color(255, 99, 71));
		}
		return btnCancelar;
	}

	private JButton getBtnCerrar() {
		if (btnCerrar == null) {
			btnCerrar = new JButton("Cerrar Instalaci\u00F3n");
			btnCerrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					Instalacion selected = (Instalacion)cbInstalacion.getSelectedItem();
					
					if(rdbtnNo.isSelected()) {
						try {
							selected.setPermisionAlquileres(false);
							p.updateInstalacion(selected);
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(CerrarEspecificando.this, "Ha ocurrido un error modificando la información de la instalación a cerrar");
						}
					}
					
					for(int i = 0; i < lm.size(); i++) {
						Actividad aVetar = lm.get(i);
						
						try {
							p.vetarActividadEnInstalacion(selected, aVetar);
							p.deleteAsociadosConCierre();
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(CerrarEspecificando.this, "Ha ocurrido un error vetando la actividad de la instalación");
						}
					}
					
					dispose();
				}
			});
			btnCerrar.setBackground(new Color(60, 179, 113));
			btnCerrar.setForeground(new Color(255, 255, 255));
		}return btnCerrar;
}}
