package org.example.repository;

import org.example.DTO.EstudianteDTO;
import org.example.entidades.Estudiante;

import java.util.List;

public interface EstudianteRepository {
    void insert(Estudiante estudiante);
    List<Estudiante> findAll();
    Estudiante findById(String dni);
    List<EstudianteDTO> listarEstudiantesOrdenados(String ordenPor);
    EstudianteDTO findByNumeroLibreta(String numeroLibreta);
    List<EstudianteDTO> findByGenero(String genero);
    void delete(Estudiante estudiante);
    void close();
}
