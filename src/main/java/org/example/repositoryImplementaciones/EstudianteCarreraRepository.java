package org.example.repositoryImplementaciones;

import org.example.DTO.EstudianteDTO;
import org.example.DTO.ReporteDTO;
import org.example.entidades.Carrera;
import org.example.entidades.Estudiante;
import org.example.entidades.Estudiante_Carrera;
import org.example.DTO.EstudiantesCarreraDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EstudianteCarreraRepository extends BaseJPARepository<Estudiante_Carrera, Long> {
    private static EstudianteCarreraRepository instance;


    private EstudianteCarreraRepository(EntityManager em) {
        super(em, Estudiante_Carrera.class, Long.class);

    }

    public static synchronized EstudianteCarreraRepository getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            EntityManager em = emf.createEntityManager();
            instance = new EstudianteCarreraRepository(em);
        }
        return instance;
    }

    //Matricula un estudiante a una carrera
    public void matricularEstudiante(String dniEstudiante, long idCarrera, Date fechaInscripcion) {
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();

            Estudiante estudiante = em.find(Estudiante.class, dniEstudiante);
            if (estudiante == null) {
                throw new IllegalArgumentException("Estudiante no encontrado con DNI: " + dniEstudiante);
            }

            Carrera carrera = em.find(Carrera.class, idCarrera);
            if (carrera == null) {
                throw new IllegalArgumentException("Carrera no encontrada con ID: " + idCarrera);
            }

            Estudiante_Carrera estudianteCarrera = new Estudiante_Carrera();
            estudianteCarrera.setEstudiante(estudiante);
            estudianteCarrera.setCarrera(carrera);
            estudianteCarrera.setFechaInscripcion(fechaInscripcion);
            estudianteCarrera.setAntiguedad();
            boolean graduado = estudiante.isGraduado();
            estudianteCarrera.setGraduado(graduado);

            em.persist(estudianteCarrera);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Obtiene carreras con estudiantes inscriptos
    public List<EstudiantesCarreraDTO> obtenerCarrerasConEstudiantesInscritos() {
        String jpql = "SELECT new org.example.DTO.EstudiantesCarreraDTO(c.nombre, COUNT(ec.estudiante)) " +
                "FROM Carrera c JOIN Estudiante_Carrera ec ON c.id = ec.carrera.id " +
                "GROUP BY c.id, c.nombre " +
                "ORDER BY COUNT(ec.estudiante) DESC";

        TypedQuery<EstudiantesCarreraDTO> query = em.createQuery(jpql, EstudiantesCarreraDTO.class);
        return query.getResultList();
    }

    // Obtiene estudiantes por carrera y ciudad
    public List<EstudianteDTO> obtenerEstudiantesPorCarreraYCiudad(long idCarrera, String ciudad) {
        String jpql = "SELECT e " +
                "FROM Estudiante_Carrera ec JOIN ec.estudiante e " +
                "WHERE ec.carrera.id = :idCarrera AND e.ciudadResidencia = :ciudad";

        TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
        query.setParameter("idCarrera", idCarrera);
        query.setParameter("ciudad", ciudad);

        List<Estudiante> estudiantes = query.getResultList();
        return estudiantes.stream()
                .map(e -> new EstudianteDTO(
                        e.getDni(),
                        e.getNombres(),
                        e.getApellido(),
                        e.getGenero(),
                        e.getCiudadResidencia(),
                        e.getNumeroLibretaUniversitaria()))
                .collect(Collectors.toList());
    }

    // Genera reporte de carreras con estudiantes inscriptos y egresados por año
    public List<ReporteDTO> generarReporteCarreras() {
        // Obtener las carreras
        String jpqlCarreras = "SELECT c FROM Carrera c ORDER BY c.nombre";
        TypedQuery<Carrera> queryCarreras = em.createQuery(jpqlCarreras, Carrera.class);
        List<Carrera> carreras = queryCarreras.getResultList();

        List<ReporteDTO> reportes = new ArrayList<>();

        for (Carrera carrera : carreras) {
            // Obtener inscriptos por año
            Map<Integer, Long> inscriptosPorAno = em.createQuery(
                            "SELECT YEAR(ec.fechaInscripcion), COUNT(ec) " +
                                    "FROM Estudiante_Carrera ec " +
                                    "WHERE ec.carrera.id = :idCarrera " +
                                    "GROUP BY YEAR(ec.fechaInscripcion)",
                            Object[].class)
                    .setParameter("idCarrera", carrera.getId())
                    .getResultList()
                    .stream()
                    .collect(Collectors.toMap(
                            e -> (Integer) e[0],
                            e -> (Long) e[1]
                    ));

            Map<Integer, Long> egresadosPorAno = em.createQuery(
                            "SELECT YEAR(ec.fechaInscripcion), COUNT(ec) " +
                                    "FROM Estudiante_Carrera ec " +
                                    "WHERE ec.carrera.id = :idCarrera AND ec.isGraduado = true " +
                                    "GROUP BY YEAR(ec.fechaInscripcion)",
                            Object[].class)
                    .setParameter("idCarrera", carrera.getId())
                    .getResultList()
                    .stream()
                    .collect(Collectors.toMap(
                            e -> (Integer) e[0],
                            e -> (Long) e[1]
                    ));

            reportes.add(new ReporteDTO(carrera.getNombre(), inscriptosPorAno, egresadosPorAno));
        }

        return reportes;
    }
}
