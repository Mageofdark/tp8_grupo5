package ar.edu.unju.escmi.dao.impl;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.entities.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ClienteDAO implements ar.edu.unju.escmi.dao.IClienteDAO {

    private EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();

    public void guardar(Cliente cliente) {
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
    }

    public void actualizar(Cliente cliente) {
        em.getTransaction().begin();
        em.merge(cliente);
        em.getTransaction().commit();
    }

    public void eliminar(Long id) {
        em.getTransaction().begin();
        Cliente c = em.find(Cliente.class, id);
        if (c != null) {
            c.setEstado(false);
            em.merge(c);
        }
        em.getTransaction().commit();
    }

    public Cliente buscarPorId(Long id) {
        return em.find(Cliente.class, id);
    }

    public List<Cliente> listarTodos() {
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
        return query.getResultList();
    }
}
