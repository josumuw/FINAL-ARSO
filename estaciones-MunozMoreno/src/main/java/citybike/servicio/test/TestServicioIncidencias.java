package citybike.servicio.test;

import java.util.List;

import citybike.servicio.IServicioEstaciones;
import citybike.servicio.IServicioIncidencias;
import dto.IncidenciaDTO;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class TestServicioIncidencias {

	public static void main(String[] args) {
		// IServicioEstaciones
		IServicioEstaciones servicioEstacion = FactoriaServicios.getServicio(IServicioEstaciones.class);
		// IServicioIncidencias
		IServicioIncidencias servicioIncidencia = FactoriaServicios.getServicio(IServicioIncidencias.class);
	
		// Requisitos para las siguientes pruebas (suponemos que los repos están vacios):
		// - Estacion con plazas disponibles
		// - Bicicleta estacionada
		// lo creamos...
		try {
			// Estaciones
			String idEstacion1 = servicioEstacion.crear("TSI. Estacion Biblioteca Regional", // nombre
					10, // numPuestos
					"Av. Juan Carlos I, 17, 30008 Murcia", // dirPostal
					"37.998575", // latitud
					"-1.137249"); // longitud
			// Bicicletas
			String modeloBici1 = "modeloPrueba1";
			String idBici1 = servicioEstacion.altaBicicleta(modeloBici1, idEstacion1);
			
			// ### Pruebas ServicioIncidencias ###
			// 1. crearIncidencia
			IncidenciaDTO incidencia1 = servicioIncidencia.crearIncidencia(idBici1, "Descripción incidente, prueba crearIncidencia");
			// Comprobamos ...
			// - Se niega la disponibilidad de bicicleta (atributo disponible)
			// - Se ha creado una incidencia en la tabla incidencia con los valores pertinentes.
			
			// 2. cancelarIncidencia
			servicioIncidencia.cancelarIncidencia(incidencia1, "Motivo cancelación prueba");
			// Comprobamos ...
			// - La bici vuelve a estar disponible (atributo disponible)
			// - Se ha cambiado la columna estado a CANCELADO y se añade a la columna fecha_cierre la fecha (la actual)
			
			// 3. asignarIncidencia
			IncidenciaDTO incidencia2 = servicioIncidencia.crearIncidencia(idBici1, "Descripción incidente, prueba asignarIncidencia");
			servicioIncidencia.asignarIncidencia(incidencia2, "Operario prueba");
			// Comprobamos ...
			// - La bici se retira de la estación, con lo que ello conlleva (visto en TestServicioEstaciones2)
			// - Se ha cambiado la columna estado a ASIGNADA y se añade a la columna operario el parmátro pasado
			
			// 4. resolverIncidencia
			IncidenciaDTO incidencia3 = servicioIncidencia.crearIncidencia(idBici1, "Descripción incidente, prueba resolverIncidencia1");
			//4.1. bici arreglada
			servicioIncidencia.resolverIncidencia(incidencia3, "Motivo cierre ARREGLADA", true);
			// Comprobamos ...
			// - Se establece el estado RESUELTA, motivo y fecha de cierre
			// - Se vuelve a considerar disponible la bicicleta, se estaciona, con lo que ello conlleva (visto en TestServicioEstaciones2)
			//4.2. bici NO arreglada
			IncidenciaDTO incidencia4 = servicioIncidencia.crearIncidencia(idBici1, "Descripción incidente, prueba resolverIncidencia2");
			servicioIncidencia.resolverIncidencia(incidencia4, "Motivo cierre NO ARREGLADA", false);
			// Comprobamos ...
			// - Se establece el estado RESUELTA, motivo y fecha de cierre
			// - Se da de baja la bicicleta

			// 5. getIncidenciasAbiertas
			// Añadimos un par de bicis más con incidencias
			String modeloBici2 = "modeloPrueba2";
			String idBici2 = servicioEstacion.altaBicicleta(modeloBici2, idEstacion1);
			String modeloBici3 = "modeloPrueba3";
			String idBici3 = servicioEstacion.altaBicicleta(modeloBici2, idEstacion1);
			IncidenciaDTO incidencia5 = servicioIncidencia.crearIncidencia(idBici2, "Descripción incidente, prueba getIncidenciasAbiertas");
			IncidenciaDTO incidencia6 = servicioIncidencia.crearIncidencia(idBici3, "Descripción incidente, prueba getIncidenciasAbiertas");
			// Obtenemos una lista con las incidencias abiertas (estado PENDIENTE)
			List<IncidenciaDTO> incidenciasAbiertas = servicioIncidencia.getIncidenciasAbiertas();
			// Comprobamos que obtenemos aquellas incidencias con estado PENDIETE
			System.out.println(incidenciasAbiertas);

		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
