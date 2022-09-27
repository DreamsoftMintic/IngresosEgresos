package com.dreamsoft.ingresosegresos.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "empresa")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "nombre_empresa", unique = true)
    private String nombreEmpresa;
    @Column(name = "direccion_empresa")
    private String direccionEmpresa;
    @Column(name = "telefono_empresa")
    private String telefonoEmpresa;
    @Column(name = "NIT", unique = true)
    private int NIT;

    @Column(name = "fecha_cr")
    private LocalDate fechaCr;
    @Column(name = "fecha_upd")
    private LocalDate fechaUpd;

    @OneToMany(targetEntity = Empleado.class, mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Empleado> empleados;
/*
    @OneToMany(targetEntity = MovimientoDinero.class, mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MovimientoDinero> movimientos;
*/
    public Empresa(){

    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public String getTelefonoEmpresa() {
        return telefonoEmpresa;
    }

    public int getNIT() {
        return NIT;
    }

    public LocalDate getFechaCr() {
        return fechaCr;
    }

    public LocalDate getFechaUpd() {
        return fechaUpd;
    }


    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public void setTelefonoEmpresa(String telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    public void setNIT(int NIT) {
        this.NIT = NIT;
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

    @JsonManagedReference
    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
    /*
    @JsonManagedReference (value = "empresa-movimiento")
    public List<MovimientoDinero> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<MovimientoDinero> movimientos) {
        this.movimientos = movimientos;
    }
*/
    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nombreEmpresa='" + nombreEmpresa + '\'' +
                ", direccionEmpresa='" + direccionEmpresa + '\'' +
                ", telefonoEmpresa='" + telefonoEmpresa + '\'' +
                ", NIT=" + NIT +
                ", fechaCr=" + fechaCr +
                ", fechaUpd=" + fechaUpd +
                ", empleados=" + empleados +
                //", movimientos=" + movimientos +
                '}';
    }
}
