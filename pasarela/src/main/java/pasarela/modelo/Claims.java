package pasarela.modelo;

public class Claims {

    private String id;
    private String nombreCompleto;
    private String Rol;

    public Claims() {

    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getNombreCompleto() {
	return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
	this.nombreCompleto = nombreCompleto;
    }

    public String getRol() {
	return Rol;
    }

    public void setRol(String rol) {
	Rol = rol;
    }

}
