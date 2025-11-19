package ar.edu.unju.escmi.entities;

import jakarta.persistence.*;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String apellido;
    private String domicilio;

    @Column(unique = true)
    private int dni;

    private boolean estado;

    //Constructor vacío creo que es obligatorio
    public Cliente() {}

    //Constructor con los parámetros pe
    public Cliente(String nombre, String apellido, String domicilio, int dni, boolean estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.domicilio = domicilio;
        this.dni = dni;
        this.estado = estado;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public int getDni() { return dni; }
    public void setDni(int dni) {
        if (dni > 1000000 && dni < 99999999) this.dni = dni;
        else throw new IllegalArgumentException("DNI inválido");
    }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
