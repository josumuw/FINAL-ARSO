package estaciones.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import estaciones.modelo.Estacion;

@NoRepositoryBean
public interface RepositorioEstaciones extends PagingAndSortingRepository<Estacion, String> {

	Page<Estacion> findAll(Pageable pageable);
}
