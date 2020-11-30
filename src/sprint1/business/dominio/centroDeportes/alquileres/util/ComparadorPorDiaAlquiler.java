package sprint1.business.dominio.centroDeportes.alquileres.util;

import java.util.Comparator;

import sprint1.business.dominio.centroDeportes.alquileres.Alquiler;

public class ComparadorPorDiaAlquiler implements Comparator<Alquiler> {

	@Override
	public int compare(Alquiler a1, Alquiler a2) {
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
