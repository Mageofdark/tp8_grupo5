package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.model.Factura;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class FacturaDAO {

    private EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();

    public void guardar(Factura factura) {
        em.getTransaction().begin();
        em.persist(factura);
        em.getTransaction().commit();
    }

    public void actualizar(Factura factura) {
        em.getTransaction().begin();
        em.merge(factura);
        em.getTransaction().commit();
    }

    public void eliminar(Long id) {
        em.getTransaction().begin();
        Factura f = em.find(Factura.class, id);
        if (f != null) {
            f.setEstado(false);
            em.merge(f);
        }
        em.getTransaction().commit();
    }

    public Factura buscarPorId(Long id) {
        return em.find(Factura.class, id);
    }

    public List<Factura> listarTodas() {
        TypedQuery<Factura> query = em.createQuery("SELECT f FROM Factura f", Factura.class);
        return query.getResultList();
    }

    public List<Factura> listarFacturasMayoresA(double monto) {
        TypedQuery<Factura> query = em.createQuery("SELECT f FROM Factura f WHERE f.total > :monto", Factura.class);
        query.setParameter("monto", monto);
        return query.getResultList();
    }
}
