package org.example.factory;

import org.example.repositoryImplementaciones.CarreraRepository;
import org.example.repositoryImplementaciones.EstudianteCarreraRepository;
import org.example.repositoryImplementaciones.EstudianteRepository;


public abstract class Repositoryfactory {
    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;


    public abstract EstudianteRepository getEstudianteRepository();
    public abstract CarreraRepository getCarreraRepository();
    public abstract EstudianteCarreraRepository getEstudianteCarreraRepository();


    public static Repositoryfactory getRepositoryFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_JDBC:
                return new MySQLRepositoryFactory();
            case DERBY_JDBC:
                return new DerbyRepositoryFactory();
            default:
                return null;
        }
    }
}