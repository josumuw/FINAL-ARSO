package citybike.servicio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import citybike.modelo.Bicicleta;
import citybike.modelo.Estacion;
import citybike.modelo.Historico;
import citybike.repositorio.RepositorioEstacionMongoDB;
import citybike.repositorio.RepositorioHistoricoMongoDB;
import dto.BicicletaDTO;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ServicioEstaciones implements IServicioEstaciones {

	private RepositorioEstacionMongoDB repoEstacion = FactoriaRepositorios.getRepositorio(Estacion.class);
	private RepositorioHistoricoMongoDB repoHistorico = FactoriaRepositorios.getRepositorio(Historico.class);
	private Repositorio<Bicicleta, String> repoBicicleta = FactoriaRepositorios.getRepositorio(Bicicleta.class);
	private ISitiosTuristicos servicio = FactoriaServicios.getServicio(ISitiosTuristicos.class);

	@Override
	public String crear(String nombre, int numPuestos, String dirPostal, String longitud, String latitud)
			throws RepositorioException {
		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("nombre: no debe ser nulo ni vacio");

		if (numPuestos < 1)
			throw new IllegalArgumentException("numPuestos: debe ser mayor que cero");

		if (dirPostal == null || dirPostal.isEmpty())
			throw new IllegalArgumentException("dirPostal: no debe ser nula ni vacia");

		if (longitud == null || longitud.isEmpty())
			throw new IllegalArgumentException("longitud cierre: no debe ser nula ni vacia");

		if (latitud == null || latitud.isEmpty())
			throw new IllegalArgumentException("latitud cierre: no debe ser nula ni vacia");

		Point location = new Point(new Position(Double.parseDouble(latitud), Double.parseDouble(longitud)));
		Estacion estacion = new Estacion(nombre, numPuestos, dirPostal, location);

		String id = repoEstacion.add(estacion);
		return id;

	}

	@Override
	public List<SitioTuristicoResumen> getSitiosTuristicos(String id) throws RepositorioException {
		try {
			// Obtenemos la Estación con id

			Estacion estacion = repoEstacion.getById(id);

			List<Double> coordenadas = estacion.getLocation().getPosition().getValues();
			String latitud = Double.toString(coordenadas.get(1));
			String longitud = Double.toString(coordenadas.get(0));
			return servicio.getSitiosTuristicosGeo(latitud, longitud);

		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setSitiosTuristicos(String id, Collection<SitioTuristicoResumen> sitiosTuristicos)
			throws RepositorioException {
		try {
			Estacion estacion = repoEstacion.getById(id);
			Set<SitioTuristicoResumen> newSitiosTuristicos = new HashSet<>(sitiosTuristicos);
			Set<SitioTuristicoResumen> currentSitiosTuristicos = estacion.getSitiosTuristicos();
			currentSitiosTuristicos.addAll(newSitiosTuristicos);
			estacion.setSitiosTuristicos(newSitiosTuristicos);
			repoEstacion.update(estacion);
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Estacion getEstacion(String id) throws RepositorioException {
		try {
			return repoEstacion.getById(id);
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String altaBicicleta(String modelo, String idEstacion) throws RepositorioException {
		try {
			// Obtener la estación por ID
			Estacion estacion = repoEstacion.getById(idEstacion);

			// Verificar si hay espacio disponible en la estación
			if (estacion.getNumPuestos() > 0) {
				// Generar un identificador único para la bicicleta

				// Crear la bicicleta y establecer la fecha de alta
				Bicicleta bicicleta = new Bicicleta(modelo);

				// Cambiar bici a disponible
				bicicleta.setDisponible(true);

				// Guardar bicicleta en repo
				repoBicicleta.add(bicicleta);

				// Estacionar la bicicleta en la estación
				estacionarBicicleta(bicicleta.getId(), idEstacion);

				// Retornar el identificador de la bicicleta
				return bicicleta.getId();
			} else {
				throw new RepositorioException(
						"No hay espacio disponible en la estación para estacionar la bicicleta.");
			}
		} catch (RepositorioException | EntidadNoEncontrada e) {
			throw new RepositorioException("Error al dar de alta la bicicleta en la estación.", e);
		}
	}

	@Override
	public void estacionarBicicleta(String idBicicleta) throws RepositorioException {
		// Solicitar a repoEstaciones una Estacion con plazas libres.
		String idEstacion = repoEstacion.getEstacionPuestosLibres().getId();
		estacionarBicicleta(idBicicleta, idEstacion);
	}

	@Override
	public void estacionarBicicleta(String idBicicleta, String idEstacion) throws RepositorioException {
		try {
			// Obtener la estación y la bicicleta por ID
			Estacion estacion = repoEstacion.getById(idEstacion);
			Bicicleta bicicleta = repoBicicleta.getById(idBicicleta);

			if (estacion.getNumPuestos() == 0) {
				throw new RepositorioException(
						"No hay espacio disponible en la estación para estacionar la bicicleta.");

			}

			// Verificar si la bicicleta está estacionada en la estación
			if (estacion.añadirBici(idBicicleta)) {
				// Actualizar la estación en el repositorio
				bicicleta.setDisponible(true);
				bicicleta.setIdEstacion(idEstacion);
				repoBicicleta.update(bicicleta);

				// Actualizar (añadir nuevo) Bicicleta/Estacion
				repoEstacion.update(estacion);

				// Actualizar idEstacion de Bicicleta
				bicicleta.setIdEstacion(idEstacion);
				repoBicicleta.update(bicicleta);

				// Actualizar (añadir nuevo) Historico Bicicleta/Estacion
				Historico historico = new Historico(idBicicleta, idEstacion);
				repoHistorico.add(historico);

			} else {
				throw new RepositorioException("La bicicleta ya está estacionada en la estación.");
			}
		} catch (RepositorioException | EntidadNoEncontrada e) {
			throw new RepositorioException("Error al estacionar la bicicleta en la estación.", e);
		}

	}

	@Override
	public void retirarBicicleta(String idBicicleta) throws RepositorioException {
		try {
			// Obtener bicicleta por id
			Bicicleta bicicleta = repoBicicleta.getById(idBicicleta);
			String idEstacion = bicicleta.getIdEstacion();
			Estacion estacion = repoEstacion.getById(idEstacion);
			bicicleta.setIdEstacion(null);
			repoBicicleta.update(bicicleta);
			// Quitar bici de repo
			estacion.quitarBici(idBicicleta);
			repoEstacion.update(estacion);
			// Actualizar historico
			// Obtenenmos el historico bici/estacion sin cerrar (sin fechaFin)
			Historico historico = repoHistorico.getHistoricoAbierto(idBicicleta, idEstacion);
			// Lo cerramos (le ponemos fechaFin)
			historico.setFechaFin(LocalDateTime.now());
			// Actualizamos en repo historico
			repoHistorico.update(historico);
			// Aunque hay distintas formas de "cerrar el historico" obtamos por esta para implenetar una constula en el repoHistorico, aunque probablemente sea poco eficiente. 
			// Otra implementación posible sería añadir un atribto idHistorico en Bicicleta para poder consultar de manera más ágil para cerrarlo.
		} catch (RepositorioException | EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void darDeBajaBicicleta(String idBicicleta, String motivo) throws RepositorioException, EntidadNoEncontrada {
		try {
			// Obtenemos la bicicleta por id
			Bicicleta bicicleta = repoBicicleta.getById(idBicicleta);
			if (bicicleta != null) {
				// Si existe bicicleta...
				// Actualizamos bicicleta:
				// Establecemos el motivo de baja
				bicicleta.setMotivoBaja(motivo);
				// Establecemos la fecha de baja
				bicicleta.setFechaBaja(new Date());
				// Establecemos bici a NO disponible
				bicicleta.setDisponible(false);
				// Actualizamos bicicleta en repo
				repoBicicleta.update(bicicleta);
				// Retiramos la bicicleta de la estacion
				retirarBicicleta(idBicicleta);
			}
		} catch (RepositorioException e) {
			throw new RepositorioException("Error al dar de baja la bicicleta.", e);

		} catch (EntidadNoEncontrada e) {
			throw new EntidadNoEncontrada("Entidad no encontrada.");
		}

	}

	@Override
	public List<BicicletaDTO> getBicicletasCercanas(double latitud, double longitud)
			throws RepositorioException, EntidadNoEncontrada {
		List<Bicicleta> bicicletasCercanas = new ArrayList<>();

		try {
			// Obtener estaciones cercanas
			List<Estacion> estacionesCercanas = repoEstacion.getEstacionesCerca(latitud, longitud);

			for (Estacion estacion : estacionesCercanas) {
				// Obtener bicicletas por estación

				for (String idBicicleta : estacion.getBicicletas()) {
					Bicicleta bicicleta = repoBicicleta.getById(idBicicleta);
					if (bicicleta.isDisponible())
						// Establecemos el nombre de la estación en lugar del id para mayor compresión por parte del cliente
						bicicletasCercanas.add(bicicleta);
				}
			}
			List<BicicletaDTO> bicicletasCercanasDTO = new ArrayList<>();
			for (Bicicleta bicicleta  : bicicletasCercanas) {
				bicicletasCercanasDTO.add(transformToDTOBasic(bicicleta));
			}
			return bicicletasCercanasDTO;

			// Obtener bicicletas por estaciones
		} catch (RepositorioException e) {
			throw new RepositorioException("Error al obtener las bicicletas cercanas.", e);
		}
	}

	@Override
	public List<Estacion> getEstacionesConSitiosTuristicosCercanas()
			throws RepositorioException {
		try {
			return repoEstacion.getEstacionesConMasSitiosTuristicos();
		} catch (RepositorioException e) {

			throw new RepositorioException("Error al estacionar la bicicleta en la estación.", e);
		}

	}

	private BicicletaDTO transformToDTOBasic(Bicicleta bicicleta) {
		return new BicicletaDTO(bicicleta.getId(), bicicleta.getModelo(), bicicleta.getIdEstacion());
	}
}
