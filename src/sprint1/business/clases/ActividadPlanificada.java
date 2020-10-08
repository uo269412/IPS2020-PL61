package sprint1.business.clases;

import java.sql.Date;

public class ActividadPlanificada {
	private String codigoActividad;
	private int dia;
	private int mes;
	private int año;
	private int limitePlazas;
	private int horaInicio;
	private int horaFin;
	private String codigoMonitor;
	private String codigoPlanificada;

	public ActividadPlanificada(int dia, int mes, int año, int limitePlazas, int horaInicio, int horaFin,
			String codigoMonitor, String codigoActividad) {
		this.codigoPlanificada = "P-" + codigoActividad + "/" + codigoMonitor;
		this.dia = dia;
		this.mes = mes;
		this.año = año;
		this.limitePlazas = limitePlazas;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.codigoMonitor = codigoMonitor;
		this.codigoActividad = codigoActividad;
	}

	public ActividadPlanificada(String codigoActividad, int dia, int mes, int año, int limitePlazas, int horaInicio, int horaFin,
			String codigoMonitor, String codigoPlanificada) {
		this.codigoPlanificada = codigoPlanificada;
		this.dia = dia;
		this.mes = mes;
		this.año = año;
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
		

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAño() {
		return año;
	}

	public void setAño(int año) {
		this.año = año;
	}

	@Override
	public String toString() {
		return "ActividadPlanificada [codigoActividad=" + codigoActividad + ", dia=" + dia + ", mes=" + mes + ", año="
				+ año + ", limitePlazas=" + limitePlazas + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin
				+ ", codigoMonitor=" + codigoMonitor + ", codigoPlanificada=" + codigoPlanificada + "]";
	}


}