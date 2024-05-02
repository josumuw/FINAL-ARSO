package citybike.servicio.test;

import citybike.modelo.Incidencia;
import citybike.servicio.IServicioEstaciones;
import citybike.servicio.IServicioIncidencias;
import servicio.FactoriaServicios;

public class TestServicioEstaciones2 {

	public static void main(String[] args) throws Exception {

		
		// IServicioEstaciones
		IServicioEstaciones servicioEstacion = FactoriaServicios.getServicio(IServicioEstaciones.class);

		// Requisitos para las siguientes pruebas (suponemos que los repos están
		// vacios):
		// - Estaciones con plazas disponibles, las creamos:

		String idEstacion1 = servicioEstacion.crear("TSE2. Estacion Biblioteca Regional", // nombre
				10, // numPuestos
				"Av. Juan Carlos I, 17, 30008 Murcia", // dirPostal
				"34.998575", // latitud
				"-1.137249"); // longitud
		String idEstacion2 = servicioEstacion.crear("TSE2. Estación Jardín Florida Blanca", // nombre
				10, // numPuestos
				"C. Proclamación, 6, 30002 Murcia", // dirPostal
				"33.97551735", // latitud
		
				"-1.2"); // longitud
		// ### Prueba ServiciosEstaciones Entrega 2###
		// 1. altaBicicleta
		// Modelos de las nuevas bicis
		String modeloBici1 = "modeloPrueba1";
		String modeloBici2 = "modeloPrueba2";
		String modeloBici3 = "modeloPrueba3";
		String modeloBici4 = "modeloPrueba4";
		String modeloBici5 = "modeloPrueba5";
		String modeloBici6 = "modeloPrueba6";
		String modeloBici7 = "modeloPrueba7";




		// Damos de alta las nuevas bicis
		String idBici1 = servicioEstacion.altaBicicleta(modeloBici1, idEstacion1);
		String idBici2 = servicioEstacion.altaBicicleta(modeloBici2, idEstacion1);
		String idBici3 = servicioEstacion.altaBicicleta(modeloBici3, idEstacion2);
		String idBici4 = servicioEstacion.altaBicicleta(modeloBici4, idEstacion2);
		String idBici5 = servicioEstacion.altaBicicleta(modeloBici5, idEstacion2);
		String idBici6 = servicioEstacion.altaBicicleta(modeloBici6, idEstacion2);
		String idBici7 = servicioEstacion.altaBicicleta(modeloBici7, idEstacion2);
		
		// Comprobamos ...
		// En el Repositorio<Estaciones> en MongoDB con las estaciones previamente
		// creadas:
		// - Se añade la bicicleta estacionada a la coleccion de dicha estación.
		// - Se actualiza el número de plazas disponibles (-1)
		// En el Repositorio<Bicicleta> en SQL:
		// - Se guardan las bicicletas creadas adecuadamente (el idEstacion es el correcto y más)
		// En el Repositorio<Historico> en MongoDB se crea adecuadamente registro historico al estacionar

		// 2.1 estacionarBicicleta (sin estación)
		// 2.2 estacionarBicicleta
		// El servicio anteriormente probado (altaBicicleta) hace uso de este servicio,
		// luego funciona correctamente. Aún, realizamos una prueba tras 3. retirarBicicleta
		// 3. retirarBicicleta
		// Retiramos la bici1 estacionada (a relizar su alta en la estación 1) previamente
		servicioEstacion.retirarBicicleta(idBici1);
		
		// Comprobamos ...
		// En el Repositorio<Estaciones> en MongoDB con las estaciones previamente
		// creadas:
		// - Se elimina la bicicleta retirada de la coleccion de dicha estación.
		// - Se actualiza el número de plazas disponibles (+1)
		// En el Repositorio<Bicicleta> en SQL:
		// - El atributo id_estación de la bicicleta se establece a null
		// En el Repositorio<Historico> en MongoDB:
		// - Se "cierra" el historico bici/estación adecuado
		 

		// 2. estacionarBicicleta
		// Estacionamos la bicicleta que acabamos de retirar:
		// Utilizamos el método que no necesita idEstación, dado que este hace uso a su
		// vez del que sí.
		// Dado que no especificamos estación, se estacionará en la primera estación con
		// plazas disponibles.
		servicioEstacion.estacionarBicicleta(idBici1);

		// Comprobamos ...
		// En el Repositorio<Estaciones> en MongoDB con las estaciones previamente
		// creadas:
		// - Se añade la bicicleta estacionada a la coleccion de dicha estación.
		// - Se actualiza el número de plazas disponibles (-1)
		// En el Repositorio<Bicicleta> en SQL:
		// - La columna id_estación de la bici vuelve a tener estación asociada
		// En el Repositorio<Historico> en MongoDB se crea adecuadamente registro historico al estacionar

		// La retiramos de nuevo
		servicioEstacion.retirarBicicleta(idBici1);
		// Observamos que cumple el comportamiento esperado, sobre todo en el repo Historico

		// 4. darDeBajaBicicleta
		// Damos de baja la bici2 estacionada (a relizar su alta en la estación 2)
		// previamente
		servicioEstacion.darDeBajaBicicleta(idBici2, "Motivo prueba TSE2");
		// Comprobamos ...
		// Además de lo ya mencionado anteriormente en retirarBicicleta (dado que
		// utiliza llama a dicho método):
		// En el Repositorio<Bicicleta> en SQL:
		// - Se establece en la columna motivo_baja el motivo indicado en el parámetro
		// - Se establece automáticamente en la columna fecha_baja la fecha de baja
		// (fecha acutal)
		// - Se pone 0 en la columna disponible
		// El Repositorio<Historico> el elemento bici/estacion corespondiente se cierra
		
		// 5. getBicicletasCercanas
		
		System.out.println(servicioEstacion.getBicicletasCercanas(34.998575, -1.137249));
		// Observamos que obtenemos el resultado esperado, bicis cercanas a las coordenadas indicadas (las de estacion1)
	
	
		// 6. getEstacionesConSitiosTuristicosCercanas
		System.out.println(servicioEstacion.getEstacionesConSitiosTuristicosCercanas());
			
	}
}