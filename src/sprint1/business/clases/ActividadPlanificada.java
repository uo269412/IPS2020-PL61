package sprint1.business.clases;

import java.util.Date;

public class ActividadPlanificada {
	private String codigoActividad;
	private Date fecha;
	private int limitePlazas;
	private int horaInicio;
	private int horaFin;
	private String codigoMonitor;
	private String codigoPlanificada;

	public ActividadPlanificada(Date fecha, int limitePlazas, int horaInicio, int horaFin,
			String codigoMonitor, String codigoActividad) {
		this.codigoPlanificada = codigoActividad + "_" + codigoMonitor;
		this.fecha = fecha;
		this.limitePlazas = limitePlazas;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.codigoMonitor = codigoMonitor;
		this.codigoActividad = codigoActividad;
	}

	public ActividadPlanificada(String codigoActividad, Date fecha, int limitePlazas, int horaInicio, int horaFin,
			String codigoMonitor, String codigoPlanificada) {
		this.codigoPlanificada = codigoPlanificada;
		this.fecha = fecha;
		this.limitePlazas = limitePlazas;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.codigoMonitor = codigoMonitor;
		this.codigoActividad = codigoActividad;
	}

	public String getCodigoPlanificada() {
		return codigoPlanificada;
	}

	public void setCodigoPlanificada(String codigoPlanificada) {
		this.codigoPlanificada = codigoPlanificada;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	@Override
	public String toString() {
		return "ActividadPlanificada [codigoPlanificada=" + codigoPlanificada + ", fecha=" + fecha + ", limitePlazas="
				+ limitePlazas + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + ", codigoMonitor="
				+ codigoMonitor + ", codigoActividad=" + codigoActividad + "]";
	}

}
