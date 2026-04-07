package com.angeles.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "EM_EMPLEADOS")
public class Empleado {

    @Id
    @Column(name = "ID_EMPLEADO")
    private Integer idEmpleado;

    @Column(name = "TX_NIF")
    private String nif;

    @Column(name = "TX_NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "TX_APELLIDO1", nullable = false)
    private String apellido1;

    @Column(name = "TX_APELLIDO2", nullable = false)
    private String apellido2;

    @Column(name = "F_NACIMIENTO", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "N_TELEFONO1", nullable = false)
    private String telefono1;

    @Column(name = "N_TELEFONO2", nullable = false)
    private String telefono2;

    @Column(name = "TX_EMAIL", nullable = false)
    private String email;

    @Column(name = "F_ALTA", nullable = false)
    private LocalDate fechaAlta;

    @Column(name = "F_BAJA", nullable = false)
    private LocalDate fechaBaja;

    @Column(name = "CX_EDOCIVIL", nullable = false)
    private String estadoCivil;

    @Column(name = "B_FORMACIONU", nullable = false)
    private String formacionUniversitaria;

    // Constructor vacío
    public Empleado() {}

    // Getters y setters
    public Integer getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(Integer idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido1() { return apellido1; }
    public void  setApellido1(String apellido1) { this.apellido1 = apellido1; }

    public String getApellido2() { return apellido2; }
    public void setApellido2(String apellido2) { this.apellido2 = apellido2; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTelefono1() { return telefono1; }
    public void setTelefono1(String telefono1) { this.telefono1 = telefono1; }

    public String getTelefono2() { return telefono2; }
    public void setTelefono2(String telefono2) { this.telefono2 = telefono2; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta;}

    public LocalDate getFechaBaja() { return fechaBaja; }
    public void setFechaBaja(LocalDate fechaBaja) { this.fechaBaja = fechaBaja; }

    public String getEstadoCivil() { return estadoCivil; }
    public void setEstadoCivil(String estadoCivil) {this.estadoCivil = estadoCivil; }

    public String getFormacionUniversitaria() { return formacionUniversitaria; }
    public void setFormacionUniversitaria(String formacionUniversitaria) { this.formacionUniversitaria = formacionUniversitaria; }
}
