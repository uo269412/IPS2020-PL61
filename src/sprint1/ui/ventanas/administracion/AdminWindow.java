package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sprint1.business.dominio.clientes.Tercero;
import sprint1.ui.ventanas.MainWindow;
import sprint1.ui.ventanas.administracion.actividades.CrearActividadDialog;
import sprint1.ui.ventanas.administracion.alquileres.AdminAlquilaSocioAhoraDialog;
import sprint1.ui.ventanas.administracion.alquileres.RegistrarEntradaSocioDialog;
import sprint1.ui.ventanas.administracion.alquileres.RegistrarSalidaSocioDialog;
import sprint1.ui.ventanas.administracion.estado.ListaSociosConImpagos;
import sprint1.ui.ventanas.administracion.estado.VerConflictosDialog;
import sprint1.ui.ventanas.administracion.estado.VerOcupacionDialog;
import sprint1.ui.ventanas.administracion.instalaciones.AsignarRecursosAInstalaciónDialog;
import sprint1.ui.ventanas.administracion.instalaciones.CerrarInstalacionDialog;
import sprint1.ui.ventanas.administracion.monitores.AsignarMonitorDialog;
import sprint1.ui.ventanas.administracion.reservas.AdminReservaSocioDialog;
import sprint1.ui.ventanas.administracion.util.CalendarioAdmin;
import sprint1.ui.ventanas.administracion.util.CalendarioAdminAlquilar;
import sprint1.ui.ventanas.administracion.util.CalendarioSemanalPlanificar;
import sprint1.ui.ventanas.administracion.util.CalendarioTercero;
import sprint1.ui.ventanas.util.CalendarioSemanalBase;

