package citybike.repositorio;

import citybike.modelo.Historico;
import repositorio.RepositorioException;

public interface IRepositorioHistoricoMongo {

	public Historico getHistoricoAbierto(String idBici, String idEstacion) throws RepositorioException;
}
