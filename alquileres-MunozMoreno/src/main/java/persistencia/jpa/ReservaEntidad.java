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

import alquileres.modelo.Reserva;
import utils.DateToLocalDateTimeConversion;

@Entity
@Table(name = "reserva")
public class ReservaEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "id_bicicleta")
	private String idBicicleta;
	@Column(name = "fecha_creacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creada;
	@Column(name = "fecha_caducidad")
	@Temporal(TemporalType.TIMESTAMP)
	private Date caducidad;

	@ManyToOne
	@JoinColumn(name = "usuario_fk")
	private UsuarioEntidad usuario;

	public ReservaEntidad() {

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

	public Date getCreada() {
		return creada;
	}

	public void setCreada(Date creada) {
		this.creada = creada;
	}

	public Date getCaducidad() {
		return caducidad;
	}

	public void setCaducidad(Date caducidad) {
		this.caducidad = caducidad;
	}

	public UsuarioEntidad getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntidad usuario) {
		this.usuario = usuario;
	}

	public Reserva toReserva() {
		Reserva reserva = new Reserva();
		reserva.setIdBicicleta(this.idBicicleta);
		reserva.setCreada(DateToLocalDateTimeConversion.toLocalDateTime(this.creada));
		reserva.setCaducidad(DateToLocalDateTimeConversion.toLocalDateTime(this.caducidad));
		return reserva;
	}

	public static ReservaEntidad fromReserva(Reserva reserva) {
		ReservaEntidad reservaEntidad = new ReservaEntidad();
		reservaEntidad.idBicicleta = reserva.getIdBicicleta();
		reservaEntidad.creada = DateToLocalDateTimeConversion.toDate(reserva.getCreada());
		reservaEntidad.caducidad = DateToLocalDateTimeConversion.toDate(reserva.getCaducidad());
		return reservaEntidad;
	}

}
