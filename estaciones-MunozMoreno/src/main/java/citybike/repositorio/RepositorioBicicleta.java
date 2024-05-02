package citybike.repositorio;

import java.util.List;

import citybike.modelo.Bicicleta;
import citybike.modelo.Incidencia;
import repositorio.RepositorioException;
import repositorio.RepositorioString;

public interface RepositorioBicicleta extends RepositorioString<Bicicleta> {
    
	List<Incidencia> getIncidenciasAbiertas() throws RepositorioException;

}
