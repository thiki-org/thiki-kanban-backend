package org.thiki.kanban.user;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsersPersistence {
    Integer create(User user);

    User findById(@Param("id") String id);

    List<User> loadAll();

    Integer update(User user);

    Integer deleteById(@Param("id") String id);

    User findByEmail(String email);
}
