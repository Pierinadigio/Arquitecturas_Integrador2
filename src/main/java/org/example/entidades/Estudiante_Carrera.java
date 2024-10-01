package org.example.entidades;

import javax.persistence.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
public class Estudiante_Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int antiguedad;
    private Date fechaInscripcion;
    private boolean isGraduado;

    @ManyToOne
    private Carrera carrera;
    @ManyToOne
    private Estudiante estudiante;

    public boolean isGraduado() {

        return isGraduado;
    }

    public void setGraduado(boolean graduado) {
        this.isGraduado = graduado;

    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public int  calcularAntiguedad() {
        if (fechaInscripcion == null) {
            return 0; // o lanzar una excepción según tu caso
        }

        long diferenciaMillis = new Date().getTime() - fechaInscripcion.getTime();
        long diferenciaDias = TimeUnit.DAYS.convert(diferenciaMillis, TimeUnit.MILLISECONDS);

        return (int) (diferenciaDias / 365); // Co
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad() {
        this.antiguedad = calcularAntiguedad();
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}
