package sprint1.ui.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sprint1.business.clases.Programa;
import sprint1.business.clases.Socio;
import sprint1.business.clases.Tercero;
import sprint1.ui.ventanas.administracion.AdminWindow;
import sprint1.ui.ventanas.socio.CbSociosWindow;
import sprint1.ui.ventanas.socio.SocioWindow;
import sprint1.ui.ventanas.tercero.TerceroWindow;

public class MainWindow extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private AdminWindow adminWindow = null;
	private SocioWindow socioWindow = null;
	private TerceroWindow terceroWindow = null;
	

	private static Programa programa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					programa = new Programa();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("Centro de Deportes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 577, 545);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel pnTitulo = new JPanel();
		pnTitulo.setBackground(Color.WHITE);
		contentPane.add(pnTitulo);
		
		JLabel lblIcono = new JLabel("");
		lblIcono.setIcon(new ImageIcon(MainWindow.class.getResource("/sprint1/ui/resources/icono.jpg")));
		pnTitulo.add(lblIcono);
		
		JPanel pnBotones = new JPanel();
		pnBotones.setBackground(Color.WHITE);
		contentPane.add(pnBotones, BorderLayout.SOUTH);
		
		JButton btnAdmin = new JButton("Acceder como administrador");
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openAdminWindow();
			}
		});
		pnBotones.add(btnAdmin);
		
		JButton btnSocio = new JButton("Acceder como socio");
		btnSocio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				String id_socio;
//				id_socio = JOptionPane.showInputDialog("Por favor, introduce un id de socio v�lido ");
//				if (id_socio != null) {
//					if(programa.encontrarSocio(id_socio) != null) {
//						openSocioWindow(programa.encontrarSocio(id_socio)); 
//					} else {
//						JOptionPane.showMessageDialog(MainWindow.this, "Por favor, introduce un id de socio v�lido ");
//					}
//				}
				abrirCbSociosWindowPrueba();
				
			}
		});
		pnBotones.add(btnSocio);
		
		JButton btnTercero = new JButton("Acceder como tercero");
		btnTercero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nombre_tercero;
				nombre_tercero = JOptionPane.showInputDialog("Por favor, introduce un nombre");
				boolean foundTercero = false;
				Tercero t = null;
				for(Tercero ter: programa.getTerceros()) {
					if(ter.getNombre().equals(nombre_tercero)) {
						foundTercero = true;
						t = ter;
						break;
					}
				}
				if(!foundTercero) {
					t = new Tercero(nombre_tercero);
					if (t.getNombre() != null && !t.getNombre().isEmpty()) {
						openTerceroWindow(t);
						try {
							programa.a�adirTercero(t);
						} catch (SQLException e) {
							System.out.println("Ha ocurrido un problema a�adiendo un nuevo tercero, por favor p�ngase en contacto con el admin");
						}
					}
					else {
						JOptionPane.showMessageDialog(MainWindow.this, "Por favor, introduce un id de tercero v�lido ");
					}
				} else if(foundTercero) {
					openTerceroWindow(t);
				}
			}
		});
		pnBotones.add(btnTercero);
	}
	
	public Programa getPrograma() {
		return MainWindow.programa;
	}

	private void openSocioWindow(Socio socio) {
		socioWindow = new SocioWindow(this, socio);
		socioWindow.setModal(true);
		socioWindow.setLocationRelativeTo(this);
		socioWindow.setVisible(true);
	}
	
	private void openTerceroWindow(Tercero tercero) {
		terceroWindow =  new TerceroWindow(this, tercero);
		terceroWindow.setModal(true);
		terceroWindow.setLocationRelativeTo(this);
		terceroWindow.setVisible(true);
	}
	
	private void openAdminWindow() {
		adminWindow = new AdminWindow(this);
		adminWindow.setModal(true);
		adminWindow.setLocationRelativeTo(this);
		adminWindow.setVisible(true);

	}
	

	private void abrirCbSociosWindowPrueba() {
		CbSociosWindow cbSocios = new CbSociosWindow(this);
		cbSocios.setModal(true);
		cbSocios.setLocationRelativeTo(this);
		cbSocios.setVisible(true);
	}

}
