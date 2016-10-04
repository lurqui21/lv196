package com.softserve.edu.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    /**
     * Wraps CrudRepository#findOne(java.io.Serializable) with Optional.
     *
     * @param id - given id
     * @return the Optional of the entity with given id
     * or an empty Optional if none found.
     */
    default Optional<T> findOneOpt(ID id) {
        return Optional.ofNullable(this.findOne(id));
    }
}