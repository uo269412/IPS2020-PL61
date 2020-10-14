package sprint1.business.clases;

public class Socio extends Cliente {

	public Socio(String id_cliente, String nombre, String apellido) {
		super(id_cliente, nombre, apellido);
	}

	public Socio(String nombre, String apellido) {
		super(nombre, apellido);
		this.id_cliente = "S-" + nombre + "_" + apellido;
	}


}
