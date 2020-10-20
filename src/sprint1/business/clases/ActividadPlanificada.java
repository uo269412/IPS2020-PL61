package sprint1.business.clases;

import java.util.UUID;

public class ActividadPlanificada {

	private String codigoPlanificada;
	private String codigoActividad;
	private String codigoMonitor = null;
	private int dia;
	private int mes;
	private int año;
	private int horaInicio;
	private int horaFin;
	private int limitePlazas;
	private String codigoInstalacion;

	
//	public ActividadPlanificada(String codigoActividad, int dia, int mes, int año, int horaInicio, int horaFin, int limitePlazas, String codigoMonitor) {
//		this(codigoActividad + "_" + codigoMonitor, codigoActividad, dia, mes, año, horaInicio, horaFin, limitePlazas, codigoMonitor);
//	}

	public ActividadPlanificada(int dia, int mes, int año, int limitePlazas, int horaInicio, int horaFin,
			String codigoMonitor, String codigoActividad, String codigoInstalacion) {
		this.codigoPlanificada = "P-" + codigoActividad + "/" + codigoMonitor + "-" + UUID.randomUUID().toString().substring(0,5);
		this.dia = dia;
		this.mes = mes;
		this.año = año;
		this.limitePlazas = limitePlazas;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.codigoMonitor = codigoMonitor;
		this.codigoActividad = codigoActividad;
		this.codigoInstalacion = codigoInstalacion;
	}
	
	public ActividadPlanificada(String codigoActividad, int dia, int mes, int año, int limitePlazas, int horaInicio, int horaFin, String codigoInstalacion) {
		String codigoPlanificada = "P-" + codigoActividad + "/" + codigoMonitor + "-" + UUID.randomUUID().toString().substring(0,5);
		
		setCodigoPlanificada(codigoPlanificada);
		setFecha(dia, mes, año);
		setLimitePlazas(limitePlazas);
		setHoraInicio(horaInicio);
		setHoraFin(horaFin);
		setCodigoActividad(codigoActividad);
		setCodigoInstalacion(codigoInstalacion);
	}


	public ActividadPlanificada(String codigoActividad, int dia, int mes, int año, int limitePlazas, int horaInicio,
			int horaFin, String codigoMonitor, String codigoPlanificada, String codigoInstalacion) {
		this.codigoPlanificada = codigoPlanificada;
		this.dia = dia;
		this.mes = mes;
		this.año = año;
		this.limitePlazas = limitePlazas;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.codigoMonitor = codigoMonitor;
		this.codigoActividad = codigoActividad;
		this.codigoInstalacion = codigoInstalacion;
	}

	public ActividadPlanificada(String codigoPlanificada, String codigoActividad, int dia, int mes, int año,
			int horaInicio, int horaFin, int limitePlazas, String codigoMonitor, String codigoInstalacion) {

		setCodigoPlanificada(codigoPlanificada);
		setFecha(dia, mes, año);
		setLimitePlazas(limitePlazas);
		setHoraInicio(horaInicio);
		setHoraFin(horaFin);
		setCodigoMonitor(codigoMonitor);
		setCodigoActividad(codigoActividad);
		setCodigoInstalacion(codigoInstalacion);
	}
	
	public ActividadPlanificada(String codigoPlanificada, String codigoActividad, int dia, int mes, int año,
			int horaInicio, int horaFin, int limitePlazas, String codigoInstalacion) {

		setCodigoPlanificada(codigoPlanificada);
		setFecha(dia, mes, año);
		setLimitePlazas(limitePlazas);
		setHoraInicio(horaInicio);
		setHoraFin(horaFin);
		setCodigoActividad(codigoActividad);
		setCodigoInstalacion(codigoInstalacion);
	}

	public String getCodigoPlanificada() {
		return codigoPlanificada;
	}

	public void setCodigoPlanificada(String codigoPlanificada) {
		this.codigoPlanificada = codigoPlanificada;
	}

	public String getFecha() {
		return dia + "-" + mes + "-" + año;
	}

	public void setFecha(int dia, int mes, int año) {
		this.dia = dia;
		this.mes = mes;
		this.año = año;
	}

	public int getDia() {
		return dia;
	}

	public int getMes() {
		return mes;
	}

	public int getAño() {
		return año;
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
	
	public void setCodigoInstalacion(String codigoInstalacion) {
		this.codigoInstalacion = codigoInstalacion;
	}

	public boolean tieneMonitor() {
		if (getCodigoMonitor() == null || getCodigoMonitor().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public String getCodigoInstalacion() {
		return this.codigoInstalacion;
	}
	
	public boolean esDeLibreAcceso() {
		return this.limitePlazas == 0;
	}

	@Override
	public String toString() {
		String monitor;
		if (!tieneMonitor()) {
			monitor = ", todavía sin ningún monitor";
		} else {
			monitor = " impartida por el monitor " + getCodigoMonitor();
		}
		return "Actividad " + codigoActividad + " empieza a las " + horaInicio + " horas y acaba a las " + horaFin
				+ " del " + dia + "/" + mes + "/" + año + monitor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoPlanificada == null) ? 0 : codigoPlanificada.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActividadPlanificada other = (ActividadPlanificada) obj;
		if (codigoPlanificada == null) {
			if (other.codigoPlanificada != null)
				return false;
		} else if (!codigoPlanificada.equals(other.codigoPlanificada))
			return false;
		return true;
	}

}