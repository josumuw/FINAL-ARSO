package repositorio;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public abstract class RepositorioMongoDB<T extends Identificable> implements RepositorioString<T> {

	public abstract MongoCollection<T> getColeccion();

	// private final CodecRegistry codecRegistry;

	/**
	 * public RepositorioMongoDB(CodecRegistry codecRegistry) {
	 * this.codecRegistry = codecRegistry;
	 * }
	 */

	@Override
	public String add(T entity) throws RepositorioException {

		try {
			getColeccion().insertOne(entity);
			return entity.getId();
		} catch (Exception e) {
			throw new RepositorioException("Error al agregar entidad", e);
		}

	}

	@Override
	public void update(T entity) throws RepositorioException, EntidadNoEncontrada {
		try {
			T existingEntity = getById(entity.getId());
			if (existingEntity != null) {
				getColeccion().replaceOne(Filters.eq("_id", new ObjectId(entity.getId())), entity);
			} else {
				throw new EntidadNoEncontrada("Entidad no encontrada con ID: " + entity.getId());
			}
		} catch (Exception e) {
			throw new RepositorioException("Error al actualizar entidad", e);
		}

	}

	@Override
	public void delete(T entity) throws RepositorioException, EntidadNoEncontrada {
		try {
			getColeccion().deleteOne(Filters.eq("_id", new ObjectId(entity.getId())));
		} catch (Exception e) {
			throw new RepositorioException("Error al eliminar entidad", e);
		}

	}

	@Override
	public T getById(String id) throws RepositorioException, EntidadNoEncontrada {

		try {
			return getColeccion().find(Filters.eq("_id", new ObjectId(id))).first();
		} catch (Exception e) {
			throw new RepositorioException("Error al obtener entidad por ID", e);
		}
	}

	@Override
	public List<T> getAll() throws RepositorioException {
		try {
			List<T> entities = new ArrayList<>();
			getColeccion().find().into(entities);
			return entities;
		} catch (Exception e) {
			throw new RepositorioException("Error al obtener todas las entidades", e);
		}
	}

	@Override
	public List<String> getIds() throws RepositorioException {
		try {
			List<String> ids = new ArrayList<>();
			getColeccion().find().map(entity -> entity.getId()).into(ids);
			return ids;
		} catch (Exception e) {
			throw new RepositorioException("Error al obtener los IDs de las entidades", e);
		}
	}

}