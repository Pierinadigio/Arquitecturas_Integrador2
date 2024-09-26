package org.example.repository;

import org.example.entidades.Carrera;

import java.util.List;

public interface CarreraRepository {
    void insert (Carrera carrera);
    List<Carrera> findAll();
    Carrera findById(long id);
    void delete(Carrera carrera);
    void close();
}
