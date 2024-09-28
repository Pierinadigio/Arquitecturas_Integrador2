package org.example.repositoryClass;

import org.example.entidades.Carrera;
import org.example.repository.BaseJPARepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CarreraRepository extends BaseJPARepository<Carrera, Long> {
    private EntityManagerFactory emf;
    private static CarreraRepository instance = null;


    private CarreraRepository(EntityManager em) {
        super(em, Carrera.class, Long.class);
        this.emf = emf;
    }

    public static CarreraRepository getInstance(EntityManagerFactory emf){
        if(instance==null){
            EntityManager em = emf.createEntityManager();
            instance = new CarreraRepository(em);
        }
        return instance;
    }
}