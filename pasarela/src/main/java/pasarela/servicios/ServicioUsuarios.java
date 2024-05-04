package pasarela.servicios;

import java.util.Map;

import org.springframework.stereotype.Service;

import pasarela.dto.LoginDTO;
import pasarela.dto.OAuth2DTO;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Service
public class ServicioUsuarios implements IServicioUsuarios {

    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:5277")
	    .addConverterFactory(JacksonConverterFactory.create()).build();
    IUsuariosRestClient service = retrofit.create(IUsuariosRestClient.class);

    @Override
    public Map<String, String> autenticarLogin(String username, String password) throws ServicioUsuariosException {
	try {
	    LoginDTO request = new LoginDTO(username, password);
	    Response<Map<String, String>> response = service.autenticarLogin(request).execute();
	    Map<String, String> claims = response.body();
	    return claims;
	} catch (Exception e) {
	    throw new ServicioUsuariosException("Error en la comunicación con el servicio Usuarios.");
	}
    }

    @Override
    public Map<String, String> autenticarOAuth2(String id) throws ServicioUsuariosException {
	try {
	    OAuth2DTO request = new OAuth2DTO(id);
	    Response<Map<String, String>> response = service.autenticarOAuth2(request).execute();
	    Map<String, String> claims = response.body();
	    return claims;
	} catch (Exception e) {
	    throw new ServicioUsuariosException("Error en la comunicación con el servicio Usuarios.");
	}
    }

}
