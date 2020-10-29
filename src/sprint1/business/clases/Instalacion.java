package sprint1.business.clases;

import java.util.UUID;

public class Instalacion implements Comparable<Instalacion> {
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

	public Instalacion(String nombre, double precioHora) {
		this(UUID.randomUUID().toString(), nombre, precioHora);
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

	public String getCodigoInstalacion() {
		return codigo_instalacion;
	}

	@Override
	public String toString() {
		return "Instalacion [codigo_instalacion=" + codigo_instalacion + ", nombre=" + nombre + ", precioHora="
				+ precioHora + "]";
	}

	@Override
	public int compareTo(Instalacion arg0) {
		return getNombre().compareTo(arg0.getNombre());
	}

}