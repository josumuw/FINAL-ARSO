package alquileres.servicio.test;

import alquileres.servicio.IServicioAlquileres;
import servicio.FactoriaServicios;

public class ProgramaPrueba {

    public static void main(String[] args) {
	// IServiciosAlquileres
	IServicioAlquileres servicioAlquiler = FactoriaServicios.getServicio(IServicioAlquileres.class);
	try {
	    String idUsuario = "mendy";
	    String idBicicleta = "6616619906437c2787bb2a3e";
	    String idEstacion = "66158942d582c33e20ab9759";
	    servicioAlquiler.alquilar(idUsuario, idBicicleta);
	    servicioAlquiler.dejarBicicleta(idUsuario, idEstacion);
	    //servicioAlquiler.dejarBicicleta(idUsuario, idBicicleta);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	// servicioAlquiler.reservar("juan@um.es", "bici-23");
	// servicioAlquiler.confirmarReserva("juan@um.es");
	// servicioAlquiler.dejarBicicleta("juan@um.es", "bici-23");

	// HistorialUsuario historial = servicioAlquiler.historialUsuario("juan@um.es");
	// System.out.println(historial.toString());

    }

}
