package alquileres.servicio;

import java.io.IOException;

import alquileres.dto.EstacionInfoDTO;
import alquileres.retrofit.EstacionesRestClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ServicioEstaciones implements IServicioEstaciones {

    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080/")
	    .addConverterFactory(JacksonConverterFactory.create()).build();
    EstacionesRestClient service = retrofit.create(EstacionesRestClient.class);

    @Override
    public void isPlazasDisponibles(String idEstacion) throws ServicioEstacionesException, RequisitosException {
	try {
	    Response<EstacionInfoDTO> response = service.getEstacionInfo(idEstacion).execute();
	    if (response.isSuccessful() && response.body() != null) {
		EstacionInfoDTO estacionInfoDTO = response.body();
		if (estacionInfoDTO.getNumPuestos() < 1)
		    throw new RequisitosException("No hay plazas disponibles.");
	    } else {
		throw new ServicioEstacionesException("Error de comunicación con el servicio de estaciones.");
	    }
	} catch (IOException e) {
	    throw new ServicioEstacionesException("Error de conexión con el servicio de estaciones.", e);
	}
    }

    @Override
    public void estacionarBicicleta(String idBicicleta, String idEstacion)
	    throws ServicioEstacionesException, RequisitosException {
	this.isPlazasDisponibles(idEstacion);
	try {
	    Response<Void> response = service.estacionarBicicleta(idEstacion, idBicicleta).execute();
	    if (!response.isSuccessful())
		throw new ServicioEstacionesException("Error de comunicación con el servicio de estaciones.");
	} catch (IOException e) {
	    throw new ServicioEstacionesException("Error de conexión con el servicio de estaciones.", e);
	}
    }

}
