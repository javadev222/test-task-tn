package com.test_task_tn.business.service.impl;

import com.test_task_tn.business.dao.CommonRepository;
import com.test_task_tn.business.domain.AbstractEntity;
import com.test_task_tn.business.service.ICommonService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public abstract class AbstractService<T extends AbstractEntity, R extends CommonRepository<T>> implements ICommonService<T> {

    protected final R repository;

    @Override
    public T create(T t) {
        return repository.saveAndFlush(t);
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> get(long id) {
        return repository.findById(id);
    }

    @Override
    public T update(T t) {
        return repository.saveAndFlush(t);
    }

    @Override
    public void deleteByID(long id) {
        repository.deleteById(id);
    }
}
