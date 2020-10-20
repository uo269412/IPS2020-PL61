package sprint1.ui.ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sprint1.business.clases.Programa;
import sprint1.business.clases.Socio;
import sprint1.ui.ventanas.administracion.AdminWindow;
import sprint1.ui.ventanas.socio.SocioWindow;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class MainWindow extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private AdminWindow adminWindow = null;
	private SocioWindow socioWindow = null;

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
		setBounds(100, 100, 551, 541);
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
				String id_socio;
				id_socio = JOptionPane.showInputDialog("Por favor, introduce un id de socio válido ");
				if (id_socio != null) {
					if(programa.encontrarSocio(id_socio) != null) {
						openSocioWindow(programa.encontrarSocio(id_socio)); 
					} else {
						JOptionPane.showMessageDialog(MainWindow.this, "Por favor, introduce un id de socio válido ");
					}
				}
				
			}

		});
		pnBotones.add(btnSocio);
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
	
	private void openAdminWindow() {
		adminWindow = new AdminWindow(this);
		adminWindow.setModal(true);
		adminWindow.setLocationRelativeTo(this);
		adminWindow.setVisible(true);

	}

}
