package org.example.repository;

import java.io.Serializable;
import java.util.List;

public interface Repository <Entity, ID extends Serializable> {
        public Entity findById(ID id);
        public List<Entity> findAll();
        public void insert (Entity entity);
        public void delete(Entity entity);

}
