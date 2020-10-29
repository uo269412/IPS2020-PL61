package sprint1.business.clases;

public class Instalacion {
	private String codigo_instalacion;
	private String nombre;
	private double precioHora;

	public Instalacion(String codigo_instalacion, String nombre) {
		this.codigo_instalacion = codigo_instalacion;
		this.nombre = nombre;
	}

	public Instalacion(String codigo_instalacion, String nombre, double precioHora) {
		this(codigo_instalacion, nombre);
		this.precioHora = precioHora;
	}

	public String getCodigo() {
		return codigo_instalacion;
	}

	public String getNombre() {
		return nombre;
	}
	
	public double getPrecioHora() {
		return precioHora;
	}

	@Override
	public String toString() {
		return nombre + ", id: " + codigo_instalacion;
	}

	public String getCodigoInstalacion() {
		return codigo_instalacion;
	}

}
