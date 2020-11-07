package sprint1.business.clases;

import java.util.UUID;

public class Alquiler implements Comparable<Alquiler> {

	private String id_alquiler;
	private String id_instalacion;
	private String id_cliente;
	private int dia;
	private int mes;
	private int año;
	private int horaInicio;
	private int horaFin;

	public Alquiler(String id_alquiler, String id_instalacion, String id_cliente, int dia, int mes, int año,
			int horaInicio, int horaFin) {
		this.id_alquiler = id_alquiler;
		this.id_instalacion = id_instalacion;
		this.id_cliente = id_cliente;
		this.dia = dia;
		this.mes = mes;
		this.año = año;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	public Alquiler(String id_instalacion, String id_cliente, int dia, int mes, int año, int horaInicio, int horaFin) {
		this(UUID.randomUUID().toString(), id_instalacion, id_cliente, dia, mes, año, horaInicio, horaFin);
	}

	public String getId_alquiler() {
		return id_alquiler;
	}

	public void setId_alquiler(String id_alquiler) {
		this.id_alquiler = id_alquiler;
	}

	public String getId_instalacion() {
		return id_instalacion;
	}

	public void setId_instalacion(String id_instalacion) {
		this.id_instalacion = id_instalacion;
	}

	public String getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAño() {
		return año;
	}

	public void setAño(int año) {
		this.año = año;
	}

	public int getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(int horaInicio) {
		this.horaInicio = horaInicio;
	}

	public int getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(int horaFin) {
		this.horaFin = horaFin;
	}

	@Override
	public String toString() {
		return "Alquiler desde " + horaInicio + " hasta " + horaFin;
	}
	

	@Override
	public int compareTo(Alquiler arg0) {
		if (getAño() == arg0.getAño()) {
			if (getMes() == arg0.getMes()) {
				if (getDia() == arg0.getDia()) {
					if (horaInicio == arg0.getHoraInicio()) {
						if (horaFin == arg0.getHoraFin()) {
							return 0;
						} else if (horaFin < arg0.getHoraFin()) {
							return -1;
						} else {
							return 1;
						}
					} else if (horaInicio < arg0.getHoraInicio()) {
						return -1;
					} else {
						return 1;
					}
				}
			} else if (getMes() < arg0.getMes()) {
				return -1;
			} else {
				return 1;
			}
		} else if (getAño() < arg0.getAño()) {
			return -1;
		}
		return 1;
	}

}
