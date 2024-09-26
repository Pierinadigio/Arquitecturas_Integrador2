package org.example.DTO;

import org.example.entidades.Carrera;

import java.util.ArrayList;
import java.util.List;

public class EstudianteDTO {
    private String dni;
    private String nombres;
    private String apellido;
    private String genero;
    private String ciudadResidencia;
    private String numeroLibretaUniversitaria;


    public EstudianteDTO(String dni, String nombres, String apellido, String genero, String ciudadResidencia ,String numeroLibretaUniversitaria) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellido = apellido;
        this.genero = genero;
        this.ciudadResidencia = ciudadResidencia;
        this.numeroLibretaUniversitaria = numeroLibretaUniversitaria;

    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

   public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public String getNumeroLibretaUniversitaria() {
        return numeroLibretaUniversitaria;
    }

    public void setNumeroLibretaUniversitaria(String numeroLibretaUniversitaria) {
        this.numeroLibretaUniversitaria = numeroLibretaUniversitaria;
    }


    @Override
    public String toString() {
        return "Estudiante" +
                " dni='" + dni + '\'' +
                ", nombres='" + nombres + '\'' +
                "apellido='" + apellido + '\''
                ;
    }
}
