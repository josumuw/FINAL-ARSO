package alquileres.util;

import alquileres.dto.AlquilerDTO;
import alquileres.modelo.Alquiler;

public class AlquilerConverter {

    public static AlquilerDTO convertToDTO(Alquiler alquiler) {
        AlquilerDTO alquilerDTO = new AlquilerDTO();
        alquilerDTO.setIdBicicleta(alquiler.getIdBicicleta());
        alquilerDTO.setInicio(alquiler.getInicio());
        alquilerDTO.setFin(alquiler.getFin());
        return alquilerDTO;
    }

    public static Alquiler convertToEntity(AlquilerDTO alquilerDTO) {
        Alquiler alquiler = new Alquiler();
        alquiler.setIdBicicleta(alquilerDTO.getIdBicicleta());
        alquiler.setInicio(alquilerDTO.getInicio());
        alquiler.setFin(alquilerDTO.getFin());
        return alquiler;
    }
}
