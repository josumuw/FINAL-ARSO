package estaciones.modelo;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bicicletas")
public class Bicicleta {

	@Id
	private String id;
	private String modelo;
	private LocalDateTime fechaAlta;
	private LocalDateTime fechaBaja;
	private String motivoBaja;
	private EstadoBicicleta estado;

	private String idEstacion;

	public Bicicleta(String modelo, String idEstacion) {
		this.modelo = modelo;
		this.idEstacion = idEstacion;
		this.fechaAlta = LocalDateTime.now();
		this.idEstacion = idEstacion;
		this.estado = EstadoBicicleta.DISPONIBLE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public LocalDateTime getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(LocalDateTime fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getMotivoBaja() {
		return motivoBaja;
	}

	public void setMotivoBaja(String motivoBaja) {
		this.motivoBaja = motivoBaja;
	}

	public EstadoBicicleta getEstado() {
		return estado;
	}

	public void setEstado(EstadoBicicleta estado) {
		this.estado = estado;
	}

	public String getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(String idEstacion) {
		this.idEstacion = idEstacion;
	}

	public boolean isEstacionable() {
		return (idEstacion == null);
	}

	public boolean darBajaBicicleta(String motivoBaja) {
		if (this.motivoBaja != null)
			return false;
		this.motivoBaja = motivoBaja;
		this.fechaBaja = LocalDateTime.now();
		this.estado = EstadoBicicleta.NO_DISPONIBLE;
		this.idEstacion = null;
		return true;
	}

	public boolean estacionarBicicleta(String idEstacion) {
		if (!this.isEstacionable())
			return false;
		this.motivoBaja = null;
		this.fechaBaja = null;
		this.estado = EstadoBicicleta.DISPONIBLE;
		this.idEstacion = idEstacion;
		return true;
	}

	@Override
	public String toString() {
		return "Bicicleta [id=" + id + ", modelo=" + modelo + ", fechaAlta=" + fechaAlta + ", fechaBaja=" + fechaBaja
				+ ", motivoBaja=" + motivoBaja + ", estado=" + estado + ", idEstacion=" + idEstacion + "]";
	}
}
