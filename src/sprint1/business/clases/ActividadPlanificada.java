package sprint1.business.clases;

import java.util.ArrayList;
import java.util.List;

public class ActividadPlanificada {

	private String codigoPlanificada;
	private String codigoActividad;
	private String codigoMonitor = null;
	private int dia;
	private int mes;
	private int a�o;
	private int horaInicio;
	private int horaFin;
	private int limitePlazas;
	private List<Recurso> recursosActividad;

	
	public ActividadPlanificada(String codigoActividad, int dia, int mes, int a�o, int horaInicio, int horaFin, int limitePlazas, String codigoMonitor) {
		this(codigoActividad + "_" + codigoMonitor, codigoActividad, dia, mes, a�o, horaInicio, horaFin, limitePlazas, codigoMonitor);
	}

	public ActividadPlanificada(int dia, int mes, int a�o, int limitePlazas, int horaInicio, int horaFin,
			String codigoMonitor, String codigoActividad) {
		this.codigoPlanificada = "P-" + codigoActividad + "/" + codigoMonitor;
		this.dia = dia;
		this.mes = mes;
		this.a�o = a�o;
		this.limitePlazas = limitePlazas;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.codigoMonitor = codigoMonitor;
		this.codigoActividad = codigoActividad;
	}
	
	public ActividadPlanificada(String codigoActividad, int dia, int mes, int a�o, int limitePlazas, int horaInicio, int horaFin) {
		String codigoPlanificada = "P-" + codigoActividad + "/" + codigoMonitor;
		
		setCodigoPlanificada(codigoPlanificada);
		setFecha(dia, mes, a�o);
		setLimitePlazas(limitePlazas);
		setHoraInicio(horaInicio);
		setHoraFin(horaFin);
		setCodigoActividad(codigoActividad);
	}


	public ActividadPlanificada(String codigoActividad, int dia, int mes, int a�o, int limitePlazas, int horaInicio,
			int horaFin, String codigoMonitor, String codigoPlanificada) {
		this.codigoPlanificada = codigoPlanificada;
		this.dia = dia;
		this.mes = mes;
		this.a�o = a�o;
		this.limitePlazas = limitePlazas;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.codigoMonitor = codigoMonitor;
		this.codigoActividad = codigoActividad;
	}

	public ActividadPlanificada(String codigoPlanificada, String codigoActividad, int dia, int mes, int a�o,
			int horaInicio, int horaFin, int limitePlazas, String codigoMonitor) {

		setCodigoPlanificada(codigoPlanificada);
		setFecha(dia, mes, a�o);
		setLimitePlazas(limitePlazas);
		setHoraInicio(horaInicio);
		setHoraFin(horaFin);
		setCodigoMonitor(codigoMonitor);
		setCodigoActividad(codigoActividad);

		recursosActividad = new ArrayList<Recurso>();
	}
	
	public ActividadPlanificada(String codigoPlanificada, String codigoActividad, int dia, int mes, int a�o,
			int horaInicio, int horaFin, int limitePlazas) {

		setCodigoPlanificada(codigoPlanificada);
		setFecha(dia, mes, a�o);
		setLimitePlazas(limitePlazas);
		setHoraInicio(horaInicio);
		setHoraFin(horaFin);
		setCodigoActividad(codigoActividad);
		
		recursosActividad = new ArrayList<>();
	}

	public String getCodigoPlanificada() {
		return codigoPlanificada;
	}

	public void setCodigoPlanificada(String codigoPlanificada) {
		this.codigoPlanificada = codigoPlanificada;
	}

	public String getFecha() {
		return dia + "-" + mes + "-" + a�o;
	}

	public void setFecha(int dia, int mes, int a�o) {
		this.dia = dia;
		this.mes = mes;
		this.a�o = a�o;
	}

	public int getDia() {
		return dia;
	}

	public int getMes() {
		return mes;
	}

	public int getA�o() {
		return a�o;
	}

	public int getLimitePlazas() {
		return limitePlazas;
	}

	public void setLimitePlazas(int limitePlazas) {
		this.limitePlazas = limitePlazas;
	}

	public int getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(int horaInicio) {
		this.horaInicio = horaInicio;
	}

	public int getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(int horaFin) {
		this.horaFin = horaFin;
	}

	public String getCodigoMonitor() {
		return codigoMonitor;
	}

	public void setCodigoMonitor(String codigoMonitor) {
		this.codigoMonitor = codigoMonitor;
	}

	public String getCodigoActividad() {
		return codigoActividad;
	}

	public void setCodigoActividad(String codigoActividad) {
		this.codigoActividad = codigoActividad;
	}

	public void a�adirRecurso(Recurso r) {
		recursosActividad.add(r);
	}

	public boolean tieneMonitor() {
		if (getCodigoMonitor() == null || getCodigoMonitor().isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String monitor;
		if (!tieneMonitor()) {
			monitor = ", todav�a sin ning�n monitor";
		} else {
			monitor = " impartida por el monitor " + getCodigoMonitor();
		}
		return "Actividad " + codigoActividad + " empieza a las " + horaInicio + " horas y acaba a las " + horaFin
				+ " del " + dia + "/" + mes + "/" + a�o + monitor;
	}

}