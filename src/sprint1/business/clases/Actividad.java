package sprint1.business.clases;

import java.util.LinkedList;
import java.util.List;

public class Actividad {
	
	public static final int INTENSIDAD_BAJA = 0;
	public static final int INTENSIDAD_MODERADA = 1;
	public static final int INTENSIDAD_ALTA = 2;
	//en un futuro se cambiarán por un enum
	
	private String codigo;
	private String nombre;
	private int intensidad;
	private List<Recurso> recursosRequeridos;
	
	public Actividad(String codigo, String nombre, int intensidad) {
		setCodigo(codigo);
		setNombre(nombre);
		setIntensidad(intensidad);
		recursosRequeridos = new LinkedList<>();
	}

	public Actividad(String nombre, int intensidad) {
		setCodigo(nombre + intensidad);
		setNombre(nombre);
		setIntensidad(intensidad);
		recursosRequeridos = new LinkedList<>();
	}
	
	private void setIntensidad(int intensidad) {
		if(intensidad != INTENSIDAD_BAJA && intensidad != INTENSIDAD_MODERADA && intensidad != INTENSIDAD_ALTA)
			throw new IllegalArgumentException("El nivel de intensidad no es válido");
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
	public String toString() { //conviene imprimir también los recursos necesarios
		return "Actividad [codigo=" + codigo + ", nombre=" + nombre + ", horaInicio="  + ", intensidad=" + intensidad + "]";
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
	
	public void añadirRecurso(Recurso recurso) {
		recursosRequeridos.add(recurso);
	}
	
	public int getIntensidad() {
		return this.intensidad;
	}
	
	

}
