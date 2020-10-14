package sprint1.business.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Programa {

	private List<Actividad> actividades;
	private List<ActividadPlanificada> actividadesPlanificadas;
	private List<Socio> socios;
	private List<Reserva> reservas;
	private List<Monitor> monitores;

<<<<<<< HEAD
	//Conexi�n Javi
	//public static String URL = "jdbc:sqlite:C:\\Users\\javie\\Desktop\\master\\sprint1\\resources\\bdProject.db";
	
	//Conexi�n Dani
	//public static String URL = "jdbc:sqlite:C:\\Users\\Dani\\git\\IPS2020-PL61\\resources\\bdProject.db";
	
	//Conexi�n Juan.elo
	public static String URL = "jdbc:sqlite:C:\\Users\\Usuario\\git\\IPS2020-PL61\\resources\\bdProject.db";
	
=======
	// Conexi�n Javi
	public static String URL = "jdbc:sqlite:C:\\Users\\javie\\Desktop\\master\\sprint1\\resources\\bdProject.db";

	// Conexi�n Dani
	// public static String URL =
	// "jdbc:sqlite:C:\\Users\\Dani\\git\\IPS2020-PL61\\resources\\bdProject.db";

>>>>>>> branch 'master' of https://github.com/uo269412/IPS2020-PL61
	public Programa() throws SQLException {
		cargarBaseDatos();
	}

	public void cargarBaseDatos() throws SQLException {
		try {
			cargarActividades();
			cargarSocios();
			cargarReservas();
			cargarMonitores();
			cargarActividadesPlanificadas();
			printAllLists();
		} catch (SQLException e) {
			System.out.println("Ha surgido un error cargando la base de datos");
		}
	}

	private void printAllLists() {
		printActividades();
		printActividadesPlanificadas();
		printSocios();
		printReservas();
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
		PreparedStatement pst = con.prepareStatement("INSERT INTO ACTIVIDAD " + "VALUES (?,?,?)");
		pst.setString(1, a.getCodigo());
		pst.setString(2, a.getNombre());
		pst.setInt(3, a.getIntensidad());
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
		int intensidad = rs.getInt(3);
		return new Actividad(codigo, nombre, intensidad);

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

//ACTIVIDADES PLANIFICADAS	

	private void cargarActividadesPlanificadas() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM ACTIVIDAD_PLANIFICADA");
		convertirActividadesPlanificadasEnLista(rs);
		rs.close();
		st.close();
		con.close();

	}

	private void convertirActividadesPlanificadasEnLista(ResultSet rs) throws SQLException {
		this.actividadesPlanificadas = new ArrayList<>();
		while (rs.next()) {
			actividadesPlanificadas.add(convertirActividadPlanificada(rs));
		}
	}

	private ActividadPlanificada convertirActividadPlanificada(ResultSet rs) throws SQLException {
		String codigoActividad = rs.getString(1);
		int dia = rs.getInt(2);
		int mes = rs.getInt(3);
		int a�o = rs.getInt(4);
		int limitePlazas = rs.getInt(5);
		int horaInicio = rs.getInt(6);
		int horaFin = rs.getInt(7);
		String codigoMonitor = rs.getString(8);
		String codigoPlanificada = rs.getString(9);
		return new ActividadPlanificada(codigoActividad, dia, mes, a�o, limitePlazas, horaInicio, horaFin,
				codigoMonitor, codigoPlanificada);
	}

	public List<ActividadPlanificada> getActividadesPlanificadas() {
		return actividadesPlanificadas;
	}

	public ActividadPlanificada encontrarActividadPlanificada(String codigo) {
		for (ActividadPlanificada actividad : actividadesPlanificadas) {
			if (actividad.getCodigoPlanificada().equals(codigo)) {
				return actividad;
			}
		}
		return null;
	}

	public void printActividadesPlanificadas() {
		System.out.println("Lista de actividades planificadas");
		for (ActividadPlanificada actividad : actividadesPlanificadas) {
			System.out.println(actividad.toString());
		}
	}

	public List<ActividadPlanificada> getActividadesPlanificadas(int dia, int mes, int a�o) {
		List<ActividadPlanificada> listaSort = new ArrayList<ActividadPlanificada>();
		for (ActividadPlanificada ap : getActividadesPlanificadas()) {
			if (ap.getDia() == dia && ap.getMes() == mes && ap.getA�o() == a�o) {
				listaSort.add(ap);
			}
		}
		return listaSort;
	}

	public boolean comprobarTiempoActividadesColisiona(ActividadPlanificada actividadSeleccionada,
			ActividadPlanificada actividad) {
//		return ((actividadSeleccionada.getHoraInicio() == actividad.getHoraInicio())
//				&& (actividadSeleccionada.getHoraFin() == actividad.getHoraFin()));
		return ((actividadSeleccionada.getHoraInicio() == actividad.getHoraInicio())
				|| (actividadSeleccionada.getHoraFin() == actividad.getHoraFin())
				|| ((actividadSeleccionada.getHoraInicio() < actividad.getHoraInicio())
						&& (actividadSeleccionada.getHoraFin() < actividad.getHoraFin())
						&& (actividadSeleccionada.getHoraFin() > actividad.getHoraInicio()))
				|| ((actividadSeleccionada.getHoraInicio() > actividad.getHoraInicio())
						&& (actividadSeleccionada.getHoraFin() > actividad.getHoraFin())
						&& (actividadSeleccionada.getHoraFin() < actividad.getHoraInicio()))
				|| ((actividadSeleccionada.getHoraInicio() < actividad.getHoraInicio())
						&& (actividadSeleccionada.getHoraFin() > actividad.getHoraFin()))
				|| ((actividadSeleccionada.getHoraInicio() > actividad.getHoraInicio())
						&& (actividadSeleccionada.getHoraFin() < actividad.getHoraFin())));
	}

	public void actualizarPlazasActividadPlanificada(ActividadPlanificada actividad, int incremento) {
		try {
			Connection con = DriverManager.getConnection(Programa.URL);
			PreparedStatement pst = con
					.prepareStatement("UPDATE ACTIVIDAD_PLANIFICADA SET limitePlazas = ? WHERE codigoPlanificada = ?");
			pst.setInt(1, incremento);
			pst.setString(2, actividad.getCodigoPlanificada());
			pst.execute();
			pst.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error borrando la reserva");
		}
	}

	private boolean validarHora(ActividadPlanificada actividad, int hora, int dia, int mes, int a�o) {
//		return hora < actividad.getHoraInicio();
		return true;
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

	public List<ActividadPlanificada> getActividadesPlanificadasQueHaReservadoSocio(Socio socio) {
		List<ActividadPlanificada> actividadesQueYaTieneReservadasElSocio = new ArrayList<ActividadPlanificada>();
		for (Reserva reserva : getReservas()) {
			if (reserva.getId_cliente().equals(socio.getId_cliente())) {
				for (ActividadPlanificada ap : getActividadesPlanificadas()) {
					if (reserva.getCodigo_actividad().equals(ap.getCodigoPlanificada())) {
						actividadesQueYaTieneReservadasElSocio.add(ap);
					}
				}
			}
		}
		return actividadesQueYaTieneReservadasElSocio;
	}

	public List<ActividadPlanificada> getActividadesPlanificadasQueHaReservadoSocioEnUnDiaEspecifico(Socio socio,
			int dia, int mes, int a�o) {
		List<ActividadPlanificada> actividadesQueYaTieneReservadasElSocio = new ArrayList<ActividadPlanificada>();
		for (Reserva reserva : getReservas()) {
			if (reserva.getId_cliente().equals(socio.getId_cliente())) {
				for (ActividadPlanificada ap : getActividadesPlanificadas()) {
					if (ap.getDia() == dia && ap.getMes() == mes && ap.getA�o() == a�o
							&& reserva.getCodigo_actividad().equals(ap.getCodigoPlanificada())) {
						actividadesQueYaTieneReservadasElSocio.add(ap);
					}
				}
			}
		}
		return actividadesQueYaTieneReservadasElSocio;
	}

//RESERVAS	

	public void cargarReservas() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM RESERVA");
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
		return new Reserva(id_cliente, codigo_actividad);
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

	public void eliminarReserva(String codigoActividad) {
		for (Reserva reserva : reservas) {
			if (reserva.getCodigo_actividad().equals(codigoActividad)) {
				reservas.remove(reserva);
			}
		}
	}

	public void addReserva(String id_cliente, String codigoPlanificada) {
		reservas.add(new Reserva(id_cliente, codigoPlanificada));
	}

	public void anularReserva(Socio socio, ActividadPlanificada actividad, List<Reserva> reservas) {
		Calendar calendar = Calendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH)+1;
		int a�o = calendar.get(Calendar.YEAR);
		for (Reserva reserva : reservas) {
			if (reserva.getCodigo_actividad().equals(actividad.getCodigoPlanificada())
					&& validarHora(actividad, hora, dia, mes, a�o)) {
				cancelarPlazaReserva(socio.getId_cliente(), actividad.getCodigoPlanificada());
				actualizarPlazasActividadPlanificada(actividad, +1);
			}
		}
	}

	public void a�adirReserva(String id_cliente, String codigoPlanificada) {
		try {
			Connection con = DriverManager.getConnection(Programa.URL);
			PreparedStatement pst = con
					.prepareStatement("INSERT INTO RESERVA (id_cliente, codigo_actividad) VALUES(?,?)");
			pst.setString(1, id_cliente);
			pst.setString(2, codigoPlanificada);
			pst.execute();
			pst.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error a�adiendo la reserva");
		}
	}

	private void cancelarPlazaReserva(String id_cliente, String id_actividad) {
		try {
			Connection con = DriverManager.getConnection(Programa.URL);
			PreparedStatement pst = con
					.prepareStatement("DELETE FROM RESERVA WHERE id_cliente = ? AND codigo_actividad = ?");
			pst.setString(1, id_cliente);
			pst.setString(2, id_actividad);
			pst.execute();
			pst.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error borrando la reserva");
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

	public List<ActividadPlanificada> getActividadesPlanificadasDia(int dia, int mes, int a�o) throws SQLException {
		List<ActividadPlanificada> actividadesDia = new ArrayList<>();

		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement(
				"SELECT codigoPlanificada FROM actividad_planificada WHERE dia = ? AND mes = ? AND a�o = ?");
		pst.setInt(1, dia);
		pst.setInt(2, mes);
		pst.setInt(3, a�o);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			actividadesDia.add(encontrarActividadPlanificada(rs.getString(1)));
		}

		return actividadesDia;
	}

	public void eliminarActividadPlanificada(ActividadPlanificada a) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement("DELETE FROM actividad_planificada WHERE codigoPlanificada = ?");

		pst.setString(1, a.getCodigoPlanificada());
		pst.execute();
	}

//ADMINISTRACI�N

	public void asignarMonitorActividad(Monitor monitor, ActividadPlanificada actividadPlanificada) {
		String codigoMonitor = monitor.getCodigoMonitor();
		String codigoActividadPlanificada = actividadPlanificada.getCodigoPlanificada();
		System.out.println(codigoMonitor);
		System.out.println(codigoActividadPlanificada);
		try {
			Connection con = DriverManager.getConnection(URL);
			PreparedStatement pst = con
					.prepareStatement("UPDATE ACTIVIDAD_PLANIFICADA SET codigoMonitor = ? WHERE codigoPlanificada = ?");
			pst.setString(1, codigoMonitor);
			pst.setString(2, codigoActividadPlanificada);
			pst.execute();
			pst.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error asignando monitor");
			encontrarActividadPlanificada(codigoActividadPlanificada).setCodigoMonitor(codigoMonitor);
		}
	}

	private boolean checkActividadNoTieneMonitor(String codigoActividadPlanificada) {
		for (ActividadPlanificada actividad : actividadesPlanificadas) {
			if (actividad.getCodigoPlanificada().equals(codigoActividadPlanificada)
					&& !actividad.getCodigoMonitor().isEmpty()) {
				return false;
			}
		}
		return true;

	}

	private boolean checkMonitorNoTieneActividad(String codigoMonitor) {
		for (Monitor monitor : monitores) {
			for (ActividadPlanificada actividad : actividadesPlanificadas) {
				if (actividad.getCodigoMonitor().equals(monitor.getCodigoMonitor())) {
					return false;
				}
			}
		}
		return true;
	}

	// UTIL

	public int[] obtenerHoraDiaMesA�o() {
		Calendar calendar = Calendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH)+1;
		int a�o = calendar.get(Calendar.YEAR);
		int[] fecha = { hora, dia, mes, a�o };
		return fecha;
	}

}
