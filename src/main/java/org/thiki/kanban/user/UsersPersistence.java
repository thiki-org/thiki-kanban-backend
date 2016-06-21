package org.thiki.kanban.user;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsersPersistence {
    Integer create(UserProfile userProfile);

    UserProfile findById(@Param("id") String id);

    List<UserProfile> loadAll();

    Integer update(UserProfile userProfile);

    Integer deleteById(@Param("id") String id);

    UserProfile findByEmail(String email);
}
