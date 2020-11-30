package sprint1.business.clases;

import java.util.UUID;

public class Instalacion implements Comparable<Instalacion> {
	private String codigo_instalacion;
	private String nombre;
	private double precioHora;
	private int estado;
	
	public static final int DISPONIBLE = 1;
	public static final int CERRADA = 0;

//	public Instalacion(String codigo_instalacion, String nombre) {
//		this.codigo_instalacion = codigo_instalacion;
//		this.nombre = nombre;
//	}

	public Instalacion(String codigo_instalacion, String nombre, double precioHora, int estado) {
		this.codigo_instalacion = codigo_instalacion;
		this.nombre = nombre;
		this.precioHora = precioHora;
		this.estado = estado;
	}

	public Instalacion(String nombre, double precioHora, int estado) {
		this(UUID.randomUUID().toString(), nombre, precioHora, estado);
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

	
//	@Override
//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		sb.append(getNombre() + " (" + getPrecioHora() + "€ la hora). ");
//		if(estado == DISPONIBLE) {
//			sb.append("DISPONIBLE");
//		} else {
//			sb.append("CERRADA");
//		}
//		
//		return sb.toString();
//	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getNombre());
		return sb.toString();
	}
	
	public String toDebug() {
		StringBuilder sb = new StringBuilder();
		sb.append("Instalacion [codigo_instalacion=" + codigo_instalacion + ", nombre=" + nombre + ", precioHora="
				+ precioHora);
		if(estado == DISPONIBLE) {
			sb.append(", estado=DISPONIBLE]");
		} else {
			sb.append(", estado=CERRADA]");
		}
		
		return sb.toString();
	}

	@Override
	public int compareTo(Instalacion arg0) {
		return getNombre().compareTo(arg0.getNombre());
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
}