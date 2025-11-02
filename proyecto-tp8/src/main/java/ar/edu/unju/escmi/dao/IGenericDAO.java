package ar.edu.unju.escmi.dao;

import java.util.List;

public interface IGenericDAO<T> {
    void alta(T entity);
    T buscar(long id);
    List<T> listar();
    void modificar(T entity);
    void eliminar(long id);
}