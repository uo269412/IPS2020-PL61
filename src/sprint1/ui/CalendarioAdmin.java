package sprint1.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;

import sprint1.business.clases.ActividadPlanificada;
import sprint1.business.clases.Programa;

public class CalendarioAdmin extends Calendario{

	private Programa p;
	private ActionListener al;
	
	public CalendarioAdmin(Programa currentPrograma) {
		super();
		this.p = currentPrograma;
		
		al = new AsignarActividades();
		prepararBotones();
	}
	
	private void prepararBotones() {
		for(Component c: pnDiasMes.getComponents()) {
			JButton b = (JButton)c;
			int[] fecha = parseFecha(b);
			int dia = fecha[0];
			int mes = fecha[1];
			int año = fecha[2];
			
			int acc = 0;
			try {
				for(ActividadPlanificada a: p.getActividadesPlanificadasDia(dia, mes, año)) {
					acc += a.getHoraFin() - a.getHoraInicio();
				}
				if(acc >= 15) { //15 son las horas de apertura del centro de deportes
					botonDiaOcupado(b);
				} else {
					botonDiaLibre(b);
				}
			} catch (SQLException e) {
				System.out.println("Ha habido algún problema con la base de datos, comuníquelo al administrador del sistema o a un desarrollador");
			}
		}
	}
	
	private void botonDiaLibre(JButton bt) {
		bt.setEnabled(true);
		bt.addActionListener(al);
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
			JButton button = (JButton)arg0.getSource();
			int[] fecha = parseFecha(button);
			int dia = fecha[0];
			int mes = fecha[1];
			int año = fecha[2];
			AsignarActividadDialog aad = new AsignarActividadDialog(button, getPrograma(), dia, mes, año);
			aad.setVisible(true);
		}
		
	}
	
	private int[] parseFecha(JButton b) {
		int[] fecha = new int[3];
		
		fecha[0] = Integer.parseInt(b.getText());
		fecha[1] = Integer.parseInt((String)cbMeses.getSelectedItem());
		fecha[2] = (Integer)cbAños.getSelectedItem();
		
		return fecha;
	}
}
