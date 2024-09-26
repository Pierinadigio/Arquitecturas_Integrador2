package org.example.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrera  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // PK
    private String nombre;

    @OneToMany (mappedBy = "carrera", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Estudiante_Carrera> estudiantes = new ArrayList<>();

    // Getters y Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
