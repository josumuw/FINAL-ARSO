package persistencia.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import alquileres.modelo.Alquiler;
import utils.DateToLocalDateTimeConversion;

@Entity
@Table(name = "alquiler")
public class AlquilerEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "id_bicicleta")
	private String idBicicleta;
	@Column(name = "fecha_inicio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date inicio;
	@Column(name = "fecha_fin")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fin;

	@ManyToOne
	@JoinColumn(name = "usuario_fk")
	private UsuarioEntidad usuario;

	public AlquilerEntidad() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public UsuarioEntidad getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntidad usuario) {
		this.usuario = usuario;
	}

	public Alquiler toAlquiler() {
		Alquiler alquiler = new Alquiler();
		alquiler.setIdBicicleta(this.idBicicleta);
		alquiler.setInicio(DateToLocalDateTimeConversion.toLocalDateTime(this.inicio));
		alquiler.setFin(DateToLocalDateTimeConversion.toLocalDateTime(this.fin));
		return alquiler;
	}

	public static AlquilerEntidad fromAlquiler(Alquiler alquiler) {
		AlquilerEntidad alquilerEntidad = new AlquilerEntidad();
		alquilerEntidad.idBicicleta = alquiler.getIdBicicleta();
		alquilerEntidad.inicio = DateToLocalDateTimeConversion.toDate(alquiler.getInicio());
		alquilerEntidad.fin = DateToLocalDateTimeConversion.toDate(alquiler.getFin());
		return alquilerEntidad;
	}

}
