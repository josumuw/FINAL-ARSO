package citybike.web.bicicleta;


import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import citybike.servicio.IServicioEstaciones;
import citybike.servicio.ServicioEstaciones;
import dto.BicicletaDTO;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

@Named
@RequestScoped
public class BuscadorBicicletaWeb implements Serializable {

    private IServicioEstaciones servicioEstaciones;

    private double latitud;
    private double longitud;
    private List<BicicletaDTO> listaBicicletas;
    
    protected FacesContext facesContext;
    
    public BuscadorBicicletaWeb() {
    	servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
    }

    public void setServicioEstaciones(ServicioEstaciones servicioEstaciones) {
        this.servicioEstaciones = servicioEstaciones;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public List<BicicletaDTO> getListaBicicletas() {
        return listaBicicletas;
    }

    public void setListaBicicletas(List<BicicletaDTO> listaBicicletas) {
        this.listaBicicletas = listaBicicletas;
    }

    public void buscarBicicletas() {
		try {
			listaBicicletas = servicioEstaciones.getBicicletasCercanas(latitud, longitud);
			//listaBicicletas = (listaBicicletas != null) ? listaBicicletas : Collections.emptyList();
		} catch (EntidadNoEncontrada | RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
