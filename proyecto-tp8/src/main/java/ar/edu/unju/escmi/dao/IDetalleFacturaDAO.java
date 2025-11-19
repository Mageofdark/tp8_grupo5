package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.entities.DetalleFactura;
import java.util.List;

public interface IDetalleFacturaDAO {
    void guardar(DetalleFactura detalle);
    void actualizar(DetalleFactura detalle);
    DetalleFactura buscarPorId(Long id);
    List<DetalleFactura> listarTodos();
    
}
