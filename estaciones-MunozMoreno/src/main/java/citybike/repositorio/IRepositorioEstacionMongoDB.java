package citybike.repositorio;

import java.util.List;

import citybike.modelo.Estacion;
import repositorio.RepositorioException;
import repositorio.RepositorioString;

public interface IRepositorioEstacionMongoDB extends RepositorioString<Estacion> {

    public Estacion getEstacionPuestosLibres() throws RepositorioException;

    public List<Estacion> getEstacionesCerca(double longitud, double latitud) throws RepositorioException;

    public List<Estacion> getEstacionesConMasSitiosTuristicos() throws RepositorioException;

}
