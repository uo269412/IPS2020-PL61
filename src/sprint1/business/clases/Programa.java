package sprint1.business.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Programa {

	private List<Actividad> actividades;
	private List<ActividadPlanificada> actividadesPlanificadas;
	private List<Socio> socios;
	private List<Reserva> reservas;
	private List<Monitor> monitores;
	private List<Instalacion> instalaciones;
	private List<Recurso> recursos;
	private List<Tercero> terceros;
	private List<Alquiler> alquileres;
	private List<Registro> registros;

	// Conexión Javi
	//public static String URL = "jdbc:sqlite:C:\\Users\\javie\\git\\IPS2020-PL61\\resources\\bdProject.db";

	// Conexión Dani
	 public static String URL =
	 "jdbc:sqlite:C:\\Users\\Dani\\git\\IPS2020-PL61_sprint2\\resources\\bdProject.db";

	// Conexión Juan.elo
	// public static String URL =
	// "jdbc:sqlite:C:\\Users\\Usuario\\git\\IPS2020-PL61\\resources\\bdProject.db";

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
			cargarInstalaciones();
			cargarRecursos();
			cargarTerceros();
			cargarAlquileres();
			cargarRegistros();
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
		printInstalaciones();
		printRecursos();
		printTerceros();
		printAlquileres();
		printRegistros();
	}

//ACTIVIDADES	

	private void cargarActividades() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM ACTIVIDAD");

		convertirActividadesEnLista(rs);
		ordenarActividades();
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

	public void ordenarActividades() {
		Collections.sort(this.actividades);
	}

	public List<Actividad> actividadesPorInstalacion(String codigo_instalacion) {
		List<Actividad> listaActividades = new LinkedList<>();
		for(Actividad a: getActividades()) {
			if(!a.requiresRecursos()) {
				listaActividades.add(a);
			} else {
				boolean contieneTodos = true;
				for(Recurso r: a.getRecursos()) {
					if(r.getInstalacion().equals(codigo_instalacion)) {
						contieneTodos = contieneTodos && true;
					} else {
						contieneTodos = contieneTodos && false;
					}
				}
				if(contieneTodos) {
					listaActividades.add(a);
				}
			}
		}
		
		return listaActividades;
	}
