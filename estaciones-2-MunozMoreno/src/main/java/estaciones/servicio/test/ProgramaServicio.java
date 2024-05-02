package estaciones.servicio.test;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import estaciones.EstacionesApp;
import estaciones.repositorio.EntidadNoEncontrada;
import estaciones.servicio.IServicioEstaciones;
import estaciones.servicio.RequisitosException;

public class ProgramaServicio {

	public static void main(String[] args) throws EntidadNoEncontrada {
		ConfigurableApplicationContext contexto = SpringApplication.run(EstacionesApp.class, args);

		IServicioEstaciones servicio = contexto.getBean(IServicioEstaciones.class);

		System.out.println("### PROGRAMA DE PRUEBA DEL SERVICIO ESTACIONES ###");
		String idBicicleta = "6616619906437c2787bb2a3e";
		String idEstacion = "66158942d582c33e20ab9759";
		String motivoBaja = "Prueba evento bici-desactivada.";
		try {
		    //servicio.estacionarBicicleta(idEstacion, idBicicleta);
		    //servicio.estacionarBicicleta(idEstacion, "6616619906437c2787bb2a3e");
		    servicio.darBajaBicicleta(idBicicleta, motivoBaja);
		    System.out.println("POST COSAS");

		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
		/*
		System.out.println("--Pruebas funcionalidades usuario ADMIN--");

		System.out.println("Alta Estacion...");
		String idEstacion = servicio.altaEstacion("ex", 20, "Calle X, Nº X", 37.98404, -1.12865);
		System.out.println("Éxito alta Estacion con id: " + idEstacion);

		System.out.println("Alta Bicicleta...");
		String idBicicleta = servicio.altaBicicleta("mX", idEstacion);
		System.out.println("Éxito alta Bicicleta con id: " + idBicicleta);

		System.out.println("Dar baja Bicicleta...");
		boolean isBaja = servicio.darBajaBicicleta(idBicicleta, "Me la bajo para probar");
		System.out.println("Resultado baja Bicicleta con id " + idBicicleta + ": " + isBaja);

		System.out.println("Obtener todas las Bicicletas de una Estacion...");
		System.out.println(" Inserto 5 Bicicletas adicionales en la Estacion...");
		for (int i = 0; i < 5; i++) {
			servicio.altaBicicleta("m" + i, idEstacion);
		}
		System.out.println("Resultado de getAllBicicletasEstacion()...");

		List<Bicicleta> bicicletasEstacion = servicio.getAllBicicletasEstacion(idEstacion);
		System.out.println("Total Bicicletas Estacion: " + bicicletasEstacion.size());
		int cont = 1;
		for (Bicicleta b : bicicletasEstacion) {
			System.out.println(cont + ". " + b);
			cont++;
		}


		System.out.println("--Pruebas funcionalidades usuario CLIENTE--");

		System.out.println("Obtener todas las Estaciones...");
		for (int i = 0; i < 5; i++) {
			servicio.altaEstacion("e" + i, 20, "Calle " + i + ", Nº " + i, 37.98404, -1.12865);
		}
		System.out.println("Resultado de getAllEstaciones()...");

		List<Estacion> estaciones = servicio.getAllEstaciones();
		System.out.println("Total Estaciones: " + estaciones.size());
		cont = 1;
		for (Estacion e : estaciones) {
			System.out.println(cont + ". " + e);
			cont++;
		}

		System.out.println("Obtener Info de Estacion...");
		System.out.println("Resultado de EstacionInfo...");
		System.out.println(servicio.getEstacionInfo(idEstacion));

		System.out.println("Obtener Bicicletas Disponibles de una Estacion...");
		System.out.println("Resultado de getBicicletasEstacion()...");
		
		bicicletasEstacion = servicio.getBicicletasEstacion(idEstacion);
		System.out.println("Total Bicicletas Estacion: " + bicicletasEstacion.size());
		cont = 1;
		for (Bicicleta b : bicicletasEstacion) {
			System.out.println(cont + ". " + b);
			cont++;
		}


		System.out.println("Estacionar Bicicleta en una Estacion...");
		boolean isEstacionada = servicio.estacionarBicicleta(idEstacion, idBicicleta);
		System.out.println("Resultado de estacionar Bicicleta " + idBicicleta + ": " + isEstacionada);
		*/
		contexto.close();
	}

}
