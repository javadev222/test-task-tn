package com.test_task_tn.business.dao;

import com.test_task_tn.business.domain.User;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CommonRepository<User> {
    List<User> findByIsOnlineAndStatusTimeAfter (boolean isOnline, Timestamp statusTime);
    List<User> findByIsOnline (boolean isOnline);
    List<User> findByStatusTimeAfter (Timestamp statusTime);
    Optional<User> findByName(String name);
}
