package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.entities.Producto;
import java.util.List;

public interface IProductoDAO {
     void guardar(Producto producto);
    void actualizar(Producto producto);
    void eliminar(Long id);
    Producto buscarPorId(Long id);
    List<Producto> listarTodos();
}
