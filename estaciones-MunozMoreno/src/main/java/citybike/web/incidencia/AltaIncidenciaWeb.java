package citybike.web.incidencia;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import citybike.servicio.IServicioIncidencias;
import citybike.web.locale.ActiveLocale;
import dto.IncidenciaDTO;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

@Named
@ViewScoped
public class AltaIncidenciaWeb implements Serializable {

	private String idBici;
	private String descripcion;


	private IServicioIncidencias servicioIncidencias;
	
	@Inject
	protected FacesContext facesContext;

	@Inject
	private ActiveLocale localeConfig;
	
	public AltaIncidenciaWeb() {

		servicioIncidencias = FactoriaServicios.getServicio(IServicioIncidencias.class);
	}
	
	

	public void altaIncidencia() throws RepositorioException {
		try {
			IncidenciaDTO resultado = servicioIncidencias.crearIncidencia(idBici, descripcion);
	        facesContext.addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_INFO, "",localeConfig.getBundle().getString("exitoIncidencia")));
	        /*
	        try {

	            facesContext.getExternalContext().redirect("detail.xhtml?id="+resultado);

	        } catch (IOException e) {

	            facesContext.addMessage(null,

	                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se ha podido navegar"));

	            e.printStackTrace();

	        }*/

	    } catch(IllegalArgumentException e) {

	        facesContext.addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_WARN, "", e.getMessage()));

	        e.printStackTrace();

	    }

	}

	public String getIdBici() {
		return idBici;
	}



	public void setIdBici(String idBici) {
		this.idBici = idBici;
	}

	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
}
