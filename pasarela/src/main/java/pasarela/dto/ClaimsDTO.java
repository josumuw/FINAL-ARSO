package pasarela.dto;

import pasarela.modelo.Claims;

public class ClaimsDTO {

    private String id;
    private String nombreCompleto;
    private String Rol;

    public ClaimsDTO() {

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
    
    public Claims toEntity() {
	Claims c = new Claims();
	c.setId(id);
	c.setNombreCompleto(nombreCompleto);
	c.setRol(Rol);
	return c;
    }
    
    public static ClaimsDTO fromEntity(Claims claims) {
	ClaimsDTO dto = new ClaimsDTO();
	dto.setId(claims.getId());
	dto.setNombreCompleto(claims.getNombreCompleto());
	dto.setRol(claims.getRol());
	return dto;
    }

}
