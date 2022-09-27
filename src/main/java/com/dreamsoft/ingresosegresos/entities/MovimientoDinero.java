package com.dreamsoft.ingresosegresos.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "movimiento_dinero")
public class MovimientoDinero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "monto")
    private double monto;

    @Column(name = "concepto")
    private String concepto;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "fecha_movimiento")
    private Date fechaMovimiento;
    @Column(name = "fecha_cr")
    private LocalDate fechaCr;
    @Column(name = "fecha_upd")
    private LocalDate fechaUpd;
/*
    @ManyToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

 */

    @ManyToOne
    @JoinColumn(name = "empleado_id", referencedColumnName = "id")
    private Empleado empleado;

    @Column(name = "empresa_id")
    private long empresaId;

    public MovimientoDinero(){

    }


    public double getMonto() {
        return monto;
    }

    public String getConcepto() {
        return concepto;
    }

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    @JsonBackReference
    public Empleado getEmpleado() {
        return empleado;
    }

    public LocalDate getFechaCr() {
        return fechaCr;
    }

    public LocalDate getFechaUpd() {
        return fechaUpd;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
     }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFechaCr(LocalDate fechaCr) {
        this.fechaCr = fechaCr;
    }

    public void setFechaUpd(LocalDate fechaUpd) {
        this.fechaUpd = fechaUpd;
    }


    /*
        @JsonBackReference(value = "empresa-movimiento")
        public Empresa getEmpresa() {
            return empresa;
        }

        public void setEmpresa(Empresa empresa) {
            this.empresa = empresa;
        }
    */
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(long empresaId) {
        this.empresaId = empresaId;
    }

    @Override
    public String toString() {
        return "MovimientoDinero{" +
                "id=" + id +
                ", monto=" + monto +
                ", concepto='" + concepto + '\'' +
                ", fechaMovimiento=" + fechaMovimiento +
                ", fechaCr=" + fechaCr +
                ", fechaUpd=" + fechaUpd +
                //", empresa=" + empresa +
                ", empleado=" + empleado +
                '}';
    }
}
