package sprint1.business.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
//	 public static String URL = "jdbc:sqlite:C:\\Users\\javie\\git\\IPS2020-PL61\\resources\\bdProject.db";

	// Conexión Dani
	//public static String URL = "jdbc:sqlite:C:\\Users\\Dani\\git\\IPS2020-PL61_sprint2\\resources\\bdProject.db";

//	 Conexión Juan.elo
	 public static String URL =
	 "jdbc:sqlite:C:\\Users\\Usuario\\git\\IPS2020-PL61\\resources\\bdProject.db";

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

//CLIENTES
	public Set<Cliente> clientesAfectadosPorCierre(Instalacion i) throws SQLException {
		Set<Cliente> clientesAfectados = new HashSet<>();

		LocalDate horacierre = LocalDate.now();
		Connection con = DriverManager.getConnection(URL);
//		PreparedStatement pst1 = con.prepareStatement("SELECT id_cliente FROM reserva JOIN actividad_planificada ON reserva.codigo_actividad=actividad_planificada=codigoPlanificada WHERE codigoInstalacion=?");
//		pst1.setString(1, i.getCodigo());
//		ResultSet rs1 = pst1.executeQuery();
//		while(rs1.next()) {
//			for(Socio s: socios) {
//				if(s.getId_cliente().equals(rs1.getString(1))) {
//					clientesAfectados.add(s);
//				}
//			}
//			//la iteración por terceros se podría quitar
//			for(Tercero s: terceros) {
//				if(s.getId_cliente().equals(rs1.getString(1))) {
//					clientesAfectados.add(s);
//				}
//			}
//		}
//		rs1.close();
//		pst1.close();
		PreparedStatement pst2 = con.prepareStatement(
				"SELECT id_cliente, dia, mes, año, horaInicio FROM alquiler WHERE id_instalacion = ?");
		pst2.setString(1, i.getCodigo());
		ResultSet rs2 = pst2.executeQuery();
		while (rs2.next()) {

			for (Tercero s : terceros) {
				if (s.getId_cliente().equals(rs2.getString(1))
						&& horacierre.isBefore(LocalDate.of(rs2.getInt(4), rs2.getInt(3), rs2.getInt(2)))) {
					clientesAfectados.add(s);
				}
			}
		}

		return clientesAfectados;

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
		for (Actividad a : getActividades()) {
			if (!a.requiresRecursos()) {
				listaActividades.add(a);
			} else {
				boolean contieneTodos = true;
				for (Recurso r : a.getRecursos()) {
					if (r.getInstalacion().equals(codigo_instalacion)) {
						contieneTodos = contieneTodos && true;
					} else {
						contieneTodos = contieneTodos && false;
					}
				}
				if (contieneTodos) {
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

	public List<ActividadPlanificada> getActividadesPlanificadasInstalacionDia(String idInstalacion, int dia, int mes,
			int año) {
		List<ActividadPlanificada> planificadasSinFiltrar = getActividadesPlanificadas(dia, mes, año);
		List<ActividadPlanificada> toRet = new LinkedList<>();
		for (ActividadPlanificada a : planificadasSinFiltrar) {
			if (a.getCodigoInstalacion().equals(idInstalacion)) {
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

	public List<ActividadPlanificada> getActividadesPlanificadas(int hora, int dia, int mes, int año) {
		List<ActividadPlanificada> listaSort = new ArrayList<ActividadPlanificada>();
		for (ActividadPlanificada ap : getActividadesPlanificadas()) {
			if (ap.getDia() == dia && ap.getMes() == mes && ap.getAño() == año) {
				if (hora == ap.getHoraInicio() || (hora > ap.getHoraInicio() && hora < ap.getHoraFin())) {
					listaSort.add(ap);
				}
			}
		}
		return listaSort;
	}

	public List<ActividadPlanificada> getActividadesPlanificadas(String codigoInstalacion, int hora, int dia, int mes,
			int año) {
		List<ActividadPlanificada> listaSort = new ArrayList<ActividadPlanificada>();
		for (ActividadPlanificada ap : getActividadesPlanificadas()) {
			if (ap.getDia() == dia && ap.getMes() == mes && ap.getAño() == año) {
				if ((hora == ap.getHoraInicio()) || (hora > ap.getHoraInicio() && hora < ap.getHoraFin())) {
					if (ap.getCodigoInstalacion().equals(codigoInstalacion)) {
						listaSort.add(ap);
					}
				}
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

	public boolean checkIfHayActividadesParaHoy() {
		int fecha[] = obtenerHoraDiaMesAño();
		int dia = fecha[1];
		int mes = fecha[2];
		int año = fecha[3];
		if (getActividadesPlanificadas(dia, mes, año).isEmpty()) {
			return false;
		}
		return true;
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
	
	public Set<Socio> sociosQueNoHanPagadoAlquilerMes(int mes, int año) {
		Set<Socio> sociosSinPagar = new HashSet<>();
		for(Alquiler a: getAlquileres(mes, año)) {
			boolean hayRegistro = false;
			for(Registro r: registros) {
				if(r.getId_alquiler().equals(a.getId_alquiler())) {
					hayRegistro = true;
					if(!r.isAlquilerPagado()) {
						sociosSinPagar.add(encontrarSocio(a.getId_cliente()));
					}
				}
			}
			if(!hayRegistro) { //no se ha presentado
				sociosSinPagar.add(encontrarSocio(a.getId_cliente()));
			}
		}
		
		return sociosSinPagar;
	}

//TERCEROS

	public void añadirTercero(Tercero t) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement("INSERT INTO terceros(id_cliente, nombre) VALUES(?, ?)");
		pst.setString(1, t.getId_cliente());
		pst.setString(2, t.getNombre());
		pst.executeUpdate();
		terceros.add(t);
		pst.close();
		con.close();
	}

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

	public List<Alquiler> getAlquileresQueHaHechoTerceroEnInstalacion(Tercero tercero, Instalacion i) {
		List<Alquiler> alquileresSocio = new LinkedList<>();

		for (Alquiler a : getAlquileres()) {
			if (a.getId_cliente().equals(tercero.getId_cliente()) && a.getId_instalacion().equals(i.getCodigo())) {
				alquileresSocio.add(a);
			}
		}

		return alquileresSocio;
	}

	public List<Alquiler> getAlquileresQueHaHechoTerceroEnInstalacionAPartirDe(Tercero tercero, Instalacion i, int dia,
			int mes, int año, int hora) {
		List<Alquiler> alquileresSocio = new LinkedList<>();

		for (Alquiler a : getAlquileresQueHaHechoTerceroEnInstalacion(tercero, i)) {
			if (LocalDate.of(año, mes, dia).isBefore(LocalDate.of(a.getAño(), a.getMes(), a.getDia()))) {
				alquileresSocio.add(a);
			} else if (LocalDate.of(año, mes, dia).isEqual(LocalDate.of(a.getAño(), a.getMes(), a.getDia()))) {
				if (hora <= a.getHoraInicio()) {
					alquileresSocio.add(a);
				}
			}
		}

		return alquileresSocio;
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

	public List<Reserva> getReservas(int hora, int dia, int mes, int año) {
		List<Reserva> listaSort = new ArrayList<Reserva>();
		for (Reserva reserva : getReservas()) {
			for (ActividadPlanificada ap : getActividadesPlanificadas(hora, dia, mes, año)) {
				if (ap.getCodigoPlanificada().equals(reserva.getCodigo_actividad())) {
					listaSort.add(reserva);
				}
			}
		}
		return listaSort;
	}

	public List<Reserva> getReservas(String codigo_instalacion, int hora, int dia, int mes, int año) {
		List<Reserva> listaSort = new ArrayList<Reserva>();
		for (Reserva reserva : getReservas()) {
			for (ActividadPlanificada ap : getActividadesPlanificadas(codigo_instalacion, hora, dia, mes, año)) {
				if (ap.getCodigoPlanificada().equals(reserva.getCodigo_actividad())) {
					listaSort.add(reserva);
				}
			}
		}
		return listaSort;

	}

//ALQUILERES

	public void añadirAlquilerDia(Cliente cliente, Instalacion instalacion, int dia, int mes, int año, int horaInicio,
			int horaFin) {
		Alquiler alquiler = new Alquiler(instalacion.getCodigoInstalacion(), cliente.getId_cliente(), dia, mes, año,
				horaInicio, horaFin);
		try {
			Connection con = DriverManager.getConnection(Programa.URL);
			PreparedStatement pst = con.prepareStatement(
					"INSERT INTO ALQUILER (id_alquiler, id_instalacion, id_cliente, dia, mes, año, horaInicio, horaFin) VALUES(?,?,?,?,?,?,?,?)");
			pst.setString(1, alquiler.getId_alquiler());
			pst.setString(2, alquiler.getId_instalacion());
			pst.setString(3, alquiler.getId_cliente());
			pst.setInt(4, alquiler.getDia());
			pst.setInt(5, alquiler.getMes());
			pst.setInt(6, alquiler.getAño());
			pst.setInt(7, alquiler.getHoraInicio());
			pst.setInt(8, alquiler.getHoraFin());
			pst.execute();
			pst.close();
			con.close();
			cargarAlquileres();
		} catch (SQLException e) {
			System.out.println("Error añadiendo el alquier");
		}
	}
	
	public List<Alquiler> getAlquileres(int mes, int año) {
		List<Alquiler> listaSort = new ArrayList<Alquiler>();
		for (Alquiler ap : getAlquileres()) {
			if (ap.getMes() == mes && ap.getAño() == año) {
				listaSort.add(ap);
			}
		}
		return listaSort;
	}
	
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
		return new Alquiler(id_alquiler, id_instalacion, id_cliente, dia, mes, año, horaInicio, horaFin);
	}

	public List<Alquiler> getAlquileres() {
		return alquileres;
	}

	public List<Alquiler> getAlquileres(int dia, int mes, int año) {
		List<Alquiler> listaSort = new ArrayList<Alquiler>();
		for (Alquiler a : getAlquileres()) {
			if (a.getDia() == dia && a.getMes() == mes && a.getAño() == año) {
				listaSort.add(a);
			}
		}
		return listaSort;
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
			System.out.println(alquiler.toDebug());
		}
	}

	public void ordenarAlquileres() {
		Collections.sort(this.alquileres);
	}

	public void eliminarAlquileresNoDisponibles() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement("DELETE FROM alquiler WHERE id_instalacion = ?");
		for (Instalacion i : instalaciones) {
			if (i.getEstado() == Instalacion.CERRADA) {
				pst.setString(1, i.getCodigoInstalacion());
				pst.executeUpdate();
			}
		}
		pst.close();
		con.close();
	}

	public List<Alquiler> getAlquileresDia(int dia, int mes, int año) throws SQLException {
		List<Alquiler> alquileresDia = new ArrayList<>();

		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con
				.prepareStatement("SELECT id_alquiler FROM alquiler WHERE dia = ? AND mes = ? AND año = ?");
		pst.setInt(1, dia);
		pst.setInt(2, mes);
		pst.setInt(3, año);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			alquileresDia.add(encontrarAlquileres(rs.getString(1)));
		}

		return alquileresDia;
	}

	public void anularAlquiler(Alquiler alquiler) {
		cancelarAlquiler(alquiler.getId_alquiler());
		anularRegistroAsociado(alquiler.getId_alquiler());
		try {
			cargarAlquileres();
		} catch (SQLException e) {
			System.out.println("Fallo al cargar alquileres");
		}
	}

	private void cancelarAlquiler(String id_alquiler) {
		try {
			Connection con = DriverManager.getConnection(Programa.URL);
			PreparedStatement pst = con.prepareStatement("DELETE FROM ALQUILER WHERE id_alquiler = ?");
			pst.setString(1, id_alquiler);
			pst.execute();
			pst.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error borrando el alquiler");
		}
	}

	private void anularRegistroAsociado(String id_alquiler) {
		try {
			Connection con = DriverManager.getConnection(Programa.URL);
			PreparedStatement pst = con.prepareStatement("DELETE FROM REGISTRO WHERE id_alquiler = ?");
			pst.setString(1, id_alquiler);
			pst.execute();
			pst.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error borrando el registro del alquiler");
		}
	}

	public boolean comprobarAlquilerAPartirDeHoy(Alquiler alquiler) {
		int[] fechaActual = obtenerHoraDiaMesAño();
		int diaAlquiler = alquiler.getDia();
		int mesAlquiler = alquiler.getMes();
		int añoAlquiler = alquiler.getAño();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date real = sdformat.parse(fechaActual[3] + "-" + fechaActual[2] + "-" + fechaActual[1]);
			Date fechaAlquiler = sdformat.parse(añoAlquiler + "-" + mesAlquiler + "-" + diaAlquiler);
			if (real.compareTo(fechaAlquiler) < 0) {
				return true;
			}
		} catch (ParseException e) {
			System.out.println("Error parseando fechas");
		}
		return false;
	}

	public List<Alquiler> getAlquileresSocioEnUnDiaEspecifico(Cliente cliente, int dia, int mes, int año) {
		List<Alquiler> alquileresQueYaTieneAlquiladassElSocio = new ArrayList<Alquiler>();
		for (Alquiler alquiler : getAlquileres()) {
			if (alquiler.getId_cliente().equals(cliente.getId_cliente())) {
				if (alquiler.getDia() == dia && alquiler.getMes() == mes && alquiler.getAño() == año) {
					alquileresQueYaTieneAlquiladassElSocio.add(alquiler);
				}
			}
		}
		return alquileresQueYaTieneAlquiladassElSocio;
	}

	public Alquiler getAlquilerSocioAhora(Cliente cliente) {
		int[] fecha = obtenerHoraDiaMesAño();
		int hora = fecha[0];
		int dia = fecha[1];
		int mes = fecha[2];
		int año = fecha[3];
		for (Alquiler alquiler : getAlquileres()) {
			if (alquiler.getId_cliente().equals(cliente.getId_cliente())) {
				if (alquiler.getDia() == dia && alquiler.getMes() == mes && alquiler.getAño() == año) {
					if (alquiler.getHoraInicio() == hora
							|| ((hora >= alquiler.getHoraInicio()) && (hora <= alquiler.getHoraFin())))
						return alquiler;
				}
			}
		}
		return null;
	}

	public Alquiler getAlquilerSocioAhoraNoCancelado(Cliente cliente) {
		int[] fecha = obtenerHoraDiaMesAño();
		int hora = fecha[0];
		int dia = fecha[1];
		int mes = fecha[2];
		int año = fecha[3];
		for (Alquiler alquiler : getAlquileres()) {
			if (alquiler.getId_cliente().equals(cliente.getId_cliente())) {
				if (alquiler.getDia() == dia && alquiler.getMes() == mes && alquiler.getAño() == año) {
					if (alquiler.getHoraInicio() == hora
							|| ((hora >= alquiler.getHoraInicio()) && (hora <= alquiler.getHoraFin()))) {
						if (encontrarInstalacion(alquiler.getId_instalacion()).getEstado() == Instalacion.DISPONIBLE) {
							return alquiler;
						}
					}
				}
			}
		}
		return null;
	}

	public List<Alquiler> getAlquileres(int hora, int dia, int mes, int año) {
		List<Alquiler> listaSort = new ArrayList<Alquiler>();
		for (Alquiler ap : getAlquileres()) {
			if (ap.getDia() == dia && ap.getMes() == mes && ap.getAño() == año) {
				if (hora == ap.getHoraInicio() || (hora > ap.getHoraInicio() && hora < ap.getHoraFin())) {
					listaSort.add(ap);
				}
			}
		}
		return listaSort;
	}

	public List<Alquiler> getAlquileresNoCancelados(int hora, int dia, int mes, int año) {
		List<Alquiler> listaSort = new ArrayList<>();
		for (Alquiler a : getAlquileres()) {
			if (a.getDia() == dia && a.getMes() == mes && a.getAño() == año) {
				for (Instalacion instalacion : getInstalaciones()) {
					if (a.getId_instalacion().equals(instalacion.getCodigo())) {
						if (instalacion.getEstado() == Instalacion.DISPONIBLE) {
							if (hora == a.getHoraInicio() || (hora >= a.getHoraInicio() && hora <= a.getHoraFin())) {
								listaSort.add(a);
							}
						}
					}
				}

			}
		}
		return listaSort;
	}

	public Alquiler getAlquilerAhoraNoCancelado(Socio socio, int hora, int dia, int mes, int año) {
		for (Alquiler a : getAlquileres()) {
			if (a.getId_cliente().equals(socio.getId_cliente())) {
				if (a.getDia() == dia && a.getMes() == mes && a.getAño() == año) {
					for (Instalacion instalacion : getInstalaciones()) {
						if (a.getId_instalacion().equals(instalacion.getCodigo())) {
							if (instalacion.getEstado() == Instalacion.DISPONIBLE) {
								if (hora == a.getHoraInicio()
										|| (hora >= a.getHoraInicio() && hora <= a.getHoraFin())) {
									return a;
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	public List<Alquiler> getAlquileres(String codigo_instalacion, int hora, int dia, int mes, int año) {
		List<Alquiler> listaSort = new ArrayList<Alquiler>();
		for (Alquiler ap : getAlquileres()) {
			if (ap.getDia() == dia && ap.getMes() == mes && ap.getAño() == año) {
				if ((hora == ap.getHoraInicio()) || (hora > ap.getHoraInicio() && hora < ap.getHoraFin())) {
					if (codigo_instalacion.equals(ap.getId_instalacion())) {
						listaSort.add(ap);
					}
				}
			}
		}
		return listaSort;
	}

	public boolean hayAlquileresAhoraSocioNoHaEntrado() {
		for (Socio socio : getSocios()) {
			Alquiler alquiler = getAlquilerSocioAhora(socio);
			if (alquiler != null) {
				if (encontrarRegistro(alquiler.getId_alquiler()) == null) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hayAlquileresConSocioDentro() {
		int[] fecha = obtenerHoraDiaMesAño();
		for (Alquiler alquiler : getAlquileresNoCancelados(fecha[0], fecha[1], fecha[2], fecha[3])) {
			if (alquiler != null) {
				if (encontrarRegistro(alquiler.getId_alquiler()) != null) {
					Registro seleccionado = encontrarRegistro(alquiler.getId_alquiler());
					if (seleccionado.isSocioPresentado() && (seleccionado.getHora_salida() == 0)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void añadirAlquiler(Cliente cliente, Instalacion instalacion, int horaInicio, int horaFin) {
		int[] fecha = obtenerHoraDiaMesAño();
		Alquiler alquiler = new Alquiler(instalacion.getCodigoInstalacion(), cliente.getId_cliente(), fecha[1],
				fecha[2], fecha[3], horaInicio, horaFin);
		try {
			Connection con = DriverManager.getConnection(Programa.URL);
			PreparedStatement pst = con.prepareStatement(
					"INSERT INTO ALQUILER (id_alquiler, id_instalacion, id_cliente, dia, mes, año, horaInicio, horaFin) VALUES(?,?,?,?,?,?,?,?)");
			pst.setString(1, alquiler.getId_alquiler());
			pst.setString(2, alquiler.getId_instalacion());
			pst.setString(3, alquiler.getId_cliente());
			pst.setInt(4, alquiler.getDia());
			pst.setInt(5, alquiler.getMes());
			pst.setInt(6, alquiler.getAño());
			pst.setInt(7, alquiler.getHoraInicio());
			pst.setInt(8, alquiler.getHoraFin());
			pst.executeUpdate();
			pst.close();
			con.close();
			cargarAlquileres();
		} catch (SQLException e) {
			System.out.println("Error añadiendo el alquier");
		}
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

	public Registro encontrarRegistro(String id_alquiler) {
		for (Registro registro : registros) {
			if (registro.getId_alquiler().equals(id_alquiler)) {
				return registro;
			}
		}
		return null;
	}
	
	

	public void crearRegistro(Alquiler alquiler) {
		Registro registro = new Registro(alquiler.getId_alquiler());
		int[] fecha = obtenerHoraDiaMesAño();
		int hora = fecha[0];
		try {
			Connection con = DriverManager.getConnection(Programa.URL);
			PreparedStatement pst = con.prepareStatement(
					"INSERT INTO REGISTRO(id_registro, id_alquiler, hora_entrada, alquilerPagado, socioPresentado) VALUES(?,?,?,?,?)");
			pst.setString(1, registro.getId_registro());
			pst.setString(2, registro.getId_alquiler());
			pst.setInt(3, hora);
			pst.setBoolean(4, false);
			pst.setBoolean(5, true);
			pst.execute();
			pst.close();
			con.close();
			cargarRegistros();
		} catch (SQLException e) {
			System.out.println("Error añadiendo el registro");
		}
	}

	public void registrarHoraSalidaSocio(Registro registro) {
		String id = registro.getId_registro();
		int[] fecha = obtenerHoraDiaMesAño();
		int hora = fecha[0];
		try {
			Connection con = DriverManager.getConnection(Programa.URL);
			PreparedStatement pst = con.prepareStatement("UPDATE REGISTRO SET hora_salida = ? WHERE id_registro = ?");
			pst.setInt(1, hora);
			pst.setString(2, id);
			pst.execute();
			pst.close();
			con.close();
			cargarRegistros();
		} catch (SQLException e) {
			System.out.println("Error actualizando la hora de salida");
		}
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

//INSTALACION

	public Instalacion obtenerInstalacionPorId(String id) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement(
				"SELECT codigo_instalacion, nombre_instalacion, preciohora, estado FROM instalacion WHERE codigo_instalacion = ?");
		pst.setString(1, id);
		ResultSet rs = pst.executeQuery();
		String codigo_instalacion = rs.getString(1);
		String nombre = rs.getString(2);
		double precio = rs.getDouble(3);
		int estado = rs.getInt(4);
		rs.close();
		pst.close();
		con.close();

		return new Instalacion(codigo_instalacion, nombre, precio, estado);
	}

	public void updateInstalacion(Instalacion i) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement(
				"UPDATE INSTALACION SET nombre_instalacion=?, preciohora=?, estado=? WHERE codigo_instalacion = ?");
		pst.setString(1, i.getNombre());
		pst.setDouble(2, i.getPrecioHora());
		pst.setInt(3, i.getEstado());
		pst.setString(4, i.getCodigo());
		pst.executeUpdate();
		pst.close();
		con.close();
	}

	private List<Instalacion> convertirInstalacionesEnLista(ResultSet rs) throws SQLException {
		instalaciones = new ArrayList<Instalacion>();
		while (rs.next()) {
			String codigo = rs.getString(1);
			String nombre = rs.getString(2);
			Double precio = rs.getDouble(3);
			int estado = rs.getInt(4);
			instalaciones.add(new Instalacion(codigo, nombre, precio, estado));
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

	public List<Instalacion> getInstalacionesDisponibles() {
		List<Instalacion> instalacionesDisponibles = new LinkedList<>();

		for (Instalacion i : instalaciones) {
			if (i.getEstado() == Instalacion.DISPONIBLE) {
				instalacionesDisponibles.add(i);
			}
		}

		return instalacionesDisponibles;
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

	public boolean checkIfHayInstalacionesLibresParaAhora() {
		int fecha[] = obtenerHoraDiaMesAño();
		int hora = fecha[0] + 1;
		int dia = fecha[1];
		int mes = fecha[2];
		int año = fecha[3];
		for (Instalacion instalacion : instalaciones) {
			if (instalacion.getEstado() == Instalacion.DISPONIBLE) {
				if (getAlquileres(instalacion.getCodigoInstalacion(), hora, dia, mes, año).isEmpty()
						&& getReservas(instalacion.getCodigoInstalacion(), hora, dia, mes, año).isEmpty()) {
					return true;
				}
			}
		}

		return false;
	}

	public Instalacion encontrarInstalacion(String id_instalacion) {
		for (Instalacion instalacion : instalaciones) {
			if (instalacion.getCodigo().equals(id_instalacion)) {
				return instalacion;
			}
		}
		return null;
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