package org.example.repositoryClass;
import org.example.DTO.EstudianteDTO;
import org.example.entidades.Estudiante;
import org.example.repository.BaseJPARepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public class EstudianteRepository extends BaseJPARepository<Estudiante, String> {
    private static EstudianteRepository instance;
    private EntityManagerFactory emf;
    private EstudianteRepository(EntityManager em) {
        super(em, Estudiante.class, String.class);
        this.emf = emf;
    }

    public static EstudianteRepository getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            EntityManager em = emf.createEntityManager();
            instance = new EstudianteRepository(em);
        }
        return instance;
    }

    // Lista Estudiantes ORDENADOS por algún criterio
    public List<EstudianteDTO> listarEstudiantesOrdenados(String ordenPor) {
        try {
            String jpql = "SELECT e FROM Estudiante e";

            if (ordenPor != null && !ordenPor.isEmpty()) {
                jpql += " ORDER BY ";
                switch (ordenPor.toLowerCase()) {
                    case "apellido":
                        jpql += "e.apellido";
                        break;
                    case "libreta":
                        jpql += "e.numeroLibretaUniversitaria";
                        break;
                    default:
                        throw new IllegalArgumentException("Criterio de ordenamiento no válido.");
                }
            }

            TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
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
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // Recupera estudiante por número de libreta
    public EstudianteDTO findByNumeroLibreta(String numeroLibreta) {
        try {
            String jpql = "SELECT e FROM Estudiante e WHERE e.numeroLibretaUniversitaria = :numeroLibreta";
            TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
            query.setParameter("numeroLibreta", numeroLibreta);

            Estudiante estudiante = query.getSingleResult();
            return new EstudianteDTO(estudiante.getDni(), estudiante.getNombres(),
                    estudiante.getApellido(), estudiante.getGenero(),
                    estudiante.getCiudadResidencia(),
                    estudiante.getNumeroLibretaUniversitaria());
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Recupera todos los estudiantes en base a su género
    public List<EstudianteDTO> findByGenero(String genero) {
        try {
            String jpql = "SELECT e FROM Estudiante e WHERE e.genero = :genero ORDER BY e.apellido, e.nombres";
            TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
            query.setParameter("genero", genero);

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
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }




}
