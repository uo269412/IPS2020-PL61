package sprint1.business.clases;

public class Monitor {
	String codigoMonitor;
	String nombre;
	String apellido;

	public Monitor(String codigoMonitor, String nombre, String apellido) {
		this.codigoMonitor = codigoMonitor;
		this.nombre = nombre;
		this.apellido = apellido;
	}
	
	public Monitor(String nombre, String apellido) {
		this.codigoMonitor = ("M-" + nombre + "_" + apellido.substring(0, 3));
		this.nombre = nombre;
		this.apellido = apellido;
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
}
