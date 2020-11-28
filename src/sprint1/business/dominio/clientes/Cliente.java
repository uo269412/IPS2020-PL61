package sprint1.business.dominio.clientes;

import java.util.UUID;

public abstract class Cliente {

	String id_cliente;

	public Cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}

	public Cliente() {
		this.id_cliente = UUID.randomUUID().toString();
	}

	public String getId_cliente() {
		return id_cliente;
	}
}
