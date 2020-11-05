package sprint1.business.clases;

import java.util.UUID;

public class Recurso {
	private String id_recurso; // formato:
	private String nombre_recurso;
	private String codigo_actividad = null;
	private String codigo_instalacion;

//	public Recurso(String nombre, String codigo_instalacion) {
//		this.nombre_recurso = nombre;
//		this.codigo_instalacion = codigo_instalacion;
//		this.id_recurso = "REC_" + nombre.substring(0, 3).toUpperCase()
//				+ codigo_instalacion.substring(0, 3).toUpperCase();
//	}

	public Recurso(String nombre, String codigo_instalacion) {
		this.nombre_recurso = nombre;
		this.codigo_instalacion = codigo_instalacion;
		this.id_recurso = UUID.randomUUID().toString();
	}

	public String getActividad() {
		return codigo_actividad;
	}

	public void setActividad(String codigoActividad) {
		this.codigo_actividad = codigoActividad;
	}

	public String getIdRecurso() {
		return this.id_recurso;
	}

	public String getInstalacion() {
		return this.codigo_instalacion;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Recurso [id_recurso=");
		sb.append(id_recurso);
		sb.append(", nombre_recurso=");
		sb.append(nombre_recurso);
		sb.append(", codigo_actividad=");
		if (codigo_actividad == null) {
			sb.append("no tiene");
		} else {
			sb.append(codigo_actividad);
		}
		sb.append(", codigo_instalacion=");
		sb.append(codigo_instalacion);
		sb.append("]");

		return sb.toString();
	}
}
