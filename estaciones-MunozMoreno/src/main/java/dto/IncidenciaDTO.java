package dto;

import java.util.Objects;

public class IncidenciaDTO {
	
	private String idBicicleta;
	private String idIncidencia;
	private String estado;
	private String fecha;
	private String descripcion;
	private String motivo;
	private String operario;
	private Boolean reparada;


	public IncidenciaDTO(String idBicicleta, String idIncidencia, String estado, String fecha, String descripcion) {
		super();
		this.idBicicleta = idBicicleta;
		this.idIncidencia = idIncidencia;
		this.estado = estado;
		this.fecha = fecha;
		this.descripcion = descripcion;
	}
	
	public IncidenciaDTO(String idBicicleta, String idIncidencia, String estado, String fecha, String descripcion, String motivo, String operario, Boolean reparada) {
		super();
		this.idBicicleta = idBicicleta;
		this.idIncidencia = idIncidencia;
		this.estado = estado;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.motivo = motivo;
		this.operario = operario;
		this.reparada = reparada;
	}

	// Constructor para tabla CRUD de PRIMEFACES
	public IncidenciaDTO() {
		
	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String bicicleta) {
		this.idBicicleta = bicicleta;
	}

	public String getIdIncidencia() {
		return idIncidencia;
	}

	public void setIdIncidencia(String idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getOperario() {
		return operario;
	}

	public void setOperario(String operario) {
		this.operario = operario;
	}

	public Boolean getReparada() {
		return reparada;
	}

	public void setReparada(Boolean reparada) {
		this.reparada = reparada;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idBicicleta, idIncidencia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IncidenciaDTO other = (IncidenciaDTO) obj;
		return Objects.equals(idBicicleta, other.idBicicleta) && Objects.equals(idIncidencia, other.idIncidencia);
	}

	@Override
	public String toString() {
		return "IncidenciaDTO [idBicicleta=" + idBicicleta + ", idIncidencia=" + idIncidencia + ", estado=" + estado
				+ ", fecha=" + fecha + ", descripcion=" + descripcion + ", motivo=" + motivo + ", operario=" + operario
				+ ", reparada=" + reparada + "]";
	}
}
