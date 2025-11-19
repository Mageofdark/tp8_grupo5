package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.entities.Factura;
import java.util.List;

public interface IFacturaDAO {
    void guardar(Factura factura);
    void actualizar(Factura factura);
    void eliminar(Long id);
    Factura buscarPorId(Long id);
    List<Factura> listarTodas();
    List<Factura> listarFacturasMayoresA(double monto);
}
