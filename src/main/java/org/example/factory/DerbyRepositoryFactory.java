package org.example.factory;

import org.example.repositoryClass.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DerbyRepositoryFactory extends Repositoryfactory {
    private EntityManagerFactory emf;

    public DerbyRepositoryFactory() {

        this.emf = Persistence.createEntityManagerFactory("derby_persistence_unit");
    }


    public EstudianteRepository getEstudianteRepository() {
        return EstudianteRepository.getInstance(emf);
    }


    public CarreraRepository getCarreraRepository() {

        return CarreraRepository.getInstance(emf);
    }

    public EstudianteCarreraRepository getEstudianteCarreraRepository() {

        return EstudianteCarreraRepository.getInstance(emf);
    }


}
