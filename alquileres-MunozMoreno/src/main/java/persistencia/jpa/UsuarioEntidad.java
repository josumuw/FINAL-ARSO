package persistencia.jpa;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alquileres.modelo.Usuario;
import repositorio.Identificable;

@Entity
@Table(name = "usuario")
public class UsuarioEntidad implements Identificable {

	@Id
	@Column(name = "id")
	private String id;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReservaEntidad> reservas;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AlquilerEntidad> alquileres;

	public UsuarioEntidad() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ReservaEntidad> getReservas() {
		return reservas;
	}

	public void setReservas(List<ReservaEntidad> reservas) {
		this.reservas = reservas;
	}

	public List<AlquilerEntidad> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(List<AlquilerEntidad> alquileres) {
		this.alquileres = alquileres;
	}

	public Usuario toUsuario() {
		Usuario usuario = new Usuario();
		usuario.setId(this.getId());
		usuario.setAlquileres(converterList(this.getAlquileres(), AlquilerEntidad::toAlquiler));
		usuario.setReservas(converterList(this.getReservas(), ReservaEntidad::toReserva));
		return usuario;
	}

	public static UsuarioEntidad fromUsuario(Usuario usuario) {
		UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
		usuarioEntidad.setId(usuario.getId());
		// Configuración de Alquileres en UsuarioEntidad
	    List<AlquilerEntidad> alquileresEntidad = converterList(usuario.getAlquileres(), AlquilerEntidad::fromAlquiler);
	    for (AlquilerEntidad alquilerEntidad : alquileresEntidad) {
	        alquilerEntidad.setUsuario(usuarioEntidad);
	    }
	    usuarioEntidad.setAlquileres(alquileresEntidad);
	    // Configuración de Reservas en UsuarioEntidad
	    List<ReservaEntidad> reservasEntidad = converterList(usuario.getReservas(), ReservaEntidad::fromReserva);
	    for (ReservaEntidad reservaEntidad : reservasEntidad) {
	        reservaEntidad.setUsuario(usuarioEntidad);
	    }
	    usuarioEntidad.setReservas(reservasEntidad);
	    return usuarioEntidad;
	}

    // Método de apoyo para convertir listas
    private static <T, U> List<U> converterList(List<T> sourceList, Function<T, U> converter) {
        if (sourceList == null) {
            return null;
        }
        return sourceList.stream().map(converter).collect(Collectors.toList());
    }
}