//ACTIVIDADES PLANIFICADAS	

	private void cargarActividadesPlanificadas() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM ACTIVIDAD_PLANIFICADA");
		convertirActividadesPlanificadasEnLista(rs);
		ordenarActividadesPlanificadasPorFecha();
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
		int año = rs.getInt(4);
		int limitePlazas = rs.getInt(5);
		int horaInicio = rs.getInt(6);
		int horaFin = rs.getInt(7);
		String codigoMonitor = rs.getString(8);
		String codigoPlanificada = rs.getString(9);
		String codigoInstalacion = rs.getString(10);
		return new ActividadPlanificada(codigoActividad, dia, mes, año, limitePlazas, horaInicio, horaFin,
				codigoMonitor, codigoPlanificada, codigoInstalacion);
	}

	public List<ActividadPlanificada> getActividadesPlanificadas() {
		return actividadesPlanificadas;
	}

	public List<ActividadPlanificada> getActividadesPlanificadasInstalacionDia(String idInstalacion, int dia, int mes, int año) {
		List<ActividadPlanificada> planificadasSinFiltrar = getActividadesPlanificadas(dia, mes, año);
		List<ActividadPlanificada> toRet = new LinkedList<>();
		for(ActividadPlanificada a: planificadasSinFiltrar) {
			if(a.getCodigoInstalacion().equals(idInstalacion)) {
				toRet.add(a);
			}
		}
		
		return toRet;
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

	public List<ActividadPlanificada> getActividadesPlanificadas(int dia, int mes, int año) {
		List<ActividadPlanificada> listaSort = new ArrayList<ActividadPlanificada>();
		for (ActividadPlanificada ap : getActividadesPlanificadas()) {
			if (ap.getDia() == dia && ap.getMes() == mes && ap.getAño() == año) {
				listaSort.add(ap);
			}
		}
		return listaSort;
	}

	public boolean comprobarTiempoActividadesColisiona(ActividadPlanificada actividadSeleccionada,
			ActividadPlanificada actividad) {
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

	public void ordenarActividadesPlanificadasPorFecha() {
		Collections.sort(this.actividadesPlanificadas);
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

	public void añadirActividadPlanificada(ActividadPlanificada a) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con
				.prepareStatement("INSERT INTO actividad_planificada" + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		pst.setString(1, a.getCodigoActividad());
		pst.setInt(2, a.getDia());
		pst.setInt(3, a.getMes());
		pst.setInt(4, a.getAño());
		pst.setInt(5, a.getLimitePlazas());
		pst.setInt(6, a.getHoraInicio());
		pst.setInt(7, a.getHoraFin());
		pst.setString(8, a.getCodigoMonitor());
		pst.setString(9, a.getCodigoPlanificada());
		pst.setString(10, a.getCodigoInstalacion());
		pst.executeUpdate();

		actividadesPlanificadas.add(a);
		printActividadesPlanificadas();

	}

	public void updateActividadPlanificada(ActividadPlanificada a) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement(
				"UPDATE actividad_planificada" + " SET codigoActividad = ?, dia = ?, mes = ?, año = ?, "
						+ "limitePlazas = ?, horaInicio = ?, horaFin = ?, "
						+ "codigoMonitor = ?, codigo_instalacion = ? WHERE codigoPlanificada = ?");
		pst.setString(1, a.getCodigoActividad());
		pst.setInt(2, a.getDia());
		pst.setInt(3, a.getMes());
		pst.setInt(4, a.getAño());
		pst.setInt(5, a.getLimitePlazas());
		pst.setInt(6, a.getHoraInicio());
		pst.setInt(7, a.getHoraFin());
		pst.setString(8, a.getCodigoMonitor());
		pst.setString(9, a.getCodigoInstalacion());
		pst.setString(10, a.getCodigoPlanificada());
		pst.executeUpdate();
	}

	public List<ActividadPlanificada> getActividadesPlanificadasDia(int dia, int mes, int año) throws SQLException {
		List<ActividadPlanificada> actividadesDia = new ArrayList<>();

		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement(
				"SELECT codigoPlanificada FROM actividad_planificada WHERE dia = ? AND mes = ? AND año = ?");
		pst.setInt(1, dia);
		pst.setInt(2, mes);
		pst.setInt(3, año);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			actividadesDia.add(encontrarActividadPlanificada(rs.getString(1)));
		}

		return actividadesDia;
	}

	public void eliminarActividadPlanificada(ActividadPlanificada a) throws SQLException {
		if (encontrarActividadPlanificada(a.getCodigoPlanificada()) != null) {
			Connection con = DriverManager.getConnection(URL);
			PreparedStatement pst = con
					.prepareStatement("DELETE FROM actividad_planificada WHERE codigoPlanificada = ?");
			pst.setString(1, a.getCodigoPlanificada());
			pst.execute();
			actividadesPlanificadas.remove(a);
		}

	}

//SOCIOS	

	private void cargarSocios() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM SOCIO");
		convertirSociosEnLista(rs);
		ordenarSocios();
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
			int dia, int mes, int año) {
		List<ActividadPlanificada> actividadesQueYaTieneReservadasElSocio = new ArrayList<ActividadPlanificada>();
		for (Reserva reserva : getReservas()) {
			if (reserva.getId_cliente().equals(socio.getId_cliente())) {
				for (ActividadPlanificada ap : getActividadesPlanificadas()) {
					if (ap.getDia() == dia && ap.getMes() == mes && ap.getAño() == año
							&& reserva.getCodigo_actividad().equals(ap.getCodigoPlanificada())) {
						actividadesQueYaTieneReservadasElSocio.add(ap);
					}
				}
			}
		}
		return actividadesQueYaTieneReservadasElSocio;
	}

	public List<ActividadPlanificada> getActividadesDisponiblesParaReservaDeSocio(int dia, int mes, int año, int hora) {
		List<ActividadPlanificada> listaSort = new ArrayList<ActividadPlanificada>();
		for (ActividadPlanificada ap : getActividadesPlanificadas()) {
			if (ap.getDia() == dia + 1 && ap.getMes() == mes && ap.getAño() == año) {
				listaSort.add(ap);
			} else if (ap.getDia() == dia && ap.getMes() == mes && ap.getAño() == año
					&& ap.getHoraInicio() >= hora + 1) {
				listaSort.add(ap);
			}
		}
		return listaSort;
	}
	
	public void ordenarSocios() {
		Collections.sort(this.socios);
	}
	

