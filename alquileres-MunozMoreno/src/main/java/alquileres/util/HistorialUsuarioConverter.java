package alquileres.util;

import alquileres.dto.AlquilerDTO;
import alquileres.dto.HistorialUsuarioDTO;
import alquileres.dto.ReservaDTO;
import alquileres.modelo.Alquiler;
import alquileres.servicio.HistorialUsuario;
import alquileres.modelo.Reserva;

import java.util.ArrayList;
import java.util.List;

public class HistorialUsuarioConverter {

    public static HistorialUsuarioDTO convertToDTO(HistorialUsuario historialUsuario) {
        List<Reserva> reservas = historialUsuario.getReservas();
        List<ReservaDTO> reservaDTOs = new ArrayList<>();
        for (Reserva reserva : reservas) {
            reservaDTOs.add(ReservaConverter.convertToDTO(reserva));
        }

        List<Alquiler> alquileres = historialUsuario.getAlquileres();
        List<AlquilerDTO> alquilerDTOs = new ArrayList<>();
        for (Alquiler alquiler : alquileres) {
            alquilerDTOs.add(AlquilerConverter.convertToDTO(alquiler));
        }

        return new HistorialUsuarioDTO(reservaDTOs, alquilerDTOs, historialUsuario.getEstado(), historialUsuario.getTiempoUso());
    }
}
