package sprint1.business.clases;

import java.sql.Connection;
import java.sql.DriverManager;
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

	public Administracion administracion;

	public static String URL = "jdbc:sqlite:C:\\Users\\javie\\Desktop\\master\\sprint1\\resources\\bdProject.db";

	public Programa() throws SQLException {
		administracion = new Administracion();
		cargarBaseDatos();
	}

	public void cargarBaseDatos() throws SQLException {
		try {
			cargarActividades();
			cargarSocios();
			cargarReservas();
			cargarMonitores();
		} catch (SQLException e) {
			System.out.println("Ha surgido un error cargando la base de datos");
		}
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
		return new Actividad(codigo, nombre, horaInicio, horaFin, limitePlazas);

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

//SOCIOS	

	private void cargarSocios() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM SOCIO");
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
		return new Reserva(id_cliente, codigo_actividad, fecha_reserva);
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

//MONITORES

	private void cargarMonitores() {
		// TODO Auto-generated method stub

	}

}