//TERCEROS

	private void cargarTerceros() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM TERCEROS");
		convertirTercerosEnLista(rs);
		ordenarTerceros();
		rs.close();
		st.close();
		con.close();
	}

	private void convertirTercerosEnLista(ResultSet rs) throws SQLException {
		this.terceros = new ArrayList<>();
		while (rs.next()) {
			terceros.add(convertirTercero(rs));
		}

	}

	private Tercero convertirTercero(ResultSet rs) throws SQLException {
		String id_cliente = rs.getString(1);
		String nombre = rs.getString(2);
		return new Tercero(id_cliente, nombre);
	}

	public List<Tercero> getTerceros() {
		return terceros;
	}

	public Tercero encontrarTerceros(String id_cliente) {
		for (Tercero tercero : terceros) {
			if (tercero.getId_cliente().equals(id_cliente)) {
				return tercero;
			}
		}
		return null;
	}

	public void printTerceros() {
		System.out.println("Lista de terceros");
		for (Tercero tercero : terceros) {
			System.out.println(tercero.toString());
		}
	}
	public void ordenarTerceros() {
		Collections.sort(this.terceros);
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

	public void anularReserva(Socio socio, ActividadPlanificada actividad) {
		for (Reserva reserva : reservas) {
			if (reserva.getCodigo_actividad().equals(actividad.getCodigoPlanificada())) {
				cancelarPlazaReserva(socio.getId_cliente(), actividad.getCodigoPlanificada());
				actualizarPlazasActividadPlanificada(actividad, actividad.getLimitePlazas() + 1);
				try {
					cargarReservas();
					cargarActividadesPlanificadas();
				} catch (SQLException e) {
					System.out.println("Fallo al cargar reservas");
				}
			}
		}
	}

	public boolean añadirReserva(Socio socio, ActividadPlanificada actividad) {
		boolean result = insertarReserva(socio.getId_cliente(), actividad.getCodigoPlanificada());
		actualizarPlazasActividadPlanificada(actividad, actividad.getLimitePlazas() - 1);
		try {
			cargarReservas();
			cargarActividadesPlanificadas();
		} catch (SQLException e) {
			System.out.println("Fallo al cargar reservas");
		}
		return result;
	}

	public boolean insertarReserva(String id_cliente, String codigoPlanificada) {
		try {
			Connection con = DriverManager.getConnection(Programa.URL);
			PreparedStatement pst = con
					.prepareStatement("INSERT INTO RESERVA (id_cliente, codigo_actividad) VALUES(?,?)");
			pst.setString(1, id_cliente);
			pst.setString(2, codigoPlanificada);
			pst.execute();
			pst.close();
			con.close();
			return true;
		} catch (SQLException e) {
			System.out.println("Error añadiendo la reserva");
			return false;
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
	

//ALQUILERES

	private void cargarAlquileres() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM ALQUILER");
		convertirAlquileresEnLista(rs);
		ordenarAlquileres();
		rs.close();
		st.close();
		con.close();
	}

	private void convertirAlquileresEnLista(ResultSet rs) throws SQLException {
		this.alquileres = new ArrayList<>();
		while (rs.next()) {
			alquileres.add(convertirAlquiler(rs));
		}

	}

	private Alquiler convertirAlquiler(ResultSet rs) throws SQLException {
		String id_alquiler = rs.getString(1);
		String id_instalacion = rs.getString(2);
		String id_cliente = rs.getString(3);
		int dia = rs.getInt(4);
		int mes = rs.getInt(5);
		int año = rs.getInt(6);
		int horaInicio = rs.getInt(7);
		int horaFin = rs.getInt(8);
		String estado = rs.getString(9);
		return new Alquiler(id_alquiler, id_instalacion, id_cliente, dia, mes, año, horaInicio, horaFin, estado);
	}

	public List<Alquiler> getAlquileres() {
		return alquileres;
	}

	public Alquiler encontrarAlquileres(String id_alquiler) {
		for (Alquiler alquiler : alquileres) {
			if (alquiler.getId_alquiler().equals(id_alquiler)) {
				return alquiler;
			}
		}
		return null;
	}

	public void printAlquileres() {
		System.out.println("Lista de alquileres");
		for (Alquiler alquiler : alquileres) {
			System.out.println(alquiler.toString());
		}
	}
	public void ordenarAlquileres() {
		Collections.sort(this.alquileres);
	}

//REGISTROS

	public void cargarRegistros() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM REGISTRO");
		convertirRegistrosEnLista(rs);
		ordenarRegistros();
		rs.close();
		st.close();
		con.close();

	}

	private void convertirRegistrosEnLista(ResultSet rs) throws SQLException {
		this.registros = new ArrayList<>();
		while (rs.next()) {
			registros.add(convertirRegistro(rs));
		}

	}

	private Registro convertirRegistro(ResultSet rs) throws SQLException {
		String id_registro = rs.getString(1);
		String id_alquiler = rs.getString(2);
		int hora_entrada = rs.getInt(3);
		int hora_salida = rs.getInt(4);
		boolean alquilerPagado = rs.getBoolean(5);
		boolean socioPresentado = rs.getBoolean(6);
		return new Registro(id_registro, id_alquiler, hora_entrada, hora_salida, alquilerPagado, socioPresentado);
	}

	public List<Registro> getRegistros() {
		return registros;
	}

	private void printRegistros() {
		System.out.println("Lista de registros");
		for (Registro registro : registros) {
			System.out.println(registro.toString());
		}
	}
	
	public void ordenarRegistros() {
		Collections.sort(this.registros);
	}

//MONITORES

	private void cargarMonitores() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM MONITOR");
		convertirMonitoresEnLista(rs);
		ordenarMonitores();
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
	
	public void ordenarMonitores() {
		Collections.sort(this.monitores);
	}

//ADMINISTRACIÓN

	public void asignarMonitorActividad(Monitor monitor, ActividadPlanificada actividadPlanificada) {
		String codigoMonitor = monitor.getCodigoMonitor();
		String codigoActividadPlanificada = actividadPlanificada.getCodigoPlanificada();
		try {
			Connection con = DriverManager.getConnection(URL);
			PreparedStatement pst = con
					.prepareStatement("UPDATE ACTIVIDAD_PLANIFICADA SET codigoMonitor = ? WHERE codigoPlanificada = ?");
			pst.setString(1, codigoMonitor);
			pst.setString(2, codigoActividadPlanificada);
			pst.execute();
			pst.close();
			con.close();
			cargarActividadesPlanificadas();
		} catch (SQLException e) {
			System.out.println("Error asignando monitor");
		}

	}

	// RECURSOS

	public boolean checkRecursosExisten(String[] nombreRecursos) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement("SELECT nombre_recurso FROM recurso");
		ResultSet rs = pst.executeQuery();
		List<String> nombres = new ArrayList<>();
		while (rs.next()) {
			nombres.add(rs.getString(1));
		}
		for (int i = 0; i < nombreRecursos.length; i++) {
			if (!nombres.contains(nombreRecursos[i]))
				return false;
		}

		return true;
	}

	public void updateRecursosFromLista(List<Recurso> listaRecursos) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		for (Recurso r : listaRecursos) {

			PreparedStatement pst = con
					.prepareStatement("UPDATE RECURSO SET codigo_actividad = ? WHERE id_recurso = ?");
			pst.setString(1, r.getActividad());
			pst.setString(2, r.getIdRecurso());
			pst.execute();
		}
	}

	public void cargarRecursos() throws SQLException {
		recursos = new ArrayList<>();
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement("SELECT * FROM recursos");
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("nombre_recurso");
			String codigoInst = rs.getString("codigo_instalacion");
			Recurso r = new Recurso(nombre, codigoInst);
			recursos.add(r);
			String codigoActividad = rs.getString("codigo_actividad");
			if (!rs.wasNull()) {
				for (Actividad a : actividades) {
					if (a.getCodigo().equals(codigoActividad)) {
						a.añadirRecurso(r);
					}
				}
			}
		}

	}

	public Recurso obtenerRecursoPorNombre(String nombreRecurso) throws SQLException {
		// no hay que hacer check aqui porque se supone que ya hemos checkeado los
		// nombres usando el otro método antes que este
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement("SELECT codigo_instalacion FROM recurso WHERE nombre_recurso = ?");
		pst.setString(1, nombreRecurso);
		ResultSet rs = pst.executeQuery();
		String codigo_instalacion = rs.getString(1);

		rs.close();
		pst.close();
		con.close();
		return new Recurso(nombreRecurso, codigo_instalacion);
	}

	public void printRecursos() {
		System.out.println("Lista de recursos");
		for (Recurso r : recursos) {
			System.out.println(r.toString());
		}
	}

	// INSTALACION

	public Instalacion obtenerInstalacionPorId(String id) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement(
				"SELECT codigo_instalacion, nombre_instalacion, preciohora FROM instalacion WHERE codigo_instalacion = ?");
		pst.setString(1, id);
		ResultSet rs = pst.executeQuery();
		String codigo_instalacion = rs.getString(1);
		String nombre = rs.getString(2);
		double precio = rs.getDouble(3);
		rs.close();
		pst.close();
		con.close();

		return new Instalacion(codigo_instalacion, nombre, precio);
	}

	private List<Instalacion> convertirInstalacionesEnLista(ResultSet rs) throws SQLException {
		instalaciones = new ArrayList<Instalacion>();
		while (rs.next()) {
			String codigo = rs.getString(1);
			String nombre = rs.getString(2);
			Double precio = rs.getDouble(3);
			instalaciones.add(new Instalacion(codigo, nombre, precio));
		}
		return instalaciones;
	}

	public void cargarInstalaciones() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement("SELECT * from instalacion");
		ResultSet rs = pst.executeQuery();
		convertirInstalacionesEnLista(rs);
		ordenarInstalaciones();
	}

	public void printInstalaciones() {
		System.out.println("Lista de instalaciones");
		for (Instalacion instalacion : instalaciones) {
			System.out.println(instalacion.toString());
		}
	}

	public List<Instalacion> getInstalaciones() {
		return this.instalaciones;
	}
	
	public void ordenarInstalaciones() {
		Collections.sort(this.instalaciones);
	}

	// UTIL

	public int[] obtenerHoraDiaMesAño() {
		Calendar calendar = Calendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH) + 1;
		int año = calendar.get(Calendar.YEAR);
		int[] fecha = { hora, dia, mes, año };
		return fecha;
	}

	public boolean comprobarActividadAntesQueFecha(ActividadPlanificada actividad) {
		int[] fechaActual = obtenerHoraDiaMesAño();
		int horaActividad = actividad.getHoraInicio();
		int diaActividad = actividad.getDia();
		int mesActividad = actividad.getMes();
		int añoActividad = actividad.getAño();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date real = sdformat.parse(fechaActual[3] + "-" + fechaActual[2] + "-" + fechaActual[1]);
			Date fechaActividad = sdformat.parse(añoActividad + "-" + mesActividad + "-" + diaActividad);
			if ((real.compareTo(fechaActividad) < 0)
					|| (real.compareTo(fechaActividad) == 0 && fechaActual[0] < horaActividad)) {
				return true;
			}
		} catch (ParseException e) {
			System.out.println("Error parseando fechas");
		}
		return false;
	}
}