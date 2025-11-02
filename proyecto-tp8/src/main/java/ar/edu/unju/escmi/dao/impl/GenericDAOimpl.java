package ar.edu.unju.escmi.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IGenericDAO;

public abstract class GenericDAOimpl<T> implements IGenericDAO<T> {
    protected EntityManager em;
    private Class<T> clazz;

    public GenericDAOimpl(Class<T> clazz){
        this.em = EmfSingleton.getInstance().getEmf().createEntityManager();
        this.clazz = clazz;
    }

    private void ejecutarTransaccion(Runnable accion){
        EntityTransaction tx = em.getTransaction();
        try{
            tx.begin();
            accion.run();
            tx.commit();
        }catch(Exception e){
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void alta(T entity){
        ejecutarTransaccion(() -> em.persist(entity));
    }

    @Override
    public T buscar(long id){
        return em.find(clazz, id);
    }

    @Override
    public List<T> listar(){
        return em.createQuery("FROM " + clazz.getSimpleName(), clazz).getResultList();
    }

    @Override
    public void modificar(T entity){
        ejecutarTransaccion(() -> em.merge(entity));
    }

    @Override
    public void eliminar(long id){
        ejecutarTransaccion(() -> {
            T entity = em.find(clazz, id);
            if(entity != null) em.remove(entity);
        });
    }
}
