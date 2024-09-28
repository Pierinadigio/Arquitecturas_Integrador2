package org.example.factory;

import org.example.repositoryClass.CarreraRepository;
import org.example.repositoryClass.EstudianteCarreraRepository;
import org.example.repositoryClass.EstudianteRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MySQLRepositoryFactory extends Repositoryfactory {
    private EntityManagerFactory emf;

    public MySQLRepositoryFactory() {

        this.emf = Persistence.createEntityManagerFactory("mysql_persistence_unit");
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

