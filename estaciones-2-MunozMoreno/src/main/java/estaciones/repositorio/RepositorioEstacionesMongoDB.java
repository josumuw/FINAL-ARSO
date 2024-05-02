package estaciones.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import estaciones.modelo.Estacion;

public interface RepositorioEstacionesMongoDB extends RepositorioEstaciones, MongoRepository<Estacion, String> {

	
}