public class AdminWindow extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow parent = null;
	private JPanel pnTextos;
	private JLabel lblBienvenida;
	private JLabel lblAcciones;
	private JPanel pnFuncionalidad;
	private JPanel pnLogOut;
	private JPanel pnAcciones;
	private JButton btnLogOut;
	private JButton btnAsignarMonitor;
	private JButton btnCrearActividad;
	private JButton btnAsignarActividad;

	private AsignarMonitorDialog asignarMonitor;
	private AdminReservaSocioDialog adminReservaSocio;
	private AdminAlquilaSocioAhoraDialog alquilarSocioMomento;
	private CalendarioAdminAlquilar calendarioAlquilerAdmin;
	private RegistrarEntradaSocioDialog registrarEntradaSocio;
	private RegistrarSalidaSocioDialog registrarSalidaSocio;
	private CalendarioSemanalPlanificar calendarioSemanalPlanificar;
	private JButton btnReservaSocio;
	private JButton btnVerOcupacion;
	private JButton btnCerrarInstalación;
	private JButton btnAlquilarSocioMomento;
	private JButton btnAlquilarSocio;
	private JButton btnRegistrarEntrada;
	private JButton btnSociosConImpagos;
	private JButton btnCobrarAlquileres;
	private JButton btnRegistrarSalida;
	private JButton btnAlquilerTercero;
	private JButton btnVerOcupacionBotones;
	private JButton btnPlanificarActividades;
	private JButton btnAsignarRecursos;
	private JButton btnInspeccionarConflictos;

	/**
	 * Create the dialog.
	 */
	public AdminWindow(MainWindow mainWindow) {
		setTitle("Centro de Deportes: Administrador");
		this.parent = mainWindow;
		setBounds(100, 100, 511, 413);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPnTextos(), BorderLayout.NORTH);
		getContentPane().add(getPnFuncionalidad(), BorderLayout.CENTER);
	}

	private JPanel getPnTextos() {
		if (pnTextos == null) {
			pnTextos = new JPanel();
			pnTextos.setBackground(Color.WHITE);
			pnTextos.setLayout(new BorderLayout(0, 0));
			pnTextos.add(getLblBienvenida(), BorderLayout.NORTH);
			pnTextos.add(getLblAcciones());
		}
		return pnTextos;
	}

	private JLabel getLblBienvenida() {
		if (lblBienvenida == null) {
			lblBienvenida = new JLabel("Bienvenido, administrador");
			lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
			lblBienvenida.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return lblBienvenida;
	}

	private JLabel getLblAcciones() {
		if (lblAcciones == null) {
			lblAcciones = new JLabel("Acciones disponibles como administrador:");
			lblAcciones.setFont(new Font("Arial", Font.BOLD, 14));
			lblAcciones.setHorizontalAlignment(SwingConstants.CENTER);
			lblAcciones.setBackground(Color.WHITE);
		}
		return lblAcciones;
	}

	private JPanel getPnFuncionalidad() {
		if (pnFuncionalidad == null) {
			pnFuncionalidad = new JPanel();
			pnFuncionalidad.setLayout(new BorderLayout(0, 0));
			pnFuncionalidad.add(getPnLogOut(), BorderLayout.SOUTH);
			pnFuncionalidad.add(getPnAcciones(), BorderLayout.CENTER);
		}
		return pnFuncionalidad;
	}

	private JPanel getPnLogOut() {
		if (pnLogOut == null) {
			pnLogOut = new JPanel();
			pnLogOut.setBackground(new Color(255, 255, 255));
			pnLogOut.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnLogOut.add(getBtnLogOut());
		}
		return pnLogOut;
	}

	private JPanel getPnAcciones() {
		if (pnAcciones == null) {
			pnAcciones = new JPanel();
			pnAcciones.setBackground(new Color(255, 255, 255));
			pnAcciones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnAcciones.add(getBtnAsignarMonitor_1());
			pnAcciones.add(getBtnCrearActividad());
			pnAcciones.add(getBtnAsignarActividad());
			pnAcciones.add(getBtnReservaSocio());
			pnAcciones.add(getBtnVerOcupacion());
			pnAcciones.add(getBtnCerrarInstalación());
			pnAcciones.add(getBtnAlquilarSocioMomento());
			pnAcciones.add(getBtnAlquilarSocio());
			pnAcciones.add(getBtnCobrarAlquileres());
			pnAcciones.add(getBtnRegistrarSalida());
			pnAcciones.add(getBtnRegistrarEntrada());
			pnAcciones.add(getBtnSociosConImpagos());
			pnAcciones.add(getBtnAlquilerTercero());
			pnAcciones.add(getBtnVerOcupacionBotones());
			pnAcciones.add(getBtnPlanificarActividades());
			pnAcciones.add(getBtnAsignarRecursos());
			pnAcciones.add(getBtnInspeccionarConflictos());
		}
		return pnAcciones;
	}

	private JButton getBtnLogOut() {
		if (btnLogOut == null) {
			btnLogOut = new JButton("Log Out");
			btnLogOut.setMnemonic('O');
			btnLogOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnLogOut.setHorizontalAlignment(SwingConstants.RIGHT);
			btnLogOut.setForeground(Color.WHITE);
			btnLogOut.setBackground(new Color(255, 99, 71));
		}
		return btnLogOut;
	}

	private JButton getBtnAsignarMonitor_1() {
		if (btnAsignarMonitor == null) {
			btnAsignarMonitor = new JButton("Asignar monitor a actividad");
			btnAsignarMonitor.setMnemonic('M');
			btnAsignarMonitor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					openAsignarMonitorActividadWindow();
				}
			});
		}
		return btnAsignarMonitor;
	}

	public void openAsignarMonitorActividadWindow() {
		asignarMonitor = new AsignarMonitorDialog(this);
		asignarMonitor.setModal(true);
		asignarMonitor.setLocationRelativeTo(this);
		asignarMonitor.setVisible(true);
	}

	private JButton getBtnCrearActividad() {
		if (btnCrearActividad == null) {
			btnCrearActividad = new JButton("Crear actividad");
			btnCrearActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CrearActividadDialog dialog = new CrearActividadDialog(parent.getPrograma());
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(AdminWindow.this);
					dialog.setVisible(true);
				}
			});
			btnCrearActividad.setMnemonic('C');
		}
		return btnCrearActividad;
	}

	public MainWindow getParent() {
		return this.parent;
	}

	private JButton getBtnAsignarActividad() {
		if (btnAsignarActividad == null) {
			btnAsignarActividad = new JButton("Asignar actividad");
			btnAsignarActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					getMe().setModal(false);
					CalendarioAdmin ca = new CalendarioAdmin(getMe());
					ca.setModal(true);
					ca.setVisible(true);
				}
			});
			btnAsignarActividad.setMnemonic('A');
		}
		return btnAsignarActividad;
	}

	private AdminWindow getMe() {
		return this;
	}

	public void openAdminReservaSocioWindow() {
		adminReservaSocio = new AdminReservaSocioDialog(this);
		adminReservaSocio.setModal(true);
		adminReservaSocio.setLocationRelativeTo(this);
		adminReservaSocio.setVisible(true);
	}

	private JButton getBtnReservaSocio() {
		if (btnReservaSocio == null) {
			btnReservaSocio = new JButton("Hacer una reserva para un socio en una actividad que se realiza hoy");
			btnReservaSocio.setMnemonic('H');
			btnReservaSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (getParent().getPrograma().checkIfHayActividadesParaHoy()) {
						openAdminReservaSocioWindow();
					} else {
						JOptionPane.showMessageDialog(null, "No hay ninguna actividad planificada para hoy");
					}
				}
			});
		}
		return btnReservaSocio;
	}

	private JButton getBtnVerOcupacion() {
		if (btnVerOcupacion == null) {
			btnVerOcupacion = new JButton("Ver ocupaci\u00F3n");
			btnVerOcupacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VerOcupacionDialog vow = new VerOcupacionDialog(parent.getPrograma());
					vow.setModal(true);
					vow.setLocationRelativeTo(AdminWindow.this);
					vow.setVisible(true);
				}
			});
			btnVerOcupacion.setMnemonic('V');
		}
		return btnVerOcupacion;
	}
	
	private JButton getBtnCerrarInstalación() {
		if (btnCerrarInstalación == null) {
			btnCerrarInstalación = new JButton("Cerrar instalaci\u00F3n");
			btnCerrarInstalación.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CerrarInstalacionDialog cid = new CerrarInstalacionDialog(getMe());
					cid.setLocationRelativeTo(getMe());
					cid.setModal(true);
					cid.setVisible(true);
				}
			});
		}
		return btnCerrarInstalación;
	}

	private JButton getBtnAlquilarSocioMomento() {
		if (btnAlquilarSocioMomento == null) {
			btnAlquilarSocioMomento = new JButton("Alquilarle una instalación en el momento a un socio");
			btnAlquilarSocioMomento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (getParent().getPrograma().checkIfHayInstalacionesLibresParaAhora()) {
						openAlquilarSocioMomentoWindow();
					} else {
						JOptionPane.showMessageDialog(null, "No hay ninguna instalación libre para hoy");
					}
				}
			});
		}
		return btnAlquilarSocioMomento;
	}

	private void openAlquilarSocioMomentoWindow() {
		alquilarSocioMomento = new AdminAlquilaSocioAhoraDialog(this);
		alquilarSocioMomento.setModal(true);
		alquilarSocioMomento.setLocationRelativeTo(this);
		alquilarSocioMomento.setVisible(true);

	}

	private JButton getBtnAlquilarSocio() {
		if (btnAlquilarSocio == null) {
			btnAlquilarSocio = new JButton("Alquilarle una instalaci\u00F3n a un socio");
			btnAlquilarSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					openAlquilarSocioWindow();
				}
			});
		}
		return btnAlquilarSocio;
	}

	private void openAlquilarSocioWindow() {
		calendarioAlquilerAdmin = new CalendarioAdminAlquilar(this);
		calendarioAlquilerAdmin.setModal(true);
		calendarioAlquilerAdmin.setLocationRelativeTo(this);
		calendarioAlquilerAdmin.setVisible(true);
	}

	private JButton getBtnRegistrarEntrada() {
		if (btnRegistrarEntrada == null) {
			btnRegistrarEntrada = new JButton("Registrar entrada al alquiler");
			btnRegistrarEntrada.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (getParent().getPrograma().hayAlquileresAhoraSocioNoHaEntrado()) {
						openRegistrarEntradaSocioWindow();
					} else {
						JOptionPane.showMessageDialog(null,
								"No hay ningún alquiler o los clientes ya se han presentado a los alquileres actuales");
					}
				}
			});
		}
		return btnRegistrarEntrada;
	}

	private void openRegistrarEntradaSocioWindow() {
		registrarEntradaSocio = new RegistrarEntradaSocioDialog(this);
		registrarEntradaSocio.setModal(true);
		registrarEntradaSocio.setLocationRelativeTo(this);
		registrarEntradaSocio.setVisible(true);
	}
	
	private JButton getBtnSociosConImpagos() {
		if (btnSociosConImpagos == null) {
			btnSociosConImpagos = new JButton("Ver socios que no han pagado sus alquileres");
			btnSociosConImpagos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ListaSociosConImpagos lsci = new ListaSociosConImpagos(parent.getPrograma());
					lsci.setLocationRelativeTo(getMe());
					lsci.setModal(true);
					lsci.setVisible(true);
				}
			});
		}
		return btnSociosConImpagos;
	}
	private JButton getBtnCobrarAlquileres() {
		if (btnCobrarAlquileres == null) {
			btnCobrarAlquileres = new JButton("Cobrar alquiler a usuarios");
		}
		return btnCobrarAlquileres;
	}

	private JButton getBtnRegistrarSalida() {
		if (btnRegistrarSalida == null) {
			btnRegistrarSalida = new JButton("Registrar salida del alquiler");
			btnRegistrarSalida.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (getParent().getPrograma().hayAlquileresConSocioDentro()) {
						openRegistrarSalidaSocioWindow();
					} else {
						JOptionPane.showMessageDialog(null,
								"No hay ningún socio en los alquileres de las instalaciones");
					}
				}
			});
		}
		return btnRegistrarSalida;
	}

	private void openRegistrarSalidaSocioWindow() {
		registrarSalidaSocio = new RegistrarSalidaSocioDialog(this);
		registrarSalidaSocio.setModal(true);
		registrarSalidaSocio.setLocationRelativeTo(this);
		registrarSalidaSocio.setVisible(true);
	}
	private JButton getBtnAlquilerTercero() {
		if (btnAlquilerTercero == null) {
			btnAlquilerTercero = new JButton("Alquilar a un tercero");
			btnAlquilerTercero.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String nombre_tercero;
					nombre_tercero = JOptionPane.showInputDialog("Por favor, introduce un nombre");
					boolean foundTercero = false;
					Tercero t = null;
					for(Tercero ter: parent.getPrograma().getTerceros()) {
						if(ter.getNombre().equals(nombre_tercero)) {
							foundTercero = true;
							t = ter;
							break;
						}
					}
					if(!foundTercero) {
						t = new Tercero(nombre_tercero);
						if (t.getNombre() != null && !t.getNombre().isEmpty()) {
							openCalendarioTercero(t);
							try {
								parent.getPrograma().añadirTercero(t);
							} catch (SQLException e) {
								System.out.println("Ha ocurrido un problema añadiendo un nuevo tercero, por favor póngase en contacto con el admin");
							}
						}
						else {
							JOptionPane.showMessageDialog(AdminWindow.this, "Por favor, introduce un nombre de tercero válido ");
						}
					} else if(foundTercero) {
						openCalendarioTercero(t);
					}
				}
			});
		}
		return btnAlquilerTercero;
	}
	
	private void openCalendarioTercero(Tercero t) {
		CalendarioTercero ct = new CalendarioTercero(this, t);
		ct.setLocationRelativeTo(this);
		ct.setModal(true);
		ct.setVisible(true);
	}
	private JButton getBtnVerOcupacionBotones() {
		if (btnVerOcupacionBotones == null) {
			btnVerOcupacionBotones = new JButton("Ver Ocupacion (Botones)");
			btnVerOcupacionBotones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CalendarioSemanalBase vowb = new CalendarioSemanalBase(parent.getPrograma());
					vowb.setLocationRelativeTo(AdminWindow.this);
					vowb.setModal(true);
					vowb.setVisible(true);
				}
			});
		}
		return btnVerOcupacionBotones;
	}
	
	private void openCalendarioSemanalPlanificar() {
		calendarioSemanalPlanificar = new CalendarioSemanalPlanificar(this);
		calendarioSemanalPlanificar.setModal(true);
		calendarioSemanalPlanificar.setLocationRelativeTo(this);
		calendarioSemanalPlanificar.setVisible(true);
	}
	private JButton getBtnPlanificarActividades() {
		if (btnPlanificarActividades == null) {
			btnPlanificarActividades = new JButton("Planificar actividades para varios d\u00EDas");
			btnPlanificarActividades.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					openCalendarioSemanalPlanificar();
				}
			});
		}
		return btnPlanificarActividades;
	}
	
	private JButton getBtnAsignarRecursos() {
		if (btnAsignarRecursos == null) {
			btnAsignarRecursos = new JButton("Asignar recursos a instalacion");
			btnAsignarRecursos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					AsignarRecursosAInstalaciónDialog araid = new AsignarRecursosAInstalaciónDialog(parent.getPrograma());
					araid.setLocationRelativeTo(AdminWindow.this);
					araid.setModal(true);
					araid.setVisible(true);
					
					
				}
			});
		}
		return btnAsignarRecursos;
	}
	
	private JButton getBtnInspeccionarConflictos() {
		if (btnInspeccionarConflictos == null) {
			btnInspeccionarConflictos = new JButton("Inspeccionar Conflictos");
			btnInspeccionarConflictos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VerConflictosDialog vcd = new VerConflictosDialog(parent.getPrograma());
					vcd.setLocationRelativeTo(AdminWindow.this);
					vcd.setModal(true);
					vcd.setVisible(true);
					
					
				}
			});
		}
		return btnInspeccionarConflictos;
	}
}
