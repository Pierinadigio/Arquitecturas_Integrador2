package org.example.repositoryImplementaciones;

import org.example.entidades.Carrera;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CarreraRepository extends BaseJPARepository<Carrera, Long> {
     private static CarreraRepository instance = null;


    private CarreraRepository(EntityManager em) {
        super(em, Carrera.class, Long.class);
   }

    public static CarreraRepository getInstance(EntityManagerFactory emf){
        if(instance==null){
            EntityManager em = emf.createEntityManager();
            instance = new CarreraRepository(em);
        }
        return instance;
    }
}