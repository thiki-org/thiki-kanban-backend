package org.thiki.kanban.auth;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserPersistence {
    Integer create(User user);

    User findById(@Param("id") String id);

    List<User> loadAll();

    Integer update(User users);

    Integer deleteById(@Param("id") String id);
    User findByIdandsalt(@Param("id") String id,@Param("salt") String salt);
}
