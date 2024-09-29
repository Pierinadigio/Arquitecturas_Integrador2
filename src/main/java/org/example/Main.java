package org.example;
import com.opencsv.CSVReader;
import org.example.DTO.EstudianteDTO;
import org.example.DTO.EstudiantesCarreraDTO;
import org.example.DTO.ReporteDTO;
import org.example.entidades.Carrera;
import org.example.entidades.Estudiante;
import org.example.factory.Repositoryfactory;
import org.example.repositoryImplementaciones.CarreraRepository;
import org.example.repositoryImplementaciones.EstudianteCarreraRepository;
import org.example.repositoryImplementaciones.EstudianteRepository;

import java.io.FileReader;
import java.util.Date;
import java.util.List;

public class Main {
    public static <Set> void main(String[] args) {
        Repositoryfactory RepositoryFactory = Repositoryfactory.getRepositoryFactory(1);

        EstudianteRepository estudianteRepository = RepositoryFactory.getEstudianteRepository();
        CarreraRepository carreraRepository = RepositoryFactory.getCarreraRepository();
        EstudianteCarreraRepository estudiante_carreraRepository = RepositoryFactory.getEstudianteCarreraRepository();

        // Cargar Estudiantes desde CSV
        cargarEstudiantesDesdeCSV(estudianteRepository, "src/main/java/org/example/datasets/estudiantes.csv");
        // Cargar Carreras desde CSV
        cargarCarrerasDesdeCSV(carreraRepository, "src/main/java/org/example/datasets/carreras.csv");

        //Nuevo estudiante
        Estudiante e = new Estudiante();
        e.setDni("29.547.666");
        e.setNombres("Micaela");
        e.setApellido("Dominguez");
        e.setEdad(20);
        e.setGenero("femenino");
        e.setNumeroLibretaUniversitaria("47666");
        e.setCiudadResidencia("Tandil");

        // Punto a) ALTA Estudiante
        estudianteRepository.insert(e);

        //Punto b) Matricular Estudiante en Carrera
        Date fechaInscripcion = new Date();
        estudiante_carreraRepository.matricularEstudiante(e.getDni(), 1, fechaInscripcion);
        estudiante_carreraRepository.matricularEstudiante("30.243.986", 1, fechaInscripcion);
        estudiante_carreraRepository.matricularEstudiante(e.getDni(), 2, fechaInscripcion);

        // Punto c) Recuperar estudiantes ordenados
        // Ordenados por apellido
        List<EstudianteDTO> estudiantesOrdenadosPorApellido = estudianteRepository.listarEstudiantesOrdenados("apellido");
        System.out.println("PUNTO C: Estudiantes ordenados por apellido:");
        for (EstudianteDTO estudiante : estudiantesOrdenadosPorApellido) {
            System.out.println(estudiante);
        }

        // Ordenados por número de libreta
        List<EstudianteDTO> estudiantesOrdenadosPorLibreta = estudianteRepository.listarEstudiantesOrdenados("libreta");
        System.out.println("PUNTO C: Estudiantes ordenados por número de libreta:");
        for (EstudianteDTO estudiante : estudiantesOrdenadosPorLibreta) {
            System.out.println(estudiante  + "Nro Libreta :" + estudiante.getNumeroLibretaUniversitaria());
        }

        //Punto d) Obtener Estudiante por Número de Libreta
       EstudianteDTO estudianteDTO = estudianteRepository.findByNumeroLibreta(e.getNumeroLibretaUniversitaria());
       if (estudianteDTO != null) {
            System.out.println("PUNTO D: Estudiante encontrado: " + estudianteDTO);
       } else {
            System.out.println("Estudiante no encontrado.");
       }

       //Punto e) Listar estudiantes por género
        String generoBuscado = "masculino";
        List<EstudianteDTO> estudiantesPorGenero = estudianteRepository.findByGenero(generoBuscado);
        System.out.println("PUNTO E: Listado estudiantes de género " + generoBuscado + ":");
        for (EstudianteDTO estudiante : estudiantesPorGenero) {
            System.out.println(estudiante);
        }

        //Punto f) carreras con estudiantes inscriptos
        List<EstudiantesCarreraDTO> carrerasConEstudiantes = estudiante_carreraRepository.obtenerCarrerasConEstudiantesInscritos();

        System.out.println("PUNTO F: Carreras con estudiantes inscritos, ordenadas por cantidad:");
        for (EstudiantesCarreraDTO dto : carrerasConEstudiantes) {
            System.out.println(dto);
        }

        //Punto g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia
        int idCarrera = 1;
        String ciudad = "Tandil";
        List<EstudianteDTO> estudiantes = estudiante_carreraRepository.obtenerEstudiantesPorCarreraYCiudad(idCarrera, ciudad);

        System.out.println("PUNTO G: Estudiantes en la carrera con ID " + idCarrera + " y ciudad " + ciudad + ":");
        for (EstudianteDTO estudiante : estudiantes) {
            System.out.println(estudiante);
        }

        //PUNTO 3
        List<ReporteDTO> reporteCarreras = estudiante_carreraRepository.generarReporteCarreras();

        System.out.println("PUNTO 3: Reporte de Carreras:");
        for (ReporteDTO reporte : reporteCarreras) {
            System.out.println(reporte);
        }


        estudianteRepository.close();
        carreraRepository.close();
        estudiante_carreraRepository.close();
    }





    private static void cargarEstudiantesDesdeCSV(EstudianteRepository estudianteRepository, String archivoCsv) {
        try (CSVReader csvReader = new CSVReader(new FileReader(archivoCsv))) {
            String[] fila;
            while ((fila = csvReader.readNext()) != null) {
                Estudiante estudiante = new Estudiante();
                estudiante.setDni(fila[0]);
                estudiante.setNombres(fila[1]);
                estudiante.setApellido(fila[2]);
                estudiante.setEdad(Integer.parseInt(fila[3]));
                estudiante.setGenero(fila[4]);
                estudiante.setNumeroLibretaUniversitaria(fila[5]);
                estudiante.setCiudadResidencia(fila[6]);

                estudianteRepository.insert(estudiante);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void cargarCarrerasDesdeCSV(CarreraRepository carreraRepository, String archivoCsv) {
        try (CSVReader csvReader = new CSVReader(new FileReader(archivoCsv))) {
            String[] fila;
            while ((fila = csvReader.readNext()) != null) {
                Carrera carrera = new Carrera();
                carrera.setNombre(fila[1]);

                carreraRepository.insert(carrera);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
