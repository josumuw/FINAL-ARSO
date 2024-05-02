package estaciones.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import estaciones.modelo.Bicicleta;

@NoRepositoryBean
public interface RepositorioBicicletas extends PagingAndSortingRepository<Bicicleta, String> {

	Page<Bicicleta> findByidEstacion(String idEstacion, Pageable pageable);
	
	Page<Bicicleta> findDisponiblesByidEstacion(String idEstacion, Pageable pageable);
}
