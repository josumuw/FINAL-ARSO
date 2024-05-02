package estaciones.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import estaciones.modelo.Bicicleta;

public interface RepositorioBicicletasMongoDB extends RepositorioBicicletas, MongoRepository<Bicicleta, String> {

}
