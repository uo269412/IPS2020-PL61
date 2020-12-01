package sprint1.ui.ventanas.administracion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

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
import sprint1.ui.ventanas.administracion.instalaciones.CerrarEspecificando;
import sprint1.ui.ventanas.administracion.instalaciones.CerrarInstalacionDialog;
import sprint1.ui.ventanas.administracion.instalaciones.CerrarPorDiasDialog;
import sprint1.ui.ventanas.administracion.monitores.AsignarMonitorDialog;
import sprint1.ui.ventanas.administracion.reservas.AdminReservaSocioDialog;
import sprint1.ui.ventanas.administracion.util.CalendarioAdmin;
import sprint1.ui.ventanas.administracion.util.CalendarioAdminAlquilar;
import sprint1.ui.ventanas.administracion.util.CalendarioSemanalCancelar;
import sprint1.ui.ventanas.administracion.util.CalendarioSemanalModificar;
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
	private JPanel pnActividades;
	private JPanel pnMonitores;
	private JPanel pnAlquileres;
	private JPanel pnInstalaciones;
	private JButton btnModificarPlanificacion;
	private JButton btnCerrarFlexibilidadDia;
	private JButton btnCancelaPlanificada;
	private JButton btnCerrarEspecifico;

	/**
	 * Create the dialog.
	 */
	public AdminWindow(MainWindow mainWindow) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminWindow.class.getResource("/sprint1/ui/resources/titulo.png")));
		setTitle("Centro de Deportes: Administrador");
		this.parent = mainWindow;
		setBounds(100, 100, 600, 600);
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
			pnAcciones.setLayout(new GridLayout(0, 1, 0, 0));
			pnAcciones.add(getPnActividades());
			pnAcciones.add(getPnMonitores());
			pnAcciones.add(getPnAlquileres());
			pnAcciones.add(getPnInstalaciones());
		}
		return pnAcciones;
	}

	private JButton getBtnLogOut() {
		if (btnLogOut == null) {
			btnLogOut = new JButton("Cerrar sesi\u00F3n");
			btnLogOut.setMnemonic('s');
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
			btnAsignarMonitor.setForeground(new Color(255, 255, 255));
			btnAsignarMonitor.setBackground(new Color(25, 25, 112));
			btnAsignarMonitor.setMnemonic('m');
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
			btnCrearActividad.setForeground(new Color(255, 255, 255));
			btnCrearActividad.setBackground(new Color(25, 25, 112));
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
			btnAsignarActividad = new JButton("Planificar actividad");
			btnAsignarActividad.setForeground(new Color(255, 255, 255));
			btnAsignarActividad.setBackground(new Color(25, 25, 112));
			btnAsignarActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					getMe().setModal(false);
					CalendarioAdmin ca = new CalendarioAdmin(getMe());
					ca.setModal(true);
					ca.setVisible(true);
				}
			});
			btnAsignarActividad.setMnemonic('P');
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
			btnReservaSocio = new JButton("Reservar plaza en una actividad de hoy");
			btnReservaSocio.setForeground(new Color(255, 255, 255));
			btnReservaSocio.setBackground(new Color(25, 25, 112));
			btnReservaSocio.setMnemonic('R');
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
			btnVerOcupacion.setForeground(new Color(255, 255, 255));
			btnVerOcupacion.setBackground(new Color(25, 25, 112));
			btnVerOcupacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VerOcupacionDialog vow = new VerOcupacionDialog(parent.getPrograma());
					vow.setModal(true);
					vow.setLocationRelativeTo(AdminWindow.this);
					vow.setVisible(true);
				}
			});
			btnVerOcupacion.setMnemonic('o');
		}
		return btnVerOcupacion;
	}
	
	private JButton getBtnCerrarInstalación() {
		if (btnCerrarInstalación == null) {
			btnCerrarInstalación = new JButton("Cerrar instalaci\u00F3n indefinidamente");
			btnCerrarInstalación.setForeground(new Color(255, 255, 255));
			btnCerrarInstalación.setBackground(new Color(25, 25, 112));
			btnCerrarInstalación.setMnemonic('l');
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
			btnAlquilarSocioMomento = new JButton("Alquilar una instalaci\u00F3n ahora a un socio");
			btnAlquilarSocioMomento.setForeground(new Color(255, 255, 255));
			btnAlquilarSocioMomento.setBackground(new Color(25, 25, 112));
			btnAlquilarSocioMomento.setMnemonic('h');
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
			btnAlquilarSocio = new JButton("Alquilar a un socio");
			btnAlquilarSocio.setForeground(new Color(255, 255, 255));
			btnAlquilarSocio.setBackground(new Color(25, 25, 112));
			btnAlquilarSocio.setMnemonic('q');
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
			btnRegistrarEntrada.setForeground(new Color(255, 255, 255));
			btnRegistrarEntrada.setBackground(new Color(25, 25, 112));
			btnRegistrarEntrada.setMnemonic('e');
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
			btnSociosConImpagos.setForeground(new Color(255, 255, 255));
			btnSociosConImpagos.setBackground(new Color(25, 25, 112));
			btnSociosConImpagos.setMnemonic('n');
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
			btnCobrarAlquileres.setForeground(new Color(255, 255, 255));
			btnCobrarAlquileres.setBackground(new Color(25, 25, 112));
			btnCobrarAlquileres.setMnemonic('b');
		}
		return btnCobrarAlquileres;
	}

	private JButton getBtnRegistrarSalida() {
		if (btnRegistrarSalida == null) {
			btnRegistrarSalida = new JButton("Registrar salida del alquiler");
			btnRegistrarSalida.setForeground(new Color(255, 255, 255));
			btnRegistrarSalida.setBackground(new Color(25, 25, 112));
			btnRegistrarSalida.setMnemonic('s');
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
			btnAlquilerTercero.setForeground(new Color(255, 255, 255));
			btnAlquilerTercero.setBackground(new Color(25, 25, 112));
			btnAlquilerTercero.setMnemonic('t');
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
			btnVerOcupacionBotones = new JButton("Ver ocupaci\u00F3n semanal");
			btnVerOcupacionBotones.setForeground(new Color(255, 255, 255));
			btnVerOcupacionBotones.setBackground(new Color(25, 25, 112));
			btnVerOcupacionBotones.setMnemonic('o');
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
			btnPlanificarActividades.setForeground(new Color(255, 255, 255));
			btnPlanificarActividades.setBackground(new Color(25, 25, 112));
			btnPlanificarActividades.setMnemonic('a');
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
			btnAsignarRecursos = new JButton("Asignar recursos a instalaci\u00F3n");
			btnAsignarRecursos.setForeground(new Color(255, 255, 255));
			btnAsignarRecursos.setBackground(new Color(25, 25, 112));
			btnAsignarRecursos.setMnemonic('g');
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
			btnInspeccionarConflictos = new JButton("Inspeccionar conflictos");
			btnInspeccionarConflictos.setForeground(new Color(255, 255, 255));
			btnInspeccionarConflictos.setBackground(new Color(25, 25, 112));
			btnInspeccionarConflictos.setMnemonic('I');
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
	private JPanel getPnActividades() {
		if (pnActividades == null) {
			pnActividades = new JPanel();
			pnActividades.setBackground(new Color(255, 255, 255));
			pnActividades.setBorder(new TitledBorder(null, "Administrar actividades", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnActividades.add(getBtnCrearActividad());
			pnActividades.add(getBtnAsignarActividad());
			pnActividades.add(getBtnPlanificarActividades());
			pnActividades.add(getBtnModificarPlanificacion());
		}
		return pnActividades;
	}
	private JPanel getPnMonitores() {
		if (pnMonitores == null) {
			pnMonitores = new JPanel();
			pnMonitores.setBackground(new Color(255, 255, 255));
			pnMonitores.setBorder(new TitledBorder(null, "Administrar planificaciones y reservas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnMonitores.add(getBtnAsignarMonitor_1());
			pnMonitores.add(getBtnReservaSocio());
			pnMonitores.add(getBtnCancelaPlanificada());
		}
		return pnMonitores;
	}
	private JPanel getPnAlquileres() {
		if (pnAlquileres == null) {
			pnAlquileres = new JPanel();
			pnAlquileres.setBackground(new Color(255, 255, 255));
			pnAlquileres.setBorder(new TitledBorder(null, "Administrar alquileres", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnAlquileres.add(getBtnAlquilarSocio());
			pnAlquileres.add(getBtnAlquilarSocioMomento());
			pnAlquileres.add(getBtnAlquilerTercero());
			pnAlquileres.add(getBtnRegistrarEntrada());
			pnAlquileres.add(getBtnRegistrarSalida());
			pnAlquileres.add(getBtnSociosConImpagos());
			pnAlquileres.add(getBtnCobrarAlquileres());
		}
		return pnAlquileres;
	}
	private JPanel getPnInstalaciones() {
		if (pnInstalaciones == null) {
			pnInstalaciones = new JPanel();
			pnInstalaciones.setBackground(new Color(255, 255, 255));
			pnInstalaciones.setBorder(new TitledBorder(null, "Administrar instalaciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnInstalaciones.add(getBtnAsignarRecursos());
			pnInstalaciones.add(getBtnCerrarInstalación());
			pnInstalaciones.add(getBtnCerrarFlexibilidadDia());
			pnInstalaciones.add(getBtnCerrarEspecifico());
			pnInstalaciones.add(getBtnInspeccionarConflictos());
			pnInstalaciones.add(getBtnVerOcupacion());
			pnInstalaciones.add(getBtnVerOcupacionBotones());
		}
		return pnInstalaciones;
	}
	private JButton getBtnModificarPlanificacion() {
		if (btnModificarPlanificacion == null) {
			btnModificarPlanificacion = new JButton("Modificar planificacion de actividades");
			btnModificarPlanificacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CalendarioSemanalModificar csm = new CalendarioSemanalModificar(getMe());
					csm.setModal(true);
					csm.setVisible(true);
					csm.setLocationRelativeTo(getMe());
				}
			});
			btnModificarPlanificacion.setBackground(new Color(25, 25, 112));
			btnModificarPlanificacion.setForeground(new Color(255, 255, 255));
		}
		return btnModificarPlanificacion;
	}
	private JButton getBtnCerrarFlexibilidadDia() {
		if (btnCerrarFlexibilidadDia == null) {
			btnCerrarFlexibilidadDia = new JButton("Cerrar instalaci\u00F3n d\u00EDas espec\u00EDficos");
			btnCerrarFlexibilidadDia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CerrarPorDiasDialog cpd = new CerrarPorDiasDialog(parent.getPrograma());
					cpd.setModal(true);
					cpd.setLocationRelativeTo(AdminWindow.this);
					cpd.setVisible(true);
				}
			});
			btnCerrarFlexibilidadDia.setBackground(new Color(25, 25, 112));
			btnCerrarFlexibilidadDia.setForeground(new Color(255, 255, 255));
		}
		return btnCerrarFlexibilidadDia;
	}
	private JButton getBtnCancelaPlanificada() {
		if (btnCancelaPlanificada == null) {
			btnCancelaPlanificada = new JButton("Cancelar actividad planificada");
			btnCancelaPlanificada.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CalendarioSemanalCancelar csm = new CalendarioSemanalCancelar(AdminWindow.this);
					csm.setModal(true);
					csm.setLocationRelativeTo(AdminWindow.this);
					csm.setVisible(true);
				}
			});
			btnCancelaPlanificada.setMnemonic('R');
			btnCancelaPlanificada.setForeground(Color.WHITE);
			btnCancelaPlanificada.setBackground(new Color(25, 25, 112));
		}
		return btnCancelaPlanificada;
	}
	private JButton getBtnCerrarEspecifico() {
		if (btnCerrarEspecifico == null) {
			btnCerrarEspecifico = new JButton("Cerrar instalaci\u00F3n para actividades o alquileres");
			btnCerrarEspecifico.setBackground(new Color(25, 25, 112));
			btnCerrarEspecifico.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CerrarEspecificando ce = new CerrarEspecificando(parent.getPrograma());
					ce.setLocationRelativeTo(getMe());
					ce.setModal(true);
					ce.setVisible(true);
				}
			});
			btnCerrarEspecifico.setForeground(Color.WHITE);
		}
		return btnCerrarEspecifico;
	}
}
