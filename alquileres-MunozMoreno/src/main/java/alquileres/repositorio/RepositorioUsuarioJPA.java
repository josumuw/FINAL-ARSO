package alquileres.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import alquileres.modelo.Usuario;
import persistencia.jpa.UsuarioEntidad;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import repositorio.RepositorioJPA;
import utils.EntityManagerHelper;

public class RepositorioUsuarioJPA extends RepositorioJPA<Usuario> implements IRepositorioUsuario {

	@Override
	public Class<Usuario> getClase() {
		return Usuario.class;
	}

	@Override
	public String getNombre() {
		return getClase().getSimpleName();
	}

	@Override
	public String add(Usuario usuario) throws RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		UsuarioEntidad entity = UsuarioEntidad.fromUsuario(usuario);

		try {
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RepositorioException("Error al guardar la entidad con id " + entity.getId(), e);
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			EntityManagerHelper.closeEntityManager();
		}
		return entity.getId();
	}

	@Override
	public void update(Usuario usuario) throws RepositorioException, EntidadNoEncontrada {
		EntityManager em = EntityManagerHelper.getEntityManager();
		UsuarioEntidad entity = UsuarioEntidad.fromUsuario(usuario);

		try {
			em.getTransaction().begin();

			UsuarioEntidad instance = em.find(UsuarioEntidad.class, entity.getId());
			if (instance == null) {
				throw new EntidadNoEncontrada(entity.getId() + " no existe en el repositorio");
			}
			entity = em.merge(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RepositorioException("Error al actualizar la entidad con id " + entity.getId(), e);
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			EntityManagerHelper.closeEntityManager();
		}
	}

	@Override
	public void delete(Usuario usuario) throws RepositorioException, EntidadNoEncontrada {
		EntityManager em = EntityManagerHelper.getEntityManager();
		UsuarioEntidad entity = UsuarioEntidad.fromUsuario(usuario);
		try {
			em.getTransaction().begin();
			UsuarioEntidad instance = em.find(UsuarioEntidad.class, entity.getId());
			if (instance == null) {
				throw new EntidadNoEncontrada(entity.getId() + " no existe en el repositorio");
			}
			em.remove(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RepositorioException("Error al borrar la entidad con id " + entity.getId(), e);
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			EntityManagerHelper.closeEntityManager();
		}
	}

	@Override
	public Usuario getById(String id) throws EntidadNoEncontrada, RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			UsuarioEntidad instance = em.find(UsuarioEntidad.class, id);

			if (instance != null) {
				em.refresh(instance);
			} else {
				throw new EntidadNoEncontrada(id + " no existe en el repositorio");
			}

			Usuario usuario = instance.toUsuario();
			return usuario;

		} catch (RuntimeException re) {
			throw new RepositorioException("Error al recuperar la entidad con id " + id, re);
		}
	}

	@Override
	public List<Usuario> getAll() throws RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			final String queryString = " SELECT model from " + "UsuarioEntidad" + " model ";

			Query query = em.createQuery(queryString);

			query.setHint(QueryHints.REFRESH, HintValues.TRUE);

			List<UsuarioEntidad> entities = query.getResultList();
			List<Usuario> usuarios = new ArrayList();
			for (UsuarioEntidad entity : entities) {
				usuarios.add(entity.toUsuario());
			}

			return usuarios;

		} catch (RuntimeException re) {

			throw new RepositorioException("Error buscando todas las entidades de " + getNombre(), re);

		}
	}

	@Override
	public List<String> getIds() throws RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			final String queryString = " SELECT model.id from " + "UsuarioEntidad" + " model ";

			Query query = em.createQuery(queryString);

			query.setHint(QueryHints.REFRESH, HintValues.TRUE);

			return query.getResultList();

		} catch (RuntimeException re) {

			throw new RepositorioException("Error buscando todos los ids de " + getNombre(), re);

		}
	}
}
