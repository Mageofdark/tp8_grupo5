package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.entities.Cliente;
import java.util.List;

public interface IClienteDAO {
    void guardar(Cliente cliente);
    void actualizar(Cliente cliente);
    void eliminar(Long id);
    Cliente buscarPorId(Long id);
    List<Cliente> listarTodos();
}
