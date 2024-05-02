package citybike.servicio;

import java.util.List;

import dto.IncidenciaDTO;
import repositorio.RepositorioException;

public interface IServicioIncidencias {
	IncidenciaDTO crearIncidencia(String idBici, String descripcion);
	// Gestión de incidencias
	boolean cancelarIncidencia(IncidenciaDTO incidencia, String motivo);
	boolean asignarIncidencia(IncidenciaDTO incidencia, String operario);
	boolean resolverIncidencia(IncidenciaDTO incidencia, String motivo, boolean reparada);
	// Recuperar incidencias abiertas
	List<IncidenciaDTO> getIncidenciasAbiertas() throws RepositorioException;
	
}
