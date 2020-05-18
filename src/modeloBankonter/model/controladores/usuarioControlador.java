package modeloBankonter.model.controladores;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import modeloBankonter.Controlador;
import modeloBankonter.model.Usuario;




public class usuarioControlador extends Controlador {

	private static usuarioControlador controlador = null;
	
	// Variable principal del patrón Singleton
			private static usuarioControlador instance = null;

	public usuarioControlador () {
		super(Usuario.class, "Trabajo_bankonter");
	}
	
	
	public static usuarioControlador getInstance() {
		if (instance == null) {
			instance = new usuarioControlador();
		}
		return instance;
	}
	
	/**
	 * 
	 * @return
	 */
	public static usuarioControlador getControlador () {
		if (controlador == null) {
			controlador = new usuarioControlador();
		}
		return controlador;
	}

	/**
	 *  
	 */
	public Usuario find (int id) {
		return (Usuario) super.find(id);
	}

	
	/**
	 * 
	 * @return
	 */
	public Usuario findFirst () {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT u FROM Usuario u order by c.id", Usuario.class);
			Usuario resultado = (Usuario) q.setMaxResults(1).getSingleResult();
			em.close();
			return resultado;
		}
		catch (NoResultException nrEx) {
			return null;
		}
	}

	
	
	
	/**
	 * 
	 * @return
	 */
	public Usuario findLast () {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT u FROM Usuario u order by u.id desc", Usuario.class);
			Usuario resultado = (Usuario) q.setMaxResults(1).getSingleResult();
			em.close();
			return resultado;
		}
		catch (NoResultException nrEx) {
			return null;
		}
	}

	
	
	
	/**
	 * 
	 * @return
	 */
	public Usuario findNext (Usuario u) {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT u FROM Usuario u where u.id > :idActual order by u.id", Usuario.class);
			q.setParameter("idActual", u.getId());
			Usuario resultado = (Usuario) q.setMaxResults(1).getSingleResult();
			em.close();
			return resultado;
		}
		catch (NoResultException nrEx) {
			return null;
		}
	}

	
	
	
	/**
	 * 
	 * @return
	 */
	public Usuario findPrevious (Usuario c) {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = em.createQuery("SELECT u FROM Usuario u where u.id < :idActual order by u.id desc", Usuario.class);
			q.setParameter("idActual", c.getId());
			Usuario resultado = (Usuario) q.setMaxResults(1).getSingleResult();
			em.close();
			return resultado;
		}
		catch (NoResultException nrEx) {
			return null;
		}
	}

	
	
	
	public List<Usuario> findAll () {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Query q = em.createQuery("SELECT u FROM Concesionario u", Usuario.class);
		List<Usuario> resultado = (List<Usuario>) q.getResultList();
		em.close();
		return resultado;
	}
	

	
	public static String toString (Usuario usuario) {
		return "Id: " + usuario.getId() + " - Nombre Usuario: " + usuario.getNombreUsuario() +
				" - Email: " + usuario.getEmail() + " - Password: " + usuario.getPassword(); 
	}

	
	
}
