package citybike.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import citybike.modelo.Bicicleta;
import citybike.modelo.EstadoIncidencia;
import citybike.modelo.Incidencia;
import repositorio.RepositorioException;
import repositorio.RepositorioJPA;
import utils.EntityManagerHelper;

public class RepositorioBiciJPA extends RepositorioJPA<Bicicleta> implements RepositorioBicicleta {

	@Override
	public Class<Bicicleta> getClase() {
		return Bicicleta.class;
	}

	@Override
	public String getNombre() {
		return getClase().getSimpleName();
	}

	@Override
	public List<Incidencia> getIncidenciasAbiertas() throws RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
		
			String queryString = "SELECT DISTINCT i " 
									+  " FROM Bicicleta b " 
									+ " INNER JOIN b.incidencias i"
									+ " WHERE i.estado = :estadoPendiente OR i.estado = :estadoAsignada";
			
			Query query = em.createQuery(queryString);
			query.setParameter("estadoPendiente", EstadoIncidencia.PENDIENTE);
	        query.setParameter("estadoAsignada", EstadoIncidencia.ASIGNADA);
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
		
			return query.getResultList();
		}catch(RuntimeException e) {
			throw new RepositorioException("Error buscando incidencias abiertas", e);
		}
	}

}
