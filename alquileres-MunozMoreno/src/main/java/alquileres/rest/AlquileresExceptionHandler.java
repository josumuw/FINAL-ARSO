package alquileres.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import alquileres.servicio.RequisitosException;
import alquileres.servicio.ServicioEstacionesException;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@Provider
public class AlquileresExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception excepcion) {
        if (excepcion instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Petición inválida: " + excepcion.getMessage()).build();
        } else if (excepcion instanceof RepositorioException || excepcion instanceof EntidadNoEncontrada) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno del servidor al acceder al repositorio").build();
        } else if (excepcion instanceof RequisitosException) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Petición no cumple las relgas: " + excepcion.getMessage()).build();
        } else if (excepcion instanceof ServicioEstacionesException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(excepcion.getMessage()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Recurso no encontrado.").build();
        }
    }
}
