package alquileres.servicio;

import java.util.List;

import alquileres.modelo.Alquiler;
import alquileres.modelo.Reserva;
import alquileres.modelo.Usuario;

public class HistorialUsuario {
	private List<Reserva> reservas;
	private List<Alquiler> alquileres;
	private EstadoServicio estado;
	private int tiempoUso;

	public HistorialUsuario(Usuario usuario) {
		this.reservas = usuario.getReservas();
		this.alquileres = usuario.getAlquileres();
		this.estado = usuario.superaTiempo() ? EstadoServicio.BLOQUEADO : EstadoServicio.ACTIVO;
		this.tiempoUso = usuario.tiempoUsoSemana();
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	public List<Alquiler> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(List<Alquiler> alquileres) {
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
		return "HistorialUsuario [reservas=" + reservas + ", alquileres=" + alquileres + ", estado=" + estado
				+ ", tiempoUso=" + tiempoUso + "]";
	}

}
