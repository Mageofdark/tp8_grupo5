package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.model.DetalleFactura;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class DetalleFacturaDAO {

    private EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();

    public void guardar(DetalleFactura detalle) {
        em.getTransaction().begin();
        em.persist(detalle);
        em.getTransaction().commit();
    }

    public void actualizar(DetalleFactura detalle) {
        em.getTransaction().begin();
        em.merge(detalle);
        em.getTransaction().commit();
    }

    public DetalleFactura buscarPorId(Long id) {
        return em.find(DetalleFactura.class, id);
    }

    public List<DetalleFactura> listarTodos() {
        TypedQuery<DetalleFactura> query = em.createQuery("SELECT d FROM DetalleFactura d", DetalleFactura.class);
        return query.getResultList();
    }
}
