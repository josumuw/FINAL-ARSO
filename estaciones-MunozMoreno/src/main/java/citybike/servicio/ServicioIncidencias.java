package citybike.servicio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import citybike.modelo.Bicicleta;
import citybike.modelo.EstadoIncidencia;
import citybike.modelo.Incidencia;
import citybike.repositorio.RepositorioBiciJPA;
import dto.IncidenciaDTO;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ServicioIncidencias implements IServicioIncidencias {

	private RepositorioBiciJPA repositorioBici = FactoriaRepositorios.getRepositorio(Bicicleta.class);
	IServicioEstaciones servicioEstacion = FactoriaServicios.getServicio(IServicioEstaciones.class);

	private IncidenciaDTO transformToDTO(Incidencia incidencia) {   
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        IncidenciaDTO rdto = new IncidenciaDTO(
        		incidencia.getBicicleta().getId(), 
        		incidencia.getId().toString(), 
        		incidencia.getEstado().toString(), 
        		formatoFecha.format(incidencia.getFechaCreacion()), 
        		incidencia.getDescripcion());
        return rdto;
    }
	
	private Incidencia transformFromDTOBasic(IncidenciaDTO incidenciaDTO) { 
		Incidencia incidencia = new Incidencia();
		incidencia.setDescripcion(incidenciaDTO.getDescripcion());
		// Formateamos la fecha de string a Date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			incidencia.setFechaCreacion(dateFormat.parse(incidenciaDTO.getFecha()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		incidencia.setId(Integer.parseInt(incidenciaDTO.getIdIncidencia()));
		return incidencia;
    }
	
	@Override
	public IncidenciaDTO crearIncidencia(String idBici, String descripcion) {
		try {
			Bicicleta bicicleta = repositorioBici.getById(idBici);
			// Crear incidencia
			Incidencia incidencia = new Incidencia(descripcion, bicicleta);
			// Establecer bicicleta como NO disponible
			bicicleta.setDisponible(false);
			// Añadir incidencia a lista de incidencias de bici
			bicicleta.addIncidencia(incidencia);
			repositorioBici.update(bicicleta);
			
			return transformToDTO(incidencia);
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
	public boolean cancelarIncidencia(IncidenciaDTO incidenciaDTO, String motivo) {
		String idBicicleta = incidenciaDTO.getIdBicicleta();
		Bicicleta bicicleta;
		try {
			bicicleta = repositorioBici.getById(idBicicleta);
			Incidencia incidencia = transformFromDTOBasic(incidenciaDTO);
			incidencia.setBicicleta(bicicleta);
			// Para la incidencia...
			// Cambiamos su estado a CANCELADO
			incidencia.setEstado(EstadoIncidencia.CANCELADO);
			// Establecemos el motivo de cierre
			incidencia.setMotivoCierre(motivo);
			// Establecemos la fecha de cierre
			incidencia.setFechaCierre(new Date());
			// La bicicleta se vuelve a considerar disponible
			bicicleta.setDisponible(true);
			// Actualizamos la incidencia
			List<Incidencia> incidencias = bicicleta.getIncidencias();
			incidencias.remove(incidencias.size()-1);
			incidencias.add(incidencia);
			bicicleta.setIncidencias(incidencias);
			repositorioBici.update(bicicleta);
			return true;
		} catch (EntidadNoEncontrada e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RepositorioException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean asignarIncidencia(IncidenciaDTO incidenciaDTO, String operario) {
		String idBicicleta = incidenciaDTO.getIdBicicleta();
		Bicicleta bicicleta;
		try {
			bicicleta = repositorioBici.getById(idBicicleta);
			Incidencia incidencia = transformFromDTOBasic(incidenciaDTO);
			incidencia.setBicicleta(bicicleta);
			// Para la incidencia...
			// Cambiamos su estado a ASIGNADA
			incidencia.setEstado(EstadoIncidencia.ASIGNADA);
			// Establecemos el operario asignado
			incidencia.setOperario(operario);
			// Actualizar incidencia en repo
			List<Incidencia> incidencias = bicicleta.getIncidencias();
			incidencias.remove(incidencias.size()-1);
			incidencias.add(incidencia);
			repositorioBici.update(bicicleta);
			// La deja e ocupar sitio en la estación (se retira de la estación)
			servicioEstacion.retirarBicicleta(bicicleta.getId());
			return true;
		} catch (EntidadNoEncontrada e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RepositorioException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean resolverIncidencia(IncidenciaDTO incidenciaDTO, String motivo, boolean reparada) {
		String idBicicleta = incidenciaDTO.getIdBicicleta();
		Bicicleta bicicleta;
		try {
			bicicleta = repositorioBici.getById(idBicicleta);
			Incidencia incidencia = transformFromDTOBasic(incidenciaDTO);
			incidencia.setBicicleta(bicicleta);
			// Para la incidencia...
			// Cambiamos su estado a RESULTA
			incidencia.setEstado(EstadoIncidencia.RESUELTA);
			// Establecemos el motivo de cierre
			incidencia.setMotivoCierre(motivo);
			// Establecemos la fecha de cierre
			incidencia.setFechaCierre(new Date());
			// Actualizar incidencia en repo
			List<Incidencia> incidencias = bicicleta.getIncidencias();
			incidencias.remove(incidencias.size()-1);
			incidencias.add(incidencia);
			repositorioBici.update(bicicleta);
			// Dependiendo del resultado (reparada o no)...
			if (reparada) servicioEstacion.estacionarBicicleta(bicicleta.getId());
			else servicioEstacion.darDeBajaBicicleta(bicicleta.getId(), motivo);
			return true;
		} catch (EntidadNoEncontrada e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RepositorioException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<IncidenciaDTO> getIncidenciasAbiertas() {
		try {
			List<Incidencia> incidencias = repositorioBici.getIncidenciasAbiertas();
			List<IncidenciaDTO> incidenciasDTO = new ArrayList<>();
			for (Incidencia i : incidencias) {
				incidenciasDTO.add(transformToDTO(i));
			}
			return incidenciasDTO;
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
