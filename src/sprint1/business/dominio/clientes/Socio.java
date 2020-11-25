package sprint1.business.dominio.clientes;

import java.util.UUID;

public class Socio extends Cliente implements Comparable<Socio> {

	private String nombre;
	private String apellido;

	public Socio(String id_cliente, String nombre, String apellido) {
		super(id_cliente);
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public Socio(String nombre, String apellido) {
		this.id_cliente = UUID.randomUUID().toString();
		this.nombre = nombre;
		this.apellido = apellido;
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
		return (nombre + " " + apellido);
	}

	@Override
	public int compareTo(Socio arg0) {
		if (getNombre().compareTo(arg0.getNombre()) == 0) {
			return getApellido().compareTo(arg0.getApellido());
		}
		return getNombre().compareTo(arg0.getNombre());
	}

}
