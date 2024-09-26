package org.example.repository;

import org.example.DTO.EstudianteDTO;
import org.example.DTO.EstudiantesCarreraDTO;
import org.example.DTO.ReporteDTO;
import java.util.Date;
import java.util.List;

public interface Estudiante_CarreraRepository {
    void matricularEstudiante(String dniEstudiante, long idCarrera, Date fechaInscripcion);
    List<EstudiantesCarreraDTO> obtenerCarrerasConEstudiantesInscritos();
    List<EstudianteDTO> obtenerEstudiantesPorCarreraYCiudad(long idCarrera, String ciudad);
    List<ReporteDTO> generarReporteCarreras();
    void close();
}
