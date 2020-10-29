package sprint1.business.clases;

import java.util.UUID;

public class Monitor implements Comparable<Monitor> {
	String codigoMonitor;
	String nombre;
	String apellido;

	public Monitor(String codigoMonitor, String nombre, String apellido) {
		this.codigoMonitor = codigoMonitor;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public Monitor(String nombre, String apellido) {
		this(UUID.randomUUID().toString(), nombre, apellido);
	}

	public String getCodigoMonitor() {
		return codigoMonitor;
	}

	public void setCodigoMonitor(String codigoMonitor) {
		this.codigoMonitor = codigoMonitor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Override
	public String toString() {
		return nombre + " " + apellido + " con código de monitor: " + codigoMonitor;
	}

	@Override
	public int compareTo(Monitor arg0) {
		if (getNombre().compareTo(arg0.getNombre()) == 0) {
			return getApellido().compareTo(arg0.getApellido());
		}
		return getNombre().compareTo(arg0.getNombre());
	}
}