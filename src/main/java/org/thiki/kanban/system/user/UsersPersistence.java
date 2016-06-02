package org.thiki.kanban.system.user;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsersPersistence {
    Integer create(User user);

    User findById(@Param("id") int id);

    List<User> loadAll();

    Integer update(User user);

    Integer deleteById(@Param("id") int id);

}
