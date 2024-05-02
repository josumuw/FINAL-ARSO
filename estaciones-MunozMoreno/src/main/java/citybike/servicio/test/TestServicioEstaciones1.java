package citybike.servicio.test;

import java.util.List;

import citybike.modelo.Estacion;
import citybike.servicio.IServicioEstaciones;
import citybike.servicio.ISitiosTuristicos;
import citybike.servicio.SitioTuristicoResumen;
import servicio.FactoriaServicios;

public class TestServicioEstaciones1 {

	public static void main(String[] args) throws Exception {

		// IServicioEstaciones
		IServicioEstaciones servicioEstacion = FactoriaServicios.getServicio(IServicioEstaciones.class);
		// ISitiosTuristicos
		ISitiosTuristicos servicioSitio = FactoriaServicios.getServicio(ISitiosTuristicos.class);

		// ### Prueba ServiciosEstaciones Entrega 1###
		// 1. Crear y dar de alta estación
		System.out.println("1. Crear y dar de alta estación1 y estación2:");
		String idEstacion1 = servicioEstacion.crear("Estacion Biblioteca Regional", // nombre
				10, // numPuestos
				"Av. Juan Carlos I, 17, 30008 Murcia", // dirPostal
				"37.998575", // latitud
				"-1.137249"); // longitud
		String idEstacion2 = servicioEstacion.crear("Estación Jardín Florida Blanca", // nombre
				10, // numPuestos
				"C. Proclamación, 6, 30002 Murcia", // dirPostal
				"37.97551735", // latitud
				"-1.13258395589231"); // longitud
		System.out.println();
		// 2. Obtener SitiosTuristicos (resumen) cercanos a Estación
		System.out.println("2. Obtener lista SitiosTuristicos cercanos a estaciones:");
		System.out.println("\n2.1. SitiosTuristicos cercanos a estacion1 (Biblioteca Regional):");
		List<SitioTuristicoResumen> sitios = servicioEstacion.getSitiosTuristicos(idEstacion1);
		System.out.println("Total resultados (a 1 km): " + sitios.size());
		for (SitioTuristicoResumen sitio : sitios) {
			System.out.println(sitio.toString());
		}
		System.out.println("\n2.2. SitiosTuristicos cercanos a estacion2 (Jardín Florida Blanca):");
		sitios = servicioEstacion.getSitiosTuristicos(idEstacion2);
		System.out.println("Total resultados (a 1 km): " + sitios.size());
		for (SitioTuristicoResumen sitio : sitios) {
			System.out.println(sitio.toString());
		}
		System.out.println();
		// 3. Añadir una colección de SitiosTuristicos (obtenida mediante 2.)
		System.out.println("3. Asociar a estacion2 una coleción de SitiosTuristicos:");
		// Observamos que la lista de de SitiosTuristicos (resumen) de estacion2 está
		// vacia.
		Estacion estacion2 = servicioEstacion.getEstacion(idEstacion2);
		System.out.println(estacion2.getSitiosTuristicos());
		// Asociamos los sitios obtenidos a estacion2 (todos, pero podríamos filtrar)
		servicioEstacion.setSitiosTuristicos(idEstacion2, sitios);
		// Consultamos estación al repo de nuevo actualizada
		estacion2 = servicioEstacion.getEstacion(idEstacion2);
		System.out.println(estacion2.getSitiosTuristicos() + "\n");

		// 4. Obtener objeto estación dada su id
		System.out.println("4. Obtener estacion1 y estacion2 dado su id:");
		Estacion estacion1 = servicioEstacion.getEstacion(idEstacion1);
		System.out.println(estacion1 + "\n" + estacion2 + "\n");

		// ### Prueba ServiciosTuristicosGeoNames ###
		// 1. Obtener lista SitioTuristico (resumen) dado unas coordenadas con GeoNames
		// (ya se prueba en "Prueba ServicioEstaciones 2." indirectamente)
		System.out.println("1. Obtener una lista con SitioTuristico dado unas coordenadas: ");
		sitios = servicioSitio.getSitiosTuristicosGeo("37.998575", "-1.137249");
		// Se obtiene el mismo resultado que cuando se consulta mediante el servicio de
		// Estaciones
		for (SitioTuristicoResumen sitio : sitios) {
			System.out.println(sitio.toString());
		}
		System.out.println();
		// 2. Obtener información adicional de un SitioTuristico consultado DBpedia para
		// obtener información de su artículo de wikipedia.
		// Procesamos dos consultas, una que debe estar en cache y otra que no.
		// Consulta en cache
		System.out.println(servicioSitio.getSitioTuristicoInfo("Catedral_de_Murcia"));
		// Consulta nueva, no se añade a cache al no ser realizada por el método 1.git
		System.out.println(servicioSitio.getSitioTuristicoInfo("Plaza_de_toros_de_La_Condomina"));
	}
}
