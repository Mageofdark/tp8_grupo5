package ar.edu.unju.escmi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate fecha;
    private String domicilio;
    private int total;
    private boolean estado;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<DetalleFactura> detalles = new ArrayList<>();


    public Factura() {
        this.fecha = LocalDate.now();
    }

    public Factura(Cliente cliente, String domicilio, boolean estado) {
        this.fecha = LocalDate.now();
        this.cliente = cliente;
        this.domicilio = domicilio;
        this.estado = estado;
    }

    public int getId() { return id; }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public List<DetalleFactura> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleFactura> detalles) { this.detalles = detalles; }
}

