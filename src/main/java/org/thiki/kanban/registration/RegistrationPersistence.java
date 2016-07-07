package org.thiki.kanban.registration;


import org.springframework.stereotype.Repository;

/**
 * Created by joeaniu on 6/21/16.
 */
@Repository
public interface RegistrationPersistence {
    /**
     * 是否已经存在了相同的用户, 名字、手机、邮箱任一项相同即返回true
     * @param name
     * @param phone
     * @param mail
     * @return
     */
//    boolean existsUser(String name, String phone, String mail);

    /**
     * 增加注册信息
     *
     * @param userRegistration
     */
    void create(Registration userRegistration);

    /**
     * 根据用户名查找用户嘻嘻
     *
     * @param userName
     */
    Registration findByName(String userName);

    boolean existsUser(Registration registration);

    Registration findByEmail(String email);
}
