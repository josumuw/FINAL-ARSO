package alquileres.rest;

import java.time.LocalDateTime;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import alquileres.adapters.LocalDateTimeAdapter;
import alquileres.dto.AlquilerDTO;
import alquileres.dto.HistorialUsuarioDTO;
import alquileres.dto.ReservaDTO;
import alquileres.modelo.Alquiler;
import alquileres.modelo.Reserva;
import alquileres.servicio.HistorialUsuario;
import alquileres.servicio.IServicioAlquileres;
import alquileres.util.AlquilerConverter;
import alquileres.util.HistorialUsuarioConverter;
import alquileres.util.ReservaConverter;
import servicio.FactoriaServicios;

@Path("alquileres")
public class AlquileresControladorRest {

    private IServicioAlquileres servicio = FactoriaServicios.getServicio(IServicioAlquileres.class);

    @Context
    private UriInfo uriInfo;

    private final Gson gson;

    public AlquileresControladorRest() {
	gson = new GsonBuilder().serializeNulls().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
		.create();
    }

    /*
     * CURL reservar
     * curl -X POST
     * http://localhost:8081/api/alquileres/usuarios/{idUsuario}/reservas
     * -H 'Content-Type: application/json'
     * -H "Authorization: {Bearer code}"
     * -d '{"idBicicleta"="{idBicicleta}"}"
     */
    @POST
    @Path("/usuarios/{idUsuario}/reservas")
    // @RolesAllowed({ "ADMIN", "CLIENT" })
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearReserva(@PathParam("idUsuario") String idUsuario, JsonObject json) throws Exception {
	if (json.has("idBicicleta")) {
	    JsonElement element = json.get("idBicicleta");
	    String idBicicleta = element.getAsString();
	    Reserva reserva = servicio.reservar(idUsuario, idBicicleta);
	    ReservaDTO reservaDTO = ReservaConverter.convertToDTO(reserva);
	    return Response.status(Response.Status.OK).entity(gson.toJson(reservaDTO)).build();
	}
	throw new IllegalArgumentException("Petición sin idBicicleta.");
    }

    /*
     * CURL confirmarReserva
     * curl -X POST http://localhost:8081/api/usuarios/{idUsuario}/alquileres
     * -H 'Content-Type: application/json'
     * -H "Authorization: {Bearer code}"
     * -d '{}'
     */

    /*
     * CURL alquilar
     * curl -X POST http://localhost:8081/api/usuarios/{idUsuario}/alquileres
     * -H 'Content-Type: application/json'
     * -H "Authorization: {Bearer code}"
     * -d '{"idBicicleta"="{idBicicleta}"}"
     */

    @POST
    @Path("/usuarios/{idUsuario}/alquileres")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearAlquiler(@PathParam("idUsuario") String idUsuario, JsonObject json) throws Exception {
	Alquiler alquiler = null;
	if (json.has("idBicicleta")) {
	    // Si se proporciona idBicicleta, se realiza la operación de alquilar
	    JsonElement element = json.get("idBicicleta");
	    String idBicicleta = element.getAsString();
	    alquiler = servicio.alquilar(idUsuario, idBicicleta);
	} else {
	    // Si no se proporciona idBicicleta, se realiza la operación de confirmar
	    // reserva
	    alquiler = servicio.confirmarReserva(idUsuario);
	}

	AlquilerDTO alquilerDTO = AlquilerConverter.convertToDTO(alquiler);

	if (alquilerDTO != null) {
	    return Response.status(Response.Status.OK).entity(gson.toJson(alquilerDTO)).build();
	} else {
	    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
    }

    /*
     * CURL getHisorialUsuario
     * curl -X GET http://localhost:8081/api/usuarios/{idUsuario}
     * -H 'Content-Type: application/json'
     * -H "Authorization: {Bearer code}"
     */
    @GET
    @Path("/usuarios/{idUsuario}")
    @PermitAll
    // @RolesAllowed({ "ADMIN", "CLIENT" })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistorialUsuario(@PathParam("idUsuario") String idUsuario) throws Exception {
	HistorialUsuario historial = servicio.historialUsuario(idUsuario);
	HistorialUsuarioDTO historialDTO = HistorialUsuarioConverter.convertToDTO(historial);
	return Response.status(Response.Status.OK).entity(gson.toJson(historialDTO)).build();
    }

    /*
     * CURL liberarBloqueo
     * curl -X PATCH http://localhost:8081/api/usuarios/{idUsuario}/alquileres
     * -H 'Content-Type: application/json'
     * -H "Authorization: {Bearer code}"
     * -d '{"idEstacion"="{idEstacion}"}"
     */
    @PATCH
    @Path("/usuarios/{idUsuario}/alquileres")
    @PermitAll
    // @RolesAllowed({ "ADMIN", "CLIENT" })
    @Consumes(MediaType.APPLICATION_JSON)
    public Response dejarBicicleta(@PathParam("idUsuario") String idUsuario, JsonObject json) throws Exception {
	if (json.has("idEstacion")) {
	    JsonElement element = json.get("idEstacion");
	    String idEstacion = element.getAsString();
	    // Llamada al servicio dejarBicicleta
	    servicio.dejarBicicleta(idUsuario, idEstacion);
	    return Response.status(Response.Status.OK).build();
	}
	return Response.status(Response.Status.BAD_REQUEST).build();
    }

    /*
     * CURL liberarBloqueo
     * curl -X PATCH http://localhost:8081/api/usuarios/{idUsuario}/reservas
     * -H "Authorization: {Bearer code}"
     */
    @PATCH
    @Path("/usuarios/{idUsuario}/reservas")
    @PermitAll
    // @RolesAllowed({ "ADMIN" })
    @Consumes(MediaType.APPLICATION_JSON)
    public Response liberarBloqueo(@PathParam("idUsuario") String idUsuario) throws Exception {
	// Llamada al servicio liberarBloqueo
	servicio.liberarBloqueo(idUsuario);
	return Response.status(Response.Status.OK).build();

    }
}