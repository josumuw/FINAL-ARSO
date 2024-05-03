package pasarela.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pasarela.dto.ClaimsDTO;
import pasarela.dto.LoginDTO;
import pasarela.dto.OAuth2DTO;
import pasarela.modelo.Claims;
import pasarela.servicios.IServicioUsuarios;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private IServicioUsuarios servicio;

    @Autowired
    public AuthController(IServicioUsuarios servicio) {
	this.servicio = servicio;
    }

    @PostMapping("login")
    public ClaimsDTO autenticarLogin(@RequestBody LoginDTO request) throws Exception {
	Claims claims  = servicio.autenticarLogin(request.getId(), request.getPassword());
	ClaimsDTO response = ClaimsDTO.fromEntity(claims);
	return response;
    }

    @PostMapping("oauth2")
    public ClaimsDTO autenticarOAuth2(@RequestBody OAuth2DTO request) throws Exception {
	Claims claims = servicio.autenticarOAuth2(request.getId());
	ClaimsDTO response = ClaimsDTO.fromEntity(claims);
	return response;
    }

}
