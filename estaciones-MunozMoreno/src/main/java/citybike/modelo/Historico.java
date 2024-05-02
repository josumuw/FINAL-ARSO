package citybike.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import repositorio.Identificable;

public class Historico implements Identificable, Serializable {

    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String id;
    @BsonProperty(value = "idBicicleta")
    private String idBicicleta;
    @BsonProperty(value = "idEstacion")
    private String idEstacion;
    @BsonProperty(value = "fechaInicio")
    private LocalDateTime fechaInicio;
    @BsonProperty(value = "fechaFin")
    private LocalDateTime fechaFin;

    public Historico() {
    }

    // Constructor
    public Historico(String idBicicleta, String idEstacion) {
        this.idBicicleta = idBicicleta;
        this.idEstacion = idEstacion;
        this.fechaInicio = LocalDateTime.now();
    }

    // Métodos getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdBicicleta() {
        return idBicicleta;
    }

    public void setIdBicicleta(String idBicicleta) {
        this.idBicicleta = idBicicleta;
    }

    public String getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(String idEstacion) {
        this.idEstacion = idEstacion;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "Historico [id=" + id + ", idBicicleta=" + idBicicleta + ", idEstacion=" + idEstacion + ", fechaInicio="
                + fechaInicio + ", fechaFin=" + fechaFin + "]";
    }

}
