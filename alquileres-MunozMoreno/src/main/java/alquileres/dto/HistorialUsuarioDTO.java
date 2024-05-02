package alquileres.dto;

import alquileres.servicio.EstadoServicio;
import java.util.List;

public class HistorialUsuarioDTO {
    private List<ReservaDTO> reservas;
    private List<AlquilerDTO> alquileres;
    private EstadoServicio estado;
    private int tiempoUso;

    public HistorialUsuarioDTO() {
    }

    public HistorialUsuarioDTO(List<ReservaDTO> reservas, List<AlquilerDTO> alquileres, EstadoServicio estado, int tiempoUso) {
        this.reservas = reservas;
        this.alquileres = alquileres;
        this.estado = estado;
        this.tiempoUso = tiempoUso;
    }

    public List<ReservaDTO> getReservas() {
        return reservas;
    }

    public void setReservas(List<ReservaDTO> reservas) {
        this.reservas = reservas;
    }

    public List<AlquilerDTO> getAlquileres() {
        return alquileres;
    }

    public void setAlquileres(List<AlquilerDTO> alquileres) {
        this.alquileres = alquileres;
    }

    public EstadoServicio getEstado() {
        return estado;
    }

    public void setEstado(EstadoServicio estado) {
        this.estado = estado;
    }

    public int getTiempoUso() {
        return tiempoUso;
    }

    public void setTiempoUso(int tiempoUso) {
        this.tiempoUso = tiempoUso;
    }

    @Override
    public String toString() {
        return "HistorialUsuarioDTO [reservas=" + reservas + ", alquileres=" + alquileres + ", estado=" + estado + ", tiempoUso=" + tiempoUso + "]";
    }
}
