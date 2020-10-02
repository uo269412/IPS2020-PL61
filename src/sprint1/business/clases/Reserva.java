package sprint1.business.clases;

import java.util.Date;

public class Reserva {
	String id_cliente;
	String codigo_actividad;

	Date fecha_reserva;

	public Reserva(String id_cliente, String codigo_actividad, Date fecha_reserva) {
		this.id_cliente = id_cliente;
		this.codigo_actividad = codigo_actividad;
		this.fecha_reserva = fecha_reserva;
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

	public Date getFecha_reserva() {
		return fecha_reserva;
	}

	public void setFecha_reserva(Date fecha_reserva) {
		this.fecha_reserva = fecha_reserva;
	}

}
