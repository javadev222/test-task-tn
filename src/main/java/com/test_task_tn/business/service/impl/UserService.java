package com.test_task_tn.business.service.impl;

import com.test_task_tn.business.dao.UserRepository;
import com.test_task_tn.business.domain.StatusResponse;
import com.test_task_tn.business.domain.User;
import com.test_task_tn.shared.exceptions.NotFoundUserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service("jpaUserService")
@Transactional
public class UserService extends AbstractService<User, UserRepository> {
    public UserService(UserRepository repository) {
        super(repository);
    }

    public List<User> getAll(Long id, Boolean isOnline) {
        if (id != null) {
            Timestamp time = new Timestamp(id);
            if (isOnline != null) {
                return repository.findByIsOnlineAndStatusTimeAfter(isOnline, time);
            }
            return repository.findByStatusTimeAfter(time);
        } else if (isOnline != null) {
            return repository.findByIsOnline(isOnline);
        }
        return repository.findAll();
    }

    public StatusResponse changeStatus(Long id, Boolean currentStatus) {
        Timestamp updateTime = new Timestamp(System.currentTimeMillis());
        User user = get(id).orElseThrow(NotFoundUserException::new);
        boolean old = user.isOnline();
        user.setOnline(currentStatus);
        user.setStatusTime(updateTime);
        create(user);
        return new StatusResponse(id, old, currentStatus);
    }
}
