package pasarela.servicios;

import java.io.IOException;

import org.springframework.stereotype.Service;

import pasarela.dto.ClaimsDTO;
import pasarela.dto.LoginDTO;
import pasarela.dto.OAuth2DTO;
import pasarela.modelo.Claims;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Service
public class ServicioUsuarios implements IServicioUsuarios {

    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:5277")
	    .addConverterFactory(JacksonConverterFactory.create()).build();
    IUsuariosRestClient service = retrofit.create(IUsuariosRestClient.class);

    @Override
    public Claims autenticarLogin(String username, String password) throws ServicioUsuariosException {
	try {
	    LoginDTO request = new LoginDTO(username, password);
	    Response<ClaimsDTO> response = service.autenticarLogin(request).execute();
	    ClaimsDTO dto = response.body();
	    Claims claims = dto.toEntity();
	    return claims;
	} catch (Exception e) {
	    throw new ServicioUsuariosException("Error en la comunicación con el servicio Usuarios.");
	}
    }

    @Override
    public Claims autenticarOAuth2(String id) throws ServicioUsuariosException {
	try {
	    OAuth2DTO request = new OAuth2DTO(id);
	    Response<ClaimsDTO> response = service.autenticarOAuth2(request).execute();
	    ClaimsDTO dto = response.body();
	    Claims claims = dto.toEntity();
	    return claims;
	} catch (Exception e) {
	    throw new ServicioUsuariosException("Error en la comunicación con el servicio Usuarios.");
	}
    }

}
