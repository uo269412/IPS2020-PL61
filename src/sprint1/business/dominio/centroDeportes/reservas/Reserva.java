package sprint1.business.dominio.centroDeportes.reservas;

public class Reserva {
	String id_cliente;
	String codigo_actividad;

	public Reserva(String id_cliente, String codigo_actividad) {
		this.id_cliente = id_cliente;
		this.codigo_actividad = codigo_actividad;
	}

	public String getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getCodigo_actividad() {
		return codigo_actividad;
	}

	public void setCodigo_actividad(String codigo_actividad) {
		this.codigo_actividad = codigo_actividad;
	}

	@Override
	public String toString() {
		return "Reserva [id_cliente=" + id_cliente + ", codigo_actividad=" + codigo_actividad + "]";
	}
}
