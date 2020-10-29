package sprint1.business.clases;

public class Socio extends Cliente {

	private String nombre;
	private String apellido;

	public Socio(String id_cliente, String nombre, String apellido) {
		super(id_cliente);
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
		return ("Socio " + nombre + " " + apellido);
	}

}
