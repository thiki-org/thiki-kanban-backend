package org.thiki.kanban.user;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface UsersPersistence {
    Integer create(UserProfile userProfile);

    UserProfile findById(@Param("id") String id);

    List<UserProfile> loadAll();

    Integer update(UserProfile userProfile);

    Integer deleteById(@Param("id") String id);

    UserProfile findByEmail(String email);
    
    UserProfile findByPhone(String phone);

    UserProfile findByName(String userName);

//    boolean existsUser(String userName, String phone, String mail);
    boolean existsUser(Map<String, String> filter);

}
