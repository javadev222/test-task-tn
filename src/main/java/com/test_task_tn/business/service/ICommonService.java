package com.test_task_tn.business.service;

import com.test_task_tn.business.domain.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface ICommonService<T extends AbstractEntity> {
    T create(T t);

    List<T> getAll();

    Optional<T> get(long id);

    T update(T t);

    void deleteByID(long id);
}