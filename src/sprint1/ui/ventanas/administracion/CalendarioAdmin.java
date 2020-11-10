package sprint1.ui.ventanas.administracion;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;

import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Programa;
import sprint1.ui.ventanas.socio.Calendario;

public class CalendarioAdmin extends Calendario {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Programa p;
	private ActionListener al;

	public CalendarioAdmin(AdminWindow parent) {
		super();
		setTitle("Calendario");
		this.p = parent.getParent().getPrograma();
		al = new AsignarActividades();
		setLocationRelativeTo(parent);
		iniciarMes("Noviembre", 2020);
		
	}

	private void prepararBotones() {
		int dia = 0;
		int mes = 0;
		int año = 0;
		for (Component c : pnDiasMes.getComponents()) {
			JButton b = (JButton) c;
			if (b.isEnabled()) {
				int[] fecha = parseFecha(b);
				dia = fecha[0];
				mes = fecha[1];
				año = fecha[2];
				if (dia != 0 && mes != 0 && año != 0) {
					int acc = 0;
					try {
						for (ActividadPlanificada a : p.getActividadesPlanificadasDia(dia, mes, año)) {
							acc += a.getHoraFin() - a.getHoraInicio();
						}
						if (acc >= 15) { // 15 son las horas de apertura del centro de deportes
							botonDiaOcupado(b);
						} else {
							botonDiaLibre(b);
						}
					} catch (SQLException e) {
						System.out.println(
								"Ha habido algún problema con la base de datos, comuníquelo al administrador del sistema o a un desarrollador");
					}
				}
			}
			
		}
	}
	
	@Override
	public void iniciarMes(String mes, int año) {	
		super.iniciarMes(mes, año);
		prepararBotones();
	}

	private void botonDiaLibre(JButton bt) {
		bt.setEnabled(true);
		if(bt.getActionListeners().length <= 1) {
			if(bt.getActionListeners().length == 1) {
				bt.getActionListeners()[0] = al;
			} else {
				bt.addActionListener(al);
			}
			
		}
	}

	private void botonDiaOcupado(JButton bt) {
		bt.setEnabled(false);
	}

	private Programa getPrograma() {
		return this.p;
	}

	private class AsignarActividades implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JButton button = (JButton) arg0.getSource();
			int[] fecha = parseFecha(button);
			int dia = fecha[0];
			int mes = fecha[1];
			int año = fecha[2];
			AsignarActividadDialog aad = new AsignarActividadDialog(getMe(), getPrograma(), dia, mes, año);
			aad.setLocationRelativeTo(getMe());
			aad.setModal(true);
			aad.setVisible(true);
			aad.mostrarActividadesPlanificadasDia();
			
		}
	}
	
	private CalendarioAdmin getMe() {
		return this;
	}

	private int[] parseFecha(JButton b) {
		int[] fecha = new int[3];
		fecha[0] = Integer.parseInt(b.getText());
		fecha[1] = cbMeses.getSelectedIndex() + 1;
		fecha[2] = (Integer) cbAños.getSelectedItem();

		return fecha;
	}

}
