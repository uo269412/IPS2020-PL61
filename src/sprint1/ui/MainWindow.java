package sprint1.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sprint1.business.clases.Programa;
import sprint1.business.clases.Socio;

import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {
	
	//buenas javi

	/**
	 * 
	 */
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
		setTitle("Programa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));

		JButton btnAdmin = new JButton("Acceder como administrador");
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openAdminWindow();
			}
		});
		contentPane.add(btnAdmin);

		JButton btnSocio = new JButton("Acceder como socio");
		btnSocio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id_socio;
				do {
					id_socio = JOptionPane.showInputDialog("Por favor, introduce un id de socio válido ");
					System.out.println(id_socio);
				} while (programa.encontrarSocio(id_socio) == null);
				openSocioWindow(programa.encontrarSocio(id_socio));
			}

		});
		contentPane.add(btnSocio);
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

	private void openSocioWindow(Socio socio) {
		socioWindow = new SocioWindow(this, socio);
		socioWindow.setModal(true);
		socioWindow.setLocationRelativeTo(this);
		socioWindow.setVisible(true);
	}

}
