package estaciones.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import utils.LocalDateTimeDeserializer;

public class AlquilerDTO {
    private String idBicicleta;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime inicio;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)

    private LocalDateTime fin;

    public AlquilerDTO() {
    }

    public AlquilerDTO(String idBicicleta, LocalDateTime inicio, LocalDateTime fin) {
	this.idBicicleta = idBicicleta;
	this.inicio = inicio;
	this.fin = fin;
    }

    public String getIdBicicleta() {
	return idBicicleta;
    }

    public void setIdBicicleta(String idBicicleta) {
	this.idBicicleta = idBicicleta;
    }

    public LocalDateTime getInicio() {
	return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
	this.inicio = inicio;
    }

    public LocalDateTime getFin() {
	return fin;
    }

    public void setFin(LocalDateTime fin) {
	this.fin = fin;
    }

    @Override
    public String toString() {
	return "AlquilerDTO [idBicicleta=" + idBicicleta + ", inicio=" + inicio + ", fin=" + fin + "]";
    }

}
