package pasarela.servicios;

import java.util.Map;

public interface IServicioUsuarios {

    public Map<String, String> autenticarLogin(String username, String password) throws ServicioUsuariosException;
    public Map<String, String> autenticarOAuth2(String id) throws ServicioUsuariosException;
}
