package ar.edu.unju.escmi.entities;

import jakarta.persistence.*;

@Entity
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int cantidad;
    private int subtotal;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Factura factura;

    public DetalleFactura() {}

    public DetalleFactura(Producto producto, Factura factura, int cantidad) {
        this.producto = producto;
        this.factura = factura;
        this.cantidad = cantidad;
        this.subtotal = cantidad * producto.getPrecioUnitario();
    }

    public int getId() { return id; }
    
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public int getSubtotal() { return subtotal; }
    public void setSubtotal(int subtotal) { this.subtotal = subtotal; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }
}
