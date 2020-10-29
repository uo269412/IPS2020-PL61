package sprint1.business.clases;

public abstract class Cliente {

	String id_cliente;

	public Cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getId_cliente() {
		return id_cliente;
	}

}
