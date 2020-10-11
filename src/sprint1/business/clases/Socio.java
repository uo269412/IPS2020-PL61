package sprint1.business.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Socio extends Cliente {

	public Socio(String id_cliente, String nombre, String apellido) {
		super(id_cliente, nombre, apellido);
	}

	public Socio(String nombre, String apellido) {
		super(nombre, apellido);
		this.id_cliente = "S-" + nombre + "_" + apellido;
	}

	public void anularReserva(ActividadPlanificada actividad, List<Reserva> reservas) {
		Calendar calendar = Calendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH);
		int año = calendar.get(Calendar.YEAR);
		for (Reserva reserva : reservas) {
			if (reserva.getCodigo_actividad().equals(actividad.getCodigoPlanificada())
					&& validarHora(actividad, hora, dia, mes, año)) {
				System.out.println("Estamos aquí");
				cancelarPlazaReserva(actividad.getCodigoPlanificada());
				actualizarPlazaReserva(actividad, +1);
			}
		}
	}

	private boolean validarHora(ActividadPlanificada actividad, int hora, int dia, int mes, int año) {
//		return hora < actividad.getHoraInicio();
		return true;
	}

	private void cancelarPlazaReserva(String id_actividad) {
		try {
			Connection con = DriverManager.getConnection(Programa.URL);
			PreparedStatement pst = con
					.prepareStatement("DELETE FROM RESERVA WHERE id_cliente = ? AND codigo_actividad = ?");
			pst.setString(1, getId_cliente());
			pst.setString(2, id_actividad);
			pst.execute();
			pst.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error borrando la reserva");
		}
	}

	private void actualizarPlazaReserva(ActividadPlanificada actividad, int incremento) {
		try {
			Connection con = DriverManager.getConnection(Programa.URL);
			Statement st = con.createStatement();
			PreparedStatement pst = con
					.prepareStatement("UPDATE ACTIVIDAD_PLANIFICADA SET limitePlazas = ? WHERE codigoPlanificada = ?");
			pst.setInt(1, actividad.getLimitePlazas());
			pst.setString(2, actividad.getCodigoPlanificada());
			pst.execute();
			pst.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error borrando la reserva");
		}
	}
}
