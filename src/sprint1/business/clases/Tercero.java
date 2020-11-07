package sprint1.business.clases;

public class Tercero extends Cliente implements Comparable<Tercero> {

	private String nombre;

	public Tercero(String id_cliente, String nombre) {
		super(id_cliente);
		this.nombre = nombre;
	}
	
	public Tercero(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getId() {
		return super.id_cliente;
	}

	@Override
	public String toString() {
		return "Tercero [nombre=" + nombre + "]";
	}

	@Override
	public int compareTo(Tercero arg0) {
		return getNombre().compareTo(arg0.getNombre());
	}

}