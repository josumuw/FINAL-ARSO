package citybike.web.incidencia;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import citybike.servicio.IServicioIncidencias;
import dto.IncidenciaDTO;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

@Named
@ViewScoped
public class GestionarIncidenciasWeb implements Serializable{  

    private List<IncidenciaDTO> incidencias;
    
    private IncidenciaDTO incidenciaSeleccionada;
    
    private List<IncidenciaDTO> incidenciasSeleccionadas;

    
    private IServicioIncidencias servicioIncidencias;  

    @Inject
    protected FacesContext facesContext;

        

    public GestionarIncidenciasWeb() {     

    	servicioIncidencias = FactoriaServicios.getServicio(IServicioIncidencias.class);        

    }

    @PostConstruct
    public void init() {

        try {
        	// Obtenemos las incidencias abiertas automáticamente
			incidencias = servicioIncidencias.getIncidenciasAbiertas();
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public List<IncidenciaDTO> getIncidencias() {
		return incidencias;
	}
    
	public IncidenciaDTO getIncidenciaSeleccionada() {
		return incidenciaSeleccionada;
	}

	public void setIncidenciaSeleccionada(IncidenciaDTO incidenciaSeleccionada) {
		this.incidenciaSeleccionada = incidenciaSeleccionada;
	}

	public List<IncidenciaDTO> getIncidenciasSeleccionadas() {
		return incidenciasSeleccionadas;
	}

	public void setIncidenciasSeleccionadas(List<IncidenciaDTO> incidenciasSeleccionadas) {
		this.incidenciasSeleccionadas = incidenciasSeleccionadas;
	}

	public void setIncidencias(List<IncidenciaDTO> incidencias) {
		this.incidencias = incidencias;
	}

	public void cancelarIncidencia() {
		String motivo = incidenciaSeleccionada.getMotivo();
		boolean resultado = servicioIncidencias.cancelarIncidencia(incidenciaSeleccionada, motivo);
		facesContext.addMessage(null,
			    resultado ? new FacesMessage(FacesMessage.SEVERITY_INFO, "", "OPERACIÓN REALIZADA: incidencia cancelada.")
			            : new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "ERROR: operacion NO cancelada."));
	}
	
	public void asignarIncidencia() {
		String operario = incidenciaSeleccionada.getOperario();
		boolean resultado = servicioIncidencias.asignarIncidencia(incidenciaSeleccionada, operario);
		facesContext.addMessage(null,
			    resultado ? new FacesMessage(FacesMessage.SEVERITY_INFO, "", "OPERACIÓN REALIZADA: incidencia asignada.")
			            : new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "ERROR: operacion NO cancelada."));
	}
	
	public void resolverIncidencia() {
		String motivo = incidenciaSeleccionada.getMotivo();
		Boolean reparada = incidenciaSeleccionada.getReparada();
		boolean resultado = servicioIncidencias.resolverIncidencia(incidenciaSeleccionada, motivo, reparada);
		facesContext.addMessage(null,
			    resultado ? new FacesMessage(FacesMessage.SEVERITY_INFO, "", "OPERACIÓN REALIZADA: incidencia resuelta.")
			            : new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "ERROR: operacion NO cancelada."));
	}
}
