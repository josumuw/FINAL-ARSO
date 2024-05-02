package citybike.repositorio;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import citybike.modelo.Historico;
import repositorio.RepositorioException;
import repositorio.RepositorioMongoDB;
import utils.PropertiesReader;

public class RepositorioHistoricoMongoDB extends RepositorioMongoDB<Historico> implements IRepositorioHistoricoMongo {

    protected MongoClient mongoClient;
    protected MongoDatabase database;
    protected MongoCollection<Historico> coleccion;

    public RepositorioHistoricoMongoDB() {

        PropertiesReader properties;
        try {

            properties = new PropertiesReader("mongo.properties");
            String connectionString = properties.getProperty("mongouri");
            MongoClient mongoClient = MongoClients.create(connectionString); // cadena de conexión

            database = mongoClient.getDatabase("citybike"); // nombre bases de datos

            CodecRegistry defaultCodecRegistry = CodecRegistries.fromRegistries(
                    MongoClientSettings.getDefaultCodecRegistry(),
                    CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

            this.coleccion = database.getCollection("historicos", Historico.class)
                    .withCodecRegistry(defaultCodecRegistry);

        } catch (Exception e) {

        }
    }

    @Override
    public MongoCollection<Historico> getColeccion() {
        return coleccion;
    }

	@Override
	public Historico getHistoricoAbierto(String idBici, String idEstacion) throws RepositorioException {
		try {
			Document filtro = new Document("idBicicleta", idBici)
		            .append("idEstacion", idEstacion)
		            .append("fechaFin", new Document("$exists", false));
			// Aplicamos el filtro.
			FindIterable<Historico> historicos = getColeccion().find(filtro);
			// Según la lógica implmentada en ServicioEstaciones solo DEBE EXISTIR un historico, y SOLO UNO que cupmla dichas condiciones.
			return  historicos.first(); 
		} catch (Exception e) {
			throw new RepositorioException("Error al obtener estaciones cercanas", e);
		}
	}
}

