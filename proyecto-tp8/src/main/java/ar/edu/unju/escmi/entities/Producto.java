package ar.edu.unju.escmi.entities;

import jakarta.persistence.*;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int codigo;
    private String descripcion;
    private int precioUnitario;
    private boolean estado;

    public Producto() {}

    public Producto(int codigo, String descripcion, int precioUnitario, boolean estado) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.estado = estado;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public int getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(int precioUnitario) {
        if (precioUnitario > 0) this.precioUnitario = precioUnitario;
        else throw new IllegalArgumentException("El precio debe ser mayor a 0");
    }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}

