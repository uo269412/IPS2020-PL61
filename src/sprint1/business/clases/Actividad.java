package sprint1.business.clases;

public class Actividad {
	String codigo;
	String nombre;
	int horaInicio;
	int horaFin;
	int limitePlazas;

	public Actividad(String codigo, String nombre, int horaInicio, int horaFin, int limitePlazas) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.limitePlazas = limitePlazas;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public int getLimitePlazas() {
		return limitePlazas;
	}

	public void setLimitePlazas(int limitePlazas) {
		this.limitePlazas = limitePlazas;
	}

	@Override
	public String toString() {
		return "Actividad [codigo=" + codigo + ", nombre=" + nombre + ", horaInicio=" + horaInicio + ", horaFin="
				+ horaFin + ", limitePlazas=" + limitePlazas + "]";
	}
	
	

}
