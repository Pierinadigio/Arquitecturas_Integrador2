package org.example.repositoryImplementaciones;
import org.example.repository.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

public class BaseJPARepository<Entity, ID extends Serializable> implements Repository<Entity, ID> {
    protected EntityManagerFactory emf;
    protected EntityManager em;
    protected Class<Entity> entityClass;
    protected Class<ID> idClass;


        public BaseJPARepository(EntityManager em, Class<Entity> entityClass, Class<ID> idClass) {
            this.em = em;
            this.entityClass = entityClass;
            this.idClass = idClass;
        }


        public Entity findById(ID id) {
        Entity entity = em.find(entityClass, id);
        return entity;
    }

    public void insert(Entity entity) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }

    }

    public List<Entity> findAll() {
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        TypedQuery<Entity> query = em.createQuery(jpql, entityClass);
        return query.getResultList();

    }

    public void delete(Entity entity) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Entity mergedEntity = em.merge(entity);
            em.remove(mergedEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }

    }

    public void close() {
        if (emf != null) {
            emf.close();
        }
    }


}
