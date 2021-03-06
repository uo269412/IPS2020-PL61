package sprint1.ui.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sprint1.business.Programa;
import sprint1.ui.ventanas.administracion.AdminWindow;
import sprint1.ui.ventanas.socio.util.SeleccionSocioWindow;
import java.awt.Toolkit;

public class MainWindow extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private AdminWindow adminWindow = null;
	

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/sprint1/ui/resources/titulo.png")));
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
		btnAdmin.setBackground(new Color(25, 25, 112));
		btnAdmin.setForeground(Color.WHITE);
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openAdminWindow();
			}
		});
		pnBotones.add(btnAdmin);
		
		JButton btnSocio = new JButton("Acceder como socio");
		btnSocio.setForeground(Color.WHITE);
		btnSocio.setBackground(new Color(25, 25, 112));
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
		
	}
	
	public Programa getPrograma() {
		return MainWindow.programa;
	}
	
	private void openAdminWindow() {
		adminWindow = new AdminWindow(this);
		adminWindow.setModal(true);
		adminWindow.setLocationRelativeTo(this);
		adminWindow.setVisible(true);

	}
	

	private void abrirCbSociosWindowPrueba() {
		SeleccionSocioWindow cbSocios = new SeleccionSocioWindow(this);
		cbSocios.setModal(true);
		cbSocios.setLocationRelativeTo(this);
		cbSocios.setVisible(true);
	}

}
