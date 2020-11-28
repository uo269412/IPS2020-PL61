package sprint1.business.dominio.centroDeportes.planificaciones;

import sprint1.business.Programa;
import sprint1.business.dominio.centroDeportes.actividades.Actividad;
import sprint1.business.dominio.centroDeportes.actividades.ActividadPlanificada;

public class Conflicto {

	private ActividadPlanificada a1;
	private ActividadPlanificada a2;
	private String nombreActividad1 = null;
	private String nombreActividad2 = null;
	
	public Conflicto(ActividadPlanificada a1, ActividadPlanificada a2) {
		this.a1 = a1;
		this.a2 = a2;
	}
	
	public void conseguirNombreActividades(Programa p) {
		for(Actividad a: p.getActividades()) {
			if(a.getCodigo().equals(a1.getCodigoActividad())) {
				setNombreActividadAfectada(a.getNombre());
			}
			
			if(a.getCodigo().equals(a2.getCodigoActividad())) {
				setNombreActividadConflictiva(a.getNombre());
			}
		}
	}
	
	public ActividadPlanificada getActividadAfectada() {
		return a1;
	}

	public void setActividadAfectada(ActividadPlanificada a1) {
		this.a1 = a1;
	}

	public ActividadPlanificada getActividadConflictiva() {
		return a2;
	}

	public void setActividadConflictiva(ActividadPlanificada a2) {
		this.a2 = a2;
	}

	public String getNombreActividadAfectada() {
		return nombreActividad1;
	}

	public void setNombreActividadAfectada(String nombreActividad1) {
		this.nombreActividad1 = nombreActividad1;
	}

	public String getNombreActividadActividadConflictiva() {
		return nombreActividad2;
	}

	public void setNombreActividadConflictiva(String nombreActividad2) {
		this.nombreActividad2 = nombreActividad2;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CONFLICTO: Actividad ");
		sb.append(getNombreActividadAfectada());
		sb.append(" desarrollada el ");
		sb.append(a1.getDia()+"/"+a1.getMes()+"/"+a1.getAño());
		sb.append(" de " +  a1.getHoraInicio() + " a " + a1.getHoraFin());
		sb.append(" en la instalación " + a1.getCodigoInstalacion() + "\n");
		sb.append("\t\t entra en conflicto con\n");
		sb.append("\t\t Actividad ");
		sb.append(getNombreActividadAfectada());
		sb.append(" desarrollada el ");
		sb.append(a2.getDia()+"/"+a2.getMes()+"/"+a2.getAño());
		sb.append(" de " +  a2.getHoraInicio() + " a " + a2.getHoraFin());
		sb.append(" en la instalación " + a2.getCodigoInstalacion() + "\n");
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a1 == null) ? 0 : a1.hashCode());
		result = prime * result + ((a2 == null) ? 0 : a2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conflicto other = (Conflicto) obj;
		if (a1 == null) {
			if (other.a1 != null)
				return false;
		} else if (!a1.equals(other.a1))
			return false;
		if (a2 == null) {
			if (other.a2 != null)
				return false;
		} else if (!a2.equals(other.a2))
			return false;
		return true;
	}
	
	
}
