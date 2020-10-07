package sprint1.business.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Programa {

	private List<Actividad> actividades;
	private List<Socio> socios;
	private List<Reserva> reservas;
	private List<Monitor> monitores;

	// Conexión Javi
	public static String URL = "jdbc:sqlite:C:\\Users\\javie\\Desktop\\master\\sprint1\\resources\\bdProject.db";

	// Conexión Dani
	// public static String URL =
	// "jdbc:sqlite:C:\\Users\\Dani\\git\\IPS2020-PL61\\resources\\bdProject.db";

	public Programa() throws SQLException {
		cargarBaseDatos();
	}

	public void cargarBaseDatos() throws SQLException {
		try {
			cargarActividades();
			cargarSocios();
//			cargarReservas();  //No funciona
			cargarMonitores();
			printAllLists();
		} catch (SQLException e) {
			System.out.println("Ha surgido un error cargando la base de datos");
		}
	}

	private void printAllLists() {
		printActividades();
		printSocios();
//		printReservas(); //No funciona
		printMonitores();

	}

//ACTIVIDADES	

	private void cargarActividades() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM ACTIVIDAD");
		convertirActividadesEnLista(rs);
		rs.close();
		st.close();
		con.close();

	}

	public void insertarActividad(Actividad a) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement("INSERT INTO ACTIVIDAD " + "VALUES (?,?,?,?,?,?)");
		pst.setString(1, a.getCodigo());
		pst.setString(2, a.getNombre());
		pst.setInt(3, a.getHoraInicio());
		pst.setInt(4, a.getHoraFin());
		pst.setInt(5, a.getLimitePlazas());
		pst.setInt(6, a.getIntensidad());

		pst.execute();
		pst.close();
		con.close();
		actividades.add(a);
	}

	private void convertirActividadesEnLista(ResultSet rs) throws SQLException {
		this.actividades = new ArrayList<>();
		while (rs.next()) {
			actividades.add(convertirActividad(rs));
		}
	}

	private Actividad convertirActividad(ResultSet rs) throws SQLException {
		String codigo = rs.getString(1);
		String nombre = rs.getString(2);
		int horaInicio = rs.getInt(3);
		int horaFin = rs.getInt(4);
		int limitePlazas = rs.getInt(5);
		int intensidad = rs.getInt(6);
		return new Actividad(codigo, nombre, horaInicio, horaFin, limitePlazas, intensidad);

	}

	public List<Actividad> getActividades() {
		return actividades;
	}

	public Actividad encontrarActividad(String codigo) {
		for (Actividad actividad : actividades) {
			if (actividad.getCodigo().equals(codigo)) {
				return actividad;
			}
		}
		return null;
	}

	public void printActividades() {
		System.out.println("Lista de actividades");
		for (Actividad actividad : actividades) {
			System.out.println(actividad.toString());
		}
	}

//SOCIOS	

	private void cargarSocios() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM CLIENTE");
		convertirSociosEnLista(rs);
		rs.close();
		st.close();
		con.close();
	}

	private void convertirSociosEnLista(ResultSet rs) throws SQLException {
		this.socios = new ArrayList<>();
		while (rs.next()) {
			socios.add(convertirSocio(rs));
		}

	}

	private Socio convertirSocio(ResultSet rs) throws SQLException {
		String id_cliente = rs.getString(1);
		String nombre = rs.getString(2);
		String apellido = rs.getString(3);
		return new Socio(id_cliente, nombre, apellido);
	}

	public List<Socio> getSocios() {
		return socios;
	}

	public Socio encontrarSocio(String id_cliente) {
		for (Socio socio : socios) {
			if (socio.getId_cliente().equals(id_cliente)) {
				return socio;
			}
		}
		return null;
	}

	public void printSocios() {
		System.out.println("Lista de socios");
		for (Socio socio : socios) {
			System.out.println(socio.toString());
		}
	}

//RESERVAS	

	private void cargarReservas() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM RESERVAS");
		convertirReservasEnLista(rs);
		rs.close();
		st.close();
		con.close();

	}

	private void convertirReservasEnLista(ResultSet rs) throws SQLException {
		this.reservas = new ArrayList<>();
		while (rs.next()) {
			reservas.add(convertirReserva(rs));
		}

	}

	private Reserva convertirReserva(ResultSet rs) throws SQLException {
		String id_cliente = rs.getString(1);
		String codigo_actividad = rs.getString(2);
		Date fecha_reserva = rs.getDate(3);
		String codigo_monitor = rs.getString(4);
		return new Reserva(id_cliente, codigo_actividad, fecha_reserva, codigo_monitor);
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	private void printReservas() {
		System.out.println("Lista de reservas");
		for (Reserva reserva : reservas) {
			System.out.println(reserva.toString());
		}
	}

//MONITORES

	private void cargarMonitores() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM MONITOR");
		convertirMonitoresEnLista(rs);
		rs.close();
		st.close();
		con.close();
	}

	private void convertirMonitoresEnLista(ResultSet rs) throws SQLException {
		this.monitores = new ArrayList<>();
		while (rs.next()) {
			monitores.add(convertirMonitor(rs));
		}
	}

	private Monitor convertirMonitor(ResultSet rs) throws SQLException {
		String codigoMonitor = rs.getString(1);
		String nombre = rs.getString(2);
		String apellido = rs.getString(3);
		return new Monitor(codigoMonitor, nombre, apellido);
	}

	public List<Monitor> getMonitores() {
		return monitores;
	}

	private void printMonitores() {
		System.out.println("Lista de monitores");
		for (Monitor monitor : monitores) {
			System.out.println(monitor.toString());
		}
	}

	public Monitor encontrarMonitor(String codigoMonitor) {
		for (Monitor monitor : monitores) {
			if (monitor.getCodigoMonitor().equals(codigoMonitor)) {
				return monitor;
			}
		}
		return null;
	}

//ADMINISTRACIÓN

	public void asignarMonitorActividad(String codigoMonitor, String codigoActividad) {
		if (checkMonitorNoTieneActividad(codigoMonitor) && checkActividadNoTieneMonitor(codigoActividad)) {
			try {
				Connection con = DriverManager.getConnection(URL);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(
						"UPDATE ACTIVIDAD SET codigoMonitor = " + codigoMonitor + " WHERE codigo = " + codigoActividad);
				rs.close();
				st.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("Error asignando monitor");
			}
			encontrarActividad(codigoActividad).setCodigoMonitor(codigoMonitor);
		}
	}

	private boolean checkActividadNoTieneMonitor(String codigoActividad) {
		for (Actividad actividad : actividades) {
			if (actividad.getCodigo().equals(codigoActividad) && !actividad.getCodigoMonitor().isEmpty()) {
				return false;
			}
		}
		return true;

	}

	private boolean checkMonitorNoTieneActividad(String codigoMonitor) {
		for (Monitor monitor : monitores) {
			for (Actividad actividad : actividades) {
				if (actividad.getCodigoMonitor().equals(monitor.getCodigoMonitor())) {
					return false;
				}
			}

		}
		return true;
	}

}
