package sprint1.ui.ventanas.socio.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import sprint1.business.dominio.centroDeportes.alquileres.Alquiler;
import sprint1.business.dominio.centroDeportes.alquileres.util.ComparadorPorDiaAlquiler;
import sprint1.business.dominio.centroDeportes.instalaciones.Instalacion;
import sprint1.business.dominio.clientes.Socio;
import sprint1.ui.ventanas.socio.SocioWindow;
import java.awt.Toolkit;

public class VerAlquileresSocio extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SocioWindow parent;
	private Socio socio;

	private JPanel contentPane;
	private JPanel pnCentro;
	private JPanel pnSur;
	private JButton btnVolver;
	private JPanel pnNorth;
	private JLabel lblTitulo;
	private JPanel pnFiltros;
	private JLabel lblFiltrarPor;
	private JComboBox<String> cbOpcion;

	private List<String> modeloCb;
	private boolean tituloPuesto;
	private Alquiler alquilerPrevio;

	private List<Alquiler> alquileresAñadidos = new ArrayList<Alquiler>();

	/**
	 * Create the frame.
	 */
	public VerAlquileresSocio(SocioWindow parent, Socio socio) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VerAlquileresSocio.class.getResource("/sprint1/ui/resources/titulo.png")));
		setTitle("Centro de deportes: Ver alquileres");
		this.parent = parent;
		this.socio = socio;
		creaModeloComboBox();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 758, 493);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPnCentro(), BorderLayout.CENTER);
		contentPane.add(getPnSur(), BorderLayout.SOUTH);
		contentPane.add(getPnNorth(), BorderLayout.NORTH);
		crearAlquileres((String) cbOpcion.getSelectedItem());
	}
	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setBackground(Color.WHITE);
			pnCentro.setLayout(new BoxLayout(pnCentro, BoxLayout.Y_AXIS));
		}
		return pnCentro;
	}
	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
			pnSur.setBackground(Color.WHITE);
			pnSur.add(getBtnVolver());
		}
		return pnSur;
	}
	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.setBackground(Color.BLUE);
			btnVolver.setForeground(Color.WHITE);
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VerAlquileresSocio.this.dispose();
				}
			});
			btnVolver.setMnemonic('V');
			btnVolver.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return btnVolver;
	}
	private void crearAlquileres(String opcion) {
		pnCentro.removeAll();
		alquileresAñadidos.clear();
		tituloPuesto = false;
		Date hoy = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(hoy);
		int diaDeHoy = cal.get(Calendar.DAY_OF_MONTH);
		int mesActual = cal.get(Calendar.MONTH) + 1;
		int añoActual = cal.get(Calendar.YEAR);
		int hora = cal.get(Calendar.HOUR_OF_DAY);
		List<Alquiler> alquileres = parent.getParent().getPrograma().getAlquileresSocio(socio);
		alquileres.sort(new ComparadorPorDiaAlquiler());
		for (Alquiler a : alquileres) {
			switch (opcion) {
			case "Pasado":
				muestraPasado(hora, diaDeHoy, mesActual, añoActual, a);
				break;
			case "Futuro":
				muestraFuturo(hora, diaDeHoy, mesActual, añoActual, a);
				break;
			case "":
				crearAlquileres((String) cbOpcion.getSelectedItem());
				break;
			}
		}
		if(pnCentro.getComponents().length == 0) {
			JLabel label = new JLabel();
			label.setText("No hay alquileres disponibles");
			pnCentro.add(label);
		}
		pnCentro.repaint();
		pnCentro.validate();
	}

	private void muestraPasado(int hora, int diaDeHoy, int mesActual, int añoActual, Alquiler a) {
		if (alquilerPrevio != null && (a.getDia() != alquilerPrevio.getDia() || a.getMes() != alquilerPrevio.getMes() || a.getAño() != alquilerPrevio.getAño())) {
			tituloPuesto = false;
		}
		if ((a.getAño() < añoActual || a.getAño() == añoActual && a.getMes() < mesActual ||
				a.getAño() == añoActual && a.getMes() == mesActual && a.getDia() < diaDeHoy ||
				a.getAño() == añoActual && a.getMes() == mesActual && a.getDia() == diaDeHoy && a.getHoraInicio() <= hora)) {
			if (!tituloPuesto) {
				añadirLabelTituloDia(a.getDia(), a.getMes(), a.getAño());
				tituloPuesto = true;
			}
			if(!alquileresAñadidos.contains(a))
				añadirLabelAlquiler(a);
		}
		alquilerPrevio = a;
	}
	private void muestraFuturo(int hora, int diaDeHoy, int mesActual, int añoActual, Alquiler a) {
		if (alquilerPrevio != null && (a.getDia() != alquilerPrevio.getDia() || a.getMes() != alquilerPrevio.getMes() || a.getAño() != alquilerPrevio.getAño())) {
			tituloPuesto = false;
		}
		if ((a.getAño() > añoActual || a.getAño() == añoActual && a.getMes() > mesActual ||
				a.getAño() == añoActual && a.getMes() == mesActual && a.getDia() > diaDeHoy ||
				a.getAño() == añoActual && a.getMes() == mesActual && a.getDia() == diaDeHoy && a.getHoraInicio() > hora)) {
			if (!tituloPuesto) {
				añadirLabelTituloDia(a.getDia(), a.getMes(), a.getAño());
				tituloPuesto = true;
			}
			if(!alquileresAñadidos.contains(a))
				añadirLabelAlquiler(a);
		}
		alquilerPrevio = a;
	}
	private void añadirLabelTituloDia(int dia, int mes, int año) {
		String fechaString = dia + "/" + mes + "/" + año;
		Date fecha = null;
		try {
			fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		JLabel lblDia = new JLabel();
		lblDia.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDia.setText("■ " + obtenerDia(cal.get(Calendar.DAY_OF_WEEK)) + " " 
				+ dia + " de " + obtenerMes(mes) + ", " + año + ":");
		pnCentro.add(lblDia);
	}
	
	private void añadirLabelAlquiler(Alquiler a) {
		alquileresAñadidos .add(a);
		JLabel lblActividad = new JLabel();
		String labelText = "    ● Alquiler de ";
		for (Instalacion i : parent.getParent().getPrograma().getInstalaciones()) {
			if (i.getCodigo().equals(a.getId_instalacion()))
				labelText += i.getNombre();
		}
		labelText += " desde " + a.getHoraInicio() + ":00 hasta " + a.getHoraFin() + ":00";
		lblActividad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblActividad.setText(labelText);
		pnCentro.add(lblActividad);
	}

	private String obtenerDia(int numeroDiaSemana) {
		switch (numeroDiaSemana) {
		case Calendar.SUNDAY:
			return "Domingo";
		case Calendar.MONDAY:
			return "Lunes";
		case Calendar.TUESDAY:
			return "Martes";
		case Calendar.WEDNESDAY:
			return "Miércoles";
		case Calendar.THURSDAY:
			return "Jueves";
		case Calendar.FRIDAY:
			return "Viernes";
		case Calendar.SATURDAY:
			return "Sábado";
		}
		return null;
	}
	
	private String obtenerMes(int mes) {
		switch (mes) {
		case 1:
			return "Enero";
		case 2:
			return "Febrero";
		case 3: 
			return "Marzo";
		case 4:
			return "Abril";
		case 5:
			return "Mayo";
		case 6:
			return "Junio";
		case 7:
			return "Julio";
		case 8:
			return "Agosto";
		case 9:
			return "Septiembre";
		case 10:
			return "Octubre";
		case 11:
			return "Noviembre";
		case 12:
			return "Diciembre";
		}
		return null;
	}
	private void creaModeloComboBox() {
		modeloCb = new ArrayList<String>();
		modeloCb.add("Pasado");
		modeloCb.add("Futuro");
	}
	private JPanel getPnNorth() {
		if (pnNorth == null) {
			pnNorth = new JPanel();
			pnNorth.setBackground(Color.WHITE);
			pnNorth.setLayout(new BorderLayout(0, 0));
			pnNorth.add(getLblTitulo_1());
			pnNorth.add(getPnFiltros(), BorderLayout.EAST);
		}
		return pnNorth;
	}
	private JLabel getLblTitulo_1() {
		if (lblTitulo == null) {
			lblTitulo = new JLabel("Alquileres:");
			lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 36));
		}
		return lblTitulo;
	}
	private JPanel getPnFiltros() {
		if (pnFiltros == null) {
			pnFiltros = new JPanel();
			pnFiltros.setBorder(new LineBorder(new Color(0, 0, 0)));
			pnFiltros.setBackground(Color.WHITE);
			pnFiltros.setLayout(new GridLayout(0, 1, 2, 2));
			pnFiltros.add(getLblFiltrarPor());
			pnFiltros.add(getCbOpcion());
		}
		return pnFiltros;
	}
	private JLabel getLblFiltrarPor() {
		if (lblFiltrarPor == null) {
			lblFiltrarPor = new JLabel("Filtrar por:");
			lblFiltrarPor.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblFiltrarPor.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return lblFiltrarPor;
	}
	private JComboBox<String> getCbOpcion() {
		if (cbOpcion == null) {
			cbOpcion = new JComboBox<String>(new DefaultComboBoxModel<String>(modeloCb.toArray(new String[0])));
			cbOpcion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					crearAlquileres((String)cbOpcion.getSelectedItem());
				}
			});
			
		}
		return cbOpcion;
	}
}
