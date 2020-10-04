package sprint1.business.clases;

import java.util.LinkedList;
import java.util.List;

public class Actividad {
	
	public static final int INTENSIDAD_BAJA = 0;
	public static final int INTENSIDAD_MODERADA = 1;
	public static final int INTENSIDAD_ALTA = 2;
	//en un futuro se cambiar�n por un enum
	
	private String codigo;
	private String nombre;
	private int horaInicio;
	private int horaFin;
	private int limitePlazas;
	private int intensidad;
	private List<Recurso> recursosRequeridos;

	public Actividad(String codigo, String nombre, int horaInicio, int horaFin, int limitePlazas, int intensidad) {
		setCodigo(codigo);
		setNombre(nombre);
		setHoraInicio(horaInicio);
		setHoraFin(horaFin);
		setLimitePlazas(limitePlazas);
		setIntensidad(intensidad);
		recursosRequeridos = new LinkedList<>();
	}
	
	private void setIntensidad(int intensidad) {
		if(intensidad != INTENSIDAD_BAJA && intensidad != INTENSIDAD_MODERADA && intensidad != INTENSIDAD_ALTA)
			throw new IllegalArgumentException("El nivel de intensidad no es v�lido");
		else
			this.intensidad = intensidad;
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
	public String toString() { //conviene imprimir tambi�n los recursos necesarios
		return "Actividad [codigo=" + codigo + ", nombre=" + nombre + ", horaInicio=" + horaInicio + ", horaFin="
				+ horaFin + ", limitePlazas=" + limitePlazas + ", intensidad=" + intensidad + "]";
	}
	
	 @Override
	    public boolean equals(Object obj) {
	        if (obj == null) {
	            return false;
	        }

	        if (obj.getClass() != this.getClass()) {
	            return false;
	        }

	        final Actividad other = (Actividad) obj;
	        if ((this.codigo == null) ? (other.codigo != null) : !this.codigo.equals(other.codigo)) {
	            return false;
	        }

	        return true;
	    }
	
	public void a�adirRecurso(Recurso recurso) {
		recursosRequeridos.add(recurso);
	}
	
	public int getIntensidad() {
		return this.intensidad;
	}
	
	

}
