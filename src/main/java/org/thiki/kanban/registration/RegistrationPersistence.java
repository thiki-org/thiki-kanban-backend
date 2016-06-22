package org.thiki.kanban.registration;

/**
 * Created by joeaniu on 6/21/16.
 */
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
     * @param userRegistration
     */
    void create(UserRegistration userRegistration);
}
