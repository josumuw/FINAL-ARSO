package pasarela.servicios;

import pasarela.modelo.Claims;

public interface IServicioUsuarios {

    public Claims autenticarLogin(String username, String password) throws ServicioUsuariosException;
    public Claims autenticarOAuth2(String id) throws ServicioUsuariosException;
}
