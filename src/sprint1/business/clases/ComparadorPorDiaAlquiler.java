package sprint1.business.clases;

import java.util.Comparator;

public class ComparadorPorDiaAlquiler implements Comparator<Alquiler> {

	@Override
	public int compare(Alquiler a1, Alquiler a2) {
		if (a1.getA�o() > a2.getA�o())
			return 1;
		else if (a1.getA�o() < a2.getA�o())
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
