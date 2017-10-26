package com.example.kishan.bankingapp3.FrontEnd.Repositories;

import java.util.Set;

/**
 * Created by Ferin on 2016-08-28.
 */
public interface Repository<E, ID> {
    E findById(ID id);

    E save(E entity);

    E update(E entity);

    E delete(E entity);

    Set<E> findAll();

    int deleteAll();
}
