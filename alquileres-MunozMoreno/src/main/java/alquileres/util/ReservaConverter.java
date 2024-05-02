package alquileres.util;

import alquileres.dto.ReservaDTO;
import alquileres.modelo.Reserva;

import java.time.LocalDateTime;

public class ReservaConverter {

    public static ReservaDTO convertToDTO(Reserva reserva) {
        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setIdBicicleta(reserva.getIdBicicleta());
        reservaDTO.setCreada(reserva.getCreada());
        reservaDTO.setCaducidad(reserva.getCaducidad());
        return reservaDTO;
    }

    public static Reserva convertToEntity(ReservaDTO reservaDTO) {
        Reserva reserva = new Reserva();
        reserva.setIdBicicleta(reservaDTO.getIdBicicleta());
        reserva.setCreada(reservaDTO.getCreada());
        reserva.setCaducidad(reservaDTO.getCaducidad());
        return reserva;
    }
}
