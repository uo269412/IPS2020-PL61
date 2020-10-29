package sprint1.business.clases;

public class Tercero extends Cliente {

	private String nombre;

	public Tercero(String id_cliente, String nombre) {
		super(id_cliente);
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public String toString() {
		return "Tercero [nombre=" + nombre + "]";
	}

}
