package pasarela.dto;

public class LoginDTO {

    private String id;
    private String password;

    public LoginDTO() {
	
    }
    
    public LoginDTO(String id, String password) {
	this.id = id;
	this.password = password;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

}
