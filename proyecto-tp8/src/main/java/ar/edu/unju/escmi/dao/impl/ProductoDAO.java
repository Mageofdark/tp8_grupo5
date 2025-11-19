package ar.edu.unju.escmi.dao.impl;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.entities.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProductoDAO implements ar.edu.unju.escmi.dao.IProductoDAO {

    private EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();

    public void guardar(Producto producto) {
        em.getTransaction().begin();
        em.persist(producto);
        em.getTransaction().commit();
    }

    public void actualizar(Producto producto) {
        em.getTransaction().begin();
        em.merge(producto);
        em.getTransaction().commit();
    }

    public void eliminar(Long id) {
        em.getTransaction().begin();
        Producto p = em.find(Producto.class, id);
        if (p != null) {
            p.setEstado(false);
            em.merge(p);
        }
        em.getTransaction().commit();
    }

    public Producto buscarPorId(Long id) {
        return em.find(Producto.class, id);
    }

    public List<Producto> listarTodos() {
        TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p", Producto.class);
        return query.getResultList();
    }
}
