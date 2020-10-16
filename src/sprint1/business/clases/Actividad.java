package sprint1.business.clases;

import java.util.LinkedList;
import java.util.List;

public class Actividad {

	public static final int INTENSIDAD_BAJA = 0;
	public static final int INTENSIDAD_MODERADA = 1;
	public static final int INTENSIDAD_ALTA = 2;
	// en un futuro se cambiar�n por un enum

	private String codigo;
	private String nombre;
	private int intensidad;
	private Recurso[] recursosRequeridos;

	public Actividad(String codigo, String nombre, int intensidad, Recurso... recursos) {
		setCodigo(codigo);
		setNombre(nombre);
		setIntensidad(intensidad);
		recursosRequeridos = recursos;
	}
	
	public Actividad(String codigo, String nombre, int intensidad) {
		setCodigo(codigo);
		setNombre(nombre);
		setIntensidad(intensidad);
		recursosRequeridos = new Recurso[0];
	}

//	public Actividad(String nombre, int intensidad) {
//		setCodigo(nombre.substring(0, 3).toUpperCase() + "_" + intensidad);
//		setNombre(nombre);
//		setIntensidad(intensidad);
//		recursosRequeridos = new LinkedList<>();
//	}

	private void setIntensidad(int intensidad) {
		if (intensidad != INTENSIDAD_BAJA && intensidad != INTENSIDAD_MODERADA && intensidad != INTENSIDAD_ALTA)
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

	@Override
	public String toString() { // conviene imprimir tambi�n los recursos necesarios
		return "Actividad [codigo=" + codigo + ", nombre=" + nombre + ", " + "intensidad=" + intensidad	+ "]";
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



	public int getIntensidad() {
		return this.intensidad;
	}
	
	public Recurso[] getRecursos() {
		return this.recursosRequeridos;
	}
	
	public boolean requiresRecursos()  {
		return recursosRequeridos.length > 0;
	}

}