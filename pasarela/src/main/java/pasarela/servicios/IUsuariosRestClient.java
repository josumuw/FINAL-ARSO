package pasarela.servicios;

import java.util.Map;

import pasarela.dto.LoginDTO;
import pasarela.dto.OAuth2DTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IUsuariosRestClient {

    @POST("/api/usuarios/login")
    Call<Map<String, String>> autenticarLogin(@Body LoginDTO request);

    @POST("/api/usuarios/oauth2")
    Call<Map<String, String>> autenticarOAuth2(@Body OAuth2DTO request);

}
