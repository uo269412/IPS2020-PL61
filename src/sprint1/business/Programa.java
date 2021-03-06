package sprint1.business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import sprint1.business.dominio.centroDeportes.actividades.Actividad;
import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;
import sprint1.business.dominio.centroDeportes.alquileres.Alquiler;
import sprint1.business.dominio.centroDeportes.empleados.Monitor;
import sprint1.business.dominio.centroDeportes.instalaciones.Instalacion;
import sprint1.business.dominio.centroDeportes.instalaciones.Recurso;
import sprint1.business.dominio.centroDeportes.planificaciones.Conflicto;
import sprint1.business.dominio.centroDeportes.reservas.Registro;
import sprint1.business.dominio.centroDeportes.reservas.Reserva;
import sprint1.business.dominio.clientes.Cliente;
import sprint1.business.dominio.clientes.Socio;
import sprint1.business.dominio.clientes.Tercero;

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
	// Conexi�n Javi
	 public static String URL =
	 "jdbc:sqlite:C:\\Users\\javie\\git\\IPS2020-PL61\\resources\\bdProject.db";

	// Conexi�n Dani
//	public static String URL = "jdbc:sqlite:C:\\Users\\Dani\\git\\IPS2020-PL61_sprint3\\resources\\bdProject.db";

	// Conexi�n Juan.elo
	//public static String URL = "jdbc:sqlite:C:\\Users\\Usuario\\git\\IPS2020-PL61\\resources\\bdProject.db";

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
			cargarConflictos();
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

		LocalDateTime horacierre = LocalDateTime.now();
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst1 = con.prepareStatement(
				"SELECT id_cliente, dia, mes, a�o, horaInicio, horaFin FROM alquiler WHERE id_instalacion = ?");
		pst1.setString(1, i.getCodigo());
		ResultSet rs1 = pst1.executeQuery();
		while (rs1.next()) {
			for (Socio s : socios) {
				if (s.getId_cliente().equals(rs1.getString(1)) && horacierre
						.isBefore(LocalDateTime.of(rs1.getInt(4), rs1.getInt(3), rs1.getInt(2), rs1.getInt(5), 0))) {
					clientesAfectados.add(s);
				}
			}
			for (Tercero s : terceros) {
				if (s.getId_cliente().equals(rs1.getString(1)) && horacierre
						.isBefore(LocalDateTime.of(rs1.getInt(4), rs1.getInt(3), rs1.getInt(2), rs1.getInt(5), 0))) {
					clientesAfectados.add(s);
				}
			}
		}

		return clientesAfectados;

	}
	
	public Set<Cliente> clientesAfectadosPorCierreAlquiler(Instalacion i) {
		
		Set<Cliente> clientesAfectados = new HashSet<>();
		
		for(Alquiler a: getAlquileres()) {
			if(a.getId_instalacion().equals(i.getCodigoInstalacion())) {
				for (Socio s : socios) {
					if (s.getId_cliente().equals(a.getId_cliente())) {
						clientesAfectados.add(s);
					}
				}
				for (Tercero s : terceros) {
					if (s.getId_cliente().equals(a.getId_cliente())) {
						clientesAfectados.add(s);
					}
				}
			}
		}
		
		return clientesAfectados;
	}
	
	public Set<Cliente> clientesAfectadosPorCierreActividad(Instalacion i, Actividad a) {
		Set<Cliente> clientesAfectados = new HashSet<>();
		
		for(ActividadPlanificada ap: getActividadesPlanificadas()) {
			if(ap.getCodigoActividad().equals(a.getCodigo()) && ap.getCodigoInstalacion().equals(i.getCodigoInstalacion())) {
				for(Reserva r: getReservas()) {
					if(r.getCodigo_actividad().equals(ap.getCodigoPlanificada())) {
						clientesAfectados.add(encontrarSocio(r.getId_cliente()));
					}
				}
			}
		}
		
		return clientesAfectados;
	}

	public Set<Cliente> clientesAfectadosPorCierreDia(Instalacion i, int dia, int mes, int a�o) {
		Set<Cliente> clientesAfectados = new HashSet<>();
		
		
		
		for(ActividadPlanificada ap: getActividadesPlanificadasInstalacionDia(i.getCodigoInstalacion(), dia, mes, a�o)) {
			for(Reserva r: getReservas()) {
				if(r.getCodigo_actividad().equals(ap.getCodigoPlanificada())) {
					clientesAfectados.add(encontrarSocio(r.getId_cliente()));
				}
			}
				
		}
		
		for(Alquiler a: getAlquileres(i.getCodigoInstalacion(), dia, mes, a�o)) {
			for (Socio s : socios) {
				if (s.getId_cliente().equals(a.getId_cliente())) {
					clientesAfectados.add(s);
				}
			}
			for (Tercero s : terceros) {
				if (s.getId_cliente().equals(a.getId_cliente())) {
					clientesAfectados.add(s);
				}
			}
		}

		return clientesAfectados;
	}

	public List<Alquiler> getAlquileresQueHaHechoClienteEnInstalacion(Cliente c, Instalacion i) {
		List<Alquiler> alquileresCliente = new LinkedList<>();

		for (Alquiler a : getAlquileres()) {
			if (a.getId_cliente().equals(c.getId_cliente()) && a.getId_instalacion().equals(i.getCodigo())) {
				alquileresCliente.add(a);
			}
		}

		return alquileresCliente;
	}

	public List<Alquiler> getAlquileresQueHaHechoClienteEnInstalacionAPartirDe(Cliente c, Instalacion i, int dia,
			int mes, int a�o, int hora) {
		List<Alquiler> alquileresCliente = new LinkedList<>();

		for (Alquiler a : getAlquileresQueHaHechoClienteEnInstalacion(c, i)) {
			if (LocalDateTime.of(a�o, mes, dia, hora, 0)
					.isBefore(LocalDateTime.of(a.getA�o(), a.getMes(), a.getDia(), a.getHoraInicio(), 0))) {
				alquileresCliente.add(a);
			} else if (LocalDateTime.of(a�o, mes, dia, hora, 0)
					.isEqual(LocalDateTime.of(a.getA�o(), a.getMes(), a.getDia(), a.getHoraInicio(), 0))) {
				if (hora <= a.getHoraInicio()) {
					alquileresCliente.add(a);
				}
			}
		}

		return alquileresCliente;
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
		//ordenarActividadesPlanificadasPorFecha();
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
		String codigoInstalacion = rs.getString(10);
		return new ActividadPlanificada(codigoActividad, dia, mes, a�o, limitePlazas, horaInicio, horaFin,
				codigoMonitor, codigoPlanificada, codigoInstalacion);
	}

	public List<ActividadPlanificada> getActividadesPlanificadas() {
		return actividadesPlanificadas;
	}

	public List<ActividadPlanificada> getActividadesPlanificadasInstalacionDia(String idInstalacion, int dia, int mes,
			int a�o) {
		List<ActividadPlanificada> planificadasSinFiltrar = getActividadesPlanificadas(dia, mes, a�o);
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

	public String encontrarNombrePlanificada(ActividadPlanificada planificada) {
		for (Actividad a : getActividades()) {
			if (a.getCodigo().equals(planificada.getCodigoActividad())) {
				return a.getNombre();
			}
		}

		return null;
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

	public List<ActividadPlanificada> getActividadesPlanificadas(int hora, int dia, int mes, int a�o) {
		List<ActividadPlanificada> listaSort = new ArrayList<ActividadPlanificada>();
		for (ActividadPlanificada ap : getActividadesPlanificadas()) {
			if (ap.getDia() == dia && ap.getMes() == mes && ap.getA�o() == a�o) {
				if (hora == ap.getHoraInicio() || (hora > ap.getHoraInicio() && hora < ap.getHoraFin())) {
					listaSort.add(ap);
				}
			}
		}
		return listaSort;
	}

	public List<ActividadPlanificada> getActividadesPlanificadas(String codigoInstalacion, int hora, int dia, int mes,
			int a�o) {
		List<ActividadPlanificada> listaSort = new ArrayList<ActividadPlanificada>();
		for (ActividadPlanificada ap : getActividadesPlanificadas()) {
			if (ap.getDia() == dia && ap.getMes() == mes && ap.getA�o() == a�o) {
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

//		
//		for (int hora = actividadSeleccionada.getHoraInicio(); hora < actividadSeleccionada.getHoraFin(); hora++) {
//			if (hora >= actividad.getHoraInicio() && hora < actividad.getHoraFin())
//				return true;
//		}
//		return false;
	}

	public boolean comprobarTiempoActividadyAlquilerColisiona(ActividadPlanificada actividadSeleccionada,
			Alquiler actividad) {
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
		int fecha[] = obtenerHoraDiaMesA�o();
		int dia = fecha[1];
		int mes = fecha[2];
		int a�o = fecha[3];
		if (getActividadesPlanificadas(dia, mes, a�o).isEmpty()) {
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
			
		} catch (SQLException e) {
			System.out.println("Error borrando la reserva");
		}
	}

	public void a�adirActividadPlanificada(ActividadPlanificada a) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con
				.prepareStatement("INSERT INTO actividad_planificada" + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		pst.setString(1, a.getCodigoActividad());
		pst.setInt(2, a.getDia());
		pst.setInt(3, a.getMes());
		pst.setInt(4, a.getA�o());
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
				"UPDATE actividad_planificada SET codigoActividad = ?, dia = ?, mes = ?, a�o = ?, limitePlazas = ?, horaInicio = ?, horaFin = ?, codigoMonitor = ?, codigoInstalacion = ? WHERE codigoPlanificada = ?");
		pst.setString(1, a.getCodigoActividad());
		pst.setInt(2, a.getDia());
		pst.setInt(3, a.getMes());
		pst.setInt(4, a.getA�o());
		pst.setInt(5, a.getLimitePlazas());
		pst.setInt(6, a.getHoraInicio());
		pst.setInt(7, a.getHoraFin());
		pst.setString(8, a.getCodigoMonitor());
		pst.setString(9, a.getCodigoInstalacion());
		pst.setString(10, a.getCodigoPlanificada());
		pst.executeUpdate();
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

//	public List<ActividadPlanificada> getActividadesPlanificadasQueHaReservadoSocioEnUnDiaEspecifico(Socio socio,
//			int dia, int mes, int a�o) {
//		List<ActividadPlanificada> actividadesQueYaTieneReservadasElSocio = new ArrayList<ActividadPlanificada>();
//		for (Reserva reserva : getReservas()) {
//			if (reserva.getId_cliente().equals(socio.getId_cliente())) {
//				for (ActividadPlanificada ap : getActividadesPlanificadas()) {
//					if (ap.getDia() == dia || ap.getDia() == dia + 1 && ap.getMes() == mes && ap.getA�o() == a�o
//							&& reserva.getCodigo_actividad().equals(ap.getCodigoPlanificada())) {
//						actividadesQueYaTieneReservadasElSocio.add(ap);
//					}
//				}
//			}
//		}
//		return actividadesQueYaTieneReservadasElSocio;
//	}

	public List<ActividadPlanificada> getActividadesPlanificadasQueHaReservadoSocioEnUnDiaEspecifico(Socio socio,
			int dia, int mes, int a�o) {
		List<ActividadPlanificada> actividadesQueYaTieneReservadasElSocio = new ArrayList<ActividadPlanificada>();
		for (Reserva reserva : getReservas(dia, mes, a�o)) {
			if (reserva.getId_cliente().equals(socio.getId_cliente())) {
				for (ActividadPlanificada ap : getActividadesPlanificadas(dia, mes, a�o)) {
					if (ap.getCodigoPlanificada().equals(reserva.getCodigo_actividad())
							&& reserva.getId_cliente().equals(socio.getId_cliente())) {
						actividadesQueYaTieneReservadasElSocio.add(ap);
					}
				}
			}
		}
		return actividadesQueYaTieneReservadasElSocio;
	}

	public List<ActividadPlanificada> getActividadesDisponiblesParaReservaDeSocio(int dia, int mes, int a�o, int hora) {
		List<ActividadPlanificada> listaSort = new ArrayList<ActividadPlanificada>();
		for (ActividadPlanificada ap : getActividadesPlanificadas()) {
			if (ap.getDia() == dia + 1 && ap.getMes() == mes && ap.getA�o() == a�o) {
				listaSort.add(ap);
			} else if (ap.getDia() == dia && ap.getMes() == mes && ap.getA�o() == a�o
					&& ap.getHoraInicio() > hora + 1) {
				listaSort.add(ap);
			}
		}
		return listaSort;
	}

	public void ordenarSocios() {
		Collections.sort(this.socios);
	}

	public Set<Socio> sociosQueNoHanPagadoAlquilerMes(int mes, int a�o) {
		Set<Socio> sociosSinPagar = new HashSet<>();
		for (Alquiler a : getAlquileres(mes, a�o)) {
			for (Registro r : registros) {
				if (r.getId_alquiler().equals(a.getId_alquiler())) {
					if (!r.isAlquilerPagado()) {
						if (encontrarSocio(a.getId_cliente()) != null) {
							sociosSinPagar.add(encontrarSocio(a.getId_cliente()));
						}
					}
				}
			}
		}

		return sociosSinPagar;
	}

	public Set<Socio> sociosQueNoHanPagadoAlquilerMesSocio(int mes, int a�o) {
		Set<Socio> sociosSinPagar = new HashSet<>();
		for (Alquiler a : getAlquileres(mes, a�o)) {
			boolean hayRegistro = false;
			for (Registro r : registros) {
				if (r.getId_alquiler().equals(a.getId_alquiler())) {
					hayRegistro = true;
					if (!r.isAlquilerPagado()) {
						if (encontrarSocio(a.getId_cliente()) != null) {
							sociosSinPagar.add(encontrarSocio(a.getId_cliente()));
						}
					}
				}
			}
			if (!hayRegistro) { // no se ha presentado
				if (encontrarSocio(a.getId_cliente()) != null) {
					sociosSinPagar.add(encontrarSocio(a.getId_cliente()));
				}
			}
		}

		return sociosSinPagar;
	}

	public Set<Socio> sociosAfectadosPorModificacionActividad(ActividadPlanificada a) throws SQLException {
		Set<Socio> sociosAfectados = new HashSet<>();

		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement("SELECT id_cliente FROM reserva WHERE codigo_actividad=?");
		pst.setString(1, a.getCodigoPlanificada());
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			String id = rs.getString(1);
			for (Socio s : getSocios()) {
				if (s.getId_cliente().equals(id)) {
					sociosAfectados.add(s);
				}
			}
		}

		return sociosAfectados;
	}

	public List<Alquiler> alquileresNoPagadosSocio(Socio s) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		List<Alquiler> alquileresNoPagados = new ArrayList<>();
		PreparedStatement pst = con.prepareStatement(
				"SELECT * FROM registro " + "JOIN alquiler ON registro.id_alquiler = alquiler.id_alquiler "
						+ "WHERE alquilerPagado = 0 AND id_cliente = ?");
		pst.setString(1, s.getId_cliente());
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			alquileresNoPagados.add(encontrarAlquileres(rs.getString(2)));
		}
		rs.close();
		pst.close();
		con.close();

		return alquileresNoPagados;
	}

	public double getCuotaSocio(Socio s) throws SQLException {
		List<Alquiler> alquileresNoPagados = alquileresNoPagadosSocio(s);
		double precioTotal = 0;
		for (Alquiler a : alquileresNoPagados) {
			precioTotal += (a.getHoraFin() - a.getHoraInicio())
					* encontrarInstalacion(a.getId_instalacion()).getPrecioHora();
		}

		return precioTotal;
	}

//TERCEROS

	public void a�adirTercero(Tercero t) throws SQLException {
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

	public List<Socio> eliminarReserva(String codigoActividad) {
		ArrayList<Reserva> toRemove = new ArrayList<>();
		for (Reserva reserva : reservas) {
			if (reserva.getCodigo_actividad().equals(codigoActividad)) {
				toRemove.add(reserva);
			}
		}
		ArrayList<Socio> toRet = new ArrayList<Socio>();
		for (Reserva reserva : toRemove) {
			remove(reserva);
			toRet.add(encontrarSocio(reserva.getId_cliente()));
		}
		return toRet;
	}

	private void remove(Reserva reserva) {
		reservas.remove(reserva);
		cancelarPlazaReserva(reserva.getId_cliente(), reserva.getCodigo_actividad());
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

	public boolean a�adirReserva(Socio socio, ActividadPlanificada actividad) {
		boolean result = insertarReserva(socio.getId_cliente(), actividad.getCodigoPlanificada());
		if (result)
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
			System.out.println("Error a�adiendo la reserva");
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

	public List<Reserva> getReservas(int hora, int dia, int mes, int a�o) {
		List<Reserva> listaSort = new ArrayList<Reserva>();
		for (Reserva reserva : getReservas()) {
			for (ActividadPlanificada ap : getActividadesPlanificadas(hora, dia, mes, a�o)) {
				if (ap.getCodigoPlanificada().equals(reserva.getCodigo_actividad())) {
					listaSort.add(reserva);
				}
			}
		}
		return listaSort;
	}

	public List<Reserva> getReservas(int dia, int mes, int a�o) {
		List<Reserva> listaSort = new ArrayList<Reserva>();
		for (Reserva reserva : getReservas()) {
			for (ActividadPlanificada ap : getActividadesPlanificadas(dia, mes, a�o)) {
				if (ap.getCodigoPlanificada().equals(reserva.getCodigo_actividad())) {
					listaSort.add(reserva);
				}
			}
		}
		return listaSort;
	}

	public List<Reserva> getReservas(String codigo_instalacion, int hora, int dia, int mes, int a�o) {
		List<Reserva> listaSort = new ArrayList<Reserva>();
		for (Reserva reserva : getReservas()) {
			for (ActividadPlanificada ap : getActividadesPlanificadas(codigo_instalacion, hora, dia, mes, a�o)) {
				if (ap.getCodigoPlanificada().equals(reserva.getCodigo_actividad())) {
					listaSort.add(reserva);
				}
			}
		}
		return listaSort;

	}

//ALQUILERES

	public void a�adirAlquilerDia(Cliente cliente, Instalacion instalacion, int dia, int mes, int a�o, int horaInicio,
			int horaFin) {
		Alquiler alquiler = new Alquiler(instalacion.getCodigoInstalacion(), cliente.getId_cliente(), dia, mes, a�o,
				horaInicio, horaFin);
		try {
			Connection con = DriverManager.getConnection(Programa.URL);
			PreparedStatement pst = con.prepareStatement(
					"INSERT INTO ALQUILER (id_alquiler, id_instalacion, id_cliente, dia, mes, a�o, horaInicio, horaFin) VALUES(?,?,?,?,?,?,?,?)");
			pst.setString(1, alquiler.getId_alquiler());
			pst.setString(2, alquiler.getId_instalacion());
			pst.setString(3, alquiler.getId_cliente());
			pst.setInt(4, alquiler.getDia());
			pst.setInt(5, alquiler.getMes());
			pst.setInt(6, alquiler.getA�o());
			pst.setInt(7, alquiler.getHoraInicio());
			pst.setInt(8, alquiler.getHoraFin());
			pst.execute();
			pst.close();
			con.close();
			cargarAlquileres();
		} catch (SQLException e) {
			System.out.println("Error a�adiendo el alquier");
		}
	}

	public List<Alquiler> getAlquileres(int mes, int a�o) {
		List<Alquiler> listaSort = new ArrayList<Alquiler>();
		for (Alquiler ap : getAlquileres()) {
			if (ap.getMes() == mes && ap.getA�o() == a�o) {
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
		int a�o = rs.getInt(6);
		int horaInicio = rs.getInt(7);
		int horaFin = rs.getInt(8);
		return new Alquiler(id_alquiler, id_instalacion, id_cliente, dia, mes, a�o, horaInicio, horaFin);
	}

	public List<Alquiler> getAlquileres() {
		return alquileres;
	}

	public List<Alquiler> getAlquileres(int dia, int mes, int a�o) {
		List<Alquiler> listaSort = new ArrayList<Alquiler>();
		for (Alquiler a : getAlquileres()) {
			if (a.getDia() == dia && a.getMes() == mes && a.getA�o() == a�o) {
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

	public List<Alquiler> getAlquileresDia(int dia, int mes, int a�o) throws SQLException {
		List<Alquiler> alquileresDia = new ArrayList<>();

		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con
				.prepareStatement("SELECT id_alquiler FROM alquiler WHERE dia = ? AND mes = ? AND a�o = ?");
		pst.setInt(1, dia);
		pst.setInt(2, mes);
		pst.setInt(3, a�o);
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
			Connection con = DriverManager.getConnection(URL);
			PreparedStatement pst = con.prepareStatement("DELETE FROM ALQUILER WHERE id_alquiler = ?");
			pst.setString(1, id_alquiler);
			pst.execute();
			pst.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error borrando el alquiler");
			System.out.println(e.getMessage());
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
		int[] fechaActual = obtenerHoraDiaMesA�o();
		int diaAlquiler = alquiler.getDia();
		int mesAlquiler = alquiler.getMes();
		int a�oAlquiler = alquiler.getA�o();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date real = sdformat.parse(fechaActual[3] + "-" + fechaActual[2] + "-" + fechaActual[1]);
			Date fechaAlquiler = sdformat.parse(a�oAlquiler + "-" + mesAlquiler + "-" + diaAlquiler);
			if (real.compareTo(fechaAlquiler) < 0) {
				return true;
			}
		} catch (ParseException e) {
			System.out.println("Error parseando fechas");
		}
		return false;
	}

	public List<Alquiler> getAlquileresSocioEnUnDiaEspecifico(Cliente cliente, int dia, int mes, int a�o) {
		List<Alquiler> alquileresQueYaTieneAlquiladassElSocio = new ArrayList<Alquiler>();
		for (Alquiler alquiler : getAlquileres()) {
			if (alquiler.getId_cliente().equals(cliente.getId_cliente())) {
				if (alquiler.getDia() == dia && alquiler.getMes() == mes && alquiler.getA�o() == a�o) {
					alquileresQueYaTieneAlquiladassElSocio.add(alquiler);
				}
			}
		}
		return alquileresQueYaTieneAlquiladassElSocio;
	}

	public Alquiler getAlquilerSocioAhora(Cliente cliente) {
		int[] fecha = obtenerHoraDiaMesA�o();
		int hora = fecha[0];
		int dia = fecha[1];
		int mes = fecha[2];
		int a�o = fecha[3];
		for (Alquiler alquiler : getAlquileres()) {
			if (alquiler.getId_cliente().equals(cliente.getId_cliente())) {
				if (alquiler.getDia() == dia && alquiler.getMes() == mes && alquiler.getA�o() == a�o) {
					if (alquiler.getHoraInicio() == hora
							|| ((hora >= alquiler.getHoraInicio()) && (hora <= alquiler.getHoraFin())))
						return alquiler;
				}
			}
		}
		return null;
	}

	public Alquiler getAlquilerSocioAhoraNoCancelado(Cliente cliente) {
		int[] fecha = obtenerHoraDiaMesA�o();
		int hora = fecha[0];
		int dia = fecha[1];
		int mes = fecha[2];
		int a�o = fecha[3];
		for (Alquiler alquiler : getAlquileres()) {
			if (alquiler.getId_cliente().equals(cliente.getId_cliente())) {
				if (alquiler.getDia() == dia && alquiler.getMes() == mes && alquiler.getA�o() == a�o) {
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

	public List<Alquiler> getAlquileres(int hora, int dia, int mes, int a�o) {
		List<Alquiler> listaSort = new ArrayList<Alquiler>();
		for (Alquiler ap : getAlquileres()) {
			if (ap.getDia() == dia && ap.getMes() == mes && ap.getA�o() == a�o) {
				if (hora == ap.getHoraInicio() || (hora > ap.getHoraInicio() && hora < ap.getHoraFin())) {
					listaSort.add(ap);
				}
			}
		}
		return listaSort;
	}

	public List<Alquiler> getAlquileresNoCancelados(int hora, int dia, int mes, int a�o) {
		List<Alquiler> listaSort = new ArrayList<>();
		for (Alquiler a : getAlquileres()) {
			if (a.getDia() == dia && a.getMes() == mes && a.getA�o() == a�o) {
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

	public Alquiler getAlquilerAhoraNoCancelado(Socio socio, int hora, int dia, int mes, int a�o) {
		for (Alquiler a : getAlquileres()) {
			if (a.getId_cliente().equals(socio.getId_cliente())) {
				if (a.getDia() == dia && a.getMes() == mes && a.getA�o() == a�o) {
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

	public List<Alquiler> getAlquileres(String codigo_instalacion, int hora, int dia, int mes, int a�o) {
		List<Alquiler> listaSort = new ArrayList<Alquiler>();
		for (Alquiler ap : getAlquileres()) {
			if (ap.getDia() == dia && ap.getMes() == mes && ap.getA�o() == a�o) {
				if ((hora == ap.getHoraInicio()) || (hora > ap.getHoraInicio() && hora < ap.getHoraFin())) {
					if (codigo_instalacion.equals(ap.getId_instalacion())) {
						listaSort.add(ap);
					}
				}
			}
		}
		return listaSort;
	}

	public List<Alquiler> getAlquileres(String codigo_instalacion, int dia, int mes, int a�o) {
		List<Alquiler> listaSort = new ArrayList<Alquiler>();
		for (Alquiler ap : getAlquileres()) {
			if (ap.getDia() == dia && ap.getMes() == mes && ap.getA�o() == a�o) {
				if (codigo_instalacion.equals(ap.getId_instalacion())) {
					listaSort.add(ap);
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

	public List<Alquiler> getAlquileresSocio(Socio socio) {
		List<Alquiler> listaSort = new ArrayList<Alquiler>();
		for (Alquiler al : getAlquileres()) {
			if (al.getId_cliente().equals(socio.getId_cliente()))
				listaSort.add(al);
		}
		return listaSort;
	}

	public boolean hayAlquileresConSocioDentro() {
		int[] fecha = obtenerHoraDiaMesA�o();
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

	public void a�adirAlquiler(Cliente cliente, Instalacion instalacion, int horaInicio, int horaFin) {
		int[] fecha = obtenerHoraDiaMesA�o();
		Alquiler alquiler = new Alquiler(instalacion.getCodigoInstalacion(), cliente.getId_cliente(), fecha[1],
				fecha[2], fecha[3], horaInicio, horaFin);
		try {
			Connection con = DriverManager.getConnection(Programa.URL);
			PreparedStatement pst = con.prepareStatement(
					"INSERT INTO ALQUILER (id_alquiler, id_instalacion, id_cliente, dia, mes, a�o, horaInicio, horaFin) VALUES(?,?,?,?,?,?,?,?)");
			pst.setString(1, alquiler.getId_alquiler());
			pst.setString(2, alquiler.getId_instalacion());
			pst.setString(3, alquiler.getId_cliente());
			pst.setInt(4, alquiler.getDia());
			pst.setInt(5, alquiler.getMes());
			pst.setInt(6, alquiler.getA�o());
			pst.setInt(7, alquiler.getHoraInicio());
			pst.setInt(8, alquiler.getHoraFin());
			pst.executeUpdate();
			pst.close();
			con.close();
			cargarAlquileres();
		} catch (SQLException e) {
			System.out.println("Error a�adiendo el alquier");
		}
	}

	public void a�adirAlquiler(Cliente cliente, Instalacion instalacion, int horaInicio, int horaFin, int dia, int mes,
			int a�o) {
		Alquiler alquiler = new Alquiler(instalacion.getCodigoInstalacion(), cliente.getId_cliente(), dia, mes, a�o,
				horaInicio, horaFin);
		try {
			Connection con = DriverManager.getConnection(Programa.URL);
			PreparedStatement pst = con.prepareStatement(
					"INSERT INTO ALQUILER (id_alquiler, id_instalacion, id_cliente, dia, mes, a�o, horaInicio, horaFin) VALUES(?,?,?,?,?,?,?,?)");
			pst.setString(1, alquiler.getId_alquiler());
			pst.setString(2, alquiler.getId_instalacion());
			pst.setString(3, alquiler.getId_cliente());
			pst.setInt(4, alquiler.getDia());
			pst.setInt(5, alquiler.getMes());
			pst.setInt(6, alquiler.getA�o());
			pst.setInt(7, alquiler.getHoraInicio());
			pst.setInt(8, alquiler.getHoraFin());
			pst.executeUpdate();
			pst.close();
			con.close();
			cargarAlquileres();
		} catch (SQLException e) {
			System.out.println("Error a�adiendo el alquier");
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

	public void crearRegistro(Alquiler alquiler, boolean haPagado) {
		Registro registro = new Registro(alquiler.getId_alquiler());
		int[] fecha = obtenerHoraDiaMesA�o();
		int hora = fecha[0];
		try {
			Connection con = DriverManager.getConnection(Programa.URL);
			PreparedStatement pst = con.prepareStatement(
					"INSERT INTO REGISTRO(id_registro, id_alquiler, hora_entrada, alquilerPagado, socioPresentado) VALUES(?,?,?,?,?)");
			pst.setString(1, registro.getId_registro());
			pst.setString(2, registro.getId_alquiler());
			pst.setInt(3, hora);
			pst.setBoolean(4, haPagado);
			pst.setBoolean(5, true);
			pst.execute();
			pst.close();
			con.close();
			cargarRegistros();
		} catch (SQLException e) {
			System.out.println("Error a�adiendo el registro");
		}
	}

	public void registrarHoraSalidaSocio(Registro registro) {
		String id = registro.getId_registro();
		int[] fecha = obtenerHoraDiaMesA�o();
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

//ADMINISTRACI�N

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

	public void insertarRecurso(Recurso r) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con
				.prepareStatement("INSERT INTO RECURSOS(codigo_recurso, nombre_recurso, codigo_instalacion, unidades)"
						+ "VALUES (?,?,?,?)");
		pst.setString(1, r.getIdRecurso());
		pst.setString(2, r.getNombre());
		pst.setString(3, r.getInstalacion());
		pst.setInt(4, r.getUnidades());

		pst.executeUpdate();
	}

	public void updateRecursosFromLista(List<Recurso> listaRecursos) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		for (Recurso r : listaRecursos) {

			PreparedStatement pst = con
					.prepareStatement("UPDATE RECURSOS SET codigo_actividad = ? WHERE codigo_recurso = ?");
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
			String codigoRecurso = rs.getString("codigo_recurso");
			String nombre = rs.getString("nombre_recurso");
			String codigoInst = rs.getString("codigo_instalacion");
			int unidades = rs.getInt("unidades");
			Recurso r = new Recurso(codigoRecurso, nombre, codigoInst, unidades);
			recursos.add(r);
			String codigoActividad = rs.getString("codigo_actividad");
			if (!rs.wasNull()) {
				for (Actividad a : actividades) {
					if (a.getCodigo().equals(codigoActividad)) {
						a.a�adirRecurso(r);
					}
				}
			}
		}

	}

	public List<Recurso> getRecursosSinActividad() {
		List<Recurso> recursosSinActividad = new ArrayList<>();
		for (Recurso r : recursos) {
			if (r.getActividad() == null) {
				recursosSinActividad.add(r);
			}
		}

		return recursosSinActividad;
	}

	public Recurso obtenerRecursoPorNombre(String nombreRecurso) throws SQLException {
		// no hay que hacer check aqui porque se supone que ya hemos checkeado los
		// nombres usando el otro m�todo antes que este
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con
				.prepareStatement("SELECT codigo_instalacion, codigo_recurso FROM recurso WHERE nombre_recurso = ?");
		pst.setString(1, nombreRecurso);
		ResultSet rs = pst.executeQuery();
		rs.next();
		String codigo_instalacion = rs.getString(1);
		String codigoRecurso = rs.getString(2);
		int unidades = rs.getInt(2);
		rs.close();
		pst.close();
		con.close();
		return new Recurso(codigoRecurso, nombreRecurso, codigo_instalacion, unidades);
	}

	public List<Recurso> getRecursos() {
		return this.recursos;
	}

	public void printRecursos() {
		System.out.println("Lista de recursos");
		for (Recurso r : recursos) {
			System.out.println(r.toDebug());
		}
	}

//INSTALACION

	public Instalacion obtenerInstalacionPorId(String id) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement(
				"SELECT codigo_instalacion, nombre_instalacion, preciohora, estado, permite_alquileres FROM instalacion WHERE codigo_instalacion = ?");
		pst.setString(1, id);
		ResultSet rs = pst.executeQuery();
		String codigo_instalacion = rs.getString(1);
		String nombre = rs.getString(2);
		double precio = rs.getDouble(3);
		int estado = rs.getInt(4);
		boolean permite_alquileres = rs.getInt(5) == 1 ? true : false;
		rs.close();
		pst.close();
		con.close();

		return new Instalacion(codigo_instalacion, nombre, precio, estado, permite_alquileres);
	}

	public void updateInstalacion(Instalacion i) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement(
				"UPDATE INSTALACION SET nombre_instalacion=?, preciohora=?, estado=?, permite_alquileres=? WHERE codigo_instalacion = ?");
		pst.setString(1, i.getNombre());
		pst.setDouble(2, i.getPrecioHora());
		pst.setInt(3, i.getEstado());
		if (i.permiteAlquileres()) {
			pst.setInt(4, 1);
		} else {
			pst.setInt(4, 0);
		}
		pst.setString(5, i.getCodigo());
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
			boolean permiteAlquileres = rs.getInt(5) == 1;
			instalaciones.add(new Instalacion(codigo, nombre, precio, estado, permiteAlquileres));
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

	public List<Instalacion> getInstalacionesDisponiblesParaAlquiler() {
		List<Instalacion> instalacionesDisponiblesAlq = new LinkedList<>();

		for (Instalacion i : getInstalacionesDisponibles()) {
			if (i.permiteAlquileres()) {
				instalacionesDisponiblesAlq.add(i);
			}
		}

		return instalacionesDisponiblesAlq;
	}

	public void printInstalaciones() {
		System.out.println("Lista de instalaciones");
		for (Instalacion instalacion : instalaciones) {
			System.out.println(instalacion.toDebug());
		}
	}

	public List<Instalacion> getInstalaciones() {
		return this.instalaciones;
	}

	public void ordenarInstalaciones() {
		Collections.sort(this.instalaciones);
	}

	public boolean checkIfHayInstalacionesLibresParaAhora() {
		int fecha[] = obtenerHoraDiaMesA�o();
		int hora = fecha[0] + 1;
		int dia = fecha[1];
		int mes = fecha[2];
		int a�o = fecha[3];
		for (Instalacion instalacion : instalaciones) {
			if (instalacion.getEstado() == Instalacion.DISPONIBLE) {
				if (getAlquileres(instalacion.getCodigoInstalacion(), hora, dia, mes, a�o).isEmpty()
						&& getReservas(instalacion.getCodigoInstalacion(), hora, dia, mes, a�o).isEmpty()) {
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

	public boolean instalacionDisponibleDia(Instalacion i, int dia, int mes, int a�o) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement(
				"SELECT COUNT(*) FROM cierre_dia WHERE codigo_instalacion = ? AND dia = ? AND mes = ? AND a�o = ?");
		pst.setString(1, i.getCodigoInstalacion());
		pst.setInt(2, dia);
		pst.setInt(3, mes);
		pst.setInt(4, a�o);
		ResultSet rs = pst.executeQuery();
		rs.next();
		if (rs.getInt(1) > 0) {
			rs.close();
			pst.close();
			con.close();
			return false;
		}
		rs.close();
		pst.close();
		con.close();
		return true;
	}
	
	public boolean instalacionDisponibleActividad(Instalacion i, String codigo_actividad) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement(
				"SELECT COUNT(*) FROM cierre_actividad WHERE codigo_instalacion = ? AND codigo_actividad = ?");
		pst.setString(1, i.getCodigoInstalacion());
		pst.setString(2, codigo_actividad);
		ResultSet rs = pst.executeQuery();
		rs.next();
		if (rs.getInt(1) > 0) {
			rs.close();
			pst.close();
			con.close();
			return false;
		}
		rs.close();
		pst.close();
		con.close();
		return true;
	}

	public boolean cierreInstalacionDia(Instalacion i, int dia, int mes, int a�o) throws SQLException {
		if (i.getEstado() == Instalacion.CERRADA) {
			return false;
		} else if (!instalacionDisponibleDia(i, dia, mes, a�o)) {
			return false;
		} else {
			Connection con = DriverManager.getConnection(URL);
			PreparedStatement pst = con
					.prepareStatement("INSERT INTO cierre_dia(codigo_instalacion, dia, mes, a�o) VALUES(?,?,?,?)");
			pst.setString(1, i.getCodigoInstalacion());
			pst.setInt(2, dia);
			pst.setInt(3, mes);
			pst.setInt(4, a�o);
			pst.executeUpdate();

			pst.close();
			con.close();
			return true;
		}
	}

	public Set<Actividad> getActividadesDisponiblesParaInstalacion(Instalacion i) throws SQLException {
		List<String> idActividadesNoDisponibles = new LinkedList<>();

		Set<Actividad> toRet = new HashSet<>();

		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con
				.prepareStatement("SELECT codigo_actividad FROM CIERRE_ACTIVIDAD WHERE codigo_instalacion=?");
		pst.setString(1, i.getCodigoInstalacion());

		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			idActividadesNoDisponibles.add(rs.getString(1));
		}

		for (Actividad a : getActividades()) {
			if (!idActividadesNoDisponibles.contains(a.getCodigo())) {
				toRet.add(a);
			}
		}

		return toRet;
	}

	public void deleteActividadesAsociadasConCierre() throws SQLException {
		Set<ActividadPlanificada> aBorrar = new HashSet<>();

		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pstDia = con.prepareStatement("SELECT * FROM cierre_dia");
		ResultSet rsDia = pstDia.executeQuery();

		while (rsDia.next()) {
			String codigoinstalacion = rsDia.getString("codigo_instalacion");
			int dia = rsDia.getInt("dia");
			int mes = rsDia.getInt("mes");
			int a�o = rsDia.getInt("a�o");

			for (ActividadPlanificada ap : getActividadesPlanificadas()) {
				if (ap.getCodigoInstalacion().equals(codigoinstalacion) && ap.getDia() == dia && ap.getMes() == mes
						&& ap.getA�o() == a�o) {
					aBorrar.add(ap);
				}
			}
		}

		rsDia.close();
		pstDia.close();

		PreparedStatement pstActividad = con.prepareStatement("SELECT * FROM cierre_actividad");
		ResultSet rsActividad = pstActividad.executeQuery();

		while (rsActividad.next()) {
			String codigoinstalacion = rsActividad.getString("codigo_instalacion");
			String codigoactividad = rsActividad.getString("codigo_actividad");

			for (ActividadPlanificada ap : getActividadesPlanificadas()) {
				if (ap.getCodigoInstalacion().equals(codigoinstalacion)
						&& ap.getCodigoActividad().equals(codigoactividad)) {
					aBorrar.add(ap);
				}
			}
		}
		rsActividad.close();
		pstActividad.close();

		con.close();

		for (ActividadPlanificada planificadaParaBorrar : aBorrar) {
			eliminarReserva(planificadaParaBorrar.getCodigoPlanificada());
			eliminarActividadPlanificada(planificadaParaBorrar);
		}
	}

	public void deleteAlquileresAsociadosConCierreDias() throws SQLException {
		
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement("SELECT * FROM cierre_dia");
		ResultSet rs = pst.executeQuery();
		
		Set<Alquiler> alquileresABorrar = new HashSet<>();
		while(rs.next()) {
			String id_instalacion = rs.getString(1);
			int dia = rs.getInt(2);
			int mes = rs.getInt(3);
			int a�o = rs.getInt(4);
			for (Alquiler a : getAlquileres(id_instalacion,dia,mes,a�o)) {
				alquileresABorrar.add(a);
			}
		}
		
		rs.close();
		pst.close();
		con.close();
		for (Alquiler a : alquileresABorrar) {
			anularAlquiler(a);
		}
	}
	
	public void deleteAlquileresAsociadosConCierreEspecifico() {
		
		Set<Alquiler> alquileresABorrar = new HashSet<>();
		
		for(Alquiler a: getAlquileres()) {
			if(!encontrarInstalacion(a.getId_instalacion()).permiteAlquileres()) {
				alquileresABorrar.add(a);
			}
		}
		
		for(Alquiler a: alquileresABorrar) {
			anularAlquiler(a);
		}
	}
	
	
	public void deleteAsociadosConCierreParaDias() throws SQLException {
		deleteActividadesAsociadasConCierre();
		deleteAlquileresAsociadosConCierreDias();
	}
	
	public void deleteAsociadosConCierreParaEspecifico() throws SQLException {
		deleteActividadesAsociadasConCierre();
		deleteAlquileresAsociadosConCierreEspecifico();
	}
	
	public void vetarActividadEnInstalacion(Instalacion i, Actividad a) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement(
				"INSERT INTO cierre_actividad(codigo_instalacion, codigo_actividad)" + " VALUES(?,?)");
		pst.setString(1, i.getCodigoInstalacion());
		pst.setString(2, a.getCodigo());
		pst.executeUpdate();

		pst.close();
		con.close();
	}

	public boolean isActividadVetadaEnInstalacion(Instalacion i, Actividad a) throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		PreparedStatement pst = con.prepareStatement(
				"SELECT COUNT(*) FROM cierre_actividad WHERE codigo_instalacion=? AND codigo_actividad=?");
		pst.setString(1, i.getCodigo());
		pst.setString(2, a.getCodigo());
		ResultSet rs = pst.executeQuery();
		rs.next();
		if (rs.getInt(1) < 1) {
			rs.close();
			pst.close();
			con.close();
			return false;
		}
		rs.close();
		pst.close();
		con.close();
		return true;
	}

	public Set<Instalacion> instalacionesDisponiblesParaActividad(Actividad a) throws SQLException {

		Set<Instalacion> instalacionesDisponibles = new HashSet<>();

		for (Instalacion i : getInstalacionesDisponibles()) {
			if (!isActividadVetadaEnInstalacion(i, a)) {
				instalacionesDisponibles.add(i);
			}
		}

		return instalacionesDisponibles;
	}

// UTIL

	public int[] obtenerHoraDiaMesA�o() {
		Calendar calendar = Calendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH) + 1;
		int a�o = calendar.get(Calendar.YEAR);
		int[] fecha = { hora, dia, mes, a�o };
		return fecha;
	}

	public boolean comprobarActividadAntesQueFecha(ActividadPlanificada actividad) {
		int[] fechaActual = obtenerHoraDiaMesA�o();
		int horaActividad = actividad.getHoraInicio();
		int diaActividad = actividad.getDia();
		int mesActividad = actividad.getMes();
		int a�oActividad = actividad.getA�o();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date real = sdformat.parse(fechaActual[3] + "-" + fechaActual[2] + "-" + fechaActual[1]);
			Date fechaActividad = sdformat.parse(a�oActividad + "-" + mesActividad + "-" + diaActividad);
			if ((real.compareTo(fechaActividad) < 0)
					|| (real.compareTo(fechaActividad) == 0 && fechaActual[0] < horaActividad)) {
				return true;
			}
		} catch (ParseException e) {
			System.out.println("Error parseando fechas");
		}
		return false;
	}

	// CONFLICTOS
	public void crearConflicto(ActividadPlanificada a1, ActividadPlanificada a2) throws SQLException {
		Conflicto c = new Conflicto(a1, a2);
		c.conseguirNombreActividades(this);
		a1.a�adirConflicto(c);

		Connection con = DriverManager.getConnection(URL);

		PreparedStatement pst = con.prepareStatement("INSERT INTO CONFLICTOS VALUES (?,?)");
		pst.setString(1, a1.getCodigoPlanificada());
		pst.setString(2, a2.getCodigoPlanificada());

		pst.executeUpdate();

		pst.close();
		con.close();
	}

	public void cargarConflictos() throws SQLException {
		Connection con = DriverManager.getConnection(URL);

		for (ActividadPlanificada a : actividadesPlanificadas) {

			PreparedStatement pst = con.prepareStatement(
					"SELECT codigoActividadConflictiva FROM CONFLICTOS WHERE codigoActividadAfectada = ?");

			pst.setString(1, a.getCodigoPlanificada());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				ActividadPlanificada actividadConflictiva = null;
				for (ActividadPlanificada a2 : actividadesPlanificadas) {
					if (a2.getCodigoPlanificada().equals(rs.getString(1))) {
						actividadConflictiva = a2;
					}
				}
				Conflicto c = new Conflicto(a, actividadConflictiva);
				c.conseguirNombreActividades(this);
				a.a�adirConflicto(c);
			}

			rs.close();
			pst.close();
		}
	}

}