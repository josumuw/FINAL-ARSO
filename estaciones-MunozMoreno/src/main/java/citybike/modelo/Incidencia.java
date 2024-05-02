package citybike.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "incidencia")
public class Incidencia implements Serializable {
    private static Integer contIncidencias = 1;

	// Atributos
    @Id
    @Column(name = "id")
    private Integer id;
	@Lob
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "fecha_creacion", columnDefinition = "DATE")
	private Date fechaCreacion;
	@Lob
	@Column(name = "motivo_cierre")
	private String motivoCierre;
	@Column(name = "fecha_cierre", columnDefinition = "DATE")
	private Date fechaCierre;
	@Enumerated(EnumType.STRING)
	@Column(name = "estado")
	private EstadoIncidencia estado;
	@Column(name = "operario")
	private String operario;

	@ManyToOne
	@JoinColumn(name = "bicicleta_fk")
	private Bicicleta bicicleta;

	// Constructor
	public Incidencia() {

	}

	public Incidencia(String descripcion, Bicicleta bicicleta) {
		this.id = contIncidencias;
		this.descripcion = descripcion;
		this.fechaCreacion = new Date();
		this.estado = EstadoIncidencia.PENDIENTE;
		this.bicicleta = bicicleta;
		Incidencia.contIncidencias++;
	}

	// Mëtodos get/set
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public EstadoIncidencia getEstado() {
		return estado;
	}

	public void setEstado(EstadoIncidencia estado) {
		this.estado = estado;
	}

	public String getOperario() {
		return operario;
	}

	public void setOperario(String operario) {
		this.operario = operario;
	}

	public Bicicleta getBicicleta() {
		return bicicleta;
	}

	public void setBicicleta(Bicicleta bicicleta) {
		this.bicicleta = bicicleta;
	}

	public String getMotivoCierre() {
		return motivoCierre;
	}

	public void setMotivoCierre(String motivoCierre) {
		this.motivoCierre = motivoCierre;
	}

	@Override
	public String toString() {
		return "Incidencia [id=" + id + ", descripcion=" + descripcion + ", fechaCreacion=" + fechaCreacion
				+ ", fechaCierre=" + fechaCierre + ", estado=" + estado + ", operario=" + operario + "]";
	}

}
