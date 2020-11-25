package sprint1.ui.ventanas.socio.acciones;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sprint1.business.Programa;
import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;
import sprint1.business.dominio.clientes.Socio;
import sprint1.ui.ventanas.socio.SocioWindow;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

public class ReservaSocioWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTitulo;
	private JScrollPane spActividades;
	private JList<ActividadPlanificada> listActividades;
	private DefaultListModel<ActividadPlanificada> modeloActividades = null;
	private Socio socio;
	private SocioWindow parent;
	private JPanel pnBotones;
	private JButton btnReservar;
	private JButton btnVolver;
	private JPanel pnRecuerda;
	private JLabel lblRecuerda;


	/**
	 * Create the frame.
	 */
	public ReservaSocioWindow(SocioWindow socioWindow, Socio socio) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ReservaSocioWindow.class.getResource("/sprint1/ui/resources/titulo.png")));
		setTitle("Centro de deportes: Reserva de actividad");
		this.parent = socioWindow;
		this.socio = socio;
		setBounds(100, 100, 681, 588);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getLblTitulo(), BorderLayout.NORTH);
		contentPane.add(getSpActividades(), BorderLayout.CENTER);
		contentPane.add(getPnBotones(), BorderLayout.SOUTH);
		cargarActividades();
	}

	private JLabel getLblTitulo() {
		if (lblTitulo == null) {
			lblTitulo = new JLabel("Realizando una reserva para el socio " + socio.getNombre() + " " + socio.getApellido());
		}
		return lblTitulo;
	}
	private JScrollPane getSpActividades() {
		if (spActividades == null) {
			spActividades = new JScrollPane();
			spActividades.setViewportView(getListActividades());
		}
		return spActividades;
	}
	private JList<ActividadPlanificada> getListActividades() {
		if (listActividades == null) {
			modeloActividades = new DefaultListModel<ActividadPlanificada>();
			listActividades = new JList<ActividadPlanificada>(modeloActividades);
			listActividades.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent arg0) {
					btnReservar.setEnabled(true);
				}
			});
			listActividades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return listActividades;
	}
	private void cargarActividades() {
		modeloActividades.clear();
		int fecha[] = getPrograma().obtenerHoraDiaMesAño();
		int hora = fecha[0];
		int dia = fecha[1];
		int mes = fecha[2];
		int año = fecha[3];
		List<ActividadPlanificada> actividadesYaReservadasPorSocio = new ArrayList<ActividadPlanificada>();
		actividadesYaReservadasPorSocio = getPrograma()
				.getActividadesPlanificadasQueHaReservadoSocioEnUnDiaEspecifico(socio, dia, mes, año);
		List<ActividadPlanificada> actividadesDisponibles = getPrograma().getActividadesDisponiblesParaReservaDeSocio(dia, mes, año, hora);
		
		for (ActividadPlanificada actividadPosible : actividadesDisponibles) {
			if (actividadesYaReservadasPorSocio.isEmpty()) {
				modeloActividades.addElement(actividadPosible);
			} else if (!actividadesYaReservadasPorSocio.contains(actividadPosible)) {
				for (ActividadPlanificada actividadYaReservada : actividadesYaReservadasPorSocio) {
					if (!getPrograma().comprobarTiempoActividadesColisiona(actividadPosible,
							actividadYaReservada) && !modeloActividades.contains(actividadPosible)) {
						modeloActividades.addElement(actividadPosible);
					}
					else if (getPrograma().comprobarTiempoActividadesColisiona(actividadPosible,
							actividadYaReservada)){
						break;
					}
				}
			}
		}
		if (modeloActividades.isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"No hay actividades disponibles para reservar por el momento.");
			dispose();
		}
	}
	private Programa getPrograma() {
		return parent.getParent().getPrograma();
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new BoxLayout(pnBotones, BoxLayout.X_AXIS));
			pnBotones.add(getPnRecuerda());
			pnBotones.add(getBtnVolver());
			pnBotones.add(getBtnReservar());
		}
		return pnBotones;
	}
	private JButton getBtnReservar() {
		if (btnReservar == null) {
			btnReservar = new JButton("Reservar");
			btnReservar.setMnemonic('R');
			btnReservar.setEnabled(false);
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int yesNo = JOptionPane.showConfirmDialog(null, "¿Seguro de que reservar para la actividad "
							+ listActividades.getSelectedValue().getCodigoActividad() + " ?");
					if (yesNo == JOptionPane.YES_OPTION) {
						ActividadPlanificada ap = listActividades.getSelectedValue();
						if (getPrograma().añadirReserva(socio, ap)) {
							JOptionPane.showMessageDialog(null, "Se ha realizado la reserva correctamente");
							cargarActividades();
						}
						else {
							JOptionPane.showMessageDialog(ReservaSocioWindow.this, "Ya habías reservado esta actividad anteriormente.",
									"Error en la reserva", JOptionPane.WARNING_MESSAGE);
						}

					}
				}
			});
			btnReservar.setForeground(Color.WHITE);
			btnReservar.setBackground(new Color(0, 128, 0));
		}
		return btnReservar;
	}
	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.setMnemonic('V');
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ReservaSocioWindow.this.dispose();
				}
			});
			btnVolver.setForeground(Color.WHITE);
			btnVolver.setBackground(new Color(30, 144, 255));
			btnVolver.setActionCommand("Cancel");
		}
		return btnVolver;
	}
	private JPanel getPnRecuerda() {
		if (pnRecuerda == null) {
			pnRecuerda = new JPanel();
			pnRecuerda.setLayout(new GridLayout(0, 1, 0, 0));
			pnRecuerda.add(getLblRecuerda());
		}
		return pnRecuerda;
	}
	private JLabel getLblRecuerda() {
		if (lblRecuerda == null) {
			lblRecuerda = new JLabel("<html><body>Recuerda que solo puedes realizar reservas desde un d\u00EDa antes de la actividad hasta una hora antes de su comienzo</body></html>");
			lblRecuerda.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return lblRecuerda;
	}
}
