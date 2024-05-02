package citybike.modelo;

import java.io.Serializable;

public class Usuario implements Serializable {
	
	private String nombre;
	private String username;
	private String password;
	private String rol;

	public String getNombre() {

		return nombre;

	}

	public void setNombre(String nombre) {

		this.nombre = nombre;

	}


	public String getUsername() {

		return username;

	}

	public void setUsername(String username) {

		this.username = username;

	}

	public String getPassword() {

		return password;

	}

	public void setPassword(String password) {

		this.password = password;

	}
	
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", username=" + username + ", password=" + password + ", rol=" + rol + "]";
	}
	
	
}
