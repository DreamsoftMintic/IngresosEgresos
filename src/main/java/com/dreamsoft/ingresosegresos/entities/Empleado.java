package com.dreamsoft.ingresosegresos.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
//en el modelo entidad-relacion user es la identificacion de tabla empleado
@Entity
@Table(name = "empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "nombre_empleado")
    private String nombreEmpleado;
    @Column(name = "documento", unique = true)
    private int documento;


    //agregado 23 sep 2022
    //@javax.validation.constraints.NotEmpty
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "correo_empleado", unique = true)
    private String correoEmpleado;

    @Column(name = "pass")
    private String pass;

    @Column(name = "telefono")
    private String telefono;
    @Column(name = "rol")
    private String rol;

    @Column(name = "fecha_cr")
    private LocalDate fechaCr;
    @Column(name = "fecha_upd")
    private LocalDate fechaUpd;

    @Column(name = "estado")
    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    @OneToMany(targetEntity = MovimientoDinero.class, mappedBy = "empleado", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MovimientoDinero> movimientos;

    public Empleado(){
    }
    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public int getDocumento() { return documento; }

    public String getCorreoEmpleado() {
        return correoEmpleado;
    }

    public String getRol() {
        return rol;
    }

    public LocalDate getFechaCr() { return fechaCr; }

    public LocalDate getFechaUpd() { return fechaUpd; }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
            }

    public void setDocumento(int documento) {
        this.documento = documento;
        }

    public void setCorreoEmpleado(String correoEmpleado) {
        this.correoEmpleado = correoEmpleado;
            }

    public void setRol(String rol) {
        this.rol = rol;
            }

    public void setFechaCr(LocalDate fechaCr) {
        this.fechaCr = fechaCr;
    }

    public void setFechaUpd(LocalDate fechaUpd) {
        this.fechaUpd = fechaUpd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonBackReference
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<MovimientoDinero> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<MovimientoDinero> movimientos) {
        this.movimientos = movimientos;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getUsername() {return username;  }

    public void setUsername(String username) {this.username = username; }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombreEmpleado='" + nombreEmpleado + '\'' +
                ", documento=" + documento +
                ", correoEmpleado='" + correoEmpleado + '\'' +
                ", username='" + username + '\'' +
                ", rol=" + rol +
                ", fechaCr=" + fechaCr +
                ", fechaUpd=" + fechaUpd +
                ", estado=" + estado +
                //", empresa=" + empresa.getId() +
                ", movimientos=" + movimientos +
                '}';
    }
}
