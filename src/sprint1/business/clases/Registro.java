package sprint1.business.clases;

import java.util.UUID;

public class Registro implements Comparable<Registro> {

	private String id_registro;
	private String id_alquiler;
	private int hora_entrada;
	private int hora_salida;
	private boolean alquilerPagado;
	private boolean socioPresentado;

	public Registro(String id_registro, String id_alquiler, int hora_entrada, int hora_salida, boolean alquilerPagado,
			boolean socioPresentado) {
		super();
		this.id_registro = id_registro;
		this.id_alquiler = id_alquiler;
		this.hora_entrada = hora_entrada;
		this.hora_salida = hora_salida;
		this.alquilerPagado = alquilerPagado;
		this.socioPresentado = socioPresentado;
	}

	public Registro(String id_alquiler, int hora_entrada, int hora_salida, boolean alquilerPagado,
			boolean socioPresentado) {
		this(UUID.randomUUID().toString(), id_alquiler, hora_entrada, hora_salida, alquilerPagado, socioPresentado);
	}

	public Registro(String id_alquiler) {
		this.id_registro = UUID.randomUUID().toString();
		this.id_alquiler = id_alquiler;
	}

	public String getId_registro() {
		return id_registro;
	}

	public void setId_registro(String id_registro) {
		this.id_registro = id_registro;
	}

	public String getId_alquiler() {
		return id_alquiler;
	}

	public void setId_alquiler(String id_alquiler) {
		this.id_alquiler = id_alquiler;
	}

	public int getHora_entrada() {
		return hora_entrada;
	}

	public void setHora_entrada(int hora_entrada) {
		this.hora_entrada = hora_entrada;
	}

	public int getHora_salida() {
		return hora_salida;
	}

	public void setHora_salida(int hora_salida) {
		this.hora_salida = hora_salida;
	}

	public boolean isAlquilerPagado() {
		return alquilerPagado;
	}

	public void setAlquilerPagado(boolean alquilerPagado) {
		this.alquilerPagado = alquilerPagado;
	}

	public boolean isSocioPresentado() {
		return socioPresentado;
	}

	public void setSocioPresentado(boolean socioPresentado) {
		this.socioPresentado = socioPresentado;
	}

	@Override
	public String toString() {
		return "Registro [id_registro=" + id_registro + ", id_alquiler=" + id_alquiler + ", hora_entrada="
				+ hora_entrada + ", hora_salida=" + hora_salida + ", alquilerPagado=" + alquilerPagado
				+ ", socioPresentado=" + socioPresentado + "]";
	}

	@Override
	public int compareTo(Registro arg0) {
		if (getHora_entrada() < arg0.getHora_entrada()) {
			return -1;
		} else if (getHora_entrada() == arg0.getHora_entrada()) {
			if (getHora_salida() < arg0.getHora_salida()) {
				return -1;
			} else if (getHora_salida() == getHora_salida()) {
				return 0;
			} else {
				return 1;
			}
		} else {
			return 1;
		}
	}
}
