package citybike.modelo;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import repositorio.Identificable;

@Entity
@Table(name = "bicicleta")
public class Bicicleta implements Identificable {
	
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "fecha_alta", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;

    @Column(name = "fecha_baja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;

    @Column(name = "motivo_baja")
    private String motivoBaja;
    
    @Column(name = "disponible")
    private boolean disponible;

    @Column(name = "id_estacion")
    private String idEstacion;
    
    @OneToMany (mappedBy = "bicicleta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Incidencia> incidencias;
    

    // Constructor, getters, setters, y toString

	public String getIdEstacion() {
        return idEstacion;
    }

    public Bicicleta() {
        // Constructor por defecto necesario para JPA
    }

    public Bicicleta(String modelo) {
    	this.id = UUID.randomUUID().toString();
        this.modelo = modelo;
        this.fechaAlta = new Date();
        this.disponible = false;
    }

    // Getters y setters

    public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public String getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public void setIdEstacion(String idEstacion) {
        this.idEstacion = idEstacion;
    }


    public List<Incidencia> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}
	
	public void addIncidencia(Incidencia incidencia) {
		this.incidencias.add(incidencia);
	}
	
    // Método toString

    @Override
	public String toString() {
		return "Bicicleta [id=" + id + 
				", modelo=" + modelo + 
				", fechaAlta=" + fechaAlta + 
				", fechaBaja=" + fechaBaja + 
				", motivoBaja=" + motivoBaja + 
				", idEstacion=" + idEstacion + 
				", incidencias=" + incidencias + 
				"]";
	}
    

    @Override
    public void setId(String id) {
        this.id = id;
    }
}