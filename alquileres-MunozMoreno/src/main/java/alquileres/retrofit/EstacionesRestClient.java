package alquileres.retrofit;

import alquileres.dto.EstacionInfoDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EstacionesRestClient {

    @GET("estaciones/{idEstacion}")
    Call<EstacionInfoDTO> getEstacionInfo(@Path("idEstacion") String idEstacion);

    @POST("estaciones/{idEstacion}/bicicletas/{idBicicleta}")
    Call<Void> estacionarBicicleta(@Path("idEstacion") String idEstacion, @Path("idBicicleta") String idBicicleta);
}