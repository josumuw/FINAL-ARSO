package pasarela.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pasarela.dto.LoginDTO;
import pasarela.dto.OAuth2DTO;
import pasarela.servicios.IServicioUsuarios;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private IServicioUsuarios servicio;
    private JwtUtil jwtUtil;
    
    @Autowired
    public AuthController(IServicioUsuarios servicio, JwtUtil jwtUtil) {
	this.servicio = servicio;
	this.jwtUtil = jwtUtil;
    }

    @PostMapping("login")
    public ResponseEntity<String> autenticarLogin(@RequestBody LoginDTO request) throws Exception {
	Map<String, String> claims  = servicio.autenticarLogin(request.getId(), request.getPassword());
	// Verificar si se autenticó correctamente
        if (claims != null && !claims.isEmpty()) {
            String token = jwtUtil.generateToken(claims);
            // Devolver el token JWT como parte del cuerpo de la respuesta
            return ResponseEntity.ok(token);
        } else {
            // Devolver un mensaje de error en caso de falla de autenticación
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autenticación");
        }
    }
    
    @PostMapping("oauth2")
    public ResponseEntity<String> autenticarOAuth2(@RequestBody OAuth2DTO request) throws Exception {
	Map<String, String> claims = servicio.autenticarOAuth2(request.getId());
	// Verificar si se autenticó correctamente
        if (claims != null && !claims.isEmpty()) {
            String token = jwtUtil.generateToken(claims);
            // Devolver el token JWT como parte del cuerpo de la respuesta
            return ResponseEntity.ok(token);
        } else {
            // Devolver un mensaje de error en caso de falla de autenticación
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autenticación");
        }
    }
}
