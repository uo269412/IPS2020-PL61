package sprint1.business.clases;

public abstract class Cliente {

	String id_cliente;
	String nombre;
	String apellido;

	public Cliente(String id_cliente, String nombre, String apellido) {
		this.id_cliente = id_cliente;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public String getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(String id_cliente) {
		this.id_cliente = id_cliente;
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

}
