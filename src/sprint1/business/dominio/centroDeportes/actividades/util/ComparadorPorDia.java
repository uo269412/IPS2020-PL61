package sprint1.business.dominio.centroDeportes.actividades.util;

import java.util.Comparator;

import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;

public class ComparadorPorDia implements Comparator<ActividadPlanificada> {

	@Override
	public int compare(ActividadPlanificada a1, ActividadPlanificada a2) {
		if (a1.getAño() > a2.getAño())
			return 1;
		else if (a1.getAño() < a2.getAño())
			return -1;
		else {
			if (a1.getMes() > a2.getMes()) 
				return 1;
			else if (a1.getMes() < a2.getMes())
				return -1;
		}
		return a1.getDia() - a2.getDia();
	}

}
