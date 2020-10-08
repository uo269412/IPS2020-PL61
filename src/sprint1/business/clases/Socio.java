package sprint1.business.clases;

import java.sql.Connection;
import java.sql.DriverManager;
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
		calendar.get(Calendar.HOUR_OF_DAY);
		for (Reserva reserva : reservas) {
			if (reserva.id_cliente.equals(reserva.id_cliente) && (calendar.get(Calendar.HOUR_OF_DAY) < actividad.getHoraInicio())) {
				reservas.remove(reserva);
				cancelarPlazaReserva(actividad.getCodigoPlanificada());
				actualizarPlazaReserva(actividad, +1);
			}
		}
	}

	private void cancelarPlazaReserva(String id_actividad) {
		try {
			Connection con = DriverManager
					.getConnection("jdbc:sqlite:C:\\Users\\javie\\Desktop\\master\\sprint1\\resources\\bdProject.db");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("DELETE FROM RESERVA WHERE id_cliente = " + getId_cliente()
					+ " AND codigo_actividad =" + id_actividad);
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error borrando la reserva");
		}
	}

	private void actualizarPlazaReserva(ActividadPlanificada actividad, int incremento) {
		try {
			Connection con = DriverManager
					.getConnection("jdbc:sqlite:C:\\Users\\javie\\Desktop\\master\\sprint1\\resources\\bdProject.db");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("UPDATE ACTIVIDAD_PLANIFICADA SET limitePlaza = " + actividad.getLimitePlazas()
					+ incremento + " WHERE codigo = " + actividad.getCodigoPlanificada());
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error borrando la reserva");
		}
	}

}
