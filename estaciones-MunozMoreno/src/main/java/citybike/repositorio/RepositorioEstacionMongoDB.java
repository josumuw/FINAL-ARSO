package citybike.repositorio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import citybike.modelo.Estacion;
import repositorio.RepositorioException;
import repositorio.RepositorioMongoDB;
import utils.PropertiesReader;

public class RepositorioEstacionMongoDB extends RepositorioMongoDB<Estacion> implements IRepositorioEstacionMongoDB {

	protected MongoClient mongoClient;
	protected MongoDatabase database;
	protected MongoCollection<Estacion> coleccion;

	public RepositorioEstacionMongoDB() {

		PropertiesReader properties;
		try {

			properties = new PropertiesReader("mongo.properties");
			String connectionString = properties.getProperty("mongouri");
			MongoClient mongoClient = MongoClients.create(connectionString); // cadena de conexión

			database = mongoClient.getDatabase("citybike"); // nombre bases de datos

			CodecRegistry defaultCodecRegistry = CodecRegistries.fromRegistries(
					MongoClientSettings.getDefaultCodecRegistry(),
					CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

			this.coleccion = database.getCollection("estaciones", Estacion.class)
					.withCodecRegistry(defaultCodecRegistry);

			coleccion.createIndex(new Document("location", "2dsphere"));

		} catch (Exception e) {

		}
	}

	@Override
	public MongoCollection<Estacion> getColeccion() {
		return coleccion;
	}

	@Override
	public Estacion getEstacionPuestosLibres() throws RepositorioException {
		try {
			return getColeccion().find(Filters.gt("numPuestos", 0)).first();
		} catch (Exception e) {
			throw new RepositorioException("Error al obtener estación con puestos libres", e);
		}
	}

	@Override
	public List<Estacion> getEstacionesCerca(double latitud, double longitud) throws RepositorioException {
		try {
			Point location = new Point(new Position(latitud, longitud));
			return getColeccion()
					.find(Filters.near("location", location, 10000000000000000000.0, 0.0))
					.limit(3)
					.into(new ArrayList<>());
		} catch (Exception e) {
			throw new RepositorioException("Error al obtener estaciones cercanas", e);
		}
	}

	@Override
	public List<Estacion> getEstacionesConMasSitiosTuristicos() throws RepositorioException {
		try {

			List<Estacion> estacionesConMasSitiosTuristicos = new ArrayList<Estacion>();
			estacionesConMasSitiosTuristicos.addAll(getAll());
			estacionesConMasSitiosTuristicos
					.sort((e1, e2) -> e2.getSitiosTuristicos().size() - e1.getSitiosTuristicos().size());

			return estacionesConMasSitiosTuristicos;
		} catch (Exception e) {
			throw new RepositorioException("Error al obtener estaciones con más sitios turísticos", e);
		}
	}
}
